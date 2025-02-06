package com.barunsw.ojt.sjcha.day17;

import org.apache.logging.log4j.Logger;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;

public class RackViewFrame extends JFrame {
	private static final Logger LOGGER = LogManager.getLogger(RackViewFrame.class);

	public static final int WIDTH 	= 870;
	public static final int HEIGHT 	= 1100;

	private RackViewPanel RackViewPanel = new RackViewPanel();

	public RackViewFrame() {
		try {
			initComponent();
		}
		catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void initComponent() throws Exception {
		this.setTitle("RackView");

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.setContentPane(RackViewPanel);

		this.addWindowListener(new ChatFrame_this_WindowAdapter(this));
	}

	void windowClosing(WindowEvent e) {
		LOGGER.debug("windowClosing");

		int confirmResult = JOptionPane.showConfirmDialog(RackViewFrame.this, "종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION);

		if (confirmResult == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
}

class ChatFrame_this_WindowAdapter extends WindowAdapter {
	private RackViewFrame testAdapter;

	public ChatFrame_this_WindowAdapter(RackViewFrame testAdapter) {
		this.testAdapter = testAdapter;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		testAdapter.windowClosing(e);
	}
}
