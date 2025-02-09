package com.barunsw.ojt.jyb.day10;

import java.util.List;

public interface AddressBookInterface {

	public List<AddressVo> selectAddressList(AddressVo addressVo);

	public int insertAddress(AddressVo addressVo);

	public int updateAddress(AddressVo addressVo);

	public int deleteAddress(AddressVo addressVo);

}
