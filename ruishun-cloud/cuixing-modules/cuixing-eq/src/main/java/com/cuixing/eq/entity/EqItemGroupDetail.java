package com.cuixing.eq.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.cuixing.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.math.BigInteger;
import java.util.Date;

 /**
 * 组别详情;
 * @author : http://www.chiner.pro
 * @date : 2024-7-5
 */
@ApiModel(value = "组别详情")
public class EqItemGroupDetail extends BaseEntity{
    /** 自增主键 */
    @ApiModelProperty(name = "自增主键")
    private BigInteger id ;
    /** 所属组别id */
    @ApiModelProperty(name = "所属组别id")
    private BigInteger groupId ;
    /** 所属组别名称 */
    @ApiModelProperty(name = "所属组别名称")
    private String groupName ;
    /** 负责人id */
    @ApiModelProperty(name = "负责人id")
    private BigInteger headId ;
    /** 负责人 */
    @ApiModelProperty(name = "负责人")
    private String headName ;
    /** 设备id */
    @ApiModelProperty(name = "设备id")
    private BigInteger equipmentId ;
    /** 设备名称 */
    @ApiModelProperty(name = "设备名称")
    private String equipmentName ;
    /** 设备编码 */
    @ApiModelProperty(name = "设备编码")
    private String equipmentCode ;
    /** 设备类型id */
    @ApiModelProperty(name = "设备类型id")
    private BigInteger typeId ;
    /** 设备类型名称 */
    @ApiModelProperty(name = "设备类型名称")
    private String typeName ;
    /** 位置 */
    @ApiModelProperty(name = "位置")
    private String position ;
    /** 设备型号 */
    @ApiModelProperty(name = "设备型号")
    private String equipmentModel ;
    /** 逻辑删除 */
    @ApiModelProperty(name = "逻辑删除")
    private Integer isDeleted ;
    /** 乐观锁 */
    @ApiModelProperty(name = "乐观锁")
    private Integer version ;
    /** 创建人 */
    @ApiModelProperty(name = "创建人")
    private String createBy ;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime ;
    /** 更新人 */
    @ApiModelProperty(name = "更新人")
    private String updateBy ;
    /** 更新时间 */
    @ApiModelProperty(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime ;
    /** 预留字段1 */
    @ApiModelProperty(name = "预留字段1")
    private String attr1 ;
    /** 预留字段2 */
    @ApiModelProperty(name = "预留字段2")
    private Integer attr2 ;
    /** 预留字段3 */
    @ApiModelProperty(name = "预留字段3")
    private String attr3 ;

    /** 自增主键 */
    public BigInteger getId(){
        return this.id;
    }
    /** 自增主键 */
    public void setId(BigInteger id){
        this.id=id;
    }
    /** 所属组别id */
    public BigInteger getGroupId(){
        return this.groupId;
    }
    /** 所属组别id */
    public void setGroupId(BigInteger groupId){
        this.groupId=groupId;
    }
    /** 所属组别名称 */
    public String getGroupName(){
        return this.groupName;
    }
    /** 所属组别名称 */
    public void setGroupName(String groupName){
        this.groupName=groupName;
    }
    /** 负责人id */
    public BigInteger getHeadId(){
        return this.headId;
    }
    /** 负责人id */
    public void setHeadId(BigInteger headId){
        this.headId=headId;
    }
    /** 负责人 */
    public String getHeadName(){
        return this.headName;
    }
    /** 负责人 */
    public void setHeadName(String headName){
        this.headName=headName;
    }
    /** 设备id */
    public BigInteger getEquipmentId(){
        return this.equipmentId;
    }
    /** 设备id */
    public void setEquipmentId(BigInteger equipmentId){
        this.equipmentId=equipmentId;
    }
    /** 设备名称 */
    public String getEquipmentName(){
        return this.equipmentName;
    }
    /** 设备名称 */
    public void setEquipmentName(String equipmentName){
        this.equipmentName=equipmentName;
    }
    /** 设备编码 */
    public String getEquipmentCode(){
        return this.equipmentCode;
    }
    /** 设备编码 */
    public void setEquipmentCode(String equipmentCode){
        this.equipmentCode=equipmentCode;
    }
    /** 设备类型id */
    public BigInteger getTypeId(){
        return this.typeId;
    }
    /** 设备类型id */
    public void setTypeId(BigInteger typeId){
        this.typeId=typeId;
    }
    /** 设备类型名称 */
    public String getTypeName(){
        return this.typeName;
    }
    /** 设备类型名称 */
    public void setTypeName(String typeName){
        this.typeName=typeName;
    }
    /** 位置 */
    public String getPosition(){
        return this.position;
    }
    /** 位置 */
    public void setPosition(String position){
        this.position=position;
    }
    /** 设备型号 */
    public String getEquipmentModel(){
        return this.equipmentModel;
    }
    /** 设备型号 */
    public void setEquipmentModel(String equipmentModel){
        this.equipmentModel=equipmentModel;
    }
    /** 逻辑删除 */
    public Integer getIsDeleted(){
        return this.isDeleted;
    }
    /** 逻辑删除 */
    public void setIsDeleted(Integer isDeleted){
        this.isDeleted=isDeleted;
    }
    /** 乐观锁 */
    public Integer getVersion(){
        return this.version;
    }
    /** 乐观锁 */
    public void setVersion(Integer version){
        this.version=version;
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
            .append("groupId", getGroupId())
            .append("groupName", getGroupName())
            .append("headId", getHeadId())
            .append("headName", getHeadName())
            .append("equipmentId", getEquipmentId())
            .append("equipmentName", getEquipmentName())
            .append("equipmentCode", getEquipmentCode())
            .append("typeId", getTypeId())
            .append("typeName", getTypeName())
            .append("position", getPosition())
            .append("equipmentModel", getEquipmentModel())
            .append("isDeleted", getIsDeleted())
            .append("version", getVersion())
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