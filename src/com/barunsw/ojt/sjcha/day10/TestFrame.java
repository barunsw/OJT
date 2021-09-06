package com.barunsw.ojt.sjcha.day10;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestFrame extends JFrame {
		static final Logger LOGGER = LogManager.getLogger(TestFrame.class);
		
		public static final int WIDTH  = 500;
		public static final int HEIGHT = 500;
				 
		// private TestPanel testPanel = new TestPanel();
		private ClockPanel clockPanel = new ClockPanel();
		
		public TestFrame() {
			try {
				initComponent();
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
		
		private void initComponent() {
			this.setTitle("TestThread");
			this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

			//this.add(testPanel);
			this.add(clockPanel);
			
			this.addWindowListener(new TestFrame_this_WindowAdapter(this));
		}
		
		void windowClosing(WindowEvent e) {
			int result = JOptionPane.showConfirmDialog(this,
					"정말 종료 하시겠습니까?", "Exit", JOptionPane.OK_CANCEL_OPTION);
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