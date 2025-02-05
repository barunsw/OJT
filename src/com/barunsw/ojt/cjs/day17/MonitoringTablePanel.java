package com.barunsw.ojt.cjs.day17;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.cjs.common.BoardVo;
import com.barunsw.ojt.constants.Severity;

public class MonitoringTablePanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringTablePanel.class);

	private final int TABLE_COLUMN_SEVERITY = 0;

	private ServerInterface serverIf;
	BorderLayout border = new BorderLayout();
	private JTable jTable_List = new JTable();
	private JScrollPane jScrollPane_Table = new JScrollPane();
	private CommonTableModel tableModel = new CommonTableModel();

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);

	public MonitoringTablePanel(ServerInterface serverIf) {
		ClientMain.eventQueueWorker.addEventListener(this);
		this.serverIf = serverIf;
		try {
			initComponent();
			initTable();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void initComponent() {
		this.setLayout(new GridBagLayout());
		this.add(jScrollPane_Table, 
				new GridBagConstraints(0, 0, 1, 1,
						1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		jScrollPane_Table.getViewport().add(jTable_List);
	}

	private void initTable() {
		Vector<String> columnData = new Vector<>(); // 테이블의 컬럼값 저장
		columnData.add("Severity");
		columnData.add("BoardType");
		columnData.add("BoardName");
		columnData.add("EventTime");

		tableModel.setColumn(columnData);
		jTable_List.setModel(tableModel);
		jTable_List.getColumnModel().getColumn(TABLE_COLUMN_SEVERITY).setCellRenderer(new SeverityCellRenderer());
		jTable_List.setRowHeight(30);

	}

	@Override
	public void push(Object o) {
		if (o instanceof BoardVo) {
			BoardVo boardVo = (BoardVo) o;
			int severity = boardVo.getSeverity();
			int boardId = boardVo.getBoardId();
			Severity sev = Severity.items[severity];
			Vector inputData = new Vector();
			inputData.add(sev.toString());
			if (boardId < 2) {
				inputData.add("MPU");
				inputData.add("MPU");
			}
			else if (boardId % 18 == 0 || boardId == 36) {
				inputData.add("SRGU");
				inputData.add("SRGU");
			}
			else {
				inputData.add("SALC");
				inputData.add("SALC");
			}
			inputData.add(boardVo.getEventTime());
			tableModel.addData(inputData, 0);
		}
		tableModel.fireTableDataChanged();
	}

}
