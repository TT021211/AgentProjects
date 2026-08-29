package com.cuixing.wms.utils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class NumberGenerator2 {

    private static final ConcurrentHashMap<String, Integer> counterCache = new ConcurrentHashMap<>();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private static final String DB_URL = "jdbc:mysql://localhost/rsmes_cloud?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    static {
        // 初始化数据库连接并加载计数器
        loadCounterFromDatabase();
    }

    public static synchronized String generateNumber(String prefix, Date date) {
        String formattedPrefix = prefix + dateFormat.format(date);

        // 从缓存获取计数器，如果不存在则从数据库加载
        Integer counter = counterCache.get(formattedPrefix);
        if (counter == null) {
            counter = loadCounterFromDatabase(formattedPrefix);
            counterCache.put(formattedPrefix, counter);
        }

        // 生成编号
        String number = String.format("%s%05d", formattedPrefix, counter);

        // 更新计数器
        counter++;
        counterCache.put(formattedPrefix, counter);

        // 保存到数据库
        saveCounterToDatabase(formattedPrefix, counter);

        return number;
    }

    private static void loadCounterFromDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT prefix_date, counter FROM number_counter")) {
            while (rs.next()) {
                String prefixDate = rs.getString("prefix_date");
                int counter = rs.getInt("counter");
                counterCache.put(prefixDate, counter);
            }
        } catch (SQLException e) {
            System.err.println("Failed to load counters from database: " + e.getMessage());
        }
    }

    private static int loadCounterFromDatabase(String formattedPrefix) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // 尝试获取计数器
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT counter FROM number_counter WHERE prefix_date = ?")) {
                pstmt.setString(1, formattedPrefix);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("counter");
                }
            }

            // 如果不存在，初始化计数器为 1
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO number_counter (prefix_date, counter) VALUES (?, 1) "
                            + "ON DUPLICATE KEY UPDATE counter = counter")) {
                pstmt.setString(1, formattedPrefix);
                pstmt.executeUpdate();
                return 1;
            }
        } catch (SQLException e) {
            System.err.println("Failed to load counter for " + formattedPrefix + ": " + e.getMessage());
            return 1; // 默认值
        }
    }

    private static void saveCounterToDatabase(String formattedPrefix, int counter) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO number_counter (prefix_date, counter) VALUES (?, ?) "
                             + "ON DUPLICATE KEY UPDATE counter = ?")) {
            pstmt.setString(1, formattedPrefix);
            pstmt.setInt(2, counter);
            pstmt.setInt(3, counter);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to save counter for " + formattedPrefix + ": " + e.getMessage());
        }
    }
}
