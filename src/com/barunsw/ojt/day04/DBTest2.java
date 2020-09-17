package com.barunsw.ojt.day04;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBTest2 {
	private static Logger LOGGER = LogManager.getLogger(DBTest2.class);

	public static void main(String[] args) throws Exception {
		SqlSessionFactory fac = SqlSessionFactoryManager.getSqlSessionFactory();
		LOGGER.debug("fac:" + fac);
		
		try (SqlSession session = fac.openSession();) {
			Person param = new Person();

			// 1번 방법
			List<Person> personList = session.selectList("com.barunsw.ojt.day04.PersonDao.selectPersonList", param);
			for (Person onePerson : personList) {
				LOGGER.debug("onePerson:" + onePerson);
			}
			
			// 2번 방법
			PersonDao mapper = session.getMapper(PersonDao.class);
			List<Person> personList2 = mapper.selectPersonList(param);
			for (Person onePerson : personList2) {
				LOGGER.debug("onePerson:" + onePerson);
			}
		}		
	}
}
