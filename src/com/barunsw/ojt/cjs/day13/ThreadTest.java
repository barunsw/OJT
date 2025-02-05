package com.barunsw.ojt.cjs.day13;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadTest {
	private static Logger LOGGER = LoggerFactory.getLogger(ClockPanel.class);

	public static void main(String[] args) {
		LOGGER.debug("+++ activeCount:" + Thread.activeCount());

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception ex) {
		}
		
		TestFrame frame = new TestFrame();
		
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xPos = (scrDim.width - TestFrame.WIDTH) / 2;
		int yPos = (scrDim.height - TestFrame.HEIGHT) / 2;
		
		frame.setBounds(new Rectangle(xPos, yPos, TestFrame.WIDTH, TestFrame.HEIGHT));
		frame.setVisible(true);

		LOGGER.debug("--- activeCount:" + Thread.activeCount());		
			
	}
}