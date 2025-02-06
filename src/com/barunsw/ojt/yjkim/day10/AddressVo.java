package com.barunsw.ojt.yjkim.day10;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AddressVo implements Serializable{
	private int seq;
	private String name;
	private int age;
	private Gender gender;
	private String Address;
	
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
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
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
		//return String.format("%s(%s)", name, age);
	}
	
	
}
