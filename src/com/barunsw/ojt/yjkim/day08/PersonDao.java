package com.barunsw.ojt.yjkim.day08;

import java.util.List;
import java.util.Map;

public interface PersonDao {
	public void insert_Person(Map<String,Object> map);
	
	public List<Map<String,Object>> select_Person();
	
	public void delete_Person(Object Param);

	public void update_Person(Map<String,Object> map);
	
	public List<Map<String,Object>> select_Particular_Person(Map<String,Object> map);
}
