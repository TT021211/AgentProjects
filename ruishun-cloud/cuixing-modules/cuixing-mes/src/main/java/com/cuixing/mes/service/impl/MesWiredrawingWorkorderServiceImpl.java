package com.cuixing.mes.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.mes.ProductException;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mes.entity.MesEnamellingWorkorder;
import com.cuixing.mes.entity.MesProductWorkorder;
import com.cuixing.mes.mapper.MesEnamellingWorkorderMapper;
import com.cuixing.mes.service.IMesProductWorkorderService;
import com.cuixing.mes.utils.CheckScopeUtil;
import com.cuixing.mes.utils.CodeGenerator;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.RemoteSaleService;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.domain.md.MdAccessory;
import com.cuixing.system.api.domain.sale.SaleOrder;
import com.cuixing.system.api.domain.md.MdWorkbenchWiredrawing;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.mes.entity.MesWiredrawingWorkorder;
import com.cuixing.mes.mapper.MesWiredrawingWorkorderMapper;
import com.cuixing.mes.service.IMesWiredrawingWorkorderService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 拉丝工单;(mes_wiredrawing_workorder)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-11
 */
@Service
public class MesWiredrawingWorkorderServiceImpl implements IMesWiredrawingWorkorderService{
    @Autowired
    private MesWiredrawingWorkorderMapper mesWiredrawingWorkorderMapper;

    @Autowired
    private IMesProductWorkorderService iMesProductWorkorderService;

     @Autowired
     private RemoteMdService remoteMdService;

    @Autowired
    private RemoteSaleService remoteSaleService;

    @Autowired
    private RemoteSystemService remoteSystemService;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MesWiredrawingWorkorder queryById(BigInteger id){
        return mesWiredrawingWorkorderMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mesWiredrawingWorkorder 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MesWiredrawingWorkorder> selectMesWiredrawingWorkorderList(MesWiredrawingWorkorder mesWiredrawingWorkorder){
       return mesWiredrawingWorkorderMapper.selectMesWiredrawingWorkorderList(mesWiredrawingWorkorder);
    }

    /**
     * 新增数据
     *
     * @param mesWiredrawingWorkorder 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
//    @Transactional
    public int insert(MesWiredrawingWorkorder mesWiredrawingWorkorder) throws ParseException {
        if (null == mesWiredrawingWorkorder.getCreateTime()){
            mesWiredrawingWorkorder.setCreateTime(DateUtils.getNowDate());
        }else{
            mesWiredrawingWorkorder.setCreateTime(mesWiredrawingWorkorder.getCreateTime());
        }
		mesWiredrawingWorkorder.setCreateBy(SecurityUtils.getUsername());
        String[] idStringArray = mesWiredrawingWorkorder.getEquipmentId().split(",");
        boolean result = checkScope(idStringArray, mesWiredrawingWorkorder.getSpecification());
        if (!result){
            throw new ProductException("机台生产范围与规格不匹配！！！");
        }
        //拉丝工单号
        Date createTime = mesWiredrawingWorkorder.getCreateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(createTime);
        String autoCode = CodeGenerator.generateCode(formattedDate,"LS");
        mesWiredrawingWorkorder.setWiredrawingCode(autoCode);
        MesProductWorkorder mesProductWorkorder = iMesProductWorkorderService.queryById(mesWiredrawingWorkorder.getWorkorderId());
//        //计划重量
//        Double weight = mesProductWorkorder.getWeight();
//        //已排产重量
//        Double eWeight = mesProductWorkorder.getWiredrawingSchedulingWeight();
//        //剩余重量
//        Double rWeight = weight - eWeight;
//        if (mesWiredrawingWorkorder.getWeight() > rWeight){
//            throw new ProductException("拉丝排产重量超过剩余计划量！！！");
//        }else{
//            mesProductWorkorder.setWiredrawingSchedulingWeight(eWeight + mesWiredrawingWorkorder.getWeight());
//        }
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
        //修改漆包工单的排产状态为N
       /* MesEnamellingWorkorder mesEnamellingWorkorder = mesEnamellingWorkorderMapper.queryByWorkorderId(mesWiredrawingWorkorder.getWorkorderId());
        if (mesEnamellingWorkorder != null){
            mesEnamellingWorkorder.setAttr1("Y");
            mesEnamellingWorkorderMapper.update(mesEnamellingWorkorder);
        }*/
        return mesWiredrawingWorkorderMapper.insert(mesWiredrawingWorkorder);
    }
    //校验拉丝范围
    private boolean checkScope(String[] workbenchId, String specification){
        for (String s : workbenchId) {
            int workbenchIdInt = Integer.parseInt(s);
            MdWorkbenchWiredrawing mdWorkbenchWiredrawing = remoteMdService.getWorkbenchWiredrawingById(BigInteger.valueOf(workbenchIdInt), SecurityConstants.INNER).getData();
            //生产下限
            double productionLowerLimit = mdWorkbenchWiredrawing.getProductionLowerLimit();
            //生产上限
            double productionUpperLimit = mdWorkbenchWiredrawing.getProductionUpperLimit();
            double specificationDouble = CheckScopeUtil.extractFirstValue(specification);
            if (specificationDouble > productionUpperLimit || specificationDouble < productionLowerLimit) {
                return false;
            }
        }
        return true;
    }
    /**
     * 更新数据
     *
     * @param mesWiredrawingWorkorder 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MesWiredrawingWorkorder mesWiredrawingWorkorder){
        mesWiredrawingWorkorder.setUpdateTime(DateUtils.getNowDate());
		mesWiredrawingWorkorder.setUpdateBy(SecurityUtils.getUsername());
        return mesWiredrawingWorkorderMapper.update(mesWiredrawingWorkorder);
    }


     @Override
     public int updateSortById(BigInteger id) {
         return mesWiredrawingWorkorderMapper.updateSortById(id);
     }
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mesWiredrawingWorkorderMapper.deleteByIds(ids);
    }

    /**
     * 拉丝工单作废
     * @param id
     * @return
     */
    @Override
    public int cancelById(BigInteger id) {
        return mesWiredrawingWorkorderMapper.cancelById(id);
    }

    /**
     * * 通过id变更状态
     * @param id
     * @param status
     * @return
     */
    @Override
    public int updateStatusById(BigInteger id, String status) {
        if (!status.equals("0") && !status.equals("1") && !status.equals("2") && !status.equals("3")) throw new ProductException("生产状态值错误!!!");
        return mesWiredrawingWorkorderMapper.updateStatusById(id, status);
    }

    @Override
    public MesWiredrawingWorkorder getInfoByWiredrawingCode(String wiredrawingCode) {
        return this.mesWiredrawingWorkorderMapper.getInfoByWiredrawingCode(wiredrawingCode);
    }

    @Override
    public int batchUpdate(ArrayList<MesWiredrawingWorkorder> mesWiredrawingWorkorders) {
        return this.mesWiredrawingWorkorderMapper.batchUpdate(mesWiredrawingWorkorders);
    }

}
