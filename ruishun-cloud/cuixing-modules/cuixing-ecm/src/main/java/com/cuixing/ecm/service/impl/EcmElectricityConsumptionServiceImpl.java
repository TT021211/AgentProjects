package com.cuixing.ecm.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.ecm.domain.EcmElectricDevice;
import com.cuixing.ecm.domain.VO.*;
import com.cuixing.ecm.mapper.EcmDataCacheMapper;
import com.cuixing.ecm.service.IEcmElectricDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.ecm.mapper.EcmElectricityConsumptionMapper;
import com.cuixing.ecm.domain.EcmElectricityConsumption;
import com.cuixing.ecm.service.IEcmElectricityConsumptionService;

/**
 * 电能耗数据Service业务层处理
 *
 * @author zhan
 * @date 2024-07-24
 */
@Service
public class EcmElectricityConsumptionServiceImpl implements IEcmElectricityConsumptionService {
    @Autowired
    private EcmElectricityConsumptionMapper ecmElectricityConsumptionMapper;

    @Autowired
    private IEcmElectricDeviceService ecmElectricDeviceService;

    @Autowired
    private EcmDataCacheMapper ecmDataCacheMapper;

    /**
     * 查询电能耗数据
     *
     * @param consumptionId 电能耗数据主键
     * @return 电能耗数据
     */
    @Override
    public EcmElectricityConsumption selectEcmElectricityConsumptionByConsumptionId(Long consumptionId) {
        return ecmElectricityConsumptionMapper.selectEcmElectricityConsumptionByConsumptionId(consumptionId);
    }

    /**
     * 查询电能耗数据列表
     *
     * @param ecmElectricityConsumption 电能耗数据
     * @return 电能耗数据
     */
    @Override
    public List<EcmElectricityConsumption> selectEcmElectricityConsumptionList(EcmElectricityConsumption ecmElectricityConsumption) {
        return ecmElectricityConsumptionMapper.selectEcmElectricityConsumptionList(ecmElectricityConsumption);
    }

    /**
     * 新增电能耗数据
     *
     * @param ecmElectricityConsumption 电能耗数据
     * @return 结果
     */
    @Override
    public int insertEcmElectricityConsumption(EcmElectricityConsumption ecmElectricityConsumption) {
        ecmElectricityConsumption.setCreateTime(DateUtils.getNowDate());
        return ecmElectricityConsumptionMapper.insertEcmElectricityConsumption(ecmElectricityConsumption);
    }

    /**
     * 修改电能耗数据
     *
     * @param ecmElectricityConsumption 电能耗数据
     * @return 结果
     */
    @Override
    public int updateEcmElectricityConsumption(EcmElectricityConsumption ecmElectricityConsumption) {
        ecmElectricityConsumption.setUpdateTime(DateUtils.getNowDate());
        return ecmElectricityConsumptionMapper.updateEcmElectricityConsumption(ecmElectricityConsumption);
    }

    /**
     * 批量删除电能耗数据
     *
     * @param consumptionIds 需要删除的电能耗数据主键
     * @return 结果
     */
    @Override
    public int deleteEcmElectricityConsumptionByConsumptionIds(String[] consumptionIds) {
        return ecmElectricityConsumptionMapper.deleteEcmElectricityConsumptionByConsumptionIds(consumptionIds);
    }

    /**
     * 删除电能耗数据信息
     *
     * @param consumptionId 电能耗数据主键
     * @return 结果
     */
    @Override
    public int deleteEcmElectricityConsumptionByConsumptionId(String consumptionId) {
        return ecmElectricityConsumptionMapper.deleteEcmElectricityConsumptionByConsumptionId(consumptionId);
    }

    /**
     * 计算每半小时的消耗量
     *
     * @param electricDeviceCode 设备编码
     * @param date               日期
     * @return int[]
     */

