package com.barunsw.ojt.sjcha.day10;

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
		LOGGER.debug("+++ activeCount:" + Thread.activeCount());

		/*
		 * Timer timer = new Timer(); for (int i = 0; i < 3; i++) { TestTimerTask t =
		 * new TestTimerTask(0); //timer.schedule(t, 0, 1000L);
		 * timer.scheduleAtFixedRate(t, 0, 1000L);
		 * 
		 * LOGGER.debug(String.format("[%d] activeCount:%d", 0, Thread.activeCount()));
		 * }
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
		
		frame.setBounds(new Rectangle(xPos, yPos, TestFrame.WIDTH, TestFrame.HEIGHT));
		// 프레임 표시
		frame.setVisible(true);

		LOGGER.debug("--- activeCount:" + Thread.activeCount());		
	}
}
