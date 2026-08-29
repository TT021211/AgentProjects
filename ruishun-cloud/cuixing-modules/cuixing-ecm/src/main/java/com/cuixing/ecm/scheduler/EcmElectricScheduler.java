package com.cuixing.ecm.scheduler;

import com.cuixing.common.core.exception.ServiceException;
import com.cuixing.ecm.domain.DTO.EcmElectricApiResponse;
import com.cuixing.ecm.domain.DTO.EcmElectricDeviceData;
import com.cuixing.ecm.domain.EcmElectricityConsumption;
import com.cuixing.ecm.domain.EcmScheduledTaskLog;
import com.cuixing.ecm.domain.EcmStepTariff;
import com.cuixing.ecm.mapper.EcmElectricityConsumptionMapper;
import com.cuixing.ecm.mapper.EcmScheduledTaskLogMapper;
import com.cuixing.ecm.mapper.EcmStepTariffMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 获取
 *
 * @author zhan
 * @date 2024-07-24
 */
@Component
public class EcmElectricScheduler {

    private String apiUrl = "https://docking.delixi-electric.com/api/third/docking/queryByParam";
    private String appKey = "wMNkfHD6";
    private String secret = "191b263ba254f798030e155e0029f750";

    @Autowired
    private EcmElectricityConsumptionMapper ecmElectricityConsumptionMapper;

    @Autowired
    private EcmScheduledTaskLogMapper scheduledTaskLogMapper;

    @Autowired
    private EcmStepTariffMapper ecmStepTariffMapper;

    //@Scheduled(cron = "0 5 * * * ?") // 每小时第5分钟执行
    @Transactional
    public void fetchDataScheduler() {
        EcmScheduledTaskLog lastLog = scheduledTaskLogMapper.selectEcmScheduledTaskLogByTaskName("fetchDataScheduler");
        Date now = getCurrentNearestFifthMinute();
        Date lastExecutionTime;
        if (lastLog == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(2024, Calendar.JULY, 22, 0, 5, 0);
            lastExecutionTime = calendar.getTime();
            EcmScheduledTaskLog newLog = new EcmScheduledTaskLog();
            newLog.setTaskName("fetchDataScheduler");
            newLog.setLastExecutionTime(now);
            scheduledTaskLogMapper.insertEcmScheduledTaskLog(newLog);
        } else {
            lastExecutionTime = lastLog.getLastExecutionTime();
        }

        if (lastExecutionTime.after(now)) {
            throw new ServiceException("lastExecutionTime 不能比当前时间晚");
        }


        while (lastExecutionTime.before(now)) {

            Calendar cal = Calendar.getInstance();
            cal.setTime(lastExecutionTime);
            cal.add(Calendar.HOUR_OF_DAY, 1);
            cal.set(Calendar.MINUTE, 5);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date endTime = cal.getTime();
            if (endTime.after(now)) {
                endTime = now;
            }
            cal.add(Calendar.MINUTE, -15);
            Date startTime = cal.getTime();

            //拉取并更新半小时的
            cal.add(Calendar.MINUTE, -30);
            Date halfStartTime = cal.getTime();
            cal.add(Calendar.MINUTE, 15);
            Date halfEndTime = cal.getTime();
            fetchDataAndUpdateDatabase(halfStartTime, halfEndTime,getHalfPoint(halfEndTime));

            //拉取并更新整点的
            fetchDataAndUpdateDatabase(startTime, endTime,getIntegralPoint(endTime));

            lastExecutionTime = endTime;

            EcmScheduledTaskLog newLog = new EcmScheduledTaskLog();
            newLog.setTaskName("fetchDataScheduler");
            newLog.setLastExecutionTime(endTime);
            scheduledTaskLogMapper.updateEcmScheduledTaskLogByTaskName(newLog);
        }
    }

//    private void fetchDataAndUpdateDatabase(Date startTime, Date endTime, Date creatTime) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String startTimeStr = simpleDateFormat.format(startTime);
//        String endTimeStr = simpleDateFormat.format(endTime);
//        System.out.println("开始时间：" + startTimeStr);
//        System.out.println("结束时间：" + endTimeStr);
//
//        EcmElectricApiResponse ecmElectricApiResponse = fetchData(startTimeStr, endTimeStr);
//        List<EcmElectricDeviceData> ecmElectricDeviceDataList = ecmElectricApiResponse.getData();
//        if (ecmElectricDeviceDataList == null){
//            return;
//        }
//        List<EcmElectricDeviceData> filteredList = ecmElectricDeviceDataList.stream()
//                .filter(data -> data.getCmdType().equals("2")).collect(Collectors.toList());
//        System.out.println("过滤后数据：" + filteredList);
//
//        //获取阶梯电价表数据
//        List<EcmStepTariff> ecmStepTariffs = ecmStepTariffMapper.selectEcmStepTariffList(new EcmStepTariff());
//        for (EcmElectricDeviceData ecmElectricDeviceData : filteredList) {
//            EcmElectricityConsumption ecmElectricityConsumption = new EcmElectricityConsumption();
//            //根据电表编码查询该电表最后一条数据
//            EcmElectricityConsumption lastEcmElectricityConsumption = ecmElectricityConsumptionMapper.selectLastByCode(ecmElectricDeviceData.getDeviceCode());
//            if (lastEcmElectricityConsumption == null) {
//                ecmElectricityConsumption.setConsumptionValue(ecmElectricDeviceData.getOnceEPP());
//            }else {
//                ecmElectricityConsumption.setConsumptionValue(ecmElectricDeviceData.getOnceEPP() - lastEcmElectricityConsumption.getValue());
//            }
//            ecmElectricityConsumption.setElectricDeviceCode(ecmElectricDeviceData.getDeviceCode());
//            ecmElectricityConsumption.setRecordingTime(ecmElectricDeviceData.getCreateTime());
//            ecmElectricityConsumption.setValue(ecmElectricDeviceData.getOnceEPP());
//            ecmElectricityConsumption.setCreateBy("定时拉取三方数据");
//            ecmElectricityConsumption.setCreateTime(creatTime);
//            //根据creatTime获取电价
//            BigDecimal electricityPrice = getElectricityPrice(creatTime, ecmStepTariffs);
//            ecmElectricityConsumption.setElectricityPrice(electricityPrice);
//            Integer consumptionValue = ecmElectricityConsumption.getConsumptionValue();
//            if (consumptionValue != null && consumptionValue > 0) {
//                BigDecimal consumptionValueWh = new BigDecimal(consumptionValue);
//                BigDecimal divide = consumptionValueWh.divide(new BigDecimal(1000),3, RoundingMode.HALF_UP);
//                ecmElectricityConsumption.setElectricCharge(electricityPrice.multiply(divide));
//            }
//
//            ecmElectricityConsumptionMapper.insertEcmElectricityConsumption(ecmElectricityConsumption);
//        }
//    }

