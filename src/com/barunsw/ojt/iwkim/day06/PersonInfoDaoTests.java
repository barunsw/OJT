package com.barunsw.ojt.iwkim.day06;

import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.PersonInfo;
import com.barunsw.ojt.iwkim.day05.service.CliService;

public class PersonInfoDaoTests {
	private static Logger LOGGER = LogManager.getLogger(PersonInfoDaoTests.class);
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
		LOGGER.info("sqlSessionFactory : " + sqlSessionFactory);
		// sql 명령어를 실행하기 위해 필요한 모든 메서드를 가지고 있는 SqlSession객체를 생성해주자 
		// 파라미터를 가지지 않는 디폴트 openSession()메소드는 다음과 같은 성격을 가진 SqlSession을 만든다.
		// 트랜잭션 스코프는 시작될 것이다.
		// Connection 객체는 활성화된 환경에 의해 설정된 DataSource인스턴스를 획득할 것이다.
		// 트랜잭션 격리 레벨은 드라이버나 데이터소스가 디폴트로 제공하는 옵션을 사용할 것이다.
		// PrepartedStatements는 재사용되지 않을 것이다. 그리고 update또한 배치처리되지 않을 것이다. 
		CliService cliService = new CliService();
		
		

		try (SqlSession session = sqlSessionFactory.openSession()) {
			LOGGER.info("session : " + sqlSessionFactory.openSession());
			PersonInfoDao personInfoDaoMapper = session.getMapper(PersonInfoDao.class);
			
			while (true) {
				cliService.start();
				String input = sc.next();
				String name;
				PersonInfo person;
				switch (input.toLowerCase()) {
					case "c" :
						person = cliService.inputPersonInfo();
						if (personInfoDaoMapper.isExistName(person.getName()) != 0) {
							System.out.println("중복된 이름이 존재합니다. 다시입력해 주세요");
						}
						else {
							personInfoDaoMapper.insertPerson(person);
							System.out.println("입력이 완료되었습니다.");
						}
						break;
					case "u" :
						name = cliService.certify();
						if (personInfoDaoMapper.isExistName(name) != 0) {
							person = cliService.inputPersonInfoForUpdate(name);
							personInfoDaoMapper.updatePerson(person);
							System.out.println("수정이 완료되었습니다.");
						}
						else {
							System.out.println("정보가 존재하지 않습니다. 다시 입력해주세요.");
						}
						break;
					case "d" :
						name = cliService.certify();
						if (personInfoDaoMapper.deletePerson(name) != 0) {
							System.out.println("삭제가 완료되었습니다.");
						}
						else {
							System.out.println("정보가 존재하지 않습니다. 다시 입력해주세요.");
						}
						break;
					case "r" :
						List<PersonInfo> personList = personInfoDaoMapper.selectPersonList();
						for(PersonInfo personInfo : personList) {
							LOGGER.info(personInfo);
						}	
					break;
						case "end" :
						System.out.println("프로그램이 종료됩니다.");
						return;
					default : 
						System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
						break;
				}
			}
		}
		catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
		}
		finally {
			sc.close();
		}
	}
}
