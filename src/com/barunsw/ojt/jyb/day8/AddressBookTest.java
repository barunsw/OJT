package day8;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddressBookTest extends JFrame {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressBookTest.class);
    
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    
    private AddressBook addressBookPanel = new AddressBook();

    public AddressBookTest() {
        try {
            initComponent();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private void initComponent() throws Exception {

        this.setTitle("Address Book");

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.setContentPane(addressBookPanel);
        
        this.addWindowListener(new AddressBookTest_this_WindowAdapter(this));
    }

    void windowClosing() {
        LOGGER.debug("windowClosing");
        
        int result = JOptionPane.showConfirmDialog(AddressBookTest.this, 
                "정말로 종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        AddressBookTest frame = new AddressBookTest();
        
        Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
        int xPos = (scrDim.width - WIDTH) / 2;
        int yPos = (scrDim.height - HEIGHT) / 2;

        frame.setBounds(new Rectangle(xPos, yPos, WIDTH, HEIGHT));
        frame.setVisible(true);
    }
}

class AddressBookTest_this_WindowAdapter extends java.awt.event.WindowAdapter {
    private AddressBookTest adaptee;

    public AddressBookTest_this_WindowAdapter(AddressBookTest adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void windowClosing(java.awt.event.WindowEvent e) {
        adaptee.windowClosing();
    }
}
