package com.barunsw.ojt.jyb.day9;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.barunsw.ojt.common.AddressBookInterface;
import com.barunsw.ojt.vo.AddressVo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MybatisAddressBookImpl implements AddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(MybatisAddressBookImpl.class);

	private SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();

	@Override
	public List<AddressVo> selectAddressList(AddressVo paramVo){
		List<AddressVo> resultList = new ArrayList<>();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			resultList = mapper.selectAddressList(paramVo);
		}
		return resultList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		int insertResult = 0;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			insertResult = mapper.insertAddress(addressVo);
			session.commit();
		}
		return insertResult;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		int updateResult = 0;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			updateResult = mapper.updateAddress(addressVo);
			session.commit();
		}
		return updateResult;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		int deleteResult = 0;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			deleteResult = mapper.deleteAddress(addressVo);
			session.commit();
		}
		return deleteResult;
	}
}
