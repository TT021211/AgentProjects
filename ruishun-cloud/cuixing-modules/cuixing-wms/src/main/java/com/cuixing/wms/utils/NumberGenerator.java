package com.cuixing.wms.utils;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NumberGenerator {

    private static final ConcurrentHashMap<String, Integer> counterCache = new ConcurrentHashMap<>();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private static final String COUNTER_FILE = "randomCode.properties";

    static {
        loadCounterFromFile();
    }

    public static synchronized String generateNumber(String prefix, Date date) {
        String formattedPrefix = prefix + dateFormat.format(date);

        // 获取当天的计数器，如果没有则初始化为 1
        int counter = counterCache.computeIfAbsent(formattedPrefix, k -> 1);

        // 生成编号
        String number = String.format("%s%05d", formattedPrefix, counter);

        // 更新计数器
        counterCache.put(formattedPrefix, counter + 1);

        // 保存计数器到文件
        saveCounterToFile();

        return number;
    }

    private static void loadCounterFromFile() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(COUNTER_FILE)) {
            properties.load(fis);
            for (String key : properties.stringPropertyNames()) {
                int value = Integer.parseInt(properties.getProperty(key));
                counterCache.put(key, value);
            }
        } catch (IOException e) {
            // 文件不存在或读取失败，忽略
        }
    }

    private static void saveCounterToFile() {
        Properties properties = new Properties();
        for (String key : counterCache.keySet()) {
            properties.setProperty(key, String.valueOf(counterCache.get(key)));
        }
        try (FileOutputStream fos = new FileOutputStream(COUNTER_FILE)) {
            properties.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
