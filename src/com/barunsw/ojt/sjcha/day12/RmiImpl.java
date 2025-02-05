package com.barunsw.ojt.sjcha.day12;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RmiImpl extends UnicastRemoteObject implements AddressBookInterface {

	private AddressBookInterface mybatisaddressbook = new MybatisAddressBookImpl(); 

	protected RmiImpl() throws RemoteException {
		super();
	}

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) throws Exception {
		List<AddressVo> personList = mybatisaddressbook.selectAddressList(addressVo);
		return personList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		mybatisaddressbook.insertAddress(addressVo);
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		mybatisaddressbook.updateAddress(addressVo);
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		mybatisaddressbook.deleteAddress(addressVo);
		return 0;
	}
}
