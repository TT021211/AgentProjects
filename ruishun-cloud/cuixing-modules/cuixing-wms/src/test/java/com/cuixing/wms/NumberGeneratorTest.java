package com.cuixing.wms;

import com.cuixing.wms.utils.NumberGenerator;

import java.util.Date;

public class NumberGeneratorTest {

    public static void main(String[] args) {
        Date currentDate = new Date(); // 当前日期

        // 生成多个带有 ZT 前缀的编号
        for (int i = 0; i < 10; i++) {
            String ztNumber = NumberGenerator.generateNumber("ZT", currentDate);
            System.out.println("生成的 ZT 编号: " + ztNumber);
        }

        // 生成多个带有 GB 前缀的编号
        for (int i = 0; i < 5; i++) {
            String gbNumber = NumberGenerator.generateNumber("GB", currentDate);
            System.out.println("生成的 GB 编号: " + gbNumber);
        }

        // 模拟第二天
        Date nextDay = new Date(currentDate.getTime() + 24 * 60 * 60 * 1000); // 假设当前时间是28号，nextDay是29号
        for (int i = 0; i < 5; i++) {
            String ztNumber = NumberGenerator.generateNumber("ZT", nextDay);
            System.out.println("生成的 ZT 编号: " + ztNumber);
        }

        for (int i = 0; i < 5; i++) {
            String gbNumber = NumberGenerator.generateNumber("GB", nextDay);
            System.out.println("生成的 GB 编号: " + gbNumber);
        }
    }
}
