package com.cuixing.mes.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.mes.ProductException;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mes.entity.*;
import com.cuixing.mes.mapper.MesInputLogMapper;
import com.cuixing.mes.mapper.MesTinningWorkorderMapper;
import com.cuixing.mes.service.IMesProductWorkorderService;
import com.cuixing.mes.service.MesTinningWorkorderService;
import com.cuixing.mes.utils.CheckScopeUtil;
import com.cuixing.mes.utils.CodeGenerator;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.RemoteSaleService;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.domain.md.MdWorkbenchStrandedwire;
import com.cuixing.system.api.domain.md.MdWorkbenchTinning;
import com.cuixing.system.api.domain.sale.SaleOrder;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;

/**
 * 镀锡工单(MesTinningWorkorder)表服务实现类
 *
 * @author makejava
 * @since 2024-11-25 11:04:48
 */
@Service("mesTinningWorkorderService")
public class MesTinningWorkorderServiceImpl implements MesTinningWorkorderService {
    @Autowired
    private MesTinningWorkorderMapper mesTinningWorkorderMapper;

    @Autowired
    private RemoteMdService remoteMdService;

    @Autowired
    private RemoteSaleService remoteSaleService;

    @Autowired
    private RemoteSystemService remoteSystemService;

    @Autowired
    private IMesProductWorkorderService iMesProductWorkorderService;

