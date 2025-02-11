package com.barunsw.ojt.jyb.day3;

public class GenericExample {

	public static <T extends Comparable<T>> T findMax(T[] array) {
		// T : 메소드 호출 시 실제 타입으로 대체
		// <T extends Comparable<T>> : T는 Comparable 인터페이스를 구현한 타입이어야 함 -> T 타입의 객체끼리
		// 비교할 수 있음을 보장
		// comparable을 상속받았기 때문에 아래에서 compareTo 사용 가능

		T max = array[0]; // 첫 번째 요소로 초기화
		for (T element : array) {
			if (element.compareTo(max) > 0) {
				max = element; // 더 큰 값으로 업데이트
			}
		}
		return max;
	}

	public static void main(String[] args) {
		// Integer 배열
		Integer[] intArray = { 1, 3, 7, 2, 5 };
		Integer maxInt = findMax(intArray); // 매개변수로 배열
		System.out.println("최대 Integer 값 : " + maxInt);

		// String 배열
		String[] strArray = { "apple", "orange", "banana", "kiwi" };
		String maxStr = findMax(strArray);
		System.out.println("최대 String 값 : " + maxStr);
	}

}
