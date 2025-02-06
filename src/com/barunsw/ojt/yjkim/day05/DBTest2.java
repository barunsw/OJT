package com.barunsw.ojt.yjkim.day05;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBTest2 {
	private static final Logger LOGGER = LogManager.getLogger(DBTest2.class);
	//Mybatis 
	public static void main(String[] args) {
		SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
		/*
		//select list 예제
		try (SqlSession session = sqlSessionFactory.openSession()) {
			PersonDao mapper = session.getMapper(PersonDao.class);
			List<Person> personList = mapper.selectPersonList(new Person());
			for (int i = 0; i < personList.size(); i++) {
				LOGGER.debug(String.format("이름[%s] 성별[%s] 나이[%d] 번호[%s] 주소[%s]",
						personList.get(i).getName(), personList.get(i).getGender(), personList.get(i).getAge(),
						personList.get(i).getPhone(), personList.get(i).getAddress()));
			}
		} 
		*/
		//insert 예제
		/*try (SqlSession session = sqlSessionFactory.openSession()) {
			Person person = new Person();
			person.setName("김윤제");
			person.setGender(Gender.toGender("남자"));
			person.setAge(28);
			person.setPhone("010-4071-2232");
			person.setAddress("서울시 성북구");
			
			PersonDao mapper = session.getMapper(PersonDao.class);
			mapper.insertPerson(person);
			//commit이 auto가 아니기 때문에 수동으로 commit해줘야한다.
			session.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}*/
		/*
		//update 예제
		try (SqlSession session = sqlSessionFactory.openSession()){
			
			PersonDao mapper = session.getMapper(PersonDao.class);
			Map <String,Object> map = new HashMap<String,Object>();
			map.put("name", "김윤제");
			map.put("age",20);
			mapper.updatePerson(map);
			session.commit();
		}
		*/
		/*
		//delete 예제
		try (SqlSession session = sqlSessionFactory.openSession()){
			
			PersonDao mapper = session.getMapper(PersonDao.class);
			mapper.deletePerson(20);
			session.commit();
		}
		*/
		
		/*
		//select one 예제
		try (SqlSession session = sqlSessionFactory.openSession()){
			PersonDao mapper = session.getMapper(PersonDao.class);
			Person person = mapper.selectOnePerson("이효리");
			LOGGER.debug(String.format("이름 [%s] 성별 [%s] 나이[%d] 번호[%s] 주소[%s]",
					person.getName(), person.getGender(), person.getAge(),
					person.getPhone(), person.getAddress()));
		}
		*/
	} 
	
}
