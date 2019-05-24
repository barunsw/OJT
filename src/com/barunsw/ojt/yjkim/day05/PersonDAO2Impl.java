package com.barunsw.ojt.yjkim.day05;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PersonDAO2Impl implements PersonDAO2 {

	private static Logger LOGGER = LogManager.getLogger(PersonDAO2Impl.class);

	private static SqlSessionFactory sqlSessionFactory;
	
	private String namespace = "com.barunsw.ojt.yjkim.day05.PersonDao";
	public PersonDAO2Impl() {
		sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
	}

	@Override
	public List<Person> selectListPerson() {
		try(SqlSession sqlSession = sqlSessionFactory.openSession()){
			List<Person> list = sqlSession.selectList(namespace+".selectPersonList");
			return list;
		}
	}

	@Override
	public Person selectOnePerson(String name) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession()){
			Person person = sqlSession.selectOne(namespace+".selectOnePerson",name);
			return person;
		}
	}

	@Override
	public void updatePerson(Map<String,Object> map) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession()){
			sqlSession.update(namespace+".updatePerson",map);
			sqlSession.commit();
		}
	}

	@Override
	public void deletePerson(int age) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession()){
			sqlSession.delete(namespace+".deletePerson",age);
			sqlSession.commit();
		}
	}

	@Override
	public void insertPerson(Person person) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession()){
			sqlSession.insert(namespace+".insertPerson",person);
			sqlSession.commit();
		}
	}

}
