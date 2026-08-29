package com.cuixing.mes.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.math.BigInteger;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import com.cuixing.common.core.annotation.Excel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 工序任务参数;
 */
@ApiModel(description = "工序任务参数")
public class MesProcessTaskVo implements Serializable {

    /** 工单id */
    @ApiModelProperty("工单id")
    @Excel(name = "工单id")
    @NotNull(message = "工单id不能为空")
    private BigInteger workorderId;

    /** 工单明细id */
    @ApiModelProperty("工单明细id")
    @Excel(name = "工单明细id")
    @NotNull(message = "工单i明细d不能为空")
    private BigInteger workorderEntryId;

    /** 产品id */
    @ApiModelProperty("产品id")
    @Excel(name = "产品id")
    @NotNull(message = "产品id不能为空")
    private BigInteger productId;

    /** 机台id */
    @ApiModelProperty("机台id")
    @Excel(name = "机台id")
    @NotNull(message = "机台id不能为空")
    private BigInteger workstationId;
    /** 机台编码 */
    @ApiModelProperty("机台编码")
    @Excel(name = "机台编码")
    @NotNull(message = "机台编码不能为空")
    private String workstationCode;
    /** 机台名称 */
    @ApiModelProperty("机台名称")
    @Excel(name = "机台名称")
    @NotNull(message = "机台名称不能为空")
    private String workstationName;
    /** 班组id */
    @ApiModelProperty("班组id")
    @Excel(name = "班组id")
    @NotNull(message = "班组id不能为空")
    private BigInteger workgroupId;
    /** 班组编码 */
    @ApiModelProperty("班组编码")
    @Excel(name = "班组编码")
    @NotNull(message = "班组编码不能为空")
    private String workgroupCode;
    /** 班组名称 */
    @ApiModelProperty("班组名称")
    @Excel(name = "班组名称")
    @NotNull(message = "班组名称不能为空")
    private String workgroupName;
    /** 工艺BOMid（mes_product_bom） */
    @ApiModelProperty("工艺BOMid（mes_product_bom）")
    @Excel(name = "工艺BOMid（mes_product_bom）")
    @NotNull(message = "工艺BOMid不能为空")
    private BigInteger productBomId;

    @ApiModelProperty("排产重量")
    @Excel(name = "排产重量")
    @NotNull(message = "排产重量不能为空")
    private BigDecimal taskWeight;

    @ApiModelProperty("排产数量")
    @Excel(name = "排产数量")
    private BigDecimal taskNum;

    @ApiModelProperty("参考投料重量")
    @Excel(name = "参考投料重量")
    @NotNull(message = "参考投料重量不能为空")
    private BigDecimal inputWeight;

    @ApiModelProperty("参考投料数量")
    @Excel(name = "参考投料数量")
    private BigDecimal inputNum;




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
    /** 工艺BOMid（mes_product_bom） */
    public BigInteger getProductBomId(){
        return this.productBomId;
    }
    /** 工艺BOMid（mes_product_bom） */
    public void setProductBomId(BigInteger productBomId){
        this.productBomId=productBomId;
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


    /** 参考投料重量 */
    public BigDecimal getInputWeight(){
        return this.inputWeight;
    }
    /** 参考投料重量 */
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


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("workorderId", getWorkorderId())
                .append("workorderEntryId", getWorkorderEntryId())
                .append("productId", getProductId())
                .append("workstationId", getWorkstationId())
                .append("workstationCode", getWorkstationCode())
                .append("workstationName", getWorkstationName())
                .append("workgroupId", getWorkgroupId())
                .append("workgroupCode", getWorkgroupCode())
                .append("workgroupName", getWorkgroupName())
                .append("productBomId", getProductBomId())
                .append("taskWeight", getTaskWeight())
                .append("taskNum", getTaskNum())
                .append("inputWeight", getInputWeight())
                .append("inputNum", getInputNum())
                .toString();
    }
}
