package com.barunsw.ojt.day08;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class TestTablePanel extends JPanel {
	private final int TABLE_CELL_ID_NAME 	= 0;
	private final int TABLE_CELL_ID_AGE 	= 1;
	
	private JPanel jPanel_Command = new JPanel();
	
	private JButton jButton_Reload = new JButton("조회");
	
	private JScrollPane jScrollPane_Result = new JScrollPane();
	private JTable jTable_Result = new JTable();
	
	private TestTableModel tableModel;
	
	private GridBagLayout gridBagLayout = new GridBagLayout();
	
//	private AddressBookInterface addressBookIf = new 
	
	public TestTablePanel() {
		try {
			initComponent();
			initTable();
			initData();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void initComponent() {
		this.setLayout(gridBagLayout);
		jPanel_Command.setLayout(gridBagLayout);
		
		jButton_Reload.setPreferredSize(new Dimension(120, 22));
		
		jPanel_Command.add(jButton_Reload, new GridBagConstraints(0, 0, 1, 1,
				1.0, 1.0,
				GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
				new Insets(5, 5, 5, 5),
				0, 0));
		
		this.add(jPanel_Command, new GridBagConstraints(0, 0, 1, 1,
				1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0),
				0, 0));
		
		this.add(jScrollPane_Result, new GridBagConstraints(0, 1, 1, 1,
				1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 5, 5, 5),
				0, 0));
		
		jScrollPane_Result.getViewport().add(jTable_Result);
		
		jButton_Reload.addActionListener(new TestTablePanel_jButton_Reload_ActionListener(this));
	}
	
	private void initTable() {
		Vector columns = new Vector();
		columns.add("이름");
		columns.add("나이");
		columns.add("성별");
		
		tableModel = new TestTableModel(columns);
		
		jTable_Result.setModel(tableModel);
		jTable_Result.setRowHeight(22);
		
		DefaultTableCellRenderer centerCellRenderer = new DefaultTableCellRenderer();
		centerCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		int columnCount = jTable_Result.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
            TableColumn tableColumn = jTable_Result.getColumnModel().getColumn(i);
//            tableColumn.getHeaderRenderer().;

            switch(i) {
			case TABLE_CELL_ID_NAME:
				tableColumn.setPreferredWidth(100);
				tableColumn.setCellRenderer(centerCellRenderer);
				break;
			case TABLE_CELL_ID_AGE:
				tableColumn.setPreferredWidth(100);
				tableColumn.setCellRenderer(centerCellRenderer);
				break;
			}
		}
	}
	
	private void initData() {
		Vector tableData = new Vector();
		
		Vector oneTableData = new Vector();
		oneTableData.add("홍길동");
		oneTableData.add("30");
		oneTableData.add("남");
		
		tableData.add(oneTableData);

		oneTableData = new Vector();
		oneTableData.add("유관순");
		oneTableData.add("20");
		oneTableData.add("여");
		
		tableData.add(oneTableData);

		tableModel.setData(tableData);		
	}
	
	void jButton_Reload_actionPerformed(ActionEvent e) {
		Vector tableData = new Vector();
		
		Vector oneTableData = new Vector();
		oneTableData.add("홍길동");
		oneTableData.add("30");
		oneTableData.add("남");
		
		tableData.add(oneTableData);

		oneTableData = new Vector();
		oneTableData.add("유관순");
		oneTableData.add("20");
		oneTableData.add("여");
		
		tableData.add(oneTableData);

		oneTableData = new Vector();
		oneTableData.add("조예빈");
		oneTableData.add("20");
		oneTableData.add("여");
		
		tableData.add(oneTableData);

		tableModel.setData(tableData);		
		tableModel.fireTableDataChanged();
	}
}

class TestTablePanel_jButton_Reload_ActionListener implements ActionListener {
	private TestTablePanel adaptee;
	
	public TestTablePanel_jButton_Reload_ActionListener(TestTablePanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		adaptee.jButton_Reload_actionPerformed(e);
	}
}