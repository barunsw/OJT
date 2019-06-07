package com.barunsw.ojt.gtkim.day14;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.AddressVo;
import com.barunsw.ojt.constants.RmiAddressBookInterface;

public class DBAddressBookImpl extends UnicastRemoteObject implements RmiAddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(DBAddressBookImpl.class);

	private SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
	private AddressDao mapper;
	private SqlSession session;
	
	public DBAddressBookImpl() throws RemoteException  {
		super();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			mapper = session.getMapper(AddressDao.class);
			mapper.createAddressTable();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		List<AddressVo> addressList = new ArrayList<>();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			mapper = session.getMapper(AddressDao.class);
			addressList = mapper.selectAddressList();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		return addressList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		int result = 0;
		try {
			session = sqlSessionFactory.openSession();
			mapper  = session.getMapper(AddressDao.class);
			result  = mapper.insertAddress(addressVo);
			session.commit();
		}
		catch (Exception ex) {
			session.rollback();
			LOGGER.error(ex.getMessage(), ex);
		}
		finally {
			if (session != null) {
				session.close();
			}
		}
		return result;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		int result = 0;
		try {
			session = sqlSessionFactory.openSession();
			mapper  = session.getMapper(AddressDao.class);
			result  = mapper.updateAddress(addressVo);
			session.commit();
		}
		catch (Exception ex) {
			session.rollback();
			LOGGER.error(ex.getMessage(), ex);
		}
		finally {
			if (session != null) {
				session.close();
			}
		}
		return result;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		int result = 0;
		try {
			session = sqlSessionFactory.openSession();
			mapper  = session.getMapper(AddressDao.class);
			result  = mapper.deleteAddress(addressVo);
			session.commit();
		}
		catch (Exception ex) {
			session.rollback();
			LOGGER.error(ex.getMessage(), ex);
		}
		finally {
			if (session != null) {
				session.close();
			}
		}
		return result;
	}

	@Override
	public List<AddressVo> selectParticularAddress(Map<String, Object> map) throws Exception {
		return null;
	}

}
