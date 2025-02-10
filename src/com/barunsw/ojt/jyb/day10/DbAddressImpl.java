package com.barunsw.ojt.jyb.day10;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbAddressImpl implements AddressBookInterface {
	private static final Logger LOGGER = LoggerFactory.getLogger(DbAddressImpl.class);
	private static String DB_URL;
	private static String USERNAME;
	private static String PASSWORD;

	static { // 클래스가 처음 로드될 때 한 번만 실행 -> DB 연결 정보를 한 번만 읽어오고 초기화. 애플리케이션 실행 동안 동일한 DB 연결 정보를
				// 재사용할 수 있게 하기 위함
		Properties properties = new Properties(); // DB연결에 필요한 설정 저장
		try (InputStream input = DbAddressImpl.class.getClassLoader().getResourceAsStream("jdbc.properties")) {
			// 클래스의 객체에서 클래스로더를 꺼내 jdbc.properties파일을 찾고 InputStream을 반환
			properties.load(input);

			// DB연결 정보 초기화
			DB_URL = properties.getProperty("URL");
			USERNAME = properties.getProperty("USER");
			PASSWORD = properties.getProperty("PASSWORD");
		} catch (Exception ex) {
			LOGGER.error("properties 파일 로딩 중 에러 발생: " + ex.getMessage(), ex);
		}
	}

	public DbAddressImpl() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) {
		ArrayList<AddressVo> list = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				Statement stmt = conn.createStatement()) {

			ResultSet resultSet = stmt.executeQuery("SELECT SEQ, NAME, AGE, GENDER, PHONE, ADDRESS FROM USER_INFO");

			while (resultSet.next()) {
				int seq = resultSet.getInt("SEQ");
				String name = resultSet.getString("NAME");
				int age = resultSet.getInt("AGE");
				String genderStr = resultSet.getString("GENDER");
				String phone = resultSet.getString("PHONE");
				String address = resultSet.getString("ADDRESS");

				Gender gender = Gender.toGender(genderStr); // string을 enum으로 변경

				AddressVo userInfo = new AddressVo();
				userInfo.setSeq(seq);
				userInfo.setName(name);
				userInfo.setAge(age);
				userInfo.setGender(gender);
				userInfo.setPhone(phone);
				userInfo.setAddress(address);

				list.add(userInfo);
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		return list;
	}

	@Override
	public int insertAddress(AddressVo addressVo) {
		String sql = "INSERT INTO USER_INFO(NAME, AGE, GENDER, PHONE, ADDRESS)" + "VALUES(?, ?, ?, ?, ?)";
		int insertCnt = 0;
		try (
				Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, addressVo.getName());
			pstmt.setInt(2, addressVo.getAge());
			pstmt.setString(3, addressVo.getGender().toString());
			pstmt.setString(4, addressVo.getPhone());
			pstmt.setString(5, addressVo.getAddress());

			insertCnt = pstmt.executeUpdate();
			if (insertCnt == 0) {
				LOGGER.warn("insert된 행이 없음: " + addressVo.getName());
			}
			LOGGER.debug("insert된 행 수: " + insertCnt);

		} catch (SQLException e) {
			LOGGER.error("insert 도중 SQL 예외 발생: ", e);
		}
		return insertCnt;
	}

	public int updateAddress(AddressVo addressVo) {
		String sql = "UPDATE USER_INFO SET AGE=?, GENDER = ?, PHONE=?, ADDRESS=? WHERE NAME=?";
		int updateCnt = 0;
		try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, addressVo.getAge());
			pstmt.setString(2, addressVo.getGender().toString());
			pstmt.setString(3, addressVo.getPhone());
			pstmt.setString(4, addressVo.getAddress());
			pstmt.setString(5, addressVo.getName());

			updateCnt = pstmt.executeUpdate();
			if (updateCnt == 0) {
				LOGGER.warn("update된 행이 없음: " + addressVo.getName());
			}
			LOGGER.debug("update된 행 수: " + updateCnt);
		} catch (SQLException e) {
			LOGGER.error("수정 도중 SQL 예외 발생: ", e);
		}
		return updateCnt;
	}

	public int deleteAddress(AddressVo addressVo) {
		String sql = "DELETE FROM USER_INFO WHERE NAME=?";
		int deleteCnt = 0;
		try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, addressVo.getName());

			deleteCnt = pstmt.executeUpdate();
			if (deleteCnt == 0) {
				LOGGER.warn("delete된 행이 없음: " + addressVo.getName());
			}
			LOGGER.debug("delete된 행 수: " + deleteCnt);
		} catch (SQLException e) {
			LOGGER.error("delete 도중 SQL 예외 발생: ", e);
		}
		return deleteCnt;
	}

}
