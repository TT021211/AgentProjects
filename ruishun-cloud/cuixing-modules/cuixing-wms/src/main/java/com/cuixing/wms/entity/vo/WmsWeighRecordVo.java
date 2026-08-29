package com.cuixing.wms.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
@Getter
@Setter
public class WmsWeighRecordVo {
    @JsonProperty("gross_weight")
    private BigDecimal grossWeight;
    @JsonProperty("batch_number2")
    private String batchNumber2;
    @JsonProperty("ss_time")
    private String Sstime;
    @JsonProperty("quality_status")
    private String qualityStatus;
    @JsonProperty("qualified_rate")
    private String QualifiedRate;
    @JsonProperty("id")
    private BigInteger id;

}
