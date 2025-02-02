package day6;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import day4.JdbcAddressBookImpl;

public class SqlSessionFactoryManager {
	private static Logger logger = LoggerFactory.getLogger(JdbcAddressBookImpl.class);
	private static final SqlSessionFactory sqlMapper;

	static {
		String resource = "SqlMapConfig.xml";

		Reader reader = null;

		try {
			reader = Resources.getResourceAsReader(resource);
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
		}

		long startTime = System.currentTimeMillis();

		sqlMapper = new SqlSessionFactoryBuilder().build(reader, "development", System.getProperties());

		long endTime = System.currentTimeMillis();

		logger.debug(String.format("SqlSessionFactoryManager created(%s)", (endTime - startTime)));
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlMapper;
	}
}
