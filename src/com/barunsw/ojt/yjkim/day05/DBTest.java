package com.barunsw.ojt.yjkim.day05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.barunsw.ojt.constants.Gender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBTest {
	private static final Logger LOGGER = LogManager.getLogger(DBTest.class);
	
	public static void main(String[] args) {
		LOGGER.debug("main");
		String url 		= "jdbc:mysql://localhost:3306/db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
		String user 	= "root";
		String passwd 	= "root";
		
		Connection conn = null;
		Statement statement = null;
		PreparedStatement preparestatement = null;

		//ResulSet은 executeQuery( ) 메소드에서 실행된 select 문의 결과값을 가지고 있는 객체이다.
		ResultSet resultset = null;
		String sql = "";
		
		//insert 예제
		//insert시 executeUpdate()를 사용한다. 
		
		/*try {
			//Class.forName()에 의해
			//JDBC 드라이버 파일의 드라이버 인터페이스를 상속한 클래스가
			//동적으로 로딩될 때 자동으로 JDBC 드라이버 인스턴스가 생성되어 준비가 완료된다.
			Class.forName("org.mariadb.jdbc.Driver");
			
			//DriverManager.getConnection() 
			//실제 자바 프로그램과 데이터베이스를 네트워크상에서 연결해주는 메소드이며
			//연결에 성공하면 DB와 연결된 상태를 Connection 객체로 반환한다.
			conn = DriverManager.getConnection(url,user,passwd);
			
			//Statement 객체는 자바프로그램은 DB쪽으로 SQL 문을 전송하고,
			//DB는 처리된 결과를 다시 프로그램  쪽으로 
			//전달하는 역할을 한다.
			statement = conn.createStatement();
			sql = "insert into TB_PERSON (NAME,GENDER,AGE,PHONE,ADDRESS) values ('이효리','WOMAN','38','010-3321-3532','제주특별시')";
			statement.executeUpdate(sql);
			
		} catch (ClassNotFoundException cnfe) {
			LOGGER.error(cnfe.getMessage(), cnfe);
		} catch (SQLException se) {
			LOGGER.error(se.getMessage(), se);
		}finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException se) {
					LOGGER.error(se.getMessage(), se);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException se) {
					LOGGER.error(se.getMessage(), se);
				}
			}
		}*/
		
		//update 예제
		/*try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,passwd);
			preparestatement = conn.prepareStatement("update TB_PERSON set AGE = ? where NAME = ?");
			preparestatement.setInt(1, 20);
			preparestatement.setString(2, "김윤제");
			preparestatement.executeUpdate();
		} catch (ClassNotFoundException cnfe) {
			LOGGER.error(cnfe.getMessage(), cnfe);
		} catch (SQLException se) {
			LOGGER.error(se.getMessage(), se);
		} finally {
			if(preparestatement != null) {
				try {
					preparestatement.close();
				} catch (SQLException se) {
					LOGGER.error(se.getMessage(), se);
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException se) {
					LOGGER.error(se.getMessage(), se);
				}
			}
		}
		*/
		/*
		//select 예제 
		List<Person> person_list = new ArrayList<Person>();
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,passwd);
			preparestatement = conn.prepareStatement("select * from TB_PERSON");
			resultset = preparestatement.executeQuery();
			
			while(resultset.next()) {
				Person person = new Person();
				person.setName(resultset.getString(2));
				person.setGender(Gender.toGender(resultset.getString(3)));
				person.setAge(resultset.getInt(4));
				person.setPhone(resultset.getString(5));
				person.setAddress(resultset.getString(6));
				
				person_list.add(person);
			}
			for(Person person : person_list) {
				LOGGER.debug(String.format("이름 [%s] 성별 [%s] 나이[%d] 전화번호 [%s] 주소[%s]", 
						person.getName(), person.getGender(), person.getAge(), person.getPhone(), person.getAddress()));
			}
		} catch (ClassNotFoundException cnfe) {
			LOGGER.error(cnfe.getMessage(), cnfe);
		} catch (SQLException se) {
			LOGGER.error(se.getMessage(), se);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

		} finally {
			if(resultset != null) {
				try {
					resultset.close();
				} catch (SQLException se) {
					LOGGER.error(se.getMessage(), se);
				}
			}
			if (preparestatement != null) {
				try {
					preparestatement.close();
				} catch (SQLException se) {
					LOGGER.error(se.getMessage(), se);
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException se) {
					LOGGER.error(se.getMessage(), se);
				}
			}
		}
		*/
		
		//delete 예제
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,passwd);
			preparestatement = conn.prepareStatement("delete from TB_PERSON where NAME = ?");
			preparestatement.setString(1, "김윤제");
			preparestatement.executeUpdate();
			
		} catch (ClassNotFoundException cnfe) {
			LOGGER.error(cnfe.getMessage(), cnfe);
		} catch (SQLException se) {
			LOGGER.error(se.getMessage(), se);
		} finally {
			if (preparestatement != null) {
				try {
					preparestatement.close();
				} catch (SQLException se) {
					LOGGER.error(se.getMessage(), se);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException se) {
					LOGGER.error(se.getMessage(), se);
				}
			}
		}
		
		
		/*try {
			//드라이버 정보를 로드한다.
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url 		= "jdbc:mysql://localhost:3306/db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
			String user 	= "root";
			String passwd 	= "root";
			
			//드라이버 연결 
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
				
				/*resultSet = statement.executeQuery("SELECT NAME, GENDER, AGE FROM TB_PERSON");
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
		*/
		
		
		
	}
}
