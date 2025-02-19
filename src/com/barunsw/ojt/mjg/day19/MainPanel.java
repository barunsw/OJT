package com.barunsw.ojt.mjg.day19;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainPanel extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainPanel.class);
	
	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	private JSplitPane jSplitPane_Left = new JSplitPane();
	private JSplitPane jSplitPane_Right = new JSplitPane();
	
	private ExplorerPanel explorerPanel = new ExplorerPanel();
	private InputPanel inputPanel = new InputPanel();
	private ResultPanel resultPanel = new ResultPanel();
	
	public MainPanel() {
		try {
			initEvent();
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initEvent() {
		ClientMain.eventQueueWorker.addEventListener(resultPanel);
	}

	private void initComponent() {
		this.setLayout(gridBagLayout);		
		
		jSplitPane_Left.setOneTouchExpandable(true);
		jSplitPane_Left.setDividerLocation(250);
		
		jSplitPane_Right.setOneTouchExpandable(true);
		jSplitPane_Right.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jSplitPane_Right.setDividerLocation(250);
		
		jSplitPane_Left.setLeftComponent(explorerPanel);
		jSplitPane_Left.setRightComponent(jSplitPane_Right);
		
		jSplitPane_Right.setTopComponent(inputPanel);
		jSplitPane_Right.setBottomComponent(resultPanel);
		
		this.add(jSplitPane_Left, new GridBagConstraints(
					0, 0, 1, 1,
					1.0, 1.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0),
					0, 0
				));
	}
}
