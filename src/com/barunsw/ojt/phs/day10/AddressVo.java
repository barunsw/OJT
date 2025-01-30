package com.barunsw.ojt.phs.day10;

import java.io.Serializable;

public class AddressVo implements Serializable{
	private String name;
	private int age;
	private String gender;
	private String phone;
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
		// return ToStringBuilder.reflectionToString(this);
		return String.format("%s,%s,%s,%s,%s", name, age, gender, phone, address);
	}
}