    @Override
    public ConsumptionValueAndCharge calculateHourlyConsumption(String electricDeviceCode, LocalDate date) {

        LocalDate nextDay = date.plusDays(1);
        List<EcmElectricityConsumption> consumptions = ecmElectricityConsumptionMapper.getConsumptionByDeviceAndDate(electricDeviceCode, date);
        //查询下一天零点的数据
        EcmElectricityConsumption zeroPointData = ecmElectricityConsumptionMapper.getZeroPointData(electricDeviceCode, nextDay);

        int[] hourlyConsumptionValues = new int[49];
        BigDecimal [] hourlyConsumptionCharges = new BigDecimal[49];
        for (int i = 0; i < 49; i++) {
            hourlyConsumptionValues[i] = 0;
            hourlyConsumptionCharges[i] = BigDecimal.ZERO;
        }
        int dailyTotal = 0;
        BigDecimal dailyCharge = BigDecimal.ZERO;
        for (EcmElectricityConsumption consumption : consumptions) {
            Date createTime = consumption.getCreateTime();
            LocalDateTime localDate = createTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            int hour = localDate.getHour();
            int minute = localDate.getMinute();

            int consumptionValue = consumption.getConsumptionValue();
            BigDecimal electricCharge = consumption.getElectricCharge();

            if (hour == 0 && minute == 0) {
                continue;
            }
            if (minute == 0) {
                hourlyConsumptionValues[hour * 2] = consumptionValue;
                hourlyConsumptionCharges[hour * 2] = electricCharge;
            } else {
                hourlyConsumptionValues[hour * 2 + 1] = consumptionValue;
                hourlyConsumptionCharges[hour * 2 + 1] = electricCharge;
            }
            dailyTotal += consumptionValue;
            dailyCharge = dailyCharge.add(electricCharge);
        }
        if (zeroPointData != null){
            hourlyConsumptionValues[48] = zeroPointData.getConsumptionValue() != null ? zeroPointData.getConsumptionValue() : 0;
            hourlyConsumptionCharges[48] = zeroPointData.getElectricCharge() != null  ? zeroPointData.getElectricCharge() : BigDecimal.ZERO;
        }else{
            hourlyConsumptionValues[48] = 0;
            hourlyConsumptionCharges[48] = BigDecimal.ZERO;
        }

        dailyTotal += hourlyConsumptionValues[48];
        dailyCharge = dailyCharge.add(hourlyConsumptionCharges[48]);
        hourlyConsumptionValues[0] = dailyTotal;
        hourlyConsumptionCharges[0] = dailyCharge;

        return new ConsumptionValueAndCharge(hourlyConsumptionValues, hourlyConsumptionCharges);
    }

    @Override
    public ConsumptionValueAndCharge calculateDailyConsumption(String electricDeviceCode, LocalDate month) {
        LocalDate startDate = month.withDayOfMonth(1);
        LocalDate endDate = month.plusMonths(1).withDayOfMonth(1);
        int daysInMonth = (int) ChronoUnit.DAYS.between(startDate, endDate);
        int[] dailyValues = new int[daysInMonth + 1];
        BigDecimal [] dailyCharges = new BigDecimal[daysInMonth + 1];
        Arrays.fill(dailyValues, 0);
        //dailyCharges初始化
        Arrays.fill(dailyCharges, BigDecimal.ZERO);

        int monthTotal = 0;
        BigDecimal monthCharge = BigDecimal.ZERO;
        for (int day = 0; day < daysInMonth; day++) {

            LocalDate currentDate = startDate.plusDays(day);
            // 获取当前日期的消费记录
            List<EcmElectricityConsumption> consumptions = ecmElectricityConsumptionMapper.getConsumptionByDeviceAndDate(electricDeviceCode, currentDate);

            //查询下一天零点的数据
            EcmElectricityConsumption zeroPointData = ecmElectricityConsumptionMapper.getZeroPointData(electricDeviceCode, currentDate.plusDays(1));

            Integer nextDayZeroConsumptionHourValue = null;
            BigDecimal nextDayZeroConsumptionHourCharge = null;
            if (zeroPointData!=null){
                nextDayZeroConsumptionHourValue= zeroPointData.getConsumptionValue();
                nextDayZeroConsumptionHourCharge = zeroPointData.getElectricCharge();
            }


            for (int i = 0; i < consumptions.size(); i++) {
                EcmElectricityConsumption consumption = consumptions.get(i);
                Date createTime =  consumption.getCreateTime();
                LocalDateTime localDate = createTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                int hour = localDate.getHour();
                int minute = localDate.getMinute();
                if (hour == 0 && minute == 0) {
                    continue;
                }
                dailyValues[day + 1] += consumption.getConsumptionValue();
                dailyCharges[day + 1] = dailyCharges[day + 1].add(consumption.getElectricCharge());
            }
            dailyValues[day + 1] += nextDayZeroConsumptionHourValue != null ? nextDayZeroConsumptionHourValue : 0;
            dailyCharges[day + 1] = dailyCharges[day + 1].add(nextDayZeroConsumptionHourCharge != null ? nextDayZeroConsumptionHourCharge : BigDecimal.ZERO);
            monthTotal += dailyValues[day + 1];
            monthCharge = monthCharge.add(dailyCharges[day + 1]);
        }
        dailyValues[0] = monthTotal;
        dailyCharges[0] = monthCharge;
        return new ConsumptionValueAndCharge(dailyValues, dailyCharges);
    }

