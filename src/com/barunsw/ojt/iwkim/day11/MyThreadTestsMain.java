package com.barunsw.ojt.iwkim.day11;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyThreadTestsMain {
	private static Logger LOGGER = LogManager.getLogger(ThreadTest.class);
	
	public static void main(String[] args) {
		//MyThreadTests myThreadTests = new MyThreadTests();
		
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
