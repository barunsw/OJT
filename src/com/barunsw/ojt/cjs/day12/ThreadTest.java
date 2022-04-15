package com.barunsw.ojt.cjs.day12;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Timer;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadTest.class);

	public static void main(String[] args) {
		LOGGER.debug("+++ activeCount:" + Thread.activeCount());

//		for (int i = 0; i < 10; i++) {
//			TestThread t = new TestThread(i);
//			t.start();
//			LOGGER.debug(String.format("[%d] ,activeCount: %d",i,Thread.activeCount()));
//		}

//		Timer timer = new Timer();
//
//		for (int i = 0; i < 3; i++) {
//			TimerTest t = new TimerTest(i);
//			timer.scheduleAtFixedRate(t, 0, 1000L);
//			LOGGER.debug(String.format("[%d] activeCount: %d", 0, Thread.activeCount()));
//		}

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		TestFrame testFrame = new TestFrame();

		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();

		int xPos = (scrDim.width - testFrame.WIDTH) / 2;
		int yPos = (scrDim.height - testFrame.HEIGHT) / 2;

		testFrame.setBounds(new Rectangle(xPos, yPos, testFrame.WIDTH, testFrame.HEIGHT));
		testFrame.setVisible(true);
	}
}
