package com.barunsw.ojt.yjkim.day05;

import java.util.List;
import java.util.Map;

public interface PersonDao {
	public List<Person> selectPersonList(Person person);
	
	public Person selectOnePerson(String name);
	
	public void insertPerson(Person person);
	
	public void updatePerson(Map<String,Object> map);
	
	public void deletePerson(int age);
	
}
