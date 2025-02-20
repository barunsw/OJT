package com.barunsw.ojt.iwkim.day16;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

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
		}
	
		
		TestFrame frame = new TestFrame();

		// 화면의 전체 크기
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();

		int xPos = (scrDim.width - TestFrame.WIDTH) / 2;
		int yPos = (scrDim.height - TestFrame.HEIGHT) / 2;

		// 1)과 2)를 동시에 처리
		frame.setBounds(new Rectangle(xPos, yPos, TestFrame.WIDTH, TestFrame.HEIGHT));
		// 3) 프레임 표시
		frame.setVisible(true);
	}
}
