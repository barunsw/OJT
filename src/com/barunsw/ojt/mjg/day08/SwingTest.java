package com.barunsw.ojt.mjg.day08;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class SwingTest {
	private static Logger LOGGER = LoggerFactory.getLogger(SwingTest.class);
	
	public static void main(String[] args) {
		//JFrame testFrame = new JFrame();
		//testFrame.setLocation(new Point(100, 100));
		//testFrame.setSize(new Dimension(600, 480));
		//testFrame.setBounds(new Rectangle(100, 100, 600, 480));
		
		
//		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
//		
//		int xPos = (scrDim.width - 600) / 2;
//		int yPos = (scrDim.height - 480) / 2;
//		
//		testFrame.setLocation(new Point(xPos, yPos));
//		testFrame.setSize(new Dimension(600, 480));
//		
//		testFrame.getContentPane().setLayout(new FlowLayout());
//		testFrame.getContentPane().add(new JButton("조회"));
		
//		testFrame.setLocation(xPos, yPos);
//		testFrame.setSize(600, 480);
//		
//		testFrame.setVisible(true);

		try {
			// Look and Feel UIManager를 통해 ui 쉽게 변경 가능
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception ex) {
		}

		TestFrame frame = new TestFrame();
		//frame.setSize(100, 100);
		// 1) 표시할 위치 지정
//		frame.setLocation(new Point(100, 100));
		// 2) 표시할 크기 지정
//		frame.setSize(new Dimension(300, 300));
		
		// 화면의 전체 크기
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xPos = (scrDim.width - TestFrame.WIDTH) / 2;
		int yPos = (scrDim.height - TestFrame.HEIGHT) / 2;
		
		// 1)과 2)를 동시에 처리
		frame.setBounds(new Rectangle(xPos, yPos, TestFrame.WIDTH, TestFrame.HEIGHT));
		// 3) 프레임 표시
		frame.setVisible(true);
		
		LOGGER.debug("active count:" + Thread.activeCount());
	}
}
