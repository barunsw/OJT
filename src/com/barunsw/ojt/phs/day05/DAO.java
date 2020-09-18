package com.barunsw.ojt.phs.day05;

import java.util.List;

import com.barunsw.ojt.phs.day05.PersonDTO;

public interface DAO {
	public List<PersonDTO> selectPersonList();
	
	public PersonDTO selectOnePerson(String name);
	
	public int insertPerson(PersonDTO person);
	
	public int updatePerson(PersonDTO person);
	
	public int deletePerson(PersonDTO person);
}
