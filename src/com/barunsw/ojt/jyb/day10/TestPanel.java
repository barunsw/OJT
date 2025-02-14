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
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Properties;
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

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.jyb.day14.RmiAddressBookInterface;
import com.barunsw.ojt.vo.AddressVo;

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestPanel.class);

	private static RmiAddressBookInterface addressBookInterface; //TestPanel.addressBookInterface라고 쓰는 경우에만 선언

	static { //연결을 한 번 하고 끊어버리기 때문에 static을 사용하면 안됨
		try {
			// config.properties 파일을 읽어들임
			Properties properties = new Properties();
//			try (Reader reader = Resources.getResourceAsReader("config.properties")) {
//				properties.load(reader);
//			}

			// address_if_class 프로퍼티 값을 읽어옴
//			String addressIfClassName = properties.getProperty("address_socket");
//			Object o = null;
//			System.out.println(System.getProperty("user.dir"));
//			String host = properties.getProperty("host");
//			int port = Integer.parseInt(properties.getProperty("port2"));

//			if (addressIfClassName == null) {
//				throw new RuntimeException("address_if_class 프로퍼티가 설정되지 않았습니다.");
//			}

			// 클래스를 동적으로 로드
			// Class<?> clazz = Class.forName(addressIfClassName);

			// addressBookInterface = new SocketAddressBookImpl(host, port);
			// addressBookInterface = (RmiAddressBookInterface) new RmiAddressBookImpl();

			// Registry registry = LocateRegistry.getRegistry(host, port);
			Registry registry = LocateRegistry.getRegistry("localhost", 50004);
			Remote remote = registry.lookup("ADDRESSBOOK");
			if (remote instanceof RmiAddressBookInterface) {
				addressBookInterface = (RmiAddressBookInterface) remote;
			} else {
				throw new RuntimeException("ADDRESSBOOK이 RmiAddressBookInterface로 등록되지 않았습니다.");
			}

		}
		catch (Exception e) {
			throw new RuntimeException("config.properties 로드 실패", e);
		}
	}

	// private AddressBookInterface addressBookInterface = new
	// MybatisAddressBookImpl();
	// private AddressBookInterface addressBookInterface = new
	// JdbcAddressBookImpl();
	// private AddressBookInterface addressBookInterface = new
	// FileAddressBookImpl();
	// private AddressBookInterface addressBookInterface = new
	// ObjectAddressBookImpl();

	private final int COLUMN_INDEX_NAME = 0;
	private final int COLUMN_INDEX_AGE = 1;
	private final int COLUMN_INDEX_GENDER = 2;
	private final int COLUMN_INDEX_PHONE = 3;
	private final int COLUMN_INDEX_ADDRESS = 4;
	private final int COLUMN_INDEX_PERSON = 5;

	private int selectedRow = 0;
	private final Dimension LABEL_SIZE = new Dimension(80, 30);
	private final Dimension SIZE = new Dimension(120, 30);

	private CommonTableModel tableModel = new CommonTableModel();
	private CardLayout cardLayout = new CardLayout();

	private JTable jTable_Result = new JTable();
	private JTable jTable = new JTable();

	private JPanel jPanel_Form = new JPanel();
	private JLabel jLabel_Name = new JLabel("이름");
	private JTextField jTextField_Name = new JTextField();
	private JLabel jLabel_Age = new JLabel("나이");
	private JSpinner jSpinner_Age = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
	private JLabel jLabel_Gender = new JLabel("성별");
	private JRadioButton jRadioButton_Boy = new JRadioButton("남자");
	private JRadioButton jRadioButton_Girl = new JRadioButton("여자");

	private JPanel jPanel_Command = new JPanel();
	private JLabel jLabel_Phone = new JLabel("전화번호");
	private JTextField jTextField_Phone = new JTextField();
	private JLabel jLabel_Address = new JLabel("주소");
	private JTextField jTextField_Address = new JTextField();

	private JPanel jPanel_Table = new JPanel();
	private JButton jButton_Add = new JButton("추가");
	private JButton jButton_Update = new JButton("변경");

	private JPopupMenu jPopupMenu = new JPopupMenu();
	private JMenuItem jMenuItem_Delete = new JMenuItem("삭제");
	private JScrollPane jScrollPane_Table = new JScrollPane();
	private GridBagLayout gridBagLayout = new GridBagLayout();

	public TestPanel() {
		try {
			initRmi();
			initComponent();
			initTable();
			initreset();
			initData();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initRmi() {
		
	}

	private void initreset() {

		CommonTableModel model = (CommonTableModel) jTable_Result.getModel();
		model.setNumRows(0);

		// 테이블 첫번째 로우 선택
		// jTable_Result.setRowSelectionInterval(0, 0);
	}

	public void initComponent() throws Exception {
		// Layout 설정
		this.setLayout(gridBagLayout);
		jPanel_Form.setLayout(gridBagLayout);
		jPanel_Command.setLayout(gridBagLayout);
		jPanel_Table.setLayout(gridBagLayout);
		jScrollPane_Table.setViewportView(jTable_Result);
		// Form 내용 추가
		jPanel_Form.add(jLabel_Name, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

		jPanel_Form.add(jTextField_Name, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

		jPanel_Form.add(jLabel_Age, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

		jPanel_Form.add(jSpinner_Age, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

		jPanel_Form.add(jLabel_Gender, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

		jPanel_Form.add(jRadioButton_Boy, new GridBagConstraints(5, 0, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

		jPanel_Form.add(jRadioButton_Girl, new GridBagConstraints(6, 0, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

		jLabel_Phone.setPreferredSize(new Dimension(120, 22));
		jPanel_Form.add(jLabel_Phone, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));

		jPanel_Form.add(jTextField_Phone, new GridBagConstraints(1, 1, 6, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		jPanel_Form.add(jLabel_Address, new GridBagConstraints(0, 2, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));

		jPanel_Form.add(jTextField_Address, new GridBagConstraints(1, 2, 6, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		jPanel_Command.add(jButton_Add, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.VERTICAL, new Insets(0, 0, 5, 5), 0, 0));

		jPanel_Command.add(jButton_Update, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		jPanel_Table.add(jScrollPane_Table, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));

		this.add(jPanel_Form, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		this.add(jPanel_Command, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		this.add(jPanel_Table, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		jButton_Add.addActionListener(new TestPanel_jButton_Add_ActionListener(this));

		jButton_Update.addActionListener(new TestPanel_jButton_Update_ActionListener(this));

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

			if (jRadioButton_Boy.isSelected()) {
				oneUser.setGender(Gender.toGender(jRadioButton_Boy.getText()));
			} else if (jRadioButton_Girl.isSelected()) {
				oneUser.setGender(Gender.toGender(jRadioButton_Girl.getText()));
			} else {
				JOptionPane.showMessageDialog(TestPanel.this, "값을 입력하세요.", "경고", JOptionPane.ERROR_MESSAGE);
			}
			oneUser.setPhone(jTextField_Phone.getText());
			oneUser.setAddress(jTextField_Address.getText());

			try {
				addressBookInterface.insertAddress(oneUser);
			}
			catch (Exception e) {
				LOGGER.error("데이터 삽입 중 오류 발생: " + oneUser, e);
				JOptionPane.showMessageDialog(TestPanel.this, "삽입 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
			}

			selectedRow = jTable_Result.getSelectedRow();

			initreset();
			initData();
		}
		catch (Exception e) {
			LOGGER.error("데이터 처리 오류", e);
			JOptionPane.showMessageDialog(TestPanel.this, "삽입 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void updateData() throws Exception {
		List<AddressVo> personList = addressBookInterface.selectAddressList(new AddressVo());
		try {
			AddressVo onePerson = new AddressVo();
			int selectedRow = jTable_Result.getSelectedRow();

			int age = (Integer) jSpinner_Age.getValue();

			onePerson = personList.get(selectedRow);

			onePerson.setName(jTextField_Name.getText());

			onePerson.setAge(age);

			if (jRadioButton_Boy.isSelected()) {
				onePerson.setGender(Gender.toGender(jRadioButton_Boy.getText()));
			} else if (jRadioButton_Girl.isSelected()) {
				onePerson.setGender(Gender.toGender(jRadioButton_Girl.getText()));
			} else {
				JOptionPane.showMessageDialog(TestPanel.this, "값을 선택하세요.", "경고", JOptionPane.ERROR_MESSAGE);
			}

			onePerson.setPhone(jTextField_Phone.getText());
			onePerson.setAddress(jTextField_Address.getText());

			Object value = tableModel.getValueAt(selectedRow, COLUMN_INDEX_PERSON);
			if (value instanceof AddressVo) {
				try {
					AddressVo addressVo = (AddressVo) value;
					addressBookInterface.updateAddress(addressVo);
				}
				catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			}

			addressBookInterface.updateAddress(onePerson);
			initreset();
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

	private void initData() throws Exception {
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

	private void deleteData() throws Exception {
		Object value = tableModel.getValueAt(selectedRow, COLUMN_INDEX_PERSON);

		if (value instanceof AddressVo) {
			try {
				AddressVo addressVo = (AddressVo) value;
				addressBookInterface.deleteAddress(addressVo);
			}
			catch (Exception ex) {
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

	void jButton_Update_ActionListener(ActionEvent e) throws Exception {
		updateData();
	}

	void jTable_Result_MouseListener(MouseEvent e) throws Exception {
		List<AddressVo> personList = addressBookInterface.selectAddressList(new AddressVo());
		try {
			AddressVo onePerson = new AddressVo();

			selectedRow = jTable_Result.getSelectedRow();

			onePerson = personList.get(selectedRow);

			jTextField_Name.setText(onePerson.getName());
			jSpinner_Age.setValue(onePerson.getAge());
			if (onePerson.getGender() == Gender.toGender("남")) {
				jRadioButton_Boy.setSelected(true);
			} else {
				jRadioButton_Girl.setSelected(true);
			}
			jTextField_Phone.setText(onePerson.getPhone());
			jTextField_Address.setText(onePerson.getAddress());

			if (e.getModifiersEx() != MouseEvent.BUTTON1_DOWN_MASK) {

			}

			if (e.getButton() == 3) { // 오른쪽 마우스 눌렀을 때만
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
	void jMenuItem_Delete_ActionListener(ActionEvent e) throws Exception {
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
		try {
			adaptee.jButton_Update_ActionListener(e);
		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class TestPanel_jMenuItem_Delete_ActionListener implements ActionListener {
	private TestPanel adaptee;

	public TestPanel_jMenuItem_Delete_ActionListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			adaptee.jMenuItem_Delete_ActionListener(e);
		}
		catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class TestPanel_jTable_Result_MouseListener extends MouseAdapter {
	private TestPanel adaptee;

	public TestPanel_jTable_Result_MouseListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			adaptee.jTable_Result_MouseListener(e);
		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}