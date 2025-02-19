package com.barunsw.ojt.mjg.day19;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerControl {
	private static final Logger LOGGER = LogManager.getLogger(ServerControl.class);

	private static final String MARIA_DB_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String DB_URL_PREFIX = "jdbc:mariadb://";
	private static final String DB_HOST = "localhost";
	private static final String DB_URL_SUFFIX = "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&autoReconnect=true";
	private static final int DB_PORT = 3306;

	private static ServerControl instance = new ServerControl();
	
	// DB Connection 보관용
	private Connection conn = null;
	
	private String currentId;
	private String currentPw;
	private String currentDb;

	public static ServerControl getInstance() {
		return instance;
	}
	
    // getConnection()이 항상 유효한 Connection을 반환하도록 수정
    public synchronized Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                LOGGER.warn("기존 DB 연결이 끊어졌거나 존재하지 않습니다. 재연결을 시도합니다.");
                if (currentId != null && currentPw != null && currentDb != null) {
                    return reconnectToDB();
                } else {
                    LOGGER.error("이전 DB 연결 정보가 없습니다. connectToDB()를 먼저 호출해야 합니다.");
                    return null;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("DB 연결 체크 중 오류 발생: {}", e.getMessage(), e);
            return null;
        }
        return conn;
    }

    // 기존 ID, PW, DB 정보를 사용하여 재연결
    private synchronized Connection reconnectToDB() {
        return connectToDB(currentId, currentPw, currentDb);
    }

    public synchronized Connection connectToDB(String id, String pw, String db) {
        try {
            // MariaDB 드라이버 로드
            Class.forName(MARIA_DB_DRIVER);

            // 기존 연결이 있으면 닫기
            if (conn != null && !conn.isClosed()) {
                try {
                    conn.close();
                    LOGGER.info("이전 DB 연결을 닫음.");
                } 
                catch (SQLException e) {
                    LOGGER.error("이전 DB 연결 닫기 실패: {}", e.getMessage());
                }
            }

            // 입력된 db 값을 DB 이름으로 사용하여 URL 구성
            String url = DB_URL_PREFIX + DB_HOST + ":" + DB_PORT + "/" + db + DB_URL_SUFFIX;

            // **이전 DB 연결 정보 유지**
            this.currentId = id;
            this.currentPw = pw;
            this.currentDb = db;

            conn = DriverManager.getConnection(url, id, pw);
            LOGGER.debug("DB 연결 성공 conn={}", conn);
            return conn;
        } 
        catch (ClassNotFoundException e) {
            LOGGER.error("드라이버 로드 실패: {}", e.getMessage());
            throw new RuntimeException("드라이버 로드 실패", e);
        } 
        catch (SQLException e) {
            LOGGER.error("DB 연결 오류: {}", e.getMessage());
            throw new RuntimeException("DB 연결 오류", e);
        }
    }
}
