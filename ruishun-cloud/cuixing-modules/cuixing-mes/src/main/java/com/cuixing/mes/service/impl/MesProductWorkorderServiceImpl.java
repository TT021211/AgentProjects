package com.cuixing.mes.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.mes.ProductException;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mes.entity.*;
import com.cuixing.mes.entity.vo.KanbanPage;
import com.cuixing.mes.entity.vo.MesCountWorkorderKanban;
import com.cuixing.mes.entity.vo.MesPageWeek;
import com.cuixing.mes.mapper.*;
import com.cuixing.mes.service.*;
import com.cuixing.mes.utils.CodeGenerator;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.RemoteSaleService;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.domain.sale.SaleOrder;
import com.cuixing.system.api.domain.md.MdUnitMeasure;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
 /**
 * 生产工单;(mes_product_workorder)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-11
 */
@Service
public class MesProductWorkorderServiceImpl implements IMesProductWorkorderService{
    @Autowired
    private MesProductWorkorderMapper mesProductWorkorderMapper;

    @Autowired
    private MesProcessRoutingServiceImpl iMesProcessRoutingService;

    @Autowired
    private MesWiredrawingWorkorderMapper mesWiredrawingWorkorderMapper;

    @Autowired
    private MesStrandedwireWorkorderMapper mesStrandedwireWorkorderMapper;

    @Autowired
    private MesAnnealingWorkorderMapper mesAnnealingWorkorderMapper;

    @Autowired
    private MesDoublewireWorkorderMapper mesDoublewireWorkorderMapper;

    @Autowired
    private MesTinningWorkorderMapper mesTinningWorkorderMapper;

    @Autowired
    private MesOuputLogMapper mesOuputLogMapper;

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
    public MesProductWorkorder queryById(BigInteger id){
        return mesProductWorkorderMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mesProductWorkorder 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MesProductWorkorder> selectMesProductWorkorderList(MesProductWorkorder mesProductWorkorder){
       return mesProductWorkorderMapper.selectMesProductWorkorderList(mesProductWorkorder);
    }

    /**
     * 新增数据
     *
     * @param mesProductWorkorder 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
//    @Transactional
    public int insert(MesProductWorkorder mesProductWorkorder) throws ParseException {
        //获取生产工单编码

        if (mesProductWorkorder.getRoutingId() != null){
            MesProcessRouting mesProcessRouting = iMesProcessRoutingService.queryById(mesProductWorkorder.getRoutingId());
            mesProductWorkorder.setRoutingName(mesProcessRouting.getRoutingName());
        }
        if (mesProductWorkorder.getMeasureId() != null){
            R<MdUnitMeasure> measure = remoteMdService.getMeasure(mesProductWorkorder.getMeasureId(), SecurityConstants.INNER);
            System.out.println("查看单位名称：mesProductWorkorder：{}"+ measure.getData().getMeasureName());
            mesProductWorkorder.setMeasureName(measure.getData().getMeasureName());
        }
        //修改销售订单状态为“已下发”
        if (mesProductWorkorder.getOrderId() != null){
            R<SaleOrder> order = remoteSaleService.getOrder(mesProductWorkorder.getOrderId(), SecurityConstants.INNER);
            if (order.getCode() == 200){
                SaleOrder saleOrder = order.getData();
                saleOrder.setStatus("1");
                R<AjaxResult> ajaxResultR = remoteSaleService.updateOrder(saleOrder, SecurityConstants.INNER);
                if (ajaxResultR.getCode() != 200){
                    throw new RuntimeException("主数据服务调用失败{"+ajaxResultR.getMsg()+"}");
                }
            }
        }
        if (null == mesProductWorkorder.getCreateTime()){
            mesProductWorkorder.setCreateTime(DateUtils.getNowDate());
        }else{
            mesProductWorkorder.setCreateTime(mesProductWorkorder.getCreateTime());
        }
        Date createTime = mesProductWorkorder.getCreateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(createTime);
        String code = CodeGenerator.generateCode(formattedDate,"PR");
        mesProductWorkorder.setWorkorderCode(code);

        mesProductWorkorder.setCreateBy(SecurityUtils.getUsername());
        return mesProductWorkorderMapper.insert(mesProductWorkorder);
    }

    /**
     * 更新数据
     *
     * @param mesProductWorkorder 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MesProductWorkorder mesProductWorkorder){
        mesProductWorkorder.setUpdateTime(DateUtils.getNowDate());
		mesProductWorkorder.setUpdateBy(SecurityUtils.getUsername());
        return mesProductWorkorderMapper.update(mesProductWorkorder);
    }


     /**
      * * 通过id变更状态
      * @param id
      * @param status
      * @return
      */
     @Override
     public int updateStatus(BigInteger id, String status) {
         if (!status.equals("0") && !status.equals("1") && !status.equals("2") && !status.equals("3") && !status.equals("4")) throw new ProductException("生产状态值错误!!!");
         return mesProductWorkorderMapper.updateStatus(id, status);
     }

     /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mesProductWorkorderMapper.deleteByIds(ids);
    }

