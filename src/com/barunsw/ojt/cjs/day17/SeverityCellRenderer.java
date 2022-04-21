package com.barunsw.ojt.cjs.day17;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeverityCellRenderer extends JPanel implements TableCellRenderer {
	private static final Logger LOGGER = LoggerFactory.getLogger(SeverityCellRenderer.class);
	private JLabel jLabel_Severity = new JLabel();

	public SeverityCellRenderer() {
		initComponent();
	}

	private void initComponent() {
		this.setLayout(new BorderLayout());
		this.add(jLabel_Severity);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		String serverity = null;
		if (value instanceof String) {
			serverity = (String) value;
			jLabel_Severity.setText(serverity);
		}
		if (serverity.equals("CRITICAL")) {
			jLabel_Severity.setForeground(Color.black);
			this.setBackground(Color.RED);
		}
		else if (serverity.equals("MAJOR")) {
			jLabel_Severity.setForeground(Color.black);
			this.setBackground(Color.ORANGE);
		}
		else if (serverity.equals("MINOR")) {
			jLabel_Severity.setForeground(Color.black);
			this.setBackground(Color.YELLOW);
		}
		else {
			jLabel_Severity.setForeground(Color.black);
			this.setBackground(Color.WHITE);
		}
		return this;
	}

}
