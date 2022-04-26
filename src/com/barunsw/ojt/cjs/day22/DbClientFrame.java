package com.barunsw.ojt.cjs.day22;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbClientFrame extends JFrame {
	private static final Logger LOGGER = LoggerFactory.getLogger(DbClientFrame.class);
	private DbClientPanel dbPanel = new DbClientPanel();

	public static final int WIDTH = 700;
	public static final int HEIGHT = 640;

	public DbClientFrame() {
		try {
			initCompnent();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void initCompnent() throws Exception {
		this.setTitle("DB Client");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new DbClientFrame_this_WindowAdapter(this));
		this.setContentPane(dbPanel);
	}

	void windowClosing(WindowEvent e) {
		int result = JOptionPane.showConfirmDialog(DbClientFrame.this, "종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
}

class DbClientFrame_this_WindowAdapter extends WindowAdapter {
	private DbClientFrame frameAdaptee;

	public DbClientFrame_this_WindowAdapter(DbClientFrame frameAdaptee) {
		this.frameAdaptee = frameAdaptee;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		frameAdaptee.windowClosing(e);
	}
}
