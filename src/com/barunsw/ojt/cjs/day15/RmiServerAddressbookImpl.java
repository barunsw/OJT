package com.barunsw.ojt.cjs.day15;

import java.lang.reflect.Constructor;
import java.rmi.RemoteException;
import java.util.List;
import java.rmi.server.UnicastRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.cjs.common.AddressBookInterface;
import com.barunsw.ojt.cjs.common.AddressVo;

public class RmiServerAddressbookImpl extends UnicastRemoteObject implements AddressBookInterface {
	private static final Logger LOGGER = LoggerFactory.getLogger(RmiServerAddressbookImpl.class);
	AddressBookInterface addressBookIf;

	public RmiServerAddressbookImpl() throws RemoteException {
		super();
	}

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.debug("selectAddressList:" + addressVo);
		LOGGER.debug(addressBookIf + "");

		List<AddressVo> addressList = addressBookIf.selectAddressList(addressVo);
		
		addressVo.getAge();
		addressVo.getName();
		addressVo.getGender();
		addressVo.getAddress();

		addressList.add(addressVo);
		return addressList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.debug("insertAddress:" + addressVo);
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws RemoteException {
		// TODO Auto-generated method stub
		LOGGER.debug("updateAddress:" + addressVo);
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws RemoteException {
		// TODO Auto-generated method stub
		LOGGER.debug("deleteAddress:" + addressVo);
		return 0;
	}
}
