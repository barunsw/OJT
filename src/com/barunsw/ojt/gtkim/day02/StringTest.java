package com.barunsw.ojt.gtkim.day02;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.day02.Person;

public class StringTest {
	private static final Logger LOG = LogManager.getLogger(StringTest.class);

	public static void main(String[] args) {

		final String equalTest1 = "Hello World";
		final String equalTest2 = "Hello World";

		if (equalTest1 == equalTest2) {
			LOG.debug("서로 같은 객체입니다.");
		}

		final String string1 = "나들이 가기 딱 좋은 날";
		LOG.debug("string1 첫번째 인덱스 : " + string1.charAt(0));

		LOG.debug("string1 첫번째 코드값(10) : " + string1.codePointAt(0));
		LOG.debug("string1 첫번째 코드값(16) : " + String.format("%X", string1.codePointAt(0)));
		final char ch = '\uB098';
		LOG.debug("ch는 무슨글자? : " + ch);

		LOG.debug("codePointBefore : " + string1.codePointBefore(1));

		LOG.debug("codePointCount : " + string1.codePointCount(0, string1.length()));
		final String string2 = "나들이 가기 딱 싫은 날";
		LOG.debug("codePointCount : " + string2.codePointCount(0, string2.length()));

		if (string1.compareTo(string2) == 0) {
			LOG.debug("string1 과 string2는 같습니다.");
		}

		final String string3 = "Hello World!";
		final String string4 = "hello world!";
		if (string3.compareToIgnoreCase(string4) == 0) {
			LOG.debug("string3 과 string4는 대,소문자 구별 하지 않으면 같습니다.");
		}

		final String string5 = string1.concat(string3);
		LOG.debug("concat : " + string5);

		if (string5.contains("좋은 날")) {
			LOG.debug("좋은 날 입니다.");
		}

		char[] array = { 'S', 'U', 'P', 'E', 'R', ' ', 'C', 'O', 'F', 'F', 'E', 'E' };
		String string6 = String.copyValueOf(array);

		LOG.debug("copyValueOf : " + string6);
		if (string6.toLowerCase().endsWith("coffee")) {
			LOG.debug("커피로 끝납니다.");
		} else {
			LOG.debug("커피로 끝나지 않습니다.");
		}

		if (string6.toLowerCase().startsWith("coffee")) {
			LOG.debug("커피로 시작합니다.");
		} else {
			LOG.debug("커피로 시작하지 않습니다.");
		}

		if (string6.equals("house coffee")) {
			LOG.debug("같은 커피입니다.");
		} else {
			LOG.debug("서로 다른 커피입니다.");
		}

		LOG.debug(String.format("오늘의 커피는 %s 입니다.", string6));
		final byte[] by = string6.getBytes();
		for (byte b : by) {
			LOG.debug(String.format("%c ", b));
		}

		int codeValue = "".hashCode();
		LOG.debug("빈 문자열의 해시코드 값 : " + codeValue);

		LOG.debug("indexOf : string6의 커피인덱스 " + string6.indexOf("COFFEE"));

		LOG.debug("-- intern test --");
		final String string7 = "Summer";
		final String string8 = new String("Summer");
		final String string9 = new String("Summer").intern();

		if (string7 == string8) {
			LOG.debug("string7 == string8");
		}

		if (string7 == string9) {
			LOG.debug("string7 == string9");
		}

		if (string8 == string9) {
			LOG.debug("string8 == string9");
		}

		if (string7.isEmpty()) {
			LOG.debug("빈 문자열 입니다.");
		} else {
			LOG.debug(string7 + "는 빈 문자열이 아닙니다");
		}

		LOG.debug(String.format("%s의 마지막 m 인덱스는 %d 입니다.", string7, string7.lastIndexOf("m")));
		LOG.debug(String.format("%s의 길이는 %d 입니다.", string7, string7.length()));
		String reg = "([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})";
		String ip = "59.7.123.342";
		if (ip.matches(reg)) {
			LOG.debug("같은 정규식 표현입니다.");
		}

		String string10 = "LG all day new LG Gram";
		String string11 = string10.replace("LG", "SAMSUNG");
		LOG.debug(String.format("replace -> 바꾸기 전 : %s, 바꾼 후 : %s", string10, string11));
		string11 = string10.replaceAll("[A-Z]", "SAMSUNG");
		LOG.debug(String.format("replaceAll -> 바꾸기 전 : %s, 바꾼 후 : %s", string10, string11));

		String[] splitList = string10.split(" ");
		int length = splitList.length;
		for (int i = 0; i < length; i++) {
			LOG.debug(String.format("list[%d] = %s", i, splitList[i]));
		}

		LOG.debug("subSequence : " + string10.subSequence(1, 5));
		LOG.debug("subString : " + string10.substring(1, 5));

		array = string10.toCharArray();
		length = array.length;
		for (int i = 0; i < length; i++) {
			LOG.debug(String.format("array[%2d] : %c ", i, array[i]));
		}

		Person saram = new Person(26, "김균태");
		LOG.debug(saram);

		final String string12 = "\t\t\n\n\n\n\tHow are\tyou today?\n\n\n\n   ";
		LOG.debug(string12.trim());
		LOG.debug(string12.trim().replace("\t", " "));

		final String string13 = null;
		LOG.debug("valueOf : " + String.valueOf(string13));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
