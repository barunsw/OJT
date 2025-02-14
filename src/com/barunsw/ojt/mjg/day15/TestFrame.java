package com.barunsw.ojt.mjg.day15;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.common.RmiAddressBookInterface;
import com.barunsw.ojt.mjg.day15.AddressBookLayoutPanel;

public class TestFrame extends JFrame {
    private static final Logger LOGGER = LogManager.getLogger(TestFrame.class);

    public static final int WIDTH  = 600;
    public static final int HEIGHT = 400;

    private AddressBookLayoutPanel addressBookLayoutPanel;

    // RMI 서버 인터페이스
    private RmiAddressBookInterface rmiAddressBookInterface;

    // 생성자에서 RMI 인터페이스 받아옴
    public TestFrame(RmiAddressBookInterface rmiAddressBookInterface) {
    	this.rmiAddressBookInterface = rmiAddressBookInterface;

        try {
            initComponent();
        }
        catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private void initComponent() throws Exception {
        // 타이틀
        this.setTitle("AddressBook");
        // 기본적인 닫힘 오퍼레이션
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // RMI 인터페이스를 AddressBookLayoutPanel에 전달
        addressBookLayoutPanel = new AddressBookLayoutPanel(rmiAddressBookInterface);
        this.setContentPane(addressBookLayoutPanel);

        // 윈도우 이벤트
        this.addWindowListener(new TestFrame_this_WindowAdapter(this));
    }

    void windowClosing(WindowEvent e) {
        LOGGER.debug("windowClosing");

        int result = JOptionPane.showConfirmDialog(TestFrame.this, 
                "정말로 종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }
}

class TestFrame_this_WindowAdapter extends WindowAdapter {
    private TestFrame adaptee;

    public TestFrame_this_WindowAdapter(TestFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        adaptee.windowClosing(e);
    }
}
