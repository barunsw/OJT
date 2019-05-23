package com.barunsw.ojt.yjkim.day04;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.barunsw.ojt.constants.Gender;

public class Person implements Serializable {
	private String name;
	private Gender gender;
	private int age;
	private String phone;
	private String address;
	
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
	
	////ToStringBuilder.reflectionToString(this)는 
	//Object 클래스의 toString() 메서드 값과 객체에 할당된 값이 출력된다. 
	//commons-lang3 필요 
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
