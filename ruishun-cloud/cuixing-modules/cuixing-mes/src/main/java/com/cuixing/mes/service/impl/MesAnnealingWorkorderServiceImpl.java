package com.cuixing.mes.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.mes.ProductException;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mes.entity.MesAnnealingWorkorder;
import com.cuixing.mes.entity.MesEnamellingWorkorder;
import com.cuixing.mes.entity.MesInputLog;
import com.cuixing.mes.entity.MesProductWorkorder;
import com.cuixing.mes.mapper.MesAnnealingWorkorderMapper;
import com.cuixing.mes.mapper.MesInputLogMapper;
import com.cuixing.mes.service.IMesProductWorkorderService;
import com.cuixing.mes.service.MesAnnealingWorkorderService;
import com.cuixing.mes.utils.CheckScopeUtil;
import com.cuixing.mes.utils.CodeGenerator;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.RemoteSaleService;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.domain.md.MdWorkbenchAnnealing;
import com.cuixing.system.api.domain.md.MdWorkbenchEnamelling;
import com.cuixing.system.api.domain.sale.SaleOrder;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;

/**
 * 退火工单(MesAnnealingWorkorder)表服务实现类
 *
 * @author makejava
 * @since 2024-11-25 10:30:34
 */
@Service("mesAnnealingWorkorderService")
public class MesAnnealingWorkorderServiceImpl implements MesAnnealingWorkorderService {
    @Resource
    private MesAnnealingWorkorderMapper mesAnnealingWorkorderMapper;

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
    public MesAnnealingWorkorder queryById(BigInteger id) {
        return this.mesAnnealingWorkorderMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mesAnnealingWorkorder 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MesAnnealingWorkorder> selectMesAnnealingWorkorderList(MesAnnealingWorkorder mesAnnealingWorkorder) {
        return this.mesAnnealingWorkorderMapper.selectMesAnnealingWorkorderList(mesAnnealingWorkorder);
    }

    /**
     * 新增数据
     *
     * @param mesAnnealingWorkorder 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
    public int insert(MesAnnealingWorkorder mesAnnealingWorkorder) throws ParseException {
        if (null == mesAnnealingWorkorder.getCreateTime()){
            mesAnnealingWorkorder.setCreateTime(DateUtils.getNowDate());
        }else{
            mesAnnealingWorkorder.setCreateTime(mesAnnealingWorkorder.getCreateTime());
        }
        mesAnnealingWorkorder.setCreateBy(SecurityUtils.getUsername());
        boolean result = checkScope(mesAnnealingWorkorder.getEquipmentId(), mesAnnealingWorkorder.getSpecification());
        if (!result){
            throw new ProductException("机台生产范围与规格不匹配！！！");
        }
        //退火工单号
        Date createTime = mesAnnealingWorkorder.getCreateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(createTime);
        String autoCode = CodeGenerator.generateCode(formattedDate,"TH");
        mesAnnealingWorkorder.setAnnealingCode(autoCode);

        MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesAnnealingWorkorder.getWorkorderId());
        //计划重量
        Double weight = mesProductWorkorder.getWeight();
        //已排产重量
        Double eWeight = 0.0;
        if (mesProductWorkorder.getAnnealingSchedulingWeight()!=null){
            eWeight=mesProductWorkorder.getAnnealingSchedulingWeight();
        }
        //剩余重量
        Double rWeight = weight - eWeight;
        if (mesAnnealingWorkorder.getWeight() > rWeight){
            throw new ProductException("退火排产重量超过剩余计划量！！！");
        }else{
            mesProductWorkorder.setAnnealingSchedulingWeight(eWeight + mesAnnealingWorkorder.getWeight());
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
        return mesAnnealingWorkorderMapper.insert(mesAnnealingWorkorder);
    }

    //校验退火范围
    private boolean checkScope(BigInteger workbenchId, String specification){
        MdWorkbenchAnnealing mdWorkbenchAnnealing = remoteMdService.getWorkbenchAnnealingById(workbenchId, SecurityConstants.INNER).getData();
        //生产下限
        double productionLowerLimit = mdWorkbenchAnnealing.getProductionLowerLimit();
        //生产上限
        double productionUpperLimit = mdWorkbenchAnnealing.getProductionUpperLimit();

        double specificationDouble = CheckScopeUtil.extractFirstValue(specification);

        return !(specificationDouble > productionUpperLimit) && !(specificationDouble < productionLowerLimit);
    }

    /**
     * 退火插单
     */
    @Override
    @Transactional
    public int insertWorkorder(MesAnnealingWorkorder mesAnnealingWorkorder) {
        //插单检验
        if (mesAnnealingWorkorder.getInsertId() == null){
            throw new ProductException("插单工单为空！");
        }
        MesAnnealingWorkorder annealingWorkorder = mesAnnealingWorkorderMapper.queryById(mesAnnealingWorkorder.getId());
        MesInputLog mesInputLog = mesInputLogMapper.queryByWorkstationIdAndAxleNumber(annealingWorkorder.getEquipmentId(), annealingWorkorder.getAxleNumber());
        if (mesInputLog == null){
            throw new ProductException("投料记录为空");
        }else {
            if (!Objects.equals(mesInputLog.getTaskId(), annealingWorkorder.getId())){
                throw new ProductException("该工单暂无投料，插单失败！");
            }
        }

        MesAnnealingWorkorder insertWorkorders= mesAnnealingWorkorderMapper.queryById(mesAnnealingWorkorder.getInsertId());
        if (!Objects.equals(annealingWorkorder.getEquipmentId(), insertWorkorders.getEquipmentId()) ||
                !Objects.equals(annealingWorkorder.getAxleNumber(), insertWorkorders.getAxleNumber()) ||
                !Objects.equals(annealingWorkorder.getSpecification(), insertWorkorders.getSpecification())
        ) {
            throw new ProductException("工单机台、轴号或规格不一致，不能进行插单");
        }
        //修改插入工单的插单状态等
        insertWorkorders.setInsertStatus("1");
        insertWorkorders.setInsertId(annealingWorkorder.getId());
        insertWorkorders.setInsertWorkorder(annealingWorkorder.getAnnealingCode());
        mesAnnealingWorkorderMapper.update(insertWorkorders);
        //修改生产工单
        annealingWorkorder.setInsertId(mesAnnealingWorkorder.getInsertId());
        annealingWorkorder.setInsertWorkorder(insertWorkorders.getAnnealingCode());
        annealingWorkorder.setInsertModel(insertWorkorders.getModel());
        annealingWorkorder.setInsertStatus("2");

        //修改投料记录
        mesInputLog.setTaskId(insertWorkorders.getId());
        mesInputLog.setTaskSn(insertWorkorders.getAnnealingCode());
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

        return mesAnnealingWorkorderMapper.update(annealingWorkorder);
    }

    /**
     * 插单完成
     */
    @Override
    @Transactional
    public int cancelInsertWorkorder(BigInteger id) {
        MesAnnealingWorkorder mesAnnealingWorkorder = mesAnnealingWorkorderMapper.queryById(id);

        //修改插单工单状态为正常
        if (mesAnnealingWorkorder.getInsertId() != null){
            MesAnnealingWorkorder insertWorkorder = mesAnnealingWorkorderMapper.queryById(mesAnnealingWorkorder.getInsertId());
            insertWorkorder.setInsertStatus("0");
            insertWorkorder.setStatus("2");
            insertWorkorder.setInsertId(null);
            insertWorkorder.setInsertModel(null);
            insertWorkorder.setInsertWorkorder(null);
            mesAnnealingWorkorderMapper.update(insertWorkorder);
        }else{
            throw new ProductException("操作失败，插单完成异常!!!");
        }

        //修改生产工单
        mesAnnealingWorkorder.setInsertId(null);
        mesAnnealingWorkorder.setInsertWorkorder(null);
        mesAnnealingWorkorder.setInsertModel(null);
        mesAnnealingWorkorder.setInsertStatus("0");

        //更改投料记录
        updateMesInputLog(mesAnnealingWorkorder);

        //插单工单的插单状态变更为插单中
        return mesAnnealingWorkorderMapper.update(mesAnnealingWorkorder);
    }

    /**
     * 插单取消
     * @param id
     * @return
     */
    @Override
    public int cancelWorkorder(BigInteger id) {
        MesAnnealingWorkorder mesAnnealingWorkorder = mesAnnealingWorkorderMapper.queryById(id);

        //修改插单工单状态为正常
        if (mesAnnealingWorkorder.getInsertId() != null){
            MesAnnealingWorkorder insertWorkorder = mesAnnealingWorkorderMapper.queryById(mesAnnealingWorkorder.getInsertId());
            if (insertWorkorder.getStatus().equals("0")){
                insertWorkorder.setInsertStatus("0");
                insertWorkorder.setInsertId(null);
                insertWorkorder.setInsertWorkorder(null);
                mesAnnealingWorkorderMapper.update(insertWorkorder);
            }else{
                throw new ProductException("该插单工单已开始生产，不能取消!");
            }
        }else{
            throw new ProductException("操作失败，插单工单异常!!!");
        }
        //修改生产工单
        mesAnnealingWorkorder.setInsertId(null);
        mesAnnealingWorkorder.setInsertWorkorder(null);
        mesAnnealingWorkorder.setInsertModel(null);
        mesAnnealingWorkorder.setInsertStatus("0");

        //更改投料记录
        updateMesInputLog(mesAnnealingWorkorder);

        return mesAnnealingWorkorderMapper.update(mesAnnealingWorkorder);
    }

    @Override
    public MesAnnealingWorkorder queryByMesAnnealingWorkorderCode(String workorderCode) {
        return mesAnnealingWorkorderMapper.queryByMesAnnealingWorkorderCode(workorderCode);
    }

    @Override
    public int batchUpdate(ArrayList<MesAnnealingWorkorder> mesAnnealingWorkorders) {
        return this.mesAnnealingWorkorderMapper.batchUpdate(mesAnnealingWorkorders);
    }

    //投料记录变更
    private int updateMesInputLog(MesAnnealingWorkorder mesAnnealingWorkorder){
        //获取投料信息
        MesInputLog mesInputLog = mesInputLogMapper.queryByWorkstationIdAndAxleNumber(mesAnnealingWorkorder.getEquipmentId(), mesAnnealingWorkorder.getAxleNumber());
        if (mesInputLog == null){
            throw new ProductException("投料记录为空");
        }
        //修改投料记录
        mesInputLog.setTaskId(mesAnnealingWorkorder.getId());
        mesInputLog.setTaskSn(mesAnnealingWorkorder.getAnnealingCode());
        mesInputLog.setWorkorderId(mesAnnealingWorkorder.getWorkorderId());
        mesInputLog.setWorkorderSn(mesAnnealingWorkorder.getWorkorderCode());
        MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesAnnealingWorkorder.getWorkorderId());
        mesInputLog.setOrderId(mesProductWorkorder.getOrderId());
        mesInputLog.setOrderSn(mesProductWorkorder.getOrderCode());
        mesInputLog.setOrderType(mesProductWorkorder.getOrderType());
        mesInputLog.setClientName(mesProductWorkorder.getCustomerName());
        mesInputLog.setProductId(mesAnnealingWorkorder.getProductId());
        mesInputLog.setProductModel(mesAnnealingWorkorder.getModel());
        return mesInputLogMapper.update(mesInputLog);
    }
    /**
     * 修改数据
     *
     * @param mesAnnealingWorkorder 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MesAnnealingWorkorder mesAnnealingWorkorder) {
        mesAnnealingWorkorder.setUpdateTime(DateUtils.getNowDate());
        mesAnnealingWorkorder.setUpdateBy(SecurityUtils.getUsername());
        return mesAnnealingWorkorderMapper.update(mesAnnealingWorkorder);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(BigInteger id) {
        return this.mesAnnealingWorkorderMapper.deleteById(id) > 0;
    }

    @Override
    public int deleteByIds(BigInteger[] ids) {
        return this.mesAnnealingWorkorderMapper.deleteByIds(ids);
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
            MesAnnealingWorkorder mesAnnealingWorkorder = mesAnnealingWorkorderMapper.queryById(id);
            //修改生产工单状态为生产完成
            MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesAnnealingWorkorder.getWorkorderId());
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
        return mesAnnealingWorkorderMapper.updateStatusById(id, status);
    }

    /**
     * 退火工单作废
     * @param id
     * @return
     */
    @Override
    public int cancelById(BigInteger id) {
        //通过id查询生产工单
        return mesAnnealingWorkorderMapper.cancelById(id);
    }

    @Override
    public int updateSortById(BigInteger id) {
        return mesAnnealingWorkorderMapper.updateSortById(id);
    }

    /**
     *  通过机台id查询该机台所有已排产、生产中列表
     * @param equipmentId
     * @return
     */
    @Override
    public List<MesAnnealingWorkorder> getListByEquipmentId(BigInteger equipmentId, String axleNumber) {
        String[] axleNumbers = axleNumber.split(",");
        List<MesAnnealingWorkorder> list = new ArrayList<>();
        for (String number : axleNumbers) {
            List<MesAnnealingWorkorder> listByEquipmentId = mesAnnealingWorkorderMapper.getListByEquipmentId(equipmentId, number);
            list.addAll(listByEquipmentId);
        }
        return list;
    }
}
