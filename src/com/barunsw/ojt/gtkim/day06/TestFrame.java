package com.barunsw.ojt.gtkim.day06;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestFrame extends JFrame {
	private static final Logger LOGGER = LogManager.getLogger(TestFrame.class);
	
	public static final int WIDTH 	= 1080;
	public static final int HEIGHT 	= 720;
	
	private Container contentPane = this.getContentPane();
	private TestPanel testPanel   = new TestPanel();

	public TestFrame() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() {
		// 타이틀
		this.setTitle("SwingTest");
		// 종료시 옵션을 주는데 바로 종료도 가능하지만 
		// 종료상황시 여러 처리를 하기위해 윈도우어댑터를 사용하여 구체적으로 구현이 가능하다.
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		// 패널 붙이기 
		// 1.컨텐트 펜을 알아내어 직접 부착
		contentPane.add(testPanel);
		// 2.프레임에 부착하면 프레임이 대신 컨텐트펜에 부착 
		this.add(testPanel);
		// 3.프레임의 컨텐트펜에 바로 부착
		this.setContentPane(testPanel);
		
		this.addWindowListener(new TestFrame_this_WindowAdapter(this));
	}
	
	void windowClosing(WindowEvent e) {
		LOGGER.debug("창 종료가 선택되었습니다.");
		
		int result = JOptionPane.showConfirmDialog(this, 
				"정말로 종료 하시겠습니까?", "Exit", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION) {
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