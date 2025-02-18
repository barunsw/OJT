package com.barunsw.ojt.mjg.day18;

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
		
		int xPos = (scrDim.width - frame.WIDTH) / 2;
		int yPos = (scrDim.height - frame.HEIGHT) / 2;
		
		// 1)과 2)를 동시에 처리
		frame.setBounds(new Rectangle(xPos, yPos, frame.WIDTH, frame.HEIGHT));
		// 3) 프레임 표시
		frame.setVisible(true);
	}
}
