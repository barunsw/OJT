package com.barunsw.ojt.mjg.day08;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class ButtonLayoutPanel extends JPanel {
	private JScrollPane jScrollPane = new JScrollPane();
	private JTable jTable = new JTable();
	private JTextArea jTextArea = new JTextArea();
	
	private JPanel jPanel_Command = new JPanel();
	
	private JButton jButton_Add = new JButton("추가");
	private JButton jButton_Delete = new JButton("삭제");
	private JButton jButton_Reload = new JButton("조회");
	private JButton jButton_Close = new JButton("닫기");
	
	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	private Dimension BUTTON_SIZE = new Dimension(100, 24);
	
	public ButtonLayoutPanel() {
		try {
			initComponent();
			initTable();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void initComponent() {
		this.setLayout(gridBagLayout);
		jPanel_Command.setLayout(gridBagLayout);
		
		jScrollPane.getViewport().add(jTable);
		
		jButton_Add.setPreferredSize(BUTTON_SIZE);
		jButton_Add.setMaximumSize(BUTTON_SIZE);
		jButton_Add.setMinimumSize(BUTTON_SIZE);

		jButton_Delete.setPreferredSize(BUTTON_SIZE);
		jButton_Delete.setMaximumSize(BUTTON_SIZE);
		jButton_Delete.setMinimumSize(BUTTON_SIZE);

		jButton_Reload.setPreferredSize(BUTTON_SIZE);
		jButton_Reload.setMaximumSize(BUTTON_SIZE);
		jButton_Reload.setMinimumSize(BUTTON_SIZE);

		jButton_Close.setPreferredSize(BUTTON_SIZE);
		jButton_Close.setMaximumSize(BUTTON_SIZE);
		jButton_Close.setMinimumSize(BUTTON_SIZE);
		
		this.add(jScrollPane, new GridBagConstraints(
				0, 0, 1, 1,
				1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5),
				0, 0
				));
		
		this.add(jPanel_Command, new GridBagConstraints(
				0, 1, 1, 1,
				1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0),
				0, 0
				));
		
		jPanel_Command.add(jButton_Add, new GridBagConstraints(
				0, 0, 1, 1,
				1.0, 1.0,
				GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
				new Insets(0, 5, 5, 5),
				0, 0
				));

		jPanel_Command.add(jButton_Delete, new GridBagConstraints(
				1, 0, 1, 1,
				0.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5),
				0, 0
				));

		jPanel_Command.add(jButton_Reload, new GridBagConstraints(
				2, 0, 1, 1,
				0.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5),
				0, 0
				));

		jPanel_Command.add(jButton_Close, new GridBagConstraints(
				3, 0, 1, 1,
				0.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5),
				0, 0
				));
	}
	
	private void initTable() {
		String[] columns = {"번호", "이름", "전화번호", "주소"};
		
		DefaultTableModel tableModel = new DefaultTableModel(columns, 1);
		tableModel.setColumnIdentifiers(columns);
		
		jTable.setModel(tableModel);
	}
}
