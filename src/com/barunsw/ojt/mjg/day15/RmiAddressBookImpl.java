package com.barunsw.ojt.mjg.day15;

import java.io.IOException;
import java.io.Reader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.common.AddressBookInterface;
import com.barunsw.ojt.common.RmiAddressBookInterface;
import com.barunsw.ojt.vo.AddressVo;

public class RmiAddressBookImpl extends UnicastRemoteObject implements RmiAddressBookInterface {
	private AddressBookInterface addressBookIf;

	RmiAddressBookImpl() throws RemoteException {
		super();

		Properties properties = new Properties();
		try (Reader reader = Resources.getResourceAsReader("config.properties")) {
			// config.properties 로드
			properties.load(reader);

			// 설정 파일에서 클래스 이름 추출
			String addressIfClass = properties.getProperty("address_if_class");

			// 클래스 동적 로딩 및 인스턴스화
			Object o = Class.forName(addressIfClass).newInstance();

			// 인터페이스로 캐스팅
			addressBookIf = (AddressBookInterface) o;

		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		catch (InstantiationException ie) {
			ie.printStackTrace();
		}
		catch (IllegalAccessException iae) {
			iae.printStackTrace();
		}
		catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) throws RemoteException{
			try {
				return addressBookIf.selectAddressList(addressVo);
			}
			catch (Exception ex) {
				throw new RemoteException(ex.getMessage(), ex);
			}

	}

	@Override
	public int insertAddress(AddressVo addressVo) throws RemoteException {
		try {
			return addressBookIf.insertAddress(addressVo);
		}
		catch (Exception ex) {
			throw new RemoteException(ex.getMessage(), ex);
		}
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws RemoteException {
		try {
			return addressBookIf.updateAddress(addressVo);
		}
		catch (Exception ex) {
			throw new RemoteException(ex.getMessage(), ex);
		}
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws RemoteException {
		try {
			return addressBookIf.deleteAddress(addressVo);
		}
		catch (Exception ex) {
			throw new RemoteException(ex.getMessage(), ex);
		}
	}
}