package com.barunsw.ojt.yjkim.day10;

import java.util.List;
import java.util.Map;

public interface AddressBookInterface {
	public List<AddressVo> selectAddressList() throws Exception;
	public int insertAddress(AddressVo addressVo) throws Exception;
	public int updateAddress(int index, AddressVo addressVo) throws Exception;
	public int deleteAddress(int index) throws Exception;
	public List<AddressVo> selectParticularAddress(Map<String,Object> map) throws Exception;
	
}
