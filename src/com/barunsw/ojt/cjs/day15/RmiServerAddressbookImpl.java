package com.barunsw.ojt.cjs.day15;

import java.lang.reflect.Constructor;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
		
		initAddressBookIf();
	}
	

	private void initAddressBookIf() throws Exception {

		String className = ServerMain.serverProperties.getProperty("address_if_class");
		LOGGER.debug(className);
		Object o = null;
		String serverHost = ServerMain.serverProperties.getProperty("host");
		LOGGER.debug(serverHost);

		int serverPort = Integer.parseInt(ServerMain.serverProperties.getProperty("port"));
		LOGGER.debug(serverPort + "");

		String regist = ServerMain.serverProperties.getProperty("regist");
		LOGGER.debug(regist);

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
		Registry registry = LocateRegistry.createRegistry(serverPort);
		registry.rebind(regist, addressBookIf);
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
