package com.barunsw.ojt.jyb.day2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringSharingExample {
    private static final Logger logger = LoggerFactory.getLogger(StringSharingExample.class);

    public static void main(String[] args) {
        String str1 = "abc";
        String str2 = "abc";

        String str3 = new String("abc");

        logger.info("str1의 주소 : {}", System.identityHashCode(str1));
        logger.info("str2의 주소 : {}", System.identityHashCode(str2));
        logger.info("str3의 주소 : {}", System.identityHashCode(str3));

        logger.info("str1과 str2의 주소는 같은가? : {}", (str1 == str2));
        logger.info("str2와 str3의 주소는 같은가? : {}", (str2 == str3));
    }
}
