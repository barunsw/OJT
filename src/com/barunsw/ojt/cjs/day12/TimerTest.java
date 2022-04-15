package com.barunsw.ojt.cjs.day12;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimerTest extends TimerTask{
	private static final Logger LOGGER = LogManager.getLogger(TestThread.class);

	private int id;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);

	public TimerTest(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		String currentTime = dateFormat.format(Calendar.getInstance().getTime());
		
		LOGGER.debug(String.format("[%d] %s", id, currentTime));
	}
}
