package com.barunsw.ojt.cjs.day22;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbClientMain {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbClientMain.class);
	public static DbClientFrame dbFrame;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception ex) {
		}

		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();

		dbFrame = new DbClientFrame();
		int xPos = (scrDim.width - DbClientFrame.WIDTH) / 2;
		int yPos = (scrDim.height - DbClientFrame.HEIGHT) / 2;

		dbFrame.setBounds(new Rectangle(xPos, yPos, DbClientFrame.WIDTH, DbClientFrame.HEIGHT));
		dbFrame.setVisible(true);

	}
}
