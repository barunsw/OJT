package com.barunsw.ojt.yjkim.day16.RMIChat;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.yjkim.day14.TestFrame;

public class ClientMain {
	private static final Logger LOGGER = LogManager.getLogger(ClientMain.class);
	
	
	public static void main(String[] args) {
		try {
	
			
			
			ClientFrame frame = new ClientFrame();
			Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
			
			int xPos = (scrDim.width - TestFrame.WIDTH) / 2;
			int yPos = (scrDim.height - TestFrame.HEIGHT) / 2;
			
			// 1)과 2)를 동시에 처리
			frame.setBounds(new Rectangle(xPos, yPos, TestFrame.WIDTH, TestFrame.HEIGHT));
			// 3) 프레임 표시
			frame.setVisible(true);
			/*ClientTestPanel clientTestPanel = new ClientTestPanel();
			clientTestPanel.push(clientIf);*/
		} 
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
