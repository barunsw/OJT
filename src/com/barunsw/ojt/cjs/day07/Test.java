package com.barunsw.ojt.cjs.day07;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Test {

	private static Logger LOGGER = LoggerFactory.getLogger(Test.class);

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} 
		catch (Exception ex) {
		}
		
		Frame frame = new Frame();

		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();

		int xPos = (scrDim.width - Frame.WIDTH) / 2;
		int yPos = (scrDim.height - Frame.HEIGHT) / 2;

		frame.setBounds(new Rectangle(xPos, yPos, Frame.WIDTH, Frame.HEIGHT));
		frame.setVisible(true);

		LOGGER.debug("active count:" + Thread.activeCount());
		//스윙은 최소 2개 이상의 스레드가 돌아간다.
	}
}
