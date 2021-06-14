package com.barunsw.ojt.iwkim.day11;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel extends JPanel {
	private static Logger LOGGER = LogManager.getLogger(TestPanel.class);
	
	private String currentTime = "1900-01-01 00:00:00";

	private Font font = new Font("DialogInput", Font.PLAIN, 20);
	
	private Thread timeThread;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public TestPanel() {
		try {
			initComponent();
			initThread();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		
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
					catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
					
					currentTime = sdf.format(Calendar.getInstance().getTime());
					
					TestPanel.this.repaint();
				}
			}
			
		});
		
		timeThread.start();
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
