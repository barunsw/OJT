package com.barunsw.ojt.jyb.day10;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestPanel.class);

	private int selectedRow = 0;
	private final Dimension LABEL_SIZE = new Dimension(80, 30);
	private final Dimension SIZE = new Dimension(120, 30);

	private CommonTableModel tableModel = new CommonTableModel();
	private CardLayout cardLayout = new CardLayout();
	private JTable jTable_Result = new JTable();
	private JTable jTable = new JTable();

	private JPanel jPanel_Form = new JPanel();
	private JLabel label_Name = new JLabel("이름");
	private JTextField jTextField_Name = new JTextField();
	private JLabel label_Age = new JLabel("나이");
	private JSpinner jSpinner_Age = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
	private JLabel label_Gender = new JLabel("성별");
	private JRadioButton btnBoy = new JRadioButton("남자");
	private JRadioButton btnGirl = new JRadioButton("여자");

	private JPanel jPanel_Command = new JPanel();
	private JLabel label_Phone = new JLabel("전화번호");
	private JTextField textField_Phone = new JTextField();
	private JLabel label_Address = new JLabel("주소");
	private JTextField textField_Address = new JTextField();

	private JPanel jPanel_Table = new JPanel();
	private JButton jButton_Add = new JButton("추가");
	private JButton jButton_Update = new JButton("변경");
	private JButton jButton_Save = new JButton("저장");

	private JPopupMenu jPopupMenu = new JPopupMenu();
	private JMenuItem jMenuItem_Delete = new JMenuItem("삭제");
	private JScrollPane scrollPane = new JScrollPane();
	private GridBagLayout gridBagLayout = new GridBagLayout();

	private AddressBookInterface addressBookInterface = new DbAddressImpl();
	private FileAddressBookImpl fileAddressBookImpl = new FileAddressBookImpl();

	private final int COLUMN_INDEX_NAME = 0;
	private final int COLUMN_INDEX_AGE = 1;
	private final int COLUMN_INDEX_GENDER = 2;
	private final int COLUMN_INDEX_PHONE = 3;
	private final int COLUMN_INDEX_ADDRESS = 4;
	private final int COLUMN_INDEX_PERSON = 5;

	public TestPanel() {
		try {
			initComponent();
			initTable();
			initreset();
			initData();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initreset() {

		CommonTableModel model = (CommonTableModel) jTable_Result.getModel();
		model.setNumRows(0);
	}

	public void initComponent() throws Exception {
		jPanel_Form.setLayout(new GridBagLayout());

		// Form 내용 추가
		jPanel_Form.add(label_Name, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

		jPanel_Form.add(jTextField_Name, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

		jPanel_Form.add(label_Age, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

		jPanel_Form.add(jSpinner_Age, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

		jPanel_Form.add(label_Gender, new GridBagConstraints(4, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

		jPanel_Form.add(btnBoy, new GridBagConstraints(5, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

		jPanel_Form.add(btnGirl, new GridBagConstraints(6, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

		label_Phone.setPreferredSize(new Dimension(120, 22));
		jPanel_Form.add(label_Phone, new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));

		jPanel_Form.add(textField_Phone, new GridBagConstraints(1, 1, 7, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		jPanel_Form.add(label_Address, new GridBagConstraints(0, 2, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));

		jPanel_Form.add(textField_Address, new GridBagConstraints(1, 2, 7, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		jPanel_Command.setLayout(new GridBagLayout());

		jPanel_Command.add(new JLabel(" "), new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));

		jPanel_Command.add(jButton_Add, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));

		jPanel_Command.add(jButton_Update, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));

		jPanel_Command.add(jButton_Save, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));

		jPanel_Table.setLayout(new GridBagLayout());

		this.setLayout(new GridBagLayout());

		this.add(jPanel_Form, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		this.add(jPanel_Command, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		this.add(jPanel_Table, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		this.add(new JScrollPane(jTable_Result), new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		jButton_Add.addActionListener(new TestPanel_jButton_Add_ActionListener(this));
		jButton_Update.addActionListener(new TestPanel_jButton_Update_ActionListener(this));

		FileAddressBookImpl fileAddressBookImpl = new FileAddressBookImpl();

		jButton_Save.addActionListener(new TestPanel_jButton_Save_ActionListener(fileAddressBookImpl));

		jTable_Result.addMouseListener(new TestPanel_jTable_Result_MouseListener(this));

	}

	private void initTable() {
		Vector<String> columnData = new Vector<>();

		columnData.add("이름");
		columnData.add("나이");
		columnData.add("성별");
		columnData.add("전화번호");
		columnData.add("주소");

		tableModel.setColumn(columnData);

		jTable_Result.setModel(tableModel);

	}

	private void insertData() {
		try {
			AddressVo oneUser = new AddressVo();
			int age = (int) jSpinner_Age.getValue();

			oneUser.setName(jTextField_Name.getText());
			oneUser.setAge(age);

			if (btnBoy.isSelected()) {
				oneUser.setGender(Gender.toGender(btnBoy.getText()));
			} else if (btnGirl.isSelected()) {
				oneUser.setGender(Gender.toGender(btnGirl.getText()));
			} else {
				JOptionPane.showMessageDialog(TestPanel.this, "값을 입력하세요.", "경고", JOptionPane.ERROR_MESSAGE);
			}
			oneUser.setPhone(textField_Phone.getText());
			oneUser.setAddress(textField_Address.getText());

			addressBookInterface.insertAddress(oneUser);
			selectedRow = jTable_Result.getSelectedRow();

			initreset();
			initData();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void updateData() {
		List<AddressVo> personList = addressBookInterface.selectAddressList(new AddressVo());
		try {
			AddressVo onePerson = new AddressVo();
			int selectedRow = jTable_Result.getSelectedRow();

			int age = (Integer) jSpinner_Age.getValue();

			onePerson = personList.get(selectedRow);

			onePerson.setName(jTextField_Name.getText());

			onePerson.setAge(age);

			if (btnBoy.isSelected()) {
				onePerson.setGender(Gender.toGender(btnBoy.getText()));
			} else if (btnGirl.isSelected()) {
				onePerson.setGender(Gender.toGender(btnGirl.getText()));
			} else {
				JOptionPane.showMessageDialog(TestPanel.this, "값을 선택하세요.", "경고", JOptionPane.ERROR_MESSAGE);
			}

			onePerson.setPhone(textField_Phone.getText());
			onePerson.setAddress(textField_Address.getText());

			Object value = tableModel.getValueAt(selectedRow, 5);
			if (value instanceof AddressVo) {
				try {
					AddressVo addressVo = (AddressVo) value;
					addressBookInterface.updateAddress(addressVo);
				} catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			}

			addressBookInterface.updateAddress(onePerson);
			initreset();
			initData();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initPopupMenu() {
		jPopupMenu.add(jMenuItem_Delete);

		jMenuItem_Delete.addActionListener(new TestPanel_jMenuItem_Delete_ActionListener(this));
	}

	private void initData() {
		List<AddressVo> userList = addressBookInterface.selectAddressList(new AddressVo());
		LOGGER.info("user: " + userList);
		Vector dataVector = new Vector();

		for (AddressVo oneUser : userList) {
			LOGGER.info("user: " + oneUser);
			Vector data = new Vector();
			data.add(oneUser.getName());
			data.add(oneUser.getAge());
			data.add(oneUser.getGender());
			data.add(oneUser.getPhone());
			data.add(oneUser.getAddress());
			data.add(oneUser);

			dataVector.add(data);
		}
		tableModel.setData(dataVector);
		jTable_Result.setModel(tableModel);
	}

	private void deleteData() {
		Object value = tableModel.getValueAt(selectedRow, COLUMN_INDEX_PERSON);

		if (value instanceof AddressVo) {
			try {
				AddressVo addressVo = (AddressVo) value;
				addressBookInterface.deleteAddress(addressVo);
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
			initreset();
			initData();
		}
	}

	void jButton_Add_ActionListener(ActionEvent e) {
		initreset(); // 입력 필드 초기화
		insertData(); // 데이터 삽입

	}

	void jButton_Update_ActionListener(ActionEvent e) {
		updateData();
	}

	void jTable_Result_MouseListener(MouseEvent e) {
		List<AddressVo> personList = addressBookInterface.selectAddressList(new AddressVo());
		try {
			AddressVo onePerson = new AddressVo();

			selectedRow = jTable_Result.getSelectedRow();

			onePerson = personList.get(selectedRow);

			jTextField_Name.setText(onePerson.getName());
			jSpinner_Age.setValue(onePerson.getAge());
			if (onePerson.getGender() == Gender.toGender("남")) {
				btnBoy.setSelected(true);
			} else {
				btnGirl.setSelected(true);
			}
			textField_Phone.setText(onePerson.getPhone());
			textField_Address.setText(onePerson.getAddress());

			if (e.getModifiersEx() != MouseEvent.BUTTON1_DOWN_MASK) {

			}

			if (e.getButton() == 3) { // 오른쪽 마우스 눌렀을 때만
				selectedRow = jTable_Result.getSelectedRow();
				initPopupMenu();
				jPopupMenu.show(TestPanel.this.jTable_Result, e.getX(), e.getY());
			}
		} catch (Exception ex) {
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
		adaptee.jButton_Update_ActionListener(e);
	}
}

class TestPanel_jButton_Save_ActionListener implements ActionListener {
	private FileAddressBookImpl fileAddressBookImpl;

	public TestPanel_jButton_Save_ActionListener(FileAddressBookImpl fileAddressBookImpl) {
		this.fileAddressBookImpl = fileAddressBookImpl;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		fileAddressBookImpl.saveAddressListToFile();
	}
}

class TestPanel_jMenuItem_Delete_ActionListener implements ActionListener {
	private TestPanel adaptee;

	public TestPanel_jMenuItem_Delete_ActionListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
		adaptee.jTable_Result_MouseListener(e);
	}
}