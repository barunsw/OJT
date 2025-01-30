package com.barunsw.ojt.phs.day06;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);
	
	private final Dimension LABEL_SIZE = new Dimension(80, 22);

	private JLabel jLabel_Name 		= new JLabel("이름");
	private JLabel jLabel_Gender 	= new JLabel("성별");
	private JLabel jLabel_Age 	    = new JLabel("나이");
	
	
	private JTextField jTextField_Name = new JTextField();
	private JCheckBox jCheckbox_Man    = new JCheckBox("남자");
	private JCheckBox jCheckbox_Woman  = new JCheckBox("여자");
	private JTextField jTextField_Age  = new JTextField();
	
	private JButton btn_Send = new JButton("전송");
	
	private JPanel jPanel_Command = new JPanel();
	
	private ButtonGroup btnGroup = new ButtonGroup();
	
	public TestPanel() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initComponent() throws Exception {
		
		this.setLayout(new GridBagLayout());
		jPanel_Command.setLayout(new GridBagLayout());
		
		jLabel_Name.setPreferredSize(new Dimension(LABEL_SIZE));
		jLabel_Gender.setPreferredSize(new Dimension(LABEL_SIZE));
		jLabel_Age.setPreferredSize(new Dimension(LABEL_SIZE));
		
		jTextField_Name.setPreferredSize(new Dimension(120,22));
		jCheckbox_Man.setPreferredSize(new Dimension(60,22));
		jCheckbox_Woman.setPreferredSize(new Dimension(60,22));
		jTextField_Age.setPreferredSize(new Dimension(40,22));
		
		//체크박스를 그룹으로 모아서 중복선택 안되게 설정
		btnGroup.add(jCheckbox_Man);
		btnGroup.add(jCheckbox_Woman);
		
		//버튼 색깔 변경
		btn_Send.setBackground(Color.BLUE);
		btn_Send.setForeground(Color.YELLOW);
		
		
//		1번째 줄 ========================================================
		this.add(jLabel_Name, new GridBagConstraints(
				0, 0, 1, 1,
				0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0),
				0, 0));
		
		this.add(jTextField_Name, new GridBagConstraints(
				1, 0, 2, 1,
				0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH  ,
				new Insets(0, 0, 0, 0),
				0, 0));
		
		
//		2번째 줄 ========================================================
		this.add(jLabel_Gender, new GridBagConstraints(
				0, 1, 1, 1,
				0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL  ,
				new Insets(0, 0, 0, 0),
				0, 0));
		
		this.add(jCheckbox_Man, new GridBagConstraints(
				1, 1, 1, 1,
				0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH  ,
				new Insets(0, 0, 0, 0),
				0, 0));
		
		this.add(jCheckbox_Woman, new GridBagConstraints(
				2, 1, 1, 1,
				0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH  ,
				new Insets(0, 0, 0, 0),
				0, 0));
		
//		3번째 줄 ========================================================
		this.add(jLabel_Age, new GridBagConstraints(
				0, 2, 1, 1,
				0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL  ,
				new Insets(0, 0, 0, 0),
				0, 0));
		
		this.add(jTextField_Age, new GridBagConstraints(
				1, 2, 1, 1,
				0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH  ,
				new Insets(0, 0, 0, 0),
				0, 0));
		
//		4번째 줄 ========================================================
		this.add(jPanel_Command, new GridBagConstraints(
				3, 3, 1, 1,
				0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH  ,
				new Insets(0, 0, 0, 0),
				0, 0));
		
		jPanel_Command.add(btn_Send);
		
//		5번째 줄 ========================================================

	}
}