package com.barunsw.ojt.mjg.day12;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Test extends Thread {

	private static final Logger LOGGER = LogManager.getLogger(ThreadTest.class);

	public static void main(String[] args) {

		Timer timer = new Timer();
		
		String mainStartTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

		for (int i = 0; i < 3; i++) {
			TestTimerTask t = new TestTimerTask(i);

			// timer.schedule(t, 0, 1000L);
			timer.scheduleAtFixedRate(t, 2000, 5000L);

			LOGGER.debug(String.format("[%d] activeCount:%d %s", i, Thread.activeCount(), mainStartTime));
		}
	}
}