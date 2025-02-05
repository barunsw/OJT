package com.barunsw.ojt.cjs.day02;

import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringTest {

	private static Logger LOGGER = LoggerFactory.getLogger(StringTest.class);

	public static void main(String[] args) {

		String s1 = "hello";
		String s2 = "hello";
		String s3 = new String("hello");

		if (s1 == s2) { // 같은 주소의 값을 참조하므로 같음
			LOGGER.debug("Same sentence");
		} else {
			LOGGER.debug("Same not");
		}

		if (s1 == s3) { // s3를 객체로 선언하여 다른 참조값을 가지므로 다름
			LOGGER.debug("Same sentence");
		} else {
			LOGGER.debug("Same not");
		}

		String charAtTest = "Hello";
		LOGGER.debug(charAtTest.charAt(1) + ""); // 배열의 1번쨰 문자 e 반환

		String codePointTest = "Apple";
		LOGGER.debug(codePointTest.codePointAt(0) + ""); // A의 유니코드 65반환
		LOGGER.debug(codePointTest.codePointBefore(1) + ""); // 0번째 인덱스 유니코드 반환 65
		LOGGER.debug(codePointTest.codePointCount(1, 3) + " ");// 2

		String compareToTest = "App";
		LOGGER.debug(compareToTest.compareTo("App") + "");// 같으므로 0 반환
		LOGGER.debug(compareToTest.compareTo("A") + "");// 두 문자열의 길이 차이 반환
		LOGGER.debug(compareToTest.compareTo("ap") + ""); // 같은 인덱스 위치에 아스키코드 값의 차이 반환

		String concatTest1 = "바른 개발";
		String concatTest2 = "연구소";
		LOGGER.debug(concatTest1.concat(concatTest2));// 바른 개발연구소

		String containsTest1 = "StringContains";
		String containsTest2 = "Contains";
		if (containsTest1.contains(containsTest2)) {
			LOGGER.debug("Test1 in Test2"); // true
		} else {
			LOGGER.debug("Not Test1 in Test2");
		}

		// 문자열의 내용이 정확히 일치해야 true, StringBuffer, StringBuilder,Char Array와도 비교가능
		String contentEqualsTest1 = "contentEquals";
		String contentEqualsTest2 = "Equals";
		if (contentEqualsTest1.contentEquals(contentEqualsTest2)) {
			LOGGER.debug("Test1 in Test2");
		} else {
			LOGGER.debug("Not Test1 in Test2"); //false
		}

		char[] c = { 'H', 'e', 'l', 'l', 'o' };

		LOGGER.debug(String.copyValueOf(c));
		LOGGER.debug(String.copyValueOf(c, 3, 2));

		String equalsTest1 = "sameword";
		String equalsTest2 = "sameWord";

		LOGGER.debug(equalsTest1.equals("sameword") + " ");
		LOGGER.debug(equalsTest1.equals(equalsTest2) + " ");

		LOGGER.debug(equalsTest1.equalsIgnoreCase(equalsTest2) + " ");
		LOGGER.debug(equalsTest1.equalsIgnoreCase("SAMEWORD") + " ");

		Date today = new Date();
		int money = 3333;

		String formatTest = "formatTest";

		LOGGER.debug(String.format("%s__", formatTest)); // 문자열 뒤로 __붙음
		LOGGER.debug(String.format("%21s", formatTest)); // 문자열의 길이를 넘어서는 숫자가 나오면 그만큼 앞에 공백이 생김
		LOGGER.debug(String.format("$ %d", money)); // %conversion 필수, c언어 printf문 생각하면 될 듯..
		LOGGER.debug(String.format(Locale.KOREA, "%tp", today));

		String indexOfTest = "indexOf int";
		LOGGER.debug(indexOfTest.indexOf("H") + ""); // 없으므로 -1반환
		LOGGER.debug(indexOfTest.indexOf("d") + ""); //
		LOGGER.debug(indexOfTest.indexOf("i") + ""); // 0번째 인덱스 0반환
		LOGGER.debug(indexOfTest.indexOf("i", 3) + ""); // 3번째 인덱스 이후의 i가 있는 인덱스 반환 8
		LOGGER.debug(indexOfTest.indexOf(108) + ""); //

		String isEmptyTest = "";
		LOGGER.debug(isEmptyTest.isEmpty() + ""); // true

		String lengthTest = "looooong";
		LOGGER.debug(lengthTest.length() + " "); // 8글자 8반환

		String matchesTest1 = " cod name ";
		String matchesTest2 = "code";
		LOGGER.debug(matchesTest1.matches(matchesTest2) + "");
		LOGGER.debug(matchesTest1.matches("(.*)code(.*)") + " "); // 문자열에 code가 포함되어있지않아 false
		LOGGER.debug(matchesTest2.matches("(.*)code(.*)") + " "); // 문자열에 code가 포함되어있어 true
		// matches는 contains와 다르게 정규식을 인자로 받고 문자열의 패턴이 같으면 true 리턴
		// contains는 단순히 전달된 문자열이 포함되어있으면 true리턴

		final String str = "Hello World";
		final String str1 = "H.el.lo W.or/.d";
		LOGGER.debug(str.replace("o", "T")); // HellT WTrld
		LOGGER.debug(str1.replaceAll(".", "/")); // .은 정규식으로 문자열을 뜻해 모든 문자가 / 으로 바뀜
		LOGGER.debug(str1.replace(".", "/")); // H/el/lo W/or//d

		String num = "010-1234-5678";
		String[] numArray = num.split("-"); // -문자를 기준으로 문자열 나눠 배열에 저장
		for (int i = 0; i < numArray.length; i++) {
			LOGGER.debug(numArray[i]);
		}

		String substringTest = "12345679";
		LOGGER.debug(substringTest.substring(3)); // 3번째 인덱스부터 출력 45679
		LOGGER.debug(substringTest.substring(1, 4)); // 1번째 인덱스부터 4번째까지 출력 234

		String LowerUpper = "HeLlO Sw";
		LOGGER.debug(LowerUpper.toLowerCase()); // hello sw
		LOGGER.debug(LowerUpper.toUpperCase()); // HELLO SW

		String trimTest = "   Hello Hello  ";

		LOGGER.debug("[" + trimTest + "]"); // [ Hello Hello ]
		LOGGER.debug("[" + trimTest.trim() + "]"); // [Hello Hello] == 문자열 사이 공백은 사라지지않음

		String name = "Barun Software";
		LOGGER.debug(name.startsWith("Barun") + ""); // Barun으로 시작하므로 true
		LOGGER.debug(name.endsWith("Software") + ""); // Software로 끝나니 true

		// toString overide
		OverideEx o = new OverideEx();
		o.setName("jaeseok");
		LOGGER.debug(o.toString());

		String s = "Hello World";
		char[] charArr = s.toCharArray();

		for (int i = 0; i < charArr.length; i++) {
			LOGGER.debug(charArr[i] + "");
		}

	}
}
