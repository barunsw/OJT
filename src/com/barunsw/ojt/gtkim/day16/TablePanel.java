package com.barunsw.ojt.gtkim.day16;

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

import com.barunsw.ojt.vo.BoardVo;

public class TablePanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LogManager.getLogger(TablePanel.class);
	
	public static final int WIDTH 	 = 854;
	public static final int HEIGHT 	 = 604;
	
	public static final int TABLE_HEIGHT = 380;
	
	private int columnIdx = 0;
	private final int TABLE_COLUMN_ID		 = columnIdx++;
	private final int TABLE_COLUMN_TYPE		 = columnIdx++;
//	private final int TABLE_COLUMN_NAME		 = columnIdx++;
	private final int TABLE_COLUMN_SEVERITY	 = columnIdx++;
	private final int TABLE_COLUMN_STATE	 = columnIdx++;
	
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
		columnData.add("Type");
//		columnData.add("NAME");
		columnData.add("Severity");
		columnData.add("State");

		tableModel.setColumn(columnData);
		jTable_Tamms.setModel(tableModel);

		int columnCount = jTable_Tamms.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			TableColumn oneColumn = jTable_Tamms.getColumnModel().getColumn(i);

			if (i == TABLE_COLUMN_ID) {
				oneColumn.setMaxWidth(80);
			} 
			else if (i == TABLE_COLUMN_TYPE) {
				oneColumn.setMaxWidth(150);
			} 
			else if (i == TABLE_COLUMN_SEVERITY) {
				oneColumn.setMaxWidth(80);
			}
		}

//		jTable_Tamms.getColumnModel().getColumn(TABLE_COLUMN_SEVERITY)
//		.setCellRenderer(new SeverityTableCellRenderer());	
//		
		jTable_Tamms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		jTable_Tamms.setRowHeight(38);
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
	
	private String getString(int severity) {
		switch (severity) {
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
	
	public void close() {
		ClientMain.eventQueueWorker.removeEventListener(this);
	}
	
	@Override
	public void push(Object o) {
		if (o instanceof AlarmVo) {
			AlarmVo alarmVo = (AlarmVo)o;
			// id가 곧 index
			LOGGER.debug(String.format("AlarmVo id : %d, severity : %d table_id : %d : table_severity : %d table_state : %s" ,
					alarmVo.getBoardId(), alarmVo.getSeverity(),
					tableModel.getValueAt(alarmVo.getBoardId(), TABLE_COLUMN_ID),
					tableModel.getValueAt(alarmVo.getBoardId(), TABLE_COLUMN_SEVERITY),
					 tableModel.getValueAt(alarmVo.getBoardId(), TABLE_COLUMN_STATE)));
			
			tableModel.setValueAt(
					alarmVo.getSeverity(), alarmVo.getBoardId(), TABLE_COLUMN_SEVERITY);
			tableModel.setValueAt(
					getString(alarmVo.getSeverity()), alarmVo.getBoardId(), TABLE_COLUMN_STATE);	
			
			tableModel.fireTableDataChanged();
		}	
	}
}