    @Autowired
    private MesInputLogMapper mesInputLogMapper;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MesTinningWorkorder queryById(BigInteger id) {
        return this.mesTinningWorkorderMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mesTinningWorkorder 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MesTinningWorkorder> selectMesTinningWorkorderList(MesTinningWorkorder mesTinningWorkorder) {
        return this.mesTinningWorkorderMapper.selectMesTinningWorkorderList(mesTinningWorkorder);
    }

    /**
     * 新增数据
     *
     * @param mesTinningWorkorder 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
    public int insert(MesTinningWorkorder mesTinningWorkorder) throws ParseException {
        if (null == mesTinningWorkorder.getCreateTime()){
            mesTinningWorkorder.setCreateTime(DateUtils.getNowDate());
        }else{
            mesTinningWorkorder.setCreateTime(mesTinningWorkorder.getCreateTime());
        }
        mesTinningWorkorder.setCreateBy(SecurityUtils.getUsername());
        boolean result = checkScope(mesTinningWorkorder.getEquipmentId(), mesTinningWorkorder.getSpecification());
        if (!result){
            throw new ProductException("机台生产范围与规格不匹配！！！");
        }
        //镀锡工单号
        Date createTime = mesTinningWorkorder.getCreateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(createTime);
        String autoCode = CodeGenerator.generateCode(formattedDate,"DX");
        mesTinningWorkorder.setTinningCode(autoCode);

        MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesTinningWorkorder.getWorkorderId());
        //计划重量
        Double weight = mesProductWorkorder.getWeight();
        //已排产重量
        Double eWeight = 0.0;
        if (mesProductWorkorder.getTinningSchedulingWeight() != null){
            eWeight=mesProductWorkorder.getTinningSchedulingWeight();
        }
        //剩余重量
        Double rWeight = weight - eWeight;
        if (mesTinningWorkorder.getWeight() > rWeight){
            throw new ProductException("镀锡排产重量超过剩余计划量！！！");
        }else{
            mesProductWorkorder.setTinningSchedulingWeight(eWeight + mesTinningWorkorder.getWeight());
        }

        String status = mesProductWorkorder.getStatus();
        if (status.equals("0")){
            mesProductWorkorder.setStatus("1");
        }
        iMesProductWorkorderService.update(mesProductWorkorder);
        //修改销售订单状态为“已排产”
        if (mesProductWorkorder.getOrderId() != null){
            SaleOrder saleOrder = remoteSaleService.getOrder(mesProductWorkorder.getOrderId(), SecurityConstants.INNER).getData();
            if (saleOrder.getStatus().equals("1")){
                saleOrder.setStatus("2");
                R<AjaxResult> ajaxResultR = remoteSaleService.updateOrder(saleOrder, SecurityConstants.INNER);
                if (ajaxResultR.getCode() != 200){
                    throw new RuntimeException("主数据服务调用失败{"+ajaxResultR.getMsg()+"}");
                }

            }
        }
        return mesTinningWorkorderMapper.insert(mesTinningWorkorder);
    }

    //校验镀锡范围
    private boolean checkScope(BigInteger workbenchId, String specification){
        MdWorkbenchTinning mdWorkbenchTinning = remoteMdService.getWorkbenchTinningById(workbenchId, SecurityConstants.INNER).getData();
        //生产下限
        double productionLowerLimit = mdWorkbenchTinning.getProductionLowerLimit();
        //生产上限
        double productionUpperLimit = mdWorkbenchTinning.getProductionUpperLimit();

        // 提取斜杠后面的数据
        double specificationDouble = CheckScopeUtil.extractFirstValue(specification);

        return !(specificationDouble > productionUpperLimit) && !(specificationDouble < productionLowerLimit);
    }

    /**
     * 退火插单
     */
    @Override
    @Transactional
    public int insertWorkorder(MesTinningWorkorder mesTinningWorkorder) {
        //插单检验
        if (mesTinningWorkorder.getInsertId() == null){
            throw new ProductException("插单工单为空！");
        }
        MesTinningWorkorder tinningWorkorder = mesTinningWorkorderMapper.queryById(mesTinningWorkorder.getId());
        MesInputLog mesInputLog = mesInputLogMapper.queryByWorkstationIdAndAxleNumber(tinningWorkorder.getEquipmentId(), tinningWorkorder.getAxleNumber());
        if (mesInputLog == null){
            throw new ProductException("投料记录为空");
        }else {
            if (!Objects.equals(mesInputLog.getTaskId(), tinningWorkorder.getId())){
                throw new ProductException("该工单暂无投料，插单失败！");
            }
        }

        MesTinningWorkorder insertWorkorders= mesTinningWorkorderMapper.queryById(mesTinningWorkorder.getInsertId());
        if (!Objects.equals(tinningWorkorder.getEquipmentId(), insertWorkorders.getEquipmentId()) ||
                !Objects.equals(tinningWorkorder.getAxleNumber(), insertWorkorders.getAxleNumber()) ||
                !Objects.equals(tinningWorkorder.getSpecification(), insertWorkorders.getSpecification())
        ) {
            throw new ProductException("工单机台、轴号或规格不一致，不能进行插单");
        }
        //修改插入工单的插单状态等
        insertWorkorders.setInsertStatus("1");
        insertWorkorders.setInsertId(tinningWorkorder.getId());
        insertWorkorders.setInsertWorkorder(tinningWorkorder.getTinningCode());
        mesTinningWorkorderMapper.update(insertWorkorders);
        //修改生产工单
        tinningWorkorder.setInsertId(mesTinningWorkorder.getInsertId());
        tinningWorkorder.setInsertWorkorder(insertWorkorders.getTinningCode());
        tinningWorkorder.setInsertModel(insertWorkorders.getModel());
        tinningWorkorder.setInsertStatus("2");

        //修改投料记录
        mesInputLog.setTaskId(insertWorkorders.getId());
        mesInputLog.setTaskSn(insertWorkorders.getTinningCode());
        mesInputLog.setWorkorderId(insertWorkorders.getWorkorderId());
        mesInputLog.setWorkorderSn(insertWorkorders.getWorkorderCode());
        MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(insertWorkorders.getWorkorderId());
        mesInputLog.setOrderId(mesProductWorkorder.getOrderId());
        mesInputLog.setOrderSn(mesProductWorkorder.getOrderCode());
        mesInputLog.setOrderType(mesProductWorkorder.getOrderType());
        mesInputLog.setClientName(mesProductWorkorder.getCustomerName());
        mesInputLog.setProductId(insertWorkorders.getProductId());
        mesInputLog.setProductModel(insertWorkorders.getModel());
        mesInputLogMapper.update(mesInputLog);

        return mesTinningWorkorderMapper.update(tinningWorkorder);
    }

    /**
     * 插单完成
     */
    @Override
    @Transactional
    public int cancelInsertWorkorder(BigInteger id) {
        MesTinningWorkorder mesTinningWorkorder = mesTinningWorkorderMapper.queryById(id);

        //修改插单工单状态为正常
        if (mesTinningWorkorder.getInsertId() != null){
            MesTinningWorkorder insertWorkorder = mesTinningWorkorderMapper.queryById(mesTinningWorkorder.getInsertId());
            insertWorkorder.setInsertStatus("0");
            insertWorkorder.setStatus("2");
            insertWorkorder.setInsertId(null);
            insertWorkorder.setInsertModel(null);
            insertWorkorder.setInsertWorkorder(null);
            mesTinningWorkorderMapper.update(insertWorkorder);
        }else{
            throw new ProductException("操作失败，插单完成异常!!!");
        }

        //修改生产工单
        mesTinningWorkorder.setInsertId(null);
        mesTinningWorkorder.setInsertWorkorder(null);
        mesTinningWorkorder.setInsertModel(null);
        mesTinningWorkorder.setInsertStatus("0");

        //更改投料记录
        updateMesInputLog(mesTinningWorkorder);

        //插单工单的插单状态变更为插单中
        return mesTinningWorkorderMapper.update(mesTinningWorkorder);
    }

    /**
     * 插单取消
     * @param id
     * @return
     */
    @Override
    public int cancelWorkorder(BigInteger id) {
        MesTinningWorkorder mesTinningWorkorder = mesTinningWorkorderMapper.queryById(id);

        //修改插单工单状态为正常
        if (mesTinningWorkorder.getInsertId() != null){
            MesTinningWorkorder insertWorkorder = mesTinningWorkorderMapper.queryById(mesTinningWorkorder.getInsertId());
            if (insertWorkorder.getStatus().equals("0")){
                insertWorkorder.setInsertStatus("0");
                insertWorkorder.setInsertId(null);
                insertWorkorder.setInsertWorkorder(null);
                mesTinningWorkorderMapper.update(insertWorkorder);
            }else{
                throw new ProductException("该插单工单已开始生产，不能取消!");
            }
        }else{
            throw new ProductException("操作失败，插单工单异常!!!");
        }
        //修改生产工单
        mesTinningWorkorder.setInsertId(null);
        mesTinningWorkorder.setInsertWorkorder(null);
        mesTinningWorkorder.setInsertModel(null);
        mesTinningWorkorder.setInsertStatus("0");

        //更改投料记录
        updateMesInputLog(mesTinningWorkorder);

        return mesTinningWorkorderMapper.update(mesTinningWorkorder);
    }

    //投料记录变更
    private int updateMesInputLog(MesTinningWorkorder mesTinningWorkorder){
        //获取投料信息
        MesInputLog mesInputLog = mesInputLogMapper.queryByWorkstationIdAndAxleNumber(mesTinningWorkorder.getEquipmentId(), mesTinningWorkorder.getAxleNumber());
        if (mesInputLog == null){
            throw new ProductException("投料记录为空");
        }
        //修改投料记录
        mesInputLog.setTaskId(mesTinningWorkorder.getId());
        mesInputLog.setTaskSn(mesTinningWorkorder.getTinningCode());
        mesInputLog.setWorkorderId(mesTinningWorkorder.getWorkorderId());
        mesInputLog.setWorkorderSn(mesTinningWorkorder.getWorkorderCode());
        MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesTinningWorkorder.getWorkorderId());
        mesInputLog.setOrderId(mesProductWorkorder.getOrderId());
        mesInputLog.setOrderSn(mesProductWorkorder.getOrderCode());
        mesInputLog.setOrderType(mesProductWorkorder.getOrderType());
        mesInputLog.setClientName(mesProductWorkorder.getCustomerName());
        mesInputLog.setProductId(mesTinningWorkorder.getProductId());
        mesInputLog.setProductModel(mesTinningWorkorder.getModel());
        return mesInputLogMapper.update(mesInputLog);
    }

