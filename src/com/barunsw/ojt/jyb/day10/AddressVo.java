package com.barunsw.ojt.jyb.day10;

public class AddressVo {
	// 이름, 나이, 성별, 전화번호, 주소
	private int seq;
	private String name;
	private int age;
	private Gender gender;
	private String phone;
	private String address;

	public int getSeq() {
		return this.seq;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return this.gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "seq=" + seq + ", name=" + name + ", age=" + age + ", gender=" + gender + ", phone=" + phone
				+ ", address=" + address;

	}
}
