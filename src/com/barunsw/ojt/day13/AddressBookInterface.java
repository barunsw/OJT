package com.barunsw.ojt.day13;

import java.util.List;

public interface AddressBookInterface {
	public List<AddressVo> selectAddressList(AddressVo addressVo) throws Exception;
	public int insertAddress(AddressVo addressVo) throws Exception;
	public int updateAddress(AddressVo addressVo) throws Exception;
	public int deleteAddress(AddressVo addressVo) throws Exception;
}
