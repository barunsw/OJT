package com.barunsw.ojt.yjkim.day11;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Timer;

import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadTest {
	private static final Logger LOGGER = LogManager.getLogger(ThreadTest.class);

	public static void main(String[] args) {
		//현재 쓰레드의 갯수를 확인 할 수 있다.
		LOGGER.debug("+++ activeCount:" + Thread.activeCount());
		/*
		for (int i = 0; i < 10; i++) {
			TestThread2 t = new TestThread2(i);
			t.start();
			
			LOGGER.debug(String.format("[%d] activeCount:%d", i, Thread.activeCount()));
		}
		*/
		

		//timer 테스트 
			Timer timer = new Timer();
			TestTimerTask t = new TestTimerTask();
			//timer.schedule(t, 0, 2000L);
			//0초 후, 10초 간격으로 timer 호출
			timer.scheduleAtFixedRate(t, 0, 10*1000);
			LOGGER.debug("TimeTask Started");
			try {
				//120초 sleep
				Thread.sleep(120000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//120초 후 timer를 종료시킨다.
			timer.cancel();
			LOGGER.debug("TimerTask Canleled");
			
			try {
				//30초
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			LOGGER.debug(String.format("activeCount:%d", Thread.activeCount()));


/*		
		for (int i = 0; i < 3; i++) {
			TestThread2 t = new TestThread2(i);
			t.start();
			
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
