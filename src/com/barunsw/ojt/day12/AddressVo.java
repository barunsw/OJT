package com.barunsw.ojt.day12;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AddressVo implements Serializable {
	private String name;
	private int age;
	
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
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
