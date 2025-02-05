package com.barunsw.ojt.day22;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class TestFrame extends JFrame {
	private TestPanel testPanel = new TestPanel();
	
	public TestFrame() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void initComponent() {
		this.setContentPane(testPanel);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
