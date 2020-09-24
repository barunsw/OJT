package com.barunsw.ojt.phs.day06;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class SwingTest extends JFrame{
	public static void main(String[] args) {
		
		TestFrame frame = new TestFrame();
		
		// 화면의 전체 크기 구하기
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
		
		//화면 중앙에 위치시키기 위해 x좌표, y좌표 구하기
		int x = (scrDim.width - TestFrame.WIDTH) / 2;
		int y = (scrDim.height - TestFrame.HEIGHT) / 2;
		
		// 위치와 크기를 동시에 지정한다.
		frame.setBounds(x, y, 600, 400);
		
		// 프레임을 표시한다.
		frame.setVisible(true);
	}
	
}