package com.barunsw.ojt.day06;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFrame extends JFrame {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestFrame.class);
	
	public static final int WIDTH 	= 800;
	public static final int HEIGHT 	= 600;
	
	private TestPanel testPanel = new TestPanel();
//	private LayoutTestPanel testPanel = new LayoutTestPanel();
	private ButtonLayoutPanel buttonLayoutPanel = new ButtonLayoutPanel();
	
	public TestFrame() {
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

		//this.setContentPane(testPanel);
		this.setContentPane(buttonLayoutPanel);
		
		// 윈도우 이벤트
		this.addWindowListener(new TestFrame_this_WindowAdapter(this));
/*
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
*/		
	}
	
	void windowClosing(WindowEvent e) {
		LOGGER.debug("windowClosing");
		/*
		String result2 = JOptionPane.showInputDialog(TestFrame.this, "값을 입력하세요.", "");
		
		int result = JOptionPane.showConfirmDialog(TestFrame.this, 
				"정말로 종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			JOptionPane.showMessageDialog(TestFrame.this, "종료하겠습니다.", "", JOptionPane.INFORMATION_MESSAGE);
			
			System.exit(0);
		}
		*/
		System.exit(0);
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