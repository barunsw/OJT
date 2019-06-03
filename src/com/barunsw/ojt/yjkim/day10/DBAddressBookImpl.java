package com.barunsw.ojt.yjkim.day10;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.yjkim.day10.AddressBookInterface;
import com.barunsw.ojt.yjkim.day10.AddressVo;

public class DBAddressBookImpl implements AddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(DBAddressBookImpl.class);

	private int count = 0;
	SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
	private String namespace = "com.barunsw.ojt.yjkim.day10.AddressDao";
	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		List<AddressVo> list;
		try (SqlSession session = sqlSessionFactory.openSession()){
			return session.selectList(namespace+".select_Address");
		}
		
		
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		try(SqlSession session = sqlSessionFactory.openSession()){
			count = session.insert(namespace+".insert_Address",addressVo);
			session.commit();
			return count;
		}
		
	}

	@Override
	public int updateAddress(int index, AddressVo addressVo) throws Exception {
		try(SqlSession session = sqlSessionFactory.openSession()){
			 count = session.update(namespace+".update_Address",addressVo);
			 session.commit();
			 return count;
			 
		}
	}


	@Override
	public int deleteAddress(int index) throws Exception {
		// TODO Auto-generated method stub
		try(SqlSession session = sqlSessionFactory.openSession()){
			count = session.delete(namespace+".delete_Address", index);
			session.commit();
			return count;
			
		}
	}



}
