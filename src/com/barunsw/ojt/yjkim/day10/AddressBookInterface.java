package com.barunsw.ojt.yjkim.day10;

import java.util.List;

public interface AddressBookInterface {
	public List<AddressVo> selectAddressList() throws Exception;
	public int insertAddress(AddressVo addressVo) throws Exception;
	public int updateAddress(int index, AddressVo addressVo) throws Exception;
	public int deleteAddress(int index, AddressVo addressVo) throws Exception;
}
