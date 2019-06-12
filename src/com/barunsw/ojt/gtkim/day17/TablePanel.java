package com.barunsw.ojt.gtkim.day17;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.vo.AlarmVo;
import com.barunsw.ojt.vo.BoardVo;

public class TablePanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LogManager.getLogger(TablePanel.class);
	
	public static final int WIDTH 	 = 854;
	public static final int HEIGHT 	 = 604;
	
	public static final int TABLE_HEIGHT = 380;
	
	private int columnIdx = 0;
	private final int TABLE_COLUMN_ALARMID	 = columnIdx++;
	private final int TABLE_COLUMN_SEVERITY	 = columnIdx++;
	private final int TABLE_COLUMN_MESSAGE	 = columnIdx++;
	private final int TABLE_COLUMN_LOCATE	 = columnIdx++;
	private final int TABLE_COLUMN_TIME 	 = columnIdx++;
	
	private CommonTableModel tableModel = new CommonTableModel();
	private JTable jTable_Tamms 		= new JTable();
	
	private JScrollPane jScrollPane_Table = new JScrollPane();
	
	private List<BoardVo> boardList = new ArrayList<>();
	
	public TablePanel() {
		LOGGER.debug("Table Panel create!");
		
		try {
			initTable();
			initComponent();
		}
		catch (Exception ex) { 
			
		}
	}
	
	private void initTable() {
		Vector<String> columnData = new Vector<>();

		columnData.add("ID");
		columnData.add("Sev");
		columnData.add("Msg");
		columnData.add("Loc");
		columnData.add("Time");

		tableModel.setColumn(columnData);
		jTable_Tamms.setModel(tableModel);

		int columnCount = jTable_Tamms.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			TableColumn oneColumn = jTable_Tamms.getColumnModel().getColumn(i);

			if (i == TABLE_COLUMN_ALARMID) {
				oneColumn.setMaxWidth(80);
			} 
			else if (i == TABLE_COLUMN_SEVERITY) {
				oneColumn.setMaxWidth(100);
			} 
		}

	jTable_Tamms.getColumnModel().getColumn(TABLE_COLUMN_SEVERITY)
		.setCellRenderer(new SeverityTableCellRenderer());	
	
		jTable_Tamms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		jTable_Tamms.setRowHeight(35);
	}
	
	private void initComponent() {
		this.setLayout(new GridBagLayout());
		jScrollPane_Table.getViewport().add(jTable_Tamms);
		
		this.add(jScrollPane_Table,
				new GridBagConstraints(0, 0, 1, 1,
						1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		ClientMain.eventQueueWorker.addEventListener(this);
	}
	
	public void updateData(List<BoardVo> boardList) {
		this.boardList = boardList;
		
		for (BoardVo oneBoardVo : boardList) {		
			// Table data init
			Vector rowdata = new Vector();
			rowdata.add(oneBoardVo.getBoardId());
			rowdata.add(oneBoardVo.getBoardType());
			rowdata.add(oneBoardVo.getSeverity());
			rowdata.add(getString(oneBoardVo.getSeverity()));
			tableModel.addData(rowdata);
		}
		tableModel.fireTableDataChanged();
	}
	
	private String getString(Object o) {
		if (o instanceof Integer) {
			int i = (int)o;
			switch (i) {
			case 0 :
				return "CRITICAL";
			case 1 :
				return "MAJOR";
			case 2 :
				return "MINOR";
			case 3:
				return "NORMAL";
			default :
				return "NULL";
			}
		}
		
		if (o instanceof String) {
			String id = (String)o;
			switch (id) {			
			case "1000" :
				return "BOARD";
			case "2000" :
				return "PORT";
			case "3000" :
				return "ENV";
				
			default :
				return "NULL";
			}
		}
		
		return null;
	}
	
	public void close() {
		ClientMain.eventQueueWorker.removeEventListener(this);
	}
	
	@Override
	public void push(Object o) {
		if (o instanceof AlarmVo) {
			AlarmVo alarmVo = (AlarmVo)o;
			
			Vector rowdata = new Vector();
			rowdata.add(getString(alarmVo.getAlarmId()));
			rowdata.add(getString(alarmVo.getSeverity()));
			rowdata.add(alarmVo.getAlarmMsg());
			rowdata.add(alarmVo.getAlarmLoc());
			rowdata.add(alarmVo.getEventTime());
			tableModel.addData(rowdata, 0);
			
			tableModel.fireTableDataChanged();
		}	
	}
}
