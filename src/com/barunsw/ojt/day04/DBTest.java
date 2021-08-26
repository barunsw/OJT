package com.barunsw.ojt.day04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBTest {
	private static Logger LOGGER = LogManager.getLogger(DBTest.class);
	
	private final static String URL = "jdbc:mysql://localhost:3306/OJT?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
	private final static String USER = "root";
	private final static String PASSWD = "real3817";

//	private final static String URL = "jdbc:postgresql://localhost:5432/postgres";
//	private final static String USER = "postgres";
//	private final static String PASSWD = "real3817";

	public static void main(String[] args) throws Exception {
		Class.forName("org.mariadb.jdbc.Driver");
//		Class.forName("org.postgresql.Driver");
		
		String pstmtSql = String.format("INSERT INTO TB_PERSON(NAME, AGE, PHONE, ADDRESS) "
				+ "VALUES (?, ?, ?, ?)");
		
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
				PreparedStatement pstmt = conn.prepareStatement(pstmtSql);
			Statement stmt = conn.createStatement();) {
			LOGGER.debug("conn:" + conn);
			
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM TB_PERSON");
			while (resultSet.next()) {
				String seq 		= resultSet.getString(1);
				String name 	= resultSet.getString(2);
				String gender	= resultSet.getString(3);
				String phone	= resultSet.getString(4);
				String address	= resultSet.getString(5);
				
				LOGGER.debug(String.format("seq:%s, name:%s, gender:%s, phone:%s, address:%s", seq, name, gender, phone, address));
			}
			
//			String insertSql = String.format("INSERT INTO TB_PERSON(NAME, AGE, PHONE, ADDRESS) VALUES ('%s', '%s', '%s', '%s')", "홍길동", 30, "1234", "서울");
//			int result = stmt.executeUpdate(insertSql);
//			
//			LOGGER.debug("result:" + result);

//			pstmt.setString(1, "유관순");
//			pstmt.setInt(2, 20);
//			pstmt.setString(3, "5678");
//			pstmt.setString(4, "경기");
//			
//			int result2 = pstmt.executeUpdate();
//			
//			LOGGER.debug("result2:" + result2);
		}
	}
}
