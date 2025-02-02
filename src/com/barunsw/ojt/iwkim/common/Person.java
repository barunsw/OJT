package com.barunsw.ojt.iwkim.common;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Person implements Serializable { // 객체를 파일이나 네트워크로 보낼때 바이트배열로 보내야 하므로 먼저 Serializable을 구현한다.
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
	public void setAge(int i) {
		this.age = i;
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
			return ToStringBuilder.reflectionToString(this);
		}
}
