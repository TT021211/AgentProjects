package com.cuixing.wms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class OrderCodeGenerator {
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyMMdd");
    private static final AtomicLong counter = new AtomicLong(1);

    public static String generateOrderCode(Date createTime) {
        if (createTime == null) {
            throw new IllegalArgumentException("createTime cannot be null");
        }
        String datePart = DATE_FORMATTER.format(createTime);
        long sequence = counter.getAndIncrement();
        return String.format("ORDER_%s%08d", datePart, sequence);
    }

    // 重置计数器（可选，用于测试或特定场景）
    public static void resetCounter() {
        counter.set(0);
    }
}