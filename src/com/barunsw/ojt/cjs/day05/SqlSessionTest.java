package com.barunsw.ojt.cjs.day05;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.constants.Gender;

public class SqlSessionTest {
	private static Logger LOGGER = LoggerFactory.getLogger(SqlSessionTest.class);

	public static void main(String[] args) throws Exception {
		SqlSessionFactory fac = SqlSessionFactoryManager.getSqlSessionFactory();
		LOGGER.debug("fac:" + fac);

		try (SqlSession session = fac.openSession(true)) { // sqlSession이 crud 관련 메서드를 제공, openSession에 보면 autocommit에 대한
														// 파라미터를 boolean값으로 줄 수 있는데 강제로 true를 통해 동작하는게 맞는지 질문
			Person param = new Person();

			// 1번 방법
//			List<Person> personList = session.selectList("com.barunsw.ojt.cjs.day05.PersonDao.selectPersonList", param);
//			for (Person p : personList) {
//				LOGGER.debug("onePerson:" + p);
//			}
			param.setName("jaeseok");
			param.setGender(Gender.toGender("MAN"));
			param.setAge(28);
			param.setPhone("010-1234-1234");
			param.setAddress("제주시 서귀포");
			param.setSeq(113);
			
			LOGGER.debug(param.getSeq() + "");
			
			session.insert("com.barunsw.ojt.cjs.day05.PersonDao.insertPerson", param);
			session.update("com.barunsw.ojt.cjs.day05.PersonDao.updatePerson", param);
			session.delete("com.barunsw.ojt.cjs.day05.PersonDao.deletePerson", param);

			// 2번 방법
			PersonDao mapper = session.getMapper(PersonDao.class); // sqlSession에 mapper객체 제공하는 getMapper
																	// getMapper는 매퍼인터페이스로 지정된 메퍼인터페이스의 구현객체를 동적 생성,
																	// 제공(Person)
//																	// 매퍼객체를 활용하면 문자열에 의존x, 리턴타입에 형변환이 필요없음
			List<Person> list = mapper.selectPersonList(param); // dao에 객체 담아서 리스트로에 던짐
			for (Person p : list) {
				LOGGER.debug("onePerson:" + p);
			}
//			return 값이 int형임

			mapper.insertPerson(param); //insert 성공하면 1아니면 0 리턴 
			mapper.updatePerson(param); //  변경된 row의 갯수가 리턴됨
			mapper.deletePerson(param.getSeq()); // delete 변경된 row갯수 리턴

		}
	}
}
