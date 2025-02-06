package com.barunsw.ojt.sjcha.day17;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.UIManager;

public class ClientMain {
	public static EventQueueWorker eventQueueWorker = new EventQueueWorker();

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception ex) {
		}

		RackViewFrame frame = new RackViewFrame();

		eventQueueWorker.start();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int xPosition = (screenSize.width - RackViewFrame.WIDTH) / 2;
		int yPosition = (screenSize.height - RackViewFrame.HEIGHT) / 2;

		frame.setBounds(new Rectangle(xPosition, yPosition, RackViewFrame.WIDTH, RackViewFrame.HEIGHT));

		frame.setVisible(true);
	}
}