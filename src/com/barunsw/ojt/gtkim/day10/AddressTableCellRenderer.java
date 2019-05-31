package com.barunsw.ojt.gtkim.day10;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

import com.barunsw.ojt.constants.Gender;

public class AddressTableCellRenderer extends JPanel implements TableCellRenderer {

	private JTextField jTextField_Gender = new JTextField();
	
	public AddressTableCellRenderer() {
		initComponent();
	}
	
	private void initComponent() {
		this.setLayout(new GridBagLayout());

		this.add(jTextField_Gender, new GridBagConstraints(
				0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5),
				0, 0));
		
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Gender gender;
		if (value instanceof Gender) {
			gender = (Gender) value;
			jTextField_Gender.setText(gender.toString());
			
			if(!isSelected) {
				if (gender == Gender.MAN) {
					this.setBackground(Color.blue);
				}
				else if (gender == Gender.WOMAN) {
					this.setBackground(Color.red);
				}
				else {
					this.setBackground(Color.black);
				}
			}
			else {
				this.setBackground(Color.white);
			}
		}
		return this;
	}
}
