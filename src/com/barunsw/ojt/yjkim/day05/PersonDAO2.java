package com.barunsw.ojt.yjkim.day05;

import java.util.List;
import java.util.Map;

public interface PersonDAO2 {
	 
	public List<Person> selectListPerson();
	
	public Person selectOnePerson(String name);
	
	public void updatePerson(Map<String,Object> map);
	
	public void deletePerson(int age);
	
	public void insertPerson(Person person);
	
	
}
