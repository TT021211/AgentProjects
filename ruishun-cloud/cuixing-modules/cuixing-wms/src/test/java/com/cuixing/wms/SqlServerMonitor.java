package com.cuixing.wms;

import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootTest
public class SqlServerMonitor {
    private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_URL = "jdbc:sqlserver://192.168.1.102:1433;databaseName=QME";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345678";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        final ResultSet[] rs = {null};

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();

            // 执行查询语句
            String sql = "SELECT * FROM 常规项目";
            rs[0] = stmt.executeQuery(sql);

            // 获取初始数据
            while (rs[0].next()) {
                // 处理查询结果

            }

            // 使用定时器定时查询数据库变化
            Timer timer = new Timer();
            Statement finalStmt = stmt;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        rs[0] = finalStmt.executeQuery(sql);
                        while (rs[0].next()) {
                            // 处理查询结果
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }, 1000, 1000); // 每隔1秒查询一次

            // 程序运行一段时间后，关闭连接
            Thread.sleep(60000);
            timer.cancel();
            rs[0].close();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs[0] != null) rs[0].close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
