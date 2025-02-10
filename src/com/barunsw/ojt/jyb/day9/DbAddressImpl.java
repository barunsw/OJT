package com.barunsw.ojt.jyb.day9;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbAddressImpl implements AddressBookInterface {
	private static final Logger LOGGER = LoggerFactory.getLogger(DbAddressImpl.class);

	private static String DB_URL;
	private static String USERNAME;
	private static String PASSWORD;
	static {
		Properties properties = new Properties();
		try (InputStream input = DbAddressImpl.class.getClassLoader().getResourceAsStream("jdbc.properties")) {
			properties.load(input);

			DB_URL = properties.getProperty("URL");
			USERNAME = properties.getProperty("USER");
			PASSWORD = properties.getProperty("PASSWORD");

		}
		catch (Exception ex) {
			LOGGER.error("Error loading properties file: " + ex.getMessage(), ex);
		}
	}

	public DbAddressImpl() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	@Override
	public List<AddressVo> selectAddressList(AddressVo person) {
		List<AddressVo> personList = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement()) {

			ResultSet resultSet1 = statement.executeQuery("SELECT * FROM ADDRESS_BOOK");

			while (resultSet1.next()) {
				String seq = resultSet1.getString(1); // seq는 사용하지 않으므로 필요에 따라 제거 가능
				String name = resultSet1.getString(2);
				String age = resultSet1.getString(3);
				String phone = resultSet1.getString(4);
				String address = resultSet1.getString(5);
				String genderStr = resultSet1.getString(6);

				AddressVo personInfo = new AddressVo();

				personInfo.setName(name);
				personInfo.setAge(Integer.parseInt(age));
				personInfo.setPhone(phone);
				personInfo.setAddress(address);

				Gender gender = Gender.toGender(genderStr); // toGender 메서드 사용
				personInfo.setGender(gender);

				personList.add(personInfo);
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

		return personList;
	}

	// 주소록 데이터 업데이트 - 변경 버튼 클릭
	@Override
	public int updateAddress(AddressVo person) throws Exception {
		String sql = "UPDATE ADDRESS_BOOK SET AGE=?, PHONE=?, ADDRESS=? WHERE NAME=?";

		try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setInt(1, person.getAge());
			preparedStatement.setString(2, person.getPhone());
			preparedStatement.setString(3, person.getAddress());
			preparedStatement.setString(4, person.getName());

			int updateResult = preparedStatement.executeUpdate();
			LOGGER.debug("update result : " + updateResult);

			if (updateResult == 0) {
				LOGGER.warn("업데이트 된 행이 없음: " + person.getName());
			}
		}
		catch (SQLException e) {
			LOGGER.error("수정 도중 SQL 예외 발생: ", e);
			throw e;
		}

		return 0;
	}

	@Override
	public int insertAddress(AddressVo person) throws Exception {

		String preparedSql = String
				.format("INSERT INTO ADDRESS_BOOK(NAME, AGE,  PHONE, ADDRESS, GENDER) " + "VALUES (?, ?, ?, ?,?)");

		try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(preparedSql);) {
			preparedStatement.setString(1, person.getName());
			preparedStatement.setInt(2, person.getAge());
			preparedStatement.setString(3, person.getPhone());
			preparedStatement.setString(4, person.getAddress());
			preparedStatement.setString(5, person.getGender().toString());

			int insertResult = preparedStatement.executeUpdate();

			LOGGER.debug("insert result : " + insertResult);
		}
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo person) throws Exception {
		String sql = "DELETE FROM ADDRESS_BOOK WHERE NAME=?";
		List<AddressVo> personList = selectAddressList(person);

		try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			int addressBookCount = personList.size();
			int deleteIndex = -1;

			for (int i = 0; i < addressBookCount; i++) {
				AddressVo oneAddress = personList.get(i);
				if (oneAddress != null && oneAddress.getName().equals(person.getName())) {
					deleteIndex = i;
					break;
				}
			}

			// deleteIndex가 -1이 아닐 때만 삭제를 수행
			if (deleteIndex != -1) {
				preparedStatement.setString(1, person.getName());
				int deleteResult = preparedStatement.executeUpdate();
				LOGGER.debug("delete result : " + deleteResult);

				// 삭제된 행이 없을 경우 경고 로그
				if (deleteResult == 0) {
					LOGGER.warn("삭제된 행이 없음: " + person.getName());
				}
			} else {
				LOGGER.warn("일치하는 이름 없음: " + person.getName());
			}
		}
		catch (SQLException e) {
			LOGGER.error("삭제 도중 SQL 예외 발생: ", e);
			throw e;
		}

		return 0;
	}

}
