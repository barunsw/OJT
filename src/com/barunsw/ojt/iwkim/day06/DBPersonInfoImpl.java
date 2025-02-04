package com.barunsw.ojt.iwkim.day06;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.PersonInfoInterface;
import com.barunsw.ojt.iwkim.common.DateUtil;
import com.barunsw.ojt.iwkim.common.PersonInfo;

public class DBPersonInfoImpl implements PersonInfoInterface {
	private static Logger LOGGER = LogManager.getLogger(DBPersonInfoImpl.class);
	
	private Connection connection = null;

	

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
			if (connection == null || connection.isClosed()) { // null이거나 닫혀있으면 connection객체 가져오기
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
		return person;
	}

	@Override
	public int insertPerson(PersonInfo param) throws Exception {
		PreparedStatement pstmt = null;
		int queryExcResult = 0;
		Date date = null;
		
		try {
			Connection conn = getConnection();
			date = new Date();
			pstmt = conn.prepareStatement("INSERT INTO person(name, gender, birth, email, regDate, updateDate) VALUES(?,?,?,?,?,?) ");
			pstmt.setString(1, param.getName());
			pstmt.setString(2, param.getGender());
			pstmt.setString(3, param.getBirth());
			pstmt.setString(4, param.getEmail());
			pstmt.setString(5, DateUtil.DEFAULT_DATE_FORMAT.format(date));
			pstmt.setString(6, DateUtil.DEFAULT_DATE_FORMAT.format(date));
			queryExcResult = pstmt.executeUpdate();
		}
		finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return queryExcResult;
	}

	@Override
	public int updatePerson(PersonInfo param) throws Exception {
		PreparedStatement pstmt = null;
		int queryExcResult = 0;
		
		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement("UPDATE person SET gender=?, birth=?, email=?, updateDate=? WHERE name=? ");
			pstmt.setString(1, param.getGender());
			pstmt.setString(2, param.getBirth());
			pstmt.setString(3, param.getEmail());
			pstmt.setString(4, DateUtil.DEFAULT_DATE_FORMAT.format(new Date()));
			pstmt.setString(5, param.getName());
			queryExcResult = pstmt.executeUpdate();
		}
		finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return queryExcResult;
	}

	@Override
	public int deletePerson(String name) throws Exception {
		PreparedStatement pstmt = null;
		int queryExcResult = 0;
		
		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement("DELETE FROM person WHERE name=?");
			pstmt.setString(1, name);
			queryExcResult = pstmt.executeUpdate();
		}
		finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return queryExcResult;
	}

	@Override
	public boolean isExistName(String name) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM person WHERE name=?");
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
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
		return false;
	}
	
	
	
	
	
	
	
	
	
}