    @Override
    public ConsumptionValueAndCharge calculateMonthlyConsumption(String electricDeviceCode, int year) {
        int[] yearConsumptionValues = new int[13];
        BigDecimal [] yearConsumptionCharges = new BigDecimal[13];
        int yearTotal = 0;
        BigDecimal yearCharge = BigDecimal.ZERO;

        for (int month = 1; month <= 12; month++) {
            LocalDate date = LocalDate.of(year, month, 1);
            ConsumptionValueAndCharge consumptionValueAndCharge = calculateDailyConsumption(electricDeviceCode, date);
            int[] monthlyConsumptionValue = consumptionValueAndCharge.getConsumptionValues();
            BigDecimal [] monthlyConsumptionCharge = consumptionValueAndCharge.getElectricCharges();

            yearConsumptionValues[month] = monthlyConsumptionValue[0];
            yearConsumptionCharges[month] = monthlyConsumptionCharge[0];

            yearTotal += yearConsumptionValues[month];
            yearCharge = yearCharge.add(yearConsumptionCharges[month]);
        }
        yearConsumptionValues[0] = yearTotal;
        yearConsumptionCharges[0] = yearCharge;
        return new ConsumptionValueAndCharge(yearConsumptionValues, yearConsumptionCharges);
    }

    //    @Override
//    public int[] calculateHourlyConsumption(String electricDeviceCode, LocalDate date) {
//
//        LocalDate nextDay = date.plusDays(1);
//
//        List<EcmElectricityConsumption> consumptions = ecmElectricityConsumptionMapper.getConsumptionByDeviceAndDate(electricDeviceCode, date);
//        if (consumptions.isEmpty()) {
//            return new int[25];
//        }
//
//        int[] hourlyValues = new int[25];
//        for (int i = 0; i < 24; i++) {
//            hourlyValues[i] = 0;
//        }
//        Map<Integer, Integer> hourlyData = new TreeMap<>();
//
//        for (EcmElectricityConsumption consumption : consumptions) {
//            Date createTime = consumption.getCreateTime();
//            int hour = createTime.toInstant().atZone(ZoneId.systemDefault()).getHour();
//            int value = consumption.getValue();
//            hourlyData.put(hour, value);
//        }
//
//        Integer nextDayZeroHourValue = ecmElectricityConsumptionMapper.getDayFirstValue(electricDeviceCode, nextDay);
//        hourlyData.put(24, nextDayZeroHourValue != null ? nextDayZeroHourValue : 0);
//
//        Integer previousValue = hourlyData.getOrDefault(0, 0);
//        int dailyTotal = 0;
//
//        for (int hour = 1; hour <= 24; hour++) {
//
//            Integer currentValue = hourlyData.get(hour);
//
//            if (currentValue != null && currentValue > previousValue) {
//                hourlyValues[hour] = currentValue - hourlyData.getOrDefault(hour - 1, previousValue);
//                previousValue = currentValue;
//                dailyTotal += hourlyValues[hour];
//            } else {
//                hourlyValues[hour] = 0;
//            }
//
//        }
//
//        hourlyValues[0] = dailyTotal;
//        return hourlyValues;
//    }

//    @Override
//    public int[] calculateDailyConsumption(String electricDeviceCode, LocalDate month) {
//        LocalDate startDate = month.withDayOfMonth(1);
//        LocalDate endDate = month.plusMonths(1).withDayOfMonth(1);
//
//
//        List<EcmElectricityConsumption> consumptions = ecmElectricityConsumptionMapper.getConsumptionByDeviceAndMonth(electricDeviceCode, startDate, endDate);
//
//
//        int daysInMonth = (int) ChronoUnit.DAYS.between(startDate, endDate);
//        int[] dailyValues = new int[daysInMonth + 1];
//        Arrays.fill(dailyValues, 0);
//
//        Map<LocalDate, Integer> dailyData = new TreeMap<>();
//        for (EcmElectricityConsumption consumption : consumptions) {
//            Date createTime = consumption.getCreateTime();
//            //如果为0点则算做昨天的
//            if (createTime.toInstant().atZone(ZoneId.systemDefault()).getHour() == 0) {
//                dailyData.put(createTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusDays(1), consumption.getValue());
//            } else {
//                LocalDate date = createTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//                int value = consumption.getValue();
//                dailyData.put(date, value);
//            }
//        }
//        Integer previousMonthLastValue = ecmElectricityConsumptionMapper.getDayLastValue(electricDeviceCode, startDate.minusDays(1));
//        Integer previousValue = previousMonthLastValue != null ? previousMonthLastValue : 0;
//
//        int monthTotal = 0;
//        for (int day = 0; day < daysInMonth; day++) {
//            LocalDate currentDate = startDate.plusDays(day);
//            LocalDate previousDate = currentDate.minusDays(1);
//            ;
//
//            Integer previousDayLastValue = dailyData.getOrDefault(previousDate, previousValue);
//            Integer currentLastValue = dailyData.get(currentDate);
//
//            if (currentLastValue != null) {
//                dailyValues[day + 1] = currentLastValue - previousDayLastValue;
//                monthTotal += dailyValues[day + 1];
//            } else {
//                dailyValues[day + 1] = 0;
//            }
//
//            previousValue = currentLastValue == null ? previousValue : currentLastValue;
//        }
//        dailyValues[0] = monthTotal;
//        return dailyValues;
//    }

//
//    @Override
//    public int[] calculateMonthlyConsumption(String electricDeviceCode, int year) {
//
//        int[] monthlyValues = new int[13];
//        //查询每个月第一条数据
//        List<EcmElectricityConsumption> firstRecordOfEachMonth = ecmElectricityConsumptionMapper.getFirstRecordOfEachMonth(year, electricDeviceCode);
//        Map<Integer, Integer> monthlyData = new TreeMap<>();
//        for (EcmElectricityConsumption consumption : firstRecordOfEachMonth) {
//            Date createTime = consumption.getCreateTime();
//            int month = createTime.toInstant().atZone(ZoneId.systemDefault()).getMonthValue();
//            int value = consumption.getValue();
//            monthlyData.put(month, value);
//        }
//
//        Integer nextYearFirstValue = ecmElectricityConsumptionMapper.getDayFirstValue(electricDeviceCode, LocalDate.of(year + 1, 1, 1));
//        if (nextYearFirstValue != null) {
//            monthlyData.put(13, nextYearFirstValue);
//        }
//        //一年的第一条
//        // Integer previousYearLastValue = monthlyData.getOrDefault(1, 0);
//        Integer previousValue = monthlyData.getOrDefault(1, 0);
//        int yearTotal = 0;
//        for (int month = 1; month <= 12; month++) {
//            Integer currentMonthFirstValue = monthlyData.get(month);
//            Integer nextMonthFirstValue = monthlyData.get(month + 1);
//            if (currentMonthFirstValue == null) {
//                monthlyValues[month] = 0;
//            } else {
//                if (nextMonthFirstValue != null) {
//                    monthlyValues[month] = nextMonthFirstValue - previousValue;
//                    previousValue = nextMonthFirstValue;
//                } else {
//                    //查询本月最后一条数据
//                    Integer lastValue = ecmElectricityConsumptionMapper.getLastRecordOfMonth(year, month, electricDeviceCode);
//                    if (lastValue == null) {
//                        throw new RuntimeException("查询本月最后一条数据失败");
//                    }
//                    monthlyValues[month] = lastValue - previousValue;
//                    previousValue = lastValue;
//                }
//
//            }
//            yearTotal += monthlyValues[month];
//        }
//        monthlyValues[0] = yearTotal;
//        return monthlyValues;
//    }


