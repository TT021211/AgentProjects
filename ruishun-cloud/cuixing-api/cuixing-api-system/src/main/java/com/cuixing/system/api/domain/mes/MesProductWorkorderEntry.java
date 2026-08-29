package com.cuixing.system.api.domain.mes;

import com.cuixing.common.core.annotation.Excel;
import com.cuixing.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * 生产工单明细表;
 * @author : cuixing-keyun-autogen
 * @date : 2024-5-29
 */
@ApiModel(description = "生产工单明细表")
public class MesProductWorkorderEntry extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty("自增主键")
    @Excel(name = "自增主键", cellType = Excel.ColumnType.NUMERIC, prompt = "自增主键")
    private BigInteger id;
    /** 工单id */
    @ApiModelProperty("工单id")
    @Excel(name = "工单id")
    private BigInteger workorderId;
    /** 产品id */
    @ApiModelProperty("产品id")
    @Excel(name = "产品id")
    private BigInteger productId;
    /** 产品编码 */
    @ApiModelProperty("产品编码")
    @Excel(name = "产品编码")
    private String productCode;
    /** 产品名称 */
    @ApiModelProperty("产品名称")
    @Excel(name = "产品名称")
    private String productName;
    /** 产品尺寸 */
    @ApiModelProperty("产品尺寸")
    @Excel(name = "产品尺寸")
    private String productSize;
    /** 工艺路线id */
    @ApiModelProperty("工艺路线id")
    @Excel(name = "工艺路线id")
    private BigInteger routingId;
    /** 排单日期 */
    @ApiModelProperty("排单日期")
    @Excel(name = "排单日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planDate;
    /** 排单重量 */
    @ApiModelProperty("排单重量")
    @Excel(name = "排单重量")
    private BigDecimal planWeight;
    /** 排单数量 */
    @ApiModelProperty("排单数量")
    @Excel(name = "排单数量")
    private BigDecimal planNum;
    /** 是否完成（0未完成1已完成） */
    @ApiModelProperty("是否完成（0未完成1已完成）")
    @Excel(name = "是否完成（0未完成1已完成）")
    private Integer isDone;
    /** 乐观锁 */
    @ApiModelProperty("乐观锁")
    @Excel(name = "乐观锁")
    private Integer version;
    /** 逻辑删除 */
    @ApiModelProperty("逻辑删除")
    @Excel(name = "逻辑删除")
    private Integer isDeleted;
    /** 创建人 */
    @ApiModelProperty("创建人")
    @Excel(name = "创建人")
    private String createBy;
    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /** 更新人 */
    @ApiModelProperty("更新人")
    @Excel(name = "更新人")
    private String updateBy;
    /** 更新时间 */
    @ApiModelProperty("更新时间")
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /** 预留字段1 */
    @ApiModelProperty("预留字段1")
    @Excel(name = "预留字段1")
    private String attr1;
    /** 预留字段2 */
    @ApiModelProperty("预留字段2")
    @Excel(name = "预留字段2")
    private Integer attr2;
    /** 预留字段3 */
    @ApiModelProperty("预留字段3")
    @Excel(name = "预留字段3")
    private String attr3;

    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 工单id */
    public BigInteger getWorkorderId(){
        return this.workorderId;
    }
    /** 工单id */
    public void setWorkorderId(BigInteger workorderId){
        this.workorderId=workorderId;
    }
    /** 产品id */
    public BigInteger getProductId(){
        return this.productId;
    }
    /** 产品id */
    public void setProductId(BigInteger productId){
        this.productId=productId;
    }
    /** 产品编码 */
    public String getProductCode(){
        return this.productCode;
    }
    /** 产品编码 */
    public void setProductCode(String productCode){
        this.productCode=productCode;
    }
    /** 产品名称 */
    public String getProductName(){
        return this.productName;
    }
    /** 产品名称 */
    public void setProductName(String productName){
        this.productName=productName;
    }
    /** 产品尺寸 */
    public String getProductSize(){
        return this.productSize;
    }
    /** 产品尺寸 */
    public void setProductSize(String productSize){
        this.productSize=productSize;
    }
    /** 工艺路线id */
    public BigInteger getRoutingId(){
        return this.routingId;
    }
    /** 工艺路线id */
    public void setRoutingId(BigInteger routingId){
        this.routingId=routingId;
    }
    /** 排单日期 */
    public Date getPlanDate(){
        return this.planDate;
    }
    /** 排单日期 */
    public void setPlanDate(Date planDate){
        this.planDate=planDate;
    }
    /** 排单重量 */
    public BigDecimal getPlanWeight(){
        return this.planWeight;
    }
    /** 排单重量 */
    public void setPlanWeight(BigDecimal planWeight){
        this.planWeight=planWeight;
    }
    /** 排单数量 */
    public BigDecimal getPlanNum(){
        return this.planNum;
    }
    /** 排单数量 */
    public void setPlanNum(BigDecimal planNum){
        this.planNum=planNum;
    }
    /** 是否完成（0未完成1已完成） */
    public Integer getIsDone(){
        return this.isDone;
    }
    /** 是否完成（0未完成1已完成） */
    public void setIsDone(Integer isDone){
        this.isDone=isDone;
    }
    /** 乐观锁 */
    public Integer getVersion(){
        return this.version;
    }
    /** 乐观锁 */
    public void setVersion(Integer version){
        this.version=version;
    }
    /** 逻辑删除 */
    public Integer getIsDeleted(){
        return this.isDeleted;
    }
    /** 逻辑删除 */
    public void setIsDeleted(Integer isDeleted){
        this.isDeleted=isDeleted;
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
    /** 更新人 */
    public String getUpdateBy(){
        return this.updateBy;
    }
    /** 更新人 */
    public void setUpdateBy(String updateBy){
        this.updateBy=updateBy;
    }
    /** 更新时间 */
    public Date getUpdateTime(){
        return this.updateTime;
    }
    /** 更新时间 */
    public void setUpdateTime(Date updateTime){
        this.updateTime=updateTime;
    }
    /** 预留字段1 */
    public String getAttr1(){
        return this.attr1;
    }
    /** 预留字段1 */
    public void setAttr1(String attr1){
        this.attr1=attr1;
    }
    /** 预留字段2 */
    public Integer getAttr2(){
        return this.attr2;
    }
    /** 预留字段2 */
    public void setAttr2(Integer attr2){
        this.attr2=attr2;
    }
    /** 预留字段3 */
    public String getAttr3(){
        return this.attr3;
    }
    /** 预留字段3 */
    public void setAttr3(String attr3){
        this.attr3=attr3;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("workorderId", getWorkorderId())
                .append("productId", getProductId())
                .append("productCode", getProductCode())
                .append("productName", getProductName())
                .append("productSize", getProductSize())
                .append("routingId", getRoutingId())
                .append("planDate", getPlanDate())
                .append("planWeight", getPlanWeight())
                .append("planNum", getPlanNum())
                .append("isDone", getIsDone())
                .append("version", getVersion())
                .append("isDeleted", getIsDeleted())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("attr1", getAttr1())
                .append("attr2", getAttr2())
                .append("attr3", getAttr3())
                .toString();
    }
}