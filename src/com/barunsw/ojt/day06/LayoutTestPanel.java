package com.barunsw.ojt.day06;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LayoutTestPanel extends JPanel {
	private static Logger LOGGER = LoggerFactory.getLogger(LayoutTestPanel.class);
	
	private CardLayout cardLayout = new CardLayout();
	
	public LayoutTestPanel() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		JPanel jPanel_Red = new JPanel();
		jPanel_Red.setBackground(Color.red);
		jPanel_Red.setPreferredSize(new Dimension(100, 100));
		
		JPanel jPanel_Blue = new JPanel();
		jPanel_Blue.setBackground(Color.blue);
		jPanel_Blue.setPreferredSize(new Dimension(100, 100));
		
		JPanel jPanel_Green = new JPanel();
		jPanel_Green.setBackground(Color.green);
		jPanel_Green.setPreferredSize(new Dimension(100, 100));
		
		// 기본은 FlowLayout
//		this.add(jPanel_Red);
//		this.add(jPanel_Blue);

		// BorderLayout
//		this.setLayout(new BorderLayout());
//		this.add(jPanel_Red, BorderLayout.WEST);
//		this.add(jPanel_Blue);
		
		// GridLayout
//		this.setLayout(new GridLayout(1, 3));
//		this.add(jPanel_Red);
//		this.add(jPanel_Blue);
//		this.add(jPanel_Green);
		
		// null layout
//		this.setLayout(null);
//		this.add(jPanel_Red);
//		this.add(jPanel_Blue);
//		this.add(jPanel_Green);
//		
//		jPanel_Red.setBounds(0, 0, 100, 100);
//		jPanel_Blue.setBounds(100, 100, 100, 100);
//		jPanel_Green.setBounds(200, 200, 100, 100);
		
//		this.setLayout(cardLayout);
//		this.add(jPanel_Red, "red");
//		this.add(jPanel_Blue, "blue");
//		this.add(jPanel_Green, "green");
//		
//		cardLayout.show(this, "green");
		
		this.setLayout(new GridBagLayout());
		
		this.add(jPanel_Red, new GridBagConstraints(
				0, 0, 1, 1,
				0.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5),
				0, 0));
		
		this.add(jPanel_Blue, new GridBagConstraints(
				1, 1, 1, 1,
				1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0),
				0, 0));
		
		this.add(jPanel_Green, new GridBagConstraints(
				2, 2, 1, 1,
				1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0),
				0, 0));
	}
}
