package com.barunsw.ojt.mjg.common;

import java.util.List;

public interface AddressBookInterface {
	
	// select
	public List<AddressBookVo> selectAddressList(AddressBookVo addressBookVo) throws Exception;
	
	// insert
	public int insertAddress(AddressBookVo addressBookVo) throws Exception;
	
	// update
	public int updateAddress(AddressBookVo addressBookVo) throws Exception;
	
	// delete
	public int deleteAddress(int deleteSeq) throws Exception;
	
	public AddressBookVo selectBySeq(int seq);
	
	// 성이 "문"인 사람만 Select
	public List<AddressBookVo> selectByLastNameMoon(AddressBookVo addressBookVo);
	
	// 1년마다 나이 +1
	public int updateAllAges();
}
