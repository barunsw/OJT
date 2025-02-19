package com.barunsw.ojt.jyb.day16;

import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import com.barunsw.ojt.vo.BoardVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlarmPanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LogManager.getLogger(AlarmPanel.class);

	private CommonTableModel tableModel;
	private JTable alarmTable;

	public AlarmPanel() {
		Vector<String> columnNames = new Vector<>();
		columnNames.add("Severity");
		columnNames.add("Board Info");
		columnNames.add("Time");

		tableModel = new CommonTableModel(columnNames);
		alarmTable = new JTable(tableModel);

		this.setLayout(new java.awt.BorderLayout());
		this.add(new JScrollPane(alarmTable), java.awt.BorderLayout.CENTER);
	}

	@Override
	public void push(Object o) {
		if (o instanceof BoardVo) {
			BoardVo boardVo = (BoardVo) o;
			String severity = String.valueOf(boardVo.getSeverity());
			String boardInfo = "Board " + boardVo.getBoardId();
			String time = java.time.LocalTime.now().toString();

			Vector<String> rowData = new Vector<>();
			rowData.add(severity);
			rowData.add(boardInfo);
			rowData.add(time);

			tableModel.addDataToTop(rowData);
			tableModel.fireTableDataChanged();
		}
	}

	public void addAlarmData(String severity, String boardInfo, String time) {
		Vector<String> rowData = new Vector<>();
		rowData.add(severity);
		rowData.add(boardInfo);
		rowData.add(time);

		tableModel.addDataToTop(rowData);
		tableModel.fireTableDataChanged();
	}

}
