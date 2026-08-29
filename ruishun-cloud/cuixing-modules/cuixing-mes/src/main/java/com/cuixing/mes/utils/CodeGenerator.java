package com.cuixing.mes.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class CodeGenerator {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final AtomicInteger counter = new AtomicInteger(1);

    /**
     * 生成编码
     * @param dateStr 日期字符串，格式为 yyyy-MM-dd HH:mm:ss
     * @param prefix 两个字母的前缀
     * @return 生成的编码
     * @throws ParseException 如果日期格式不正确
     */
    public static String generateCode(String dateStr, String prefix) throws ParseException {
        // 验证前缀是否为两个字母
        if (prefix == null || !prefix.matches("[A-Za-z]{2}")) {
            throw new IllegalArgumentException("前缀必须为两个字母");
        }

        // 解析日期
        Date date = DATE_FORMAT.parse(dateStr);
        SimpleDateFormat codeDateFormat = new SimpleDateFormat("yyMMddHHmmss");
        String datePart = codeDateFormat.format(date);

        // 获取递增序号，格式化为三位数
        int sequence = counter.getAndIncrement();
        if (sequence > 999) {
            counter.set(1); // 重置计数器
            sequence = 1;
        }
        String sequencePart = String.format("%03d", sequence);

        // 组合编码：日期部分 + 前缀 + 序号
        return datePart + prefix + sequencePart;
    }

    /**
     * 重置计数器
     */
    public static void resetCounter() {
        counter.set(1);
    }

    public static void main(String[] args) {
        try {
            // 示例用法
            String dateStr = "2025-06-26 15:22:00";
            String prefix = "LS";
            String code = generateCode(dateStr, prefix);
            System.out.println("生成的编码: " + code);

            // 生成下一个编码
            String nextCode = generateCode(dateStr, prefix);
            System.out.println("下一个编码: " + nextCode);

            // 重置计数器后生成
            resetCounter();
            String resetCode = generateCode(dateStr, prefix);
            System.out.println("重置后编码: " + resetCode);
        } catch (ParseException e) {
            System.err.println("日期格式错误: " + e.getMessage());
        }
    }
}