    private void fetchDataAndUpdateDatabase(Date startTime, Date endTime, Date creatTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTimeStr = simpleDateFormat.format(startTime);
        String endTimeStr = simpleDateFormat.format(endTime);
        System.out.println("开始时间：" + startTimeStr);
        System.out.println("结束时间：" + endTimeStr);

        EcmElectricApiResponse ecmElectricApiResponse = fetchData(startTimeStr, endTimeStr);
        List<EcmElectricDeviceData> ecmElectricDeviceDataList = ecmElectricApiResponse.getData();
        if (ecmElectricDeviceDataList == null) {
            return;
        }
        List<EcmElectricDeviceData> filteredList = ecmElectricDeviceDataList.stream()
                // 先过滤出 cmdType 为 "2" 的数据
                .filter(data -> data.getCmdType().equals("2"))
                // 按 deviceCode 分组
                .collect(Collectors.groupingBy(EcmElectricDeviceData::getDeviceCode))
                .entrySet().stream()
                // 对每个分组找到 createTime 最晚的项
                .map(entry -> entry.getValue().stream()
                        .max(Comparator.comparing(EcmElectricDeviceData::getCreateTime))
                        .orElse(null)) // 防止空指针异常
                // 去除空值并将结果收集到列表
                .filter(data -> data != null)
                .collect(Collectors.toList());
        System.out.println("过滤后数据：" + filteredList);

        for (EcmElectricDeviceData ecmElectricDeviceData : filteredList) {
            EcmElectricityConsumption ecmElectricityConsumption = new EcmElectricityConsumption();
            //根据电表编码查询该电表最后一条数据
            EcmElectricityConsumption lastEcmElectricityConsumption = ecmElectricityConsumptionMapper.selectLastByCode(ecmElectricDeviceData.getDeviceCode());
            if (lastEcmElectricityConsumption == null) {
                ecmElectricityConsumption.setConsumptionValue(ecmElectricDeviceData.getOnceEPP());
            } else if (lastEcmElectricityConsumption.getCreateTime().equals(creatTime)
            ) {
                continue;
            } else {
                ecmElectricityConsumption.setConsumptionValue(ecmElectricDeviceData.getOnceEPP() - lastEcmElectricityConsumption.getValue());
            }
            ecmElectricityConsumption.setElectricDeviceCode(ecmElectricDeviceData.getDeviceCode());
            ecmElectricityConsumption.setRecordingTime(ecmElectricDeviceData.getCreateTime());
            ecmElectricityConsumption.setValue(ecmElectricDeviceData.getOnceEPP());
            ecmElectricityConsumption.setCreateBy("定时拉取三方数据");
            ecmElectricityConsumption.setCreateTime(creatTime);

            Integer consumptionValue = ecmElectricityConsumption.getConsumptionValue();
//            if (consumptionValue != null && consumptionValue > 0) {
//                BigDecimal consumptionValueWh = new BigDecimal(consumptionValue);
//                BigDecimal divide = consumptionValueWh.divide(new BigDecimal(1000),3, RoundingMode.HALF_UP);
//                ecmElectricityConsumption.setElectricCharge(electricityPrice.multiply(divide));
//            }
            ecmElectricityConsumptionMapper.insertEcmElectricityConsumption(ecmElectricityConsumption);
        }
    }

