package com.barunsw.ojt.cjs.day05;

import java.util.List;

public interface PersonDao {
	public List<Person> selectPersonList(Person person) throws Exception;
	public int insertPerson(Person person) throws Exception;
	public int updatePerson(Person person) throws Exception;
	public int deletePerson(int seq) throws Exception;
}