package com.barunsw.ojt.cjs.day07;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Frame extends JFrame {
	private static Logger log = LoggerFactory.getLogger(Frame.class);
	public static final int WIDTH 	= 720;
	public static final int HEIGHT 	= 480;
	
	private Panel panel = new Panel();

	public Frame() {
		try {
			initCompnent();
		} catch (Exception e) {
			log.error(e.getMessage() + e);
		}
	}

	private void initCompnent() throws Exception {
		this.setTitle("THIS IS FRAME");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new Frame_this_WindowAdapter(this));
		this.setContentPane(panel);
	}

	void windowClosing(WindowEvent e) {
		int result = JOptionPane.showConfirmDialog(Frame.this, "종료하시겠습니까?",
				"종료", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
}

class Frame_this_WindowAdapter extends WindowAdapter {
	private Frame frameAdaptee;

	public Frame_this_WindowAdapter(Frame frameAdaptee) {
		this.frameAdaptee = frameAdaptee;
	}
	@Override
	public void windowClosing(WindowEvent e) {
		frameAdaptee.windowClosing(e);
	}
}