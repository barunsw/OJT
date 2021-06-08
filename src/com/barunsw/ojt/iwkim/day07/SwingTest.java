package com.barunsw.ojt.iwkim.day07;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class SwingTest {
	public static void main(String[] args) {
		TestFrame frame = new TestFrame();
		
		// 화면 사이즈
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xPos = (scrDim.width - TestFrame.WIDTH) / 2;
		int yPos = (scrDim.height - TestFrame.HEIGHT) / 2;
		
		frame.setBounds(new Rectangle(xPos, yPos, TestFrame.WIDTH, TestFrame.HEIGHT));
		frame.setVisible(true);
	}
}
