package com.barunsw.ojt.gtkim.day11;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestThread {
	private static final Logger LOGGER = LogManager.getLogger(TestThread.class);
	
	public static void main(String[] args) {
		LOGGER.debug(String.format("[%s] activeCount : %d", Thread.currentThread(), Thread.activeCount()));
		/*// simple Thread Test
		for (int i = 0; i < 10; i++) {
			ThreadOne t = new ThreadOne(i);
			t.start();
			
			LOGGER.debug(String.format("[%d] activeCount : %d", i, Thread.activeCount()));
		}
		*/
		
		/*
		// 스레드로 스케줄링 하는 방법 1. 스레드 슬립을 이용
		for (int i = 0; i < 3; i++) {
			ThreadTwo t = new ThreadTwo(i);
			t.start();
			
			LOGGER.debug(String.format("[%d] activeCount : %d", i, Thread.activeCount()));
		}
		*/
		
		
		/*
		// 스레드로 스케줄링 하는 방법 2. TimerTask 클래스 사용 
		Timer timer = new Timer();
		for (int i = 0; i < 3; i++) {
			TestTimerTask t = new TestTimerTask(i);//

			timer.schedule(t, 0, 2000L);
			
			timer.scheduleAtFixedRate(t, 0, 2000L);
			LOGGER.debug(String.format("[%d] activeCount:%d", i, Thread.activeCount()));
		}
		//timer.schedule(t, 0, 1000L);
		//timer.scheduleAtFixedRate(t, 0, 1000L);
		*/
		
		/*
		// 스레드로 스케줄링 하는 방법3. ScheduledExecutorService 클래스 사용
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		
		for (int i = 0; i < 3; i++) {
			TestTimerTask t = new TestTimerTask(i);
			service.scheduleAtFixedRate(t, 0, 2000L, TimeUnit.MILLISECONDS);	
			
			LOGGER.debug(String.format("[%d] activeCount:%d", i, Thread.activeCount()));
		}
		
		*/
		try {
			// Look and Feel UIManager를 통해 ui 쉽게 변경 가능
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception ex) {
		}
		
		TestFrame frame = new TestFrame();
		
		// 화면의 전체 크기
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xPos = (scrDim.width - TestFrame.WIDTH) / 2;
		int yPos = (scrDim.height - TestFrame.HEIGHT) / 2;
		
		// 1)과 2)를 동시에 처리
		frame.setBounds(new Rectangle(xPos, yPos, TestFrame.WIDTH, TestFrame.HEIGHT));
		// 3) 프레임 표시
		frame.setVisible(true);
		
		LOGGER.debug("--- activeCount:" + Thread.activeCount());		
	}
}
