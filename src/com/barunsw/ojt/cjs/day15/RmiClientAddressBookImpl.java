package com.barunsw.ojt.cjs.day15;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.cjs.common.AddressBookInterface;
import com.barunsw.ojt.cjs.common.AddressVo;

public class RmiClientAddressBookImpl implements AddressBookInterface {

	private static final Logger LOGGER = LoggerFactory.getLogger(RmiClientAddressBookImpl.class);
	
	private RemoteAddressBookInterface addressBookIf;

	public RmiClientAddressBookImpl() throws RemoteException {

		String lookup = ClientMain.properties.getProperty("lookup");
		LOGGER.debug(lookup);
		int serverPort = Integer.parseInt(ClientMain.properties.getProperty("port"));
		LOGGER.debug(serverPort + "");

		Registry registry = LocateRegistry.getRegistry(serverPort);
		Remote remote;
		try {
			remote = registry.lookup(lookup);
			if (remote instanceof RemoteAddressBookInterface) {
				addressBookIf = (RemoteAddressBookInterface) remote;
//				AddressBookInterface temp = (RmiServerAddressbookImpl) RmiServerAddressbookImpl.getInstance();
//				temp = (AddressBookInterface) remote;
//				new RmiServerAddressbookImpl().setAddressBookIf(temp);
			}
			LOGGER.debug("된건가 ?");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) throws Exception {
		LOGGER.debug("selectAddressList:" + addressVo);
		return addressBookIf.selectAddressList(addressVo);
		/*
		List<AddressVo> addressList = null;
		List<AddressVo> resultList = new ArrayList<AddressVo>();
		try {
			addressList = addressBookIf.selectAddressList(new AddressVo());
			LOGGER.error("[CJS] addressList.size : {}", addressList.size());
			for (AddressVo oneAddressVo : addressList) {
				// LOGGER.info("[CJS] oneAddressVo : {}", oneAddressVo);
				AddressVo temp = new AddressVo();
				temp.setName(oneAddressVo.getName());
				temp.setAge(oneAddressVo.getAge());
				temp.setGender(oneAddressVo.getGender());
				temp.setAddress(oneAddressVo.getAddress());
				LOGGER.info("[CJS] temp : {}", temp);
				resultList.add(temp);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return resultList;
		*/
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		try {
			addressBookIf.insertAddress(addressVo);
			LOGGER.debug("insertAddress:" + addressVo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		try {
			addressBookIf.updateAddress(addressVo);
			LOGGER.debug("updateAddress:" + addressVo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return 0;		
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		try {
			addressBookIf.deleteAddress(addressVo);
			LOGGER.debug("deleteAddress:" + addressVo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return 0;
	}
}
