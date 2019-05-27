package com.barunsw.ojt.day06;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);
	
	private final Dimension LABEL_SIZE = new Dimension(80, 22);
	
	private JLabel jLabel_Name 		= new JLabel("이름");
	private JLabel jLabel_Gender 	= new JLabel("성별");
	private JLabel jLabel_Address 	= new JLabel("주소");
	
	private JTextField jTextField_Name		= new JTextField();
	private JRadioButton jRadioButton_Man 	= new JRadioButton("남");
	private JRadioButton jRadioButton_Woman = new JRadioButton("여");
	private JTextArea jTextArea_Address		= new JTextArea();
	
	private JButton jButton_Add = new JButton("추가");

	private CardLayout cardLayout = new CardLayout();

	public TestPanel() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		//this.setLayout(new BorderLayout());
		//this.setLayout(new GridLayout(4, 2));
		this.setLayout(cardLayout);
		
		jLabel_Name.setPreferredSize(LABEL_SIZE);
		jLabel_Gender.setPreferredSize(LABEL_SIZE);
		jLabel_Address.setPreferredSize(LABEL_SIZE);
		
		jTextField_Name.setPreferredSize(new Dimension(120, 22));
		jRadioButton_Man.setPreferredSize(new Dimension(60, 22));
		jRadioButton_Woman.setPreferredSize(new Dimension(60, 22));
		jTextArea_Address.setPreferredSize(new Dimension(120, 60));

		/* BorderLayout
		this.add(jLabel_Name, BorderLayout.WEST);
		this.add(jTextField_Name, BorderLayout.CENTER);
		*/
		/*
		this.add(jLabel_Name);
		this.add(jTextField_Name);
		this.add(jLabel_Gender);
		this.add(jRadioButton_Man);
		this.add(jRadioButton_Woman);
		this.add(jLabel_Address);
		this.add(jTextArea_Address);
		
		this.add(jButton_Add);
		*/
		
		/* cardLayout 
		JPanel redPanel = new JPanel();
		redPanel.setBackground(Color.red);

		JPanel bluePanel = new JPanel();
		bluePanel.setBackground(Color.blue);

		JPanel whitePanel = new JPanel();
		whitePanel.setBackground(Color.white);

		this.add(redPanel, "red");
		this.add(bluePanel, "blue");
		this.add(whitePanel, "white");
		
		Thread timeThread = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						Thread.sleep(1000);
						
						if (i % 3 == 0) {
							cardLayout.show(TestPanel.this, "red");
						}
						else if (i % 3 == 1) {
							cardLayout.show(TestPanel.this, "blue");
						}
						else if (i % 3 == 2) {
							cardLayout.show(TestPanel.this, "white");
						}
					}
					catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}
			}
		});
		
		timeThread.start();
		*/
	}
}
