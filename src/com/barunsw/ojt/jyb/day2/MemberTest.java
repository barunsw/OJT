package com.barunsw.ojt.jyb.day2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemberTest {
	private static final Logger logger = LoggerFactory.getLogger(StringSharingExample.class);

	public static class Member {
		int age = 20;
		int height = 180;
		int weight = 80;
	}

	public static void main(String[] args) {
		Member member = new Member();

		logger.info("member : {}", member);
		logger.info("age : {}", member.age);
		logger.info("height : {}", member.height);
		logger.info("weight : {}", member.weight);

	}

}
