package com.cuixing.mes.entity.vo;

import com.cuixing.mes.entity.MesRoutingWorkstage;
import com.cuixing.mes.entity.MesWorkstage;
import com.cuixing.mes.entity.MesProcessRouting;

import java.io.Serializable;
import java.math.BigInteger;

public class treeSelect implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigInteger id;
    private String lable;

    public treeSelect(MesWorkstage mesWorkstage) {
        this.id=mesWorkstage.getId();
        this.lable=mesWorkstage.getWorkstageName();
    }

    public treeSelect(MesProcessRouting mesProcessRouting)
    {
        this.id= mesProcessRouting.getId();
        this.lable=mesProcessRouting.getRoutingName();
    }

    public treeSelect(MesRoutingWorkstage mesRoutingWorkstage)
    {
        this.id= BigInteger.valueOf(mesRoutingWorkstage.getOrderNum());
        this.lable=mesRoutingWorkstage.getWorkstageName();
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

}
