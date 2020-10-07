package com.barunsw.ojt.phs.day14;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class C_ClientFrame extends JFrame{
private static final Logger LOGGER = LogManager.getLogger(C_ClientFrame.class);
	
	public static final int WIDTH 	= 750;
	public static final int HEIGHT 	= 650;
	
	private C_ClientPanel clientPanel = new C_ClientPanel();
	
	public C_ClientFrame() {
		try {
			initComponent();
		}
		catch ( Exception e ) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void initComponent() throws Exception {
		
		// 타이틀을 입력한다.
		this.setTitle("주소록");
		
		// 오른쪽 상단에 닫기 X표시를 눌렀을때를 정의함
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//testPanel을 등록
		this.setContentPane(clientPanel);
		
		// window 이벤트 등록한다
		this.addWindowListener( new TestFrame_this_WindowAdapter(this) );
	}
	
	void windowClosing(WindowEvent e) {
		LOGGER.debug("windowClosing");
		
		int result = JOptionPane.showConfirmDialog(C_ClientFrame.this, 
				"정말로 종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION);
		if ( result == JOptionPane.OK_OPTION ) {
			System.exit(0);
		}
	}
}

//windowAdapter를 상속받아서 필요한것만 오버라이딩하여 사용한다.
class TestFrame_this_WindowAdapter extends WindowAdapter {
	private C_ClientFrame adaptee;
	
	public TestFrame_this_WindowAdapter (C_ClientFrame adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void windowClosing (WindowEvent e) {
		adaptee.windowClosing(e);
	}
}
