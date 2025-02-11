package com.barunsw.ojt.jyb.day2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstanceVariableExample {
	private static final Logger logger = LoggerFactory.getLogger(StringSharingExample.class);

	String color; // 인스턴스 변수
	String name; // 인스턴스 변수

	public InstanceVariableExample(String color, String name) {
		this.color = color;
		this.name = name;
	}

	public static void main(String[] args) {
		InstanceVariableExample ex1 = new InstanceVariableExample("red", "빨강");
		InstanceVariableExample ex2 = new InstanceVariableExample("blue", "파랑");

		logger.info(ex1.color);
		logger.info(ex2.color);
	}

}
