package com.barunsw.ojt.phs.day04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;

public class DBTest {
	private static final Logger LOGGER = LogManager.getLogger(DBTest.class);

	private static final String url = "jdbc:mysql://localhost:3306/OJT?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
	private static final String userName = "root";
	private static final String password = "1234";

	private static final String SELECT = "SELECT * FROM TB_PERSON";
	private static final String INSERT = String.format("INSERT INTO TB_PERSON(NAME, GENDER, AGE) " + "VALUES (?, ?, ?)");
	private static final String UPDATE = "UPDATE TB_PERSON SET AGE=40 WHERE NAME=?";
	private static final String DELETE = "DELETE FROM TB_PERSON WHERE NAME=?";

	private static final DBTest dbTest = new DBTest();

	public DBTest() {
	}

	public DBTest getDBTest() {
		return dbTest;
	}

	public void driverLoad() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			LOGGER.debug("driver 로드 성공");
		} catch (ClassNotFoundException e) {
			LOGGER.debug(e.getMessage());
		}
	}

	public void dbSelect() {
		Connection con = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		List<Person> personList = new ArrayList<Person>();
		try {
			con = DriverManager.getConnection(url, userName, password); // 연결하기
			pStmt = con.prepareStatement(SELECT);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				Person onePerson = new Person();
				onePerson.setName(rs.getString(2));
				onePerson.setGender(Gender.toGender(rs.getString(3)));
				onePerson.setAge(Integer.valueOf(rs.getString(4)));
//				onePerson.setPhone(rs.getString(5));
//				onePerson.setAddress(rs.getString(6));
				personList.add(onePerson);

				LOGGER.debug("person : " + onePerson);
			}
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		} finally {
			closeAll(pStmt,con);
		}
	}

	public void dbInsert() {
		Connection con = null;
		PreparedStatement pStmt = null;
		try {
			con = DriverManager.getConnection(url, userName, password);
			pStmt = con.prepareStatement(INSERT);
			pStmt.setString(1,"Min");
			pStmt.setString(2,"woman");
			pStmt.setInt(3,20);
			int result = pStmt.executeUpdate();
			LOGGER.debug("삽입성공 : " + result);
		}catch(Exception e) {
			LOGGER.debug(e.getMessage());
		}finally {
			closeAll(pStmt,con);
		}
	}

	public void dbUpdate() {
		Connection con = null;
		PreparedStatement pStmt = null;
		try {
			con = DriverManager.getConnection(url, userName, password);
			pStmt = con.prepareStatement(UPDATE);
			pStmt.setString(1, "Min");
			boolean result = pStmt.execute();
			LOGGER.debug("수정 여부 : " + !result);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		} finally {
			closeAll(pStmt,con);
		}
	}

	public void dbDelete() {
		Connection con = null;
		PreparedStatement pStmt = null;
		try {
			con = DriverManager.getConnection(url, userName, password);
			pStmt = con.prepareStatement(DELETE);
			pStmt.setString(1, "Min");
			boolean result = pStmt.execute();
			LOGGER.debug("제거 여부 : " + !result);
		}catch(Exception e) {
			LOGGER.debug(e.getMessage());
		}finally {
			closeAll(pStmt,con);
		}
	}
	
	public static void closeAll(Object... obj) {
		for (Object o:obj) {
			try {
				if (o instanceof PreparedStatement) {
					((PreparedStatement)o).close();
				}
				else if (o instanceof Connection) {
					((Connection)o).close();
				}
			}
			catch (Exception e) {
				LOGGER.debug(e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {

		dbTest.driverLoad();
		dbTest.dbSelect();
//		dbTest.dbInsert();
//		dbTest.dbUpdate();
//		dbTest.dbDelete();

	}
}