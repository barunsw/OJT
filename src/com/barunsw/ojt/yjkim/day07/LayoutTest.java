package com.barunsw.ojt.yjkim.day07;

import java.awt.Dimension;
import java.awt.Toolkit;

public class LayoutTest {
	
	public static void main(String[] args) {
		
		LayoutFrame layoutframe = new LayoutFrame();
		
		Dimension srcdim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xPos = (srcdim.width - LayoutFrame.WIDTH) / 2;
		int yPos = (srcdim.height - LayoutFrame.HEIGHT) / 2;
		
		layoutframe.setBounds(xPos, yPos, LayoutFrame.WIDTH, LayoutFrame.HEIGHT);
		layoutframe.setVisible(true);
	}
	
}
