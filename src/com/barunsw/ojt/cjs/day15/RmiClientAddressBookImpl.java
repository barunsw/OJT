package com.barunsw.ojt.cjs.day15;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.cjs.common.AddressBookInterface;
import com.barunsw.ojt.cjs.common.AddressVo;

public class RmiClientAddressBookImpl implements AddressBookInterface {

	private static final Logger LOGGER = LoggerFactory.getLogger(RmiServerAddressbookImpl.class);

	public RmiClientAddressBookImpl() throws RemoteException {
		
	}

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) throws RemoteException {
		// TODO Auto-generated method stub
		LOGGER.debug("selectAddressList:" + addressVo);
		LOGGER.debug(AddressBookPanel.addressBookIf + "");

		List<AddressVo> addressList = null;
		try {
			addressList = AddressBookPanel.addressBookIf.selectAddressList(new AddressVo());
			for (AddressVo oneAddressVo : addressList) {
				addressVo.getName();
				addressVo.getAge();
				addressVo.getGender();
				addressVo.getAddress();
				addressList.add(addressVo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addressList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws RemoteException {
		try {
			AddressBookPanel.addressBookIf.insertAddress(addressVo);
			LOGGER.debug("insertAddress:" + addressVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
