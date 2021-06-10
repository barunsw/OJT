package com.barunsw.ojt.iwkim.day09;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.AddressBookInterface;
import com.barunsw.ojt.iwkim.common.PersonVO;

public class MyTestPanel extends JPanel{
	private static Logger LOGGER = LogManager.getLogger(MyTestPanel.class);
	
	private AddressBookInterface addressBook = new DBAdressBookImpl();
	
	private final Dimension LABEL_DIMENSION = new Dimension(90, 22);
	
	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	private JLabel jLabel_Name    = new JLabel("이름");
	private JLabel jLabel_Age     = new JLabel("나이");
	private JLabel jLabel_Gender  = new JLabel("성별");
	private JLabel jLabel_Phone   = new JLabel("전화번호");
	private JLabel jLabel_Address = new JLabel("주소");
	
	private JTextField jTextField_Name    = new JTextField();
	private JTextField jTextField_Phone   = new JTextField();
	private JTextField jTextField_Address = new JTextField();
	
	// setValue와 setPrefferdSize로 설정해야함.
	private JSpinner jSpinner_Age = new JSpinner();
	
	private ButtonGroup buttonGroup_Gender  = new ButtonGroup();
	private JRadioButton jRadioButton_Man   = new JRadioButton("남자");
	private JRadioButton jRadioButton_Woman = new JRadioButton("여자");
	
	JPanel jPanel_Command  = new JPanel();
	JButton jButton_Add    = new JButton("추가");
	JButton jButton_Change = new JButton("변경");
	
	// 테이블 생성 테이블도 마찬가지로 ScrollPane과 같이 쓰여야 한다.
	// 또한 테이블은 직접 핸들링해주지 않고 테이블 모델 클래스를 따로 만들어 핸들링을 해주어야한다.
	// 우리는 CommonTableModel을 사용한다.
	private JTable jTable_Result           = new JTable();
	private JScrollPane jScrollPane_Result = new JScrollPane();
	private CommonTableModel tableModel    = new CommonTableModel();
	
	public MyTestPanel() {
		try {
			initComponent();
			initTable();
			initTableData();
			initEvent();
			initPopupMenu();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void initComponent() throws Exception {
		
		this.setLayout(gridBagLayout);
		jPanel_Command.setLayout(gridBagLayout);
		
		this.setBackground(Color.white);
		jPanel_Command.setBackground(Color.white);
		
		Font font = new Font("NanumGothic", Font.PLAIN, 12);
		
		jLabel_Name.setFont(font);
		jLabel_Age.setFont(font);
		jLabel_Gender.setFont(font);
		jLabel_Phone.setFont(font);
		jLabel_Address.setFont(font);
		jTextField_Name.setFont(font);
		jTextField_Phone.setFont(font);
		jTextField_Address.setFont(font);
		jSpinner_Age.setFont(font);
		jRadioButton_Man.setFont(font);
		jRadioButton_Woman.setFont(font);
		jButton_Add.setFont(font);
		jButton_Change.setFont(font);
		
		jLabel_Name.setPreferredSize(LABEL_DIMENSION);
		jLabel_Age.setPreferredSize(LABEL_DIMENSION);
		jLabel_Gender.setPreferredSize(LABEL_DIMENSION);
		jLabel_Phone.setPreferredSize(LABEL_DIMENSION);
		jLabel_Address.setPreferredSize(LABEL_DIMENSION);
		jTextField_Name.setPreferredSize(LABEL_DIMENSION);
		jTextField_Phone.setPreferredSize(LABEL_DIMENSION);
		jTextField_Address.setPreferredSize(LABEL_DIMENSION);
		jSpinner_Age.setPreferredSize(new Dimension(90, 22));
		jButton_Add.setPreferredSize(new Dimension(75, 22));
		jButton_Change.setPreferredSize(new Dimension(75, 22));
		
		
		jLabel_Name.setMinimumSize(LABEL_DIMENSION);
		jLabel_Age.setMinimumSize(LABEL_DIMENSION);
		jLabel_Gender.setMinimumSize(LABEL_DIMENSION);
		jLabel_Phone.setMinimumSize(LABEL_DIMENSION);
		jLabel_Address.setMinimumSize(LABEL_DIMENSION);
		jTextField_Name.setMinimumSize(LABEL_DIMENSION);
		jTextField_Phone.setMinimumSize(LABEL_DIMENSION);
		jTextField_Address.setMinimumSize(LABEL_DIMENSION);
		jSpinner_Age.setMinimumSize(new Dimension(130, 22));
		jButton_Add.setMinimumSize(new Dimension(75, 22));
		jButton_Change.setMinimumSize(new Dimension(75, 22));
		
		this.add(jLabel_Name, new GridBagConstraints(
				0, 0, 1, 1,
				0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 25, 5, 5),
				0, 0));
		
		this.add(jTextField_Name, new GridBagConstraints(
				1, 0, 1, 1,
				1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 0, 5, 5),
				0, 0));
		
		this.add(jLabel_Age, new GridBagConstraints(
				2, 0, 1, 1,
				0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.BOTH,
				new Insets(5, 20, 5, 0),
				0, 0));
		
		jSpinner_Age.setValue(30);
		
		this.add(jSpinner_Age, new GridBagConstraints(
				3, 0, 1, 1,
				1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH,
				new Insets(5, 0, 5, 5),
				0, 0));
		
		this.add(jLabel_Gender, new GridBagConstraints(
				4, 0, 1, 1,
				0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 45, 5, 5),
				0, 0));
		
