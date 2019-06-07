package com.barunsw.ojt.yjkim.day14;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

public class AgeCellRenderer extends JPanel
	implements TableCellRenderer {
	
	private JTextField jTextField_Age = new JTextField();
	
	
	public AgeCellRenderer() {
		initComponent();
	}
	
	private void initComponent() {
		this.setLayout(new GridBagLayout());

		this.add(jTextField_Age, new GridBagConstraints(
				0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5),
				0, 0));
		
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
		int age = 0;
		if (value instanceof Integer) {    			
			age = (int)value;
			jTextField_Age.setText(Integer.toString(age));
		}
		
    	if (!isSelected) {
    		if (age == 0) {
    			this.setBackground(Color.white);
    		}
    		else if (age <= 20) {
				this.setBackground(Color.yellow);	
			}
			else if (age <= 40) {
				this.setBackground(Color.orange);	
			}
			else {
				this.setBackground(Color.red);
			}
    	}	
    	else {
    		this.setBackground(UIManager.getColor("Table.selectionBackground"));
    	}
  
    	return this;
    }
}
