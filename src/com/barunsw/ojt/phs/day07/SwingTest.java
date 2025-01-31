package com.barunsw.ojt.phs.day07;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;

import com.barunsw.ojt.phs.day07.TestFrame;

public class SwingTest {

	public static void main(String[] args) {
		try {
			// Look and Feel UIManager를 통해 ui 쉽게 변경 가능
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception ex) {
		}

		TestFrame frame = new TestFrame();

		// 화면의 전체 크기 구하기
//		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();

		// 화면 중앙에 위치시키기 위해 x좌표, y좌표 구하기
//		int x = (scrDim.width - TestFrame.WIDTH) / 2;
//		int y = (scrDim.height - TestFrame.HEIGHT) / 2;

		// 위치와 크기를 동시에 지정한다.
//		frame.setBounds(x, y, 800, 600);
		
		
		//컴포넌트들 size에 맞게 표시해줌
		frame.pack();
		
		//위치 지정
		frame.setLocationRelativeTo(null);
		
		// 프레임을 표시한다.
		frame.setVisible(true);
		
		//창 크기를 변경 할 수 없게 만든다.
		frame.setResizable(false);

	}

}