    @Override
    public List<ConsumptionValueAndCharge> calculateTotalSummaryTable(String type, String date) {

        EcmElectricDevice ecmElectricDeviceQuery = new EcmElectricDevice();
        ecmElectricDeviceQuery.setIsMain("1");
        //查询所有主表
        List<EcmElectricDevice> ecmElectricDevices = ecmElectricDeviceService.selectEcmElectricDeviceList(ecmElectricDeviceQuery);
        List<ConsumptionValueAndCharge> consumptionValueAndCharges = new ArrayList<>();
        switch (type){
            case "年":
                for(EcmElectricDevice ecmElectricDevice : ecmElectricDevices){
//                    ConsumptionValueAndCharge consumptionValueAndCharge = calculateMonthlyConsumption(ecmElectricDevice.getElectricDeviceCode(), Integer.parseInt(split[0]));
                }
                return consumptionValueAndCharges;
            case "季":
                String[] split = date.split("-");

                for(EcmElectricDevice ecmElectricDevice : ecmElectricDevices){
                    int total = 0;
                    ConsumptionValueAndCharge consumptionValueAndCharge = calculateMonthlyConsumption(ecmElectricDevice.getElectricDeviceCode(), Integer.parseInt(split[0]));
                    int quarter = Integer.parseInt(split[1]);
                    int[] consumptionValues = consumptionValueAndCharge.getConsumptionValues();
                    ConsumptionValueAndCharge consumptionValueAndChargeRes = new ConsumptionValueAndCharge();
                    int mon = (quarter -1) * 3;
                    for (int i = mon ; i < mon + 3; i++){
                        total += consumptionValues[i+1];
                    }
                    consumptionValueAndCharges.add(consumptionValueAndCharge);
                }
                return consumptionValueAndCharges;
            case "月":
                date += "-01";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate newMonth = LocalDate.parse(date, formatter);

                for(EcmElectricDevice ecmElectricDevice : ecmElectricDevices){
                    ConsumptionValueAndCharge consumptionValueAndCharge = calculateDailyConsumption(ecmElectricDevice.getElectricDeviceCode(), newMonth);
                    consumptionValueAndCharges.add(consumptionValueAndCharge);
                }
                return consumptionValueAndCharges;
            default:
                throw new RuntimeException("type参数错误");
        }
    }

