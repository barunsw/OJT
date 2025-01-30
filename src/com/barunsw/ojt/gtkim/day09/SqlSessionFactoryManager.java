package com.barunsw.ojt.gtkim.day09;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SqlSessionFactoryManager {
	private static final Logger LOGGER = LogManager.getLogger(SqlSessionFactoryManager.class);
	private static final SqlSessionFactory sqlMapper;
	static {
		String rsc = "com/barunsw/ojt/gtkim/day09/SqlMapConfig.xml";

		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(rsc);
		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}

		sqlMapper = new SqlSessionFactoryBuilder().build(reader, "development", System.getProperties());
		LOGGER.debug("SqlSessionFactoryManager created!");
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlMapper;
	}
}
