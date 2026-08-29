package com.cuixing.mes.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.mes.ProductException;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mes.entity.*;
import com.cuixing.mes.mapper.MesDoublewireWorkorderMapper;
import com.cuixing.mes.mapper.MesInputLogMapper;
import com.cuixing.mes.service.IMesProductWorkorderService;
import com.cuixing.mes.service.MesDoublewireWorkorderService;
import com.cuixing.mes.utils.CheckScopeUtil;
import com.cuixing.mes.utils.CodeGenerator;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.RemoteSaleService;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.domain.md.MdWorkbenchAnnealing;
import com.cuixing.system.api.domain.md.MdWorkbenchDoublewire;
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
 * 并丝工单(MesDoublewireWorkorder)表服务实现类
 *
 * @author makejava
 * @since 2024-11-25 11:04:48
 */
@Service("mesDoublewireWorkorderService")
public class MesDoublewireWorkorderServiceImpl implements MesDoublewireWorkorderService {
    @Autowired
    private MesDoublewireWorkorderMapper mesDoublewireWorkorderMapper;

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
    public MesDoublewireWorkorder queryById(BigInteger id) {
        return this.mesDoublewireWorkorderMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mesDoublewireWorkorder 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MesDoublewireWorkorder> selectMesDoublewireWorkorderList(MesDoublewireWorkorder mesDoublewireWorkorder) {
        return this.mesDoublewireWorkorderMapper.selectMesDoublewireWorkorderList(mesDoublewireWorkorder);
    }

    /**
     * 新增数据
     *
     * @param mesDoublewireWorkorder 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
    public int insert(MesDoublewireWorkorder mesDoublewireWorkorder) throws ParseException {
        if (mesDoublewireWorkorder.getCreateTime()== null){
            mesDoublewireWorkorder.setCreateTime(DateUtils.getNowDate());
        }else{
            mesDoublewireWorkorder.setCreateTime(mesDoublewireWorkorder.getCreateTime());
        }
        mesDoublewireWorkorder.setCreateBy(SecurityUtils.getUsername());
        boolean result = checkScope(mesDoublewireWorkorder.getEquipmentId(), mesDoublewireWorkorder.getSpecification());
        if (!result){
            throw new ProductException("机台生产范围与规格不匹配！！！");
        }
        //并丝工单号
        Date createTime = mesDoublewireWorkorder.getCreateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(createTime);
        String autoCode = CodeGenerator.generateCode(formattedDate,"BS");
        mesDoublewireWorkorder.setDoublewireCode(autoCode);

        MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesDoublewireWorkorder.getWorkorderId());
        //计划重量
        Double weight = mesProductWorkorder.getWeight();
        //已排产重量
        Double eWeight = 0.0;
        if (mesProductWorkorder.getDoublewireSchedulingWeight() != null){
            eWeight=mesProductWorkorder.getDoublewireSchedulingWeight();
        }
        //剩余重量
        Double rWeight = weight - eWeight;
        if (mesDoublewireWorkorder.getWeight() > rWeight){
            throw new ProductException("并丝排产重量超过剩余计划量！！！");
        }else{
            mesProductWorkorder.setDoublewireSchedulingWeight(eWeight + mesDoublewireWorkorder.getWeight());
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
        return mesDoublewireWorkorderMapper.insert(mesDoublewireWorkorder);
    }

    //校验并丝范围
    private boolean checkScope(BigInteger workbenchId, String specification){
        MdWorkbenchDoublewire mdWorkbenchDoublewire = remoteMdService.getWorkbenchDoublewireById(workbenchId, SecurityConstants.INNER).getData();
        //生产下限
        double productionLowerLimit = mdWorkbenchDoublewire.getProductionLowerLimit();
        //生产上限
        double productionUpperLimit = mdWorkbenchDoublewire.getProductionUpperLimit();

        double specificationDouble = CheckScopeUtil.extractFirstValue(specification);

        return !(specificationDouble > productionUpperLimit) && !(specificationDouble < productionLowerLimit);
    }

    /**
     * 退火插单
     */
    @Override
    @Transactional
    public int insertWorkorder(MesDoublewireWorkorder mesDoublewireWorkorder) {
        //插单检验
        if (mesDoublewireWorkorder.getInsertId() == null){
            throw new ProductException("插单工单为空！");
        }
        MesDoublewireWorkorder doublewireWorkorder = mesDoublewireWorkorderMapper.queryById(mesDoublewireWorkorder.getId());
        MesInputLog mesInputLog = mesInputLogMapper.queryByWorkstationIdAndAxleNumber(doublewireWorkorder.getEquipmentId(), doublewireWorkorder.getAxleNumber());
        if (mesInputLog == null){
            throw new ProductException("投料记录为空");
        }else {
            if (!Objects.equals(mesInputLog.getTaskId(), doublewireWorkorder.getId())){
                throw new ProductException("该工单暂无投料，插单失败！");
            }
        }

        MesDoublewireWorkorder insertWorkorders= mesDoublewireWorkorderMapper.queryById(mesDoublewireWorkorder.getInsertId());
        if (!Objects.equals(doublewireWorkorder.getEquipmentId(), insertWorkorders.getEquipmentId()) ||
                !Objects.equals(doublewireWorkorder.getAxleNumber(), insertWorkorders.getAxleNumber()) ||
                !Objects.equals(doublewireWorkorder.getSpecification(), insertWorkorders.getSpecification())
        ) {
            throw new ProductException("工单机台、轴号或规格不一致，不能进行插单");
        }
        //修改插入工单的插单状态等
        insertWorkorders.setInsertStatus("1");
        insertWorkorders.setInsertId(doublewireWorkorder.getId());
        insertWorkorders.setInsertWorkorder(doublewireWorkorder.getDoublewireCode());
        mesDoublewireWorkorderMapper.update(insertWorkorders);
        //修改生产工单
        doublewireWorkorder.setInsertId(mesDoublewireWorkorder.getInsertId());
        doublewireWorkorder.setInsertWorkorder(insertWorkorders.getDoublewireCode());
        doublewireWorkorder.setInsertModel(insertWorkorders.getModel());
        doublewireWorkorder.setInsertStatus("2");

        //修改投料记录
        mesInputLog.setTaskId(insertWorkorders.getId());
        mesInputLog.setTaskSn(insertWorkorders.getDoublewireCode());
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

        return mesDoublewireWorkorderMapper.update(doublewireWorkorder);
    }

    /**
     * 插单完成
     */
    @Override
    @Transactional
    public int cancelInsertWorkorder(BigInteger id) {
        MesDoublewireWorkorder mesDoublewireWorkorder = mesDoublewireWorkorderMapper.queryById(id);

        //修改插单工单状态为正常
        if (mesDoublewireWorkorder.getInsertId() != null){
            MesDoublewireWorkorder insertWorkorder = mesDoublewireWorkorderMapper.queryById(mesDoublewireWorkorder.getInsertId());
            insertWorkorder.setInsertStatus("0");
            insertWorkorder.setStatus("2");
            insertWorkorder.setInsertId(null);
            insertWorkorder.setInsertModel(null);
            insertWorkorder.setInsertWorkorder(null);
            mesDoublewireWorkorderMapper.update(insertWorkorder);
        }else{
            throw new ProductException("操作失败，插单完成异常!!!");
        }

        //修改生产工单
        mesDoublewireWorkorder.setInsertId(null);
        mesDoublewireWorkorder.setInsertWorkorder(null);
        mesDoublewireWorkorder.setInsertModel(null);
        mesDoublewireWorkorder.setInsertStatus("0");

        //更改投料记录
        updateMesInputLog(mesDoublewireWorkorder);

        //插单工单的插单状态变更为插单中
        return mesDoublewireWorkorderMapper.update(mesDoublewireWorkorder);
    }

    /**
     * 插单取消
     * @param id
     * @return
     */
    @Override
    public int cancelWorkorder(BigInteger id) {
        MesDoublewireWorkorder mesDoublewireWorkorder = mesDoublewireWorkorderMapper.queryById(id);

        //修改插单工单状态为正常
        if (mesDoublewireWorkorder.getInsertId() != null){
            MesDoublewireWorkorder insertWorkorder = mesDoublewireWorkorderMapper.queryById(mesDoublewireWorkorder.getInsertId());
            if (insertWorkorder.getStatus().equals("0")){
                insertWorkorder.setInsertStatus("0");
                insertWorkorder.setInsertId(null);
                insertWorkorder.setInsertWorkorder(null);
                mesDoublewireWorkorderMapper.update(insertWorkorder);
            }else{
                throw new ProductException("该插单工单已开始生产，不能取消!");
            }
        }else{
            throw new ProductException("操作失败，插单工单异常!!!");
        }
        //修改生产工单
        mesDoublewireWorkorder.setInsertId(null);
        mesDoublewireWorkorder.setInsertWorkorder(null);
        mesDoublewireWorkorder.setInsertModel(null);
        mesDoublewireWorkorder.setInsertStatus("0");

        //更改投料记录
        updateMesInputLog(mesDoublewireWorkorder);

        return mesDoublewireWorkorderMapper.update(mesDoublewireWorkorder);
    }

    //投料记录变更
    private int updateMesInputLog(MesDoublewireWorkorder mesDoublewireWorkorder){
        //获取投料信息
        MesInputLog mesInputLog = mesInputLogMapper.queryByWorkstationIdAndAxleNumber(mesDoublewireWorkorder.getEquipmentId(), mesDoublewireWorkorder.getAxleNumber());
        if (mesInputLog == null){
            throw new ProductException("投料记录为空");
        }
        //修改投料记录
        mesInputLog.setTaskId(mesDoublewireWorkorder.getId());
        mesInputLog.setTaskSn(mesDoublewireWorkorder.getDoublewireCode());
        mesInputLog.setWorkorderId(mesDoublewireWorkorder.getWorkorderId());
        mesInputLog.setWorkorderSn(mesDoublewireWorkorder.getWorkorderCode());
        MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesDoublewireWorkorder.getWorkorderId());
        mesInputLog.setOrderId(mesProductWorkorder.getOrderId());
        mesInputLog.setOrderSn(mesProductWorkorder.getOrderCode());
        mesInputLog.setOrderType(mesProductWorkorder.getOrderType());
        mesInputLog.setClientName(mesProductWorkorder.getCustomerName());
        mesInputLog.setProductId(mesDoublewireWorkorder.getProductId());
        mesInputLog.setProductModel(mesDoublewireWorkorder.getModel());
        return mesInputLogMapper.update(mesInputLog);
    }

