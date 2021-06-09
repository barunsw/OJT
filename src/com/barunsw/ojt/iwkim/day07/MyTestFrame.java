package com.barunsw.ojt.iwkim.day07;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MyTestFrame extends JFrame {
	private static Logger LOGGER = LogManager.getLogger(MyTestFrame.class);
	
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 720;
	
	private MyTestPanel myTestPanel = new MyTestPanel();
	
	public MyTestFrame() {
		try {
			initComponent();
			changePanelColor();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void initComponent() {
		this.setTitle("테스트");
		// JFrame.DO_NOTHING_ON_CLOSE : 종료버튼 클릭 시 취소를 누를 경우 화면을 멈추게 하기 위함
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// JFrame의 contentPane에 컨테이너(여기에선 JPanel)를 넣어서 사용하겠다는 의미
		this.setContentPane(myTestPanel); 
		
 		// 윈도우 이벤트 등록 메서드 
		this.addWindowListener(new MyTestFrame_this_WindowListener(this));
//		this.addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowClosing(WindowEvent e) {
//				System.exit(0);
//			}
//		});
	}
	
	private void changePanelColor() {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i< 10; i ++) {
					try {
						Thread.sleep(1000);
					}
					catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
					}
					
					if (i % 2 == 0) {
						myTestPanel.changePanelColor("RED");
					}
					else if (i % 3 == 0) {
						myTestPanel.changePanelColor("GREEN");
					}
					else {
						myTestPanel.changePanelColor("BLUE");
					}
				}	
			}
		});
		thread.start();
	}
	
//	한 java파일에는 public class는 단 한개만 존재할 수 있다.
//	이유는 컴파일 시 public class의 이름을 가지고 컴파일 하는데, 
//	public class가 여러개이면 컴파일러가 무엇을 이름으로 설정해야될지 모르기 때문에 에러 발생!
//	같은 파일 내 다른 클래스에서 public class의 메서드를 호출하려면
//	접근제어자의 최소범위는 같은패키지이므로 Default를 접근제어자로 사용한다.
	void windowClosing(WindowEvent e) { 
		int result = JOptionPane.showConfirmDialog(this, "종료하시겠습니까?", "종료여부", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
}

// WindowLister인터페이스 중 일부 메서드만 오버라이딩하고싶기 때문에
// 이미 빈 메서드로 구현되어있는 WindowAdapter를 상속받아서 원하는 메서드만 재정의하자!
class MyTestFrame_this_WindowListener extends WindowAdapter {
	private MyTestFrame adaptee;
	
	public MyTestFrame_this_WindowListener(MyTestFrame adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		adaptee.windowClosing(e);
	};
}
// [JOptionPane static 변수들 값 참고용]
// DEFAULT_OPTION = -1;
//
// YES_NO_OPTION = 0;
//
// YES_NO_CANCEL_OPTION = 1;
//
// OK_CANCEL_OPTION = 2;
//
// YES_OPTION = 0;
//
// NO_OPTION = 1;
//
// CANCEL_OPTION = 2;
//
// OK_OPTION = 0;
//
// CLOSED_OPTION = -1;
//
// ERROR_MESSAGE = 0;