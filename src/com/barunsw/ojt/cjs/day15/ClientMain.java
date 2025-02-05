package com.barunsw.ojt.cjs.day15;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.FileReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClientMain {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientMain.class);
	public static Properties properties = new Properties();

	private static void loadProperties(String configPath) throws Exception {
		Reader reader = new FileReader(configPath);
		properties.load(reader);

		LOGGER.debug("========== " + configPath + " ==========");

		Iterator<Object> keySet = properties.keySet().iterator();
		while (keySet.hasNext()) {
			Object key = keySet.next();
			Object value = properties.get(key);

			LOGGER.debug(String.format("%s = %s", key, value));
		}
		LOGGER.debug("==============================");
	}

	public static void main(String[] args) throws Exception {

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} 
		catch (Exception ex) {
		}
		loadProperties(args[0]);
		AddressBookFrame frmae = new AddressBookFrame();
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();

		int xPos = (scrDim.width - frmae.WIDTH) / 2;
		int yPos = (scrDim.height - frmae.HEIGHT) / 2;

		frmae.setBounds(new Rectangle(xPos, yPos, frmae.WIDTH, frmae.HEIGHT));
		frmae.setVisible(true);
		
		Thread.sleep(10_000);
	}
}
