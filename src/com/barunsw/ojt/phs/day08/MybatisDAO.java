package com.barunsw.ojt.phs.day08;

import java.util.Vector;

public interface MybatisDAO {
	public Vector<PersonVO> selectPerson();
	
	public void updateAge();
	public void updateGender();
	public void updatePhone();
	public void updateAddress();
	
	public void deletePerson();
	
	public void insertPerson();
}
