package com.barunsw.ojt.yjkim.day02;

import java.io.UnsupportedEncodingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.day02.Person;

public class StringTest {
	private static final Logger LOGGER = LogManager.getLogger(StringTest.class);
	
	public static void main(String[] args) {
		LOGGER.debug("main");
		//Person 객체 onePerson 생성
		//이름 홍길동 나이 25세
		final Person onePerson = new Person(25, "홍길동");
		LOGGER.debug("onePerson:" + onePerson);
		
		final String aaa = "Start with End";
		//aaa가 Hello로 시작하는지 검사한다.
		if (aaa.startsWith("Start")) {
			LOGGER.debug("Start로 시작");
		}
		//aaa가 World로 끝나는지 검사한다.
		if (aaa.endsWith("End")) {
			LOGGER.debug("End로 끝");
		}
		
		//String bbb = "Start with End"를 선언하면 Heap 영역에 이미 생성된
		// Hello World에 bbb가  가리키게 되어 aaa와 bbb는 같은 객체가 된다.
		final String bbb = "Start with End";
		
		//String bbb = new String("Start with End")를 선언하면 Heap 영역에
		//다른 Start with End 객체가 생성되며 bbb가 가리키게 된다.
		
		//String bbb = new String("Start with End");
		

		//equals는 aaa가 bbb와 동일한 내용을 가지는지 검사한다.
		if (aaa.equals(bbb)) {
			LOGGER.debug("aaa와 bbb는 동일한 내용을 가진다.");
		}
		// == 는 aaa와 bbb가 동일한 객체인지를 검사한다. 
		if (aaa == bbb) {
			LOGGER.debug("aaa와 bbb는 동일한 객체다.");
		}
		else {
			LOGGER.debug("aaa와 bbb는 동일한 객체가 아니다.");
		}
		
		//equlasIgnoreCase() 메서드는 대소문자를 구분하지 않고 비교한다.
		LOGGER.debug(aaa.equalsIgnoreCase("START WITH END"));
		
		//indexOf메서드는 해당 문자열에서 특정 문자나 문자열이 처음으로 등장하는
		//위치의 인덱스를 반환한다. 
		final int niceIndex = aaa.indexOf("Nice");
		LOGGER.debug("niceIndex:" + niceIndex);
		
		final int worldIndex = aaa.indexOf("Start");
		LOGGER.debug("worldIndex:" + worldIndex);
		
		//lastIndexOf() 메서드는 해당 문자열에서 특정 문자가 마지막으로 등장하는 위치의 인덱스를 반환한다.
		String str2 = new String("Java is so hard. but Java is fun and Java is good");
		LOGGER.debug("원본 문자열 : " + str2);
		//str2에있는 마지막 "Java"의 인덱스를 반환한다.
		LOGGER.debug(str2.lastIndexOf("Java"));
		
		//length() 메서드는 문자열의 길이를 반환한다.
		final String ccc = "";
		if (ccc.length() > 0) {	
			LOGGER.debug("CCC는 비어있지 않은 스트링");
		}
		//isEmpty() 메서드는 문자열이 비어있는지 확인하여 부울 값을 리턴한다.
		if (ccc.isEmpty()) {
			LOGGER.debug("CCC는 빈 스트링");
		}
		
		//split() 메서드는  해당 문자열의 전달된 정규표현식에 따라 나눠서 반환한다.
		String ddd = "aaa,bbb,ccc";
		String[] splitList = ddd.split(",");
		int index = 0;
		for (String oneWord:splitList) {
			//String.format은 객체의 출력 형식을 지정 할 수 있다. 
			LOGGER.debug(String.format("[%d]%s", index++, oneWord));
		}
		
		//trim() 메서드는 문자열의 맨 앞과 뒤에 있는 공백을 제거한다. 
		String rawText = "\n\tHello World\n\n    ";
		String trimText = rawText.trim();
		LOGGER.debug(String.format("raw:[%s], trim:[%s]", rawText, trimText));
		
		//matches() 메서드는 정규표현식에 문자열이 일치하는지 확인한다.
		String phoneNum = "010-1111-2222";
		String regText = "(\\d+)-(\\d+)-(\\d+)";
		if (phoneNum.matches(regText)) {
			LOGGER.debug("정규표현식 일치");
		}
		else {
			LOGGER.debug("정규표현식 일치하지 않음");
		}
		
		//charAt() 메서드는 해당 문자열의 특정 인덱스에 해당하는 문자를 반환한다.
		//만약 해당 문자열의 길이보다 큰 인덱스나 음수를 전달하면
		//IndexOutOfBoundsException 오류가 발생한다.
		String str = new String("abcd");
		LOGGER.debug("원본 문자열 : " + str);
		for (int i = 0; i < str.length(); i++)
		{
			LOGGER.debug(str.charAt(i) + " ");
		}
		LOGGER.debug("\ncharAt() 메소드 호출 후 원본 문자열 : " +str);
		
		//compareTo() 메서드는 해당 문자열을 인수로 전달된 문자열과 사전 편찬 순으로 비교한다.
		//대소문자를 구분하여 비교하며, 만약 두 문자열이 같다면 0을 반환하고, 해당 문자열이 인수로 전달된
		//문자열보다 작으면 음수를, 크면 양수를 반환한다.
		str = new String("abcd");
		
		LOGGER.debug("원본 문자열 : " + str);
		//abcd는 bcef보다 작기 때문에 -1이 출력된다.
		LOGGER.debug(str.compareTo("bcef"));
		//abcd는 Abcd보다 크기 때문에 양수가 출력된다.
		LOGGER.debug(str.compareTo("ABCD"));
		//abcd는 abcd와 같기 때문에 0이 출력된다.
		LOGGER.debug(str.compareTo("abcd") + "\n");
		//str.compareToIgnoreCase()는 대소문자를 무시하고 비교하기 때문에 abcd와 Abcd는 같다.
		LOGGER.debug(str.compareToIgnoreCase("Abcd"));
		LOGGER.debug("compareTo() 메소드 호출 후 원본 문자열 : " + str);
		
		
		//concat() 메서드는 해당 문자열의 뒤에 인수로 전달된 문자열을 추가한 새로운 문자열을 반환한다.
		//만약 인수로 전달된 문자열의 길이가 0이면, 해당 문자열을 그대로 반환한다.
		str = new String("Java");
		LOGGER.debug("원본 문자열 : " + str);
		//Java에 수업이 추가되어 Java수업이 출력된다. 
		LOGGER.debug(str.concat("수업"));
		LOGGER.debug("concat() 메소드 호출 후 원본 문자열 : " + str);
		
		//toLowerCase() 메서드는 해당 문자열의 모든 문자를 소문자로 변환한다.
		//toUpperCase() 메서드는 해당 문자열의 모든 문자를 대문자로 변환한다.
		LOGGER.debug("원본 문자열 : " + str);
		//str을 소문자로 변환한다.
		LOGGER.debug(str.toLowerCase());
		//str을 대문자로 반환한다.
		LOGGER.debug(str.toUpperCase());
		LOGGER.debug("두 메소드 호출 후 원본 문자열 : " + str);
		
		//substring() 메서드는 해당 문자열의 전달된 시작 인덱스부터 마지막 인덱스까지를 새로운 문자열로 반환한다.
		str = new String("barunsw");
		//0번째부터 4번째까지의 문자열이 출력된다.
		LOGGER.debug(str.substring(0,5));
		
		//codePointAt() 메서드는 전달된 인덱스의 문자의 아스키 코드를 반환한다.
		LOGGER.debug("원본 문자열 : " + str);
		//b의 아스키코드가 출력된다.
		LOGGER.debug(str.codePointAt(0));
		
		//codePointBefore() 메서드는 전달된 인덱스 이전 문자의 아스키 코드를 반환한다.
		LOGGER.debug("원본 문자열 : " + str);
		//b의 아스키코드가 출력된다.
		LOGGER.debug(str.codePointBefore(1));
		
		//codePointCount() 메서드는 전달된 인덱스 범위만큼의 갯수가 반환된다.
		LOGGER.debug("원본 문자열 : " + str);
		LOGGER.debug(str.codePointCount(3, 5));
		
		//contains() 메서드는 전달된 문자열이 해당 문자열에 포함되어있는지 부울 값으로 반환된다.
		LOGGER.debug("원본 문자열 : " + str);
		LOGGER.debug(str.contains("barun"));
		
		//contentEquals() 메서드는 전달된 문자열과 해당 문자열이 동일한지 비교한다.
		LOGGER.debug("원본 문자열 : " + str);
		//false가 출력
		LOGGER.debug(str.contentEquals("barun"));
		//true가 출력.
		LOGGER.debug(str.contentEquals("barunsw"));

		//copyValueOf() 메서드는 char형 배열의 값을 복사하여 반환한다.
		char ch[] = new char[10];
		ch[0]='h'; ch[1]='i'; ch[2]='b'; ch[3]='a'; ch[4]='r'; ch[5]='u'; ch[6]='n';
		LOGGER.debug("원본 문자열 : " + str);
		//hibrun이 출력된다.
		LOGGER.debug(String.copyValueOf(ch));
		//0부터 2까지의 문자열을 복사한다.
		LOGGER.debug(String.copyValueOf(ch, 0, 2));
		
		String by = "aaaa";
		//getBytes() 메서드는 유니코드 문자열을 바이트코드로 인코딩 해준다.
		LOGGER.debug(by.getBytes());
		try {
			LOGGER.debug(by.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//intern() 메서드는 만들어진 객체를 상수화 시켜준다.
		//str2도 새로운 객체를 만들엇지만 intern을 호출하여 
		//기존의 Hello를 가리키게 한다.
		String str1 = "Hello"; 
		str2 = new String("Hello").intern();
		LOGGER.debug(str1.hashCode());
		LOGGER.debug(str2.hashCode());
		
		//offsetByCodePoints() 메서드는 index 위치부터 codePointOffset만큼 code point 단위로 이동한 
		//위치의 인덱스를 반환한다.
		String offset = new String("abcdef");
		//반환되는 인덱스는 5이기 때문에 f가 출력된다. 
		LOGGER.debug(offset.charAt(offset.offsetByCodePoints(2, 3)));
		
		//regionMatches() 메서드는 주어진 문자열의 index부터 길이 까지 비교한다.
		LOGGER.debug("원본 문자열 : " + str);
		LOGGER.debug(str.regionMatches(0, "barunsw", 0, 5));
		
		//replace() 메서드는 oldChar를 newChar로 바꿔준다.
		LOGGER.debug("원본 문자열 : " + str);
		LOGGER.debug(str.replace("barun", "nurab"));
		
		//replaceAll() 메서드는 해당 문자를 주어진 문자로 전부 바꾼다.
		String replaceAll = "rrrrAAAb";
		LOGGER.debug("원본 문자열 : " + str);
		LOGGER.debug(replaceAll.replaceAll("r", "barun"));
		
		//replaceFirst() 메서드는 첫번째 값만 주어진 문자로 바꾼다.
		LOGGER.debug("원본 문자열 : " + replaceAll);
		LOGGER.debug(replaceAll.replaceFirst("r", "b"));
		
		//subSequence() 메서드는 index 만큼의 문자열을 반환한다.
		LOGGER.debug("원본 문자열 : " + replaceAll);
		LOGGER.debug(replaceAll.subSequence(0, 5));
		
		//toCharArray() 메서드는 해당 문자열을 char형 배열로 반환한다.
		LOGGER.debug("원본 문자열 : " + replaceAll);
		char toChar[] = replaceAll.toCharArray();
		for(char toch:toChar) {
			LOGGER.debug(toch);
		}
		
		//toString() 메서드는 그 객체 자신을 반환한다
		LOGGER.debug("원본 문자열 : " + str);
		LOGGER.debug(str.toString());
		
		//valueOf() 메서드는 인자로 전달한 값을 해당 valueOf 메서드를 호출한 클래스의 객체로 반환한다.
		Integer i1 = 10;
		Integer i2 = new Integer(10);
		Integer i3 = Integer.valueOf(10);
		//i1과 i2는 같은 객체가 아니므로 false가 출력된다.
		LOGGER.debug(i1 == i2);
		//i3는 valueOf 메서드를 통해 10값을 가진 Integer 객체를 얻는다. 때문에 true가 출력된다.
		LOGGER.debug(i1 == i3);
		
		
		
	  //----------------------------------------------------------//
		/*
		   String, StringBuilder, StringBuffer
		 * 모두 문자열을 저장하고 관리하는 클래스들이다.
		 * String은 불변하고 StringBuffer,String Builder은 가변하다.
		 * 
		 * StringBuilder와 StringBuffer의 차이점은
		 * StringBuffer는 멀티쓰레드 환경에서 synchronized키워드가 가능하므로 동기화가 가능하다.
		 * StringBuilder는 동기화를 지원하지 않기 때문에 멀티쓰레드 환경에서는 적합하지 않다.
		 * 하지만 싱글 쓰레드 환경에서 StringBuufer에 비해 연산처리가 빠르다. 
		 * 
		 * Overriding, Overloading
		 * Overriding은 상위 클래스를 상속받아 상위 클래스의 메소드를 재정의 하여 사용하는 것이다.
		 * ex)Object는 최상위 클래스이며 Object의 메소드중 toString(),equals() 등은
		 * 흔히 재정의하여 사용되어 진다.
		 * Overloading은 메소드의 명은 같지만 매개변수의 유형과 개수를 달리하여 정의하는 방법이다.
		 *
		 * ArrayList, LinkedList
		 * ArrayList는 배열의 확장판으로 가장 많이 사용되는 컬렉션 클래스 중 하나이다.
		 * 배열을 이용하기 때문에 인덱스를 이용해 배열 요소에 빠르게 접근할 수 있다.
		 * 하지만 배열은 크기를 변경할 수 없는 인스턴스이므로, 크기를 늘리기 위해서는 새로운 배열을 생성하고
		 * 기존의 요소들을 옮겨야 하는 복잡한 과정을 거친다. 때문에 요소의 추가 및 삭제 작업에 걸리는 시간이 매우 길어진다.
		 * 
		 * LinkedList는 ArrayList가 배열을 이용하여 요소를 저장 삭제함으로써 발생하는 단점을 극복하기 위해 고안되었다.
		 * LinkedList는 저장된 요소가 비순차적으로 분포되며, 이러한 요소들 사이를 링크로 연결하여 구성한다.
		 * 삽입 삭제시 링크를 끊고 새로 연결하면 되므로, ArrayList에 비해 속도가 빠르다. 
		 * 
		 * instanceof
		 * instanceof 연산자는 참조변수가 참조하고 있는 인스턴스의 실제 타입을 알아보기 위해 instanceof 연산자를 사용한다.
		 * 주로 조건문에 사용되며, instanceof의 왼쪽에는 참조변수를 오른쪽에는 타입(클래스명)이 피연산자로 위치한다.
		 * 그리고 연산의 결과로 boolean값인 true, false 중의 하나를 반환한다.
		 * instanceof를 이용한 연산결과로 true를 얻었다는 것은 참조변수가 검사한 타입으로 형변환이 가능하다는 것이다.
		 * */
		//instanceOf 예제
		FireEngine f = new FireEngine();
		Ambulance a = new Ambulance();
		StringTest test = new StringTest();
		test.doWork(f);
		test.doWork(a);
	
	}
	public void doWork(Car c) {
		if(c instanceof FireEngine)
		{
			FireEngine f = (FireEngine)c;
			f.water();
		}
		else if(c instanceof Ambulance)
		{
			Ambulance a = (Ambulance)c;
			a.siren();
		}
	}
}
class Car
{
	
}
class FireEngine extends Car
{
	private static final Logger LOGGER = LogManager.getLogger(FireEngine.class);

	void water() 
	{
		LOGGER.debug("water");
	}
}
class Ambulance extends Car
{
	private static final Logger LOGGER = LogManager.getLogger(Ambulance.class);

	void siren()
	{
		LOGGER.debug("siren");
	}
}

