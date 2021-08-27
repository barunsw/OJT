package com.barunsw.ojt.sjcha.day04;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.day03.Person;

public class DBTest {
	public static final Logger LOGGER = LogManager.getLogger(DBTest.class);
	
	// DB 접속 주소
	private final static String DB_URL = "jdbc:mysql://localhost:3306/OJT?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
	// DB ID
	private final static String USERNAME = "root";
	// DB password
	private final static String PASSWORD = "&YOUha6644";

	public static void main(String[] args) throws Exception {
		// Class 클래스의 forName 함수를 이용해서 해당 클래스를 메모리에 로드
		Class.forName("org.mariadb.jdbc.Driver");
		
		List<Person> personList1 = new ArrayList<Person>();
		String preparedSql = String.format("INSERT INTO TB_PERSON(NAME, GENDER, AGE, PHONE, ADDRESS) "
				+ "VALUES (?, ?, ?, ?, ?)");
		
		File outputFile = new File("data/day04/sjcha/address.dat");
		
		try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement();
				PreparedStatement preparedStatement = connection.prepareStatement(preparedSql);) {
			
			connectionCheck(connection);
			
			dbInsert(preparedStatement);
			
			personList1 = dbSelect(statement);
			
			output(personList1);
			
			input();
		}
	}
	
	// DB 연결 확인 함수
	public static void connectionCheck (Connection connection) {
		if (connection != null) {
			LOGGER.debug("connection 성공? : 성공 - " + connection);
		}
		else {
			LOGGER.debug("실패");
		}
	}
	
	// db 검색
	public static List<Person> dbSelect(Statement statement) throws Exception {
		// select 검색 결과로 ResultSet 객체의 값을 반환
		ResultSet resultSet1 = statement.executeQuery("SELECT * FROM TB_PERSON");
		
		Person onePerson = new Person();
		List<Person> personList = new ArrayList<Person>();
								
		// 한 row씩 접근하면서 데이터 가져옴
		while (resultSet1.next()) {
			// getString은 값을 가져온다
			String seq = resultSet1.getString(1);
			String name = resultSet1.getString(2);
			String gender = resultSet1.getString(3);
			String age = resultSet1.getString(4);
			String phonenumber = resultSet1.getString(5);
			String address = resultSet1.getString(6);
					
			onePerson.setName(name);
			onePerson.setGender(Gender.toGender(gender));
			onePerson.setAge(Integer.parseInt(age));
			onePerson.setPhone(phonenumber);
			onePerson.setAddress(address);
						
			personList.add(onePerson);
						
			LOGGER.debug(String.format("onePerson : %s", onePerson));
		}
		return personList;
	}
	
	// db 삽입
	public static void dbInsert(PreparedStatement preparedStatement) throws Exception {
		preparedStatement.setString(1, "홍길동");
		preparedStatement.setString(2, "MAN");
		preparedStatement.setInt(3, 30);
		preparedStatement.setString(4, "01022341234");
		preparedStatement.setString(5, "서울");
		
		int insertResult = preparedStatement.executeUpdate();
		
		LOGGER.debug("insert result : " + insertResult);
	}
	
	public static void output(List<Person> personList) {		
		File outputFile = new File("data/day04/sjcha/address.dat");
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(outputFile))) {
			for (Person person : personList) {
				outputStream.writeObject(person);
			}
		}
		catch (FileNotFoundException filenotfound) {
			LOGGER.error(filenotfound.getMessage(), filenotfound);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
	}
	
	public static void input() {
		File outputFile = new File("data/day04/sjcha/address.dat");
		// 객체를 읽어온다. 
		try (ObjectInputStream objectinputStream = new ObjectInputStream(new FileInputStream(outputFile))) {
			Object object;
			while ((object = objectinputStream.readObject()) != null) {
				LOGGER.debug("oneObject : " + object);
			}
		}
		catch (FileNotFoundException filenotfound) {
			LOGGER.error(filenotfound.getMessage(), filenotfound);
		}
		// 에러메시지를 작성하면 안된다.
		catch (EOFException eof) {
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		} 
		catch (ClassNotFoundException classnotfound) {
			LOGGER.error(classnotfound.getMessage(), classnotfound);
		}
	}
}
