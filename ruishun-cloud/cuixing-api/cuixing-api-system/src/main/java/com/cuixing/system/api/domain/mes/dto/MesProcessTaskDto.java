package com.cuixing.system.api.domain.mes.dto;

import com.cuixing.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class MesProcessTaskDto {
    /** 自增主键 */
    @ApiModelProperty("自增主键")
    @Excel(name = "自增主键", cellType = Excel.ColumnType.NUMERIC, prompt = "自增主键")
    private BigInteger id;
    /** 订单id */
    @ApiModelProperty("订单id")
    @Excel(name = "订单id")
    private BigInteger orderId;
    /** 订单明细id */
    @ApiModelProperty("订单明细id")
    @Excel(name = "订单明细id")
    private BigInteger orderDetailId;
    /** 工单id */
    @ApiModelProperty("工单id")
    @Excel(name = "工单id")
    private BigInteger workorderId;
    /** 工单明细id */
    @ApiModelProperty("工单明细id")
    @Excel(name = "工单明细id")
    private BigInteger workorderEntryId;
    /** 产品id */
    @ApiModelProperty("产品id")
    @Excel(name = "产品id")
    private BigInteger productId;
    /** 任务编码 */
    @ApiModelProperty("任务编码")
    @Excel(name = "任务编码")
    private String taskSn;
    /** 任务名称 */
    @ApiModelProperty("任务名称")
    @Excel(name = "任务名称")
    private String taskName;
    /** 机台id */
    @ApiModelProperty("机台id")
    @Excel(name = "机台id")
    private BigInteger workstationId;
    /** 机台编码 */
    @ApiModelProperty("机台编码")
    @Excel(name = "机台编码")
    private String workstationCode;
    /** 机台名称 */
    @ApiModelProperty("机台名称")
    @Excel(name = "机台名称")
    private String workstationName;
    /** 班组id */
    @ApiModelProperty("班组id")
    @Excel(name = "班组id")
    private BigInteger workgroupId;
    /** 班组编码 */
    @ApiModelProperty("班组编码")
    @Excel(name = "班组编码")
    private String workgroupCode;
    /** 班组名称 */
    @ApiModelProperty("班组名称")
    @Excel(name = "班组名称")
    private String workgroupName;
    /** 工序id */
    @ApiModelProperty("工序id")
    @Excel(name = "工序id")
    private BigInteger workstageId;
    /** 工序名称 */
    @ApiModelProperty("工序名称")
    @Excel(name = "工序名称")
    private String workstageName;
    /** 工艺BOMid（mes_product_bom） */
    @ApiModelProperty("工艺BOMid（mes_product_bom）")
    @Excel(name = "工艺BOMid（mes_product_bom）")
    private BigInteger productBomId;
    /** 参考投料重量（根据成材率反推得出） */
    @ApiModelProperty("参考投料重量（根据成材率反推得出）")
    @Excel(name = "参考投料重量（根据成材率反推得出）")
    private BigDecimal inputWeight;
    /** 参考投料数量 */
    @ApiModelProperty("参考投料数量")
    @Excel(name = "参考投料数量")
    private BigDecimal inputNum;
    /** 排产重量 */
    @ApiModelProperty("排产重量")
    @Excel(name = "排产重量")
    private BigDecimal taskWeight;
    /** 排产数量 */
    @ApiModelProperty("排产数量")
    @Excel(name = "排产数量")
    private BigDecimal taskNum;
    /** 排产日期 */
    @ApiModelProperty("排产日期")
    @Excel(name = "排产日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planDate;
    /** 模具出库通知（0未下发1已下发） */
    @ApiModelProperty("模具出库通知（0未下发1已下发）")
    @Excel(name = "模具出库通知（0未下发1已下发）")
    private Integer mouldStatus;
    /** 任务状态（0未开始1已开始2已完成） */
    @ApiModelProperty("任务状态（0未开始1已开始2已完成）")
    @Excel(name = "任务状态（0未开始1已开始2已完成）")
    private Integer taskStatus;
    /** 是否冻结（0未冻结1已冻结） */
    @ApiModelProperty("是否冻结（0未冻结1已冻结）")
    @Excel(name = "是否冻结（0未冻结1已冻结）")
    private Integer isFrozen;
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

    /** 工序排序 */
    @ApiModelProperty("工序排序")
    @Excel(name = "工序排序")
    private Integer orderNum;

    /** 工序排序标识 */
    @ApiModelProperty("工序排序标识")
    @Excel(name = "工序排序标识")
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

    /** 模具id */
    @ApiModelProperty("模具id")
    @Excel(name = "模具id")
    private BigInteger mouldId;

    /** 模具名称 */
    @ApiModelProperty("模具名称")
    @Excel(name = "模具名称")
    private String mouldName;

    /** 成材率 */
    @ApiModelProperty("成材率")
    @Excel(name = "成材率")
    private BigDecimal productYield;

    /** sop标题 */
    @ApiModelProperty("sop标题")
    @Excel(name = "sop标题")
    private String sopTitle;

    /** sop描述 */
    @ApiModelProperty("sop描述")
    @Excel(name = "sop描述")
    private String sopDesc;

    /** sop地址 */
    @ApiModelProperty("sop地址")
    @Excel(name = "sop地址")
    private String sopUrl;

    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 订单id */
    public BigInteger getOrderId(){
        return this.orderId;
    }
    /** 订单id */
    public void setOrderId(BigInteger orderId){
        this.orderId=orderId;
    }
    /** 订单明细id */
    public BigInteger getOrderDetailId(){
        return this.orderDetailId;
    }
    /** 订单明细id */
    public void setOrderDetailId(BigInteger orderDetailId){
        this.orderDetailId=orderDetailId;
    }
    /** 工单id */
    public BigInteger getWorkorderId(){
        return this.workorderId;
    }
    /** 工单id */
    public void setWorkorderId(BigInteger workorderId){
        this.workorderId=workorderId;
    }
    /** 工单明细id */
    public BigInteger getWorkorderEntryId(){
        return this.workorderEntryId;
    }
    /** 工单明细id */
    public void setWorkorderEntryId(BigInteger workorderEntryId){
        this.workorderEntryId=workorderEntryId;
    }
    /** 产品id */
    public BigInteger getProductId(){
        return this.productId;
    }
    /** 产品id */
    public void setProductId(BigInteger productId){
        this.productId=productId;
    }
    /** 任务编码 */
    public String getTaskSn(){
        return this.taskSn;
    }
    /** 任务编码 */
    public void setTaskSn(String taskSn){
        this.taskSn=taskSn;
    }
    /** 任务名称 */
    public String getTaskName(){
        return this.taskName;
    }
    /** 任务名称 */
    public void setTaskName(String taskName){
        this.taskName=taskName;
    }
    /** 机台id */
    public BigInteger getWorkstationId(){
        return this.workstationId;
    }
    /** 机台id */
    public void setWorkstationId(BigInteger workstationId){
        this.workstationId=workstationId;
    }
    /** 机台编码 */
    public String getWorkstationCode(){
        return this.workstationCode;
    }
    /** 机台编码 */
    public void setWorkstationCode(String workstationCode){
        this.workstationCode=workstationCode;
    }
    /** 机台名称 */
    public String getWorkstationName(){
        return this.workstationName;
    }
    /** 机台名称 */
    public void setWorkstationName(String workstationName){
        this.workstationName=workstationName;
    }
    /** 班组id */
    public BigInteger getWorkgroupId(){
        return this.workgroupId;
    }
    /** 班组id */
    public void setWorkgroupId(BigInteger workgroupId){
        this.workgroupId=workgroupId;
    }
    /** 班组编码 */
    public String getWorkgroupCode(){
        return this.workgroupCode;
    }
    /** 班组编码 */
    public void setWorkgroupCode(String workgroupCode){
        this.workgroupCode=workgroupCode;
    }
    /** 班组名称 */
    public String getWorkgroupName(){
        return this.workgroupName;
    }
    /** 班组名称 */
    public void setWorkgroupName(String workgroupName){
        this.workgroupName=workgroupName;
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
    /** 工艺BOMid（mes_product_bom） */
    public BigInteger getProductBomId(){
        return this.productBomId;
    }
    /** 工艺BOMid（mes_product_bom） */
    public void setProductBomId(BigInteger productBomId){
        this.productBomId=productBomId;
    }
    /** 参考投料重量（根据成材率反推得出） */
    public BigDecimal getInputWeight(){
        return this.inputWeight;
    }
    /** 参考投料重量（根据成材率反推得出） */
    public void setInputWeight(BigDecimal inputWeight){
        this.inputWeight=inputWeight;
    }
    /** 参考投料数量 */
    public BigDecimal getInputNum(){
        return this.inputNum;
    }
    /** 参考投料数量 */
    public void setInputNum(BigDecimal inputNum){
        this.inputNum=inputNum;
    }
    /** 排产重量 */
    public BigDecimal getTaskWeight(){
        return this.taskWeight;
    }
    /** 排产重量 */
    public void setTaskWeight(BigDecimal taskWeight){
        this.taskWeight=taskWeight;
    }
    /** 排产数量 */
    public BigDecimal getTaskNum(){
        return this.taskNum;
    }
    /** 排产数量 */
    public void setTaskNum(BigDecimal taskNum){
        this.taskNum=taskNum;
    }
    /** 排产日期 */
    public Date getPlanDate(){
        return this.planDate;
    }
    /** 排产日期 */
    public void setPlanDate(Date planDate){
        this.planDate=planDate;
    }
    /** 模具出库通知（0未下发1已下发） */
    public Integer getMouldStatus(){
        return this.mouldStatus;
    }
    /** 模具出库通知（0未下发1已下发） */
    public void setMouldStatus(Integer mouldStatus){
        this.mouldStatus=mouldStatus;
    }
    /** 任务状态（0未开始1已开始2已完成） */
    public Integer getTaskStatus(){
        return this.taskStatus;
    }
    /** 任务状态（0未开始1已开始2已完成） */
    public void setTaskStatus(Integer taskStatus){
        this.taskStatus=taskStatus;
    }
    /** 是否冻结（0未冻结1已冻结） */
    public Integer getIsFrozen(){
        return this.isFrozen;
    }
    /** 是否冻结（0未冻结1已冻结） */
    public void setIsFrozen(Integer isFrozen){
        this.isFrozen=isFrozen;
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

    /** orderNum */
    public Integer getOrderNum(){
        return this.orderNum;
    }
    /** orderNum */
    public void setOrderNum(Integer orderNum){
        this.orderNum=orderNum;
    }

    /** orderMark */
    public String getOrderMark(){
        return this.orderMark;
    }
    /** orderMark */
    public void setOrderMark(String orderMark){
        this.orderMark=orderMark;
    }

    /** inputBomId */
    public BigInteger getInputBomId(){
        return this.inputBomId;
    }
    /** inputBomId */
    public void setInputBomId(BigInteger inputBomId){
        this.inputBomId=inputBomId;
    }

    /** inputBomName */
    public String getInputBomName(){
        return this.inputBomName;
    }
    /** inputBomName */
    public void setInputBomName(String inputBomName){
        this.inputBomName=inputBomName;
    }

    /** inputBomCode */
    public String getInputBomCode(){
        return this.inputBomCode;
    }
    /** inputBomCode */
    public void setInputBomCode(String inputBomCode){
        this.inputBomCode=inputBomCode;
    }

    /** outputBomId */
    public BigInteger getOutputBomId(){
        return this.outputBomId;
    }
    /** outputBomId */
    public void setOutputBomId(BigInteger outputBomId){
        this.outputBomId=outputBomId;
    }

    /** outputBomName */
    public String getOutputBomName(){
        return this.outputBomName;
    }
    /** outputBomName */
    public void setOutputBomName(String outputBomName){
        this.outputBomName=outputBomName;
    }

    /** outputBomCode */
    public String getOutputBomCode(){
        return this.outputBomCode;
    }
    /** outputBomCode */
    public void setOutputBomCode(String outputBomCode){
        this.outputBomCode=outputBomCode;
    }

    /** mouldId */
    public BigInteger getMouldId(){
        return this.mouldId;
    }
    /** mouldId */
    public void setMouldId(BigInteger mouldId){
        this.mouldId=mouldId;
    }

    /** mouldName */
    public String getMouldName(){
        return this.mouldName;
    }
    /** mouldName */
    public void setMouldName(String mouldName){
        this.mouldName=mouldName;
    }

    /** productYield */
    public BigDecimal getProductYield(){
        return this.productYield;
    }
    /** productYield */
    public void setProductYield(BigDecimal productYield){
        this.productYield=productYield;
    }

    /** sopTitle */
    public String getSopTitle(){
        return this.sopTitle;
    }
    /** sopTitle */
    public void setSopTitle(String sopTitle){
        this.sopTitle=sopTitle;
    }

    /** sopDesc */
    public String getSopDesc(){
        return this.sopDesc;
    }
    /** sopDesc */
    public void setSopDesc(String sopDesc){
        this.sopDesc=sopDesc;
    }

    /** sopUrl */
    public String getSopUrl(){
        return this.sopUrl;
    }
    /** sopUrl */
    public void setSopUrl(String sopUrl){
        this.sopUrl=sopUrl;
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderId", getOrderId())
                .append("orderDetailId", getOrderDetailId())
                .append("workorderId", getWorkorderId())
                .append("workorderEntryId", getWorkorderEntryId())
                .append("productId", getProductId())
                .append("taskSn", getTaskSn())
                .append("taskName", getTaskName())
                .append("workstationId", getWorkstationId())
                .append("workstationCode", getWorkstationCode())
                .append("workstationName", getWorkstationName())
                .append("workgroupId", getWorkgroupId())
                .append("workgroupCode", getWorkgroupCode())
                .append("workgroupName", getWorkgroupName())
                .append("workstageId", getWorkstageId())
                .append("workstageName", getWorkstageName())
                .append("productBomId", getProductBomId())
                .append("inputWeight", getInputWeight())
                .append("inputNum", getInputNum())
                .append("taskWeight", getTaskWeight())
                .append("taskNum", getTaskNum())
                .append("planDate", getPlanDate())
                .append("mouldStatus", getMouldStatus())
                .append("taskStatus", getTaskStatus())
                .append("isFrozen", getIsFrozen())
                .append("version", getVersion())
                .append("isDeleted", getIsDeleted())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("attr1", getAttr1())
                .append("attr2", getAttr2())
                .append("attr3", getAttr3())
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
                .toString();
    }
}