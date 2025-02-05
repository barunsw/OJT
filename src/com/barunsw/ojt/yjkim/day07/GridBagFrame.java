package com.barunsw.ojt.yjkim.day07;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GridBagFrame extends JFrame{
	private static final Logger LOGGER = LogManager.getLogger(GridBagFrame.class);

	public static final int WIDTH 	= 600;
	public static final int HEIGHT 	= 600;
	private GridBagPanel gridbagpanel = new GridBagPanel();
	
	public GridBagFrame() {
		try {
			initComponent();
			
		}catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	void initComponent() throws Exception {
		this.setTitle("GridBagLayout 테스트");
		this.setContentPane(gridbagpanel);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new GridBagFrame_this_WindowAdapter(this));
		
	}
	
	void windowClosing(WindowEvent e) {
		int result = JOptionPane.showConfirmDialog(GridBagFrame.this, 
				"종료하시겠습니까", "종료", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}else {
			JOptionPane.showMessageDialog(GridBagFrame.this, "취소되었습니다");
		}
	}
}

class GridBagFrame_this_WindowAdapter extends WindowAdapter{
	private GridBagFrame adaptee;
	
	public GridBagFrame_this_WindowAdapter(GridBagFrame adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		adaptee.windowClosing(e);
	}
}
