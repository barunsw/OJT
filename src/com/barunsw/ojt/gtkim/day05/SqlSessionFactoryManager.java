package com.barunsw.ojt.gtkim.day05;

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
		String rsc = "com/barunsw/ojt/gtkim/day05/SqlMapConfig.xml";

		//바이트 스트림으로 생성도 가능하지만 케릭터 셋에 따라 짤릴 수 있으므로 리더로 생성
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(rsc);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}

		sqlMapper = new SqlSessionFactoryBuilder().build(reader,"development",System.getProperties());
		LOGGER.debug("SqlSessionFactoryManager created!");
	}
	
	public static SqlSessionFactory getSqlSesstionFactory() {
		return sqlMapper;
	}
}
