package com.barunsw.ojt.day06;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestPanel.class);
	
	private final Dimension LABEL_SIZE = new Dimension(80, 22);
	
	private JLabel jLabel_Name 		= new JLabel("이름");
	private JLabel jLabel_Gender 	= new JLabel("성별");
	private JLabel jLabel_Address 	= new JLabel("주소");
	
	private JTextField jTextField_Name		= new JTextField();
	private JRadioButton jRadioButton_Man 	= new JRadioButton("남");
	private JRadioButton jRadioButton_Woman = new JRadioButton("여");
	private JTextArea jTextArea_Address		= new JTextArea();
	
	private ButtonGroup buttonGroup = new ButtonGroup();
	
	private JScrollPane jScrollPane_Address = new JScrollPane();
	
	private JPanel jPanel_Command = new JPanel();
	
	private JButton jButton_Add = new JButton("추가");

	private CardLayout cardLayout = new CardLayout();

	private GridBagLayout gridBagLayout = new GridBagLayout();
	
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
		//this.setLayout(cardLayout);
		this.setLayout(gridBagLayout);
		
		jPanel_Command.setLayout(gridBagLayout);
		
		jLabel_Name.setPreferredSize(LABEL_SIZE);
		jLabel_Name.setMinimumSize(LABEL_SIZE);
		jLabel_Name.setMaximumSize(LABEL_SIZE);

		jLabel_Gender.setPreferredSize(LABEL_SIZE);
		jLabel_Gender.setMinimumSize(LABEL_SIZE);
		jLabel_Gender.setMaximumSize(LABEL_SIZE);
		
		jLabel_Address.setPreferredSize(LABEL_SIZE);
		jLabel_Address.setMinimumSize(LABEL_SIZE);
		jLabel_Address.setMaximumSize(LABEL_SIZE);
		
		
		jTextField_Name.setPreferredSize(new Dimension(120, 22));
		jRadioButton_Man.setPreferredSize(new Dimension(60, 22));
		jRadioButton_Woman.setPreferredSize(new Dimension(60, 22));

		/* BorderLayout
		this.add(jLabel_Name, BorderLayout.WEST);
		this.add(jTextField_Name, BorderLayout.CENTER);
		*/
		buttonGroup.add(jRadioButton_Man);
		buttonGroup.add(jRadioButton_Woman);
		
		this.add(jLabel_Name, new GridBagConstraints(
					0, 0, 1, 1,
					0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(5, 5, 5, 5),
					0, 0
				));
		
		this.add(jTextField_Name, new GridBagConstraints(
				1, 0, 2, 1,
				1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 0, 5, 5),
				0, 0
			));
		
		this.add(jLabel_Gender, new GridBagConstraints(
				0, 1, 1, 1,
				0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 5, 5, 5),
				0, 0
			));
		
		this.add(jRadioButton_Man, new GridBagConstraints(
				1, 1, 1, 1,
				1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5),
				0, 0
			));
		
		this.add(jRadioButton_Woman, new GridBagConstraints(
				2, 1, 1, 1,
				1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5),
				0, 0
			));
		
		this.add(jLabel_Address, new GridBagConstraints(
				0, 2, 1, 1,
				0.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 5, 5, 5),
				0, 0
			));
		
		this.add(jScrollPane_Address, new GridBagConstraints(
				1, 2, 2, 1,
				1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5),
				0, 0
			));
		
		this.add(jPanel_Command, new GridBagConstraints(
				0, 3, 3, 1,
				1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0),
				0, 0
			));
		
		jPanel_Command.add(jButton_Add, new GridBagConstraints(
				0, 0, 1, 1,
				1.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 5, 5, 5),
				0, 0
			));

		jScrollPane_Address.getViewport().add(jTextArea_Address);
		
		//cardLayout 
//		JPanel redPanel = new JPanel();
//		redPanel.setBackground(Color.red);
//
//		JPanel bluePanel = new JPanel();
//		bluePanel.setBackground(Color.blue);
//
//		JPanel whitePanel = new JPanel();
//		whitePanel.setBackground(Color.white);
//
//		this.add(redPanel, "red");
//		this.add(bluePanel, "blue");
//		this.add(whitePanel, "white");
//		
//
//		Thread timeThread = new Thread(new Runnable() {
//			public void run() {
//				for (int i = 0; i < 10; i++) {
//					try {
//						Thread.sleep(1000);
//						
//						if (i % 3 == 0) {
//							cardLayout.show(TestPanel.this, "red");
//						}
//						else if (i % 3 == 1) {
//							cardLayout.show(TestPanel.this, "blue");
//						}
//						else if (i % 3 == 2) {
//							cardLayout.show(TestPanel.this, "white");
//						}
//					}
//					catch (Exception ex) {
//						LOGGER.error(ex.getMessage(), ex);
//					}
//				}
//			}
//		});
//		
//		timeThread.start();
	}
}
