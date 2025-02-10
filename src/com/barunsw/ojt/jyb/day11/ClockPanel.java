package com.barunsw.ojt.jyb.day11;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClockPanel extends JPanel {
	private static Logger LOGGER = LogManager.getLogger(ClockPanel.class);

	private Timer timer; // 주기적으로 repaint를 호출하기 위한 객체
	// repaint : 화면을 다시 그리도록 요청하는 메소드
	private int hour, minute, second;

	public ClockPanel() {
		try {
			initComponent(); // 컴포넌트 초기화

			initTimer(); // 타이머 초기화
		}
		catch (Exception ex) {

		}
	}

	private void initComponent() {
	}

	private void initTimer() { // 타이머 객체 생성 + TimerTask를 이용해 주기적으로 작업을 수행하도록 설정

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
		}, 0, 1000L); // 지연 시간 : 0, 주기 : 1000L
	}

	private void initData() { // 시침, 분침, 초침의 위치를 계산하는 메소드
		Calendar calendar = Calendar.getInstance();
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		second = calendar.get(Calendar.SECOND);
	}

	// ClockPanel 객체의 repaint 메소드 호출하면 paintComponent가 호출

	@Override
	public void paintComponent(Graphics g) { // JPanel의 그래픽을 그리는 메소드
		Graphics2D g2d = (Graphics2D) g;
		LOGGER.debug("paintComponent");

		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.black);
		g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 40));
		String time = String.format("%02d:%02d:%02d", hour, minute, second);
		FontMetrics fm = g.getFontMetrics(); // 글자 너비 계산
		int stringWidth = fm.stringWidth(time);
		int x = (this.getWidth() - stringWidth) / 2; // 화면 가로 중앙
		int y = 100; // 세로 위치
		g.drawString(time, x, y);

		// g.setColor(Color.green);
		// g.drawLine(300, 300, 400, 400);

		int centerX = this.getWidth() / 2; // 화면 가로 중앙 x 좌표
		int centerY = 250; // 시계 세로 위치

		g2d.setStroke(new java.awt.BasicStroke(3)); // 원 두
		g.setColor(Color.black);
		g.drawOval(centerX - 75, centerY - 75, 150, 150);

		int hourHandLength = 30;
		int minuteHandLength = 60;
		int secondHandLength = 60;

		// 시침 : 한시간에 움직이는 각도 + 1분에 움직이는 각도
		// 24시간 -> 360도
		// 1시간 -> 30도(360/12)
		// 1분 -> 0.5도(30/60)
		double hourAngle = Math.toRadians((hour % 12) * 30 + minute * 0.5 - 90); // 삼각함수에 쓰기 위해서 라디안으로 변환
		// 가로가 0라디안인데(3시) 우리가 보는 시계는 12시부터 시작이므로 90도를 빼줌

		// 분침 : 1분마다 움직임
		double minuteAngle = Math.toRadians(minute * 6 - 90);
		double secondAngle = Math.toRadians(second * 6 - 90);

		// cos : x좌표(밑변/빗변. 보통 빗변을 1로 잡으므로 분자인 밑변만 남으므로 x좌표)
		// sin : y좌표(높이/빗변. 분자인 높이만 남으므로 y좌표)
		int hourEndX = centerX + (int) (hourHandLength * Math.cos(hourAngle));
		int hourEndY = centerY + (int) (hourHandLength * Math.sin(hourAngle));

		int minuteEndX = centerX + (int) (minuteHandLength * Math.cos(minuteAngle));
		int minuteEndY = centerY + (int) (minuteHandLength * Math.sin(minuteAngle));

		int secondEndX = centerX + (int) (secondHandLength * Math.cos(secondAngle));
		int secondEndY = centerY + (int) (secondHandLength * Math.sin(secondAngle));

		int tickLength = 10;
		g2d.setStroke(new java.awt.BasicStroke(2));
		g.setColor(Color.gray);

		// 3시 : 원의 반지름 - x길이
		g.drawLine(centerX + 75, centerY, centerX + 75 - tickLength, centerY);

		// 6 : 원의 반지름 - y길이
		g.drawLine(centerX, centerY + 75, centerX, centerY + 75 - tickLength);

		// 9 : 원의 반지름 + x길이
		g.drawLine(centerX - 75, centerY, centerX - 75 + tickLength, centerY);

		// 12 : 원의 반지름 + y길이
		g.drawLine(centerX, centerY - 75, centerX, centerY - 75 + tickLength);

		g2d.setStroke(new java.awt.BasicStroke(1));

		g.setColor(Color.red);
		g.drawLine(centerX, centerY, secondEndX, secondEndY); // 초침

		g2d.setStroke(new java.awt.BasicStroke(2));
		g.setColor(Color.black);
		g.drawLine(centerX, centerY, minuteEndX, minuteEndY); // 분침

		g2d.setStroke(new java.awt.BasicStroke(2));
		g.setColor(Color.black);
		g.drawLine(centerX, centerY, hourEndX, hourEndY); // 시침
	}
}