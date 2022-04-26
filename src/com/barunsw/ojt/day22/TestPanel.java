package com.barunsw.ojt.day22;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class TestPanel extends JPanel {
	private JButton jButton_Popup = new JButton("다이얼로그");
	public TestPanel() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void initComponent() {
		this.setLayout(new GridBagLayout());
		
		jButton_Popup.setPreferredSize(new Dimension(120, 22));
		
		this.add(jButton_Popup, new GridBagConstraints(
				0, 0, 1, 1,
				0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5),
				0, 0
				));
		
		jButton_Popup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPopup();
			}
		});
	}
	
	private void showPopup() {
		ConnectSetDialog.showDialog(DialogMain.frame, ConnectSetType.ADD);
	}
}
