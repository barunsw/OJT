package com.barunsw.ojt.cjs.day04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.cjs.day03.Person;
import com.barunsw.ojt.day04.DBTest;

public class DBTest1 {
	private static Logger LOGGER = LoggerFactory.getLogger(DBTest.class);
	private final static String URL = "jdbc:mysql://localhost:3306/OJT?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
	private final static String USER = "root";
	private final static String PASSWD = "js0911";

	public static void main(String[] args) throws Exception {

		Class.forName("org.mariadb.jdbc.Driver"); 

		String pstmtSql = String.format("INSERT INTO TB_PERSON(NAME, AGE, PHONE, ADDRESS) " + "VALUES (?, ?, ?, ?)");

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
				PreparedStatement pstmt = conn.prepareStatement(pstmtSql); // PreparedStatement객체 생성
				Statement stmt = conn.createStatement();) { // statement 객체 생성
			LOGGER.debug("conn:" + conn);

			ResultSet resultSet = stmt.executeQuery("SELECT * FROM TB_PERSON");//ResultSet은 조회된 목록들에 저장된 객체 반환
			while (resultSet.next()) {

				String seq = resultSet.getString(1);
				String name = resultSet.getString(2);
				String gender = resultSet.getString(3);
				String age = resultSet.getString(4);
				String phone = resultSet.getString(5);
				String address = resultSet.getString(6);

				LOGGER.debug(String.format("seq:%s, name:%s, gender:%s, age:%s, phone:%s, address:%s", seq, name,
						gender, age, phone, address));
			}

			// ==================================================================================

			String insertSql = String.format(
					"INSERT INTO TB_PERSON(NAME, AGE, PHONE, ADDRESS) VALUES ('%s', '%s', '%s', '%s')", "홍길동", 30,
					"1234", "서울");

			int result = stmt.executeUpdate(insertSql);
			// executeUpdate(String sql) == 조회문을 제외하고 create, drop, insert, delete, update
			// 등을 처리할 때 사용한다.

			LOGGER.debug("result:" + result);
			// 쿼리문을 반환한 후에는 int 값을 반환한다. == 1

			pstmt.setString(1, "최재석");
			pstmt.setInt(2, 20);
			pstmt.setString(3, "010224271684");
			pstmt.setString(4, "경기도 구리");

			int result2 = pstmt.executeUpdate();
			// 쿼리문을 반환한 후에는 int 값을 반환한다. == 2
			LOGGER.debug("result2:" + result2);
		}
	}

}
