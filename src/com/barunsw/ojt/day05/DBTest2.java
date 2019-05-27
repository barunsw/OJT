package com.barunsw.ojt.day05;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBTest2 {
	private static final Logger LOGGER = LogManager.getLogger(DBTest2.class);
	
	public static void main(String[] args) {
		PersonService personService = new PersonServiceImpl();
		
		List<Person> personList = personService.selectPersonList(null);
	}
}
