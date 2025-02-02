package com.barunsw.ojt.day05;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersonServiceImpl implements PersonService {
	private static final Logger LOGGER = LogManager.getLogger(PersonServiceImpl.class);
	
	@Override
	public List<Person> selectPersonList(Person person) {
		SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			PersonDao mapper = session.getMapper(PersonDao.class);
			List<Person> personList = mapper.selectPersonList(new Person());
			
			for (int i = 0; i < personList.size(); i++) {
				LOGGER.debug(String.format("[%d]%s", i, personList.get(i)));
			}
			
			return personList;
		} 
	}
}
