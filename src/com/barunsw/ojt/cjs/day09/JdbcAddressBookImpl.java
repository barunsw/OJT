package com.barunsw.ojt.cjs.day09;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.vo.AddressVo;

public class JdbcAddressBookImpl implements AddressBookInterface {
	
	private static Logger LOGGER = LoggerFactory.getLogger(JdbcAddressBookImpl.class);
	public static Properties jdbcProperties = new Properties();
	private String jdbcInfo[] = new String[3];

	public JdbcAddressBookImpl() throws Exception {
		
		Class.forName("org.mariadb.jdbc.Driver");

		Reader reader = Resources.getResourceAsReader("jdbc.properties");
		jdbcProperties.load(reader);
		int i = 0;
		Iterator<Object> keySet = jdbcProperties.keySet().iterator();
		while (keySet.hasNext()) {
			Object key = keySet.next();
			Object value = jdbcProperties.get(key);
			jdbcInfo[i] = (String) value;
			i++;
			LOGGER.debug(String.format("%s = %s", key, value));
		}
		LOGGER.debug("==============================");
	}

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) {
		List<AddressVo> addressList = new ArrayList<>();
		String SQL = "SELECT * FROM ADDRESSBOOK";
		try (Connection conn = DriverManager.getConnection(jdbcInfo[0], jdbcInfo[2], jdbcInfo[1]);
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
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return addressList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		String SQL = "INSERT INTO ADDRESSBOOK (NAME, AGE, GENDER, ADDRESS)" + "VALUES (?,?,?,?)";

		try (Connection conn = DriverManager.getConnection(jdbcInfo[0], jdbcInfo[2], jdbcInfo[1]);
				PreparedStatement psmt = conn.prepareStatement(SQL);) { // statement 객체 생성

			psmt.setString(1, addressVo.getName());
			psmt.setInt(2, addressVo.getAge());
			psmt.setString(3, String.valueOf(addressVo.getGender()));
			psmt.setString(4, addressVo.getAddress());
			psmt.executeUpdate();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		String SQL = String.format("UPDATE ADDRESSBOOK SET AGE=?, GENDER=?, ADDRESS=? WHERE NAME=?");
		try (Connection conn = DriverManager.getConnection(jdbcInfo[0], jdbcInfo[2], jdbcInfo[1]);
				PreparedStatement psmt = conn.prepareStatement(SQL);) {
			psmt.setString(1, addressVo.getName());
			psmt.setInt(2, addressVo.getAge());
			psmt.setString(3, String.valueOf(addressVo.getGender()));
			psmt.setString(4, addressVo.getAddress());
			psmt.executeUpdate();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		String SQL = String.format("DELETE FROM ADDRESSBOOK WHERE NAME=?");
		try (Connection conn = DriverManager.getConnection(jdbcInfo[0], jdbcInfo[2], jdbcInfo[1]);
				PreparedStatement psmt = conn.prepareStatement(SQL);) {
			psmt.setString(1, addressVo.getName());
			psmt.executeUpdate();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		return 0;
	}
}
