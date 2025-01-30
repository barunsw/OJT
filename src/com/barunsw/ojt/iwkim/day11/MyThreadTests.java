package com.barunsw.ojt.iwkim.day11;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MyThreadTests {
	private static Logger LOGGER = LogManager.getLogger(MyThreadTests.class);
	
	private int count1;
	private int count2;
	
	private Timer timer = new Timer();
	
	public MyThreadTests() {
		try {
			initThread();
			//initTimerTask();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initThread() {
		// 스레드를 2개만들어서 sleep메서드를 활용해 테스트해보자
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				LOGGER.info("t1 run");
				
				while (count1 < 50) {
					LOGGER.info("count1 : " + count1++ + " Thread Count : " + Thread.activeCount());
					
					try {
						// run메서드가 호출되어 실행될때
						// Thread클래스의 메서드인 sleep메서드로 딜레이를 줄수있다.
						// 보통 스레드테스트할때 많이 쓰인다.
						Thread.sleep(100);
					}
					catch (InterruptedException ie) {
						LOGGER.error(ie.getMessage(), ie);
					}
				}
			}
		}, "Thread t1");
		
		t1.start();
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				LOGGER.info("t2 run");
				
				while (count2<50) {
					LOGGER.info("count2 : " + count2++ + " Thread Count : " + Thread.activeCount());
					
					try {
						Thread.sleep(100);
					}
					catch (InterruptedException ie) {
						LOGGER.error(ie.getMessage(), ie);
					}
				}
			}
		}, "Thread t2");
		
		t2.start();
	}
	
	private void initTimerTask() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				LOGGER.info("TimerTask run");
			}
		};
		
		LOGGER.info("+++ timer.schedule");
		// timer.schedule(task, 0, 1000);
		// schedule메서드는 명령을 일고 실행하는데 실제로 1초보다 조금더 딜레이 되기때문에
		// 정확히 1초를 맞추고 싶으면 scheduleAtFixedRate을 사용하자
		timer.scheduleAtFixedRate(task, 0, 1000);
	}
}