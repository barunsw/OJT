package com.barunsw.ojt.phs.day11;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.Calendar;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClockPanel extends JPanel {
	private static Logger LOGGER = LogManager.getLogger(ClockPanel.class);

	private int x = 300;
	private int y = 300;
	private int r = 200;
	private Calendar cal;

	public ClockPanel() {
		initComponent();
	}

	private void initComponent() {
		LOGGER.debug("initComponent");

		// 1번만 실행
		new Thread() {
			public void run() {
				try {
					while (true) {
						Thread.sleep(1000);
						repaint();
					}
				} catch (InterruptedException e) {
					LOGGER.debug(e.getMessage());
				}
			};
		}.start();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR); // 시 구현
		int min = cal.get(Calendar.MINUTE); // 분 구현
		int sec = cal.get(Calendar.SECOND); // 초 구현
		LOGGER.debug(sec);

//		배경색
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

//		 시계 테두리
		g.setColor(Color.BLACK);
		g.drawOval(100, 100, 400, 400);

//		시계에 숫자 표시할 스타트값
		int j = 6;

		g.setColor(Color.BLACK);
		for (int i = 0; i <= 360; i += 6) {
			int x1 = (int) (r * Math.sin(Math.PI * i / 180) + x);
			int y1 = (int) (r * Math.cos(Math.PI * i / 180) + y);
			int x2 = (int) ((r - 5) * Math.sin(Math.PI * i / 180) + x);
			int y2 = (int) ((r - 5) * Math.cos(Math.PI * i / 180) + y);

//			30도 마다 숫자표시 (1 ~ 12)
			if ((i % 30 == 0) && (j <= 12)) {
				g.setColor(Color.BLACK);
				g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
				g.drawString(j + "", x2 - 6, y2 + 8);
				j--;
				if (j == 0) {
					j = 12;
				}
			}
//			30도 외엔 작은 눈금표시
			else {
				g.drawLine(x1, y1, x2, y2);
			}

		}

		if (sec == 60) {
			sec = 0;
			min++;
		}
		if (min == 60) {
			min = 0;
			hour++;
		}
		if (min == 60 && hour == 12) {
			hour = 0;
		}

		sec = sec * 6 - 90;
		int xSec = (int) (r * Math.cos(Math.PI * sec / 180) + x);
		int ySec = (int) (r * Math.sin(Math.PI * sec / 180) + y);
		g.setColor(Color.RED);
		g.drawLine(x, y, xSec, ySec);

		min = cal.get(Calendar.MINUTE) * 6 + sec / 60 - 90;
		int xMin = (int) (150 * Math.cos(Math.PI * min / 180) + x);
		int yMin = (int) (150 * Math.sin(Math.PI * min / 180) + y);
		g.setColor(Color.BLUE);
		g.drawLine(x, y, xMin, yMin);

		hour = hour * 30 + cal.get(Calendar.MINUTE) / 2 - 90;
		int xHour = (int) (80 * Math.cos(Math.PI * hour / 180) + x);
		int yHour = (int) (80 * Math.sin(Math.PI * hour / 180) + y);
		g.setColor(Color.BLACK);
		g.drawLine(x, y, xHour, yHour);

	}

}
