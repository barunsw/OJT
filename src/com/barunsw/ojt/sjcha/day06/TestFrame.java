package com.barunsw.ojt.sjcha.day06;

import org.apache.logging.log4j.Logger;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;

public class TestFrame extends JFrame {
	private static final Logger LOGGER = LogManager.getLogger(TestFrame.class);
	
	public static final int WIDTH 	= 600;
	public static final int HEIGHT 	= 400;
	
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
		
		// 입력 다이얼로그 입력값
		String inputResult = "";
			
		while (inputResult.equals("")) {
			// 입력 다이얼로그
			inputResult = JOptionPane.showInputDialog("이름을 입력하세요.");
			
			// 입력값이 있다면 반복문 나오기
			if (inputResult.equals("") == false) {
				LOGGER.debug("이름은 " + inputResult);	
				break;
			}	
			// 입력값이 없다면 경고 메시지 
			JOptionPane.showMessageDialog(TestFrame.this, "값을 입력하세요.", "경고", JOptionPane.ERROR_MESSAGE);
		}
		
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
