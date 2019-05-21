package com.barunsw.ojt.yjkim.day02;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Person {
	private int age;
	private String name;
	
	public Person(int age, String name) {
		this.age = age;
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	//Object 클래스의 toString()메서드를 오버라이딩 한다.
	@Override
	public String toString() {
		//String.format은 출력할 대상의 출력 형식을 지정할 수 있다.
		//return String.format("age:%s, name:%s", age, name);
		
		//ToStringBuilder.reflectionToString(this)는 
		//Object 클래스의 toString() 메서드 값과 객체에 할당된 값이 출력된다. 
		return ToStringBuilder.reflectionToString(this);
	}
}
