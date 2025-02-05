package com.barunsw.ojt.yjkim.day14;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.common.RmiAddressBookInterface;
import com.barunsw.ojt.vo.AddressVo;



public class AddressBookImpl extends UnicastRemoteObject implements RmiAddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(AddressBookImpl.class);
	private String namespace = "com.barunsw.ojt.yjkim.day14.RmiAddressDao";
	SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
	private RmiAddressDao mapper;
	
	public AddressBookImpl() throws RemoteException {
		super();
	}
	
	@Override
	public List<AddressVo> selectAddressList() throws RemoteException {
		List<AddressVo> list = null;
		try (SqlSession session = sqlSessionFactory.openSession()){
				try {
					mapper = session.getMapper(RmiAddressDao.class);
					list = mapper.selectAddressList();
				} catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			//list = session.selectList(namespace +".selectAllAddress");
		}
		return list;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws RemoteException {
		LOGGER.debug("insertAddress:" + addressVo);
		try (SqlSession session = sqlSessionFactory.openSession()){
			//session.insert(namespace +".insertAddress", addressVo);
			//session.commit();		
			try {
				mapper = session.getMapper(RmiAddressDao.class);
				mapper.insertAddress(addressVo);
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
			session.commit();
		}
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws RemoteException {
		LOGGER.debug("updateAddress:" + addressVo);
		try (SqlSession session = sqlSessionFactory.openSession()) {
			//session.update(namespace +".updateAddress", addressVo);
			//session.commit();
			try {
				mapper.updateAddress(addressVo);
				mapper = session.getMapper(RmiAddressDao.class);
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
			session.commit();
		}
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws RemoteException {
		LOGGER.debug("deleteAddress:" + addressVo);
		try (SqlSession session = sqlSessionFactory.openSession()) {
			//session.delete(namespace +".deleteAddress", addressVo);
			mapper = session.getMapper(RmiAddressDao.class);
			try {
				mapper.deleteAddress(addressVo);
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
			session.commit();
		}
		return 0;
	}

	@Override
	public List<AddressVo> selectParticularAddress(Map<String, Object> map) throws RemoteException {
		LOGGER.debug("selectParticularAddress" + map);
		List<AddressVo> list = null;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			//list = session.selectList(namespace +".deleteAddress", map);
			mapper = session.getMapper(RmiAddressDao.class);
			try {
				list = mapper.selectParticularAddress(map);
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
		return list;
	}

}
