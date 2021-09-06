package com.barunsw.ojt.sjcha.day08;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SqlSessionFactoryManager {
	private static final Logger LOGGER = LogManager.getLogger(SqlSessionFactoryManager.class);
	private static final SqlSessionFactory SQLMAPPER;

	// 가장 처음 한번 실행
	static {
		// config.xml 파일 경로
		// DB connection과 sql 구문 설정 Mappers
		String resource = "com/barunsw/ojt/sjcha/day08/SqlMapConfig.xml";
		
		// 데이터를 읽어오기, inputstream도 가능
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(resource);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		
		SQLMAPPER = new SqlSessionFactoryBuilder().build(reader, "development", System.getProperties());
	}
	
	public static SqlSessionFactory getSqlSessionFactory() {
		return SQLMAPPER;
	}
}
