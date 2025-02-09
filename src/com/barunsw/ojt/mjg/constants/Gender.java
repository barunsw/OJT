package com.barunsw.ojt.mjg.constants;

public enum Gender {
	MAN("MAN", "남자"), 
	WOMAN("WOMAN", "여자");

	private final String eng;
	private final String kor;

	// 디비 저장할 땐 eng 사용하고
	// 데이터 가져올 땐 kor로 가져오기
	 
	private Gender(String eng, String kor) {
		this.eng = eng;
		this.kor = kor;
	}

	public String getEng() {
		return eng;
	}

	public String getKor() {
		return kor;
	}

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
		case "남":
		case "MAN":
			return MAN;
		case "여자":
		case "여":
		case "WOMAN":
			return WOMAN;
		default:
			throw new Exception(String.format("적절하지 않은 value(%s)입니다.", value));
		}
	}
}
