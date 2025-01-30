package day4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

public class ObjectAddressBookImpl implements AddressBookInterface {

	private static Logger logger = LoggerFactory.getLogger(ObjectAddressBookImpl.class);
	public static Properties jdbcProperties = new Properties();
	private String driverName;
	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	public ObjectAddressBookImpl() throws Exception {
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
				PreparedStatement psmt = conn.prepareStatement(SQL)) {

			psmt.setString(1, addressVO.getName());
			psmt.setInt(2, addressVO.getAge());
			psmt.setString(3, addressVO.getPhone());
			psmt.setString(4, addressVO.getAddress());

			psmt.executeUpdate();
			saveToFile();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public List<AddressVO> selectAddressList(AddressVO addressVO) {
		List<AddressVO> addressList = new ArrayList<>();
		String SQL = "SELECT * FROM ADDRESS_BOOK";

		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement psmt = conn.prepareStatement(SQL);
				ResultSet resultSet = psmt.executeQuery()) {

			while (resultSet.next()) {
				AddressVO addressObject = new AddressVO();
				addressObject.setSeq(resultSet.getInt("SEQ"));
				addressObject.setName(resultSet.getString("NAME"));
				addressObject.setAge(resultSet.getInt("AGE"));
				addressObject.setPhone(resultSet.getString("PHONE"));
				addressObject.setAddress(resultSet.getString("ADDRESS"));

				addressList.add(addressObject);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return addressList;
	}

	@Override
	public void updateAddress(AddressVO addressVO) throws Exception {
		String SQL = "UPDATE ADDRESS_BOOK SET NAME=?, AGE=?, PHONE=?, ADDRESS=? WHERE SEQ=?";
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement psmt = conn.prepareStatement(SQL)) {

			psmt.setString(1, addressVO.getName());
			psmt.setInt(2, addressVO.getAge());
			psmt.setString(3, addressVO.getPhone());
			psmt.setString(4, addressVO.getAddress());
			psmt.setInt(5, addressVO.getSeq());

			psmt.executeUpdate();
			saveToFile();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void deleteAddress(AddressVO addressVO) throws Exception {
		String SQL = "DELETE FROM ADDRESS_BOOK WHERE SEQ=?";
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement psmt = conn.prepareStatement(SQL)) {

			psmt.setInt(1, addressVO.getSeq());
			psmt.executeUpdate();
			saveToFile();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void saveToFile() {
		List<AddressVO> addressList = selectAddressList(null);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("AddressBookFile.csv"))) {
			for (AddressVO address : addressList) {
				String line = String.format("%d,%s,%d,%s,%s", address.getSeq(), address.getName(), address.getAge(),
						address.getPhone(), address.getAddress());
				writer.write(line);
				writer.newLine();
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