    public EcmElectricApiResponse fetchData(String startTime, String endTime) {
        RestTemplate restTemplate = new RestTemplate();

        String timestamp = String.valueOf(System.currentTimeMillis());
        String randomStr = generateRandomStr(6, 8);
        String sign = generateSign(appKey, timestamp, randomStr, secret);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("appKey", appKey)
                .queryParam("timestamp", timestamp)
                .queryParam("randomStr", randomStr)
                .queryParam("sign", sign);

        if (startTime != null) {
            builder.queryParam("startTime", startTime);
        }
        if (endTime != null) {
            builder.queryParam("endTime", endTime);
        }
        URI uri = builder.build().toUri();
        System.out.println("请求地址：" + uri);

        return restTemplate.getForObject(uri, EcmElectricApiResponse.class);
    }

    private String generateRandomStr(int minLength, int maxLength) {
        int length = new Random().nextInt(maxLength - minLength + 1) + minLength;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            randomString.append(characters.charAt(random.nextInt(characters.length())));
        }
        return randomString.toString();
    }

    public static String generateSign(String appKey, String timestamp, String randomStr, String secret) {
        String data = appKey + timestamp + randomStr + secret;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(data.getBytes());
            StringBuilder sign = new StringBuilder();
            for (byte b : hashInBytes) {
                sign.append(String.format("%02x", b));
            }
            return sign.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }

    private Date getIntegralPoint(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    //获取当前半点
    private Date getHalfPoint(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    private Date getCurrentNearestFifthMinute() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.MINUTE, 5);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    //根据时间和电价列表获取当前时间对应的电价
    public BigDecimal getElectricityPrice(Date date, List<EcmStepTariff> ecmStepTariffs) {
        //获取ecmStepTariffs中月份为0的电费表定为平价电费
        EcmStepTariff defaultEcmStepTariff = ecmStepTariffs.stream()
                .filter(data -> data.getMonth().equals("0")).findFirst().orElse(null);
        BigDecimal defaultElectricityPrice = new BigDecimal(0);
        if (defaultEcmStepTariff != null) {
            defaultElectricityPrice = defaultEcmStepTariff.getElectricityPrice();
        }
        //过滤掉ecmStepTariffs中月份为0的电费表定为平价电费
        ecmStepTariffs = ecmStepTariffs.stream()
                .filter(data -> !data.getMonth().equals("0")).collect(Collectors.toList());

        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        int month = localDateTime.getMonthValue();
        LocalTime time = localDateTime.toLocalTime();

        for (EcmStepTariff ecmStepTariff : ecmStepTariffs) {
            // 将月份范围拆分
            String[] monthRanges = ecmStepTariff.getMonth().split(",");
            for (String monthRange : monthRanges) {
                // 将单个范围的起始和结束月份拆分
                String[] months = monthRange.split("-");
                // 起始月份
                int startMonth = Integer.parseInt(months[0]);
                // 结束月份
                int endMonth = Integer.parseInt(months[1]);

                // 存在跨年情况
                if ((startMonth <= month && month <= endMonth) ||
                        (startMonth > endMonth && (month >= startMonth || month <= endMonth))) {

                    LocalTime startTime = LocalTime.parse(ecmStepTariff.getStartTime()); // 开始时间
                    LocalTime endTime = LocalTime.parse(ecmStepTariff.getEndTime()); // 结束时间

                    // 判断当前时间是否在电价适用时间段内
                    if (time.isAfter(startTime) && !time.isAfter(endTime)) {
                        return ecmStepTariff.getElectricityPrice(); // 返回对应电价
                    }
                }
            }
        }
        return defaultElectricityPrice;
    }
}
