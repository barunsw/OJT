package com.barunsw.ojt.mjg.day11.copy;

import java.util.List;

public interface AddressBookInterface {
	public List<AddressVo> selectAddressList(AddressVo addressVo);

	public int insertAddress(AddressVo onePerson) throws Exception;

	public int updateAddress(AddressVo addressVo) throws Exception;

	public int deleteAddress(AddressVo addressVo) throws Exception;
}
