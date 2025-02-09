package day9;

public enum Gender {
	MAN, WOMAN;

	@Override
	public String toString() { // 열거형의 각 인스턴스를 문자열로 변경
		switch (this) { // this : 현재 객체를 참조하는 키워드(열거형의 인스턴스)
		case MAN:
			return "남";
		case WOMAN:
			return "여";
		default:
			return "";
		}
	}

	public static Gender toGender(String value) throws Exception {
		if(value == null) {
			throw new Exception("null값은 허용되지 않습니다.");
		}
			
		switch (value) {
		case "남자":
		case "남":
		case "MAN":
		case "man":
			return MAN;
		case "여자":
		case "여":
		case "WOMAN":
		case "woman":
			return WOMAN;
		default:
			throw new Exception(String.format("적절하지 않은 value(%s)입니다.", value));
		}
	}

}
