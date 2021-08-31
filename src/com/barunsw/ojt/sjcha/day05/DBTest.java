package com.barunsw.ojt.sjcha.day05;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.sjcha.day05.SqlSessionFactoryManager;

public class DBTest {
	private static final Logger LOGGER = LogManager.getLogger(DBTest.class);
	
	public static void main(String[] args) {
		LOGGER.debug("MyBatis Test");
		
		// SqlSessionFactory로 SqlSession 생성
		SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
		
		// session 만들기
		try (SqlSession session = sqlSessionFactory.openSession()){
			PersonDao mapper = session.getMapper(PersonDao.class);
			
			// select 결과 담을 객체
			List<Person> personList = mapper.selectPersonList(new Person());
			
			for (Person person: personList) {
				LOGGER.debug(person);
			}
			
			// 삽입할 객체 생성
			Person insertPerson = new Person();
			
			insertPerson.setName("김하나");
			insertPerson.setGender(Gender.toGender("WOMAN"));
			insertPerson.setAge(29);
			insertPerson.setPhone("010-1144-1344");
			insertPerson.setAddress("인천 동구");
			
			int insertResult = mapper.insertPersonList(insertPerson);
			
			// 삽입한 결과 확인하기
			personList = mapper.selectPersonList(null);
						
			for (Person person: personList) {
				LOGGER.debug(person);
			}
			
			// 업데이트할 객체 생성
			Person updatePerson = new Person();
			updatePerson.setName("홍길동");
			updatePerson.setAge(20);
			updatePerson.setPhone("010-1111-1111");
			
			int updateResult = mapper.updatePersonByName(updatePerson);
			
			// 업데이트한 결과 확인하기
			personList = mapper.selectPersonList(null);
									
			for (Person person: personList) {
				LOGGER.debug(person);
			}
			
			Person deletePerson = new Person();
			deletePerson.setName("홍길동");
			
			int deleteResult = mapper.deletePersonByName(deletePerson);
			
			// 삭제한 결과 확인하기
			personList = mapper.selectPersonList(null);
												
			for (Person person: personList) {
				LOGGER.debug(person);
			}
			
			// 차의 성을 가진 사람 select 
			Person onePerson = new Person();
			onePerson.setName("차수진");
			
			personList = mapper.selectPerson_Kim(onePerson);
			
			for (Person person: personList) {
				LOGGER.debug("차의 성을 가진 사람 : " + person);
			}
			session.commit();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
