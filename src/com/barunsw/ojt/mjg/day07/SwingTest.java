package com.barunsw.ojt.mjg.day07;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.UIManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwingTest {
	private static Logger LOGGER = LoggerFactory.getLogger(SwingTest.class);
	
	public static void main(String[] args) {
		// JFrame을 통한 GUI를 생성할 때 사용될 수 있는 코드
		// JFrame testFrame = new JFrame();
		// testFrame.setLocation(new Point(100, 100));  // 프레임의 시작 위치를 설정
		// testFrame.setSize(new Dimension(600, 480));  // 프레임의 크기를 설정
		// testFrame.setBounds(new Rectangle(100, 100, 600, 480));  // 위치와 크기를 동시에 설정
		
		// 화면 해상도에 따른 중앙 배치 코드 예제
		// Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();  // 화면의 크기 가져오기
		// int xPos = (scrDim.width - 600) / 2;  // 화면 중앙에 위치하도록 x 좌표 계산
		// int yPos = (scrDim.height - 480) / 2;  // 화면 중앙에 위치하도록 y 좌표 계산
		
		// 프레임의 위치 및 크기 설정 (중앙 배치)
		// testFrame.setLocation(new Point(xPos, yPos));
		// testFrame.setSize(new Dimension(600, 480));

		// 프레임을 화면 중앙에 위치시키고 크기 지정
		// testFrame.setLocation(xPos, yPos);
		// testFrame.setSize(600, 480);

		// 프레임을 표시
		// testFrame.setVisible(true);

		try {
			// UIManager를 이용해 Look and Feel을 변경
			// javax.swing에서 제공하는 다양한 테마 중 Nimbus 테마로 설정
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception ex) {
			// 예외 발생 시 별도 처리 없음 (빈 블록)
		}

		// 사용자 정의 프레임 생성
		TestFrame frame = new TestFrame();
		// 프레임의 크기를 개별적으로 설정 (현재 주석 처리)
		// frame.setSize(100, 100);

		// 프레임 위치 설정 코드 예제
		// frame.setLocation(new Point(100, 100));

		// 프레임 크기 설정 코드 예제
		// frame.setSize(new Dimension(300, 300));

		// 화면의 전체 해상도를 가져옴
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();

		// 프레임이 화면 중앙에 위치하도록 좌표 계산
		int xPos = (scrDim.width - TestFrame.WIDTH) / 2;
		int yPos = (scrDim.height - TestFrame.HEIGHT) / 2;

		// 프레임 위치와 크기를 동시에 설정
		frame.setBounds(new Rectangle(xPos, yPos, TestFrame.WIDTH, TestFrame.HEIGHT));

		// 프레임을 화면에 표시
		frame.setVisible(true);

		// 화면 중앙 배치를 위한 예제 코드 (주석 처리됨)
		/*
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  // 전체 화면 크기
		Dimension frameSize = testFrame.getSize();  // 프레임 크기 가져오기

		int x = ((screenSize.width - frameSize.width) / 2);  // 중앙 x 좌표
		int y = ((screenSize.height - frameSize.height) / 2);  // 중앙 y 좌표

		testFrame.setLocation(x, y);  // 프레임을 화면 중앙에 위치
		*/

		// 현재 활성화된 스레드 개수를 로그에 출력
		LOGGER.debug("active count:" + Thread.activeCount());
	}
}
