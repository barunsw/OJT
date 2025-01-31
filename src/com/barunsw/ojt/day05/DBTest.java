package com.barunsw.ojt.day05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;

public class DBTest {
	private static final Logger LOGGER = LogManager.getLogger(DBTest.class);
	
	public static void main(String[] args) {
		LOGGER.debug("main");
		
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url 		= "jdbc:mysql://localhost:3306/OJT?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
			String user 	= "root";
			String passwd 	= "real3817";
			
			conn = DriverManager.getConnection(url, user, passwd);
			
			List<Person> personList = new ArrayList<>();
			
			Statement statement = null;
			ResultSet resultSet = null;
			try {
				statement = conn.createStatement();
				// SELECT는 executeQuery
				// INSERT, UPDATE, DELETE는 executeUpdate
				/*
				String insertSql = "INSERT INTO TB_PERSON "
						+ "VALUES(0, '홍길동', 'MAN', 20, '010-0000-0000', '서울시 강남구')";
				statement.executeUpdate(insertSql);
				*/
				
				resultSet = statement.executeQuery("SELECT NAME, GENDER, AGE FROM TB_PERSON");
				while (resultSet.next()) {
					String name = resultSet.getString(1);
					Gender gender = Gender.toGender(resultSet.getString(2));
					int age = resultSet.getInt(3);
					
					Person onePerson = new Person();
					onePerson.setName(name);
					onePerson.setGender(gender);
					onePerson.setAge(age);
					
					personList.add(onePerson);
				}
				
				for (int i = 0; i < personList.size(); i++) {
					LOGGER.debug(String.format("[%d]%s", i, personList.get(i)));
				}
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
			finally {
				if (statement != null) {
					try {
						statement.close();
					}
					catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}
				
				if (resultSet != null) {
					try {
						LOGGER.debug("+++ resultSet.isClosed:" + resultSet.isClosed());
						resultSet.close();
						LOGGER.debug("--- resultSet.isClosed:" + resultSet.isClosed());
					}
					catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}
			}
/*			
			Person person = new Person();
			person.setName("홍길동");
	
			// 클래스 객체 동적 생성
			Object o = Class.forName("com.barunsw.ojt.day05.Person").newInstance();
			if (o instanceof Person) {
				Person p = (Person)o;
				p.setName("유관순");
			}
			
			LOGGER.debug("person:" + person);
			LOGGER.debug("person2:" + o);
*/			
		}
		catch (ClassNotFoundException cnfe) {
			LOGGER.error(cnfe.getMessage(), cnfe);
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
				}
				catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			}
		}
	}
}
