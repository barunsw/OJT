package com.barunsw.ojt.jyb.day12;

import java.util.Iterator;
import java.util.Properties;

public class PropertiesTest { // Java 시스템 속성 정보 출력
	public static void main(String[] args) {
		Properties prop = System.getProperties(); // 시스템에서 설정된 속성들을 가져옴
		Iterator iter = prop.keySet().iterator(); // 속성의 모든 키를 set 형식으로 반환
		while (iter.hasNext()) {
			String key = (String) iter.next(); // 다음 키

			System.out.println(String.format("%s=%s", key, prop.get(key)));
		}
	}
}
