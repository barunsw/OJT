package com.barunsw.ojt.yjkim.day06;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel2 extends JPanel {
	
	private static final Logger LOGGER = LogManager.getLogger(TestPanel2.class);
	
	private final Dimension LABEL_SIZE = new Dimension(80, 22);

	private JLabel jLabel_Name 		= new JLabel("이름");
	private JLabel jLabel_Gender 	= new JLabel("성별");
	private JLabel jLabel_Address 	= new JLabel("주소");
	
	private JTextField jTextField_Name		= new JTextField();
	private JRadioButton jRadioButton_Man 	= new JRadioButton("남");
	private JRadioButton jRadioButton_Woman = new JRadioButton("여");
	private JTextArea jTextArea_Address		= new JTextArea();
	
	private JButton jButton_Add = new JButton("추가");

	
	public TestPanel2() {
		try {
			Grid_Flow_initComponent();
		}catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	void initComponents() throws Exception{
		
	}
	
	void Grid_Flow_initComponent() throws Exception{
		this.setLayout(new GridLayout(3,1,10,10));
		JPanel jpanel_flow1 = new JPanel(new FlowLayout());
		JPanel jpanel_flow2 = new JPanel(new FlowLayout());
		JPanel jpanel_flow3 = new JPanel(new FlowLayout());
		jpanel_flow1.setBackground(Color.ORANGE);
		jpanel_flow2.setBackground(Color.BLUE);
		jpanel_flow3.setBackground(Color.GREEN);
		
		jLabel_Name.setPreferredSize(LABEL_SIZE);
		jLabel_Gender.setPreferredSize(LABEL_SIZE);
		jLabel_Address.setPreferredSize(LABEL_SIZE);
		
		jTextField_Name.setPreferredSize(new Dimension(120, 22));
		jRadioButton_Man.setPreferredSize(new Dimension(60, 22));
		jRadioButton_Woman.setPreferredSize(new Dimension(60, 22));
		jTextArea_Address.setPreferredSize(new Dimension(120, 60));
		jpanel_flow1.add(jLabel_Name);
		jpanel_flow1.add(jLabel_Gender);
		jpanel_flow1.add(jLabel_Address);
		jpanel_flow2.add(jTextField_Name);
		jpanel_flow2.add(jRadioButton_Man);
		jpanel_flow2.add(jRadioButton_Woman);
		jpanel_flow3.add(jTextArea_Address);
		jpanel_flow3.add(jButton_Add);
		this.add(jpanel_flow1);
		this.add(jpanel_flow2);
		this.add(jpanel_flow3);
	}

}
