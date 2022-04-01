package com.barunsw.ojt.cjs.day04;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBTest2 {
	private static Logger LOGGER = LoggerFactory.getLogger(DBTest2.class);

	public static void main(String[] args) throws Exception {
		SqlSessionFactory fac = SqlSessionFactoryManager.getSqlSessionFactory();
		LOGGER.debug("fac:" + fac);
		
		try (SqlSession session = fac.openSession();) { //sqlSession이  crud 관련 메서드를 제공
			Person param = new Person();

			// 1번 방법
			List<Person> personList = session.selectList("com.barunsw.ojt.cjs.day04.PersonDao.selectPersonList", param);
			for (Person p : personList) {
				LOGGER.debug("onePerson:" + p);
			}
			
			// 2번 방법
			PersonDao mapper = session.getMapper(PersonDao.class); //sqlSession에 mapper객체 제공하는 getMapper 
																	//getMapper는 매퍼인터페이스로 지정된 메퍼인터페이스의 구현객체를 동적 생성, 제공(Person)
																	//매퍼객체를 활용하면 문자열에 의존x, 리턴타입에 형변환이 필요없음
			List<Person> list = mapper.selectPersonList(param); //dao에 객체 담아서 리스트로에 던짐
			for (Person p : list) {
				LOGGER.debug("onePerson:" + p);
			}
		}		
	}
}
