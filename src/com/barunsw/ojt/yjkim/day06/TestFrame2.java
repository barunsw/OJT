package com.barunsw.ojt.yjkim.day06;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestFrame2 extends JFrame{
	private static final Logger LOGGER = LogManager.getLogger(TestFrame2.class);
	public static final int WIDTH  = 600;
	public static final int HEIGHT = 600;
	
	private TestPanel2 testpanel2 = new TestPanel2();
	
	
	public TestFrame2() {
		try {
			initComponents();
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
		
	}
	
	private void initComponents() throws Exception{
		this.setContentPane(testpanel2);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(new TestFrame2_this_WindowAdapter(this));
	}
	
	void windowClosing(WindowEvent e) {
		LOGGER.debug("windowClosing....");
		int result = JOptionPane.showConfirmDialog(TestFrame2.this, "종료하시겠습니까",
				"종료", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
	
}

class TestFrame2_this_WindowAdapter extends WindowAdapter{
	
	private TestFrame2 adaptee;
	
	public TestFrame2_this_WindowAdapter(TestFrame2 adaptee) {
		this.adaptee = adaptee;
	}
	@Override
	public void windowClosing(WindowEvent e) {
		adaptee.windowClosing(e);
	}
}