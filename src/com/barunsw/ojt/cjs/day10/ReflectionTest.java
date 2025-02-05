package com.barunsw.ojt.cjs.day10;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionTest.class);

	public static void main(String[] args) throws Exception {
		Class cla = Child.class;
		LOGGER.debug(cla.getName()); // cla의 클래스 이름 가져온다

		Class cla2 = Class.forName("com.barunsw.ojt.cjs.day10.Child"); // 패키지이름이 포함된 클래스이름으로 작성
		LOGGER.debug(cla2 + "");

		Constructor constructor = cla2.getConstructor();
		LOGGER.debug(constructor.getName()); // 타입과 일치하는 생성자 반환

		Constructor constructor2 = cla2.getConstructor(String.class);
		LOGGER.debug(constructor2.getName()); // 타입과 일치하는 생성자 반환

		Method method = cla2.getDeclaredMethod("method4", int.class);
		LOGGER.debug(method + "");

		Child child = new Child();
		Field field = cla2.getField("str1");
		LOGGER.debug(field.get(child) + "");

		field.set(child, "문자열");
		LOGGER.debug(field.get(child) + "");
	}

}
