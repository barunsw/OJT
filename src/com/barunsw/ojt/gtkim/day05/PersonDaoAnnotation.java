package com.barunsw.ojt.gtkim.day05;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PersonDaoAnnotation {
	
	@Select("SELECT * FROM TB_PERSON")
	public List<Person> selectPersonList();
	
	@Insert("INSERT INTO TB_PERSON VALUES(0, #{name},#{gender},#{age},#{phone},#{address})")
	public int insertPerson(Person onePerson);
	
	@Update("UPDATE TB_PERSON SET GENDER=#{gender}, AGE=#{age} WHERE name=#{name}")
	public int updatePersonByName(Person updatePerson);
	
	@Delete("DELETE FROM TB_PERSON WHERE NAME=#{name}")
	public int deletePersonByName(Person onePerson);
	
	@Update("		CREATE TABLE IF NOT EXISTS TB_PERSON3 (\r\n" + 
			"		SEQ INT PRIMARY KEY AUTO_INCREMENT\r\n" + 
			"		, NAME VARCHAR(32) NOT NULL\r\n" + 
			"		, GENDER ENUM('MAN', 'WOMAN') NOT NULL\r\n" + 
			"		, AGE INT\r\n" + 
			"		, PHONE VARCHAR(32)\r\n" + 
			"		, ADDRESS VARCHAR(256)\r\n" + 
			"		) ENGINE=InnoDB DEFAULT CHARACTER SET UTF8 COLLATE UTF8_GENERAL_CI;")
	public void createTestTable();
	
	@Update("DROP TABLE TB_PERSON3")
	public void dropTestTable();
}
