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

	private JPanel jPanel_Chat = new JPanel();
	private JPanel jPanel_Send = new JPanel();
	private JScrollPane scrollPane = new JScrollPane();
	private GridBagLayout gridBagLayout = new GridBagLayout();
	private JTextArea jTextArea = new JTextArea(10, 30);
	private JButton jButton = new JButton("Send");
	private JScrollPane jScrollPane = new JScrollPane();
	private JTextField jTextField = new JTextField(20);
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;

	public Chat() {
		try {
			initComponent();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	 private void initComponent() {
	        setLayout(new GridBagLayout());
	        jPanel_Chat.setLayout(new GridBagLayout());
	        jPanel_Send.setLayout(new GridBagLayout());

	        jTextArea.setLineWrap(true);
	        jTextArea.setWrapStyleWord(true);
	        
	        scrollPane.setViewportView(jTextArea);
	       
	        jPanel_Chat.add(scrollPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, 
	                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
	                new Insets(5, 5, 5, 5), 0, 0));

	        jPanel_Send.add(jTextField, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, 
	                GridBagConstraints.WEST, GridBagConstraints.BOTH, 
	                new Insets(0, 5, 5, 5), 0, 0));
	        
	        jPanel_Send.add(jButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, 
	                GridBagConstraints.EAST, GridBagConstraints.VERTICAL, 
	                new Insets(0, 5, 5, 5), 0, 0));

	        this.add(jPanel_Chat, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, 
	                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
	                new Insets(0, 0, 0, 0), 0, 0));
	        this.add(jPanel_Send, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, 
	                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 
	                new Insets(0, 0, 0, 0), 0, 0));
	    }
}
