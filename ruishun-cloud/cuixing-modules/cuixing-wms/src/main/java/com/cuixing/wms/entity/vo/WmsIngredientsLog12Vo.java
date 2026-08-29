package com.cuixing.wms.entity.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class WmsIngredientsLog12Vo {

    private String month;

    private BigDecimal totalInboundWeight;

    private Integer totalInboundNum;

    private BigDecimal totalOutboundWeight;

    private Integer totalOutboundNum;

}
