package com.barunsw.ojt.cjs.day06;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormFrame extends JFrame {

	private static Logger log = LoggerFactory.getLogger(FormFrame.class);
	FormPanel calPanel = new FormPanel();
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;

	public FormFrame() {
		try {
			initComponent();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void initComponent() throws Exception {

		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
		// 사용자 화면의 해상도의 크기를 구해준다.
		int xPos = (scrDim.width - FormFrame.WIDTH) / 2;
		int yPos = (scrDim.height - FormFrame.HEIGHT) / 2;

		this.setBounds(new Rectangle(xPos, yPos, FormFrame.WIDTH, FormFrame.HEIGHT));

		log.debug(String.format("%d %d", xPos, yPos));
//		this.addWindowListener(new CalFrame_this_WindowAdapter(this));

		this.setTitle("calculator");
		// JFrame의 이름 지정
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 실제로 윈도우창 종료시에 프로세스까지 종료
//		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// 윈도우창 종료 비활성화(창닫기 실행안됨)
		this.setVisible(true);
		// 현재 창을 나타낼지 안나타낼지
		this.add(calPanel);
	}

	public void windowClosing(WindowEvent e) {
		int result = JOptionPane.showConfirmDialog(FormFrame.this, "정말로 종료하시겠습니까");
		if (result == JOptionPane.OK_OPTION) {
			System.exit(0);
		}

	}

	class CalFrame_this_WindowAdapter extends WindowAdapter {
		//windowAdapter를 상속받음 == 필요한 것만 override를 통해 재정의하여 사용
		private FormFrame adapt;

		public CalFrame_this_WindowAdapter(FormFrame adapt) {
			this.adapt = adapt;
		}

		@Override
		public void windowClosing(WindowEvent e) {
			adapt.windowClosing(e);
		}
	}
}
