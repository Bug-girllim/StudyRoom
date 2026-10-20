package common.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTemplate {
    // 접속 정보는 프로젝트 루트의 db.properties에서 읽어 온다
    private static final Properties prop = new Properties();

    // static 블록은 정적 코드 블록으로서
    // 클래스가 로드 될 때 한 번만 실행된다.
    static {
        try {
            prop.load(JDBCTemplate.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // DB 와 새로운 커넥션을 맺어 반환하는 메서드
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                prop.getProperty("url"),
                prop.getProperty("user"),
                prop.getProperty("password")
        );
    }

}