package com.barunsw.ojt.jyb.day2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassVariableExample {

	static int classVariable = 0;

	private static final Logger logger = LoggerFactory.getLogger(LoggerExample.class);

	public ClassVariableExample() {
		classVariable++; // 인스턴스가 생성될 때마다 증가
	}

	public static void main(String[] args) {
		//인스턴스를 생성하지 않고 클래스 변수에 접근
		logger.info("Initial count : {}", ClassVariableExample.classVariable);
		
		//첫 번째 인스턴스 생성
		ClassVariableExample obj1 = new ClassVariableExample();
		//두 번째 인스턴스 생성
		ClassVariableExample obj2 = new ClassVariableExample();

		//인스턴스를 통해 접근
		logger.info("total : {}", classVariable);
	}

}
