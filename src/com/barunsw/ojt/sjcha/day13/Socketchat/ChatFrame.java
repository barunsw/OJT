package com.barunsw.ojt.sjcha.day13.Socketchat;

import org.apache.logging.log4j.Logger;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;

public class ChatFrame extends JFrame {
	private static final Logger LOGGER = LogManager.getLogger(ChatFrame.class);

	public static final int WIDTH 	= 600;
	public static final int HEIGHT 	= 500;

	private ChatPanel chatPanel = new ChatPanel();
	public ChatFrame() {
		try {
			initComponent();
		}
		catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void initComponent() throws Exception {
		// frame의 타이틀 설정.
		this.setTitle("Chatting Program");

		// frame 닫힘 오퍼레이션.
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// panel를 frame에 올리기.
		this.setContentPane(chatPanel);

		// event.
		this.addWindowListener(new ChatFrame_this_WindowAdapter(this));
	}

	void windowClosing(WindowEvent e) {
		LOGGER.debug("windowClosing");

		// 확인 다이얼로그
		int confirmResult = JOptionPane.showConfirmDialog(ChatFrame.this, "종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION);

		// 확인 버튼을 누르면 시스템 종료
		if (confirmResult == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
}

class ChatFrame_this_WindowAdapter extends WindowAdapter {
	private ChatFrame testAdapter;

	public ChatFrame_this_WindowAdapter(ChatFrame testAdapter) {
		this.testAdapter = testAdapter;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		testAdapter.windowClosing(e);
	}
}