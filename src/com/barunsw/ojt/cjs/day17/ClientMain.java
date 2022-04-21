package com.barunsw.ojt.cjs.day17;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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

		MainFrame frame = new MainFrame();
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();

		int xPos = (scrDim.width - MainFrame.WIDTH) / 2;
		int yPos = (scrDim.height - MainFrame.HEIGHT) / 2;

		frame.setBounds(new Rectangle(xPos, yPos, MainFrame.WIDTH, MainFrame.HEIGHT));
		frame.setVisible(true);
	}
}
