//package com.cuixing.mes.scheduledTask;
//
//
//import com.cuixing.common.core.utils.DateUtils;
//import com.cuixing.mes.entity.MesErrorLog;
//import com.cuixing.mes.entity.mqtt.InfluxQueryResult;
//import com.cuixing.mes.service.MesErrorLogService;
//import com.cuixing.system.api.RemoteEqService;
//import com.cuixing.system.api.domain.eq.EqEquipment;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.jetbrains.annotations.NotNull;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//@Component
//@EnableScheduling
//@RequiredArgsConstructor
//public class ScheduledTaskMqtt {
//    private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskMqtt.class);
//    private static final String BASE_URL = "http://cuixing.natapp1.cc/influxdb/api/query_aggregate";
//    private static final ObjectMapper mapper = new ObjectMapper();
//
//    @Autowired
//    private RemoteEqService remoteEqService;
//    @Autowired
//    private MesErrorLogService mesErrorLogService;
//    /*@Autowired
//    private WebSocketClientService webSocketClientService;*/
//
//    @Scheduled(fixedRate = 5 * 60 * 1000) // 每天中午12点执行一次 cron ="0 0 12 * * ?"   fixedRate = 5 * 60 * 1000
//    public void scheduledTask() {
//        logger.info("mqtt定时获取开始！");
//        //先去查询设备台账表
//        List<EqEquipment> eqEquipments = remoteEqService.eqEquipmentList();
//        if (!eqEquipments.isEmpty()){
//            for (EqEquipment eqEquipment : eqEquipments){
//                //拿到设备编码和设备检测的上下限
//                String attr1 = eqEquipment.getAttr1();
//                if (attr1 != null && !attr1.isEmpty()){
//                    String[] parts = attr1.split(",");
//                    int min= Integer.parseInt(parts[0].trim());
//                    int max= Integer.parseInt(parts[1].trim());
//                    String equipmentCode = eqEquipment.getEquipmentCode();
//                    try {
//                        InfluxQueryResult influxQueryResult = queryAggregate(equipmentCode);
//                        List<InfluxQueryResult.Data> data = influxQueryResult.getData();
//                        for (InfluxQueryResult.Data dataItem : data) {
//                            InfluxQueryResult.Fields fields = dataItem.getFields();
//                            InfluxQueryResult.WeightField weightField = fields.getWeight();
//                            List<InfluxQueryResult.TimeValue> values = weightField.getValues();
//                            int size = values.size();
//                            int index = 0;
//
//                            for (InfluxQueryResult.TimeValue timeValue : values) {
//                                // 如果是最后一条数据，跳过
//                                if (index == size - 1) {
//                                    index++;
//                                    continue;
//                                }
//
//                                if (timeValue.getValue() == 0.0 || timeValue.getValue() < 0.0) {
//                                    index++;
//                                    continue;
//                                }
//
//                                if (timeValue.getValue() > max || timeValue.getValue() < min) {
//                                    logger.info("时间：{}", timeValue.getTime());
//                                    logger.info("数值：{}", timeValue.getValue());
//                                    // 存入异常表
//                                    MesErrorLog mesErrorLog = getMesErrorLog(eqEquipment, timeValue, attr1);
//                                    mesErrorLogService.insert(mesErrorLog);
//                                    logger.info("存入异常表成功！");
//                                    // 推送异常消息
//                                    // 推送异常消息到 WebSocket
//                                    /*String errorMessage = String.format("异常数据: 时间=%s, 数值=%s, 机台编码=%s", timeValue.getTime(), timeValue.getValue(), eqEquipment.getEquipmentCode());
//                                    webSocketClientService.sendMessage(errorMessage); */                               }
//                                index++;
//                            }
//                        }
//                    } catch (Exception e) {
//                        logger.info("调用mqtt接口查询失败！");
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        }else{
//            logger.info("设备台账表为空！");
//        }
//    }
//
//    @NotNull
//    private static MesErrorLog getMesErrorLog(EqEquipment eqEquipment, InfluxQueryResult.TimeValue timeValue, String attr1) {
//        MesErrorLog mesErrorLog = new MesErrorLog();
//        mesErrorLog.setDeviceName(eqEquipment.getEquipmentName());
//        mesErrorLog.setDeviceCode(eqEquipment.getEquipmentCode());
//        mesErrorLog.setErrorNum((double) timeValue.getValue());
//        mesErrorLog.setStatus("0");
//        mesErrorLog.setErrorTime(timeValue.getTime());
//        mesErrorLog.setCreateTime(DateUtils.getNowDate());
//        mesErrorLog.setAttr1(attr1);
//        return mesErrorLog;
//    }
//
//    // 查询方法
//    public InfluxQueryResult queryAggregate(String code) throws Exception {
//        // 构建 tags 参数，编码 code 值
//        String tags = String.format("{\"code\":\"%s\"}", code);
//        String encodedTags = URLEncoder.encode(tags, StandardCharsets.UTF_8.name());
//
//        // 构建完整的 URL
//        String url = String.format("%s?measurement=ls-data&fields=weight&tags=%s&start=-10m&aggregate=mean&every=5m",
//                BASE_URL, encodedTags);
//
//        // 创建 HttpURLConnection
//        URL apiUrl = new URL(url);
//        StringBuilder response = getStringBuilder(apiUrl);
//
//        // 解析 JSON 响应为 InfluxQueryResult
//        return mapper.readValue(response.toString(), InfluxQueryResult.class);
//    }
//
//    @NotNull
//    private static StringBuilder getStringBuilder(URL apiUrl) throws IOException {
//        HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Accept", "application/json");
//
//        // 检查响应状态
//        int statusCode = conn.getResponseCode();
//        if (statusCode != 200) {
//            throw new RuntimeException("请求失败，状态码: " + statusCode);
//        }
//
//        // 读取响应
//        StringBuilder response = new StringBuilder();
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                response.append(line);
//            }
//        } finally {
//            conn.disconnect();
//        }
//        return response;
//    }
//}
