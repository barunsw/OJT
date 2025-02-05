package com.barunsw.ojt.sjcha.day02;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Person extends Object{
	private int age;
	private String name;
	
	public Person(int age, String name) {
		this.age = age;
		this.name = name;
	}
	
	public int getAge(int age) {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getName(String name) {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
