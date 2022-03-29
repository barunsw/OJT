package com.barunsw.ojt.cjs.day02;

public class OverideEx {

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;

	}
}
