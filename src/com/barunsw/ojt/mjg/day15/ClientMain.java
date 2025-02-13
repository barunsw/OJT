package com.barunsw.ojt.mjg.day15;

import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.common.RmiAddressBookInterface;
import com.barunsw.ojt.mjg.day15.TestFrame;
import com.barunsw.ojt.vo.AddressVo;

public class ClientMain {
    private static final Logger LOGGER = LogManager.getLogger(ClientMain.class);
    private static RmiAddressBookInterface rmiAddressBookInterface; // RMI 인터페이스 저장

    public static void main(String[] args) {
        try {
            // UI Look and Feel 설정
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } 
        catch (Exception ex) {
            LOGGER.error("UIManager 설정 오류", ex);
        }

        // RMI 서버 연결
        try {
            Registry registry = LocateRegistry.getRegistry(ServerMain.PORT);  // RMI 서버와 연결 시도
            rmiAddressBookInterface = (RmiAddressBookInterface) registry.lookup("ADDRESSBOOK");  // 서버에서 제공하는 ADDRESSBOOK 레지스트리 가져옴
            LOGGER.info("RMI 서버 연결 성공");
        } 
        catch (Exception e) {
            LOGGER.error("RMI 서버 연결 실패", e);
        }

        // UI 프레임 실행
        SwingUtilities.invokeLater(() -> {
            TestFrame frame = new TestFrame(rmiAddressBookInterface); // RMI 인터페이스 넘겨줌

            // 화면 중앙 정렬
            Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
            int xPos = (scrDim.width - TestFrame.WIDTH) / 2;
            int yPos = (scrDim.height - TestFrame.HEIGHT) / 2;
            frame.setBounds(new Rectangle(xPos, yPos, TestFrame.WIDTH, TestFrame.HEIGHT));

            // 프레임 표시
            frame.setVisible(true);
        });
    }
}
