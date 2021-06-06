package com.barunsw.ojt.iwkim.day05.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.barunsw.ojt.iwkim.common.PersonInfo;




public class CliService {
	Scanner sc = new Scanner(System.in);

	PersonInfo person;
	
	String name;
	String gender;
	String birth;
	String email;
	String regDate;
	String updateDate;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void start(){
		System.out.println();
		System.out.println("'C'  입력 -> 개인정보 입력");
		System.out.println("'U'  입력 -> 개인정보 수정");
		System.out.println("'D'  입력 -> 개인정보 삭제");
		System.out.println("'R' 입력 -> 개인정보 출력");
		System.out.println("'END' 입력 -> 프로그램 종료");
		System.out.println();
		System.out.print("입력 : ");
	};
	
	public PersonInfo inputPersonInfo(){
		System.out.println();
		System.out.println("개인정보를 입력해주세요.");
		System.out.print("이름 : ");
		name = sc.next();
		System.out.print("성별 : ");
		gender = sc.next();
		System.out.print("생년월일 : ");
		birth = sc.next();
		System.out.print("이메일 : ");
		email = sc.next();
		
		person = new PersonInfo();
		Date date = new Date();
		String standardDate = format.format(date);
		regDate = standardDate;
		updateDate = standardDate;
		
		person.setName(name);
		person.setGender(gender);
		person.setBirth(birth);
		person.setEmail(email);
		person.setRegDate(regDate);
		person.setUpdateDate(updateDate);
		
		return person;	
	}
	
	public PersonInfo inputPersonInfoForUpdate(String name){
		System.out.println();
		System.out.println("개인정보를 입력해주세요.");
		System.out.print("성별 : ");
		gender = sc.next();
		System.out.print("생년월일 : ");
		birth = sc.next();
		System.out.print("이메일 : ");
		email = sc.next();
		
		person = new PersonInfo();
		Date date = new Date();
		regDate = format.format(date);
		updateDate = format.format(date);
		
		person.setName(name);
		person.setGender(gender);
		person.setBirth(birth);
		person.setEmail(email);
		person.setRegDate(regDate);
		person.setUpdateDate(updateDate);
		
		return person;	
	}
	
	public String certify(){
		System.out.println();
		System.out.println("먼저 이름을 입력해주세요.");
		System.out.print("이름 : ");
		name = sc.next();
		return name;
	}
	
	public void resultSelect(PersonInfo person){
		if (person != null) {
			name = person.getName();
			gender = person.getGender();
			birth = person.getBirth();
			email = person.getEmail();
			regDate = person.getRegDate();
			updateDate = person.getUpdateDate();
			System.out.print("검색 결과 : ");
			System.out.println(String.format("%s, %s, %s, %s, %s, %s", name, gender, birth, email, regDate, updateDate));
		}
		else {
			System.out.println("검색 결과가 존재하지 않습니다.");
		}
	}

	
}

















