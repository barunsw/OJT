package com.barunsw.ojt.day18;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestPanel.class);

	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	private JSplitPane jSplitPane1 = new JSplitPane();
	private JSplitPane jSplitPane2 = new JSplitPane();
	
	public TestPanel() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		this.setLayout(gridBagLayout);
		
		jSplitPane1.setOneTouchExpandable(true);
		//jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jSplitPane1.setDividerLocation(200);
		
		jSplitPane2.setOneTouchExpandable(true);
		jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jSplitPane2.setDividerLocation(200);
		
		JPanel jPanel_Red = new JPanel();
		jPanel_Red.setBackground(Color.red);
		
		jSplitPane1.setLeftComponent(jPanel_Red);
		jSplitPane1.setRightComponent(jSplitPane2);
		
		this.add(jSplitPane1, new GridBagConstraints(
					0, 0, 1, 1,
					1.0, 1.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0),
					0, 0
				));
	}
}
