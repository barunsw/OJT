package com.barunsw.ojt.yjkim.day14;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.AddressVo;
import com.barunsw.ojt.constants.RmiAddressBookInterface;

public class AddressBookImpl extends UnicastRemoteObject implements RmiAddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(AddressBookImpl.class);
	private String namespace = "com.barunsw.ojt.yjkim.day14.RmiAddressDao";
	SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
	private RmiAddressDao mapper;
	
	public AddressBookImpl() throws RemoteException {
		super();
	}
	
	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		List<AddressVo> list;
		try (SqlSession session = sqlSessionFactory.openSession()){
				mapper = session.getMapper(RmiAddressDao.class);
				//list = session.selectList(namespace +".selectAllAddress");
				list = mapper.selectAddressList();
		}
		return list;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		LOGGER.debug("insertAddress:" + addressVo);
		try (SqlSession session = sqlSessionFactory.openSession()){
			//session.insert(namespace +".insertAddress", addressVo);
			//session.commit();		
			mapper = session.getMapper(RmiAddressDao.class);
			mapper.insertAddress(addressVo);
			session.commit();
		}
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		LOGGER.debug("updateAddress:" + addressVo);
		try (SqlSession session = sqlSessionFactory.openSession()) {
			//session.insert(namespace +".updateAddress", addressVo);
			//session.commit();
			mapper = session.getMapper(RmiAddressDao.class);
			mapper.updateAddress(addressVo);
			session.commit();
		}
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		LOGGER.debug("deleteAddress:" + addressVo);
		try (SqlSession session = sqlSessionFactory.openSession()) {
			//session.insert(namespace +".deleteAddress", addressVo);
			mapper = session.getMapper(RmiAddressDao.class);
			mapper.deleteAddress(addressVo);
			session.commit();
		}
		return 0;
	}

	@Override
	public List<AddressVo> selectParticularAddress(Map<String, Object> map) throws Exception {
		LOGGER.debug("selectParticularAddress" + map);
		List<AddressVo> list;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			//session.insert(namespace +".deleteAddress", addressVo);
			mapper = session.getMapper(RmiAddressDao.class);
			list = mapper.selectParticularAddress(map);
		}
		return list;
	}

}
