package com.barunsw.ojt.iwkim.day11;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadTest {
	private static Logger LOGGER = LogManager.getLogger(ThreadTest.class);
	
	private int count1;
	private int count2;
	
	private Timer timer = new Timer();

	public ThreadTest() {
		try {
			initThread();
			initTimerTask();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initThread() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				LOGGER.debug("t1 run");
				
				while (count1 < 100) {
					LOGGER.debug("count1:" + count1++ + " " + Thread.activeCount());
					
					try {
						Thread.sleep(100);
					}
					catch (InterruptedException ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}
			}
		}, "t1");
		
		t1.start();
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				LOGGER.debug("t2 run");
				
				while (count2 < 100) {
					LOGGER.debug("count2:" + count2++ + " " + Thread.activeCount());
					
					try {
						Thread.sleep(100);
					}
					catch (InterruptedException ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}
			}
		}, "t2");
		
		t2.start();		
	}
	
	private void initTimerTask() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				LOGGER.debug("timer task run");
			}
		};
		
		LOGGER.debug("+++ timer.schedule");
		timer.scheduleAtFixedRate(task, 0, 1000);

	}
	
	public static void main(String[] args) {
		LOGGER.debug("+++ main start");
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // 회사에서는 nimbus사용!
		}
		catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
		}
		
		TestFrame frame = new TestFrame();
		
		// 화면 사이즈
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xPos = (scrDim.width - TestFrame.WIDTH) / 2;
		int yPos = (scrDim.height - TestFrame.HEIGHT) / 2;
		
		frame.setBounds(new Rectangle(xPos, yPos, TestFrame.WIDTH, TestFrame.HEIGHT));
		frame.setVisible(true);
	}
}
