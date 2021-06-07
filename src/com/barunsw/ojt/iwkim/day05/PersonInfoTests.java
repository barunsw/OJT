package com.barunsw.ojt.iwkim.day05;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.PersonInfoInterface;
import com.barunsw.ojt.iwkim.day05.service.CliService;
import com.barunsw.ojt.iwkim.common.PersonInfo; 

public class PersonInfoTests {
	private static Logger LOGGER = LogManager.getLogger(PersonInfoTests.class);
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
 		PersonInfoInterface personInfo = new ObjectFilePersonInfoImpl();
// 		PersonInfoInterface personInfo = new TextFilePersonInfoImpl();
//		PersonInfoInterface personInfo = new DBPersonInfoImpl();
		
		CliService cService = new CliService();
		
		
		try {
			while (true) {
				cService.start();
				String input = sc.next();
				String name;
				PersonInfo person;
				switch (input.toLowerCase()) {
					case "c" :
						person = cService.inputPersonInfo();
						if (personInfo.isExistName(person.getName())) {
							System.out.println("중복된 이름이 존재합니다. 다시입력해 주세요");
						}
						else {
							personInfo.insertPerson(person);
							System.out.println("입력이 완료되었습니다.");
						}
						break;
					case "u" :
						name = cService.certify();
						if (personInfo.isExistName(name)) {
							person = cService.inputPersonInfoForUpdate(name);
							personInfo.updatePerson(person);
							System.out.println("수정이 완료되었습니다.");
						}
						else {
							System.out.println("정보가 존재하지 않습니다. 다시 입력해주세요.");
						}
						break;
					case "d" :
						name = cService.certify();
						if (personInfo.deletePerson(name) != 0) {
							System.out.println("삭제가 완료되었습니다.");
						}
						else {
							System.out.println("정보가 존재하지 않습니다. 다시 입력해주세요.");
						}
						break;
					case "r" :
						name = cService.certify();
						person = personInfo.select(name);
						if (person != null) {
							cService.resultSelect(person);
						}
						else {
							System.out.println("검색 결과가 존재하지 않습니다.");
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
