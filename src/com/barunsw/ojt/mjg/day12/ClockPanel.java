package com.barunsw.ojt.mjg.day12;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClockPanel extends JPanel {
    private static final Logger LOGGER = LogManager.getLogger(ClockPanel.class);
    
    private Dimension CLOCK_SIZE = new Dimension(400, 80);
    
    // 디지털 시계용 패널
    // paintComponent(Graphics g) 오버라이드해서 사용
    private JPanel jPanel_Digital = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
        	// 부모 클래스인 JPanel의 기본 그리기 작업 먼저 수행
            super.paintComponent(g);
            
            // 배경 흰색
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());
            
            // 디지털 시계 문자열 그리기
            g.setColor(Color.black);
            g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 30));
            int stringWidth = g.getFontMetrics().stringWidth(digitalTime);
            int x = (getWidth() - stringWidth) / 2;
            int y = (getHeight() + g.getFontMetrics().getAscent()) / 2;
            g.drawString(digitalTime, x, y);
        }
    };
    
    // 아날로그 시계용 패널
    // paintComponent(Graphics g) 오버라이드해서 사용
    private JPanel jPanel_Analog = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
        	// 부모 클래스인 JPanel의 기본 그리기 작업 먼저 수행
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D)g;
            
            // 배경 흰색
            g2d.setColor(Color.white);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            // 패널 중앙, 반지름 계산
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int radius = Math.min(getWidth(), getHeight()) / 2 - 10;  // 패널 안 시계의 여백 위해 -10
            
            // 시계 테두리 그리기
            // x는 오른쪽으로, y는 아래쪽으로 갈수록 증가하기 때문에 centerX, centerX를 타원의 시작점인 왼쪽 모서리 상단으로 지정하려면
            // x = centerX - radius, y = centerY - radius
            g2d.setColor(Color.black);
            g2d.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
            
            // 시/분/초에 따른 각도 계산 (12시가 0도가 되도록 -90도 조정)
            // 삼각함수에선 3시 부분이 0도지만 paintComponent에선 12시 부분을 0도로 맞춰줘야 함
            
            // 시침: 1시간 동안 30도 이동
            double hourAngle   = Math.toRadians(((hour % 12) * 30) + (minute * 0.5) - 90);
            
            // 분침: 1분 동안 6도 이동
            double minuteAngle = Math.toRadians((minute * 6) + (second * 0.1) - 90);
            
            // 초침: 1초 동안 6도 이동
            double secondAngle = Math.toRadians((second * 6) - 90);
            
            // 침 길이
            int hourHandLength   = (int)(radius * 0.5);
            int minuteHandLength = (int)(radius * 0.75);
            int secondHandLength = (int)(radius * 0.9);
            
            // 시침 
	        // (x, y) = (ℓ cos θ, ℓ sin θ)
            // 원점이 0, 0이 아니기 때문에 centerX, centerY 각각 더해줌
            g2d.setColor(Color.black);
            g2d.setStroke(new BasicStroke(4));
            int hourX = centerX + (int)(hourHandLength * Math.cos(hourAngle));
            int hourY = centerY + (int)(hourHandLength * Math.sin(hourAngle));
            // int hourX = centerX + (int)(radius * Math.cos(hourAngle));
            // int hourY = centerY + (int)(radius * Math.sin(hourAngle));
            g2d.drawLine(centerX, centerY, hourX, hourY);
            
            // 분침
            g2d.setColor(Color.black);
            g2d.setStroke(new BasicStroke(3));
            int minuteX = centerX + (int)(minuteHandLength * Math.cos(minuteAngle));
            int minuteY = centerY + (int)(minuteHandLength * Math.sin(minuteAngle));
            g2d.drawLine(centerX, centerY, minuteX, minuteY);
            
            // 초침 (빨간색)
            g2d.setColor(Color.red);
            g2d.setStroke(new BasicStroke(2));
            int secondX = centerX + (int)(secondHandLength * Math.cos(secondAngle));
            int secondY = centerY + (int)(secondHandLength * Math.sin(secondAngle));
            g2d.drawLine(centerX, centerY, secondX, secondY);
            
            // 시계 중심점 표시
            g2d.setColor(Color.black);
            g2d.fillOval(centerX - 5, centerY - 5, 10, 10);
        }
    };
    
    // 디지털 시계 표시용 문자열
    private String digitalTime = "";
    
    // 아날로그 시계용 시/분/초
    private int hour;
    private int minute;
    private int second;
    
    private Timer timer;
    
    public ClockPanel() {
        try {
            initComponent();
            initTimer();
        }
        catch (Exception ex) {
            LOGGER.error("ClockPanel 생성 중 예외 발생", ex);
        }
    }
    
    private void initComponent() {
        // ClockPanel에 GridBagLayout 설정
        this.setLayout(new GridBagLayout());
     
        
        jPanel_Digital.setPreferredSize(CLOCK_SIZE);
        
        // 디지털 시계 패널
        this.add(jPanel_Digital, new GridBagConstraints(
            0, 0, 1, 1, 1.0, 0.0, 
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
            new Insets(5, 5, 5, 5), 0, 0
        ));
            
        // 아날로그 시계 패널
        this.add(jPanel_Analog, new GridBagConstraints(
            0, 1, 1, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 5, 5, 5), 0, 0
        ));
    }
    
    private void initTimer() {
        timer = new Timer();
        
        // TimerTask를 이용해 1초마다 반복 실행되는 작업 정의
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                initData(); // 현재 데이터 가져오기(시계)
                
                SwingUtilities.invokeLater(() -> {
                    jPanel_Digital.repaint();
                    jPanel_Analog.repaint();
                });
            }
        }, 0, 1000L); // 1초마다 실행
    }
    
    private void initData() {
        // 디지털 시계 문자열
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
        digitalTime = sdf.format(now);
        
        // 아날로그 시계 (시, 분, 초)
        Calendar cal = Calendar.getInstance();
        hour   = cal.get(Calendar.HOUR);
        minute = cal.get(Calendar.MINUTE);
        second = cal.get(Calendar.SECOND);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
