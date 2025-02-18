package com.barunsw.ojt.jyb.day16;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientMain {
	private static final Logger LOGGER = LogManager.getLogger(ClientMain.class);

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception ex) {
			LOGGER.error("LookAndFeel 설정 실패", ex);
		}

		JFrame frame = new JFrame("Client Application");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout()); // 패널이 창을 꽉 채우도록 설정

		MainPanel mainPanel = new MainPanel();
		frame.add(mainPanel, BorderLayout.CENTER); // MainPanel이 전체 크기를 차지하도록 추가

		// 화면 크기에 맞게 창 크기 조절
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 870;
		int height = 1000;
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null); // 화면 중앙 정렬

		frame.setVisible(true);
	}
}
