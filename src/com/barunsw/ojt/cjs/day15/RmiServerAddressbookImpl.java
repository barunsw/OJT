package com.barunsw.ojt.cjs.day15;

import java.lang.reflect.Constructor;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.rmi.server.UnicastRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.cjs.common.AddressBookInterface;
import com.barunsw.ojt.cjs.common.AddressVo;

public class RmiServerAddressbookImpl extends UnicastRemoteObject implements AddressBookInterface {
	private static final Logger LOGGER = LoggerFactory.getLogger(RmiServerAddressbookImpl.class);
	public static AddressBookInterface addressBookIf;

//	public synchronized static AddressBookInterface getInstance() {
//		if ( addressBookIf == null ) {
//			try {
//				addressBookIf = new RmiServerAddressbookImpl();
//			} catch (RemoteException e) {
//				LOGGER.error(e.getMessage(), e);
//			} 
//		}
//		return addressBookIf;
//	}

//	public void setAddressBookIf( AddressBookInterface info ){
//		addressBookIf = info;
//	}

	public RmiServerAddressbookImpl() throws RemoteException {
		super();
		try {
			initAddressBookIf();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initAddressBookIf() throws Exception {
		String className = ServerMain.serverProperties.getProperty("address_if_class");
		LOGGER.debug(className);
		Object o = null;
		String serverHost = ServerMain.serverProperties.getProperty("host");
		LOGGER.debug(serverHost);

		int serverPort = Integer.parseInt(ServerMain.serverProperties.getProperty("port"));
		LOGGER.debug(serverPort + "");

		if (className.contains("SocketAddressBookImpl")) {
			Constructor c = Class.forName(className).getConstructor(String.class, Integer.class);
			o = c.newInstance(serverHost, serverPort);
			LOGGER.debug(o + "");
		} else {
			o = Class.forName(className).newInstance();
			LOGGER.debug(className + "");
		}
		if (o != null && o instanceof AddressBookInterface) {
			addressBookIf = (AddressBookInterface) o;
		}
	}

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.debug("selectAddressList:" + addressVo);
		LOGGER.debug(addressBookIf + "");

		List<AddressVo> addressList = addressBookIf.selectAddressList(new AddressVo());
		List<AddressVo> resultList = new ArrayList<AddressVo>();
		for (AddressVo adressVo : addressList) {
			adressVo.getAge();
			adressVo.getName();
			adressVo.getGender();
			adressVo.getAddress();
			resultList.add(adressVo);
		}
		return resultList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		addressBookIf.insertAddress(addressVo);
		LOGGER.debug("insertAddress:" + addressVo);
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		addressBookIf.updateAddress(addressVo);
		LOGGER.debug("updateAddress:" + addressVo);
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		addressBookIf.deleteAddress(addressVo);
		LOGGER.debug("deleteAddress:" + addressVo);
		return 0;
	}
}
