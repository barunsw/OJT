package com.barunsw.ojt.iwkim.day15;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestFrame extends JFrame {
	private static final Logger LOGGER = LogManager.getLogger(TestFrame.class);
	
	public static final int WIDTH 	= 480;
	public static final int HEIGHT 	= 720;
	
	private TestPanel testPanel;
	
	public TestFrame() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		this.setTitle("Chatting by RMI Test");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		String name = JOptionPane.showInputDialog("이름을 입력하세요 : ");
		
		testPanel = new TestPanel(name);
		this.setContentPane(testPanel);
		
		// 윈도우 이벤트
		this.addWindowListener(new TestFrame_this_WindowAdapter(this));
	}
	
	void windowClosing(WindowEvent e) {
		LOGGER.debug("종료 버튼이 선택 되었습니다");
		
		int result = JOptionPane.showConfirmDialog(TestFrame.this, 
				"정말로 종료하시겠습니까?", "EXIT", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			testPanel.close();
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