package com.barunsw.ojt.sjcha.day06;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);
	
	// height는 22가 제일 보기 좋다.
	private final Dimension LABEL_SIZE = new Dimension(80, 22);
	
	private JButton[] jButton_Num = new JButton[10];
	private String[] jButton_Title = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
	// TestPanel의 생성자
	public TestPanel() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	public void initComponent() throws Exception {
		// Layout 설정 - 화면 구성
		// 동서남북으로 배치
		this.setLayout(new BorderLayout());
		
		//this.setLayout(new GridLayout(3,3));
		
		for (int i = 0; i < 10; i++) {
			jButton_Num[i] = new JButton(jButton_Title[i]);
		}
		this.add(jButton_Num[1], BorderLayout.CENTER);
		this.add(jButton_Num[2], BorderLayout.NORTH);
		/*
		for (int i = 0; i < 10; i++) {
			this.add(jButton_Num[i] = new JButton(jButton_Title[i]));
		}*/
	}
}
