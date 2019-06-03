package com.barunsw.ojt.yjkim.day11;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestThread2 extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(TestThread.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
	private int id;
	
	public TestThread2(int id) {
		this.id = id;
	}
	
	//1초마다 Thread가 진행된다. 
	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			long startTime = System.currentTimeMillis();
			
			LOGGER.debug(String.format("[%d] %s"
					, id, sdf.format(Calendar.getInstance().getTime())));
			
			try {
				Thread.sleep(500);
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
			
			long endTime = System.currentTimeMillis();
			
			try {
				
				//쓰레드가 지속하는동안에 발생한 오차시간을 제거한다. 
				Thread.sleep(1000L - (endTime - startTime));
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
	}
}
