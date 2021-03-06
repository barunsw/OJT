package com.barunsw.ojt.yjkim.day14;

import java.util.List;
import java.util.Map;

import com.barunsw.ojt.vo.AddressVo;

public interface RmiAddressDao {
	public List<AddressVo> selectAddressList() throws Exception;
	public int insertAddress(AddressVo addressVo) throws Exception;
	public int updateAddress(AddressVo addressVo) throws Exception;
	public int deleteAddress(AddressVo addressVo) throws Exception;
	public List<AddressVo> selectParticularAddress(Map<String, Object> map) throws Exception;

}
