package com.barunsw.ojt.iwkim.day09;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class CommonTableModel extends AbstractTableModel {
	private Vector<String> columnData = new Vector<>();
	private Vector<Vector> data = new Vector<>();
	
	public void setColumnData(Vector<String> columnData) {
		this.columnData = columnData;
	}
	
	public void setData(Vector<Vector> data) {
		this.data = data;
	}
	
	@Override
	public String getColumnName(int index) {
		if (index < columnData.size()) {
			return columnData.get(index);
		}
		
		return null;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnData.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if (rowIndex < getRowCount() && columnIndex < getColumnCount()) {
			return data.get(rowIndex).get(columnIndex);
		}
		
		return null;
	}

}
