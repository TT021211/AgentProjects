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
 * 产品BOM表;
 * @author : cuixing-keyun-autogen
 * @date : 2024-5-22
 */
@ApiModel(description = "产品BOM表")
public class MesProductBom extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty("自增主键")
    @Excel(name = "自增主键", cellType = Excel.ColumnType.NUMERIC, prompt = "自增主键")
    private BigInteger id;
    /** 工艺路线id */
    @ApiModelProperty("工艺路线id")
    @Excel(name = "工艺路线id")
    private BigInteger routingId;
    /** 工艺路线名称 */
    @ApiModelProperty("工艺路线名称")
    @Excel(name = "工艺路线名称")
    private String routingName;
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
    /** 工艺与工序关联表的自增id */
    @ApiModelProperty("工艺与工序关联表的自增id")
    @Excel(name = "工艺与工序关联表的自增id")
    private BigInteger routingWorkstageId;
    /** 工序id */
    @ApiModelProperty("工序id")
    @Excel(name = "工序id")
    private BigInteger workstageId;
    /** 工序名称 */
    @ApiModelProperty("工序名称")
    @Excel(name = "工序名称")
    private String workstageName;
    /** 工序编码 */
    @ApiModelProperty("工序编码")
    @Excel(name = "工序编码")
    private String workstageCode;
    /** 排序 */
    @ApiModelProperty("排序")
    @Excel(name = "排序")
    private Integer orderNum;
    /** 排序标识(first、last) */
    @ApiModelProperty("排序标识(first、last)")
    @Excel(name = "排序标识(first、last)")
    private String orderMark;
    /** 投料id */
    @ApiModelProperty("投料id")
    @Excel(name = "投料id")
    private BigInteger inputBomId;
    /** 投料名称 */
    @ApiModelProperty("投料名称")
    @Excel(name = "投料名称")
    private String inputBomName;
    /** 投料编码 */
    @ApiModelProperty("投料编码")
    @Excel(name = "投料编码")
    private String inputBomCode;
    /** 产出id */
    @ApiModelProperty("产出id")
    @Excel(name = "产出id")
    private BigInteger outputBomId;
    /** 产出名称 */
    @ApiModelProperty("产出名称")
    @Excel(name = "产出名称")
    private String outputBomName;
    /** 产出编码 */
    @ApiModelProperty("产出编码")
    @Excel(name = "产出编码")
    private String outputBomCode;
    /** 使用模具ID */
    @ApiModelProperty("使用模具ID")
    @Excel(name = "使用模具ID")
    private BigInteger mouldId;
    /** 使用模具名称 */
    @ApiModelProperty("使用模具名称")
    @Excel(name = "使用模具名称")
    private String mouldName;
    /** 成材率 */
    @ApiModelProperty("成材率")
    @Excel(name = "成材率")
    private BigDecimal productYield;
    /** 图纸名称 */
    @ApiModelProperty("图纸名称")
    @Excel(name = "图纸名称")
    private String sopTitle;
    /** 图纸描述 */
    @ApiModelProperty("图纸描述")
    @Excel(name = "图纸描述")
    private String sopDesc;
    /** 图纸地址 */
    @ApiModelProperty("图纸地址")
    @Excel(name = "图纸地址")
    private String sopUrl;
    /** 其他参数 */
    @ApiModelProperty("其他参数")
    @Excel(name = "其他参数")
    private String otherParams;
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
    /** 工艺路线id */
    public BigInteger getRoutingId(){
        return this.routingId;
    }
    /** 工艺路线id */
    public void setRoutingId(BigInteger routingId){
        this.routingId=routingId;
    }
    /** 工艺路线名称 */
    public String getRoutingName(){
        return this.routingName;
    }
    /** 工艺路线名称 */
    public void setRoutingName(String routingName){
        this.routingName=routingName;
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
    /** 工艺与工序关联表的自增id */
    public BigInteger getRoutingWorkstageId(){
        return this.routingWorkstageId;
    }
    /** 工艺与工序关联表的自增id */
    public void setRoutingWorkstageId(BigInteger routingWorkstageId){
        this.routingWorkstageId=routingWorkstageId;
    }
    /** 工序id */
    public BigInteger getWorkstageId(){
        return this.workstageId;
    }
    /** 工序id */
    public void setWorkstageId(BigInteger workstageId){
        this.workstageId=workstageId;
    }
    /** 工序名称 */
    public String getWorkstageName(){
        return this.workstageName;
    }
    /** 工序名称 */
    public void setWorkstageName(String workstageName){
        this.workstageName=workstageName;
    }
    /** 工序编码 */
    public String getWorkstageCode(){
        return this.workstageCode;
    }
    /** 工序编码 */
    public void setWorkstageCode(String workstageCode){
        this.workstageCode=workstageCode;
    }
    /** 排序 */
    public Integer getOrderNum(){
        return this.orderNum;
    }
    /** 排序 */
    public void setOrderNum(Integer orderNum){
        this.orderNum=orderNum;
    }
    /** 排序标识(first、last) */
    public String getOrderMark(){
        return this.orderMark;
    }
    /** 排序标识(first、last) */
    public void setOrderMark(String orderMark){
        this.orderMark=orderMark;
    }
    /** 投料id */
    public BigInteger getInputBomId(){
        return this.inputBomId;
    }
    /** 投料id */
    public void setInputBomId(BigInteger inputBomId){
        this.inputBomId=inputBomId;
    }
    /** 投料名称 */
    public String getInputBomName(){
        return this.inputBomName;
    }
    /** 投料名称 */
    public void setInputBomName(String inputBomName){
        this.inputBomName=inputBomName;
    }
    /** 投料编码 */
    public String getInputBomCode(){
        return this.inputBomCode;
    }
    /** 投料编码 */
    public void setInputBomCode(String inputBomCode){
        this.inputBomCode=inputBomCode;
    }
    /** 产出id */
    public BigInteger getOutputBomId(){
        return this.outputBomId;
    }
    /** 产出id */
    public void setOutputBomId(BigInteger outputBomId){
        this.outputBomId=outputBomId;
    }
    /** 产出名称 */
    public String getOutputBomName(){
        return this.outputBomName;
    }
    /** 产出名称 */
    public void setOutputBomName(String outputBomName){
        this.outputBomName=outputBomName;
    }
    /** 产出编码 */
    public String getOutputBomCode(){
        return this.outputBomCode;
    }
    /** 产出编码 */
    public void setOutputBomCode(String outputBomCode){
        this.outputBomCode=outputBomCode;
    }
    /** 使用模具ID */
    public BigInteger getMouldId(){
        return this.mouldId;
    }
    /** 使用模具ID */
    public void setMouldId(BigInteger mouldId){
        this.mouldId=mouldId;
    }
    /** 使用模具名称 */
    public String getMouldName(){
        return this.mouldName;
    }
    /** 使用模具名称 */
    public void setMouldName(String mouldName){
        this.mouldName=mouldName;
    }
    /** 成材率 */
    public BigDecimal getProductYield(){
        return this.productYield;
    }
    /** 成材率 */
    public void setProductYield(BigDecimal productYield){
        this.productYield=productYield;
    }
    /** 图纸名称 */
    public String getSopTitle(){
        return this.sopTitle;
    }
    /** 图纸名称 */
    public void setSopTitle(String sopTitle){
        this.sopTitle=sopTitle;
    }
    /** 图纸描述 */
    public String getSopDesc(){
        return this.sopDesc;
    }
    /** 图纸描述 */
    public void setSopDesc(String sopDesc){
        this.sopDesc=sopDesc;
    }
    /** 图纸地址 */
    public String getSopUrl(){
        return this.sopUrl;
    }
    /** 图纸地址 */
    public void setSopUrl(String sopUrl){
        this.sopUrl=sopUrl;
    }
    /** 其他参数 */
    public String getOtherParams(){
        return this.otherParams;
    }
    /** 其他参数 */
    public void setOtherParams(String otherParams){
        this.otherParams=otherParams;
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
                .append("routingId", getRoutingId())
                .append("routingName", getRoutingName())
                .append("productId", getProductId())
                .append("productCode", getProductCode())
                .append("productName", getProductName())
                .append("routingWorkstageId", getRoutingWorkstageId())
                .append("workstageId", getWorkstageId())
                .append("workstageName", getWorkstageName())
                .append("workstageCode", getWorkstageCode())
                .append("orderNum", getOrderNum())
                .append("orderMark", getOrderMark())
                .append("inputBomId", getInputBomId())
                .append("inputBomName", getInputBomName())
                .append("inputBomCode", getInputBomCode())
                .append("outputBomId", getOutputBomId())
                .append("outputBomName", getOutputBomName())
                .append("outputBomCode", getOutputBomCode())
                .append("mouldId", getMouldId())
                .append("mouldName", getMouldName())
                .append("productYield", getProductYield())
                .append("sopTitle", getSopTitle())
                .append("sopDesc", getSopDesc())
                .append("sopUrl", getSopUrl())
                .append("otherParams", getOtherParams())
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