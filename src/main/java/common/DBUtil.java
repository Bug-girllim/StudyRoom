package common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBUtil {

    private static final Properties queries = new Properties();

    static {
        try (InputStream is = DBUtil.class
                .getClassLoader()
                .getResourceAsStream("queries.xml")) {

            if (is == null) {
                throw new IllegalStateException(
                        "queries.xml 파일을 찾을 수 없습니다.");
            }

            queries.loadFromXML(is);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "SQL 설정 파일을 읽을 수 없습니다.", e);
        }
    }

    // queries.xml에서 SQL문 가져오기
    public static String getQuery(String key) {

        String sql = queries.getProperty(key);

        if (sql == null) {
            throw new IllegalArgumentException(
                    "SQL을 찾을 수 없습니다: " + key);
        }

        return sql;
    }
}

