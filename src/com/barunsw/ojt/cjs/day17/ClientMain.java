package com.barunsw.ojt.cjs.day17;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientMain {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientMain.class);
	
	public static EventQueueWorker eventQueueWorker = new EventQueueWorker();

	public static void main(String[] args) {
		eventQueueWorker.start();
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception ex) {
		}
		
		RackViewFrame frame = new RackViewFrame();
		
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xPos = (scrDim.width - RackViewFrame.WIDTH) / 2;
		int yPos = (scrDim.height - RackViewFrame.HEIGHT) / 2;
		
		frame.setBounds(new Rectangle(xPos, yPos, RackViewFrame.WIDTH, RackViewFrame.HEIGHT));
		frame.setVisible(true);
	}
}
