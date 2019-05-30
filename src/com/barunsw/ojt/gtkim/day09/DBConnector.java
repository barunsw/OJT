package com.barunsw.ojt.gtkim.day09;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBConnector<T> {
	private static Logger LOGGER = LogManager.getLogger(DBConnector.class);
	
	private SqlSessionFactory sqlSessionFatory = SqlSessionFactoryManager.getSqlSessionFactory();
	private SqlSession session;
	public  T mapper;
	
	public DBConnector () {
		try {
			session = sqlSessionFatory.openSession();	
		}
		catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	public void setMapper(Class<T> mapper) {
		this.mapper = session.getMapper(mapper);
	}
	
	public T getMapper() {
		return mapper;
	}
	
	public void commit() {
		session.commit();
	}
	
	public void closeSession() {
		try {
			if(session != null) {
				session.close();
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
}
