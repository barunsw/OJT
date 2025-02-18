package com.barunsw.ojt.jyb.day16;

import javax.swing.JPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainPanel extends JPanel {
	private JSplitPane splitPane;
	private ShelfPanel shelfPanel;
	private JTable alarmTable;
	private JScrollPane tableScrollPane;
	public static final int BOARD_WIDTH = 40;
	public static final int BOARD_HEIGHT = 271;

	public static final int MPU_BOARD_HEIGHT = 550;
	public static final int ALL_BOARD_HEIGHT = 604;
	public static final int ALL_BOARD_WIDTH = 864;

	public MainPanel() {
		initComponents();
	}

	private void initComponents() {
		this.setLayout(new BorderLayout());

		shelfPanel = new ShelfPanel(this);

		String[] columnNames = { "Severity", "Board Name / ID", "발생 시간" };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
		alarmTable = new JTable(tableModel);
		tableScrollPane = new JScrollPane(alarmTable);
		tableScrollPane.setPreferredSize(new Dimension(ALL_BOARD_WIDTH, ALL_BOARD_HEIGHT));

		alarmTable.setPreferredScrollableViewportSize(new Dimension(ALL_BOARD_WIDTH, ALL_BOARD_HEIGHT));
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, shelfPanel, tableScrollPane);
		splitPane.setDividerLocation(ALL_BOARD_HEIGHT);
		splitPane.setResizeWeight(0.7);
		this.add(splitPane, BorderLayout.CENTER);
	}

	public void addAlarmData(String severity, String boardInfo, String time) {
		DefaultTableModel model = (DefaultTableModel) alarmTable.getModel();
		model.addRow(new Object[] { severity, boardInfo, time });
	}
}
