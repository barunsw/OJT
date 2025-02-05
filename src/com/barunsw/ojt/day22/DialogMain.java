package com.barunsw.ojt.day22;

import javax.swing.UIManager;

public class DialogMain {
	public static TestFrame frame;

	public static void main(String[] args) {
		try {
			// Look and Feel UIManager를 통해 ui 쉽게 변경 가능
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception ex) {
		}
		
		frame = new TestFrame();
		frame.setBounds(0, 0, 600, 400);
		frame.setVisible(true);
	}
}
