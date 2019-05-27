package com.barunsw.ojt.yjkim.day06;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class SwingTest {
	
	public static void main(String[] args) {
		
		//프레임 선언 
		TestFrame frame  = new TestFrame();
		
		//현재 스크린 사이즈를 담는다.
		Dimension srcDim = Toolkit.getDefaultToolkit().getScreenSize();

		//프레임을 스크린 중앙에 두기 위한 공식
		int xPos = (srcDim.width - TestFrame.WIDTH) / 2;
		int yPos = (srcDim.height- TestFrame.HEIGHT) / 2;
		
		// 1) 표시할 위치 지정
		//frame.setLocation(new Point(100, 100));
		// 2) 표시할 크기 지정
		//frame.setSize(new Dimension(300, 300));
		
		//프레임의 x,y좌표와 크기를 한 번에 지정할 수 있다.
		frame.setBounds(new Rectangle(xPos, yPos, TestFrame.WIDTH, TestFrame.HEIGHT));
		
		//프레임의 가시성을 컨트롤한다.
		frame.setVisible(true);
	}
}
