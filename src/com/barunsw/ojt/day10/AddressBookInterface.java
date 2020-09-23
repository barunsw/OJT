package com.barunsw.ojt.day10;

import java.util.List;

public interface AddressBookInterface {
	/**
	 * 주소록 조회
	 * @param addressVo
	 * @return
	 * @throws Exception
	 */
	public List<AddressVo> selectAddressList(AddressVo addressVo) throws Exception;
	
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
}
