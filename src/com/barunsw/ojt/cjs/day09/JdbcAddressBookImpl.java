package com.barunsw.ojt.cjs.day09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.vo.AddressVo;

public class JdbcAddressBookImpl implements AddressBookInterface {
	private static Logger LOGGER = LoggerFactory.getLogger(JdbcAddressBookImpl.class);
	private final static String URL = "jdbc:mysql://localhost:3306/OJT?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
	private final static String USER = "root";
	private final static String PASSWD = "js0911";

	public JdbcAddressBookImpl() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) {
		List<AddressVo> addressList = new ArrayList<>();
		String SQL = "SELECT * FROM ADDRESSBOOK";
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
				PreparedStatement psmt = conn.prepareStatement(SQL);) { 
			ResultSet resultSet = psmt.executeQuery(); // select구문은 executeQuery, 그 외에는 executeUpdate
			while (resultSet.next()) {
				String seq = resultSet.getString(1);
				String name = resultSet.getString(2);
				String gender = resultSet.getString(3);
				String age = resultSet.getString(4);
				String address = resultSet.getString(5);
				AddressVo addressObject = new AddressVo();

				addressObject.setName(name);
				addressObject.setAge(Integer.parseInt(age));
				addressObject.setGender(Gender.toGender(gender));
				addressObject.setAddress(address);

				addressList.add(addressObject);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return addressList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		String SQL = "INSERT INTO ADDRESSBOOK (NAME, AGE, GENDER, ADDRESS)" + "VALUES (?,?,CAST(? AS VARCHAR),?)";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
				PreparedStatement psmt = conn.prepareStatement(SQL);) { // statement 객체 생성

			psmt.setString(1, addressVo.getName());
			psmt.setInt(2, addressVo.getAge());
			psmt.setString(3, String.valueOf(addressVo.getGender()));
			psmt.setString(4, addressVo.getAddress());
			psmt.executeUpdate();
		}
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		String SQL = String.format("UPDATE ADDRESSBOOK SET AGE=?, GENDER=?, ADDRESS=? WHERE NAME=?");
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
				PreparedStatement psmt = conn.prepareStatement(SQL);) {
			psmt.setString(1, addressVo.getName());
			psmt.setInt(2, addressVo.getAge());
			psmt.setString(3, String.valueOf(addressVo.getGender()));
			psmt.setString(4, addressVo.getAddress());
			psmt.executeUpdate();
		}
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		String SQL = String.format("DELETE FROM ADDRESSBOOK WHERE NAME=?");
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
				PreparedStatement psmt = conn.prepareStatement(SQL);) {
			psmt.setString(1, addressVo.getName());
			psmt.executeUpdate();
		}
		return 0;
	}

}
