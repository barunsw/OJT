package com.barunsw.ojt.iwkim.day07;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MySwingTests {
	public static Logger LOGGER = LogManager.getLogger();
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // 회사에서는 nimbus사용!
		}
		catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
		}
		
		MyTestFrame frame = new MyTestFrame();
		
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xPos = (scrDim.width - MyTestFrame.WIDTH) / 2;
		int yPos = (scrDim.height - MyTestFrame.HEIGHT) / 2;
		
		frame.setBounds(new Rectangle(xPos, yPos, MyTestFrame.WIDTH, MyTestFrame.HEIGHT));
		frame.setVisible(true);
	}
}