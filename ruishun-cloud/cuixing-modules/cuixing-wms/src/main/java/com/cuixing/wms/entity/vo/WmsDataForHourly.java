package com.cuixing.wms.entity.vo;

import com.cuixing.common.core.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
public class WmsDataForHourly {
    private String productModel;//产品规格
    private BigDecimal  totalWeight;//总重
    @JsonFormat(pattern = "HH:mm:ss")
    private String date;//创建时间
    private String status;//状态
    private String percentage;//百分比
}
