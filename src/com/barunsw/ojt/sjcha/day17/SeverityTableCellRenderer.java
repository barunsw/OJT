package com.barunsw.ojt.sjcha.day17;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SeverityTableCellRenderer extends JPanel implements TableCellRenderer {
	private static final Logger LOGGER = LogManager.getLogger(SeverityTableCellRenderer.class);

	private JLabel jLabel_Severity = new JLabel();

	public SeverityTableCellRenderer() {
		initComponent();
	}

	private void initComponent() {
		this.setLayout(new GridBagLayout());

		this.add(jLabel_Severity, 
				new GridBagConstraints(0, 0,
						1, 1, 1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
						new Insets(0, 0, 0, 0),
						0, 0));
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		String severity = "";

		if (value instanceof String) {
			severity = (String) value;
			jLabel_Severity.setText(severity);
		}

		switch (severity) {
		case "CRITICAL":
			this.setBackground(Color.red);
			jLabel_Severity.setForeground(Color.white);
			break;
		case "MAJOR":
			this.setBackground(Color.orange);
			jLabel_Severity.setForeground(Color.white);
			break;
		case "MINOR":
			this.setBackground(Color.yellow);
			jLabel_Severity.setForeground(Color.black);
			break;
		default:
			this.setBackground(UIManager.getColor("Table.background"));
			break;
		}

		return this;
	}
}
