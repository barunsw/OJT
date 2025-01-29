package com.barunsw.ojt.mjg.day02;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Person extends Object {
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
	
	@Override
	public String toString() {
		return String.format("age:%s, name:%s", age, name);
//		return ToStringBuilder.reflectionToString(this);
	}
}
