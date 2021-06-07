package com.barunsw.ojt.iwkim.common;


public interface PersonInfoInterface {
	// 개인정보 조회
	public PersonInfo select(String name) throws Exception;
	// 개인정보 추가
	public int insertPerson(PersonInfo param) throws Exception;
	// 개인정보 수정
	public int updatePerson(PersonInfo param) throws Exception;
	// 개인정보 삭제
	public int deletePerson(String name) throws Exception;
	// 기본키 중복 확인
	public boolean isExistName(String name) throws Exception;
}








