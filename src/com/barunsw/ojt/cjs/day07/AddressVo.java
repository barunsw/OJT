package com.barunsw.ojt.cjs.day07;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AddressVo {
	private String name;
	private int age;
	private String gender;
	private String address;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
