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
	public void start() {
		System.out.println();
		System.out.println("'C'  입력 -> 개인정보 입력");
		System.out.println("'U'  입력 -> 개인정보 수정");
		System.out.println("'D'  입력 -> 개인정보 삭제");
		System.out.println("'RF' 입력 -> 개인정보 출력(파일)");
		System.out.println("'RD' 입력 -> 개인정보 출력(DB)");
		System.out.println("'END' 입력 -> 프로그램 종료");
		System.out.println();
		System.out.print("입력 : ");
	};
	
	public PersonInfo insertPersonInfo() {
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
		
		person = new PersonInfo();
		Date date = new Date();
		String regDate = format.format(date);
		String updateDate = format.format(date);
		
		person.setName(name);
		person.setGender(gender);
		person.setBirth(birth);
		person.setEmail(email);
		person.setRegDate(regDate);
		person.setUpdateDate(updateDate);
		
		return person;
		
		
	}
	
	
	
}

















