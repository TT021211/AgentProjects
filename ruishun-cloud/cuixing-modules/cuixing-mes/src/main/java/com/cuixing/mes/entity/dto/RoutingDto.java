package com.cuixing.mes.entity.dto;

import com.cuixing.mes.entity.MesRoutingWorkstage;
import com.cuixing.mes.entity.MesWorkstage;
import com.cuixing.mes.entity.vo.treeSelect;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class RoutingDto implements Serializable {
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键",notes = "")
    private BigInteger id ;
    /** 路线名称 */
    @ApiModelProperty(name = "路线名称",notes = "")
    private String routingName ;
    /** 路线编号 */
    @ApiModelProperty(name = "路线编号",notes = "")
    private String routingCode ;
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remark ;

    /** 创建人 */
    @ApiModelProperty(name = "创建人",notes = "")
    private String createBy ;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间",notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime ;

    @ApiModelProperty(name = "工序列表",notes = "")
    private List<treeSelect> routingWorkstages;

    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 路线名称 */
    public String getRoutingName(){
        return this.routingName;
    }
    /** 路线名称 */
    public void setRoutingName(String routingName){
        this.routingName=routingName;
    }
    /** 路线编号 */
    public String getRoutingCode(){
        return this.routingCode;
    }
    /** 路线编号 */
    public void setRoutingCode(String routingCode){
        this.routingCode=routingCode;
    }
    /** 备注 */
    public String getRemark(){
        return this.remark;
    }
    /** 备注 */
    public void setRemark(String remark){
        this.remark=remark;
    }
    /** 创建人 */
    public String getCreateBy(){
        return this.createBy;
    }
    /** 创建人 */
    public void setCreateBy(String createBy){
        this.createBy=createBy;
    }
    /** 创建时间 */
    public Date getCreateTime(){
        return this.createTime;
    }
    /** 创建时间 */
    public void setCreateTime(Date createTime){
        this.createTime=createTime;
    }
    /** 工序列表 **/
    public List<treeSelect> getRoutingWorkstages() {
        return routingWorkstages;
    }
    /** 工序列表 **/
    public void setRoutingWorkstages(List<treeSelect> routingWorkstages) {
        this.routingWorkstages = routingWorkstages;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("routingName", getRoutingName())
                .append("routingCode", getRoutingCode())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("routingWorkstages", getRoutingWorkstages())
                .toString();
    }
}
