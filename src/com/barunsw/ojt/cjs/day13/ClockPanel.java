package com.barunsw.ojt.cjs.day13;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClockPanel extends JPanel {
	private static Logger LOGGER = LoggerFactory.getLogger(ClockPanel.class);

	private Timer timer;

	private int hour;
	private int min;
	private int sec;

	private int secX, secY, minX, minY, hourX, hourY, oX = 300, oY = 200, r = 150;

	public ClockPanel() {
		try {
			initComponent();
			initTimer();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initComponent() {
	}

	private void initTimer() {

		// 익명 Thread 하나 만들고,
		// run()에서 무한루프 돌면서, 현재시간 정보 구하고,
		// repaint 호출
		// repaint에서는 시간 정보를 가지고 그림을 그린다.

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				initData();
				ClockPanel.this.repaint();
			}
		}, 0, 1000L);
	}

	private void initData() {
		Calendar calendar = Calendar.getInstance();
		hour = calendar.get(Calendar.HOUR);
		min = calendar.get(Calendar.MINUTE);
		sec = calendar.get(Calendar.SECOND);

		// 시침의 위치
		LOGGER.debug(String.format("%d시 %d분 %d초", hour, min, sec ));

		sec = sec * 6 - 90;
		secX = (int) (130 * Math.cos((sec * Math.PI / 180)) + oX);
		secY = (int) (130 * Math.sin((sec * Math.PI / 180)) + oY);

		min = min * 6 + (sec / 60 - 90);
		minX = (int) (100 * Math.cos((min * Math.PI / 180)) + oX);
		minY = (int) (100 * Math.sin((min * Math.PI / 180)) + oY);

		hour = hour * 6 + (min / 2 - 90);
		hourX = (int) (80 * Math.cos((hour * Math.PI / 180)) + oX);
		hourY = (int) (80 * Math.sin((hour * Math.PI / 180)) + oY);
	}

	@Override
	public void paintComponent(Graphics g) {

		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		
		g.setColor(Color.black);
		g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
		g.drawString("아날로그 시계", 240, 20);

		g.setColor(Color.blue);
		g.drawOval(150, 50, 300, 300);

		for (int i = 0; i <= 360; i += 6) { // 시계 테두리 눈금그려줌
			int x1 = (int) (r * Math.sin(Math.PI * i / 180) + oX);
			int y1 = (int) (r * Math.cos(Math.PI * i / 180) + oY);
			int x2 = (int) ((r - 5) * Math.sin(Math.PI * i / 180) + oX);
			int y2 = (int) ((r - 5) * Math.cos(Math.PI * i / 180) + oY);

			if (i % 30 == 0) { // 큰 눈금은 길게
				int j = 1;
				x2 = (int) ((r - 30) * Math.sin(Math.PI * i / 180) + oX);
				y2 = (int) ((r - 30) * Math.cos(Math.PI * i / 180) + oY);
			}
			g.drawLine(x1, y1, x2, y2);
		}

		// 중앙의 점과 시침의 위치를 이어서 선을 긋는다.
		g.setColor(Color.yellow);
		g.fillOval(295, 195, 10, 10);
		//중앙의 점 표기
		g.setColor(Color.red);
		g.drawLine(oX, oY, secX, secY);
		// 중앙의 점과 분침의 위치를 이어서 선을 긋는다.
		g.setColor(Color.green);
		g.drawLine(oX, oY, minX, minY);
		// 중앙의 점과 초침의 위치를 이어서 선을 긋는다.
		g.setColor(Color.black);
		g.drawLine(oX, oY, hourX, hourY);
	}

}
