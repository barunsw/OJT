package com.barunsw.ojt.iwkim.day08;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel2 extends JPanel {
	private static Logger LOGGER = LogManager.getLogger(TestPanel.class);
	
	private final int LABEL_WIDTH = 120;
	private final int LABEL_HEIGHT = 22;
	
	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	private JLabel jLabel_Name = new JLabel("이름");
	private JLabel jLabel_Age = new JLabel("나이");
	private JLabel jLabel_Gender = new JLabel("성별");
	private JLabel jLabel_Address = new JLabel("주소");
	
	private JTextField jTextField_Name = new JTextField();
	private JTextField jTextField_Age = new JTextField();
	private JRadioButton jRadioButton_Man = new JRadioButton("남자");
	private JRadioButton jRadioButton_Woman = new JRadioButton("여자");
	private JTextArea jTextArea_Address = new JTextArea();
	
	private JScrollPane jScrollPane_Address = new JScrollPane();
	
	private ButtonGroup buttonGroup_Gender = new ButtonGroup();
	
	private JPanel jPanel_Command = new JPanel();
	
	private JButton jButton_Add = new JButton("추가");
	private JButton jButton_Change = new JButton("변경");
	private JButton jButton_Delete = new JButton("삭제");
	
	public TestPanel2() {
		try {
			initComponent();
			initEvent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		this.setLayout(gridBagLayout);
		jPanel_Command.setLayout(gridBagLayout);
		
		jLabel_Name.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		jLabel_Age.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		jLabel_Gender.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		jLabel_Address.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		
		jLabel_Name.setMinimumSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		jLabel_Age.setMinimumSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		jLabel_Gender.setMinimumSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		jLabel_Address.setMinimumSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		
		this.add(jLabel_Name, new GridBagConstraints(
				0, 0, 1, 1, 
				0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
				new Insets(5, 5, 5, 5),
				0, 0));
		
		this.add(jTextField_Name, new GridBagConstraints(
				1, 0, 2, 1, 
				1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
				new Insets(5, 0, 5, 5),
				0, 0));
		
		this.add(jLabel_Age, new GridBagConstraints(
				0, 1, 1, 1, 
				0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
				new Insets(0, 5, 5, 5),
				0, 0));
		
		this.add(jTextField_Age, new GridBagConstraints(
				1, 1, 2, 1, 
				1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
				new Insets(0, 0, 5, 5),
				0, 0));
		
		this.add(jLabel_Gender, new GridBagConstraints(
				0, 2, 1, 1, 
				0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
				new Insets(0, 5, 5, 5),
				0, 0));
		
		this.add(jRadioButton_Man, new GridBagConstraints(
				1, 2, 1, 1, 
				0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
				new Insets(0, 0, 5, 20),
				0, 0));
		
		this.add(jRadioButton_Woman, new GridBagConstraints(
				2, 2, 1, 1, 
				1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
				new Insets(0, 0, 5, 5),
				0, 0));
		
		this.add(jLabel_Address, new GridBagConstraints(
				0, 3, 1, 1, 
				0.0, 0.0,
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 
				new Insets(0, 5, 5, 5),
				0, 0));
		
		this.add(jScrollPane_Address, new GridBagConstraints(
				1, 3, 2, 1, 
				1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
				new Insets(0, 0, 5, 5),
				0, 0));
		
		this.add(jPanel_Command, new GridBagConstraints(
				0, 4, 3, 1, 
				1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
				new Insets(0, 5, 5, 5),
				0, 0));
		
		jScrollPane_Address.getViewport().add(jTextArea_Address);
		
		buttonGroup_Gender.add(jRadioButton_Man);
		buttonGroup_Gender.add(jRadioButton_Woman);
		
		jPanel_Command.add(jButton_Add, new GridBagConstraints(
				0, 0, 1, 1, 
				1.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.VERTICAL, 
				new Insets(0, 0, 0, 0),
				0, 0));
		
		jPanel_Command.add(jButton_Change, new GridBagConstraints(
				1, 0, 1, 1, 
				0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
				new Insets(0, 0, 0, 0),
				0, 0));
		
		jPanel_Command.add(jButton_Delete, new GridBagConstraints(
				2, 0, 1, 1, 
				0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
				new Insets(0, 0, 0, 0),
				0, 0));
	}
	
	private void initEvent() {
		jButton_Add.addActionListener(new TestPanel2_jButton_Add_ActionListener(this));
	}
	
	void jButton_Add_actionPerformed(ActionEvent e) {
		
	}
	
	void jButton_Change_actionPerformed(ActionEvent e) {
		
	}
	
	void jButton_Delete_actionPerformed(ActionEvent e) {
		
	}
}

class TestPanel2_jButton_Add_ActionListener implements ActionListener {
	private TestPanel2 adaptee;
	
	public TestPanel2_jButton_Add_ActionListener(TestPanel2 adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton_Add_actionPerformed(e);
	}
}
