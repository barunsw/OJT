package com.barunsw.ojt.sjcha.day08;

import java.util.List;

import com.barunsw.ojt.sjcha.day08.AddressVo;

public interface AddressBookInterface {

	/**
	 * 주소록 조회
	 * @param addressVo
	 * @return
	 */
	public List<AddressVo> selectAddressList(AddressVo addressVo);

	/**
	 * 주소록 데이터 삽입
	 * @param onePerson
	 * @return
	 * @throws Exception
	 */
	public int insertAddress(AddressVo onePerson) throws Exception;

	/**
	 * 주소록 데이터 업데이트
	 * @param addressVo
	 * @return
	 * @throws Exception
	 */
	public int updateAddress(AddressVo addressVo) throws Exception;

	/**
	 * 주소록 데이터 삭제
	 * @param addressVo
	 * @return
	 * @throws Exception
	 */
	public int deleteAddress(AddressVo addressVo) throws Exception;
}
