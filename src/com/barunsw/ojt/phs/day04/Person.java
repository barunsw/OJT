package com.barunsw.ojt.phs.day04;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.barunsw.ojt.constants.Gender;

public class Person implements Serializable {
	private String name;
	private Gender gender;
	private int age;
	private String phone;
	private String address;
	
	public Person() {
		
	}
	
	public Person(String name, int age) {
		this.name = name;
		this.age  = age;	
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
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
		return ToStringBuilder.reflectionToString(this,ToStringStyle.NO_CLASS_NAME_STYLE);
	}
}
