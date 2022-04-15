package com.barunsw.ojt.cjs.day15;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddressBookFrame extends JFrame {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddressBookFrame.class);
	private AddressBookPanel panel = new AddressBookPanel();
	
	public static final int WIDTH 	= 900;
	public static final int HEIGHT 	= 720;
	

	public AddressBookFrame() {
		try {
			initCompnent();

		} catch (Exception e) {
			LOGGER.debug(e.getMessage() + e);
		}
	}

	private void initCompnent() throws Exception {
		this.setTitle("THIS IS RMI");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new SocketFrame_this_WindowAdapter(this));
		this.setContentPane(panel);
	}

	void windowClosing(WindowEvent e) {
		int result = JOptionPane.showConfirmDialog(AddressBookFrame.this, "종료하시겠습니까?", "종료",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
}

class SocketFrame_this_WindowAdapter extends WindowAdapter {
	private AddressBookFrame frameAdaptee;

	public SocketFrame_this_WindowAdapter(AddressBookFrame frameAdaptee) {
		this.frameAdaptee = frameAdaptee;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		frameAdaptee.windowClosing(e);
	}
}