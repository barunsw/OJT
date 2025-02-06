package com.barunsw.ojt.day07;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableTestPanel extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(TableTestPanel.class);

	private JScrollPane jScrollPane_Table = new JScrollPane();
	private JTable jTable_Test = new JTable();
	private CommonTableModel tableModel;
	
	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	public TableTestPanel() {
		try {
			initComponent();
			initTable();
			initData();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		this.setLayout(gridBagLayout);
		
		this.add(jScrollPane_Table, new GridBagConstraints(
				0, 0, 1, 1,
				1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5),
				0, 0
				));
		
		jScrollPane_Table.getViewport().add(jTable_Test);
	}
	
	private void initTable() {
		Vector columnInfo = new Vector();
		columnInfo.add("번호");
		columnInfo.add("이름");
		columnInfo.add("나이");
		columnInfo.add("주소");
		
		tableModel = new CommonTableModel(columnInfo);
		jTable_Test.setModel(tableModel);
		
		jTable_Test.setRowHeight(22);
	}
	
	private void initData() {
		Vector<Vector> dataInfo = new Vector<>();
		
		Vector<String> oneRow = new Vector();
		oneRow.add("1");
		oneRow.add("홍길동");
		oneRow.add("30");
		oneRow.add("서울");
		
		dataInfo.add(oneRow);
		
		oneRow = new Vector();
		oneRow.add("2");
		oneRow.add("유관순");
		oneRow.add("20");
		oneRow.add("경기");
		
		dataInfo.add(oneRow);
		
		tableModel.setData(dataInfo);
	}
}