    @Override
    public List<ConsumptionValueAndCharge24> getDeviceConsumptionValue24(String Location,LocalDate date) {

        //通过车间查询设备列表
        List<EcmElectricDevice> deviceList = ecmElectricDeviceService.getDeviceListByLocation(Location);

        return getDeviceConsumptionValue(deviceList,date);
    }

    @Override
    public List<ConsumptionValueAndCharge24> getDeviceConsumptionValue24ByLine(Integer parentId,LocalDate date) {
        List<EcmElectricDevice> deviceList = ecmElectricDeviceService.getDeviceListByParentId(parentId);
        return getDeviceConsumptionValue(deviceList,date);
    }

    @Override
    public ConsumptionValueAndCharge24 calculateHourlyConsumption24(String electricDeviceCode, LocalDate date) {
        EcmElectricDevice electricDevice = ecmElectricDeviceService.getDeviceListByElectricDeviceCode(electricDeviceCode);
        if (electricDevice == null){
            throw new RuntimeException("电表编码不存在!");
        }
        //根据电表编码以及查询时间查询该电表24小时数据
        ConsumptionValueAndCharge consumptionValueAndCharge = calculateHourlyConsumption(electricDeviceCode, date);
        int[] consumptionValues = consumptionValueAndCharge.getConsumptionValues();
        int[] consumptionValues24 = new int[25];
        consumptionValues24[0] = consumptionValues[0];
        for (int i = 2; i < consumptionValues.length+1; i = i+2) {
            consumptionValues24[i/2] =  consumptionValues[i] + consumptionValues[i-1];
        }
        ConsumptionValueAndCharge24 consumptionValueAndCharge24 = new ConsumptionValueAndCharge24();
        consumptionValueAndCharge24.setElectricDeviceCode(electricDeviceCode);
        consumptionValueAndCharge24.setElectricDeviceName(electricDevice.getElectricDeviceName());
        consumptionValueAndCharge24.setRemark(electricDevice.getRemark());
        consumptionValueAndCharge24.setConsumptionValues(consumptionValues24);
        return consumptionValueAndCharge24;
    }

