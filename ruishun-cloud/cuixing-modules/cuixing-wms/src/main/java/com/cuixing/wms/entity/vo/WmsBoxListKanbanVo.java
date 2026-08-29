package com.cuixing.wms.entity.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Setter
@Getter
public class WmsBoxListKanbanVo {
    private BigInteger id;
    private String batchNumber;
    private String wmsSpec;
    private Integer countNumber;
    private BigDecimal sumWeight;


}
