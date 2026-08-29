package com.cuixing.mes.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.mes.ProductException;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mes.entity.*;
import com.cuixing.mes.mapper.MesInputLogMapper;
import com.cuixing.mes.mapper.MesStrandedwireWorkorderMapper;
import com.cuixing.mes.service.IMesProductWorkorderService;
import com.cuixing.mes.service.MesStrandedwireWorkorderService;
import com.cuixing.mes.utils.CheckScopeUtil;
import com.cuixing.mes.utils.CodeGenerator;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.RemoteSaleService;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.domain.md.MdWorkbenchDoublewire;
import com.cuixing.system.api.domain.md.MdWorkbenchStrandedwire;
import com.cuixing.system.api.domain.sale.SaleOrder;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;

/**
 * 绞线工单(MesStrandedwireWorkorder)表服务实现类
 *
 * @author makejava
 * @since 2024-11-25 11:04:48
 */
@Service("mesStrandedwireWorkorderService")
public class MesStrandedwireWorkorderServiceImpl implements MesStrandedwireWorkorderService {
    @Autowired
    private MesStrandedwireWorkorderMapper mesStrandedwireWorkorderMapper;

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
    public MesStrandedwireWorkorder queryById(BigInteger id) {
        return this.mesStrandedwireWorkorderMapper.queryById(id);
    }

    @Override
    public int updateSortById(BigInteger id) {
        return mesStrandedwireWorkorderMapper.updateSortById(id);
    }

    /**
     * 分页查询
     *
     * @param mesStrandedwireWorkorder 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MesStrandedwireWorkorder> selectMesStrandedwireWorkorderList(MesStrandedwireWorkorder mesStrandedwireWorkorder) {
        return this.mesStrandedwireWorkorderMapper.selectMesStrandedwireWorkorderList(mesStrandedwireWorkorder);
    }

    /**
     * 新增数据
     *
     * @param mesStrandedwireWorkorder 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
    public int insert(MesStrandedwireWorkorder mesStrandedwireWorkorder) throws ParseException {
        if (null == mesStrandedwireWorkorder.getCreateTime()){
            mesStrandedwireWorkorder.setCreateTime(DateUtils.getNowDate());
        }else{
            mesStrandedwireWorkorder.setCreateTime(mesStrandedwireWorkorder.getCreateTime());
        }
        mesStrandedwireWorkorder.setCreateBy(SecurityUtils.getUsername());
        boolean result = checkScope(mesStrandedwireWorkorder.getEquipmentId(), mesStrandedwireWorkorder.getSpecification());
        if (!result){
            throw new ProductException("机台生产范围与规格不匹配！！！");
        }
        //绞线工单号
        String autoCode = CodeGenerator.generateCode(mesStrandedwireWorkorder.getCreateTime().toString(),"JX");
        mesStrandedwireWorkorder.setStrandedwireCode(autoCode);

        MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesStrandedwireWorkorder.getWorkorderId());
        //计划重量
        Double weight = mesProductWorkorder.getWeight();
        //已排产重量
        Double eWeight = 0.0;
        if (mesProductWorkorder.getStrandedwireSchedulingWeight()!=null){
            eWeight=mesProductWorkorder.getStrandedwireSchedulingWeight();
        }
        //剩余重量
        Double rWeight = weight - eWeight;
        if (mesStrandedwireWorkorder.getWeight() > rWeight){
            throw new ProductException("绞线排产重量超过剩余计划量！！！");
        }else{
            mesProductWorkorder.setStrandedwireSchedulingWeight(eWeight + mesStrandedwireWorkorder.getWeight());
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
        return mesStrandedwireWorkorderMapper.insert(mesStrandedwireWorkorder);
    }

    //校验绞线范围
    private boolean checkScope(BigInteger workbenchId, String specification){
        MdWorkbenchStrandedwire mdWorkbenchStrandedwire = remoteMdService.getWorkbenchStrandedwireById(workbenchId, SecurityConstants.INNER).getData();
        //生产下限
        double productionLowerLimit = mdWorkbenchStrandedwire.getProductionLowerLimit();
        //生产上限
        double productionUpperLimit = mdWorkbenchStrandedwire.getProductionUpperLimit();

        // 提取斜杠后面的数据
        double specificationDouble = CheckScopeUtil.extractFirstValue(specification);

        return !(specificationDouble > productionUpperLimit) && !(specificationDouble < productionLowerLimit);
    }


    /**
     * 退火插单
     */
    @Override
    @Transactional
    public int insertWorkorder(MesStrandedwireWorkorder mesStrandedwireWorkorder) {
        //插单检验
        if (mesStrandedwireWorkorder.getInsertId() == null){
            throw new ProductException("插单工单为空！");
        }
        MesStrandedwireWorkorder strandedwireWorkorder = mesStrandedwireWorkorderMapper.queryById(mesStrandedwireWorkorder.getId());
        MesInputLog mesInputLog = mesInputLogMapper.queryByWorkstationIdAndAxleNumber(strandedwireWorkorder.getEquipmentId(), strandedwireWorkorder.getAxleNumber());
        if (mesInputLog == null){
            throw new ProductException("投料记录为空");
        }else {
            if (!Objects.equals(mesInputLog.getTaskId(), strandedwireWorkorder.getId())){
                throw new ProductException("该工单暂无投料，插单失败！");
            }
        }

        MesStrandedwireWorkorder insertWorkorders= mesStrandedwireWorkorderMapper.queryById(mesStrandedwireWorkorder.getInsertId());
        if (!Objects.equals(strandedwireWorkorder.getEquipmentId(), insertWorkorders.getEquipmentId()) ||
                !Objects.equals(strandedwireWorkorder.getAxleNumber(), insertWorkorders.getAxleNumber()) ||
                !Objects.equals(strandedwireWorkorder.getSpecification(), insertWorkorders.getSpecification())
        ) {
            throw new ProductException("工单机台、轴号或规格不一致，不能进行插单");
        }
        //修改插入工单的插单状态等
        insertWorkorders.setInsertStatus("1");
        insertWorkorders.setInsertId(strandedwireWorkorder.getId());
        insertWorkorders.setInsertWorkorder(strandedwireWorkorder.getStrandedwireCode());
        mesStrandedwireWorkorderMapper.update(insertWorkorders);
        //修改生产工单
        strandedwireWorkorder.setInsertId(mesStrandedwireWorkorder.getInsertId());
        strandedwireWorkorder.setInsertWorkorder(insertWorkorders.getStrandedwireCode());
        strandedwireWorkorder.setInsertModel(insertWorkorders.getModel());
        strandedwireWorkorder.setInsertStatus("2");

        //修改投料记录
        mesInputLog.setTaskId(insertWorkorders.getId());
        mesInputLog.setTaskSn(insertWorkorders.getStrandedwireCode());
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

        return mesStrandedwireWorkorderMapper.update(strandedwireWorkorder);
    }