    /**
     * 修改数据
     *
     * @param mesTinningWorkorder 实例对象
     * @return 实例对象
     */
    @Override
    public MesTinningWorkorder update(MesTinningWorkorder mesTinningWorkorder) {
        mesTinningWorkorder.setUpdateTime(DateUtils.getNowDate());
        mesTinningWorkorder.setUpdateBy(SecurityUtils.getUsername());
        this.mesTinningWorkorderMapper.update(mesTinningWorkorder);
        return this.queryById(mesTinningWorkorder.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(BigInteger id) {
        return this.mesTinningWorkorderMapper.deleteById(id) > 0;
    }

    @Override
    public int deleteByIds(BigInteger[] ids) {
        return this.mesTinningWorkorderMapper.deleteByIds(ids);
    }

    @Override
    public int updateSortById(BigInteger id) {
        return mesTinningWorkorderMapper.updateSortById(id);
    }

    /**
     * * 通过id变更状态
     * @param id
     * @param status
     * @return
     */
    @Override
    @GlobalTransactional
    public int updateStatusById(BigInteger id, String status) {
        if (!status.equals("0") && !status.equals("1") && !status.equals("2") && !status.equals("3")) throw new ProductException("生产状态值错误!!!");
        //漆包工单完工
        if (status.equals("2")) {
            MesTinningWorkorder mesTinningWorkorder = mesTinningWorkorderMapper.queryById(id);
            //修改生产工单状态为生产完成
            MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesTinningWorkorder.getWorkorderId());
            mesProductWorkorder.setStatus("3");
            iMesProductWorkorderService.update(mesProductWorkorder);

            //销售订单状态变更
            SaleOrder saleOrder = remoteSaleService.getOrder(mesProductWorkorder.getOrderId(), SecurityConstants.INNER).getData();
            saleOrder.setStatus("4");
            R<AjaxResult> ajaxResultR = remoteSaleService.updateOrder(saleOrder, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new RuntimeException("主数据服务调用失败{"+ajaxResultR.getMsg()+"}");
            }
        }
        return mesTinningWorkorderMapper.updateStatusById(id, status);
    }

    /**
     * 退火工单作废
     * @param id
     * @return
     */
    @Override
    public int cancelById(BigInteger id) {
        //通过id查询生产工单
        return mesTinningWorkorderMapper.cancelById(id);
    }

    /**
     *  通过机台id查询该机台所有已排产、生产中列表
     * @param equipmentId
     * @return
     */
    @Override
    public List<MesTinningWorkorder> getListByEquipmentId(BigInteger equipmentId, String axleNumber) {
        String[] axleNumbers = axleNumber.split(",");
        List<MesTinningWorkorder> list = new ArrayList<>();
        for (String number : axleNumbers) {
            List<MesTinningWorkorder> listByEquipmentId = mesTinningWorkorderMapper.getListByEquipmentId(equipmentId, number);
            list.addAll(listByEquipmentId);
        }
        return list;
    }

    @Override
    public MesTinningWorkorder queryByTinningWorkorderCode(String workorderCode) {
        return mesTinningWorkorderMapper.queryByTinningWorkorderCode(workorderCode);
    }

    @Override
    public int batchUpdate(ArrayList<MesTinningWorkorder> mesTinningWorkorders) {
        return this.mesTinningWorkorderMapper.batchUpdate(mesTinningWorkorders);
    }
}
