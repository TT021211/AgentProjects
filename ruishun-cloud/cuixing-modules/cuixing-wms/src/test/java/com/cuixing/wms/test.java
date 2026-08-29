package com.cuixing.wms;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static org.quartz.utils.PoolingConnectionProvider.*;

public class test {
    private static final String DB_URL = "jdbc:mysql://localhost/rsmes_cloud?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    @Test
    public void  migratePropertiesToDatabase() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("D:\\cuixing-project\\ruishun-cloud\\randomCode.properties")) {
            properties.load(fis);
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(
                         "INSERT INTO number_counter (prefix_date, counter) VALUES (?, ?) "
                                 + "ON DUPLICATE KEY UPDATE counter = ?")) {
                for (String key : properties.stringPropertyNames()) {
                    int value = Integer.parseInt(properties.getProperty(key));
                    pstmt.setString(1, key);
                    pstmt.setInt(2, value);
                    pstmt.setInt(3, value);
                    pstmt.executeUpdate();
                }
            }
        } catch (IOException | SQLException e) {
            System.err.println("Migration failed: " + e.getMessage());
        }
    }
}
