package com.barunsw.ojt.cjs.day16;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.cjs.day09.AddressBookFrame;

public class ClientMain {
	private static final Logger LOGGER = LogManager.getLogger(ClientMain.class);

	public static EventQueueWorker eventQueueWorker = new EventQueueWorker();

	public static void main(String[] args) {
		try {
			eventQueueWorker.start();

			try {
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			} 
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}

			ChatFrame chatFrame = new ChatFrame();
			Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();

			int xPos = (scrDim.width - chatFrame.WIDTH) / 2;
			int yPos = (scrDim.height - chatFrame.HEIGHT) / 2;

			chatFrame.setBounds(new Rectangle(xPos, yPos, chatFrame.WIDTH, chatFrame.HEIGHT));
			chatFrame.setVisible(true);

		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
