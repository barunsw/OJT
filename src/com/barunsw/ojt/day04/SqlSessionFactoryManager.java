package com.barunsw.ojt.day04;

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
		String resource = "com/barunsw/ojt/cjs/day04/SqlMapConfig.xml"; //실제 경로가 아닌 config에 저장된 build path 참조

		Reader reader = null;

		try {
			reader = Resources.getResourceAsReader(resource); 
		} 
		catch ( IOException ex ) {
			LOGGER.error(ex.getMessage(), ex);
		}
		
		long startTime = System.currentTimeMillis();
		
		sqlMapper = new SqlSessionFactoryBuilder().build(reader, "development", System.getProperties());
		
		long endTime = System.currentTimeMillis(); 
		
		LOGGER.debug(String.format("SqlSessionFactoryManager created(%s)", (endTime - startTime)));
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlMapper;  //어플리케이션이 실행되는 동안 하나만 생성해서 계속 사용
	}
}

