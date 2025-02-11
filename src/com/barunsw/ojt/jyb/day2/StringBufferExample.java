package com.barunsw.ojt.jyb.day2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringBufferExample {
	private static final Logger logger = LoggerFactory.getLogger(StringSharingExample.class);

	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();

		sb.append("하나");
		logger.info(sb.toString()); //print문은 toString() 메서드가 자동으로 호출되기 때문에 sb 자체를 출력해도 됨
		sb.append("둘");
		logger.info(sb.toString());
		sb.replace(0, 2, "다섯");
		logger.info(sb.toString());
	}

}
