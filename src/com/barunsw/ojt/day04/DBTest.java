package com.barunsw.ojt.day04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

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
		
		String pstmtSql = String.format("INSERT INTO TB_PERSON(ID, AGE, NAME) "
				+ "VALUES (?, ?, ?)");
		
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
			Statement stmt = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement(pstmtSql);) {
			LOGGER.debug("conn:" + conn);
			
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM TB_PERSON");
			while (resultSet.next()) {
				String id 	= resultSet.getString(1);
				String age 	= resultSet.getString(2);
				String name	= resultSet.getString(3);
				
				LOGGER.debug(String.format("id:%s, age:%s, name:%s", id, age, name));
			}
			
//			String insertSql = String.format("INSERT INTO TB_PERSON(ID, AGE, NAME) VALUES ('%s', '%s', '%s')", "gildong", 30, "홍길동");
//			int result = stmt.executeUpdate(insertSql);
//			
//			LOGGER.debug("result:" + result);
			
			pstmt.setString(1, "gwansoon");
			pstmt.setInt(2, 20);
			pstmt.setString(3, "유관순");
			
			int result2 = pstmt.executeUpdate();
			
			LOGGER.debug("result2:" + result2);
		}
	}
}
