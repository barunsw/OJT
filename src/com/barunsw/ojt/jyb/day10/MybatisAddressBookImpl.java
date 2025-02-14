package com.barunsw.ojt.jyb.day10;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.barunsw.ojt.common.AddressBookInterface;
import com.barunsw.ojt.jyb.day14.RmiAddressBookInterface;
import com.barunsw.ojt.vo.AddressVo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MybatisAddressBookImpl implements RmiAddressBookInterface {
	protected MybatisAddressBookImpl() {
		super();
	}

	private static final Logger LOGGER = LogManager.getLogger(MybatisAddressBookImpl.class);

	private SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();

	@Override
	public List<AddressVo> selectAddressList(AddressVo paramVo) {
		List<AddressVo> resultList = new ArrayList<>();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			resultList = mapper.selectAddressList(paramVo);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) {
		int insertResult = 0;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			try {
				insertResult = mapper.insertAddress(addressVo);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			session.commit();
		}
		return insertResult;
	}

	@Override
	public int updateAddress(AddressVo addressVo) {
		int updateResult = 0;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			try {
				updateResult = mapper.updateAddress(addressVo);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			session.commit();
		}
		return updateResult;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) {
		int deleteResult = 0;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			try {
				deleteResult = mapper.deleteAddress(addressVo);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			session.commit();
		}
		return deleteResult;
	}
}
