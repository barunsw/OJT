package com.barunsw.ojt.sjcha.day11;

import org.apache.logging.log4j.Logger;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;

public class TestFrame extends JFrame {
	private static final Logger LOGGER = LogManager.getLogger(TestFrame.class);
	
	public static final int WIDTH 	= 800;
	public static final int HEIGHT 	= 800;
	
	private TestPanel testPanel = new TestPanel();
	// TestFrame 생성자
	public TestFrame() {
		try {
			initComponent();
		}
		catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void initComponent() throws Exception {
		// frame의 타이틀 설정
		this.setTitle("Swing Test");
		
		// frame 닫힘 오퍼레이션
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		// panel를 frame에 올리기
		this.setContentPane(testPanel);
		
		// event
		this.addWindowListener(new TestFrame_this_WindowAdapter(this));
	}
	
	// WindowAdapter의 windowClosing함수 overriding
	// frame을 닫았을 경우
	void windowClosing(WindowEvent e) {
		LOGGER.debug("windowClosing");
		
		// 확인 다이얼로그
		int confirmResult = JOptionPane.showConfirmDialog(TestFrame.this, "종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION);
		
		// 확인 버튼을 누르면 시스템 종료
		if (confirmResult == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
}

class TestFrame_this_WindowAdapter extends WindowAdapter {
	private TestFrame testAdapter;
	
	public TestFrame_this_WindowAdapter(TestFrame testAdapter) {
		this.testAdapter = testAdapter;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		testAdapter.windowClosing(e);
	}
}
