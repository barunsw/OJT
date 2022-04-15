package com.barunsw.ojt.cjs.day14;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketFrame extends JFrame {
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketFrame.class);
	private SocketPanel panel = new SocketPanel();
	
	public static final int WIDTH 	= 900;
	public static final int HEIGHT 	= 720;
	

	public SocketFrame() {
		try {
			initCompnent();

		} catch (Exception e) {
			LOGGER.debug(e.getMessage() + e);
		}
	}

	private void initCompnent() throws Exception {
		this.setTitle("THIS IS FRAME");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new SocketFrame_this_WindowAdapter(this));
		this.setContentPane(panel);
	}

	void windowClosing(WindowEvent e) {
		int result = JOptionPane.showConfirmDialog(SocketFrame.this, "종료하시겠습니까?", "종료",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
}

class SocketFrame_this_WindowAdapter extends WindowAdapter {
	private SocketFrame frameAdaptee;

	public SocketFrame_this_WindowAdapter(SocketFrame frameAdaptee) {
		this.frameAdaptee = frameAdaptee;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		frameAdaptee.windowClosing(e);
	}
}