package com.barunsw.ojt.jyb.day16;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainPanel extends JPanel {
	private JSplitPane splitPane;
	private ShelfPanel shelfPanel;
	private AlarmPanel alarmPanel;
	public static final int ALL_BOARD_WIDTH = 864;
	public static final int ALL_BOARD_HEIGHT = 604;

	public MainPanel() {
		initComponents();
	}

	private void initComponents() {
		this.setLayout(new BorderLayout());

		shelfPanel = new ShelfPanel();
		alarmPanel = new AlarmPanel();

		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, shelfPanel, alarmPanel);
		splitPane.setDividerLocation(ALL_BOARD_HEIGHT);
		splitPane.setResizeWeight(0.7);
		this.add(splitPane, BorderLayout.CENTER);
	}
}
