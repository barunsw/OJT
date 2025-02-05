package com.barunsw.ojt.day08;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberCellEditor extends AbstractCellEditor implements TableCellEditor {
	private static final Logger LOGGER = LoggerFactory.getLogger(NumberCellEditor.class);
	
	private JSpinner jSpinner = new JSpinner();

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return jSpinner.getValue();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		LOGGER.debug("value:" + value);
		
		if (value instanceof JSpinner) {
			jSpinner = (JSpinner)value;
			
			Object prevValue = table.getValueAt(row, column);
			
			LOGGER.debug("prevValue:" + prevValue);
			
			jSpinner.setValue(prevValue);
		}
		
		return jSpinner;
	}
}
