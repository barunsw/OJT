package com.barunsw.ojt.cjs.day13;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFrame extends JFrame {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestFrame.class);
	
	public static final int WIDTH 	= 600;
	public static final int HEIGHT 	= 400;
	
	private ClockPanel clockPanel = new ClockPanel();
	
	public TestFrame() {
		try {
			initComponent(); 
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		this.setTitle("ClockPanel");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setContentPane(clockPanel);
		this.addWindowListener(new TestFrame_this_WindowAdapter(this));
	}
	
	void windowClosing(WindowEvent e) {
		LOGGER.debug("windowClosing");
		
		int result = JOptionPane.showConfirmDialog(TestFrame.this, 
				"정말로 종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
}

class TestFrame_this_WindowAdapter extends WindowAdapter {
	private TestFrame adaptee;
	
	public TestFrame_this_WindowAdapter(TestFrame adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		adaptee.windowClosing(e);
	}
}