package org.springframework.chapter13;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author xiaokexiang
 * @since 2021/4/28
 */
public class JdbcUtils {
    public static final String JDBC_URL = "jdbc:mysql://10.10.10.5:3306/test?characterEncoding=utf8";
    // 用于传递连接信息
    private static final ThreadLocal<Connection> THREAD_LOCAL = ThreadLocal.withInitial(() -> {
        try {
            return DriverManager.getConnection(JDBC_URL, "bocloud", "a@!#123");
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    });

    public static Connection getConnection() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}

