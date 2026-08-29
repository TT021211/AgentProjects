package com.cuixing.ecm.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 三方电表拉去数据主响应类
 * @author zhan
 * @version 1.0
 * @date 2024/7/24 16:29
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EcmElectricApiResponse {
    private int code;
    private String msg;
    private List<EcmElectricDeviceData> data;
    private boolean success;
}
