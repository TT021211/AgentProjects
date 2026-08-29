package com.cuixing.ecm;

import com.cuixing.ecm.domain.EcmElectricityConsumption;
import com.cuixing.ecm.domain.EcmStepTariff;
import com.cuixing.ecm.mapper.EcmElectricityConsumptionMapper;
import com.cuixing.ecm.mapper.EcmStepTariffMapper;
import com.cuixing.ecm.scheduler.EcmElectricScheduler;
import com.cuixing.ecm.service.IEcmElectricityConsumptionService;
import com.cuixing.ecm.service.impl.EcmElectricityConsumptionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
class CuixingEcmApplicationTests {
    @Autowired
    private IEcmElectricityConsumptionService iecmElectricityConsumptionService;

    @Autowired
    private EcmElectricScheduler ecmElectricScheduler;
    @Autowired
    private EcmElectricityConsumptionMapper ecmElectricityConsumptionMapper;
    @Autowired
    private EcmElectricityConsumptionServiceImpl ecmElectricityConsumptionServiceImpl;
    @Autowired
    private EcmStepTariffMapper ecmStepTariffMapper;

    @Test
    void contextLoads() {
//        EcmElectricScheduler ecmElectricScheduler = new EcmElectricScheduler();
//        ecmElectricScheduler.fetchDataScheduler();
    }

    @Test
    void test() {
        ecmElectricScheduler.fetchDataScheduler();
    }

    @Test
    void test1() {

        List<EcmElectricityConsumption> ecmElectricityConsumptions = ecmElectricityConsumptionMapper.getConsumptionByDeviceAndDate("016108130437", LocalDate.now());
        System.out.println(ecmElectricityConsumptions);
        System.out.println(LocalDateTime.now());
    }

//    @Test
//    void test2() {
//        int[] ints = ecmElectricityConsumptionServiceImpl.calculateDailyConsumption("005108150340", LocalDate.now());
//        System.out.println(Arrays.toString(ints));
//    }

//    @Test
//    void test3() {
////        int[] ints = ecmElectricityConsumptionServiceImpl.calculateHourlyConsumption("005108150340", LocalDate.now());
////        System.out.println(Arrays.toString(ints));
//    }

    @Test
    void test4() {
        List<EcmElectricityConsumption> firstRecordOfEachMonth = ecmElectricityConsumptionMapper.getFirstRecordOfEachMonth(2024, "016108130437");
        System.out.println(firstRecordOfEachMonth);
    }
    @Test
    void test5() {
        Integer lastRecordOfMonth = ecmElectricityConsumptionMapper.getLastRecordOfMonth(2024, 7, "016108130437");
        System.out.println(lastRecordOfMonth);
    }
//    @Test
//    void test6() {
//        int[] ints = ecmElectricityConsumptionServiceImpl.calculateMonthlyConsumption("005108150340", 2024);
//        System.out.println(Arrays.toString(ints));
//    }

    @Test
    void getElectricityPrice(){
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
             date = sdf.parse("2024-07-29 20:30:00");

        }catch (Exception e){
            e.printStackTrace();
        }
        List<EcmStepTariff> ecmStepTariffs = ecmStepTariffMapper.selectEcmStepTariffList(new EcmStepTariff());
        BigDecimal electricityPrice = ecmElectricScheduler.getElectricityPrice(date, ecmStepTariffs);
        System.out.println(electricityPrice);
    }



}
