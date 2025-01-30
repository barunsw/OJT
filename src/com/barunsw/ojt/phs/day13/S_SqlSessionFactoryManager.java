package com.barunsw.ojt.phs.day13;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class S_SqlSessionFactoryManager {
	private static final Logger LOGGER = LogManager.getLogger(S_SqlSessionFactoryManager.class);
	private static final SqlSessionFactory sqlMapper;

	static {
		String resource = "com/barunsw/ojt/phs/day13/S_SqlMapConfig.xml";

		Reader reader = null;

		try {
			reader = Resources.getResourceAsReader(resource);
			LOGGER.debug("reader로 읽어옴");
		} 
		catch ( IOException ex ) {
			LOGGER.error(ex.getMessage());
		}
		
		sqlMapper = new SqlSessionFactoryBuilder().build(reader, "development");
		
		LOGGER.debug(String.format("SqlSessionFactoryManager created"));
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlMapper;  
	}

}