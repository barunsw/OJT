package com.barunsw.ojt.day09;

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
		String resource = "com/barunsw/ojt/cjs/day09/SqlMapConfig.xml"; 
		Reader reader = null;

		try {
			reader = Resources.getResourceAsReader(resource);
		} 
		catch ( IOException ex ) {
			LOGGER.error(ex.getMessage(), ex);
		}
		
		sqlMapper = new SqlSessionFactoryBuilder().build(reader, "development", System.getProperties());
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlMapper;  
	}
}
