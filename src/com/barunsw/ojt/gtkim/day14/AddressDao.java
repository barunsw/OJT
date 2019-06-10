package com.barunsw.ojt.gtkim.day14;

import java.util.List;

import com.barunsw.ojt.vo.AddressVo;

public interface AddressDao {
	public List<AddressVo> selectAddressList();
	
	public int insertAddress(AddressVo address);
	
	public int deleteAddress(AddressVo address);
	
	public int updateAddress(AddressVo address);	
	
	public int createAddressTable();
}
