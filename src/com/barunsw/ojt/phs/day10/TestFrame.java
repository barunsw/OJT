package com.barunsw.ojt.phs.day10;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestFrame extends JFrame{
private static final Logger LOGGER = LogManager.getLogger(TestFrame.class);
	
	public static final int WIDTH 	= 750;
	public static final int HEIGHT 	= 650;
	
	private TestPanel testPanel = new TestPanel();
	public TestFrame() {
		try {
			initComponent();
		}
		catch ( Exception e ) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void initComponent() throws Exception {
		
		// 타이틀을 입력한다.
		this.setTitle("SwingTest Frame");
		
		// 오른쪽 상단에 닫기 X표시를 눌렀을때를 정의함
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//testPanel을 등록
		this.setContentPane(testPanel);
		
		// window 이벤트 등록한다
		this.addWindowListener( new TestFrame_this_WindowAdapter(this) );
	}
	
	void windowClosing(WindowEvent e) {
		LOGGER.debug("windowClosing");
		
		int result = JOptionPane.showConfirmDialog(TestFrame.this, 
				"정말로 종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION);
		if ( result == JOptionPane.OK_OPTION ) {
			System.exit(0);
		}
	}
}

//windowAdapter를 상속받아서 필요한것만 오버라이딩하여 사용한다.
class TestFrame_this_WindowAdapter extends WindowAdapter {
	private TestFrame adaptee;
	
	public TestFrame_this_WindowAdapter (TestFrame adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void windowClosing (WindowEvent e) {
		adaptee.windowClosing(e);
	}
}
