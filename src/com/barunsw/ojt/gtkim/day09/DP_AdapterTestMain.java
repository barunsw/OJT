package com.barunsw.ojt.gtkim.day09;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DP_AdapterTestMain {
	private static final Logger LOGGER = LogManager.getLogger(DP_AdapterTestMain.class);
	public static void main(String[] args) {
		LOGGER.debug("어댑터 디자인 패턴 (상속버젼) 예제!");
		DP_PrintExtends p = new DP_PrintBannerExtends("Extend Adapter Pattern");
		p.printWeak();
		p.printStrong();
		
		LOGGER.debug("어댑터 디자인 패턴 (위임버젼) 예제!");
		DP_PrintBanerMandate p2 = new DP_PrintBanerMandate("Mandate Adapter Pattern");
		p2.printWeak();
		p2.printStrong();
	}

}
