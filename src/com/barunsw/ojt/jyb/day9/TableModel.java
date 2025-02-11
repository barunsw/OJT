package com.barunsw.ojt.jyb.day9;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
	private Vector<String> columns; // 열
	private Vector<Object> data; // 행

	public TableModel(Vector<String> columns) {
		this.columns = columns;
	}

	public void setColumns(Vector<String> columns) {
		this.columns = columns;
	}

	public void setData(Vector<Object> data) {
		this.data = data;
	}

	@Override
	public int getRowCount() {
		return (data != null) ? data.size() : 0;
	}

	@Override
	public int getColumnCount() {
		return columns.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return (columns != null) ? columns.get(columnIndex) : null;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return (data != null) ? ((Vector<Object>) data.get(rowIndex)).get(columnIndex) : null;
	}

}
