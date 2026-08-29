package com.cuixing.mes.message.vo;

import com.cuixing.mes.entity.*;

/**
 * 生产排产消息体
 * @author 阿江
 * @date 2021/12/01
 */
public class ProductMessage {
    //绞线
    private MesStrandedwireWorkorder mesStrandedwireWorkorder;
    //并丝
    private MesDoublewireWorkorder mesDoublewireWorkorder;
    //镀锡
    private MesTinningWorkorder mesTinningWorkorder;
    //退火
    private MesAnnealingWorkorder mesAnnealingWorkorder;
    //拉丝
    private MesWiredrawingWorkorder mesWiredrawingWorkorder;

    public MesStrandedwireWorkorder getMesStrandedwireWorkorder() {
        return mesStrandedwireWorkorder;
    }

    public void setMesStrandedwireWorkorder(MesStrandedwireWorkorder mesStrandedwireWorkorder) {
        this.mesStrandedwireWorkorder = mesStrandedwireWorkorder;
    }

    public MesDoublewireWorkorder getMesDoublewireWorkorder() {
        return mesDoublewireWorkorder;
    }

    public void setMesDoublewireWorkorder(MesDoublewireWorkorder mesDoublewireWorkorder) {
        this.mesDoublewireWorkorder = mesDoublewireWorkorder;
    }

    public MesTinningWorkorder getMesTinningWorkorder() {
        return mesTinningWorkorder;
    }

    public void setMesTinningWorkorder(MesTinningWorkorder mesTinningWorkorder) {
        this.mesTinningWorkorder = mesTinningWorkorder;
    }

    public MesAnnealingWorkorder getMesAnnealingWorkorder() {
        return mesAnnealingWorkorder;
    }

    public void setMesAnnealingWorkorder(MesAnnealingWorkorder mesAnnealingWorkorder) {
        this.mesAnnealingWorkorder = mesAnnealingWorkorder;
    }

    public MesWiredrawingWorkorder getMesWiredrawingWorkorder() {
        return mesWiredrawingWorkorder;
    }

    public void setMesWiredrawingWorkorder(MesWiredrawingWorkorder mesWiredrawingWorkorder) {
        this.mesWiredrawingWorkorder = mesWiredrawingWorkorder;
    }
}
