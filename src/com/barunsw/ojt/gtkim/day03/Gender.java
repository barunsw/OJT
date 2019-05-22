package com.barunsw.ojt.gtkim.day03;

public enum Gender {

	MAN("남"),
	WOMAN("여");

	private String gender;

	private Gender(String v) {
		gender = v;
	}
	
	public static String toGender(String value) throws Exception {
		switch (value) {
		case "남자":
		case "남":
			return Gender.MAN.getValue();
		case "여자":
		case "여":
			return Gender.WOMAN.getValue();
		default:
			throw new Exception(String.format("적절하지 않은 value(%s)입니다.", value));
		}
	}
	
	public String getValue() {
		return gender;
	}
}
