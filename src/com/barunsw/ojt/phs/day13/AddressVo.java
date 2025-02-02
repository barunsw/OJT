package com.barunsw.ojt.phs.day13;

import java.io.Serializable;

public class AddressVo implements Serializable {
	private String command;
	private String name;
	private String age;
	private String gender;
	private String phone;
	private String address;

	public AddressVo() {

	}

	public AddressVo(String command, String name, String age, String gender, String phone, String address) {
		this.command = command;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.phone = phone;
		this.address = address;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
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

}
