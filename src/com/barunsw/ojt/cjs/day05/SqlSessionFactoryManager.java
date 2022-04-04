package com.barunsw.ojt.cjs.day05;

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
		String resource = "com/barunsw/ojt/cjs/day05/SqlMapConfig.xml"; //클래스패스와 다른 위치에서 자원을 로드하는 것을 쉽게 해줌

		Reader reader = null;

		try {
			reader = Resources.getResourceAsReader(resource);
		} 
		catch ( IOException ex ) {
			LOGGER.error(ex.getMessage(), ex);
		}
		
		long startTime = System.currentTimeMillis(); //현재 시간구하는 메소드
		
		sqlMapper = new SqlSessionFactoryBuilder().build(reader, "development", System.getProperties());
		
		
		
		long endTime = System.currentTimeMillis();
		
		LOGGER.debug(String.format("SqlSessionFactoryManager created(%s)", (endTime - startTime)));
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlMapper;  
	}
}

