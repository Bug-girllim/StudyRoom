package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

    public static Connection getConnection() throws SQLException {

        Properties props = new Properties();

        try (FileInputStream fis =
                     new FileInputStream("db.properties")) {

            props.load(fis);

        } catch (IOException e) {
            throw new SQLException(
                    "DB 설정 파일을 읽을 수 없습니다.", e);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        if (url == null || user == null || password == null) {
            throw new SQLException(
                    "DB 연결 설정이 누락되었습니다.");
        }

        return DriverManager.getConnection(url, user, password);
    }

    public static void close(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement stmt) {
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
