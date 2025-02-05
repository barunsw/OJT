package com.barunsw.ojt.yjkim.day08;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestFrame2 extends JFrame {
	private static final Logger LOGGER = LogManager.getLogger(TestFrame2.class);
	
	public static final int WIDTH 	= 600;
	public static final int HEIGHT 	= 400;
	
	private TestPanel2 testPanel = new TestPanel2();
	
	public TestFrame2() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		// 타이틀
		this.setTitle("SwingTest");
		// 기본적인 닫힘 오퍼레이션
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.setContentPane(testPanel);
		
		// 윈도우 이벤트
		this.addWindowListener(new TestFrame2_this_WindowAdapter(this));
	}
	
	void windowClosing(WindowEvent e) {
		LOGGER.debug("windowClosing");
		
		int result = JOptionPane.showConfirmDialog(TestFrame2.this, 
				"정말로 종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
}

class TestFrame2_this_WindowAdapter extends WindowAdapter {
	private TestFrame2 adaptee;
	
	public TestFrame2_this_WindowAdapter(TestFrame2 adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		adaptee.windowClosing(e);
	}
}