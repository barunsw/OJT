package com.barunsw.ojt.iwkim.day08;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel extends JPanel {
	private static Logger LOGGER = LogManager.getLogger(TestPanel.class);
	
	private JPanel jPanel_Red = new JPanel();
	private JPanel jPanel_Green = new JPanel();
	private JPanel jPanel_Blue = new JPanel();
	
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
		jPanel_Blue.setBackground(Color.blue);
		
		jPanel_Red.setPreferredSize(new Dimension(100, 100));
		jPanel_Green.setPreferredSize(new Dimension(100, 100));
		jPanel_Blue.setPreferredSize(new Dimension(100, 100));
		
		jPanel_Red.setMinimumSize(new Dimension(100, 100));
		jPanel_Green.setMinimumSize(new Dimension(100, 100));
		jPanel_Blue.setMinimumSize(new Dimension(100, 100));
		
		this.setLayout(new GridBagLayout());
		
		this.add(jPanel_Red, new GridBagConstraints(
					0, 0, 1, 1,
					1.0, 1.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(5, 5, 5, 5),
					0, 0
				));

		this.add(jPanel_Green, new GridBagConstraints(
				1, 1, 1, 1,
				1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0),
				0, 0
			));
		
		this.add(jPanel_Blue, new GridBagConstraints(
				2, 2, 1, 1,
				1.0, 1.0,
				GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5),
				0, 0
			));
	}
}
