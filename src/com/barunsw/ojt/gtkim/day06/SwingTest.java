package com.barunsw.ojt.gtkim.day06;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SwingTest {
	private static final Logger LOGGER = LogManager.getLogger(SwingTest.class);

	public static void main(String[] args) {
		LOGGER.info("Swing Test Class");
		TestFrame frame   = new TestFrame();
	    TestDialog dialog = new TestDialog();
	    
		// 1) 표시할 위치 지정 이값을 기준으로 창이 생성
		//frame.setLocation(new Point(100, 100));
		// 2) 표시할 크기 지정
		//frame.setSize(new Dimension(1080, 720));
		//frame.setSize(1080,720)
		
		//화면의 전체 크기 받아오기
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//화면의 정중앙에 띄워지게 설정하는 방법
		int xPos = (scrSize.width - TestFrame.WIDTH) / 2;
		int yPos = (scrSize.height - TestFrame.HEIGHT) / 2;
		frame.setBounds(new Rectangle(xPos, yPos, 
				TestFrame.WIDTH, TestFrame.HEIGHT));
		dialog.setBounds(new Rectangle(200, 200,
				TestDialog.WIDTH, TestDialog.HEIGHT));
		
		dialog.setVisible(true);
		frame.setVisible(true);	
	}

}
