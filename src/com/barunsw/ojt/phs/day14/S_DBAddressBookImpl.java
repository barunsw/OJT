package com.barunsw.ojt.phs.day14;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class S_DBAddressBookImpl extends UnicastRemoteObject implements S_AddressBookInterface {

	private static final Logger LOGGER = LogManager.getLogger(S_DBAddressBookImpl.class);
	
	private SqlSessionFactory fac = S_SqlSessionFactoryManager.getSqlSessionFactory();
	
	private S_AddressBookInterface mapper;
	
	protected S_DBAddressBookImpl() throws RemoteException {
		try {
			SqlSession session = fac.openSession(true);
			mapper = session.getMapper(S_AddressBookInterface.class);
		}
		catch (Exception e) {
			LOGGER.debug(e);
		}
	}

	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		return mapper.selectAddressList();
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		int result = 0;
		result = mapper.insertAddress(addressVo);
		return result;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		int result = 0;
		result = mapper.updateAddress(addressVo);
		return result;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		int result = 0;
		result = mapper.deleteAddress(addressVo);
		return result;
	}

	@Override
	public int createStorage() throws Exception {
		int result = 0;
		result = mapper.createStorage();
		return result;
	}

}
