package com.barunsw.ojt.gtkim.day12;

import java.util.List;

public interface AddressDao {
	public List<AddressVo> selectAddressList();
	
	public int insertAddress(AddressVo address);
	
	public int deleteAddress(AddressVo address);
	
	public int updateAddress(AddressVo address);	
	
	public int createAddressTable();
}
