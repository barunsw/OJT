package com.barunsw.ojt.iwkim.day11;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel extends JPanel {
	private static Logger LOGGER = LogManager.getLogger(TestPanel.class);
	
	private String currentTime = "1900-01-01 00:00:00";

	private Font font = new Font("DialogInput", Font.PLAIN, 20);
	
	private Thread timeThread;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private JLabel jLabel_CurrentTime = new JLabel("현재 시간");
	private JTextField jTextField_CurrentTime = new JTextField();
	private JButton jButton_CurrentTime = new JButton("Current Time");
	
	
	public TestPanel() {
		try {
			initComponent();
			initThread();
			initEvent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		this.setLayout(new GridBagLayout());
		
		jTextField_CurrentTime.setMinimumSize(new Dimension(150, 22));
		
		this.add(jLabel_CurrentTime, new GridBagConstraints(
				0, 0, 1, 1,
				0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.BOTH,
				new Insets(5, 25, 5, 25),
				0, 0));
		
		this.add(jTextField_CurrentTime, new GridBagConstraints(
				1, 0, 1, 1,
				1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 0, 5, 5),
				0, 0));
		
		this.add(jButton_CurrentTime, new GridBagConstraints(
				2, 0, 1, 1,
				0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 0, 5, 5),
				0, 0));
		
	}
	
	private void initThread() {
		timeThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					try {
						Thread.sleep(1000);
					}
					catch (InterruptedException ie) {
						LOGGER.error(ie.getMessage(), ie);
					}
					
					currentTime = sdf.format(Calendar.getInstance().getTime());
					
					TestPanel.this.repaint();
				}
			}
			
		});
		
		timeThread.start();
	}
	
	private void initEvent() {
		jButton_CurrentTime.addActionListener(new TestPanel_jButton_CurrentTime_ActionListener(this));
	}
	
	void jButton_CurrentTime_actionPerformed(ActionEvent e) {
		LOGGER.info("Current Time 버튼 눌림" + e.getSource());
		
		jTextField_CurrentTime.setText(sdf.format(Calendar.getInstance().getTime()));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		LOGGER.debug("+++ paintComponent");
		
		g.setColor(Color.white);
//		g.drawLine(0,  0,  getWidth(), getHeight());
//		g.drawOval(100, 100, 200, 200);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.black);
		g.setFont(font);
		g.drawString(currentTime, 200, 200);
	}
}

class TestPanel_jButton_CurrentTime_ActionListener implements ActionListener {
	private TestPanel adaptee;
	
	public TestPanel_jButton_CurrentTime_ActionListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton_CurrentTime_actionPerformed(e);
	}
}






























