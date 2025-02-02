package com.barunsw.ojt.phs.day07;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.io.ResolverUtil.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdk.nashorn.internal.runtime.regexp.joni.Warnings;

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);

	private final Dimension LABEL_SIZE = new Dimension(80, 22);

	private JLabel jLabel_Name = new JLabel("이름");
	private JLabel jLabel_Age = new JLabel("나이");
	private JLabel jLabel_Gender = new JLabel("성별");
	private JLabel jLabel_PhoneNumber = new JLabel("전화번호");
	private JLabel jLabel_Address = new JLabel("주소");

	private JTextField jTextField_Name = new JTextField();
	private JTextField jTextField_PhoneNumber = new JTextField();
	private JTextField jTextField_Address = new JTextField();

	private JCheckBox jCheckbox_Man = new JCheckBox("남자");
	private JCheckBox jCheckbox_Woman = new JCheckBox("여자");

	JButton btn_Add = new JButton("추가");
	JButton btn_Change = new JButton("변경");

	private ButtonGroup btnGroup = new ButtonGroup();

	private JSpinner jSpinner = new JSpinner(new SpinnerNumberModel());

//	===========================TABLE============================
	private JTable table = null;
	private JScrollPane jScrollPane_JTable = null;
	private DefaultTableModel default_Table_Model = null;
	private String header[] = {"이름", "나이", "성별", "전화번호", "주소"};
//	============================================================
	
	public TestPanel() {
		try {
			initComponent();
			setActionListener();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void setActionListener() {
		btn_Add.addActionListener(new TestPanel_jButton_Add_ActionListener(this));
		btn_Change.addActionListener(new TestPanel_jButton_Add_ActionListener(this));
	}

	private void initComponent() throws Exception {

		this.setLayout(new GridBagLayout());
		this.setBackground(Color.WHITE);
		
		jLabel_Name.setPreferredSize(new Dimension(LABEL_SIZE));
		jLabel_Gender.setPreferredSize(new Dimension(40, 22));
		jLabel_Age.setPreferredSize(new Dimension(40, 22));
		jLabel_PhoneNumber.setPreferredSize(new Dimension(LABEL_SIZE));
		jLabel_Address.setPreferredSize(new Dimension(LABEL_SIZE));

		jLabel_Name.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		jLabel_Gender.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		jLabel_Age.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		jLabel_PhoneNumber.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		jLabel_Address.setFont(new Font(Font.DIALOG, Font.BOLD, 16));

		jTextField_Name.setPreferredSize(new Dimension(70, 22));
		jTextField_PhoneNumber.setPreferredSize(new Dimension(40, 22));
		jTextField_Address.setPreferredSize(new Dimension(40, 22));

		jCheckbox_Man.setPreferredSize(new Dimension(60, 22));
		jCheckbox_Woman.setPreferredSize(new Dimension(60, 22));

		// 체크박스를 그룹으로 모아서 중복선택 안되게 설정
		btnGroup.add(jCheckbox_Man);
		btnGroup.add(jCheckbox_Woman);
		
		// 스피너 크기와 값 설정
		jSpinner.setPreferredSize(new Dimension(50, 22));
		jSpinner.setValue(20);
		
		// 테이블 초기값 설정
		default_Table_Model = new DefaultTableModel(null, header);
		table = new JTable(default_Table_Model);
		jScrollPane_JTable = new JScrollPane(table);
		
		
//		1번째 줄 ========================================================
		this.add(jLabel_Name, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));

		this.add(jTextField_Name, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 100), 0, 0));

		this.add(jLabel_Age, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		this.add(jSpinner, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 120), 0, 0));

		this.add(jLabel_Gender, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		this.add(jCheckbox_Man, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		this.add(jCheckbox_Woman, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 20), 0, 0));

		
//		2번째 줄 ========================================================
		this.add(jLabel_PhoneNumber, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));

		this.add(jTextField_PhoneNumber, new GridBagConstraints(1, 1, 6, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 20), 0, 0));

		
//		3번째 줄 ========================================================
		this.add(jLabel_Address, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));

		this.add(jTextField_Address, new GridBagConstraints(1, 2, 6, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 20), 0, 0));

		
//		4번째 줄 ========================================================
		this.add(btn_Add, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH,
				new Insets(0, 0, 20, 0), 0, 0));

		this.add(btn_Change, new GridBagConstraints(6, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.BOTH, new Insets(0, 0, 20, 20), 0, 0));

		
//		5번째 줄 ========================================================
		this.add(jScrollPane_JTable, new GridBagConstraints(0, 4, 7, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 20, 20, 20), 0, 0));
	}

	void jButton_Add_ActionListener(ActionEvent e) {
		LOGGER.debug("Add Click!!");
		
		//선택, 적혀있는 데이터들 다 가져옴
		String name = jTextField_Name.getText();
		int age = (int)jSpinner.getValue();
		String gender = jCheckbox_Man.isSelected() ? "남자" : "여자";
		String phone = jTextField_PhoneNumber.getText();
		String address = jTextField_Address.getText();
		
		if (name.isEmpty()) {
			 JOptionPane.showMessageDialog(null, "이름을 입력해주세요.", "오류", JOptionPane.WARNING_MESSAGE);
		}
		else if (!(jCheckbox_Man.isSelected()) && !(jCheckbox_Woman.isSelected())) {
			JOptionPane.showMessageDialog(null, "성별을 선택해주세요.", "오류", JOptionPane.WARNING_MESSAGE);
		}
		else if (phone.isEmpty()) {
			JOptionPane.showMessageDialog(null, "전화번호를 입력해주세요.", "오류", JOptionPane.WARNING_MESSAGE);
		}
		else if (address.isEmpty()) {
			JOptionPane.showMessageDialog(null, "주소를 입력해주세요.", "오류", JOptionPane.WARNING_MESSAGE);
		}
		else {
			LOGGER.debug("데이터추가!!");
			
			//행 하나 추가
			Object[] data = {name, age, gender, phone, address};
			default_Table_Model.addRow(data);
			
			//초기값으로 변경
			jTextField_Name.setText(null);
			jSpinner.setValue(20);
			btnGroup.clearSelection();
			jTextField_PhoneNumber.setText(null);
			jTextField_Address.setText(null);
		}
	}

	void jButton_Change_ActionListener(ActionEvent e) {
		LOGGER.debug("Change click!!");
	}

}

class TestPanel_jButton_Add_ActionListener implements ActionListener {

	private TestPanel testPanel;

	public TestPanel_jButton_Add_ActionListener(TestPanel testPanel) {
		this.testPanel = testPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == testPanel.btn_Add) {
			testPanel.jButton_Add_ActionListener(e);
		}
		else if (e.getSource() == testPanel.btn_Change) {
			testPanel.jButton_Change_ActionListener(e);
		}
		
	}
}