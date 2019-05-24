package com.barunsw.ojt.gtkim.day05;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;

public class DBTestMybatis {
	private static final Logger LOGGER = LogManager.getLogger(DBTestMybatis.class);
	
	public static void main(String[] args) {
		LOGGER.debug("MybatisTest!");
		
		//sqlsessionFactory로 sqlsession을 생성해야한다.
		SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSesstionFactory();
		try (SqlSession session = sqlSessionFactory.openSession()){
			PersonDao mapper = session.getMapper(PersonDao.class);
			
			//select 
			List<Person> personList = mapper.selectPersonList();
			for(Person p : personList) {
				LOGGER.debug(p);
			}
			
			Person onePerson = new Person();
			onePerson.setName("KyunTaeKim");
			onePerson.setAge(29);
			onePerson.setGender(Gender.toGender("남자"));
			onePerson.setPhone("010-3322-1268");
			onePerson.setAddress("강원도 춘천시 안마산로");
			
			//insert
			int result = mapper.insertPerson(onePerson);
			LOGGER.debug(String.format("[%d]row Insert Success : %s", result, onePerson));
			
			//select 
			personList = mapper.selectPersonList();
			for(Person p : personList) {
				LOGGER.debug(p);
			}
			
			//update
			Person updatePerson = new Person();
			updatePerson.setName("KyunTaeKim");
			updatePerson.setAge(35);
			updatePerson.setGender(Gender.toGender("여"));
			updatePerson.setPhone("070-332-1123");
			result = mapper.updatePersonByName(updatePerson);
			
			//select 
			personList = mapper.selectPersonList();
			for(Person p : personList) {
				LOGGER.debug(p);
			}
			
			//delete
			Person deletePerson = new Person();
			deletePerson.setName("KyunTaeKim");
			result = mapper.deletePersonByName(deletePerson);
			
			mapper.createTestTable();
			session.commit();
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
