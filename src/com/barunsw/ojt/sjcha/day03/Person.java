package com.barunsw.ojt.sjcha.day03;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.barunsw.ojt.constants.Gender;

public class Person implements Serializable {
	private String name;
	private Gender gender;
	private int age;
	private String phone;
	private String address;
	
	// 이름 
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	// 성별
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	// 나이
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	// 핸드폰 번호
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	// 주소
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