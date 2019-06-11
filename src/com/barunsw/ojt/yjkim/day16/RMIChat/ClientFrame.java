package com.barunsw.ojt.yjkim.day16.RMIChat;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class ClientFrame extends JFrame {
	private static final Logger LOGGER = LogManager.getLogger(ClientFrame.class);

	public static final int WIDTH 	= 600;
	public static final int HEIGHT 	= 600;
	private ClientTestPanel clientTestPanel = new ClientTestPanel();
	
	public ClientFrame() {
		try {
			initComponent();
			
		}catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	void initComponent() throws Exception {
		this.setTitle("RMI 채팅 테스트");
		this.setContentPane(clientTestPanel);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new ClientFrame_this_WindowAdapter(this));
		
	}
	
	void windowClosing(WindowEvent e) {
		int result = JOptionPane.showConfirmDialog(ClientFrame.this, 
				"종료하시겠습니까", "종료", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}else {
			JOptionPane.showMessageDialog(ClientFrame.this, "취소되었습니다");
		}
	}
}

class ClientFrame_this_WindowAdapter extends WindowAdapter{
	private ClientFrame adaptee;
	
	public ClientFrame_this_WindowAdapter(ClientFrame adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		adaptee.windowClosing(e);
	}
}
