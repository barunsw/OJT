package com.barunsw.ojt.phs.day08;

import java.util.Vector;

public interface MybatisDAO {
	public Vector<PersonVO> selectPerson();
	
	public void insertPerson(PersonVO personVO);
	
	public void deletePerson(String name);
	
	public void updatePerson(PersonVO personVO);
	
	public void createTable();
}