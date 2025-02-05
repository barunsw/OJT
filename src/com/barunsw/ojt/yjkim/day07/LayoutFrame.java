package com.barunsw.ojt.yjkim.day07;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LayoutFrame extends JFrame{
	private static final Logger LOGGER = LogManager.getLogger(LayoutFrame.class);

	public static final int WIDTH  = 400;
	public static final int HEIGHT = 600;
	
	private LayoutPanel layoutpanel = new LayoutPanel();
	
	public LayoutFrame() {
		try {
			initComponent();
		}catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	void initComponent() {
		this.setContentPane(layoutpanel);
		this.setTitle("레이아웃 Test");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new LayoutFrame_this_WindowAdatper(this));
	}

	void windowClosing(WindowEvent e) {
		LOGGER.debug("windowClosing....");
		
		int result = JOptionPane.showConfirmDialog(LayoutFrame.this, "종료하시겠습니까", 
				"종료", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}else {
			JOptionPane.showMessageDialog(LayoutFrame.this, "취소하였습니다");
		}
	}
}


class LayoutFrame_this_WindowAdatper extends WindowAdapter{
	
	private LayoutFrame adaptee;
	
	public LayoutFrame_this_WindowAdatper(LayoutFrame adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		adaptee.windowClosing(e);
	}
}
