package com.barunsw.ojt.iwkim.day09;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SqlSessionFactoryManager { //static 변수 생성, 초기화블록으로 초기화 후 메서드사용하여 반환
	private static Logger LOGGER = LogManager.getLogger(SqlSessionFactoryManager.class);
	private static final SqlSessionFactory sqlMapper;
	
	static {
		String resource = "com/barunsw/ojt/iwkim/day09/SqlMapConfig.xml";
		Reader reader = null;
		
		try {
			reader = Resources.getResourceAsReader(resource);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		
		sqlMapper = new SqlSessionFactoryBuilder().build(reader, "development", System.getProperties()); 
		// build(스트림, environment의 매핑할 id, System.getProperties)
		// System.getProperties : 실행위치에 있는 파일을 읽기 위해 시스템정보를 가져오려고 사용
	}
	
	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlMapper;
	}
}
