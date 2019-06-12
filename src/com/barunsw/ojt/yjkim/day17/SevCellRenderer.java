package com.barunsw.ojt.yjkim.day17;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SevCellRenderer extends JPanel
	implements TableCellRenderer {
	private static final Logger LOGGER = LogManager.getLogger(SevCellRenderer.class);

	private JTextField jTextField_Sev = new JTextField();
	
	public SevCellRenderer() {
		initComponent();
	}
	
	private void initComponent() {
		this.setLayout(new GridBagLayout());

		this.add(jTextField_Sev, 
				new GridBagConstraints(1, 600, 1, 1,
						1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5),
				0, 0));
		
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
		LOGGER.debug("+++ getTableCellRendererComponent value " + value);
		String sev = "";
		if (value instanceof String) {  
			sev = (String)value;
			LOGGER.debug("sev :", sev);
			jTextField_Sev.setText(sev);
		}
		
		if (!isSelected) {
    		if (sev == "CRITICAL") {
    			this.setBackground(Color.RED);
    		}
    		else if (sev == "MAJOR") {
				this.setBackground(Color.orange);	
			}
			else if (sev == "MINOR") {
				this.setBackground(Color.yellow);	
			}
			else if (sev == "NORMAL") {
				this.setBackground(Color.white);
			}
		}
		LOGGER.debug("--- getTableCellRendererComponent");

    	return this;
    }
}