		buttonGroup_Gender.add(jRadioButton_Man);
		buttonGroup_Gender.add(jRadioButton_Woman);
		
		this.add(jRadioButton_Man, new GridBagConstraints(
				5, 0, 1, 1,
				0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(5, 0, 5, 5),
				0, 0));
		
		this.add(jRadioButton_Woman, new GridBagConstraints(
				6, 0, 1, 1,
				0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(5, 0, 5, 55),
				0, 0));
		
		this.add(jLabel_Phone, new GridBagConstraints(
				0, 1, 1, 1,
				0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 25, 5, 5),
				0, 0));
		
		this.add(jTextField_Phone, new GridBagConstraints(
				1, 1, 6, 1,
				0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 25),
				0, 0));
		
		this.add(jLabel_Address, new GridBagConstraints(
				0, 2, 1, 1,
				0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 25, 5, 5),
				0, 0));
		
		this.add(jTextField_Address, new GridBagConstraints(
				1, 2, 6, 1,
				0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 25),
				0, 0));
		
		this.add(jPanel_Command, new GridBagConstraints(
				0, 3, 7, 1,
				0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 25, 5, 25),
				0, 0));
		
		jPanel_Command.add(jButton_Add, new GridBagConstraints(
				0, 0, 1, 1,
				1.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 5),
				0, 0));
		
		jPanel_Command.add(jButton_Change, new GridBagConstraints(
				1, 0, 1, 1,
				0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0),
				0, 0));
		
		this.add(jScrollPane_Result, new GridBagConstraints(
				0, 4, 7, 1,
				1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 25, 20, 25),
				0, 0));	
		
		jScrollPane_Result.getViewport().add(jTable_Result);
	}
	
	private void initTable() throws Exception {
		Vector<String> columnData = new Vector<>();
		columnData.add("이름");
		columnData.add("나이");
		columnData.add("성별");
		columnData.add("전화번호");
		columnData.add("주소");
		
		tableModel.setColumnData(columnData);
		jTable_Result.setModel(tableModel);
		jTable_Result.setRowHeight(22);
	}
	
	// DB에 있는 내용들 Vector<Vector> data에 담기
	private void initTableData() throws Exception{
		List<PersonVO> personInfoList = new ArrayList<>();
		
		personInfoList = addressBook.selectList();
		
		if (personInfoList.size() > 0) {
			for (PersonVO person : personInfoList) {
				Vector onePerson = new Vector();
				onePerson.add(person.getName());
				onePerson.add(person.getAge());
				onePerson.add(person.getGender());
				onePerson.add(person.getPhone());
				onePerson.add(person.getAddress());
				tableModel.addOnePerson(onePerson);
			}
			tableModel.fireTableDataChanged();
		}
		// 단일 선택
		jTable_Result.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// 마우스 우클릭 이벤트 
		
	}
	
	private void initEvent() throws Exception {
		jButton_Add.addActionListener(new MyTestPanel_jButton_Add_ActionListener(this));
	}

	private void initPopupMenu() {
		// TODO Auto-generated method stub
		
	}
	
	void jButton_Add_actionPerformed(ActionEvent e) {
		
		PersonVO onePerson = new PersonVO();
		String input_Name = jTextField_Name.getText();
		int input_Age = (int)jSpinner_Age.getValue();
		String input_Gender = null;
		if (jRadioButton_Man.isSelected()) {
			input_Gender = "남자";
		}
		else if (jRadioButton_Woman.isSelected()) {
			input_Gender = "여자";
		}

		String input_phone = jTextField_Phone.getText();
		String input_Address = jTextField_Address.getText();
		
		onePerson.setName(input_Name);
		onePerson.setAge(input_Age);
		onePerson.setGender(input_Gender);
		onePerson.setPhone(input_phone);
		onePerson.setAddress(input_Address);
		
		if (IsEmptyInput(onePerson)) {
			Vector personInfo = new Vector();
			personInfo.add(input_Name);
			personInfo.add(input_Age);
			personInfo.add(input_Gender);
			personInfo.add(input_phone);
			personInfo.add(input_Address);
			//tableModel.addOnePerson(personInfo);
			//tableModel.fireTableDataChanged();
			
			try {
				addressBook.insertPerson(onePerson);
				initTableData();
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
			// resetModel() CommonTableModel tableModel의 data를 초기화메서드 생성?
			inputReset(); 
			tableModel.fireTableDataChanged();
			JOptionPane.showMessageDialog(null, "정보가 추가되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
		}	
	}
	
	private void inputReset() {
		jTextField_Name.setText(null);
		jSpinner_Age.setValue(30);
		buttonGroup_Gender.clearSelection();
		jTextField_Phone.setText(null);
		jTextField_Address.setText(null);
	}
	
	private boolean IsEmptyInput(PersonVO onePerson) {
		String regPhone = "^01(?:0|1|[6-9])[-]?(\\d{3}|\\d{4})[-]?(\\d{4})$";
		if ( onePerson.getName().isEmpty() ) {
			JOptionPane.showMessageDialog(this, "이름을 입력해주세요.", "ERROR", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else if (onePerson.getAge()<1) {
			JOptionPane.showMessageDialog(this, "나이는 음수가 될 수 없습니다.", "ERROR", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else if (onePerson.getGender() == null) {
			JOptionPane.showMessageDialog(this, "성별을 선택해주세요.", "ERROR", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else if (onePerson.getPhone() == null || !onePerson.getPhone().matches(regPhone)) {
			LOGGER.debug("phone : " + onePerson.getPhone());
			JOptionPane.showMessageDialog(this, "번호형식이 맞지 않습니다.", "ERROR", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else if (onePerson.getAddress().isEmpty()) {
			JOptionPane.showMessageDialog(this, "주소를 입력해주세요.", "ERROR", JOptionPane.WARNING_MESSAGE);
			return false;
		}else if (IsDuplicatedName(onePerson.getName())) {
			JOptionPane.showMessageDialog(this, "이미 등록된 이름입니다..", "ERROR", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	private boolean IsDuplicatedName(String name) {
		for (int i = 1; i <= tableModel.getRowCount(); i++) {
			if (tableModel.getValueAt(i, 0).equals(name)) {
				return true;
			}
		}
		return false;
	}
}

class MyTestPanel_jButton_Add_ActionListener implements ActionListener {
	private static Logger LOGGER = LogManager.getLogger();
	
	MyTestPanel adaptee;
	
	public MyTestPanel_jButton_Add_ActionListener(MyTestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		LOGGER.info("e.getSource() : " + e.getSource());
		adaptee.jButton_Add_actionPerformed(e);	
	}
}



























