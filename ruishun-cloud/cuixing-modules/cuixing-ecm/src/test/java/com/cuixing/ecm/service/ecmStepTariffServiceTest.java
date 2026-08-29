package com.cuixing.ecm.service;

import com.cuixing.ecm.domain.EcmStepTariff;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhan
 * @version 1.0
 * @date 2024/8/6 16:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ecmStepTariffServiceTest {
    @Autowired
    private IEcmStepTariffService ecmStepTariffService;

    @Test
    public void insert() {
        EcmStepTariff ecmStepTariff = new EcmStepTariff();
        ecmStepTariff.setName("平段");
        ecmStepTariff.setElectricityPrice(new java.math.BigDecimal("2"));
        ecmStepTariff.setStartTime("0");
        ecmStepTariff.setEndTime("0");
        ecmStepTariff.setMonth("0");
        int i = ecmStepTariffService.insertEcmStepTariff(ecmStepTariff);
    }


}
