package com.barunsw.ojt.jyb.day10;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SqlSessionFactoryManager {
	private static Logger LOGGER = LoggerFactory.getLogger(SqlSessionFactoryManager.class);
	private static final SqlSessionFactory sqlSessionFactory;

	static {
		String resource = "com/barunsw/ojt/jyb/day10/SqlMapConfig.xml"; // mybatis 설정 파일의 경로
		Reader reader = null;

		try {
			reader = new InputStreamReader(SqlSessionFactoryManager.class.getClassLoader().getResourceAsStream(resource));
			//reader = Resources.getResourceAsReader(resource); //설정 파일을 읽어옴
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

		long startTime = System.currentTimeMillis();

		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "development", System.getProperties()); //reader로부터 설정을 읽고, development라는 환경을 지정하며 시스템 속성 전달
		//환경 종류 : development(개발 환경), test(테스트 환경), production(운영 환경)

		long endTime = System.currentTimeMillis();

		LOGGER.debug(String.format("SqlSessionFactoryManager created(%s)", (endTime - startTime)));
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
}
