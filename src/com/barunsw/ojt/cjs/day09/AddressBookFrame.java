package com.barunsw.ojt.cjs.day09;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddressBookFrame extends JFrame {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddressBookFrame.class);
	private AddressBookPanel panel = new AddressBookPanel();

	public AddressBookFrame() {
		try {
			initCompnent();

		} catch (Exception e) {
			LOGGER.debug(e.getMessage() + e);
		}
	}

	private void initCompnent() throws Exception {
		this.setTitle("THIS IS FRAME");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new AddressBookFrame_this_WindowAdapter(this));
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

class AddressBookFrame_this_WindowAdapter extends WindowAdapter {
	private AddressBookFrame frameAdaptee;

	public AddressBookFrame_this_WindowAdapter(AddressBookFrame frameAdaptee) {
		this.frameAdaptee = frameAdaptee;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		frameAdaptee.windowClosing(e);
	}
}