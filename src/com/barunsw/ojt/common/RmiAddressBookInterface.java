package com.barunsw.ojt.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.barunsw.ojt.vo.AddressVo;

public interface RmiAddressBookInterface extends Remote {
	public List<AddressVo> selectAddressList(AddressVo addressVo) throws RemoteException;
	public int insertAddress(AddressVo addressVo) throws RemoteException;
	public int updateAddress(AddressVo addressVo) throws RemoteException;
	public int deleteAddress(AddressVo addressVo) throws RemoteException;
}
