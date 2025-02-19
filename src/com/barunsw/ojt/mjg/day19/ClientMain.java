package com.barunsw.ojt.mjg.day19;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.UIManager;

public class ClientMain {
	
	public static EventQueueWorker<Object> eventQueueWorker = new EventQueueWorker();
	
	public static void main(String[] args) {
		try {
			// Look and Feel UIManager를 통해 ui 쉽게 변경 가능
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception ex) {
		}
		
		eventQueueWorker.start();
		
		MainFrame frame = new MainFrame();
		
		// 화면의 전체 크기
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xPos = (scrDim.width - MainFrame.WIDTH) / 2;
		int yPos = (scrDim.height - MainFrame.HEIGHT) / 2;
		
		// 1)과 2)를 동시에 처리
		frame.setBounds(new Rectangle(xPos, yPos, MainFrame.WIDTH, MainFrame.HEIGHT));
		// 3) 프레임 표시
		frame.setVisible(true);
	}
}
