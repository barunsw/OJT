package day4;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcAddressBookImpl implements AddressBookInterface {

	private static Logger logger = LoggerFactory.getLogger(JdbcAddressBookImpl.class);

	public static Properties jdbcProperties = new Properties();
	private String driverName;
	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	public JdbcAddressBookImpl() throws Exception {
		Reader reader = Resources.getResourceAsReader("jdbc.properties");
		jdbcProperties.load(reader);
		driverName = (String) jdbcProperties.get("DRIVER_NAME");
		dbUrl = (String) jdbcProperties.get("URL");
		dbUser = (String) jdbcProperties.get("USER");
		dbPassword = (String) jdbcProperties.get("PASSWORD");

		logger.debug("===================");
	}

	@Override
	public void insertAddress(AddressVO addressVO) throws Exception {
		String SQL = "INSERT INTO ADDRESS_BOOK (NAME, AGE, PHONE, ADDRESS) VALUES (?,?,?,?)";

		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement psmt = conn.prepareStatement(SQL);) {

			psmt.setString(1, addressVO.getName());
			psmt.setInt(2, addressVO.getAge());
			psmt.setString(3, addressVO.getPhone());
			psmt.setString(4, addressVO.getAddress());

			psmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	@Override
	public List<AddressVO> selectAddressList(AddressVO addressVO) {
		List<AddressVO> addressList = new ArrayList<>();
		String SQL = "SELECT * FROM USER";
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement psmt = conn.prepareStatement(SQL);) {
			ResultSet resultSet = psmt.executeQuery();
			while (resultSet.next()) {
				String seq = resultSet.getString(1);
				String name = resultSet.getString(2);
				int age = Integer.parseInt(resultSet.getString(3));
				String phone = resultSet.getString(4);
				String address = resultSet.getString(5);
				AddressVO addressObject = new AddressVO();

				addressObject.setName(name);
				addressObject.setAge(age);
				addressObject.setPhone(phone);
				addressObject.setAddress(address);

				addressList.add(addressObject);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return addressList;
	}

	@Override
	public void updateAddress(AddressVO addressVO) throws Exception {
		String SQL = "UPDATE ADDRESS_BOOK SET NAME=?, AGE=?, ADDRESS=? WHERE NAME=? AND PHONE=?";
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement psmt = conn.prepareStatement(SQL)) {
			psmt.setString(1, addressVO.getName());
			psmt.setInt(2, addressVO.getAge());
			psmt.setString(3, addressVO.getAddress());
			psmt.setString(4, addressVO.getName());
			psmt.setString(5, addressVO.getPhone());

			psmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void deleteAddress(AddressVO addressVO) throws Exception {
		String SQL = "DELETE FROM ADDRESS_BOOK WHERE NAME=? AND PHONE=?";
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement psmt = conn.prepareStatement(SQL)) {
			psmt.setString(1, addressVO.getName());
			psmt.setString(2, addressVO.getPhone());

			psmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
