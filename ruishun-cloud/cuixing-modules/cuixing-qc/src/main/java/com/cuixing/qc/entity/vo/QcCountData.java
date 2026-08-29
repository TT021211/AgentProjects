package com.cuixing.qc.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class QcCountData {
    //月份
    private String month;
    //当月总数
    private Integer totalCount;
    //统计合格产品数
    private Integer qualifiedCount;
    //统计不合格产品数
    private Integer unqualifiedCount;
    //统计合格率
    private Double qualifiedRate;
    //统计不合格率
    private Double unqualifiedRate;
}
