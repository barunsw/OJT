package com.barunsw.ojt.mjg.day11.copy;

import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SqlSessionFactoryManager {
	private static final Logger LOGGER = LogManager.getLogger(SqlSessionFactoryManager.class);
	private static final SqlSessionFactory sqlMapper;
	
	// static 블록은 클래스(SqlSessionFactoryManager)가 로드될 때 한 번만 실행
	// SqlSessionFactory 인스턴스가 불필요하게 여러 번 생성되는 것을 방지
	static {
		// XML 설정 파일의 경로를 지정
		String resource = "com/barunsw/ojt/mjg/day11/SqlMapConfig.xml";
		
		// XML(텍스트 데이터) 사용 시에 InputStream보다 Reader 사용이 적합
		Reader reader = null;

		try {
			//getClass().getResource(COMMON_IMAGE_PATH + "logo.png")
			LOGGER.debug(SqlSessionFactoryManager.class.getClassLoader().getResource(resource));
			
			reader = new InputStreamReader(SqlSessionFactoryManager.class.getClassLoader().getResourceAsStream(resource));
			
			//reader = Resources.getResourceAsReader(resource);
//			reader = new FileReader(new File(resource));
		} 
		catch ( Exception ioe ) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		
		long startTime = System.currentTimeMillis();
		
		// MyBatis 설정 파일에서 <environments> 태그로 여러 환경을 정의하고, 해당 환경의 설정만 로드 가능
		// System.getProperties(): ${db.driver}, ${db.url} 같은 동적 변수 처리
		sqlMapper = new SqlSessionFactoryBuilder().build(reader, "development", System.getProperties());
		
		long endTime = System.currentTimeMillis();
		
		System.out.println(String.format("SqlSessionFactoryManager created(%s)", (endTime - startTime)));
	}
	
	// static method: 전역적으로 SqlSessionFactory 관리 및 재사용
	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlMapper;  
	}
}