    /**
     * 插单完成
     */
    @Override
    @Transactional
    public int cancelInsertWorkorder(BigInteger id) {
        MesStrandedwireWorkorder mesStrandedwireWorkorder = mesStrandedwireWorkorderMapper.queryById(id);

        //修改插单工单状态为正常
        if (mesStrandedwireWorkorder.getInsertId() != null){
            MesStrandedwireWorkorder insertWorkorder = mesStrandedwireWorkorderMapper.queryById(mesStrandedwireWorkorder.getInsertId());
            insertWorkorder.setInsertStatus("0");
            insertWorkorder.setStatus("2");
            insertWorkorder.setInsertId(null);
            insertWorkorder.setInsertModel(null);
            insertWorkorder.setInsertWorkorder(null);
            mesStrandedwireWorkorderMapper.update(insertWorkorder);
        }else{
            throw new ProductException("操作失败，插单完成异常!!!");
        }

        //修改生产工单
        mesStrandedwireWorkorder.setInsertId(null);
        mesStrandedwireWorkorder.setInsertWorkorder(null);
        mesStrandedwireWorkorder.setInsertModel(null);
        mesStrandedwireWorkorder.setInsertStatus("0");

        //更改投料记录
        updateMesInputLog(mesStrandedwireWorkorder);

        //插单工单的插单状态变更为插单中
        return mesStrandedwireWorkorderMapper.update(mesStrandedwireWorkorder);
    }

