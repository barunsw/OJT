package com.barunsw.ojt.cjs.day07;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.day07.CommonTableModel;

public class TablePanel extends JPanel{

	private static final Logger log = LoggerFactory.getLogger(TablePanel.class);
	private JTable jTable = new JTable();
	private CommonTableModel tableModel;

	private GridBagLayout gridBagLayout = new GridBagLayout();

	public TablePanel() {
		try {
			initComponent();
			initTable();
			initData();
		} 
		catch (Exception e) {
			log.error(e.getMessage() + e);
		}
	}
	private void initComponent() {
		this.setLayout(gridBagLayout);
		
		this.add(jTable, new GridBagConstraints(
				0,0,1,1, //x좌표, y좌표, 너비, 높이
				1.0,1.0, //가로축비율, 세로축 비율 
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, //추가될 위치, 크기 재설정
				new Insets(5, 5,5, 5), // 상하좌우 여백
				0,0 // 너비, 높이 *2 픽셀 적용
				));
	}
	private void initData() {
		Vector<Vector> dataInfo = new Vector<>();
		
		Vector<String> oneRow = new Vector();
		oneRow.add("1");
		oneRow.add("최재석");
		oneRow.add("28");
		oneRow.add("경기도");
		
		dataInfo.add(oneRow);
		
		tableModel.addData(oneRow);
	}
	private void initTable() {
		Vector columnInfo = new Vector();
		columnInfo.add("번호");
		columnInfo.add("이름");
		columnInfo.add("나이");
		columnInfo.add("주소");
		
		tableModel = new CommonTableModel(columnInfo);
		jTable.setModel(tableModel);
		
		jTable.setRowHeight(22);
	}

}
