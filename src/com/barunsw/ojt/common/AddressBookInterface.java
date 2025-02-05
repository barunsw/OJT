package com.barunsw.ojt.common;

import java.util.List;
import java.util.Map;

import com.barunsw.ojt.vo.AddressVo;

public interface AddressBookInterface {
	public List<AddressVo> selectAddressList() throws Exception;
	public int insertAddress(AddressVo addressVo) throws Exception;
	public int updateAddress(AddressVo addressVo) throws Exception;
	public int deleteAddress(AddressVo addressVo) throws Exception;
	public List<AddressVo> selectParticularAddress(Map<String, Object> map) throws Exception;
	
}
