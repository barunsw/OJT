package com.barunsw.ojt.phs.day05;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.phs.day05.PersonDTO;
import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.phs.day05.DAO;
import com.barunsw.ojt.phs.day05.SqlSessionFactoryManager;

public class DBMybatis {
	private static final Logger LOGGER = LogManager.getLogger(DBMybatis.class);
	private static final SqlSessionFactory fac = SqlSessionFactoryManager.getSqlSessionFactory();
	private static final DBMybatis dBMybatis = new DBMybatis();
	
	private DBMybatis() {}
	
	public DBMybatis getDbMybatis() {
		return dBMybatis;
	}
	
	public void processDb(boolean autocommit) {
		try(SqlSession session = fac.openSession(autocommit)){
			DAO mapper = session.getMapper(DAO.class);
			selectAll(mapper);
//			selectOne(mapper);
//			insert(mapper);
//			delete(mapper);
//			update(mapper);
		}
	}
	
	private void selectAll(DAO mapper) {
		List<PersonDTO> personList = mapper.selectPersonList();
		int idx = 1;
		for (PersonDTO onePerson : personList) {
			LOGGER.debug(idx +"-Person:" + onePerson);
			++idx;
		}
	}
	
	private void selectOne(DAO mapper) {
		String name = "minsu";
		PersonDTO person = mapper.selectOnePerson(name);
		LOGGER.debug("이름으로 검색한결과 : " + person);
	}
	
	private void insert(DAO mapper) {
		PersonDTO person = new PersonDTO();
		person.setName("hihi");
		person.setAge(232);
		person.setGender(Gender.MAN);
		int result = mapper.insertPerson(person);
		LOGGER.debug("삽입한 개수 : " + result);
	}
	
	private void delete(DAO mapper) {
		PersonDTO person = new PersonDTO();
		person.setAge(232);
		int result = mapper.deletePerson(person);
		LOGGER.debug("제거한 개수 : " + result);
	}
	
	private void update(DAO mapper) {
		PersonDTO person = new PersonDTO();
		person.setAge(50);
		person.setName("HeeSeong");
		int result = mapper.updatePerson(person);
	}
	
	
	public static void main(String[] args) {
		dBMybatis.processDb(true);		
	}
	
}








