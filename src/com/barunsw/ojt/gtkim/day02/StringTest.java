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
			LOG.debug("���� ���� ��ü�Դϴ�.");
		}

		final String string1 = "������ ���� �� ���� ��";
		LOG.debug("string1 ù��° �ε��� : " + string1.charAt(0));

		LOG.debug("string1 ù��° �ڵ尪(10) : " + string1.codePointAt(0));
		LOG.debug("string1 ù��° �ڵ尪(16) : " + String.format("%X", string1.codePointAt(0)));
		final char ch = '\uB098';
		LOG.debug("ch�� ��������? : " + ch);

		LOG.debug("codePointBefore : " + string1.codePointBefore(1));

		LOG.debug("codePointCount : " + string1.codePointCount(0, string1.length()));
		final String string2 = "������ ���� �� ���� ��";
		LOG.debug("codePointCount : " + string2.codePointCount(0, string2.length()));

		if (string1.compareTo(string2) == 0) {
			LOG.debug("string1 �� string2�� �����ϴ�.");
		}

		final String string3 = "Hello World!";
		final String string4 = "hello world!";
		if (string3.compareToIgnoreCase(string4) == 0) {
			LOG.debug("string3 �� string4�� ��,�ҹ��� ���� ���� ������ �����ϴ�.");
		}

		final String string5 = string1.concat(string3);
		LOG.debug("concat : " + string5);

		if (string5.contains("���� ��")) {
			LOG.debug("���� �� �Դϴ�.");
		}

		char[] array = { 'S', 'U', 'P', 'E', 'R', ' ', 'C', 'O', 'F', 'F', 'E', 'E' };
		String string6 = String.copyValueOf(array);

		LOG.debug("copyValueOf : " + string6);
		if (string6.toLowerCase().endsWith("coffee")) {
			LOG.debug("Ŀ�Ƿ� �����ϴ�.");
		} else {
			LOG.debug("Ŀ�Ƿ� ������ �ʽ��ϴ�.");
		}

		if (string6.toLowerCase().startsWith("coffee")) {
			LOG.debug("Ŀ�Ƿ� �����մϴ�.");
		} else {
			LOG.debug("Ŀ�Ƿ� �������� �ʽ��ϴ�.");
		}

		if (string6.equals("house coffee")) {
			LOG.debug("���� Ŀ���Դϴ�.");
		} else {
			LOG.debug("���� �ٸ� Ŀ���Դϴ�.");
		}

		LOG.debug(String.format("������ Ŀ�Ǵ� %s �Դϴ�.", string6));
		final byte[] by = string6.getBytes();
		for (byte b : by) {
			LOG.debug(String.format("%c ", b));
		}

		int codeValue = "".hashCode();
		LOG.debug("�� ���ڿ��� �ؽ��ڵ� �� : " + codeValue);

		LOG.debug("indexOf : string6�� Ŀ���ε��� " + string6.indexOf("COFFEE"));

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
			LOG.debug("�� ���ڿ� �Դϴ�.");
		} else {
			LOG.debug(string7 + "�� �� ���ڿ��� �ƴմϴ�");
		}

		LOG.debug(String.format("%s�� ������ m �ε����� %d �Դϴ�.", string7, string7.lastIndexOf("m")));
		LOG.debug(String.format("%s�� ���̴� %d �Դϴ�.", string7, string7.length()));
		String reg = "([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})";
		String ip = "59.7.123.342";
		if (ip.matches(reg)) {
			LOG.debug("���� ���Խ� ǥ���Դϴ�.");
		}

		String string10 = "LG all day new LG Gram";
		String string11 = string10.replace("LG", "SAMSUNG");
		LOG.debug(String.format("replace -> �ٲٱ� �� : %s, �ٲ� �� : %s", string10, string11));
		string11 = string10.replaceAll("[A-Z]", "SAMSUNG");
		LOG.debug(String.format("replaceAll -> �ٲٱ� �� : %s, �ٲ� �� : %s", string10, string11));

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

		Person saram = new Person(26, "�����");
		LOG.debug(saram);

		final String string12 = "\t\t\n\n\n\n\tHow are\tyou today?\n\n\n\n   ";
		LOG.debug(string12.trim());
		LOG.debug(string12.trim().replace("\t", " "));

		final String string13 = null;
		LOG.debug("valueOf : " + String.valueOf(string13));
		//String.format(format, args)
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
