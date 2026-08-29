package com.cuixing.mes.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.mes.ProductException;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mes.entity.MesInputLog;
import com.cuixing.mes.entity.MesProductWorkorder;
import com.cuixing.mes.entity.vo.MesWorkorderKanban;
import com.cuixing.mes.mapper.MesInputLogMapper;
import com.cuixing.mes.service.IMesProductWorkorderService;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.RemoteSaleService;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.domain.sale.SaleOrder;
import com.cuixing.system.api.domain.md.MdWorkbenchEnamelling;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.mes.entity.MesEnamellingWorkorder;
import com.cuixing.mes.mapper.MesEnamellingWorkorderMapper;
import com.cuixing.mes.service.IMesEnamellingWorkorderService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 漆包工单;(mes_enamelling_workorder)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-11
 */
@Service
public class MesEnamellingWorkorderServiceImpl implements IMesEnamellingWorkorderService{
    @Autowired
    private MesEnamellingWorkorderMapper mesEnamellingWorkorderMapper;

    @Autowired
    private IMesProductWorkorderService iMesProductWorkorderService;

    @Autowired
    private RemoteMdService remoteMdService;

    @Autowired
    private RemoteSaleService remoteSaleService;

    @Autowired
    private RemoteSystemService remoteSystemService;

    @Autowired
    private MesInputLogMapper mesInputLogMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MesEnamellingWorkorder queryById(BigInteger id){
        return mesEnamellingWorkorderMapper.queryById(id);
    }

    /**
     * 通过生产工单id查询单条数据
     *
     * @param workorderId 主键
     * @return 实例对象
     */
    @Override
    public MesEnamellingWorkorder queryByWorkorderId(BigInteger workorderId) {
        return mesEnamellingWorkorderMapper.queryByWorkorderId(workorderId);
    }

