package com.barunsw.ojt.gtkim.day05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;

public class DBTestBasic {
	private static Logger LOGGER = LogManager.getLogger(DBTestBasic.class);

	public static void main(String[] args) {
		LOGGER.debug("DBTest1");
		final String url = "jdbc:mysql://localhost:3306/OJT?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
		final String user = "root";
		final String passwd = "gtkim1268";

		try {
			// jdbc 4.0이후는 class.forname 호출을 안해도 자동 로드가 됨
			Class.forName("org.mariadb.jdbc.Driver");
			
			try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
			
				List<Person> personList = new ArrayList<>();
				Statement stmt = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					//Statement 준비
					stmt = conn.createStatement();
					
					String selectSql = "SELECT * FROM TB_PERSON";
					rs = stmt.executeQuery(selectSql);
				
					rs.last();
					int rowCount = rs.getRow();
					if (rowCount < 5) {
						// Insert문장
						String insertSql = "INSERT INTO TB_PERSON VALUES ("
								+ "0, 'ktkim', 'MAN', 22, '010-2410-1268', '서울시 송파구')";
						stmt.execute(insertSql);
						LOGGER.debug("Insert 수행");
					}
					// Select문장
					rs = stmt.executeQuery(selectSql);
					while (rs.next()) {
						// rs의 인덱스는 1번부터 시작!
						String name = rs.getString(2);
						Gender gender = Gender.toGender(rs.getString(3));
						int age = rs.getInt(4);
						String phone = rs.getString(5);
						String address = rs.getString(6);

						Person onePerson = new Person();
						onePerson.setName(name);
						onePerson.setGender(gender);
						onePerson.setAge(age);
						onePerson.setPhone(phone);
						onePerson.setAddress(address);
						LOGGER.debug("person : " + onePerson);

						personList.add(onePerson);
					}
					
					// Update문장
					String updateSql = "UPDATE TB_PERSON SET age=32 WHERE name=?";
					pstmt = conn.prepareStatement(updateSql);
					pstmt.setString(1, "gtkim");
					pstmt.execute();
					LOGGER.debug("update 수행!");

					// Delete문장
					String deleteSql = "DELETE FROM TB_PERSON WHERE NAME='ktkim'";
					rs = stmt.executeQuery(deleteSql);
					LOGGER.debug("delete 수행!");
				} catch (Exception e) {
					LOGGER.debug(e.getMessage(), e);
				} finally {
					if (rs != null) {
						try {
							rs.close();
						} catch (Exception e) {
							LOGGER.error(e.getMessage(), e);
						}
					}
					if (stmt != null) {
						try {
							stmt.close();
						} catch (Exception e) {
							LOGGER.error(e.getMessage(), e);
						}
					}
					if (pstmt != null) {
						try {
							pstmt.close();
						} catch (Exception e) {
							LOGGER.error(e.getMessage(), e);
						}
					}
				}
				LOGGER.debug(String.format("stmt is closesd : %s, pstmt is closed : %s, rs is closed : %s ",
						stmt.isClosed(), pstmt.isClosed(), rs.isClosed()));
			} catch (SQLException sqle) {
				LOGGER.error(sqle.getMessage(), sqle);
			}
		} catch (ClassNotFoundException cnfe) {
			LOGGER.error(cnfe.getMessage(), cnfe);
		}
	}
}
