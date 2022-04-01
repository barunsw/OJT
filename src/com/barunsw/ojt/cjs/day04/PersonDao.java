package com.barunsw.ojt.cjs.day04;

import java.util.List;

public interface PersonDao {
	public List<Person> selectPersonList(Person person);
}