package com.cuixing.wms;


import com.cuixing.wms.entity.WmsIngredientsLog;
import com.cuixing.wms.mapper.WmsIngredientsLogMapper;
import com.cuixing.wms.service.IWmsIngredientsLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
public class WmsTest {
    @Autowired
    private IWmsIngredientsLogService wmsIngredientsLogService;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyMMdd");
    private final Random random = new Random();

    @Test
    public void generateDailyData() {
        LocalDate startDate = LocalDate.of(2025, 1, 26);
        LocalDate endDate = LocalDate.of(2025, 6, 25);
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        for (long i = 0; i < days; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            // 2. 生成随机时间（小时、分钟、秒）
            int randomHour = ThreadLocalRandom.current().nextInt(0, 24);    // 0~23
            int randomMinute = ThreadLocalRandom.current().nextInt(0, 60);  // 0~59
            int randomSecond = ThreadLocalRandom.current().nextInt(0, 60);  // 0~59

            // 3. 组合成 LocalDateTime
            LocalDateTime randomDateTime = currentDate.atTime(randomHour, randomMinute, randomSecond);
            // 4. 转换为 Date（需先转成 Instant，再转 Date）
            Date resultDate = Date.from(randomDateTime.atZone(ZoneId.systemDefault()).toInstant());
            int recordsPerDay = random.nextInt(5) + 8;  // 生成 8-12 的随机数
            Random random = new Random();
            int manufacturerId = random.nextBoolean() ? 1 : 10; // 随机返回1或10
            Date preDate = null;
            for (int j = 0; j < recordsPerDay; j++) {
                WmsIngredientsLog wmsIngredientsLog = null;
                if (j == 0){
                    wmsIngredientsLog = createRandomLog(currentDate, j+1,manufacturerId,resultDate);
                }else{
                    wmsIngredientsLog = createRandomLog(currentDate, j+1,manufacturerId,preDate);
                }
                WmsIngredientsLog preData = wmsIngredientsLogService.insert(wmsIngredientsLog);
                preDate = preData.getCreateTime();
            }
        }
    }

    private WmsIngredientsLog createRandomLog(LocalDate date, int dailyCounter,int manufacturerId,Date preDateTime) {
        WmsIngredientsLog log = new WmsIngredientsLog();

        log.setManufacturerId(new BigInteger(String.valueOf(manufacturerId)));
        if (manufacturerId == 1){
            log.setManufacturerName("JT (江铜)");
        }else{
            log.setManufacturerName("JTY (锦太阳)");
        }
        String formattedDate = date.format(dateFormatter);
        String formattedCounter = String.format("%03d", dailyCounter);
        log.setPartNumber(formattedDate + "JTYL" + formattedCounter);
        log.setLotNumber(formattedDate + "JTYP" + formattedCounter);
        log.setProductId(new BigInteger("3"));
        log.setProductName("铜丝");
        log.setItemSpecification("2.6");
        log.setItemId(new BigInteger("206"));
        double netWeight = 2100.00 + (random.nextDouble() * 300.99); // 2100.00-2400.99
        log.setNetWeight(Math.round(netWeight * 100.0) / 100.0); // 2 decimal places
        double tareWeight = 120.00 + (random.nextDouble() * 10 - 5); // 115.00-125.00
        log.setTareWeight(Math.round(tareWeight * 100.0) / 100.0); // 2 decimal places
        log.setGrossWeight(Math.round((log.getNetWeight() + log.getTareWeight()) * 100.0) / 100.0);
        log.setMeasureId(new BigInteger("18"));
        log.setMeasureName("kg");

        Random random = new Random();
        int randomSeconds = 60 + random.nextInt(241); // 60~300（60 + [0, 240]）
        // Date 的计算基于毫秒，所以要先转换成毫秒
        long futureTimeInMillis = preDateTime.getTime() + (randomSeconds * 1000L);
        Date futureDate = new Date(futureTimeInMillis);
        log.setCreateTime(futureDate);
        log.setAttr2(0);
        log.setStatus("0");
        log.setCreateBy("admin");
        return log;
    }

    private Long generateRandomId() {
        return (long) (random.nextInt(10000) + 1);
    }

    private String generateRandomName(String prefix) {
        return prefix + UUID.randomUUID().toString().substring(0, 6);
    }

//    //1-1
//    @Test
//    private void test() {
//        for(int i = 0; i < 10; i++){
//            WmsIngredientsLog wmsIngredientsLog = new WmsIngredientsLog();
//            wmsIngredientsLog.setManufacturerId();
//            wmsIngredientsLog.setManufacturerName();
//            wmsIngredientsLog.setPartNumber();
//            wmsIngredientsLog.setLotNumber();
//            wmsIngredientsLog.setProductId(3L);
//            wmsIngredientsLog.setProductName("铜丝");
//            wmsIngredientsLog.setItemSpecification("");
//            wmsIngredientsLog.setItemId(new BigInteger(206));
//            wmsIngredientsLog.setNetWeight();
//            wmsIngredientsLog.setTareWeight();
//            wmsIngredientsLog.setGrossWeight();
//            wmsIngredientsLog.setMeasureId(18L);
//            wmsIngredientsLog.setMeasureName("kg");
//            WmsIngredientsLog insert = wmsIngredientsLogService.insert(wmsIngredientsLog);
//        }
//    }

    @Test
    public void generateRandomDate() {
        Random random = new Random();

        // 设置开始日期：2025-01-05 00:00:00
        Calendar startDate = Calendar.getInstance();
        startDate.set(2025, Calendar.JANUARY, 5, 0, 0, 0);
        long startMillis = startDate.getTimeInMillis();

        // 设置结束日期：2025-06-25 23:59:59
        Calendar endDate = Calendar.getInstance();
        endDate.set(2025, Calendar.JUNE, 25, 23, 59, 59);
        long endMillis = endDate.getTimeInMillis();

        // 计算时间范围内的随机毫秒数
        long randomMillis = startMillis + (long) (random.nextDouble() * (endMillis - startMillis));

        // 创建日期对象并清除时间部分
        Calendar randomCal = Calendar.getInstance();
        randomCal.setTimeInMillis(randomMillis);
        randomCal.set(Calendar.HOUR_OF_DAY, 0);
        randomCal.set(Calendar.MINUTE, 0);
        randomCal.set(Calendar.SECOND, 0);
        randomCal.set(Calendar.MILLISECOND, 0);

        System.out.println( randomCal.getTime());
    }


//    @Test
//    public void deleteData(){
//        int i = wmsIngredientsLogService.associatedDeletion("2025-01-20");
//        System.out.println(i);
//    }

}
