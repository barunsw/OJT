package com.barunsw.ojt.phs.day11;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProgressDialog extends JDialog implements PropertyChangeListener {
	private static final Logger LOGGER = LogManager.getLogger(ProgressDialog.class);
	
	public static final int WIDTH 	= 360;
	public static final int HEIGHT 	= 120;
	
	private JPanel jPanel_Main = new JPanel();
	private JPanel jPanel_Command = new JPanel();
	
	private JProgressBar jProgressBar = new JProgressBar();

	private JButton jButton_Close = new JButton("닫기");
	
	public ProgressDialog(Window window) {
		super(window, "진행중", ModalityType.APPLICATION_MODAL);
		
		try {
			initComponent();
			
			Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
			
			int xPos = (scrDim.width - WIDTH) / 2;
			int yPos = (scrDim.height - HEIGHT) / 2;
			
			setBounds(xPos, yPos, WIDTH, HEIGHT);
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	private void initComponent() throws Exception {
		this.setContentPane(jPanel_Main);
		
		jProgressBar.setStringPainted(true);
		
		jPanel_Main.setLayout(new GridBagLayout());
		jPanel_Command.setLayout(new GridBagLayout());
		
		jPanel_Main.add(jProgressBar, new GridBagConstraints(0, 0, 1, 1, 
				1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5),
				0, 0));
		jPanel_Main.add(jPanel_Command, new GridBagConstraints(0, 1, 1, 1, 
				1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 5, 5, 5),
				0, 0));
		
		jPanel_Command.add(jButton_Close, new GridBagConstraints(0, 0, 1, 1, 
				1.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
				new Insets(0, 5, 5, 5),
				0, 0));
		
		jButton_Close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
	}
	
	@Override
	public void setVisible(boolean flag) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				ProgressDialog.super.setVisible(flag);
				LOGGER.debug("dialog.setVisible");				
			}
		});
		t.start();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer)evt.getNewValue();
			jProgressBar.setValue(progress);
		}
	}
}
