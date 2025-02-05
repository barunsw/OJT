package com.barunsw.ojt.iwkim.day07;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyTestPanel extends JPanel { // panel관련 메서드들을 사용하려면 JPanel을 상속해야함
	private static Logger LOGGER = LogManager.getLogger(MyTestPanel.class);
	
	private JPanel jPanel_Red   = new JPanel();
	private JPanel jPanel_Green = new JPanel();
	private JPanel jPanel_blue	= new JPanel(); 
	
	private CardLayout cardLayout = new CardLayout();   // 컴포넌트를 포개어서 배치한다.
	
	// 생성자를 통해 레이아웃 및 JPanel객체 설정
	public MyTestPanel() {
		try {
			initComponent(); 
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void initComponent() throws Exception {
		// JPanel 객체 설정 : 객체를 add메서드에 넣기전에 하고싶은 설정을 지정해주자
		jPanel_Red.setBackground(Color.red);
		jPanel_Green.setBackground(Color.green);
		jPanel_blue.setBackground(Color.blue);
		
		jPanel_Red.setPreferredSize(new Dimension(300, 300));
		jPanel_Green.setPreferredSize(new Dimension(100,100));
		jPanel_blue.setPreferredSize(new Dimension(200, 200));
		
// 		FlowLayout 방식 : 디폴트값이므로 따로 setLayout으로 설정하지 않아도 됨. 
//		this.add(jPanel_Green);
//		this.add(jPanel_Red);
//		this.add(jPanel_blue);
		
		
//		BorderLayout 방식
// 		this.setLayout(new BorderLayout());
		
// 		this.add(jPanel_Red, BorderLayout.WEST);
//		this.add(jPanel_Green, BorderLayout.CENTER);
//		this.add(jPanel_blue, BorderLayout.NORTH);
		
		
//		GridLayout 방식 
//		this.setLayout(new GridLayout(5,5));
//		
//		for (int i = 0; i < 10; i++) { //테스트 결과 좌->우가 먼저 채워지고 그다음 위->아래가 채워진다. 
//			JPanel onePanel = new JPanel();
//			int r = (int)(Math.random() * 256);
//			int g = (int)(Math.random() * 256);
//			int b = (int)(Math.random() * 256);
//			
//			onePanel.setBackground(new Color(r, g, b));
//			onePanel.setPreferredSize(new Dimension(100, 100));
//			
//			this.add(onePanel);
//		}
			
		
//		CardLayout 방식
		this.setLayout(cardLayout);
		
		this.add(jPanel_Red, "RED");
		this.add(jPanel_Green, "GREEN");
		this.add(jPanel_blue, "BLUE");
	}
	
	public void changePanelColor(String color) {
		cardLayout.show(this, color);
	}
}