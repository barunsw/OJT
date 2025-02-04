package com.barunsw.ojt.mjg.day07;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFrame extends JFrame {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestFrame.class);
	
	public static final int WIDTH 	= 600;
	public static final int HEIGHT 	= 400;
	
//	private TestPanel testPanel = new TestPanel();
	
	public TestFrame() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		// 제목
		this.setTitle("SwingTest");
		
		// 기본적인 닫힘 오퍼레이션
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
        // 다이얼로그 예제용 버튼들이 있는 패널 생성
        JPanel panel = new JPanel();
        
		
		// 윈도우 이벤트
		// 클래스 만들어서 사용(클래스명 rule)
		this.addWindowListener(new TestFrame_this_WindowAdapter(this)); 
		
	
		// 익명 클래스
//		this.addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowClosing(WindowEvent e) {
//				// TODO Auto-generated method stub
//				System.exit(0);
//			}
//		});
		
		// Confirm Dialog 예제 버튼
        JButton btnConfirm = new JButton("Confirm Dialog 예제");
        btnConfirm.addActionListener(e -> showConfirmExample());
        
        // Input Dialog 예제 버튼
        JButton btnInput = new JButton("Input Dialog 예제");
        btnInput.addActionListener(e -> showInputExample());
        
        // Message Dialog 예제 버튼
        JButton btnMessage = new JButton("Message Dialog 예제");
        btnMessage.addActionListener(e -> showMessageExample());
        
        // 버튼들을 패널에 추가
        panel.add(btnConfirm);
        panel.add(btnInput);
        panel.add(btnMessage);
        
        // 생성한 패널을 컨텐트팬으로 설정
        this.setContentPane(panel);
        
        // 프레임 크기 및 위치 설정 후 보이기
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
	}
	
	// 윈도우 종료 시 호출되는 메소드 
	void windowClosing(WindowEvent e) {
		LOGGER.debug("windowClosing");
		
		int result = JOptionPane.showConfirmDialog(TestFrame.this, 
				"정말로 종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
	
    // Confirm Dialog
    private void showConfirmExample() {
        int result = JOptionPane.showConfirmDialog(
            this, "정말로 진행하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION
        );
        if (result == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "사용자가 YES를 선택하였습니다.");
        } else {
            JOptionPane.showMessageDialog(this, "사용자가 NO를 선택하였습니다.");
        }
    }
    
    // Input Dialog
    private void showInputExample() {
        String input = JOptionPane.showInputDialog(
            this, "이름을 입력하세요:", "입력", JOptionPane.QUESTION_MESSAGE
        );
        if (input != null && !input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "입력한 이름: " + input);
        } else {
            JOptionPane.showMessageDialog(this, "입력이 취소되었거나 빈 값입니다.");
        }
    }
    
    // Message Dialog
    private void showMessageExample() {
        JOptionPane.showMessageDialog(
            this, "작업이 성공적으로 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE
        );
    }
}

class TestFrame_this_WindowAdapter extends WindowAdapter {
	private TestFrame adaptee;
	
	public TestFrame_this_WindowAdapter(TestFrame adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		adaptee.windowClosing(e);  // TestFrame의 windowClosing 호출
	}
}