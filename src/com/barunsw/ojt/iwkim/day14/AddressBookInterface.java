package com.barunsw.ojt.iwkim.day14;

import java.rmi.Remote;
import java.util.List;

import com.barunsw.ojt.iwkim.common.PersonVO;

public interface AddressBookInterface extends Remote {
	// 주소록 조회
		public List<PersonVO> selectList() throws Exception;
		// 주소록 추가
		public int insertPerson(PersonVO param) throws Exception;
		// 주소록 수정
		public int updatePerson(PersonVO param) throws Exception;
		// 주소록 삭제
		public int deletePerson(String name) throws Exception;
}
