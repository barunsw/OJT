package com.barunsw.ojt.jyb.day2;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringConstructorExample {

	private static final Logger logger = LoggerFactory.getLogger(StringSharingExample.class);

	public static void main(String[] args) throws UnsupportedEncodingException {
		// String()
		String emptyString = new String();
		logger.info("빈 문자열 : " + emptyString);

		// String(byte[] bytes)
		byte[] byteArray = { 65, 66, 67 }; //A,B,C
		String fromByteArray = new String(byteArray);
		logger.info("바이트 배열로 생성된 문자열 : " + fromByteArray);

		// String(byte[] bytes, Charset charset)
		String fromByteArrayWithCharset = new String(byteArray, Charset.forName("UTF-8"));
		logger.info("문자 집합으로 생성된 문자열 : " + fromByteArrayWithCharset);

		// String(byte[] bytes, int offset, int length)
		String subArrayString = new String(byteArray, 1, 2);
		logger.info("바이트 배열의 특정 부분으로 생성된 문자열 : " + subArrayString);
		
		// String(byte[] bytes, int offset, int length, String charsetName)
		String subArrayWithCharsetName = new String(byteArray, 0, 3, "UTF-8");
        System.out.println("문자 집합 이름으로 생성된 문자열 : " + subArrayWithCharsetName);
        
        // String(byte[] bytes, String charsetName)
        String fromByteArrayWithName = new String(byteArray, "UTF-8");
        System.out.println("문자 집합 이름으로 생성된 문자열 : " + fromByteArrayWithName);

        // String(char[] value)
        char[] charArray = {'H', 'e', 'l', 'l', 'o'};
        String fromCharArray = new String(charArray);
        System.out.println("문자 배열로 생성된 문자열 : " + fromCharArray);

        // String(char[] value, int offset, int count)
        String subCharArrayString = new String(charArray, 1, 3); // e, l, l
        System.out.println("문자 배열의 특정 부분으로 생성된 문자열 : " + subCharArrayString);

        // String(int[] codePoints, int offset, int count)
        int[] codePoints = {72, 101, 108, 108, 111}; // H, e, l, l, o
        String fromCodePoints = new String(codePoints, 0, codePoints.length);
        System.out.println("유니코드 코드 포인트 배열로 생성된 문자열 : " + fromCodePoints);

        // String(String original)
        String originalString = "Original String";
        String copyString = new String(originalString);
        System.out.println("복사된 문자열 : " + copyString);

        // String(StringBuffer buffer)
        StringBuffer stringBuffer = new StringBuffer("StringBuffer");
        String fromStringBuffer = new String(stringBuffer);
        System.out.println("StringBuffer로 생성된 문자열 : " + fromStringBuffer);

        // String(StringBuilder builder)
        StringBuilder stringBuilder = new StringBuilder("StringBuilder");
        String fromStringBuilder = new String(stringBuilder);
        System.out.println("StringBuilder로 생성된 문자열 : " + fromStringBuilder);

	}

}