    /**
     * 插单取消
     * @param id
     * @return
     */
    @Override
    public int cancelWorkorder(BigInteger id) {
        MesStrandedwireWorkorder mesStrandedwireWorkorder = mesStrandedwireWorkorderMapper.queryById(id);

        //修改插单工单状态为正常
        if (mesStrandedwireWorkorder.getInsertId() != null){
            MesStrandedwireWorkorder insertWorkorder = mesStrandedwireWorkorderMapper.queryById(mesStrandedwireWorkorder.getInsertId());
            if (insertWorkorder.getStatus().equals("0")){
                insertWorkorder.setInsertStatus("0");
                insertWorkorder.setInsertId(null);
                insertWorkorder.setInsertWorkorder(null);
                mesStrandedwireWorkorderMapper.update(insertWorkorder);
            }else{
                throw new ProductException("该插单工单已开始生产，不能取消!");
            }
        }else{
            throw new ProductException("操作失败，插单工单异常!!!");
        }
        //修改生产工单
        mesStrandedwireWorkorder.setInsertId(null);
        mesStrandedwireWorkorder.setInsertWorkorder(null);
        mesStrandedwireWorkorder.setInsertModel(null);
        mesStrandedwireWorkorder.setInsertStatus("0");

        //更改投料记录
        updateMesInputLog(mesStrandedwireWorkorder);

        return mesStrandedwireWorkorderMapper.update(mesStrandedwireWorkorder);
    }

    //投料记录变更
    private int updateMesInputLog(MesStrandedwireWorkorder mesStrandedwireWorkorder){
        //获取投料信息
        MesInputLog mesInputLog = mesInputLogMapper.queryByWorkstationIdAndAxleNumber(mesStrandedwireWorkorder.getEquipmentId(), mesStrandedwireWorkorder.getAxleNumber());
        if (mesInputLog == null){
            throw new ProductException("投料记录为空");
        }
        //修改投料记录
        mesInputLog.setTaskId(mesStrandedwireWorkorder.getId());
        mesInputLog.setTaskSn(mesStrandedwireWorkorder.getStrandedwireCode());
        mesInputLog.setWorkorderId(mesStrandedwireWorkorder.getWorkorderId());
        mesInputLog.setWorkorderSn(mesStrandedwireWorkorder.getWorkorderCode());
        MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesStrandedwireWorkorder.getWorkorderId());
        mesInputLog.setOrderId(mesProductWorkorder.getOrderId());
        mesInputLog.setOrderSn(mesProductWorkorder.getOrderCode());
        mesInputLog.setOrderType(mesProductWorkorder.getOrderType());
        mesInputLog.setClientName(mesProductWorkorder.getCustomerName());
        mesInputLog.setProductId(mesStrandedwireWorkorder.getProductId());
        mesInputLog.setProductModel(mesStrandedwireWorkorder.getModel());
        return mesInputLogMapper.update(mesInputLog);
    }

    /**
     * 修改数据
     *
     * @param mesStrandedwireWorkorder 实例对象
     * @return 实例对象
     */
    @Override
    public MesStrandedwireWorkorder update(MesStrandedwireWorkorder mesStrandedwireWorkorder) {
        mesStrandedwireWorkorder.setUpdateTime(DateUtils.getNowDate());
        mesStrandedwireWorkorder.setUpdateBy(SecurityUtils.getUsername());
        this.mesStrandedwireWorkorderMapper.update(mesStrandedwireWorkorder);
        return this.queryById(mesStrandedwireWorkorder.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(BigInteger id) {
        return this.mesStrandedwireWorkorderMapper.deleteById(id) > 0;
    }

    @Override
    public int deleteByIds(BigInteger[] ids) {
        return this.mesStrandedwireWorkorderMapper.deleteByIds(ids);
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
            MesStrandedwireWorkorder mesStrandedwireWorkorder = mesStrandedwireWorkorderMapper.queryById(id);
            //修改生产工单状态为生产完成
            MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesStrandedwireWorkorder.getWorkorderId());
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
        return mesStrandedwireWorkorderMapper.updateStatusById(id, status);
    }

    /**
     * 退火工单作废
     * @param id
     * @return
     */
    @Override
    public int cancelById(BigInteger id) {
        //通过id查询生产工单
        return mesStrandedwireWorkorderMapper.cancelById(id);
    }

    /**
     *  通过机台id查询该机台所有已排产、生产中列表
     * @param equipmentId
     * @return
     */
    @Override
    public List<MesStrandedwireWorkorder> getListByEquipmentId(BigInteger equipmentId, String axleNumber) {
        String[] axleNumbers = axleNumber.split(",");
        List<MesStrandedwireWorkorder> list = new ArrayList<>();
        for (String number : axleNumbers) {
            List<MesStrandedwireWorkorder> listByEquipmentId = mesStrandedwireWorkorderMapper.getListByEquipmentId(equipmentId, number);
            list.addAll(listByEquipmentId);
        }
        return list;
    }
}
