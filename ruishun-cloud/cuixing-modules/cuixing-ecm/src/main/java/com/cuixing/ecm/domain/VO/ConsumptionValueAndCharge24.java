package com.cuixing.ecm.domain.VO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@ApiModel( description = "24小时电量")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionValueAndCharge24 {

    String electricDeviceCode;
    String electricDeviceName;
    String remark;
    int [] consumptionValues;
    BigDecimal[] electricCharges;
}
