package com.barunsw.ojt.jyb.day14;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.barunsw.ojt.common.AddressBookInterface;
import com.barunsw.ojt.common.RmiAddressBookInterface;
import com.barunsw.ojt.jyb.day10.ObjectAddressBookImpl;
import com.barunsw.ojt.vo.AddressVo;

public class RmiAddressBookImpl extends UnicastRemoteObject implements RmiAddressBookInterface {
	private AddressBookInterface addressBookIf;
	
	protected RmiAddressBookImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		
		addressBookIf = new ObjectAddressBookImpl();
	}

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) throws RemoteException {
		// TODO Auto-generated method stub
		return addressBookIf.selectAddressList(addressVo);
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			return addressBookIf.insertAddress(addressVo);
		}
		catch (Exception ex) {
			throw new RemoteException(ex.getMessage(), ex);
		}
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			return addressBookIf.updateAddress(addressVo);
		}
		catch (Exception ex) {
			throw new RemoteException(ex.getMessage(), ex);
		}
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			return addressBookIf.deleteAddress(addressVo);
		}
		catch (Exception ex) {
			throw new RemoteException(ex.getMessage(), ex);
		}
	}

}
