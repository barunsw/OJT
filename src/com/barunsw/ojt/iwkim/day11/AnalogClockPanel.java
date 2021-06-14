package com.barunsw.ojt.iwkim.day11;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Calendar;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnalogClockPanel extends JPanel{
	private static Logger LOGGER = LogManager.getLogger();

	// (x, y) : 원의 중심 좌표를 의미
	// r      : 원의 반지름을 의미
	private Calendar calendar;
	private int x = 300;
	private int y = 300;
	private int r = 200;

	private Font font = new Font("DialogInput", Font.PLAIN, 20);
	
	public AnalogClockPanel() {
		initThread();
	}

	private void initThread() {
		LOGGER.info("Thread run");

		Thread timeThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
						repaint();
					}
					catch (InterruptedException ie) {
						LOGGER.error(ie.getMessage(), ie);
					}
				}				
			}
		});

		timeThread.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		// 먼저 시간, 분, 초 를 구하는 변수를 선언
		// paintComponent가 호출될때마다 시간, 분, 초를 계산해야함
		calendar = Calendar.getInstance();
		int sec = calendar.get(Calendar.SECOND);
		int min = calendar.get(Calendar.MINUTE);
		int hour = calendar.get(Calendar.HOUR);
		int clockStartNumber = 1;
		
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// 시계 테두리 설정
		g.setColor(Color.ORANGE);
		g.drawOval(100, 100, 400, 400);
		
		g.setColor(Color.PINK);
		// i를 6씩 증가 시키는 이유 : 아날로그 시계를 보면
		// 시간과 시간사이에 작은눈금 들이 존재하는데 총 5칸이 나오게 된다.
		// 시간에 대한 눈금은 i가 30의 배수인 경우만 체크하므로 
		// 시간과 시간 사이의 작은눈금들은 i가 6의 배수인경우에 체크해야 한다.  
		for (int i = 0; i < 360; i += 6) {
			
			// 직각삼각형에서 직각이 아닌 한 각이 주어지고 아무 한변의 길이만 알면
			// 삼각비를 통해 다른 두변의 길이를 표현할 수 있다.
			// 예를들어 원의 중심을 (0, 0)이라고 가정하고 한변이 r(반지름)인 삼각형을 생각한다면
			// (x, y) = (r * cos(사잇각), r * sin(사잇각))으로 나타낼 수 있다.
			// 원의 중심에 따라 해당 좌표를 증감시켜 주어야 한다.
			int x1 = (int) (r * Math.sin(Math.PI * i / 180) + x);
			int y1 = (int) (r * Math.cos(Math.PI * i / 180) + y);
			int x2 = (int) ((r - 5) * Math.sin(Math.PI * i / 180) + x);
			int y2 = (int) ((r - 5) * Math.cos(Math.PI * i / 180) + y);
			
			// 시간 표시
			if((i % 30 == 0) && (clockStartNumber <= 12)) {
				x2 = (int) ((r - 10) * Math.sin(Math.PI * i / 180) + x);
				y2 = (int) ((r - 10) * Math.cos(Math.PI * i / 180) + y);

				LOGGER.info("x2 : " + x2 + ", y2 : " + y2);
				g.setColor(Color.DARK_GRAY);
				g.setFont(font);
			    g.drawString(String.valueOf(clockStartNumber++), x2, y2);
				if (clockStartNumber == 0) {
					clockStartNumber = 12;
				}
			}
			else {
				g.drawLine(x1, y1, x2, y2);
			}
		}
		
		if (sec == 60) {
			sec = 0;
			min ++;
		}
		if (min == 60) {
			min = 0;
			hour++;
		}
		if (min == 60 && hour == 12) {
			hour = 0;
		}
		
		// -90을 해주는 이유는 좌표평면에서의 삼각함수 시작 값과 시계에서의 시작값이 다르기 때문이다.
		// 시침, 분침, 초침 설정
		sec = sec * 6 - 90;
		int xSec = (int) (r * Math.cos(Math.PI * sec / 180) + x);
		int ySec = (int) (r * Math.sin(Math.PI * sec / 180) + y);
		g.setColor(Color.RED);
		g.drawLine(x, y, xSec, ySec);
		
		min = calendar.get(Calendar.MINUTE) * 6 + sec / 60 - 90;
		int xMin = (int) (150 * Math.cos(Math.PI * min / 180) + x);
		int yMin = (int) (150 * Math.sin(Math.PI * min / 180) + y);
		g.setColor(Color.BLUE);
		g.drawLine(x, y, xMin, yMin);
		
		hour = hour * 30 + calendar.get(Calendar.MINUTE) / 2 - 90;
		int xHour = (int) (80 * Math.cos(Math.PI * hour / 180) + x);
		int yHour = (int) (80 * Math.sin(Math.PI * hour / 180) + y);
		g.setColor(Color.BLACK);
		g.drawLine(x, y, xHour, yHour);
	}
}