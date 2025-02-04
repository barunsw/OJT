package com.barunsw.ojt.gtkim.day12;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientMain {
	private static final Logger LOGGER = LogManager.getLogger(ClientMain.class);

	public static void main(String[] args) {
		LOGGER.debug("Test Swing");
		try {
			// Look and Feel UIManager를 통해 ui 쉽게 변경 가능
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");		
		} 
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xPos = (scrSize.width - TestFrame.WIDTH) / 2;
		int yPos = (scrSize.height - TestFrame.HEIGHT) / 2;
		TestFrame frame = new TestFrame();
		
		frame.setBounds(new Rectangle(xPos, yPos, TestFrame.WIDTH, TestFrame.HEIGHT));
		frame.setVisible(true);
	}
}
