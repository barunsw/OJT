package com.barunsw.ojt.cjs.day22;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JdbcConnect {
	private static final int DB_TYPE_INDEX = 1;
	private static final int IP_INDEX = 2;
	private static final int PORT_INDEX = 3;
	private static final int USER_NAME_INDEX = 4;
	private static final int PASSWORD_INDEX = 5;
	private static final int DBNAME_INDEX = 6;

	private static Logger LOGGER = LoggerFactory.getLogger(JdbcConnect.class);
	private String driverName;
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	private ConnectVo connectVo;

	public JdbcConnect(ConnectVo connectVo) {
		this.connectVo = connectVo;
		dbUrl = String.format(
				"jdbc:mysql://%s/%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false",
				connectVo.getDbUrl(), connectVo.getDbName());
		dbUser = connectVo.getDbUser();
		dbPassword = connectVo.getDbPassword();
		driverName = connectVo.getDb_type().toString();
		LOGGER.debug(String.format("%s %s %s %s", dbUrl, dbUser, dbPassword, driverName));
	}

	public void SelectConnectData() throws SQLException {
		String sql = "SELECT * FROM TB_DB_CONNECT";
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement psmt = conn.prepareStatement(sql);) {
			ResultSet resultSet = psmt.executeQuery(); // select구문은 executeQuery, 그 외에는 executeUpdate
			while (resultSet.next()) {
				String db_Type = resultSet.getString(DB_TYPE_INDEX);
				String ip = resultSet.getString(IP_INDEX);
				String port = resultSet.getString(PORT_INDEX);
				String dbUsername = resultSet.getString(USER_NAME_INDEX);
				String dbPassword = resultSet.getString(PASSWORD_INDEX);
				String dbName = resultSet.getString(DBNAME_INDEX);
				ConnectVo connectData = new ConnectVo();
			}
		}
	}

	public int insertConnectData() {
		String port = connectVo.getDbUrl().substring(connectVo.getDbUrl().lastIndexOf(":") + 1);
		String sql = "INSERT INTO TB_DB_CONNECT(DB_TYPE, IP, PORT, USER_NAME, PASSWORD, DB_NAME)"
				+ "VALUES(?,?,?,?,?,?)";
		int result = 0;
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement psmt = conn.prepareStatement(sql);) {
			psmt.setObject(DB_TYPE_INDEX, connectVo.getDb_type().toStringInDatabase());
			psmt.setString(IP_INDEX, connectVo.getDbUrl());
			psmt.setString(PORT_INDEX, port);
			psmt.setString(USER_NAME_INDEX, connectVo.getDbUser());
			psmt.setString(PASSWORD_INDEX, connectVo.getDbPassword());
			psmt.setString(DBNAME_INDEX, connectVo.getDbName());

			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return result;
	}

	public void deleteConnectData(String port) {
		String sql = "DELETE FROM TB_DB_CONNECT WHERE PORT=? ";

		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement psmt = conn.prepareStatement(sql);) {
			psmt.setString(1, port);
			psmt.executeUpdate();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