     /**
      * 通过生产工单号查询生产工单
      */
     @Override
     public MesProductWorkorder queryByWorkorderCode(String workorderCode) {
         return mesProductWorkorderMapper.queryByWorkorderCode(workorderCode);
     }
     /**
      * 生产工单作废
      */
     @Override
    @GlobalTransactional
//     @Transactional
     public int cancelById(BigInteger id) {
         MesWiredrawingWorkorder selectConditions = new MesWiredrawingWorkorder();
         selectConditions.setWorkorderId(id);
         List<MesWiredrawingWorkorder> mesWiredrawingWorkorders = mesWiredrawingWorkorderMapper.selectMesWiredrawingWorkorderList(selectConditions);
         for (MesWiredrawingWorkorder mesWiredrawingWorkorder : mesWiredrawingWorkorders) {
             if (mesWiredrawingWorkorder.getStatus().equals("1") || mesWiredrawingWorkorder.getStatus().equals("2")) {
                 throw new ProductException("该生产工单下有正在生产的或完成的拉丝工单，暂不能作废！");
             }
         }
         MesAnnealingWorkorder selectConditions1 = new MesAnnealingWorkorder();
         selectConditions1.setWorkorderId(id);
         List<MesAnnealingWorkorder> mesAnnealingWorkorderList = mesAnnealingWorkorderMapper.selectMesAnnealingWorkorderList(selectConditions1);
         for (MesAnnealingWorkorder mesAnnealingWorkorder : mesAnnealingWorkorderList) {
             if (mesAnnealingWorkorder.getStatus().equals("1") || mesAnnealingWorkorder.getStatus().equals("2")) {
                 throw new ProductException("该生产工单下有正在生产或完成的退火工单，不能作废！");
             }
         }
         MesDoublewireWorkorder selectConditions2 = new MesDoublewireWorkorder();
         selectConditions2.setWorkorderId(id);
         List<MesDoublewireWorkorder> mesDoublewireWorkorders = mesDoublewireWorkorderMapper.selectMesDoublewireWorkorderList(selectConditions2);
         for (MesDoublewireWorkorder mesDoublewireWorkorder : mesDoublewireWorkorders) {
             if (mesDoublewireWorkorder.getStatus().equals("1") || mesDoublewireWorkorder.getStatus().equals("2")) {
                 throw new ProductException("该生产工单下有正在生产或完成的并丝工单，不能作废！");
             }
         }
         MesTinningWorkorder selectConditions3 = new MesTinningWorkorder();
         selectConditions3.setWorkorderId(id);
         List<MesTinningWorkorder> mesTinningWorkorders = mesTinningWorkorderMapper.selectMesTinningWorkorderList(selectConditions3);
         for (MesTinningWorkorder mesTinningWorkorder : mesTinningWorkorders) {
             if (mesTinningWorkorder.getStatus().equals("1") || mesTinningWorkorder.getStatus().equals("2")) {
                 throw new ProductException("该生产工单下有正在生产或完成的镀锡工单，不能作废！");
             }
         }
         MesStrandedwireWorkorder selectConditions4 = new MesStrandedwireWorkorder();
         selectConditions4.setWorkorderId(id);
         List<MesStrandedwireWorkorder> mesStrandedwireWorkorders = mesStrandedwireWorkorderMapper.selectMesStrandedwireWorkorderList(selectConditions4);
         for (MesStrandedwireWorkorder mesStrandedwireWorkorder : mesStrandedwireWorkorders) {
             if (mesStrandedwireWorkorder.getStatus().equals("1") || mesStrandedwireWorkorder.getStatus().equals("2")) {
                 throw new ProductException("该生产工单下有正在生产或完成的绞线工单，不能作废！");
             }
         }
         //所有工序工单状态变更为已作废
         if (!mesWiredrawingWorkorders.isEmpty()){
             for (MesWiredrawingWorkorder mesWiredrawingWorkorder : mesWiredrawingWorkorders) {
                 mesWiredrawingWorkorder.setStatus("3");
                 mesWiredrawingWorkorderMapper.update(mesWiredrawingWorkorder);
             }
         }
         if (!mesAnnealingWorkorderList.isEmpty()){
            for (MesAnnealingWorkorder mesAnnealingWorkorder : mesAnnealingWorkorderList) {
                mesAnnealingWorkorder.setStatus("3");
                mesAnnealingWorkorderMapper.update(mesAnnealingWorkorder);
            }
         }
         if (!mesTinningWorkorders.isEmpty()){
             for (MesTinningWorkorder mesTinningWorkorder : mesTinningWorkorders) {
                 mesTinningWorkorder.setStatus("3");
                 mesTinningWorkorderMapper.update(mesTinningWorkorder);
             }
         }
         if (!mesStrandedwireWorkorders.isEmpty()){
             for (MesDoublewireWorkorder mesDoublewireWorkorder : mesDoublewireWorkorders) {
                 mesDoublewireWorkorder.setStatus("3");
                 mesDoublewireWorkorderMapper.update(mesDoublewireWorkorder);
             }
         }
         if (!mesDoublewireWorkorders.isEmpty()){
             for (MesStrandedwireWorkorder mesStrandedwireWorkorder : mesStrandedwireWorkorders) {
                 mesStrandedwireWorkorder.setStatus("3");
                 mesStrandedwireWorkorderMapper.update(mesStrandedwireWorkorder);
             }
         }
         //通过id查询生产工单
         MesProductWorkorder mesProductWorkorder = mesProductWorkorderMapper.queryById(id);
         //修改销售订单状态为“已作废”
         if (mesProductWorkorder.getOrderId() != null){
             SaleOrder saleOrder = remoteSaleService.getOrder(mesProductWorkorder.getOrderId(), SecurityConstants.INNER).getData();
             if (saleOrder != null){
                 saleOrder.setStatus("5");
                 R<AjaxResult> ajaxResultR = remoteSaleService.updateOrder(saleOrder, SecurityConstants.INNER);
                 if (ajaxResultR.getCode() != 200){
                     throw new RuntimeException("主数据服务调用失败{"+ajaxResultR.getMsg()+"}");
                 }
             }
         }
         return mesProductWorkorderMapper.cancelById(id);
     }

