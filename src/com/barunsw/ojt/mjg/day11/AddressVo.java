package com.barunsw.ojt.mjg.day11;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.barunsw.ojt.mjg.constants.Gender;

public class AddressVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int seq;
	private String name;
	private Gender gender;
	private int age;
	private String address;
	private String phone;
	private String initial;
	

	public AddressVo(int seq, String name, int age, Gender gender, String address, String phone) {
		this.seq = seq;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
    }
	
//	public AddressVo(String name, int age, Gender gender, String address) {
//        this.name = name;
//        this.age = age;
//        this.gender = gender;
//        this.address = address;
//    }
	
	public AddressVo() {
	}

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
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
    public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	@Override
	public String toString() {
		return "seq=" + seq + ", name=" + name + ", gender=" + gender + ", age=" + age + ", " + 
			   "address=" + address + ", phone=" + phone;
	}

}