    public List<ConsumptionValueAndCharge24> getDeviceConsumptionValue(List<EcmElectricDevice> deviceList,LocalDate date){

        List<ConsumptionValueAndCharge24> list = new ArrayList<>();

        for (EcmElectricDevice ecmElectricDevice : deviceList) {
            //电表编码
            String electricDeviceCode = ecmElectricDevice.getElectricDeviceCode();
            //机台名称
            String electricDeviceName = ecmElectricDevice.getElectricDeviceName();
            //备注
            String remark = ecmElectricDevice.getRemark();
            //根据电表编码以及查询时间查询该电表24小时数据
            ConsumptionValueAndCharge consumptionValueAndCharge = calculateHourlyConsumption(electricDeviceCode, date);
            int[] consumptionValues = consumptionValueAndCharge.getConsumptionValues();
            int[] consumptionValues24 = new int[25];
            consumptionValues24[0] = consumptionValues[0];
            for (int i = 2; i < consumptionValues.length+1; i = i+2) {
                consumptionValues24[i/2] =  consumptionValues[i] + consumptionValues[i-1];
            }
            ConsumptionValueAndCharge24 consumptionValueAndCharge24 = new ConsumptionValueAndCharge24();
            consumptionValueAndCharge24.setElectricDeviceCode(electricDeviceCode);
            consumptionValueAndCharge24.setElectricDeviceName(electricDeviceName);
            consumptionValueAndCharge24.setRemark(remark);
            consumptionValueAndCharge24.setConsumptionValues(consumptionValues24);
            list.add(consumptionValueAndCharge24);
        }
        return list;
    }




    @Override
    public List<ElectricityConsumptionDate> calculateTotalElectricityConsumption(String location, String type ,String date) {
        //通过车间查询设备列表
        List<EcmElectricDevice> deviceList = ecmElectricDeviceService.getDeviceListByLocation(location);
        Boolean res = isNow(type, date);
        switch (type){
            case "年":
                for (EcmElectricDevice ecmElectricDevice : deviceList) {
                    String electricDeviceCode = ecmElectricDevice.getElectricDeviceCode();
//                    EcmDataCache ecmDataCache = ecmDataCacheMapper.selectEcmDataCacheByElectricDevice(electricDeviceCode, date, type);
//                    if (ecmDataCache == null){
//
//                    }
                }
                return null;
            case "季":
                return null;
            case "月":
                return null;
            default:
                throw new RuntimeException("type参数错误");
        }
    }

    @Override
    public List<ElectricityConsumptionDate> calculateTotalSummaryTableDate(String beginTime, String endTime) {
        return ecmElectricityConsumptionMapper.selectTotalConsumptionDate(beginTime, endTime);
    }

    @Override
    public List<ElectricityConsumptionDate> calculateTotalSummaryTableDateByLocation(String beginTime, String endTime) {
        return ecmElectricityConsumptionMapper.selectTotalConsumptionDateByLocation(beginTime, endTime);
    }

    public  Boolean isNow(String type, String date){
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 获取年、月、日
        String year = String.valueOf(currentDate.getYear());
        String month = String.valueOf(currentDate.getMonthValue());
        String yearMonth = year + "-" + month;
        switch (type){
            case "年":
                return !date.equals(year);
            case "季":
                String[] split = date.split("-");
                String quarterYear = split[0];
                String quarter = split[1];
                int mon = (Integer.parseInt(quarter) -1) * 3;
                int monthNow = Integer.parseInt(month);
                return quarterYear.equals(year) && monthNow > mon;
            case "月":
                return !date.equals(yearMonth);
            default:
                throw new RuntimeException("type参数错误");
        }
    }


    @Override
    public KanbanPage getKanban() {
        //根据车间获取年、月、周的电表数据
        KanbanPage kanbanPage = new KanbanPage();

        List<ElectricQuantity> workshopConsumptionEnamelledYear = ecmElectricityConsumptionMapper.getWorkshopConsumptionEnamelledYear();

        List<WeeksElectricQuantity> workshopConsumptionEnamelledMonthWeek = ecmElectricityConsumptionMapper.getWorkshopConsumptionEnamelledMonthWeek();

        List<ElectricQuantity> workshopConsumptionEnamelledMonth = ecmElectricityConsumptionMapper.getWorkshopConsumptionEnamelledMonth();

        kanbanPage.setMonthElectricQuantity(workshopConsumptionEnamelledMonth);
        kanbanPage.setWeeksElectricQuantity(workshopConsumptionEnamelledMonthWeek);
        kanbanPage.setYearElectricQuantity(workshopConsumptionEnamelledYear);

        return kanbanPage;
    }

}
