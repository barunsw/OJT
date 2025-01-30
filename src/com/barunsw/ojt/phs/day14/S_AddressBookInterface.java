package com.barunsw.ojt.phs.day14;

import java.rmi.Remote;
import java.util.List;

public interface S_AddressBookInterface extends Remote{
	public List<AddressVo> selectAddressList() throws Exception;
	
	public int insertAddress(AddressVo addressVo) throws Exception;
	
	public int updateAddress(AddressVo addressVo) throws Exception;
	
	public int deleteAddress(AddressVo addressVo) throws Exception;
	
	public int createStorage() throws Exception;
}