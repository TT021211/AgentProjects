package com.cuixing.md.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import com.cuixing.common.core.web.domain.BaseEntity;
import java.math.BigInteger;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;

/**
 * 机台表;
 * @author : cuixing-keyun-autogen
 * @date : 2024-3-20
 */
@ApiModel(value = "机台表",description = "")
public class MdWorkstation extends BaseEntity{
    /** 工作站ID */
    @ApiModelProperty(name = "工作站ID",notes = "")
    private BigInteger id ;
    /** 工作站编码 */
    @ApiModelProperty(name = "工作站编码",notes = "")
    private String workstationCode ;
    /** 工作站名称 */
    @ApiModelProperty(name = "工作站名称",notes = "")
    private String workstationName ;
    /** 工作站地点 */
    @ApiModelProperty(name = "工作站地点",notes = "")
    private String workstationAddress ;
    /** 所在车间ID */
    @ApiModelProperty(name = "所在车间ID",notes = "")
    private BigInteger workshopId ;
    /** 所在车间编码 */
    @ApiModelProperty(name = "所在车间编码",notes = "")
    private String workshopCode ;
    /** 所在车间名称 */
    @ApiModelProperty(name = "所在车间名称",notes = "")
    private String workshopName ;
    /** 工序ID */
    @ApiModelProperty(name = "工序ID",notes = "")
    private BigInteger workstageId ;
    /** 工序编码 */
    @ApiModelProperty(name = "工序编码",notes = "")
    private String workstageCode ;
    /** 工序名称 */
    @ApiModelProperty(name = "工序名称",notes = "")
    private String workstageName ;
    /** 线边库ID */
    @ApiModelProperty(name = "线边库ID",notes = "")
    private BigInteger warehouseId ;
    /** 线边库编码 */
    @ApiModelProperty(name = "线边库编码",notes = "")
    private String warehouseCode ;
    /** 线边库名称 */
    @ApiModelProperty(name = "线边库名称",notes = "")
    private String warehouseName ;
    /** 库区ID */
    @ApiModelProperty(name = "库区ID",notes = "")
    private BigInteger locationId ;
    /** 库区编码 */
    @ApiModelProperty(name = "库区编码",notes = "")
    private String locationCode ;
    /** 库区名称 */
    @ApiModelProperty(name = "库区名称",notes = "")
    private String locationName ;
    /** 库位ID */
    @ApiModelProperty(name = "库位ID",notes = "")
    private BigInteger areaId ;
    /** 库位编码 */
    @ApiModelProperty(name = "库位编码",notes = "")
    private String areaCode ;
    /** 库位名称 */
    @ApiModelProperty(name = "库位名称",notes = "")
    private String areaName ;
    /** 是否启用 */
    @ApiModelProperty(name = "是否启用",notes = "")
    private Integer ifEnable ;
    /** 负责人 */
    @ApiModelProperty(name = "负责人",notes = "")
    private String head ;
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remark ;
    /** 预留字段1 */
    @ApiModelProperty(name = "预留字段1",notes = "")
    private String attr1 ;
    /** 预留字段2 */
    @ApiModelProperty(name = "预留字段2",notes = "")
    private Integer attr2 ;
    /** 预留字段3 */
    @ApiModelProperty(name = "预留字段3",notes = "")
    private String attr3 ;
    /** 乐观锁 */
    @ApiModelProperty(name = "乐观锁",notes = "")
    private Integer version ;
    /** 逻辑删除 */
    @ApiModelProperty(name = "逻辑删除",notes = "")
    private Integer isDeleted ;
    /** 创建者 */
    @ApiModelProperty(name = "创建者",notes = "")
    private String createBy ;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间",notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime ;
    /** 更新者 */
    @ApiModelProperty(name = "更新者",notes = "")
    private String updateBy ;
    /** 更新时间 */
    @ApiModelProperty(name = "更新时间",notes = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime ;

    /** 工作站ID */
    public BigInteger getId(){
        return this.id;
    }
    /** 工作站ID */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 工作站编码 */
    public String getWorkstationCode(){
        return this.workstationCode;
    }
    /** 工作站编码 */
    public void setWorkstationCode(String workstationCode){
        this.workstationCode=workstationCode;
    }
    /** 工作站名称 */
    public String getWorkstationName(){
        return this.workstationName;
    }
    /** 工作站名称 */
    public void setWorkstationName(String workstationName){
        this.workstationName=workstationName;
    }
    /** 工作站地点 */
    public String getWorkstationAddress(){
        return this.workstationAddress;
    }
    /** 工作站地点 */
    public void setWorkstationAddress(String workstationAddress){
        this.workstationAddress=workstationAddress;
    }
    /** 所在车间ID */
    public BigInteger getWorkshopId(){
        return this.workshopId;
    }
    /** 所在车间ID */
    public void setWorkshopId(BigInteger workshopId){
        this.workshopId=workshopId;
    }
    /** 所在车间编码 */
    public String getWorkshopCode(){
        return this.workshopCode;
    }
    /** 所在车间编码 */
    public void setWorkshopCode(String workshopCode){
        this.workshopCode=workshopCode;
    }
    /** 所在车间名称 */
    public String getWorkshopName(){
        return this.workshopName;
    }
    /** 所在车间名称 */
    public void setWorkshopName(String workshopName){
        this.workshopName=workshopName;
    }
    /** 工序ID */
    public BigInteger getWorkstageId(){
        return this.workstageId;
    }
    /** 工序ID */
    public void setWorkstageId(BigInteger workstageId){
        this.workstageId=workstageId;
    }
    /** 工序编码 */
    public String getWorkstageCode(){
        return this.workstageCode;
    }
    /** 工序编码 */
    public void setWorkstageCode(String workstageCode){
        this.workstageCode=workstageCode;
    }
    /** 工序名称 */
    public String getWorkstageName(){
        return this.workstageName;
    }
    /** 工序名称 */
    public void setWorkstageName(String workstageName){
        this.workstageName=workstageName;
    }
    /** 线边库ID */
    public BigInteger getWarehouseId(){
        return this.warehouseId;
    }
    /** 线边库ID */
    public void setWarehouseId(BigInteger warehouseId){
        this.warehouseId=warehouseId;
    }
    /** 线边库编码 */
    public String getWarehouseCode(){
        return this.warehouseCode;
    }
    /** 线边库编码 */
    public void setWarehouseCode(String warehouseCode){
        this.warehouseCode=warehouseCode;
    }
    /** 线边库名称 */
    public String getWarehouseName(){
        return this.warehouseName;
    }
    /** 线边库名称 */
    public void setWarehouseName(String warehouseName){
        this.warehouseName=warehouseName;
    }
    /** 库区ID */
    public BigInteger getLocationId(){
        return this.locationId;
    }
    /** 库区ID */
    public void setLocationId(BigInteger locationId){
        this.locationId=locationId;
    }
    /** 库区编码 */
    public String getLocationCode(){
        return this.locationCode;
    }
    /** 库区编码 */
    public void setLocationCode(String locationCode){
        this.locationCode=locationCode;
    }
    /** 库区名称 */
    public String getLocationName(){
        return this.locationName;
    }
    /** 库区名称 */
    public void setLocationName(String locationName){
        this.locationName=locationName;
    }
    /** 库位ID */
    public BigInteger getAreaId(){
        return this.areaId;
    }
    /** 库位ID */
    public void setAreaId(BigInteger areaId){
        this.areaId=areaId;
    }
    /** 库位编码 */
    public String getAreaCode(){
        return this.areaCode;
    }
    /** 库位编码 */
    public void setAreaCode(String areaCode){
        this.areaCode=areaCode;
    }
    /** 库位名称 */
    public String getAreaName(){
        return this.areaName;
    }
    /** 库位名称 */
    public void setAreaName(String areaName){
        this.areaName=areaName;
    }
    /** 是否启用 */
    public Integer getIfEnable(){
        return this.ifEnable;
    }
    /** 是否启用 */
    public void setIfEnable(Integer ifEnable){
        this.ifEnable=ifEnable;
    }
    /** 负责人 */
    public String getHead(){
        return this.head;
    }
    /** 负责人 */
    public void setHead(String head){
        this.head=head;
    }
    /** 备注 */
    public String getRemark(){
        return this.remark;
    }
    /** 备注 */
    public void setRemark(String remark){
        this.remark=remark;
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
    /** 创建者 */
    public String getCreateBy(){
        return this.createBy;
    }
    /** 创建者 */
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
    /** 更新者 */
    public String getUpdateBy(){
        return this.updateBy;
    }
    /** 更新者 */
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("workstationCode", getWorkstationCode())
                .append("workstationName", getWorkstationName())
                .append("workstationAddress", getWorkstationAddress())
                .append("workshopId", getWorkshopId())
                .append("workshopCode", getWorkshopCode())
                .append("workshopName", getWorkshopName())
                .append("workstageId", getWorkstageId())
                .append("workstageCode", getWorkstageCode())
                .append("workstageName", getWorkstageName())
                .append("warehouseId", getWarehouseId())
                .append("warehouseCode", getWarehouseCode())
                .append("warehouseName", getWarehouseName())
                .append("locationId", getLocationId())
                .append("locationCode", getLocationCode())
                .append("locationName", getLocationName())
                .append("areaId", getAreaId())
                .append("areaCode", getAreaCode())
                .append("areaName", getAreaName())
                .append("ifEnable", getIfEnable())
                .append("head", getHead())
                .append("remark", getRemark())
                .append("attr1", getAttr1())
                .append("attr2", getAttr2())
                .append("attr3", getAttr3())
                .append("version", getVersion())
                .append("isDeleted", getIsDeleted())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}