package com.barunsw.ojt.day02;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StringTest {
	private static final Logger LOGGER = LogManager.getLogger(StringTest.class);
	
	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {
		LOGGER.debug("main");
		
		final Person onePerson = new Person(25, "홍길동");
		LOGGER.debug("onePerson:" + onePerson);

		final String aaa = "Hello World";
		if (aaa.startsWith("Hello")) {
			LOGGER.debug("Hello로 시작");
		}

		if (aaa.endsWith("World")) {
			LOGGER.debug("World로 끝");
		}
		
//		final String bbb = "Hello World";
		String bbb = new String("Hello World");
		
		if (aaa.equals(bbb)) {
			LOGGER.debug("aaa와 bbb는 동일한 내용을 가진다.");
		}
		
		if (aaa == bbb) {
			LOGGER.debug("aaa와 bbb는 동일한 객체다.");
		}
		else {
			LOGGER.debug("aaa와 bbb는 동일한 객체가 아니다.");
		}
		
		final int niceIndex = aaa.indexOf("Nice");
		LOGGER.debug("niceIndex:" + niceIndex);
		
		final int worldIndex = aaa.indexOf("World");
		LOGGER.debug("worldIndex:" + worldIndex);
		
		final String ccc = "";
		if (ccc.length() > 0) {
			LOGGER.debug("CCC는 빈 스트링");
		}
		
		if (ccc.isEmpty()) {
			LOGGER.debug("CCC는 빈 스트링");
		}
		
		String ddd = "aaa,bbb,ccc";
		String[] splitList = ddd.split(",");
		int index = 0;
		for (String oneWord:splitList) {
			LOGGER.debug(String.format("[%d]%s", index++, oneWord));
		}
		
		String rawText = "\n\tHello World\n\n    ";
		String trimText = rawText.trim();
		LOGGER.debug(String.format("raw:[%s], trim:[%s]", rawText, trimText));
		
		String phoneNum = "010-1111-2222";
		String regText = "(\\d+)-(\\d+)-(\\d+)";
		if (phoneNum.matches(regText)) {
			LOGGER.debug("정규표현식 일치");
		}
		else {
			LOGGER.debug("정규표현식 일치하지 않음");
		}
		
		String driverLicense = "운전면허번호:92-016593-02-1";
		String rt = ".*(\\d{2}-(\\d{6})-\\d{2}-\\d{1})";
		
		Pattern p = Pattern.compile(".*(\\d{2}-(\\d{6})-\\d{2}-\\d{1})");
		Matcher m = p.matcher(driverLicense);
		if (m.matches()) {
			System.out.println("group1:" + m.group(1));
			System.out.println("group2:" + m.group(2));
		}
		
		if (driverLicense.matches(rt)) {
			
			LOGGER.debug("정규표현식 일치");
		}
	}
}
