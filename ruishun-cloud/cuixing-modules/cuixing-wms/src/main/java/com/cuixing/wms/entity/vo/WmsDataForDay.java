package com.cuixing.wms.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WmsDataForDay {
    private String productModel;//产品规格
    private BigDecimal  totalWeight;//总重
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String date;//创建时间
    private String status;//状态
    private String percentage;//百分比
}
