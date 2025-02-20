package com.barunsw.ojt.iwkim.day14;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestFrame extends JFrame {
	private static Logger LOGGER = LogManager.getLogger(TestFrame.class);
	
	public static final int WIDTH 	= 740;
	public static final int HEIGHT 	= 625;
	
	private MyTestPanel testPanel = new MyTestPanel();
	
	public TestFrame() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		this.setTitle("AddressBook");
		// 기본적인 닫힘 오퍼레이션
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.setContentPane(testPanel);
		
		// 윈도우 이벤트
		this.addWindowListener(new TestFrame_this_WindowListener(this));
	}
	
	void windowClosing(WindowEvent e) {		
		int result = JOptionPane.showConfirmDialog(this, "종료하시겠습니까?", "종료 여부", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
}

class TestFrame_this_WindowListener extends WindowAdapter {
	private TestFrame adaptee;
	
	public TestFrame_this_WindowListener(TestFrame adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		adaptee.windowClosing(e);
	}
}