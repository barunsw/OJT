package com.barunsw.ojt.phs.day02;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StringTest {
	
	private static final Logger LOGGER = LogManager.getLogger(StringTest.class);
		
	public static void main(String[] args) {

		String str1 = "HeeSeong";
		String str2 = new String("HeeSeong");
		
//		=============================================
		
		if (str1.equals(str2)) {
			LOGGER.debug("문자열이 같음");
		}else {
			LOGGER.debug("문자열이 같지않음");
		}
		
		if (str1 == str2) {
			LOGGER.debug("주소값이 같음");
		}else {
			LOGGER.debug("주소값이 같지않음");
		}
		
//		=============================================
		
		LOGGER.debug("str1의 문자열 길이는 : " + str1.length());
		
		LOGGER.debug("str1의 객체가 비어있는지 ? : " + str1.isEmpty());
		
		LOGGER.debug("str1에 'S'문자의 인덱스번호는 ? : " + str1.indexOf("S"));
		LOGGER.debug("str1에 'Q'문자의 인덱스번호는 ? : " + str1.indexOf("Q")); //없으면 -1 반환
		
		LOGGER.debug("str1의 인덱스 1~4까지 문자열을 출력 : " + str1.substring(1,4)); //인덱스 1,2,3만 출력된다 
		
		LOGGER.debug("str1의 'H'를 'Q'로 변환하여 출력 : " + str1.replaceAll("H", "Q")); //없으면 원본 그대로 반환
		
		LOGGER.debug("str1의 문자열전부를 대문자로 출력 : " + str1.toUpperCase());
		LOGGER.debug("str1의 문자열전부를 소문자로 출력 : " + str1.toLowerCase());
		
		String str3 = "\n\nHee Seong    ";
		LOGGER.debug("str3의 좌우 공백을 제거하여 출력 : " + str3.trim());
		
		LOGGER.debug("str1의 4번인덱스 문자를 char형으로 출력 : " + str1.charAt(4));
		
		LOGGER.debug("str1을 문자열로 출력 : " + String.valueOf(str1));
		
//		=============================================
		
		char ch[] = null;
		ch = str1.toCharArray(); //str1을 char배열로 만들어 반환시켜줌
		for(char c:ch) {
			LOGGER.debug(c);
		}
		
//		=============================================
		
		byte by[] = null;
		by = str1.getBytes(); //str1을 byte배열로 만들어 반환시켜줌
		for(byte b:by) {
			LOGGER.debug(b);
		}
		
//		=============================================
		
		String str4 = "Hee,Seong";
		String st[] = str4.split(","); // ',' 기준으로 나누어 String 배열 만듬
		for(String s:st) {
			LOGGER.debug(s);
		}
//		=============================================
		
		LOGGER.debug("str1이 'Hee'로 시작하는지? : " + str1.startsWith("Hee"));
		LOGGER.debug("str1이 'Park'로 시작하는지? : " + str1.startsWith("Park"));
		LOGGER.debug("str1이 'Seong'로 끝나는지? : " + str1.endsWith("Seong"));
		LOGGER.debug("str1이 'hehe'로 끝나는지? : " + str1.endsWith("hehe"));
	}

}