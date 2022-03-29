package com.barunsw.ojt.sjcha.day15.SocketChat;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.UIManager;

public class ClientMain {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception ex) {
		}

		ChatFrame frame = new ChatFrame();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int xPosition = (screenSize.width - ChatFrame.WIDTH) / 2;
		int yPosition = (screenSize.height - ChatFrame.HEIGHT) / 2;

		frame.setBounds(new Rectangle(xPosition, yPosition, ChatFrame.WIDTH, ChatFrame.HEIGHT));

		frame.setVisible(true);
	}
}