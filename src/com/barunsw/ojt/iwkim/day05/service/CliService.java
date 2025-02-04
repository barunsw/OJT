package com.barunsw.ojt.iwkim.day05.service;

import java.util.Date;
import java.util.Scanner;
import com.barunsw.ojt.iwkim.common.DateUtil;
import com.barunsw.ojt.iwkim.common.PersonInfo;


public class CliService {
	Scanner sc = new Scanner(System.in);
	
	// ctrl + shift + r -> 블록 지정된 명칭으로 Resource 검색
	// alt + shift + r -> 커서가 위치한 변수명 또는 메소드 명을 일괄 변경
	// alt + shift + a -> 한번 : 세로 블록지정 가능하도록 모드 변경 , 두번 : 모드 빠져나오기
	// ctrl + shift + o -> 자동 import 
	
	public void start() {
		System.out.println();
		System.out.println("'C'  입력 -> 개인정보 입력");
		System.out.println("'U'  입력 -> 개인정보 수정");
		System.out.println("'D'  입력 -> 개인정보 삭제");
		System.out.println("'R' 입력 -> 개인정보 출력");
		System.out.println("'END' 입력 -> 프로그램 종료");
		System.out.println();
		System.out.print("입력 : ");
	};
	
	public PersonInfo inputPersonInfo() {
		System.out.println();
		System.out.println("개인정보를 입력해주세요.");
		System.out.print("이름 : ");
		String name = sc.next();
		System.out.print("성별 : ");
		String gender = sc.next();
		System.out.print("생년월일 : ");
		String birth = sc.next();
		System.out.print("이메일 : ");
		String email = sc.next();
		
		String standardDate = DateUtil.DEFAULT_DATE_FORMAT.format(new Date());
		String regDate = standardDate;
		String updateDate = standardDate;
		
		PersonInfo person = new PersonInfo();
		
		person.setName(name);
		person.setGender(gender);
		person.setBirth(birth);
		person.setEmail(email);
		person.setRegDate(regDate);
		person.setUpdateDate(updateDate);
		
		return person;	
	}
	
	public PersonInfo inputPersonInfoForUpdate(String name) {
		System.out.println();
		System.out.println("개인정보를 입력해주세요.");
		System.out.print("성별 : ");
		String gender = sc.next();
		System.out.print("생년월일 : ");
		String birth = sc.next();
		System.out.print("이메일 : ");
		String email = sc.next();
		
		PersonInfo person = new PersonInfo();
		person.setName(name);
		person.setGender(gender);
		person.setBirth(birth);
		person.setEmail(email);
		person.setUpdateDate(DateUtil.DEFAULT_DATE_FORMAT.format(new Date()));
		
		return person;	
	}
	
	public String certify() {
		System.out.println();
		System.out.println("먼저 이름을 입력해주세요.");
		System.out.print("이름 : ");
		String name = sc.next();
		return name;
	}
	
	public void resultSelect(PersonInfo person) {
		if (person != null) {
			String name 		= person.getName();
			String gender 		= person.getGender();
			String birth 		= person.getBirth();
			String email 		= person.getEmail();
			String regDate 		= person.getRegDate();
			String updateDate 	= person.getUpdateDate();
			System.out.print("검색 결과 : ");
			System.out.println(String.format("%s, %s, %s, %s, %s, %s", name, gender, birth, email, regDate, updateDate));
		}
		else {
			System.out.println("검색 결과가 존재하지 않습니다.");
		}
	}

	
}

