     @Override
     public MesCountWorkorderKanban getMesWorkOrdersData() {
         //1、计算所有工序的工单情况并统计
         MesCountWorkorderKanban mesCountWorkorderKanban = new MesCountWorkorderKanban();
         //1.1、查询所有已排产的工单
         int schedulingNum = mesAnnealingWorkorderMapper.countByStatus("0") + mesDoublewireWorkorderMapper.countByStatus("0") + mesStrandedwireWorkorderMapper.countByStatus("0") + mesTinningWorkorderMapper.countByStatus("0") + mesWiredrawingWorkorderMapper.countByStatus("0");
         mesCountWorkorderKanban.setSchedulingNum(schedulingNum);
         //1.2 查询所有生产中的工单
         int productionNum = mesAnnealingWorkorderMapper.countByStatus("1") + mesDoublewireWorkorderMapper.countByStatus("1") + mesStrandedwireWorkorderMapper.countByStatus("1") + mesTinningWorkorderMapper.countByStatus("1") + mesWiredrawingWorkorderMapper.countByStatus("1");
         mesCountWorkorderKanban.setProductionNum(productionNum);
         //1.3 查询所有已完成的工单
         int completedNum = mesAnnealingWorkorderMapper.countByStatus("2") + mesDoublewireWorkorderMapper.countByStatus("2") + mesStrandedwireWorkorderMapper.countByStatus("2") + mesTinningWorkorderMapper.countByStatus("2") + mesWiredrawingWorkorderMapper.countByStatus("2");
         mesCountWorkorderKanban.setCompletedNum(completedNum);
         //1.4 查询今日已排产工单
         int todaySchedulingNum = mesAnnealingWorkorderMapper.getTodaySchedulingNum() + mesDoublewireWorkorderMapper.getTodaySchedulingNum() + mesStrandedwireWorkorderMapper.getTodaySchedulingNum() + mesTinningWorkorderMapper.getTodaySchedulingNum() + mesWiredrawingWorkorderMapper.getTodaySchedulingNum();
         mesCountWorkorderKanban.setTodaySchedulingNum(todaySchedulingNum);
         //1.5 查询拉丝工单排产中数量
         int weirdrawingSchedulingNum = mesWiredrawingWorkorderMapper.countByStatus("0");
         mesCountWorkorderKanban.setWeirdrawingSchedulingNum(weirdrawingSchedulingNum);
         //1.5.1 查询拉丝今日的排产数量
         int todayWeirdrawingSchedulingNum = mesWiredrawingWorkorderMapper.getTodaySchedulingNum();
         mesCountWorkorderKanban.setTodayWeirdrawingSchedulingNum(todayWeirdrawingSchedulingNum);
         //1.5.2 查询拉丝生产中的数量
         int weirdrawingProductionNum = mesWiredrawingWorkorderMapper.countByStatus("1");
         mesCountWorkorderKanban.setWeirdrawingProductionNum(weirdrawingProductionNum);
         //1.6 查询工艺工单已排产数量
         int routingNum = mesAnnealingWorkorderMapper.countByStatus("0") + mesDoublewireWorkorderMapper.countByStatus("0") + mesStrandedwireWorkorderMapper.countByStatus("0") + mesTinningWorkorderMapper.countByStatus("0");
         mesCountWorkorderKanban.setRoutingNum(routingNum);
         //1.6.1 查询工艺今日的排产数量
         int todayRoutingNum = mesAnnealingWorkorderMapper.getTodaySchedulingNum() + mesDoublewireWorkorderMapper.getTodaySchedulingNum() + mesStrandedwireWorkorderMapper.getTodaySchedulingNum() + mesTinningWorkorderMapper.getTodaySchedulingNum();
         mesCountWorkorderKanban.setTodayRoutingNum(todayRoutingNum);
         //1.6.2 查询工艺生产中的数量
         int routingProductionNum = mesAnnealingWorkorderMapper.countByStatus("1") + mesDoublewireWorkorderMapper.countByStatus("1") + mesStrandedwireWorkorderMapper.countByStatus("1") + mesTinningWorkorderMapper.countByStatus("1");
         mesCountWorkorderKanban.setRoutingProductionNum(routingProductionNum);
         //2.1 工单列表（只查询已排产和生产中的工单数据）
         //2.1.1 退火工单数据
         List<MesAnnealingWorkorder> mesAnnealingWorkorders = mesAnnealingWorkorderMapper.getMesAnnealingWorkorderList();
         mesCountWorkorderKanban.setMesAnnealingWorkorders(mesAnnealingWorkorders);
         //2.1.2 并丝工单数据
         List<MesDoublewireWorkorder> mesDoublewireWorkorders = mesDoublewireWorkorderMapper.getMesDoublewireWorkorderList();
         mesCountWorkorderKanban.setMesDoublewireWorkorders(mesDoublewireWorkorders);
         //2.1.3 绞线工单数据
         List<MesStrandedwireWorkorder> mesStrandedwireWorkorders = mesStrandedwireWorkorderMapper.getMesStrandedwireWorkorderList();
         mesCountWorkorderKanban.setMesStrandedwireWorkorders(mesStrandedwireWorkorders);
         //2.1.4 镀锡工单数据
         List<MesTinningWorkorder> mesTinningWorkorders = mesTinningWorkorderMapper.getMesTinningWorkorderList();
         mesCountWorkorderKanban.setMesTinningWorkorders(mesTinningWorkorders);
         //2.1.5 拉丝工单数据
         List<MesWiredrawingWorkorder> mesWiredrawingWorkorders = mesWiredrawingWorkorderMapper.getMesWiredrawingWorkorderList();
         mesCountWorkorderKanban.setMesWiredrawingWorkorders(mesWiredrawingWorkorders);

         return mesCountWorkorderKanban;
     }

     @Override
     public KanbanPage getKanbanPage() {
         KanbanPage kanbanPage = mesProductWorkorderMapper.getProductWorkorderNum();
         List<MesPageWeek> mesPageWeek = mesProductWorkorderMapper.getMesProductWorkorderWeek();
         kanbanPage.setMesProductWorkorderWeek(mesPageWeek);
         List<MesPageWeek> wiredrawingNumWeek = mesOuputLogMapper.selectPageKanban();
         kanbanPage.setWiredrawingNumWeek(wiredrawingNumWeek);
         return kanbanPage;
     }
 }
