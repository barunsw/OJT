package com.barunsw.ojt.mjg.day14;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.mjg.day10.TestFrame;

public class ClientMain {
    private static final Logger LOGGER = LogManager.getLogger(ClientMain.class);

    public static void main(String[] args) {
        try {
            // UI Look and Feel 설정
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } 
        catch (Exception ex) {
            LOGGER.error("UIManager 설정 오류", ex);
        }

        // UI 프레임 실행
        TestFrame frame = new TestFrame();

        // 화면 중앙 정렬
        Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
        int xPos = (scrDim.width - TestFrame.WIDTH) / 2;
        int yPos = (scrDim.height - TestFrame.HEIGHT) / 2;
        frame.setBounds(new Rectangle(xPos, yPos, TestFrame.WIDTH, TestFrame.HEIGHT));

        // 프레임 표시
        frame.setVisible(true);
    }
}
