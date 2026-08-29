package com.cuixing.mes;

import com.cuixing.common.core.exception.mes.ProductException;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.mes.service.impl.MesOuputLogServiceImpl;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.domain.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
public class TestCode {



    @Test
    public void testCode(){
        String jobNumber = "Q57";
        String newJobNumber = jobNumber.substring(1);
        System.out.println(newJobNumber);
    }


    @Test
    public void testMM(){
        String jobNumber = "0.21";
        String number = "0.210";
        double aDouble = Double.parseDouble(jobNumber);
        double bDouble = Double.parseDouble(number);
        boolean result = aDouble == bDouble;
        System.out.println(result);
    }

    @Test
    public void testTime(){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -1); // 减去2小时
        Date twoHoursAgo = calendar.getTime();
        System.out.println(twoHoursAgo);

        Date now = DateUtils.getNowDate();
        System.out.println(now);
        long differMilliseconds  = Math.abs(now.getTime() - twoHoursAgo.getTime());
        double differHours = (double) differMilliseconds / (1000 * 60 * 60);
        System.out.println(differHours);
        if (differHours < 0.5){
            throw new ProductException("报工错误，请勿重复报工！");
        }

    }


    @Value("${cuixing.mes.outputTime.timeOne}")
    private Double timeOne;
    @Value("${cuixing.mes.outputTime.timeTwo}")
    private Double timeTwo;
    @Value("${cuixing.mes.outputTime.timeThree}")
    private Double timeThree;
    @Value("${cuixing.mes.outputTime.timeFour}")
    private Double timeFour;
    @Value("${cuixing.mes.outputTime.timeFive}")
    private Double timeFive;
    private Double checkTime(String itemSpecificationCode){
        double itemSpecificationDouble = Double.parseDouble(itemSpecificationCode);

        if ( 1.2 < itemSpecificationDouble && itemSpecificationDouble <= 2.6){
            return timeOne;
        }else if(0.38 <= itemSpecificationDouble && itemSpecificationDouble < 1.2){
            return timeTwo;
        }else if(0.15 <= itemSpecificationDouble && itemSpecificationDouble < 0.38){
            return timeThree;
        }else if(0.10 <= itemSpecificationDouble && itemSpecificationDouble < 0.15){
            return timeFour;
        }else if(0.06 <= itemSpecificationDouble && itemSpecificationDouble < 0.10){
            return timeFive;
        }
        return 0.0;
    }
    @Test
    public void testOutPutTime(){
//        String itemSpecificationCode = "0.59";
//        Double aDouble = checkTime(itemSpecificationCode);
//        System.out.println(aDouble);
        System.out.println(timeFive);
    }

}
