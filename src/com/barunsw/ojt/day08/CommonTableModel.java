package com.barunsw.ojt.day08;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class CommonTableModel extends AbstractTableModel {
	private Vector columnData = new Vector();
	private Vector<Vector> rowData = new Vector<>();
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rowData.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnData.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Vector oneRow = rowData.get(rowIndex);
		return oneRow.get(columnIndex);
	}

}
