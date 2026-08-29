package com.cuixing.ecm.domain.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 第三方拉取的智能电表数据
 *
 * @author zhan
 * @version 1.0
 * @date 2024/7/24 16:14
 */
@ApiModel( description = "第三方拉取的智能电表数据")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EcmElectricDeviceData {
    /**
     * 二次测正向有功电能
     */
    @JsonProperty("twiceEPP")
    @ApiModelProperty("二次测正向有功电能")
    private Integer twiceEPP;

    /**
     * 设备类型
     */
    @JsonProperty("itemType")
    @ApiModelProperty("设备类型")
    private String itemType;

    /**
     * 消息类型
     */
    @JsonProperty("msgType")
    @ApiModelProperty("消息类型")
    private String msgType;

    /**
     * 总视在功率
     */
    @JsonProperty("powerSS")
    @ApiModelProperty("总视在功率")
    private Integer powerSS;

    /**
     * 总无功功率
     */
    @JsonProperty("powerQS")
    @ApiModelProperty("总无功功率")
    private Integer powerQS;

    /**
     * 一次测正向有功电能
     */
    @JsonProperty("onceEPP")
    @ApiModelProperty("一次测正向有功电能")
    private Integer onceEPP;

    /**
     * A相有功功率
     */
    @JsonProperty("powerPA")
    @ApiModelProperty("A相有功功率")
    private Integer powerPA;

    /**
     * BC线电压
     */
    @JsonProperty("voltageBC")
    @ApiModelProperty("BC线电压")
    private Double voltageBC;

    /**
     * B相有功功率
     */
    @JsonProperty("powerPB")
    @ApiModelProperty("B相有功功率")
    private Integer powerPB;

    /**
     * C相有功功率
     */
    @JsonProperty("powerPC")
    @ApiModelProperty("C相有功功率")
    private Integer powerPC;

    /**
     * A相电压
     */
    @JsonProperty("voltageA")
    @ApiModelProperty("A相电压")
    private Double voltageA;

    /**
     * B相电压
     */
    @JsonProperty("voltageB")
    @ApiModelProperty("B相电压")
    private Double voltageB;

    /**
     * C相电压
     */
    @JsonProperty("voltageC")
    @ApiModelProperty("C相电压")
    private Double voltageC;

    /**
     * C相功率因数
     */
    @JsonProperty("powerFactorC")
    @ApiModelProperty("C相功率因数")
    private Integer powerFactorC;

    /**
     * C相电流
     */
    @JsonProperty("currentC")
    @ApiModelProperty("C相电流")
    private Integer currentC;

    /**
     * B相功率因数
     */
    @JsonProperty("powerFactorB")
    @ApiModelProperty("B相功率因数")
    private Integer powerFactorB;

    /**
     * B相电流
     */
    @JsonProperty("currentB")
    @ApiModelProperty("B相电流有效值")
    private Integer currentB;

    /**
     * A相功率因数
     */
    @JsonProperty("powerFactorA")
    @ApiModelProperty("A相功率因数")
    private Integer powerFactorA;

    /**
     * A相电流
     */
    @JsonProperty("currentA")
    @ApiModelProperty("A相电流有效值")
    private Integer currentA;

    /**
     * 命令类型
     */
    @JsonProperty("cmdType")
    @ApiModelProperty("命令类型")
    private String cmdType;

    /**
     * 总有功功率
     */
    @JsonProperty("powerPS")
    @ApiModelProperty("总有功功率")
    private Integer powerPS;

    /**
     * 消息类型名称
     */
    @JsonProperty("msgTypeName")
    @ApiModelProperty("消息类型名称")
    private String msgTypeName;

    /**
     * 控制器标识符
     */
    @JsonProperty("deviceCode")
    @ApiModelProperty("控制器标识符")
    private String deviceCode;

    /**
     * 总功率因数
     */
    @JsonProperty("powerFactorS")
    @ApiModelProperty("总功率因数")
    private Integer powerFactorS;

    /**
     * 电表标识符
     */
    @JsonProperty("circuitCode")
    @ApiModelProperty("电路代码")
    private String circuitCode;

    /**
     * 物业公司标识符
     */
    @JsonProperty("propertyCompanyId")
    @ApiModelProperty("物业公司标识符")
    private String propertyCompanyId;

    /**
     * A相视在功率
     */
    @JsonProperty("powerSA")
    @ApiModelProperty("A相视在功率")
    private Integer powerSA;

    /**
     * CA线电压
     */
    @JsonProperty("voltageCA")
    @ApiModelProperty("CA线电压")
    private Double voltageCA;

    /**
     * B相视在功率
     */
    @JsonProperty("powerSB")
    @ApiModelProperty("B相视在功率")
    private Integer powerSB;

    /**
     * 创建时间
     */
    @JsonProperty("createTime")
    @ApiModelProperty("创建时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * C相视在功率
     */
    @JsonProperty("powerQA")
    @ApiModelProperty("A相无功功率")
    private Integer powerQA;

    /**
     * AC线电压
     */
    @JsonProperty("powerSC")
    @ApiModelProperty("C相视在功率")
    private Integer powerSC;

    /**
     * BC线电压
     */
    @JsonProperty("powerQB")
    @ApiModelProperty("B相无功功率")
    private Integer powerQB;

    /**
     * AB线电压
     */
    @JsonProperty("voltageAB")
    @ApiModelProperty("AB线电压")
    private Double voltageAB;

    /**
     * AC线电压
     */
    @JsonProperty("powerQC")
    @ApiModelProperty("C相无功功率")
    private Integer powerQC;

    /**
     * 命令
     */
    @JsonProperty("cmd")
    @ApiModelProperty("命令")
    private String cmd;
}
