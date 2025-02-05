package com.barunsw.ojt.sjcha.day11;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.UIManager;

import com.barunsw.ojt.sjcha.day11.TestFrame;

public class ClientMain {
	public static void main(String[] args) {
		try {
			// Look and Feel UIManager를 통해 ui 쉽게 변경 가능
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception ex) {
		}
		
		// JFrame 클래스 객체 만들어서 사용하기
		TestFrame frame = new TestFrame();
		
		// screen 크기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xPosition = (screenSize.width - TestFrame.WIDTH) / 2;
		int yPosition = (screenSize.height - TestFrame.HEIGHT) / 2;
		
		// x위치, y위치, 프레임 가로길이, 프레임 세로길이
		frame.setBounds(new Rectangle(xPosition, yPosition, TestFrame.WIDTH, TestFrame.HEIGHT));
		
		// 화면에 보이게 한다. 
		frame.setVisible(true);
	}
}
