package com.barunsw.ojt.sjcha.day08;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.sjcha.day08.AddressVo;

public class DbAddressBookImplement implements AddressBookInterface {

	private static final Logger LOGGER = LogManager.getLogger(FileAddressBookImplement.class);
	// DB 접속 주소
	private final static String DB_URL = "jdbc:mysql://localhost:3306/OJT?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
	// DB ID
	private final static String USERNAME = "root";
	// DB password
	private final static String PASSWORD = "&YOUha6644";

	public DbAddressBookImplement() {
		// TODO Auto-generated constructor stub
		// Class 클래스의 forName 함수를 이용해서 해당 클래스를 메모리에 로드
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	// 주소록 조회
	@Override
	public List<AddressVo> selectAddressList(AddressVo person) {
		List<AddressVo> personList = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement();) {

			ResultSet resultSet1 = statement.executeQuery("SELECT * FROM TB_PERSON");

			while (resultSet1.next()) {
				// getString은 값을 가져온다
				String seq = resultSet1.getString(1);
				String name = resultSet1.getString(2);
				String gender = resultSet1.getString(3);
				String age = resultSet1.getString(4);
				String phonenumber = resultSet1.getString(5);
				String address = resultSet1.getString(6);

				AddressVo personInfo = new AddressVo();
				
				personInfo.setName(name);
				personInfo.setGender(Gender.toGender(gender));
				personInfo.setAge(Integer.parseInt(age));
				personInfo.setPhone(phonenumber);
				personInfo.setAddress(address);
				LOGGER.debug(String.format("onePerson : %s", personInfo));

				personList.add(personInfo);
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

		return personList;
	}

	// 주소록 데이터 추가 - 추가 버튼 클릭
	@Override
	public int insertAddress(AddressVo person) throws Exception {

		String preparedSql = String.format("INSERT INTO TB_PERSON(NAME, AGE, PHONE, ADDRESS) " + "VALUES (?, ?, ?, ?)");

		try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(preparedSql);) {
			preparedStatement.setString(1, person.getName());
			// preparedStatement.setString(2, Gender.toGender(person.getGender()));
			preparedStatement.setInt(2, person.getAge());
			preparedStatement.setString(3, person.getPhone());
			preparedStatement.setString(4, person.getAddress());

			int insertResult = preparedStatement.executeUpdate();

			LOGGER.debug("insert result : " + insertResult);
		}
		return 0;
	}

	// 주소록 데이터 업데이트 - 변경 버튼 클릭
	@Override
	public int updateAddress(AddressVo person) throws Exception {

		String sql = String.format("UPDATE TB_PERSON SET GENDER=?, AGE=?, PHONE=?, ADDRESS=? WHERE NAME=?");

		try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			// preparedStatement.setString(1, person.getName());
			preparedStatement.setString(1, "WOMAN");
			preparedStatement.setInt(2, person.getAge());
			preparedStatement.setString(3, person.getPhone());
			preparedStatement.setString(4, person.getAddress());
			preparedStatement.setString(5, person.getName());

			int updateResult = preparedStatement.executeUpdate();

			LOGGER.debug("update result : " + updateResult);

		}
		return 0;
	}

	// 주소록 데이터 삭제 - 오른쪽 마우스 버튼 클릭
	@Override
	public int deleteAddress(AddressVo person) throws Exception {

		String sql = String.format("DELETE FROM TB_PERSON WHERE NAME=?");
		List<AddressVo> personList = selectAddressList(person);
		
		try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

			int addressBookCount = personList.size();
			int deleteIndex = -1;

			for (int i = 0; i < addressBookCount; i++) {
				AddressVo oneAddress = personList.get(i);
				if (oneAddress != null && oneAddress.getName().equals(person.getName())) {
					deleteIndex = i;
					preparedStatement.setString(1, person.getName());
					LOGGER.debug(deleteIndex);
					break;
				}
			}

			int deleteResult = preparedStatement.executeUpdate();

			LOGGER.debug("delete result : " + deleteResult);
		}

		return 0;
	}
}
