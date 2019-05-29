package com.barunsw.ojt.day08;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);

	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	private JScrollPane jScrollPane_Table = new JScrollPane();
	
	private JTable jTable_Result = new JTable();
	private DefaultTableModel tableModel = new DefaultTableModel();
	private CommonTableModel commonTableModel = new CommonTableModel();
	
	public TestPanel() {
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

		this.add(jScrollPane_Table, 
				new GridBagConstraints(0, 0, 1, 1,
						1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		jScrollPane_Table.getViewport().add(jTable_Result);
	}
	
	private void initTable() {
		Vector<String> columnData = new Vector<>();
		columnData.add("번호");
		columnData.add("이름");
		columnData.add("나이");
		columnData.add("주소");
		
		tableModel.setColumnIdentifiers(columnData);

		jTable_Result.setModel(tableModel);
	}
	
	private void initData() {
		Vector oneData = new Vector();
		oneData.add("1");
		oneData.add("홍길동");
		oneData.add(30);
		oneData.add("서울시");
		
		tableModel.addRow(oneData);
		
	}
}
