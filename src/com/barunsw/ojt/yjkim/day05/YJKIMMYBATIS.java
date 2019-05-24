package com.barunsw.ojt.yjkim.day05;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;

public class YJKIMMYBATIS {
	private static Logger LOGGER = LogManager.getLogger(PersonDAO2Impl.class);

	public static void main(String[] args) {
		
		PersonDAO2 inter = new PersonDAO2Impl();
		//select list 
		/*List<Person> list = inter.selectListPerson();
		for(int i = 0 ; i < list.size(); i++) {
			LOGGER.debug(String.format("이름[%s] 성별[%s] 나이[%d] 번호[%s] 주소[%s]",
					list.get(i).getName(), list.get(i).getGender(), list.get(i).getAge(),
					list.get(i).getPhone(), list.get(i).getAddress()));
		}
		*/
		//select one
		/*Person person = inter.selectOnePerson("이효리");
		LOGGER.debug(String.format("이름[%s] 성별[%s] 나이[%d] 번호[%s] 주소[%s]",
					person.getName(), person.getGender(), person.getAge(),
					person.getPhone(), person.getAddress()));
		//update
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name","이효리");
		map.put("age",28);
		inter.updatePerson(map);
		*/
		//delete
		  //inter.deletePerson(42);

		//insert
		Person person = new Person();
		person.setName("김상순");
		try {
			person.setGender(Gender.toGender("남자"));
			person.setAge(42);
			person.setPhone("010-3332-2333");
			person.setAddress("제주특별시");
			inter.insertPerson(person);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
