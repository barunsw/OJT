package com.barunsw.ojt.gtkim.day11;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadTwo extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ThreadTwo.class);

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
	private int id;
	
	public ThreadTwo(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			long startTime = System.currentTimeMillis();
			LOGGER.debug(String.format("[%d] %s", id,
					sdf.format(Calendar.getInstance().getTime())));
	
			
			long endTime = System.currentTimeMillis();
			
			try {
				Thread.sleep(3000L - (endTime - startTime));
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
	}
}
