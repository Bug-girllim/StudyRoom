package common;

import java.io.InputStream;
import java.util.Properties;

public class DBUtil {
    private static final Properties queries = new Properties();

    // XML에서 쿼리를 로드하는 정적 블록
    static {
        try (InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("queries.xml")) {
            queries.loadFromXML(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("쿼리 로딩 중 오류 발생", e);
        }
    }

    public static String getQuery(String key) {
        return queries.getProperty(key).trim();
    }
}

