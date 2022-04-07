package com.barunsw.ojt.cjs.day08;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.cjs.day07.Frame;

public class AddressBookTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddressBookTest.class);
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} 
		catch (Exception ex) {
		}
		
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
		
		AddressBookFrame adressFrame = new AddressBookFrame();
		int xPos = (scrDim.width - Frame.WIDTH) / 2;
		int yPos = (scrDim.height - Frame.HEIGHT) / 2;

		adressFrame.setBounds(new Rectangle(xPos, yPos, Frame.WIDTH, Frame.HEIGHT));
		adressFrame.setVisible(true);

		LOGGER.debug("active count:" + Thread.activeCount());
	}
}
