package com.barunsw.ojt.cjs.day14;

import java.util.List;

import com.barunsw.ojt.vo.AddressVo;

public interface AddressBookInterface {
	/**
	 * 조회
	 * @param addressVo
	 * @return
	 * @throws Exception 
	 */
	public List<AddressVo> selectAddressList(AddressVo addressVo) throws Exception;
	
	/**
	 * 추가
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
