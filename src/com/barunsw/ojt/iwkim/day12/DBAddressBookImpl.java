package com.barunsw.ojt.iwkim.day12;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.AddressBookInterface;
import com.barunsw.ojt.iwkim.common.PersonVO;

public class DBAddressBookImpl implements AddressBookInterface {
	private static Logger LOGGER = LogManager.getLogger(DBAddressBookImpl.class);
	
	SqlSessionFactory sqlSessionFactory= SqlSessionFactoryManager.getSqlSessionFactory();
	
	@Override
	public List<PersonVO> selectList() throws Exception {
		List<PersonVO> personList = new ArrayList<>();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			personList = mapper.selectList();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		return personList;
	}

	@Override
	public int insertPerson(PersonVO param) throws Exception {
		int resultExcQuery = 0;
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			resultExcQuery = mapper.insertPerson(param);
			session.commit();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		return resultExcQuery;
	}

	@Override
	public int updatePerson(PersonVO param) throws Exception {
		int resultExcQuery = 0;
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			resultExcQuery = mapper.updatePerson(param);
			session.commit();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		return resultExcQuery;
	}

	@Override
	public int deletePerson(String name) throws Exception {
		int resultExcQuery = 0;
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			resultExcQuery = mapper.deletePerson(name);
			session.commit();
		}
		
		return resultExcQuery;
	}
}