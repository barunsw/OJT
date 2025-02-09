package day9;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class CommonTableModel extends AbstractTableModel {
	protected Vector columnInfo; // 열 정보
	protected Vector dataInfo; // 데이터 정보
	protected Vector cellEditableInfo = new Vector();

	public CommonTableModel() { // 기본 생성자
		columnInfo = new Vector();
		dataInfo = new Vector();
	}

	public CommonTableModel(Vector columnInfo) { // 열 정보를 받아 생성
		this.columnInfo = columnInfo;
		dataInfo = new Vector();
	}

	public CommonTableModel(Vector columnInfo, Vector dataInfo) { //열 정보와 데이터 정보를 받아 생성
		this.columnInfo = columnInfo;
		this.dataInfo = dataInfo;
	}

	public void addData(Vector oneRow) { //행 추가
		dataInfo.addElement(oneRow);
	}

	public void addDataToTop(Vector oneRow) { //행을 맨 위에 추가
		dataInfo.add(0, oneRow);
	}

	public void addData(Vector oneRow, int index) { //특정 인덱스에 데이터 행 추가
		dataInfo.add(index, oneRow);
	}

	public void changeData(Vector oneRow, int row) { //특정 행 데이터 변경
		dataInfo.setElementAt(oneRow, row);
	}

	public void removeData(int index) { //특정 인덱스의 데이터 삭제
		dataInfo.removeElementAt(index);
	}

	public void setData(Vector dataInfo) { //데이터 정보 저장
		this.dataInfo = dataInfo;
	}

	public Vector getData() { //데이터 정보 반환
		return dataInfo;
	}

	public void setColumn(Vector columnInfo) { //열 정보 저장
		this.columnInfo = columnInfo;
	}

	public int getColumnCount() { //열 개수 반환
		if (columnInfo != null)
			return columnInfo.size();
		else
			return 0;
	}

	public void setNumRows(int rowCount) { //행 개수 설정
		int old = getRowCount();
		if (old == rowCount) {
			return;
		}
		dataInfo.setSize(rowCount);
		if (rowCount <= old) {
			fireTableRowsDeleted(rowCount, old - 1);
		} else {
			justifyRows(old, rowCount);
			fireTableRowsInserted(old, rowCount - 1);
		}
	}

	public void setValueAt(Object value, int row, int column) { //특정 셀의 값 설정
		if (row < getRowCount()) {
			Object o = dataInfo.elementAt(row);
			if (o instanceof Vector) {
				Vector oneRow = (Vector) o;
				oneRow.setElementAt(value, column);
			}
		}
	}

	public Object getValueAt(int row, int column) { //특정 셀의 값 반환
//	    if ( row < getRowCount() && column < getColumnCount() ) {
		if (row < getRowCount()) {
			Object object = dataInfo.elementAt(row);
			if (object != null && object instanceof Vector) {
				Vector rowInfo = (Vector) object;

				if (column < rowInfo.size())
					return rowInfo.elementAt(column);
			}
		}

		return null;
	}

	public int getRowCount() { //행의 개수 반환
		if (dataInfo != null)
			return dataInfo.size();
		else
			return 0;
	}

	public String getColumnName(int column) { //열의 이름 반환
		return columnInfo.elementAt(column).toString();
	}

	public Class getColumnClass(int column) { //열의 데이터 타입 반환
		Object valObject = getValueAt(0, column);
		if (valObject == null) {
			return String.class;
		} else {
			return valObject.getClass();
		}
	}

	public void setCellEditable(int column) { //편집 가능한 셀 설정
		cellEditableInfo.addElement(new Integer(column));
	}

	public boolean isCellEditable(int row, int column) { //편집 가능 여부 반환
		Enumeration enumList = cellEditableInfo.elements();
		while (enumList.hasMoreElements()) {
			Object o = enumList.nextElement();
			if (o instanceof Integer) {
				Integer col = (Integer) o;

				if (col.intValue() == column)
					return true;
			}
		}
		return false;
	}

	private void justifyRows(int from, int to) { //행 크기 조정
		dataInfo.setSize(getRowCount());

		for (int i = from; i < to; i++) {
			if (dataInfo.elementAt(i) == null) {
				dataInfo.setElementAt(new Vector(), i);
			}
			((Vector) dataInfo.elementAt(i)).setSize(getColumnCount());
		}
	}
}