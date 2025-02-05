package com.barunsw.ojt.gtkim.day11;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestFrame extends JFrame {
		static final Logger LOGGER = LogManager.getLogger(TestFrame.class);
		
		public static final int WIDTH  = 1080;
		public static final int HEIGHT = 720;
				 
		private TestPanel testPanel = new TestPanel();
		
		public TestFrame() {
			try {
				initComponent();
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
		
		private void initComponent() {
			this.setTitle("Swing Frame for TestThread");
			this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

			this.add(testPanel);
			
			this.addWindowListener(new TestFrame_this_WindowAdapter(this));
		}
		
		void windowClosing(WindowEvent e) {
			LOGGER.debug("종료 버튼이 선택되었습니다.");
			
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