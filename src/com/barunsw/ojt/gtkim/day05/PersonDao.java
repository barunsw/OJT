package com.barunsw.ojt.gtkim.day05;

import java.util.List;

public interface PersonDao {
	public List<Person> selectPersonList();
	
	public int insertPerson(Person onePerson);
	
	public int updatePersonByName(Person updatePerson);
	
	public int deletePersonByName(Person onePerson);
	
	public void createTestTable();
}
