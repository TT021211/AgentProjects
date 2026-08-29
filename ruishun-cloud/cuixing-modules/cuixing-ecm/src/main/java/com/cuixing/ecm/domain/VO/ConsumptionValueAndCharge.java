package com.cuixing.ecm.domain.VO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author zhan
 * @version 1.0
 * @date 2024/8/7 16:31
 */
@ApiModel( description = "电量和电费集")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionValueAndCharge {
    int [] consumptionValues;
    BigDecimal [] electricCharges;
}
