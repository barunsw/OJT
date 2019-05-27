package com.barunsw.ojt.yjkim.day06;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class TestFrame extends JFrame{
	private static final Logger LOGGER = LogManager.getLogger(TestFrame.class);
	
	//현재 실행창의 크기를 지정한다.
	public static final int WIDTH 	= 600;
	public static final int HEIGHT 	= 400;
	
	//패널 생성 
	private TestPanel testPanel = new TestPanel();
	
	//프레임 생성자 내에서 컴퍼넌트 초기화 메소드를 호출한다. 
	public TestFrame() {
		try {
			initComponent();
		}catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	//컴퍼넌트 초기화 메소드 
	private void initComponent() throws Exception{
		//타이틀 설정
		this.setTitle("Swing_Test_1");
		//기본적인 닫힘 설정
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//프레임에 패널 추가
		this.setContentPane(testPanel);
		
		//윈도우 이벤트 추가
		this.addWindowListener(new TestFrame_this_WindowAdapter(this));
		
	}	
	
	
	//프레임 종료 시 발생하는 메소드
	void windowClosing(WindowEvent e) {
		LOGGER.debug("windowClosing");
		 //종료, 취소가 나오는 다이어로그 창을 띄운다. 
		int result = JOptionPane.showConfirmDialog(TestFrame.this, 
				"정말로 종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION);
		//종료 버튼 클릭 시 프레임이 종료된다.
		if(result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}else {
			//취소 버튼 클릭 시 취소 되었다는 다이어로그 메시지가 나온다.
			JOptionPane.showMessageDialog(TestFrame.this, "취소 되었습니다");
		}
	}
}

//WindowAdapter를 상속받은 클래스
class TestFrame_this_WindowAdapter extends WindowAdapter {

	private TestFrame adaptee;
	
	public TestFrame_this_WindowAdapter(TestFrame adaptee) {
		this.adaptee = adaptee;
	}
	
	//windowClosing 메소드를 상속받아 재정의한다. 
	@Override
	public void windowClosing(WindowEvent e) {
		adaptee.windowClosing(e);
	}
}

