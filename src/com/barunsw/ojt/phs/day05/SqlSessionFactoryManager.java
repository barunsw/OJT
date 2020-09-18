package com.barunsw.ojt.phs.day05;

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
		String resource = "com/barunsw/ojt/phs/day05/SqlMapConfig.xml";

		Reader reader = null;

		try {
			reader = Resources.getResourceAsReader(resource);
		} 
		catch ( IOException ex ) {
			LOGGER.error(ex.getMessage());
		}
		
		sqlMapper = new SqlSessionFactoryBuilder().build(reader, "development", System.getProperties());
		
		LOGGER.debug(String.format("SqlSessionFactoryManager created"));
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlMapper;  
	}
}
