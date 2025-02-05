package com.barunsw.ojt.cjs.day16;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatFrame extends JFrame {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChatFrame.class);
	private ChatPanel panel = new ChatPanel();
	
	public static final int WIDTH 	= 700;
	public static final int HEIGHT 	= 540;
	

	public ChatFrame() {
		try {
			initCompnent();

		} catch (Exception e) {
			LOGGER.debug(e.getMessage() + e);
		}
	}

	private void initCompnent() throws Exception {
		this.setTitle("THIS IS FRAME");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new ChatFrame_this_WindowAdapter(this));
		this.setContentPane(panel);
	}

	void windowClosing(WindowEvent e) {
		int result = JOptionPane.showConfirmDialog(ChatFrame.this, "종료하시겠습니까?", "종료",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
}

class ChatFrame_this_WindowAdapter extends WindowAdapter {
	private ChatFrame frameAdaptee;

	public ChatFrame_this_WindowAdapter(ChatFrame frameAdaptee) {
		this.frameAdaptee = frameAdaptee;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		frameAdaptee.windowClosing(e);
	}
}