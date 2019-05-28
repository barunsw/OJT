package com.barunsw.ojt.gtkim.day07;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestSwing2 {
	private static final Logger LOGGER = LogManager.getLogger(TestSwing2.class);
	public static void main(String[] args) {
		LOGGER.debug("Swing 2 Test");
		
		try {
			// Look and Feel UIManager를 통해 ui 쉽게 변경 가능
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			TestFrame frame = new TestFrame();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

}
