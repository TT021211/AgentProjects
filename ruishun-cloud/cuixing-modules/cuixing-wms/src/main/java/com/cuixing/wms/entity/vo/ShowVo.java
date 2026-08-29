package com.cuixing.wms.entity.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class ShowVo {
    private List<String> wmsSpecs;
    private Date date;
    private String type;
}
