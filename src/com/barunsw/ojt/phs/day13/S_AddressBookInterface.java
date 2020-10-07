package com.barunsw.ojt.phs.day13;

import java.util.List;

public interface S_AddressBookInterface {
	/**
	 * 주소록 조회
	 * @param addressVo
	 * @return
	 * @throws Exception
	 */
	public List<AddressVo> selectAddressList() throws Exception;
	
	/**
	 * 생성
	 * @param addressVo
	 * @return
	 * @throws Exception
	 */
	public int insertAddress(AddressVo addressVo) throws Exception;
	
	/**
	 * 수정
	 * @param addressVo
	 * @return
	 * @throws Exception
	 */
	public int updateAddress(AddressVo addressVo) throws Exception;
	
	/**
	 * 삭제
	 * @param addressVo
	 * @return
	 * @throws Exception
	 */
	public int deleteAddress(AddressVo addressVo) throws Exception;
	
	public int createStorage() throws Exception;
}
