package com.barunsw.ojt.cjs.day18;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

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
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
		int xPos = (scrDim.width - ExploreFrame.WIDTH) / 2;
		int yPos = (scrDim.height - ExploreFrame.HEIGHT) / 2;
		ExploreFrame exFrame = new ExploreFrame();

		exFrame.setBounds(new Rectangle(xPos, yPos, ExploreFrame.WIDTH, ExploreFrame.HEIGHT));
	}
}
