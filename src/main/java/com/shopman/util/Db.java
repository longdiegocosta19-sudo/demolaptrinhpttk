package com.shopman.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** JDBC connection factory. Configure with SHOPMAN_DB_URL/USER/PASSWORD. */
public final class Db {
    private static final String URL = env(
            "SHOPMAN_DB_URL",
            "jdbc:mysql://localhost:3306/ShopMan_DB?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&characterEncoding=UTF-8"
    );
    private static final String USER = env("SHOPMAN_DB_USER", "root");
    private static final String PASSWORD = env("SHOPMAN_DB_PASSWORD", "");

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new ExceptionInInitializerError("Không tìm thấy MySQL Connector/J: " + ex.getMessage());
        }
    }

    private Db() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static String env(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
