package com.barunsw.ojt.yjkim.day07;

import java.awt.Dimension;
import java.awt.Toolkit;

public class GridBagTest {

	public static void main(String[] args) {
		
		GridBagFrame gridbagframe = new GridBagFrame();
		
		Dimension srcdim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xPos = (srcdim.width - GridBagFrame.WIDTH) / 2;
		int yPos = (srcdim.height - GridBagFrame.HEIGHT) / 2;
		
		gridbagframe.setBounds(xPos, yPos, GridBagFrame.WIDTH, GridBagFrame.HEIGHT);
	
		gridbagframe.setVisible(true);
	}
}
