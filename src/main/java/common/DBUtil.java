package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String URL =
            "jdbc:mysql://localhost:3306/studyroom";

    private static final String USER = "root";

    public static Connection getConnection(String password)
            throws SQLException {

        return DriverManager.getConnection(
                URL, USER, password
        );
    }
}
