package com.barunsw.ojt.sjcha.day05;

import java.util.List;

import com.barunsw.ojt.sjcha.day05.Person;

public interface PersonDao {
	// xml의 id에 맞는 함수 하나씩 선언하기
	// 앞에는 resultType, 매개변수는 파라미터 타입
	public List<Person> selectPersonList(Person person);
	
	// insert
	public int insertPersonList(Person insertPerson);
	
	// update
	public int updatePersonByName(Person updatePerson);
	
	// delete
	public int deletePersonByName(Person deletePerson);
	
	// 김씨 select
	public List<Person> selectPerson_Kim(Person person);
}
