package com.barunsw.ojt.gtkim.day09;

import java.util.List;
import java.util.Vector;

public interface PersonDao {
	public List<PersonVO> selectPersonList();
	
	public int insertPerson(PersonVO onePerson);
	
	public int updatePerson(PersonVO updatePerson);
	
	public int updatePersonList(List<PersonVO> updatePersonList);
	
	public int deletePerson(PersonVO onePerson);
	
	public void createTestTable();
}
