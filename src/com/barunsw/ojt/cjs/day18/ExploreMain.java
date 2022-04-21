package com.barunsw.ojt.cjs.day18;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExploreMain {
	private static Logger LOGGER = LoggerFactory.getLogger(ExploreMain.class);

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage() + ex);
		}
		new ExploreFrame();
	}
}
