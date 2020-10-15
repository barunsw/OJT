package com.barunsw.ojt.phs.day16.rmi;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class CommonTableModel extends AbstractTableModel {
	protected Vector columnInfo;
    protected Vector dataInfo;
    protected Vector cellEditableInfo = new Vector();

    public CommonTableModel() {
        columnInfo = new Vector();
        dataInfo = new Vector();
    }

    public void addData(Vector oneRow) {
        dataInfo.addElement(oneRow);
    }

    public void changeData(Vector oneRow, int row) {
        dataInfo.setElementAt(oneRow, row);
    }

    public void removeData(int index) {
        dataInfo.removeElementAt(index);
    }

    public void setData(Vector dataInfo) {
        this.dataInfo = dataInfo;
    }

    public Vector getData() { 
    	return dataInfo;
    }
    
    public void setColumn(Vector columnInfo) {
        this.columnInfo = columnInfo;
    }

    public int getColumnCount() {
        if (columnInfo != null)
            return columnInfo.size();
        else
            return 0;
    }

    public void setNumRows(int rowCount) {
        int old = getRowCount();
        if (old == rowCount) {
            return;
        }
        dataInfo.setSize(rowCount);
        if (rowCount <= old) {
            fireTableRowsDeleted(rowCount, old-1);
        }
        else {
            justifyRows(old, rowCount);
            fireTableRowsInserted(old, rowCount-1);
        }
    }
    
    public void setValueAt(Object value, int row, int column) {
    	if ( row < getRowCount() ) {
    		Object o = dataInfo.elementAt(row);
    		if (o instanceof Vector) {
    			Vector oneRow = (Vector) o;
    			oneRow.setElementAt(value, column);
    		}
    	}
    }

    public Object getValueAt(int row, int column) {
//    if ( row < getRowCount() && column < getColumnCount() ) {
        if (row < getRowCount()) {
            Object object = dataInfo.elementAt(row);
            if (object != null && object instanceof Vector) {
                Vector rowInfo = (Vector) object;
                
                if ( column < rowInfo.size() )
                	return rowInfo.elementAt(column);
            }
        }

        return null;
    }

    public int getRowCount() {
        if (dataInfo != null)return dataInfo.size();
        else return 0;
    }

    public String getColumnName(int column) {
        return columnInfo.elementAt(column).toString();
    }

    public Class getColumnClass(int column) {
    	Object valObject = getValueAt(0, column);
    	if ( valObject == null ) {
    		return String.class;
    	}
    	else {
    		return valObject.getClass();
    	}
    }

    public void setCellEditable(int column) {
        cellEditableInfo.addElement(new Integer(column));
    }

    public boolean isCellEditable(int row, int column) {
        Enumeration enumList = cellEditableInfo.elements();
        while (enumList.hasMoreElements()) {
            Object o = enumList.nextElement();
            if (o instanceof Integer) {
                Integer col = (Integer) o;

                if (col.intValue() == column)return true;
            }
        }
        return false;
    }
    
    private void justifyRows(int from, int to) {
        dataInfo.setSize(getRowCount());

        for (int i = from; i < to; i++) {
            if (dataInfo.elementAt(i) == null) {
            	dataInfo.setElementAt(new Vector(), i);
            }
            ((Vector)dataInfo.elementAt(i)).setSize(getColumnCount());
        }
    }

}
