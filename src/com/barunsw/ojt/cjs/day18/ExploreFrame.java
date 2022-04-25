package com.barunsw.ojt.cjs.day18;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExploreFrame extends JFrame {

	private static Logger LOGGER = LoggerFactory.getLogger(ExploreFrame.class);
	ExplorePanel exPanel = new ExplorePanel();
	public static final int WIDTH = 960;
	public static final int HEIGHT = 720;

	public ExploreFrame() {
		try {
			initComponent();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void initComponent() throws Exception {

		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
		int xPos = (scrDim.width - ExploreFrame.WIDTH) / 2;
		int yPos = (scrDim.height - ExploreFrame.HEIGHT) / 2;

		this.setBounds(new Rectangle(xPos, yPos, ExploreFrame.WIDTH, ExploreFrame.HEIGHT));

		this.setTitle("Explorer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.add(exPanel);
	}

	public void windowClosing(WindowEvent e) {
		int result = JOptionPane.showConfirmDialog(ExploreFrame.this, "정말로 종료하시겠습니까");
		if (result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}

	}

	class ExploreFrame_this_WindowAdapter extends WindowAdapter {
		private ExploreFrame adapt;

		public ExploreFrame_this_WindowAdapter(ExploreFrame adapt) {
			this.adapt = adapt;
		}

		@Override
		public void windowClosing(WindowEvent e) {
			adapt.windowClosing(e);
		}
	}
}
