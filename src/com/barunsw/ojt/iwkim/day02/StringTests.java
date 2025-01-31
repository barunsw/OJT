package com.barunsw.ojt.iwkim.day02;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
class A{}
class B extends A{}
public class StringTests {
	private static Logger LOGGER = LogManager.getLogger(StringTests.class);
	
	public static void main(String[] args) {
		// 리터럴 값과 new 연산자를 통한 객체 값 비교
		String s1 = "inwoo";
		String s2 = "inwoo";
		String s3 = new String("inwoo");
		LOGGER.info(s1==s2); // true
		LOGGER.info(s1==s3); // false
		
		// instanceof 테스트
		A a1 = new A();
		B b1 = new B();
		LOGGER.info(a1 instanceof A); // true
		LOGGER.info(b1 instanceof A); // A클래스를 상속받고 있기 때문에 true
		LOGGER.info(a1 instanceof B); // false
		LOGGER.info(b1 instanceof B); // true
		
		// charAt() 및 codePointAt() 테스트
		String str1 = "inwoo";
		LOGGER.info(str1.charAt(2)); // w
		LOGGER.info(str1.codePointAt(2)); // w의 유니코드값을 반환
		
		// concat() 테스트
		String name1 = "Kim";
		String name2 = "in";
		String name3 = "woo";
		LOGGER.info(name1.concat(name2));
		LOGGER.info(name2.concat(name3));
		
		// compareTo() 테스트
		String alphabet1 = "aaa";
		String alphabet2 = "aab";
		String alphabet3 = new String("aaa");
		LOGGER.info(alphabet1.compareTo(alphabet2)); // 들어가는 인자 값이 더 크므로 -1을 return
		LOGGER.info(alphabet1.compareTo(alphabet3)); // 들어가는 인자 값이 더 같으므로 0을 return
		LOGGER.info(alphabet2.compareTo(alphabet3)); // 들어가는 인자 값이 더 작으므로 1을 return
		
		//contains() 테스트
		String wiseSaying1 = "소중한 깨달음도 기록해 두지않으면 소멸하고 만다.";
		LOGGER.info(wiseSaying1.contains("깨달음")); // true를 반환
		LOGGER.info(wiseSaying1.contains("김인우")); // false를 반환
		
		//format() 테스트
		LOGGER.info(String.format("16진수 %x", 20));
		LOGGER.info(String.format("8진수 %o", 20));
		LOGGER.info(String.format("실수 %f", 20.0));
		LOGGER.info(String.format("문자열 %s", "Hello"));
		
		//getBytes() 테스트
		String test1 = "Kiminwoo";
		byte[] arr1 = null;
		arr1 = test1.getBytes(); 
		LOGGER.info("바이트배열 첫번째 값 : " + arr1[1]);
		
		//hashCode() 테스트
		LOGGER.info("hashCode() : " + test1.hashCode());
		
		//indexOf() 테스트
		LOGGER.info(test1.indexOf("in")); // in이 해당 문자열에 포함되어 있으므로 in이 시작하는 위치인 3을 반환
		LOGGER.info(test1.indexOf("io")); // io가 해당 문제열에 포함되어 있지 않으므로 -1을 반환
		
		//replace() 및 replaceAll 테스트
		LOGGER.info(test1.replace("o", "O")); // 해당 문자열의 o를 O로 치환
		LOGGER.info(test1.replaceAll("i.*", "O")); // i 뒤에 있는 모든 문자들을 O로 치환
		LOGGER.info(test1.replaceAll(".*i", "O")); // i 앞에 있는 모든 문자들을 O로 치환
		
		// split() 테스트
		String[] phoneArr = null;
		String phoneNumber = "010-2076-3525";
		StringBuilder strb1 = new StringBuilder("");
		phoneArr = phoneNumber.split("-");
		for(String a : phoneArr) 
		{
			strb1.append(a);
		}
		LOGGER.info("010-2076-3525에서 구분자 -를 없애면 : " + strb1 + "라고 나온다.");
		
		// subString 테스트
		LOGGER.info(phoneNumber.substring(3));
		LOGGER.info(phoneNumber.substring(3, 8));		
		LOGGER.info(phoneNumber.subSequence(1, 5));	 // subString과 차이가 없어보이지만, Char시퀀스 형태로 반환되는데 자동 형변환이 일어난다.
		
		// startsWith() 및 endWith() 테스트
		LOGGER.info(phoneNumber.startsWith("0"));
		LOGGER.info(phoneNumber.startsWith("1"));
		LOGGER.info(phoneNumber.endsWith("5"));
		LOGGER.info(phoneNumber.endsWith("3"));
		
		// toLowerCase() 및 toUpperCase() 테스트
		LOGGER.info(test1.toLowerCase());
		LOGGER.info(test1.toUpperCase());
		
		//  equals()와 .contentEquals메서드 비교		
		// .equals()는 매개변수의 객체가 String의 인스턴스인지 확인함. 즉 String객체와 StringBuffer객체의 문자열을 비교할 수 없음.
		// .contentEquals()는 매개변수의 객체가 String의 인스턴스인지 확인안하고 내용만 비교함. 
		String a = "inwoo";
		String b = "inwoo";
		StringBuffer c = new StringBuffer("abc");
		
		LOGGER.info(a.equals(b)); // true
		LOGGER.debug(a.equals(b)); // true
		LOGGER.info(a.equals(c)); // false
		
		LOGGER.info(a.contentEquals(b)); // true
		LOGGER.info(a.contentEquals(c)); // true

		
		
		//String vs StringBuilder vs String Buffer 각각 속도 비교 테스트
		int count = 100*1000;
		
		LOGGER.info("String의 count횟수만큼의 +연산 속도 : " + StringSpeedTest(count));
		LOGGER.info("StringBuffer의 count횟수만큼의 +연산 속도 : " + StringBufferSpeedTest(count));
		LOGGER.info("StringBuilder의 count횟수만큼의 +연산 속도 : " + StringBuilderSpeedTest(count));
		
		
	}

	private static double StringSpeedTest(int count)
	{		
		String strTest = "a";
		long start = System.currentTimeMillis();
		for(int i=0; i<count; i++) 
		{
			strTest +="a";
		}
		long end = System.currentTimeMillis();
		return end-start*1.0;
	}
	
	private static double StringBufferSpeedTest(int count) 
	{
		StringBuffer strTest = new StringBuffer("a");
		long start = System.currentTimeMillis();
		for(int i=0; i<count; i++) 
		{
			strTest.append("a");
		}
		long end = System.currentTimeMillis();
		return end-start*1.0;
	}
	
	private static double StringBuilderSpeedTest(int count) 
	{
		System.out.println("hi");
		StringBuilder strTest = new StringBuilder("a");
		long start = System.currentTimeMillis();
		for(int i=0; i<count; i++) 
		{
			strTest.append("a");
		}
		long end = System.currentTimeMillis();
		return end-start*1.0;
		
	}
	
}



































