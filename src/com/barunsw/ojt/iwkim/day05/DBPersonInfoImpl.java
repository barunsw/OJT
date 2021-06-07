package com.barunsw.ojt.iwkim.day05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.PersonInfoInterface;
import com.barunsw.ojt.iwkim.common.PersonInfo;

public class DBPersonInfoImpl implements PersonInfoInterface{
	private static Logger LOGGER = LogManager.getLogger(ObjectFilePersonInfoImpl.class);
	
//	private static final String TABLE_NAME = "person";
//	private static final String SELECT_PERSON_INFO = "SELECT * FROM " + TABLE_NAME + " WHERE name=? ";
//	private static final String INSERT_PERSON_INFO = "INSERT INTO " + TABLE_NAME
//			+ "(name, gender, birth, email, regDate, updateDate) VALUES(?,?,?,?,?,?) ";
//	private static final String UPDATE_PERSON_INFO = "UPDATE " + TABLE_NAME 
//			+ " SET gender=?, birth=?, email=?, updateDate=? WHERE name=? ";
//	private static final String DELETE_PERSON_INFO = "DELETE FROM " + TABLE_NAME + " WHERE name=?";
//	private static final String SELECT_IS_EXIST_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE name=?";
//	
//	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	Date date = null;
	
	private Connection connection = null;
//	PreparedStatement pstmt = null;
//	int queryExcResult = 0;
//	ResultSet rs = null;
//	String sql = null;
//	PersonInfo person = null;
	
	public DBPersonInfoImpl() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			LOGGER.info(e.getMessage(), e);
		}
	}
	
	// DB 연결 메서드
	private Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/study01?autoReconnect=true", 
						"root", 
						"barun");
			}
		}
		catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
		}
		
		return connection;
	}
	
	// 수정, 삭제, 삽입 시 자원해제 메서드
	private void close(Connection conn, PreparedStatement pstmt) {
		try {
			if ( pstmt != null ) {
				pstmt.close();
			}
			if ( conn != null ) {
				conn.close();
			}
		}
		catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
		}
	} 
	
	// 조회 시 자원해제 메서드
	private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if ( rs != null ) {
				rs.close();
			}
			if ( pstmt != null ) {
				pstmt.close();
			}
			if ( conn != null ) {
				conn.close();
			}
		}
		catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
		}
	}
	
	@Override
	public PersonInfo select(String name) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PersonInfo person = null;
		
		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM person WHERE name=? ");
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				person = new PersonInfo();
				person.setName(rs.getString("name"));
				person.setGender(rs.getString("gender"));
				person.setBirth(rs.getString("birth"));
				person.setEmail(rs.getString("email"));
				person.setRegDate(rs.getString("regDate"));
				person.setUpdateDate(rs.getString("updateDate"));
			}
		}
		finally {
			if (rs != null) {
				rs.close();
			}			
			
			if (pstmt != null) {
				pstmt.close();
			}
		}
		
//		close(conn, pstmt, rs);
		return person;
	}

	@Override
	public int insertPerson(PersonInfo param) throws Exception {
		Connection conn = getConnection();
		date = new Date();
		pstmt = conn.prepareStatement(INSERT_PERSON_INFO);
		pstmt.setString(1, param.getName());
		pstmt.setString(2, param.getGender());
		pstmt.setString(3, param.getBirth());
		pstmt.setString(4, param.getEmail());
		pstmt.setString(5, format.format(date));
		pstmt.setString(6, format.format(date));
		int queryExcResult = pstmt.executeUpdate();
		close(conn, pstmt);
		return queryExcResult;
	}

	@Override
	public int updatePerson(PersonInfo param) throws Exception {
		Connection conn = getConnection();
		pstmt = conn.prepareStatement(UPDATE_PERSON_INFO);
		pstmt.setString(1, param.getGender());
		pstmt.setString(2, param.getBirth());
		pstmt.setString(3, param.getEmail());
		pstmt.setString(4, format.format(new Date()));
		pstmt.setString(5, param.getName());
		queryExcResult = pstmt.executeUpdate();
		close(conn, pstmt);
		return queryExcResult;
	}

	@Override
	public int deletePerson(String name) throws Exception {
		Connection conn = getConnection();
		pstmt = conn.prepareStatement(DELETE_PERSON_INFO);
		pstmt.setString(1, name);
		queryExcResult = pstmt.executeUpdate();
		close(conn, pstmt);
		return queryExcResult;
	}

	@Override
	public boolean isExistName(String name) throws Exception {
		Connection conn = getConnection();
		pstmt = conn.prepareStatement(SELECT_IS_EXIST_NAME);
		pstmt.setString(1, name);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}
		close(conn, pstmt, rs);
		return false;
	}
}
