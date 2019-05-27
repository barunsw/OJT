package com.barunsw.ojt.yjkim.day06;

import java.awt.Dimension;
import java.awt.Toolkit;

public class SwingTest2 {
	
	public static void main(String[] args) {
		
		TestFrame2 testframe = new TestFrame2();
		
		Dimension srcdim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xPos = (srcdim.width - TestFrame2.WIDTH) / 2;
		int yPos = (srcdim.height - TestFrame2.HEIGHT) / 2;
		
		
		
		testframe.setBounds(xPos, yPos, TestFrame2.WIDTH, TestFrame2.HEIGHT);
		testframe.setVisible(true);
	}
}
