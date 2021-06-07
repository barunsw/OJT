package com.barunsw.ojt.iwkim.day06;

import java.util.List;

import com.barunsw.ojt.iwkim.common.PersonInfo;

public interface PersonInfoDao {
	// 개인정보 전체 조회
	public List<PersonInfo> selectPersonList() throws Exception;
	// 개인정보 추가
	public int insertPerson(PersonInfo param) throws Exception;
	// 개인정보 수정
	public int updatePerson(PersonInfo param) throws Exception;
	// 개인정보 삭제
	public int deletePerson(String name) throws Exception;
	// 기본키 중복 확인
	public int isExistName(String name) throws Exception;
}








