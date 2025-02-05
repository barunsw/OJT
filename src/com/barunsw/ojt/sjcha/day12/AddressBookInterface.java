package com.barunsw.ojt.sjcha.day12;

import java.rmi.Remote;
import java.util.List;

public interface AddressBookInterface extends Remote {
	// 주소록 조회
	public List<AddressVo> selectAddressList(AddressVo addressVo) throws Exception;
	// 주소록 데이터 삽입
	public int insertAddress(AddressVo addressVo) throws Exception;
	// 주소록 데이터 업데이트
	public int updateAddress(AddressVo addressVo) throws Exception;
	// 주소록 데이터 삭제
	public int deleteAddress(AddressVo addressVo) throws Exception;
}