    /**
     * 修改数据
     *
     * @param mesDoublewireWorkorder 实例对象
     * @return 实例对象
     */
    @Override
    public MesDoublewireWorkorder update(MesDoublewireWorkorder mesDoublewireWorkorder) {
        mesDoublewireWorkorder.setUpdateBy(SecurityUtils.getUsername());
        mesDoublewireWorkorder.setUpdateTime(DateUtils.getNowDate());
        this.mesDoublewireWorkorderMapper.update(mesDoublewireWorkorder);
        return this.queryById(mesDoublewireWorkorder.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(BigInteger id) {
        return this.mesDoublewireWorkorderMapper.deleteById(id) > 0;
    }

    @Override
    public int deleteByIds(BigInteger[] ids) {
        return this.mesDoublewireWorkorderMapper.deleteByIds(ids);
    }

    @Override
    public int updateSortById(BigInteger id) {
        return mesDoublewireWorkorderMapper.updateSortById(id);
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
            MesDoublewireWorkorder mesDoublewireWorkorder = mesDoublewireWorkorderMapper.queryById(id);
            //修改生产工单状态为生产完成
            MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesDoublewireWorkorder.getWorkorderId());
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
        return mesDoublewireWorkorderMapper.updateStatusById(id, status);
    }

    /**
     * 退火工单作废
     * @param id
     * @return
     */
    @Override
    public int cancelById(BigInteger id) {
        //通过id查询生产工单
        return mesDoublewireWorkorderMapper.cancelById(id);
    }

    /**
     *  通过机台id查询该机台所有已排产、生产中列表
     * @param equipmentId
     * @return
     */
    @Override
    public List<MesDoublewireWorkorder> getListByEquipmentId(BigInteger equipmentId, String axleNumber) {
        String[] axleNumbers = axleNumber.split(",");
        List<MesDoublewireWorkorder> list = new ArrayList<>();
        for (String number : axleNumbers) {
            List<MesDoublewireWorkorder> listByEquipmentId = mesDoublewireWorkorderMapper.getListByEquipmentId(equipmentId, number);
            list.addAll(listByEquipmentId);
        }
        return list;
    }
}
