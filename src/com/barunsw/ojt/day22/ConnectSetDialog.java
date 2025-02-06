package com.barunsw.ojt.day22;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class ConnectSetDialog extends JDialog {
	private static ConnectSetDialog dialog;
	
	private JPanel jPanel_Main = new JPanel();
	
	public ConnectSetDialog(Frame frame, String title, ModalityType modalType) {
		super(frame, title, modalType);
		
		try {
			initComponent();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void initComponent() {
		this.setContentPane(jPanel_Main);
		
		
	}
	
	private void clearForm() {
		
	}
	
	public static void showDialog(Frame frame, ConnectSetType setType) {
		String title = "";
		if (setType == ConnectSetType.ADD) {
			title = "연결 생성";
		}
		else if (setType == ConnectSetType.UPDATE) {
			title = "연결 수정";
		}
		
		if (dialog == null) {
			dialog = new ConnectSetDialog(frame, title, ModalityType.APPLICATION_MODAL);
		}
		else {
			dialog.setTitle(title);
		}
		
		dialog.setBounds(0, 0, 300, 600);
		dialog.setVisible(true);
	}
}