    /**
     * 分页查询
     *
     * @param mesEnamellingWorkorder 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MesEnamellingWorkorder> selectMesEnamellingWorkorderList(MesEnamellingWorkorder mesEnamellingWorkorder){
       return mesEnamellingWorkorderMapper.selectMesEnamellingWorkorderList(mesEnamellingWorkorder);
    }

    /**
     * 新增数据
     *
     * @param mesEnamellingWorkorder 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
//    @Transactional
    public int insert(MesEnamellingWorkorder mesEnamellingWorkorder){
    	mesEnamellingWorkorder.setCreateTime(DateUtils.getNowDate());
		mesEnamellingWorkorder.setCreateBy(SecurityUtils.getUsername());
        boolean result = checkScope(mesEnamellingWorkorder.getEquipmentId(), mesEnamellingWorkorder.getSpecification());
        if (!result){
            throw new ProductException("机台生产范围与规格不匹配！！！");
        }
        //漆包工单号
        String autoCode = remoteSystemService.getAutoCode("WORKORDER_CODE_ENAMELLING", "1");
        String EnamellingCode = mesEnamellingWorkorder.getWorkorderCode() + autoCode;
        mesEnamellingWorkorder.setEnamellingCode(EnamellingCode);

        MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesEnamellingWorkorder.getWorkorderId());
        //计划重量
        Double weight = mesProductWorkorder.getWeight();
        //已排产重量
        Double eWeight = mesProductWorkorder.getEnamellingSchedulingWeight();
        //剩余重量
        Double rWeight = weight - eWeight;
        if (mesEnamellingWorkorder.getWeight() > rWeight){
            throw new ProductException("漆包排产重量超过剩余计划量！！！");
        }else{
            mesProductWorkorder.setEnamellingSchedulingWeight(eWeight + mesEnamellingWorkorder.getWeight());
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
        return mesEnamellingWorkorderMapper.insert(mesEnamellingWorkorder);
    }

    //校验漆包范围
    private boolean checkScope(BigInteger workbenchId, String specification){
       MdWorkbenchEnamelling mdWorkbenchEnamelling = remoteMdService.getWorkbenchEnamellingById(workbenchId, SecurityConstants.INNER).getData();
        //生产下限
       double productionLowerLimit = mdWorkbenchEnamelling.getProductionLowerLimit();
       //生产上限
       double productionUpperLimit = mdWorkbenchEnamelling.getProductionUpperLimit();

       double specificationDouble = Double.parseDouble(specification);

        return !(specificationDouble > productionUpperLimit) && !(specificationDouble < productionLowerLimit);
    }
    /**
     * 更新数据
     *
     * @param mesEnamellingWorkorder 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public int update(MesEnamellingWorkorder mesEnamellingWorkorder){
        mesEnamellingWorkorder.setUpdateTime(DateUtils.getNowDate());
		mesEnamellingWorkorder.setUpdateBy(SecurityUtils.getUsername());
        return mesEnamellingWorkorderMapper.update(mesEnamellingWorkorder);
    }

     /**
      * 排序-优先
      * @param id
      * @return
      */
     @Override
     public int updateSortById(BigInteger id) {
         return mesEnamellingWorkorderMapper.updateSortById(id);
     }
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mesEnamellingWorkorderMapper.deleteByIds(ids);
    }

    /**
     * 漆包工单作废
     * @param id
     * @return
     */
    @Override
    public int cancelById(BigInteger id) {
        //通过id查询生产工单
        return mesEnamellingWorkorderMapper.cancelById(id);
    }

    /**
     * 漆包插单
     */
    @Override
    @Transactional
    public int insertWorkorder(MesEnamellingWorkorder mesEnamellingWorkorder) {
        //插单检验
        if (mesEnamellingWorkorder.getInsertId() == null){
            throw new ProductException("插单工单为空！");
        }
        MesEnamellingWorkorder enamellingWorkorder = mesEnamellingWorkorderMapper.queryById(mesEnamellingWorkorder.getId());
        MesInputLog mesInputLog = mesInputLogMapper.queryByWorkstationIdAndAxleNumber(enamellingWorkorder.getEquipmentId(), enamellingWorkorder.getAxleNumber());
        if (mesInputLog == null){
            throw new ProductException("投料记录为空");
        }else {
            if (!Objects.equals(mesInputLog.getTaskId(), enamellingWorkorder.getId())){
                throw new ProductException("该工单暂无投料，插单失败！");
            }
        }

        MesEnamellingWorkorder insertWorkorder= mesEnamellingWorkorderMapper.queryById(mesEnamellingWorkorder.getInsertId());
        if (!Objects.equals(enamellingWorkorder.getEquipmentId(), insertWorkorder.getEquipmentId()) ||
                !Objects.equals(enamellingWorkorder.getAxleNumber(), insertWorkorder.getAxleNumber()) ||
                !Objects.equals(enamellingWorkorder.getSpecification(), insertWorkorder.getSpecification())
        ) {
            throw new ProductException("工单机台、轴号或规格不一致，不能进行插单");
        }
        //修改插入工单的插单状态等
        insertWorkorder.setInsertStatus("1");
        insertWorkorder.setInsertId(enamellingWorkorder.getId());
        insertWorkorder.setInsertWorkorder(enamellingWorkorder.getEnamellingCode());
        mesEnamellingWorkorderMapper.update(insertWorkorder);
        //修改生产工单
        enamellingWorkorder.setInsertId(mesEnamellingWorkorder.getInsertId());
        enamellingWorkorder.setInsertWorkorder(insertWorkorder.getEnamellingCode());
        enamellingWorkorder.setInsertModel(insertWorkorder.getModel());
        enamellingWorkorder.setInsertStatus("2");

        //修改投料记录
        mesInputLog.setTaskId(insertWorkorder.getId());
        mesInputLog.setTaskSn(insertWorkorder.getEnamellingCode());
        mesInputLog.setWorkorderId(insertWorkorder.getWorkorderId());
        mesInputLog.setWorkorderSn(insertWorkorder.getWorkorderCode());
        MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(insertWorkorder.getWorkorderId());
        mesInputLog.setOrderId(mesProductWorkorder.getOrderId());
        mesInputLog.setOrderSn(mesProductWorkorder.getOrderCode());
        mesInputLog.setOrderType(mesProductWorkorder.getOrderType());
        mesInputLog.setClientName(mesProductWorkorder.getCustomerName());
        mesInputLog.setProductId(insertWorkorder.getProductId());
        mesInputLog.setProductModel(insertWorkorder.getModel());
        mesInputLogMapper.update(mesInputLog);

        return mesEnamellingWorkorderMapper.update(enamellingWorkorder);
    }

    /**
     * 插单完成
     */
    @Override
    @Transactional
    public int cancelInsertWorkorder(BigInteger id) {
        MesEnamellingWorkorder enamellingWorkorder = mesEnamellingWorkorderMapper.queryById(id);

        //修改插单工单状态为正常
        if (enamellingWorkorder.getInsertId() != null){
            MesEnamellingWorkorder insertWorkorder = mesEnamellingWorkorderMapper.queryById(enamellingWorkorder.getInsertId());
            insertWorkorder.setInsertStatus("0");
            insertWorkorder.setStatus("2");
            insertWorkorder.setInsertId(null);
            insertWorkorder.setInsertModel(null);
            insertWorkorder.setInsertWorkorder(null);
            mesEnamellingWorkorderMapper.update(insertWorkorder);
        }else{
            throw new ProductException("操作失败，插单完成异常!!!");
        }

        //修改生产工单
        enamellingWorkorder.setInsertId(null);
        enamellingWorkorder.setInsertWorkorder(null);
        enamellingWorkorder.setInsertModel(null);
        enamellingWorkorder.setInsertStatus("0");

        //更改投料记录
        updateMesInputLog(enamellingWorkorder);

        //插单工单的插单状态变更为插单中
        return mesEnamellingWorkorderMapper.update(enamellingWorkorder);
    }

    /**
     * 插单取消
     * @param id
     * @return
     */
    @Override
    public int cancelWorkorder(BigInteger id) {
        MesEnamellingWorkorder enamellingWorkorder = mesEnamellingWorkorderMapper.queryById(id);

        //修改插单工单状态为正常
        if (enamellingWorkorder.getInsertId() != null){
            MesEnamellingWorkorder insertWorkorder = mesEnamellingWorkorderMapper.queryById(enamellingWorkorder.getInsertId());
            if (insertWorkorder.getStatus().equals("0")){
                insertWorkorder.setInsertStatus("0");
                insertWorkorder.setInsertId(null);
                insertWorkorder.setInsertWorkorder(null);
                mesEnamellingWorkorderMapper.update(insertWorkorder);
            }else{
                throw new ProductException("该插单工单已开始生产，不能取消!");
            }
        }else{
            throw new ProductException("操作失败，插单工单异常!!!");
        }
        //修改生产工单
        enamellingWorkorder.setInsertId(null);
        enamellingWorkorder.setInsertWorkorder(null);
        enamellingWorkorder.setInsertModel(null);
        enamellingWorkorder.setInsertStatus("0");

        //更改投料记录
        updateMesInputLog(enamellingWorkorder);

        return mesEnamellingWorkorderMapper.update(enamellingWorkorder);
    }

    //投料记录变更
    private int updateMesInputLog(MesEnamellingWorkorder mesEnamellingWorkorder){
        //获取投料信息
        MesInputLog mesInputLog = mesInputLogMapper.queryByWorkstationIdAndAxleNumber(mesEnamellingWorkorder.getEquipmentId(), mesEnamellingWorkorder.getAxleNumber());
        if (mesInputLog == null){
            throw new ProductException("投料记录为空");
        }
        //修改投料记录
        mesInputLog.setTaskId(mesEnamellingWorkorder.getId());
        mesInputLog.setTaskSn(mesEnamellingWorkorder.getEnamellingCode());
        mesInputLog.setWorkorderId(mesEnamellingWorkorder.getWorkorderId());
        mesInputLog.setWorkorderSn(mesEnamellingWorkorder.getWorkorderCode());
        MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesEnamellingWorkorder.getWorkorderId());
        mesInputLog.setOrderId(mesProductWorkorder.getOrderId());
        mesInputLog.setOrderSn(mesProductWorkorder.getOrderCode());
        mesInputLog.setOrderType(mesProductWorkorder.getOrderType());
        mesInputLog.setClientName(mesProductWorkorder.getCustomerName());
        mesInputLog.setProductId(mesEnamellingWorkorder.getProductId());
        mesInputLog.setProductModel(mesEnamellingWorkorder.getModel());
        return mesInputLogMapper.update(mesInputLog);
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
            MesEnamellingWorkorder mesEnamellingWorkorder = mesEnamellingWorkorderMapper.queryById(id);
            //修改生产工单状态为生产完成
            MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesEnamellingWorkorder.getWorkorderId());
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
        return mesEnamellingWorkorderMapper.updateStatusById(id, status);
    }

    /**
     *  通过机台id查询该机台所有已排产、生产中列表
     * @param equipmentId
     * @return
     */
    @Override
    public List<MesEnamellingWorkorder> getListByEquipmentId(BigInteger equipmentId, String axleNumber) {
        String[] axleNumbers = axleNumber.split(",");
        List<MesEnamellingWorkorder> list = new ArrayList<>();
        for (String number : axleNumbers) {
            List<MesEnamellingWorkorder> listByEquipmentId = mesEnamellingWorkorderMapper.getListByEquipmentId(equipmentId, number);
            list.addAll(listByEquipmentId);
        }
        return list;
    }

    @Override
    public MesWorkorderKanban getEnamellingSummaryData() {
        MesWorkorderKanban mesWorkorderKanban = new MesWorkorderKanban();
        //排产中数量
        int schedulingNum = mesEnamellingWorkorderMapper.getWorkorderCountByStatus("0");
        mesWorkorderKanban.setSchedulingNum(schedulingNum);
        //生产中数量
        int productionNum = mesEnamellingWorkorderMapper.getWorkorderCountByStatus("1");
        mesWorkorderKanban.setProductionNum(productionNum);
        //已完成数量
        int completedNum = mesEnamellingWorkorderMapper.getWorkorderCountByStatus("2");
        mesWorkorderKanban.setCompletedNum(completedNum);
        //排产中数量
        int todaySchedulingNum = mesEnamellingWorkorderMapper.getTodaySchedulingNum();
        mesWorkorderKanban.setTodaySchedulingNum(todaySchedulingNum);

        //工单列表
        List<MesEnamellingWorkorder> enamellingWorkorderList = mesEnamellingWorkorderMapper.getEnamellingWorkorderList();
        mesWorkorderKanban.setMesEnamellingWorkorders(enamellingWorkorderList);

        return mesWorkorderKanban;
    }
}
