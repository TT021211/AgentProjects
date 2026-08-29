package com.cuixing.mold.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 保养计划时间计算工具
 */
public class MaintainPlanUtils {
    public static int getFrequencyCount(String frequency) {
        // 假设 frequency 传入的格式为 "x月x次"
        String[] parts = frequency.split("月");
        return Integer.parseInt(parts[1].replace("次", ""));
    }

    public static int getMonthInterval(String frequency) {
        String[] parts = frequency.split("月");
        return Integer.parseInt(parts[0]);
    }

    public static LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static List<LocalDate> generateInspectionDates(LocalDate startDate, LocalDate endDate, int timesPerMonth) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate datePointer = startDate;

        while (!datePointer.isAfter(endDate)) {
            long daysInMonth = datePointer.lengthOfMonth();
            long interval = daysInMonth / timesPerMonth;

            // 如果开始日期在本月，需要判断是否在当月添加日期，然后才增加月份
            if (datePointer.getMonthValue() == startDate.getMonthValue()) {
                long dayOfMonth = datePointer.getDayOfMonth();
                for (int i = 0; i < timesPerMonth; i++) {
                    LocalDate date = datePointer.withDayOfMonth((int) (Math.min(i * interval + 1, dayOfMonth)));
                    if (!date.isBefore(startDate) && !date.isAfter(endDate) && !dates.contains(date)) {
                        dates.add(date);
                    }
                }
            } else {
                for (int i = 0; i < timesPerMonth; i++) {
                    LocalDate date = datePointer.withDayOfMonth((int) (Math.min(i * interval + 1, datePointer.lengthOfMonth())));
                    if (!date.isAfter(endDate) && !dates.contains(date)) {
                        dates.add(date);
                    }
                }
            }

            datePointer = datePointer.plusMonths(1);
            datePointer = datePointer.withDayOfMonth(1);
        }

        return dates;
    }

    // 将字符串列表转换为BigDecimal对象列表
    public static List<BigDecimal> convertToBigDecimals(List<String> decimalStrings) {
        List<BigDecimal> decimalList = new ArrayList<>();
        for (String decimalStr : decimalStrings) {
            decimalList.add(new BigDecimal(decimalStr));
        }
        return decimalList;
    }

    // 将BigDecimal列表转换回字符串列表
    public static List<String> convertToStringList(List<BigDecimal> decimalList) {
        List<String> stringList = new ArrayList<>();
        for (BigDecimal decimal : decimalList) {
            stringList.add(decimal.toString());
        }
        return stringList;
    }
}
