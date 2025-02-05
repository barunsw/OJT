package com.barunsw.ojt.sjcha.day10;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.Calendar;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.sjcha.day10.TestFrame;

public class ClockPanel extends JPanel {
	private static Logger LOGGER = LogManager.getLogger(ClockPanel.class);

	// 현재 시간을 담는다.
	// private Calendar todayTime;
	private int hour;
	private int min;
	private int second;

	// 반지름
	int radius = 150;

	private boolean runFlag;

	public ClockPanel() {
		try {
			initTimer();
		} catch (Exception ex) {

		}
	}

	private void initTimer() {
		runFlag = true;
		// 익명 Thread 하나 만들고,
		Thread timeThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// run()에서 무한루프 돌면서, 현재시간 정보 구하고,
				// repaint 호출
				// repaint에서는 시간 정보를 가지고 그림을 그린다.
				setClock(Calendar.getInstance());
				while (runFlag) {
					try {
						// 1초마다 쉰다.
						Thread.sleep(1000);
						// 시간을 구한다.
						Calendar todayTime = Calendar.getInstance();
						
						LOGGER.debug("today:" + todayTime);

						// 현재 시간, 분, 초를 저장.
						hour = todayTime.get(Calendar.HOUR);
						min = todayTime.get(Calendar.MINUTE);
						second = todayTime.get(Calendar.SECOND);

						LOGGER.debug("시간:" + hour);
						LOGGER.debug("분:" + min);
						LOGGER.debug("초:" + second);

						// 다시 그린다.
						repaint();
					} catch (InterruptedException ie) {
						LOGGER.error(ie.getMessage(), ie);
					}

					
				}
			}
		});
		timeThread.start();
	}

	private void setClock(Calendar cal) {
		this.hour = cal.get(Calendar.HOUR);
		this.min = cal.get(Calendar.MINUTE);
		this.second = cal.get(Calendar.SECOND);
	}
	
	// ClockPanel 객체의 repaint 메소드 호출하면 paintComponent가 호출
	@Override
	public void paintComponent(Graphics g) {
		LOGGER.debug("paintComponent");

		// 뒷 배경은 흰색으로 설정
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// 시계 원 테두리
		g.setColor(Color.black);
		g.drawOval(100, 100, 300, 300);

		// 시계 위치 표시
		double pos = radius * 0.85;
		for (int num = 1; num < 13; num++) {
			int x = (int) (pos * Math.cos(Math.PI * ((30 * num) - 90) / 180));
			int y = (int) (pos * Math.sin(Math.PI * ((30 * num) - 90) / 180));

			g.drawString(Integer.toString(num), x + (500 / 2), y + (500 / 2));
		}

		if (second == 60) {
			second = 0;
			min++;
		}
		if (min == 60) {
			min = 0;
			hour++;
		}
		if (min == 60 && hour == 12) {
			hour = 0;
		}

		second = second * 6 - 90;
		int xSec = (int) (radius * Math.cos(Math.PI * second / 180) + (500 / 2));
		int ySec = (int) (radius * Math.sin(Math.PI * second / 180) + (500 / 2));
		g.setColor(Color.RED);
		g.drawLine((500 / 2), (500 / 2), xSec, ySec);

		min = min * 6 + second / 60 - 90;
		int xMin = (int) (150 * Math.cos(Math.PI * min / 180) + (500 / 2));
		int yMin = (int) (150 * Math.sin(Math.PI * min / 180) + (500 / 2));
		g.setColor(Color.BLUE);
		g.drawLine((500 / 2), (500 / 2), xMin, yMin);

		hour = hour * 30 + min / 2 - 90;
		int xHour = (int) (80 * Math.cos(Math.PI * hour / 180) + (500 / 2));
		int yHour = (int) (80 * Math.sin(Math.PI * hour / 180) + (500 / 2));
		g.setColor(Color.BLACK);
		g.drawLine((500 / 2), (500 / 2), xHour, yHour);

	}
}
