package com.barunsw.ojt.cjs.day15;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.barunsw.ojt.cjs.common.AddressVo;

public interface RemoteAddressBookInterface extends Remote {
	public List<AddressVo> selectAddressList(AddressVo addressVo) throws RemoteException;
	public int insertAddress(AddressVo addressVo) throws RemoteException;
	public int updateAddress(AddressVo addressVo) throws RemoteException;
	public int deleteAddress(AddressVo addressVo) throws RemoteException;
}
