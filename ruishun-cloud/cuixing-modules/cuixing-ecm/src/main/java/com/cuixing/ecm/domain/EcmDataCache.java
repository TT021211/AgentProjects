package com.cuixing.ecm.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.cuixing.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.math.BigInteger;
import java.util.Date;

 /**
 * 用电量数据缓存;
 * @author : http://www.chiner.pro
 * @date : 2025-2-13
 */
@ApiModel(value = "用电量数据缓存",description = "")
public class EcmDataCache extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键",notes = "")
    private BigInteger id ;
    /** 电表编码 */
    @ApiModelProperty(name = "电表编码",notes = "")
    private String electricDeviceCode ;
    /** 类型(年、月、季) */
    @ApiModelProperty(name = "类型(年、月、季)",notes = "")
    private String type ;
    /** 时间 */
    @ApiModelProperty(name = "时间",notes = "")
    private String date ;
    /** 值 */
    @ApiModelProperty(name = "值",notes = "")
    private Double value ;
    /** 电费 */
    @ApiModelProperty(name = "电费",notes = "")
    private Double electricCharge ;

    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 电表编码 */
    public String getElectricDeviceCode(){
        return this.electricDeviceCode;
    }
    /** 电表编码 */
    public void setElectricDeviceCode(String electricDeviceCode){
        this.electricDeviceCode=electricDeviceCode;
    }
    /** 类型(年、月、季) */
    public String getType(){
        return this.type;
    }
    /** 类型(年、月、季) */
    public void setType(String type){
        this.type=type;
    }
    /** 时间 */
    public String getDate(){
        return this.date;
    }
    /** 时间 */
    public void setDate(String date){
        this.date=date;
    }
    /** 值 */
    public Double getValue(){
        return this.value;
    }
    /** 值 */
    public void setValue(Double value){
        this.value=value;
    }
    /** 电费 */
    public Double getElectricCharge(){
        return this.electricCharge;
    }
    /** 电费 */
    public void setElectricCharge(Double electricCharge){
        this.electricCharge=electricCharge;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("electricDeviceCode", getElectricDeviceCode())
            .append("type", getType())
            .append("date", getDate())
            .append("value", getValue())
            .append("electricCharge", getElectricCharge())
        .toString();
    }
}