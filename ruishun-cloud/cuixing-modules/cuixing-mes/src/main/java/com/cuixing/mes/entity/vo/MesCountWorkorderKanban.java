package com.cuixing.mes.entity.vo;

import com.cuixing.mes.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MesCountWorkorderKanban {
    //排产中所有工单
    private Integer schedulingNum;
    //生产中所有工单
    private Integer productionNum;
    //已完成所有工单
    private Integer completedNum;
    //今日排产工单数量
    private Integer todaySchedulingNum;
    //拉丝工单生产中数量
    private Integer weirdrawingProductionNum;
    //拉丝工单排产数量
    private Integer weirdrawingSchedulingNum;
    //今日拉丝工单排产数量
    private Integer todayWeirdrawingSchedulingNum;
    //工艺工单已排产数量
    private Integer routingNum;
    //今日工艺工单排产数量
    private Integer todayRoutingNum;
    //工艺工单生产中数量
    private Integer routingProductionNum;
    //退火工单数据
    List<MesAnnealingWorkorder> mesAnnealingWorkorders;
    //拉丝工单数据
    List<MesWiredrawingWorkorder> mesWiredrawingWorkorders;
    //镀锡工单数据
    List<MesTinningWorkorder> mesTinningWorkorders;
    //并丝工单数据
    List<MesDoublewireWorkorder> mesDoublewireWorkorders;
    //绞线工单数据
    List<MesStrandedwireWorkorder> mesStrandedwireWorkorders;

}
