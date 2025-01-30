package com.barunsw.ojt.gtkim.day16;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

public class SeverityTableCellRenderer extends JPanel implements TableCellRenderer {

	private JLabel jLabel_Severity = new JLabel();

	public SeverityTableCellRenderer() {
		initComponent();
	}
	
	private void initComponent() {
		this.setLayout(new GridBagLayout());
		
		this.add(jLabel_Severity, new GridBagConstraints(
				0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5),
				0, 0));
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		int severity = 3;
		if (value instanceof Integer) {    			
			severity = (int)value;
			jLabel_Severity.setText(Integer.toString(severity));
		}
		
		if (!isSelected) {
    		switch (severity) {
    		case 0:
    			this.setBackground(Color.red);
    			break;
    		case 1 :
    			this.setBackground(Color.orange);
    			break;
    		case 2 :
    			this.setBackground(Color.yellow);
    			break;
    		case 3 :
    		default :
    			
    		}
    	}	
		else {
    		this.setBackground(UIManager.getColor("Table.selectionBackground"));
    	}

		return this;
	}

}
