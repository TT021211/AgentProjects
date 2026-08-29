package com.cuixing.mes.entity.mqtt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@lombok.Data
// 表示整个 InfluxDB 查询结果
public class InfluxQueryResult {
    // Getter 和 Setter
    // 查询状态
    @JsonProperty("status")
    private String status;

    // 查询语句
    @JsonProperty("query")
    private String query;

    // 数据列表
    @JsonProperty("data")
    private List<Data> data;


    // 表示单个数据集合
    @lombok.Data
    public static class Data {
        // Getter 和 Setter
        // 测量名称
        @JsonProperty("measurement")
        private String measurement;

        // 字段集合
        @JsonProperty("fields")
        private Fields fields;

        // 记录列表（此处为空）
        @JsonProperty("records")
        private List<Object> records;

    }

    // 表示字段集合
    @Setter
    @Getter
    public static class Fields {
        // Getter 和 Setter
        // 重量字段
        @JsonProperty("weight")
        private WeightField weight;

    }

    // 表示重量字段
    @Setter
    @Getter
   public static class WeightField {
        // Getter 和 Setter
        // 数据类型
        @JsonProperty("type")
        private String type;

        // 时间和值的列表
        @JsonProperty("values")
        private List<TimeValue> values;

    }

    // 表示时间和值的对
    @Setter
    @Getter
    public static class TimeValue {
        // Getter 和 Setter
        // 时间戳（使用 String 保留 ISO 8601 格式）
        // 时间戳（格式化为 yyyy-MM-dd HH:mm:ss）
        @JsonProperty("time")
        private Date time;

        // 重量值
        @JsonProperty("value")
        private float value;

    }
}