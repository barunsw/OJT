package com.barunsw.ojt.sjcha.day02;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StringTest {
	// Logger 사용을 위한 Logger 선언.
	private static final Logger Logger = LogManager.getLogger(StringTest.class);
	
	public static void main(String[] args) {
		
		Logger.debug("main start");
		
		// Person 객체의 toString 메소드를 활용하여 personOne의 정보 출력.
		final Person personOne = new Person(25, "차수진");
		Logger.debug("personOne" + personOne);
		
		// String 변수 선언.
		final String string1 = "Hello World";
		
		// charAt 메소드의 결과를 담을 변수 선언.
		char stringToChar = ' ';
		
		// charAt 메소드를 활용하여 String 형을 한 글자의 char 형 추출.
		Logger.debug("charAt method");
		for (int i = 0; i < string1.length(); i++) {
			stringToChar = string1.charAt(i);
			Logger.debug(stringToChar);
		}
		
		// codepoint 메소드 활용할 변수 선언.
		String string2 = "abcdefg";
		
		Logger.debug("codePointAt method");
		for (int i = 0; i < string2.length(); i++) {
			Logger.debug(string2.codePointAt(i));
		}

		Logger.debug("codePointBefore method");
		for (int i = 1; i < string2.length(); i++) {
			Logger.debug(string2.codePointBefore(i));
		}
		
		Logger.debug("codePointCount method");
		Logger.debug(string2.codePointCount(0, string2.length()));
			
		// compareTo 메소드 사용.
		String string3 = "abc";
		String string4 = "add";
		Logger.debug("compareTo method");
		Logger.debug(string3.compareTo(string4));
		
		String string5 = "ABC";
		Logger.debug("compareToIgnoreCase method");
		Logger.debug(string3.compareToIgnoreCase(string5));
		
		Logger.debug("concat method");
		Logger.debug(string3.concat(string5));
		
		Logger.debug("contain method");
		Logger.debug(string3.contains("ab"));
		Logger.debug(string3.contains("AB"));
		
		String string6 = "abc";
		StringBuffer stringBuffer = new StringBuffer("abc");
		Logger.debug("contentEquals method");
		Logger.debug(string3.contentEquals(string6));
		Logger.debug(string3.contentEquals(stringBuffer));
		
		Logger.debug("equals method");
		Logger.debug(string3.equals(string6));
		Logger.debug(string3.equals(stringBuffer));
		
		char[] charToString = {'h', 'e', 'l', 'l', 'o'};
		String string7 = "";
		Logger.debug("copyValueOf mecthod");
		Logger.debug(string7.copyValueOf(charToString, 0, 3));
		
		Logger.debug("endsWith method");
		Logger.debug(string3.endsWith("c"));
		
		Logger.debug("indexOf method");
		Logger.debug(string2.indexOf("bcde"));
		Logger.debug(string2.indexOf("b", 3));
		 
		String string8 = "apple";
		String string9 = new String("apple");
		String string10 = string9.intern();
		Logger.debug("intern method");
		Logger.debug(string8 == string9);
		Logger.debug(string8 == string10);
		
		Logger.debug("matches method");
		String driverLicense = "운전면허증 번호:92-016593-02-1";
		Pattern pattern = Pattern.compile(".*(\\d{2}-(\\d{6})-\\d{2}-\\d{1})");
		Matcher m = pattern.matcher(driverLicense);
		if (m.matches()) {
			Logger.debug("group1:" + m.group(1));
			Logger.debug("group2:" + m.group(2));
		}
		
		Logger.debug("replace method");
		String string11 = "안녕하세요. 반갑습니다. 또 만나요.";
		Logger.debug(string11.replace(".", "~"));
		Logger.debug(string11.replaceAll(".", "~"));
		Logger.debug(string11.replaceFirst(".", "~"));
				
		Logger.debug("split method");
		String phoneNumber = "010-1234-1234";
		String[] string12 = phoneNumber.split("-");
		Logger.debug(string12[0]);
		Logger.debug(string12[1]);
		Logger.debug(string12[2]);
		
		Logger.debug("startsWith method");
		Logger.debug(string11.startsWith("안녕하세요."));
		
		Logger.debug("subString method");
		String string13 = "banana";
		Logger.debug(string13.substring(1,2));
		
		Logger.debug("toCharArray method");
		String string14 = "trip";
		char[] charArray = string14.toCharArray();
		Logger.debug(charArray[0]);
		Logger.debug(charArray[1]);
		Logger.debug(charArray[2]);
		Logger.debug(charArray[3]);
		
		Logger.debug("trim method");
		String string15 = "   안녕 하세 요   ";
		Logger.debug(string15.trim());
		
		Logger.debug("valueOf method");
		Integer value = Integer.valueOf(7);
		Logger.debug(value);
		
		
		
	}
	
	
	
	
}
