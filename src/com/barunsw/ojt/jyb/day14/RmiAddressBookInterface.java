package com.barunsw.ojt.jyb.day14;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.barunsw.ojt.common.AddressBookInterface;
import com.barunsw.ojt.vo.AddressVo;

public interface RmiAddressBookInterface extends AddressBookInterface, Remote {
	public List<AddressVo> selectAddressList(AddressVo addressVo) throws RemoteException;

	public int insertAddress(AddressVo addressVo) throws RemoteException;

	public int updateAddress(AddressVo addressVo) throws RemoteException;

	public int deleteAddress(AddressVo addressVo) throws RemoteException;
}
