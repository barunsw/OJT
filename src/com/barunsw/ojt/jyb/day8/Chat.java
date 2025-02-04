package day8;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Chat extends JPanel {
    private static Logger LOGGER = LoggerFactory.getLogger(Chat.class);
    private JTable jTable = new JTable();

    public Chat() {
        try {
            initComponent();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private void initComponent() {
        setLayout(new GridBagLayout());

        JTextArea textArea = new JTextArea(10, 30);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);

        add(scrollPane, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0, 
                GridBagConstraints.WEST, GridBagConstraints.BOTH, 
                new Insets(5, 5, 5, 5), 0, 0));
        
        JTextField textField = new JTextField(10);
        JButton button = new JButton("Send");
        
        add(textField, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, 
                GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, 
                new Insets(5, 5, 5, 5), 0, 0));
        add(button, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, 
                GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, 
                new Insets(5, 5, 5, 5), 0, 0));
        
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Window Name");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new Chat());
        frame.setVisible(true);
    }
}
