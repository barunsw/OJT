package com.barunsw.ojt.sjcha.day08;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.ButtonGroup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.sjcha.day08.AddressVo;
import com.barunsw.ojt.sjcha.day08.TestFrame;
import com.barunsw.ojt.sjcha.day08.TestPanel;

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);
	
	private final int COLUMN_INDEX_NAME			= 0;
	private final int COLUMN_INDEX_AGE			= 1;
	private final int COLUMN_INDEX_GENDER		= 2;
	private final int COLUMN_INDEX_PHONE		= 3;
	private final int COLUMN_INDEX_ADDRESS		= 4;
	private final int COLUMN_INDEX_PERSON		= 5;
	
	private int selectedRow = 0;
	private final Dimension LABEL_SIZE 		= new Dimension(80, 30);
	private final Dimension SIZE 		= new Dimension(120, 30);
	
	// layout Object 생성
	private GridBagLayout gridBagLayout 	= new GridBagLayout();
	
	// panel 
	private JPanel jPanel_Box 				= new JPanel();
	private JPanel jPanel_RadioButton 		= new JPanel();
	private JPanel jPanel_Button 			= new JPanel();
	
	private JPanel jPanel_Phone 			= new JPanel();
	
	// component
	private JLabel jLabel_Name 				= new JLabel("이름");
	private JLabel jLabel_Age 				= new JLabel("나이");
	private JLabel jLabel_Gender 			= new JLabel("성별");
	private JLabel jLabel_Phone				= new JLabel("전화번호");
	private JLabel jLabel_Address			= new JLabel("주소");
	
	private JTextField jTextField_Name 		= new JTextField();
	
	private JSpinner jSpinner_Age 			= new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
	
	private JRadioButton jRadioButton_Man 	= new JRadioButton("남");
	private JRadioButton jRadioButton_Woman = new JRadioButton("여");
	private ButtonGroup ButtonGroup 		= new ButtonGroup();
	
	private JTextField jTextField_Phone 	= new JTextField();
	
	private JTextArea jTextField_Address		= new JTextArea();
	
	private JScrollPane jScrollPane_Table 	= new JScrollPane(); 
	private JTable jTable_Result 			= new JTable();
	private CommonTableModel tableModel 	= new CommonTableModel();
			
	private JButton jButton_Add 	= new JButton("추가");
	private JButton jButton_Update 	= new JButton("변경");
	
	private JPopupMenu jPopupMenu = new JPopupMenu();
	private JMenuItem jMenuItem_Delete = new JMenuItem("삭제");
	
	//private AddressBookInterface addressBookInterface = new FileAddressBookImplement();
	 private AddressBookInterface addressBookInterface = new DbAddressBookImplement();
	//private AddressBookInterface addressBookInterface = new MybatisAddressBookImplement();
	 
	// TestPanel의 생성자
	public TestPanel() {
		try {
			initComponent();
			initTable();
			initreset();
			initData();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	public void initComponent() throws Exception {
		this.setLayout(gridBagLayout);
		jPanel_RadioButton.setLayout(gridBagLayout);
		jPanel_Button.setLayout(gridBagLayout);
		
		jLabel_Name.setPreferredSize(LABEL_SIZE);
		jTextField_Name.setPreferredSize(SIZE);
		
		jLabel_Age.setPreferredSize(LABEL_SIZE);
		jSpinner_Age.setPreferredSize(SIZE);
		
		jLabel_Gender.setPreferredSize(LABEL_SIZE);
		jRadioButton_Man.setPreferredSize(new Dimension(60, 22));
		jRadioButton_Woman.setPreferredSize(new Dimension(60, 22));
		
		jLabel_Phone.setPreferredSize(LABEL_SIZE);
		jTextField_Phone.setPreferredSize(SIZE);
		
		jLabel_Address.setPreferredSize(LABEL_SIZE);
		jTextField_Address.setPreferredSize(SIZE);
		
		jButton_Add.setPreferredSize(SIZE);
		jButton_Update.setPreferredSize(SIZE);
		
		this.add(jLabel_Name,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(20, 20, 20, 20),
						0, 0));
		
		this.add(jTextField_Name,
				new GridBagConstraints(1, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(20, 0, 20, 20),
						0, 0));
		
		this.add(jLabel_Age,
				new GridBagConstraints(2, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(20, 0, 20, 20),
						0, 0));
		
		this.add(jSpinner_Age,
				new GridBagConstraints(3, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(20, 0, 20, 20),
						0, 0));
		
		// 라디오 버튼을 그룹화
		ButtonGroup.add(jRadioButton_Man);
		ButtonGroup.add(jRadioButton_Woman);
		
		this.add(jLabel_Gender,
				new GridBagConstraints(4, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(20, 0, 20, 20),
						0, 0));
		
		this.add(jPanel_RadioButton,
				new GridBagConstraints(5, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(20, 0, 20, 20),
						0, 0));
		
		this.add(jLabel_Phone,
				new GridBagConstraints(0, 1, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 20, 20, 20),
						0, 0));
		
		this.add(jTextField_Phone,
				new GridBagConstraints(1, 1, 5, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 20, 20),
						0, 0));
	
		this.add(jLabel_Address,
				new GridBagConstraints(0, 2, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 20, 20, 20),
						0, 0));
		
		// 주소 textfield
		this.add(jTextField_Address,
				new GridBagConstraints(1, 2, 5, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 20, 20),
						0, 0));
		
		this.add(jPanel_Button, 
				new GridBagConstraints(0, 3, 6, 1,
						0.0, 0.0,
						GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
						new Insets(0, 20, 20, 20),
						0, 0));
		
		this.add(jScrollPane_Table,
				new GridBagConstraints(0, 4, 6, 1,
						1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 20, 20, 20),
						0, 0));
		
		jScrollPane_Table.getViewport().add(jTable_Result);
		
		jPanel_RadioButton.add(jRadioButton_Man,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0),
						0, 0));
		
		jPanel_RadioButton.add(jRadioButton_Woman,
				new GridBagConstraints(1, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0),
						0, 0));
		
		jPanel_Button.add(jButton_Add,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 20),
						0, 0));
		
		jPanel_Button.add(jButton_Update,
				new GridBagConstraints(1, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0),
						0, 0));
		
		// 추가 버튼을 눌렀을 경우
		jButton_Add.addActionListener(new TestPanel_jButton_Add_ActionListener(this));
		
		// 변경 버튼을 눌렀을 경우
		jButton_Update.addActionListener(new TestPanel_jButton_Update_ActionListener(this));
		
		// 테이블을 마우스로 클릭했을 때
		jTable_Result.addMouseListener(new TestPanel_jTable_Result_MouseListener(this));
	}
	
	private void initreset() {
		
		CommonTableModel model = (CommonTableModel)jTable_Result.getModel();
		model.setNumRows(0); 
	}
	// 테이블 생성
	private void initTable() {
		Vector<String> columnData = new Vector<>();
		
		columnData.add("이름");
		columnData.add("나이");
		columnData.add("성별");
		columnData.add("전화번호");
		columnData.add("주소");
		
		// 데이터 모델에 속성값 지정
		tableModel.setColumn(columnData);
		
		// 테이블에 데이터 모델 삽입
		jTable_Result.setModel(tableModel);
		
	}
	
	// 데이터 초기화
	private void initData() {
		
		List<AddressVo> personList = addressBookInterface.selectAddressList(new AddressVo());
		
		Vector dataLine = new Vector<>();
		
		for (AddressVo onePerson : personList) {
			LOGGER.info( "person : " + onePerson );
			Vector data = new Vector();
			data.add(onePerson.getName());
			data.add(onePerson.getAge());
			data.add(onePerson.getGender());
			data.add(onePerson.getPhone());
			data.add(onePerson.getAddress());
			data.add(onePerson);
			
			dataLine.add(data);
		}

		
		tableModel.setData(dataLine);
		
		tableModel.fireTableDataChanged();
	}
	
	private void insertData() {
		try {			
			AddressVo onePerson = new AddressVo();
			int age = (Integer)jSpinner_Age.getValue();

			onePerson.setName(jTextField_Name.getText());
			
			onePerson.setAge(age);
			
			if (jRadioButton_Man.isSelected()) {
				onePerson.setGender(Gender.toGender(jRadioButton_Man.getText()));
			}
			else if (jRadioButton_Woman.isSelected()) {
				onePerson.setGender(Gender.toGender(jRadioButton_Woman.getText()));
			}
			else {
				JOptionPane.showMessageDialog(TestPanel.this, "값을 입력하세요.", "경고", JOptionPane.ERROR_MESSAGE);
			}
			
			onePerson.setPhone(jTextField_Phone.getText());
			onePerson.setAddress(jTextField_Address.getText());
			
			addressBookInterface.insertAddress(onePerson);
			selectedRow = jTable_Result.getSelectedRow();
		
			initreset();
			initData();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void updateData() {
		List<AddressVo> personList = addressBookInterface.selectAddressList(new AddressVo());
		try {
			AddressVo onePerson = new AddressVo();
			int selectedRow = jTable_Result.getSelectedRow();
			
			int age = (Integer)jSpinner_Age.getValue();
			
			onePerson = personList.get(selectedRow);
			
			onePerson.setName(jTextField_Name.getText());
			
			onePerson.setAge(age);
			
			if (jRadioButton_Man.isSelected()) {
				onePerson.setGender(Gender.toGender(jRadioButton_Man.getText()));
			}
			else if (jRadioButton_Woman.isSelected()) {
				onePerson.setGender(Gender.toGender(jRadioButton_Woman.getText()));
			}
			else {
				JOptionPane.showMessageDialog(TestPanel.this, "값을 선택하세요.", "경고", JOptionPane.ERROR_MESSAGE);
			}
			
			onePerson.setPhone(jTextField_Phone.getText());
			onePerson.setAddress(jTextField_Address.getText());
			
			Object value = tableModel.getValueAt(selectedRow, COLUMN_INDEX_PERSON);
			if (value instanceof AddressVo) {
				try {
					AddressVo addressVo = (AddressVo)value;
					addressBookInterface.updateAddress(addressVo);
				}
				catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			}
			
			addressBookInterface.updateAddress(onePerson);
			//initreset();
			initData();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initPopupMenu() {
		jPopupMenu.add(jMenuItem_Delete);
		
		jMenuItem_Delete.addActionListener(new TestPanel_jMenuItem_Delete_ActionListener(this));
	}
	
	private void deleteData() {
		LOGGER.debug(selectedRow);
		
		Object value = tableModel.getValueAt(selectedRow, COLUMN_INDEX_PERSON);
		
		LOGGER.debug(value);
		if (value instanceof AddressVo) {
			try {
				AddressVo addressVo = (AddressVo)value;
				addressBookInterface.deleteAddress(addressVo);
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
			initreset();
			initData();
		}
	}
	
	// 추가 버튼을 눌렀을 경우 수행 
	void jButton_Add_ActionListener(ActionEvent e) {
		initreset();
		insertData();
	}
	
	void jButton_Update_ActionListener(ActionEvent e) {
		//initreset();
		updateData();
	}
	
	
	void jTable_Result_MouseListener(MouseEvent e) {
		List<AddressVo> personList = addressBookInterface.selectAddressList(new AddressVo());
		try {
			AddressVo onePerson = new AddressVo();
			
			selectedRow = jTable_Result.getSelectedRow();
			
			LOGGER.debug(personList.get(selectedRow));
			onePerson = personList.get(selectedRow);
			
			LOGGER.debug(selectedRow);
			jTextField_Name.setText(onePerson.getName());
			jSpinner_Age.setValue(onePerson.getAge());
			if (onePerson.getGender() == Gender.toGender("남")) {
				jRadioButton_Man.setSelected(true);
			}
			else {
				jRadioButton_Woman.setSelected(true);
			}
			jTextField_Phone.setText(onePerson.getPhone());
			jTextField_Address.setText(onePerson.getAddress());
			
			LOGGER.debug(selectedRow);
			
			if(e.getButton() == 3){// 오른쪽 마우스 눌렀을 때만 반응해라 // 팝업 메뉴를
				selectedRow = jTable_Result.getSelectedRow();
				initPopupMenu(); 
				jPopupMenu.show(TestPanel.this.jTable_Result, e.getX(), e.getY()); 
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	} 
	
	// 삭제 팝업을 눌렀을 때
	void jMenuItem_Delete_ActionListener(ActionEvent e) {
		if (selectedRow >= 0) {
			deleteData();
		}
	}
}

class TestPanel_jButton_Add_ActionListener implements ActionListener {
	private TestPanel adaptee;
	
	public TestPanel_jButton_Add_ActionListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		adaptee.jButton_Add_ActionListener(e);
	}
}

class TestPanel_jButton_Update_ActionListener implements ActionListener {
	private TestPanel adaptee;
	
	public TestPanel_jButton_Update_ActionListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		adaptee.jButton_Update_ActionListener(e);
	}
}

class TestPanel_jMenuItem_Delete_ActionListener implements ActionListener {
	private TestPanel adaptee;
	
	public TestPanel_jMenuItem_Delete_ActionListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		adaptee.jMenuItem_Delete_ActionListener(e);
	}
}

class TestPanel_jTable_Result_MouseListener extends MouseAdapter {
	private TestPanel adaptee;
	
	public TestPanel_jTable_Result_MouseListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		adaptee.jTable_Result_MouseListener(e);
	}
}