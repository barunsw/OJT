package com.barunsw.ojt.gtkim.day09;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DP_IteratorTestMain {
	private static final Logger LOGGER = LogManager.getLogger(DP_IteratorTestMain.class);
	
	public static void main(String[] args) {
		LOGGER.debug("이터레이터 디자인패턴 예제!");
		
		DP_PersonCollection personList = new DP_PersonCollection(3);
		personList.addPerson(new PersonVO("김균태", 26));
		personList.addPerson(new PersonVO("슈퍼커피", 24));
		personList.addPerson(new PersonVO("퇴근 은제하냐", 22));
		
		DP_Iterator it = personList.iterator();
		while(it.hasNext()) {
			PersonVO onePerson = (PersonVO) it.next();
			LOGGER.debug("Person >> " + onePerson.toString());
		}
	}

}
