package com.barunsw.ojt.iwkim.common;

import java.util.List;

/**
 * 주소록 인터페이스
 * 
 * @author sbae7
 *
 */
public interface AddressBookInterface {
	// 주소록 조회
	public List<PersonInfo> selectList(PersonInfo param) throws Exception;
	// 주소록 추가
	public int insertPerson(PersonInfo param) throws Exception;
	// 주소록 수정
	public int updatePerson(PersonInfo param) throws Exception;
	// 주소록 삭제
	public int deletePerson(PersonInfo param) throws Exception;
}








