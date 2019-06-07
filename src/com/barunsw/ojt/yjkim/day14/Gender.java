package com.barunsw.ojt.yjkim.day14;

public enum Gender {
	MAN
	, WOMAN;
	
	@Override
	public String toString() {
		switch (this) {
		case MAN:
			return "남";
		case WOMAN:
			return "여";
		default:
			return "";
		}
	}
	
	public static Gender toGender(String value) throws Exception {
		switch (value) {
		case "남자":
			return MAN;
		case "남":
			return MAN;
		case "MAN":
			return MAN;
		case "여자":
			return WOMAN;
		case "여":
			return WOMAN;
		case "WOMAN":
			return WOMAN;
		default:
			throw new Exception(String.format("적절하지 않은 value(%s)입니다.", value));
		}
	}
}
