package com.barunsw.ojt.day08;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class TestTableModel extends AbstractTableModel {
	private Vector<String> columns;
	private Vector data;
	
	public TestTableModel(Vector<String> columns) {
		this.columns = columns;
	}
	
	public void setColumns(Vector<String> columns) {
		this.columns = columns;
	}
	
	public void setData(Vector data) {
		this.data = data;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return (data != null)?data.size():0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columns.size();
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return (columns != null)?columns.get(columnIndex):null;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return (data != null)?((Vector)data.get(rowIndex)).get(columnIndex):null;
	}
}
