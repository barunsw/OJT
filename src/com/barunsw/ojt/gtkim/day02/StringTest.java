package com.barunsw.ojt.gtkim.day02;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.day02.Person;

public class StringTest {
	private static final Logger LOG = LogManager.getLogger(StringTest.class);

	public static void main(String[] args) {

		// == 연산자를 통한 비교
		final String equalTest1 = "Hello World";
		final String equalTest2 = "Hello World";
		if (equalTest1 == equalTest2) {
			LOG.debug("서로 같은 객체입니다.");
		}

		// charAt() string1의 첫번재 인덱스 값을 반환한다.
		final String string1 = "나들이 가기 딱 좋은 날";
		LOG.debug("string1 첫번째 인덱스 : " + string1.charAt(0));

		// codePointAt() string1의 첫번째 인덱스 값을 10진수 유니코드값과 16진수 유니코드 값으로 반환한다.
		LOG.debug("string1 첫번째 코드값(10) : " + string1.codePointAt(0));
		LOG.debug("string1 첫번째 코드값(16) : " + String.format("%X", string1.codePointAt(0)));
		final char ch = '\uB098';
		LOG.debug("ch는 무슨글자? : " + ch);

		// codePointBefore() 인덱스의 이전 인덱스 값을 반환 , codePointBefore(0)은 인덱스 에러를 반환
		LOG.debug("codePointBefore : " + string1.codePointBefore(1));

		// codePointCount() 인덱스 위치 차이가 얼마인지 정수로 반환
		LOG.debug("codePointCount : " + string1.codePointCount(0, string1.length()));
		final String string2 = "나들이 가기 딱 싫은 날";
		LOG.debug("codePointCount : " + string2.codePointCount(0, 5));

		// compareTo() string1과 string2를 비교하여 같으면 0을 반환 사전순서로 string1이 앞에 있으면 음수를 반환 반대의
		// 경우 양수를 반환
		if (string1.compareTo(string2) == 0) {
			LOG.debug("string1 과 string2는 같습니다.");
		}

		// compareToIgnoreCase() 문자열의 대소문자를 구별하지 않고 compareTo() 수행
		final String string3 = "Hello World!";
		final String string4 = "hello world!";
		if (string3.compareToIgnoreCase(string4) == 0) {
			LOG.debug("string3 과 string4는 대,소문자 구별 하지 않으면 같습니다.");
		}

		// concat() 새로운 string 객체를 만들어 문자를 연결하여 반환
		final String string5 = string1.concat(string3);
		LOG.debug("concat : " + string5);

		// contains() 문자열에 포함되어 있으면 true를 반환
		if (string5.contains("좋은 날")) {
			LOG.debug("좋은 날 입니다.");
		}

		// copyValueOf() 문자 배열을 순서대로 스트링으로 변환
		char[] array = { 'S', 'U', 'P', 'E', 'R', ' ', 'C', 'O', 'F', 'F', 'E', 'E' };
		String string6 = String.copyValueOf(array);
		LOG.debug("copyValueOf : " + string6);

		// endsWith() 문자열의 끝에 매개변수가 포함됬는지 확인, 매개변수의 문자열의 길이를 가지고 startsWith()를 호출
		if (string6.toLowerCase().endsWith("coffee")) {
			LOG.debug("커피로 끝납니다.");
		} else {
			LOG.debug("커피로 끝나지 않습니다.");
		}

		// startsWith() 해당문자열로 시작하는지 확인
		if (string6.toLowerCase().startsWith("coffee")) {
			LOG.debug("커피로 시작합니다.");
		} else {
			LOG.debug("커피로 시작하지 않습니다.");
		}

		// equals() 문자열의 내용이 같은지 비교하여 같으면 true반환
		if (string6.equals("house coffee")) {
			LOG.debug("같은 커피입니다.");
		} else {
			LOG.debug("서로 다른 커피입니다.");
		}

		// format() C의 printf와 같이 포맷 형식에 맞추어 변환
		LOG.debug(String.format("오늘의 커피는 %s 입니다.", string6));

		// getBytes() 문자열을 바이트로 변환
		final byte[] by = string6.getBytes();
		for (byte b : by) {
			LOG.debug(String.format("%c ", b));
		}

		// hashCode() 문자열의 해싴코드값을 계산, 빈 문자열이면 0
		int codeValue = "".hashCode();
		LOG.debug("빈 문자열의 해시코드 값 : " + codeValue);

		// indexOf() 문자열에 해당되는 시작 인덱스 반환
		LOG.debug("indexOf : string6의 커피인덱스 " + string6.indexOf("COFFEE"));

		// intern() 스트링 풀에 있으면 그 값을 반환하고 없으면 추가후 반환, new로 객체를 생성했어도 같아짐
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

		// isEmpty() string의 경우 문자열의 길이가 0이면 true 반환
		if (string7.isEmpty()) {
			LOG.debug("빈 문자열 입니다.");
		} else {
			LOG.debug(string7 + "는 빈 문자열이 아닙니다");
		}

		// lastIndexOf() 해당 문자열을 가진 마지막 인덱스 값을 반환
		LOG.debug(String.format("%s의 마지막 m 인덱스는 %d 입니다.", string7, string7.lastIndexOf("m")));

		// length() 문자열의 길이를 반환
		LOG.debug(String.format("%s의 길이는 %d 입니다.", string7, string7.length()));

		// matches() 문자열과 같은 정규표현식이면 true를 반환
		String reg = "([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})";
		String ip = "59.7.123.342";
		if (ip.matches(reg)) {
			LOG.debug("같은 정규식 표현입니다.");
		}

		// replace() 해당 문자열과 같으면 전부 치환
		String string10 = "LG all day new LG Gram";
		String string11 = string10.replace("LG", "SAMSUNG");
		LOG.debug(String.format("replace -> 바꾸기 전 : %s, 바꾼 후 : %s", string10, string11));

		// replaceAll() 정규표현식에 맞으면 전부 치환
		string11 = string10.replaceAll("[A-Z]", "SAMSUNG");
		LOG.debug(String.format("replaceAll -> 바꾸기 전 : %s, 바꾼 후 : %s", string10, string11));

		// split() 문자열을 분리
		String[] splitList = string10.split(" ");
		int length = splitList.length;
		for (int i = 0; i < length; i++) {
			LOG.debug(String.format("list[%d] = %s", i, splitList[i]));
		}

		// subSequence(), subString() 문자열을 인덱스 범위만큼 분리
		LOG.debug("subSequence : " + string10.subSequence(1, 5));
		LOG.debug("subString : " + string10.substring(1, 5));

		// toCharArray() 문자열을 배열로 변환
		array = string10.toCharArray();
		length = array.length;
		for (int i = 0; i < length; i++) {
			LOG.debug(String.format("array[%2d] : %c ", i, array[i]));
		}

		// toString() commons-lang3의 toStringBuilder
		Person saram = new Person(26, "김균태");
		LOG.debug(saram);

		// trim() 문자열의 앞뒤 공백을 제거
		final String string12 = "\t\t\n\n\n\n\tHow are\tyou today?\n\n\n\n   ";
		LOG.debug(string12.trim());
		LOG.debug(string12.trim().replace("\t", " "));

		// valueOf() 문자열로 반환한다. null일경우 nullPointerException을 발생하지 않고 null문자열을 만들어 반환
		final String string13 = null;
		LOG.debug("valueOf : " + String.valueOf(string13));

		// String + 연산자 사용시 속도 비교
		long start, end;
		final int max = 300000;
		String test1 = "1";
		StringBuilder test2 = new StringBuilder("1");
		StringBuffer test3 = new StringBuffer("1");

		// String class
		start = System.currentTimeMillis();
		for (int i = 0; i < max; i++) {
			test1 += "1";
		}
		end = System.currentTimeMillis();
		LOG.debug("time String : " + ((end - start) / 1000.0));

		// StringBuilder class
		start = System.currentTimeMillis();
		for (int i = 0; i < max; i++) {
			test2.append("1");
		}
		end = System.currentTimeMillis();
		LOG.debug("time StringBuilder : " + ((end - start) / 1000.0));

		// StringBuffer class
		start = System.currentTimeMillis();
		for (int i = 0; i < max; i++) {
			test3.append("1");
		}
		end = System.currentTimeMillis();
		LOG.debug("time StringBuffer : " + ((end - start) / 1000.0));

	}

	// 어노테이션을 쓰면 컴파일시 오류를 잡아줌
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
}