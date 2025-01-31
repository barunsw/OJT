package com.barunsw.ojt.iwkim.day07;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel extends JPanel {
	private static Logger LOGGER = LogManager.getLogger(TestPanel.class);
	
	private JPanel jPanel_Red = new JPanel();
	private JPanel jPanel_Green = new JPanel();
	
	private CardLayout cardLayout = new CardLayout();
	
	public TestPanel() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		jPanel_Red.setBackground(Color.red);
		jPanel_Green.setBackground(Color.green);
		
		jPanel_Red.setPreferredSize(new Dimension(100, 100));
		jPanel_Green.setPreferredSize(new Dimension(100, 100));
		
//		this.setLayout(new BorderLayout());
//		
//		this.add(jPanel_Red, BorderLayout.WEST);
//		this.add(jPanel_Green, BorderLayout.CENTER);
		
//		this.setLayout(new GridLayout(4, 4));
//
//		for (int i = 0; i < 16; i++) {
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
		
		this.setLayout(cardLayout);
		
		this.add(jPanel_Red, "RED");
		this.add(jPanel_Green, "GREEN");
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < 10; i++) {
					try {
						Thread.sleep(1000);
					}
					catch (Exception ex) {
					}
					
					if (i % 2 == 0) {
						cardLayout.show(TestPanel.this, "RED");
					}
					else {
						cardLayout.show(TestPanel.this, "GREEN");
					}
				}
			}
		});
		
		t.start();
	}
}
