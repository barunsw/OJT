package com.barunsw.ojt.cjs.day22;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcDbClient {

	private static Logger LOGGER = LoggerFactory.getLogger(JdbcDbClient.class);
	private String driverName;
	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	public JdbcDbClient(ConnectVo connectVo) throws Exception {
		dbUrl = String.format(
				"jdbc:mysql://%s/%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false",
				connectVo.getDbUrl(), connectVo.getDbName());

		dbUser = connectVo.getDbUser();
		dbPassword = connectVo.getDbPassword();
		driverName = connectVo.getDb_type().toString();
		LOGGER.debug(String.format("%s %s %s %s", dbUrl, dbUser, dbPassword, driverName));

	}

	public List<UserVo> selectUserList(String query) throws SQLException {
		String SQL = query;
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement psmt = conn.prepareStatement(SQL);) {
			ResultSet resultSet = psmt.executeQuery(); // select구문은 executeQuery, 그 외에는 executeUpdate

			return null;
		}
	}
}
