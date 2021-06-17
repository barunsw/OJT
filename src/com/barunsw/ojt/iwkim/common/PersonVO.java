package com.barunsw.ojt.iwkim.common;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
@SuppressWarnings("serial")
public class PersonVO implements Serializable{
	private String name;
	private String gender;
	private int age;
	private String phone;
	private String address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		// jTree일 경우 이름만나오게 하자
		// return String.format("%s", name);
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}