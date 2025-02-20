package com.barunsw.ojt.phs.day08;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.io.ResolverUtil.Test;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdk.nashorn.internal.runtime.regexp.joni.Warnings;

public class FilePanel extends JPanel implements AddressBookDAO{
	private static final Logger LOGGER = LogManager.getLogger(FilePanel.class);

	private final Dimension LABEL_SIZE = new Dimension(80, 22);
 
	private JLabel jLabel_Name        = new JLabel("이름");
	private JLabel jLabel_Age         = new JLabel("나이");
	private JLabel jLabel_Gender      = new JLabel("성별");
	private JLabel jLabel_PhoneNumber = new JLabel("전화번호");
	private JLabel jLabel_Address     = new JLabel("주소");

	private JTextField jTextField_Name        = new JTextField();
	private JTextField jTextField_PhoneNumber = new JTextField();
	private JTextField jTextField_Address     = new JTextField();

	private JCheckBox jCheckbox_Man   = new JCheckBox("남자");
	private JCheckBox jCheckbox_Woman = new JCheckBox("여자");

	JButton btn_Add    = new JButton("추가");
	JButton btn_Change = new JButton("변경");
	JButton btn_Delete = new JButton("삭제");

	private ButtonGroup btnGroup = new ButtonGroup();

	private JSpinner jSpinner = new JSpinner(new SpinnerNumberModel());

	private File file = new File("data/phs/addressList.dat");
	
	private Vector<PersonVO> peopleList = new Vector<PersonVO>(); 
	
//	===========================TABLE============================
	private TableModel table_Model         = new TableModel();
	private JTable table                   = new JTable();
	private JScrollPane jScrollPane_JTable = new JScrollPane();
//	============================================================
		
	public FilePanel() {
		try {
			if(file.createNewFile()) {
				LOGGER.debug("파일이 생성 되었습니다. 파일이름: " + file.getName());
			}
			else {
				LOGGER.debug("파일이 이미 존재합니다. 파일이름: " + file.getName());
			}
			initComponent();
			initTable();
			setEventListener();
			loadAllList();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private boolean inputData_Check() {
		LOGGER.debug("inputData_Check");
		boolean allCheck = true;
		String regPhone   = "^01(?:0|1|[6-9])[-]?(\\d{3}|\\d{4})[-]?(\\d{4})$";
		
		String add_Name  = jTextField_Name.getText();
		String add_Phone = jTextField_PhoneNumber.getText();
		String add_Address = jTextField_Address.getText();

		if (add_Name.isEmpty()) {
			JOptionPane.showMessageDialog(this, "이름을 입력해주세요.", "ERROR", JOptionPane.WARNING_MESSAGE);
			allCheck = false;
		}
		else if (!(jCheckbox_Man.isSelected()) && !(jCheckbox_Woman.isSelected())) {
			JOptionPane.showMessageDialog(this, "성별을 선택해주세요.", "ERROR", JOptionPane.WARNING_MESSAGE);
			allCheck = false;
		}
		else if (add_Phone.isEmpty()) {
			JOptionPane.showMessageDialog(this, "번호를 입력해주세요.", "ERROR", JOptionPane.WARNING_MESSAGE);
			allCheck = false;
		}
		else if (!(add_Phone.matches(regPhone))) {
			JOptionPane.showMessageDialog(this, "번호형식이 맞지 않습니다.", "ERROR", JOptionPane.WARNING_MESSAGE);	
			allCheck = false;
		}
		else if (add_Address.isEmpty()) {
			JOptionPane.showMessageDialog(this, "주소를 입력해주세요.", "ERROR", JOptionPane.WARNING_MESSAGE);
			allCheck = false;
		}
		
		return allCheck;
	}
	
	private void initTable() {
		LOGGER.debug("initTable");
		Vector<String> columnData = new Vector<>();
		columnData.add("이름");
		columnData.add("나이");
		columnData.add("성별");
		columnData.add("전화번호");
		columnData.add("주소");
		
		table_Model.setColumn(columnData);
		table.setModel(table_Model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	private void setEventListener() {
		LOGGER.debug("setEventListener");
		btn_Add.addActionListener(new FilePanel_jButton_Add_ActionListener(this));
		btn_Change.addActionListener(new FilePanel_jButton_Add_ActionListener(this));
		btn_Delete.addActionListener(new FilePanel_jButton_Add_ActionListener(this));
		table.addMouseListener(new FilePanel_jTable_Result_MouseAdapter(this));
	}
	
	private void tableReset() {
		LOGGER.debug("tableReset");
		
		// tableModel의 변경을 알린다.
		table_Model.fireTableDataChanged();
		
		//초기값으로 되돌린다.
		jTextField_Name.setText(null);
		jSpinner.setValue(20);
		btnGroup.clearSelection();
		jTextField_PhoneNumber.setText(null);
		jTextField_Address.setText(null);
	}
	
	private Vector inputDataLoad() {
		LOGGER.debug("inputDataLoad");
		Vector inputData = new Vector();
		
		String name = jTextField_Name.getText();
		int age = (int)jSpinner.getValue();
		String gender = jCheckbox_Man.isSelected() ? "남자" : "여자";
		String phone = jTextField_PhoneNumber.getText();
		String address = jTextField_Address.getText();
		
		inputData.add(name);
		inputData.add(age);
		inputData.add(gender);
		inputData.add(phone);
		inputData.add(address);
		
		return inputData;
	}
	
	private void initComponent() throws Exception {
		LOGGER.debug("initComponent");
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
		
//		1번째 줄 ===== 이름, 나이, 성별 ===================================================
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

		
//		2번째 줄 ===== 전화번호 ===================================================
		this.add(jLabel_PhoneNumber, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));

		this.add(jTextField_PhoneNumber, new GridBagConstraints(1, 1, 6, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 20), 0, 0));

		
//		3번째 줄 ===== 주소 ===================================================
		this.add(jLabel_Address, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));

		this.add(jTextField_Address, new GridBagConstraints(1, 2, 6, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 20), 0, 0));

		
//		4번째 줄 ===== 버튼3가지(추가, 변경, 삭제) ===================================================
		this.add(btn_Add, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 20, 0), 0, 0));

		this.add(btn_Change, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 20, 0), 0, 0));

		this.add(btn_Delete, new GridBagConstraints(6, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 20, 20), 0, 0));
		
		
//		5번째 줄 ===== 테이블 ===================================================
		this.add(jScrollPane_JTable, new GridBagConstraints(0, 4, 7, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 20, 20, 20), 0, 0));
		jScrollPane_JTable.getViewport().add(table);
	}
	
	void jButton_Add_ActionListener() {
		LOGGER.debug("Add Click!!");
		
		boolean add_Approve = true;
		String add_Name  = jTextField_Name.getText();
		String add_Phone = jTextField_PhoneNumber.getText();
		String add_Address = jTextField_Address.getText();
		
		if (inputData_Check()) {
			for (int i=0 ; i<=table_Model.getRowCount() ; i++) {
				if ((table_Model.getValueAt(i, 0)) != null) {
					if ((table_Model.getValueAt(i, 0)).equals(add_Name)) {
						JOptionPane.showMessageDialog(this, add_Name+" 님은 이미 등록 되어있습니다.", "ERROR", JOptionPane.WARNING_MESSAGE);
						add_Approve = false;
						break;
					}
				}
				else {
					break;
				}
			}
			
			if (add_Approve) {				
				table_Model.addData(inputDataLoad());
				tableReset();
				try {
					saveAllList();
				} catch (Exception e) {
					LOGGER.debug(e);
				}
			}
		}
	}

	void jButton_Change_ActionListener() {
		LOGGER.debug("Change click!!");
		
		String change_Name = jTextField_Name.getText();
		
		if (change_Name.isEmpty()) {
			JOptionPane.showMessageDialog(this, "변경하는 대상의 이름을 입력해주세요.", "ERROR", JOptionPane.WARNING_MESSAGE);
		}
		else {
			for (int i=0 ; i<=table_Model.getRowCount() ; i++) {
				if ((table_Model.getValueAt(i, 0)) != null) {
					if ((table_Model.getValueAt(i, 0)).equals(change_Name)) {
						if (inputData_Check()) {
							int result = JOptionPane.showConfirmDialog(this, change_Name+" 님의 정보를 변경 하시겠습니까?", "정보 변경", JOptionPane.YES_NO_OPTION);
							if (result == JOptionPane.YES_OPTION) {
								table_Model.changeData(inputDataLoad(), i);
								tableReset();
								try {
									saveAllList();
								} catch (Exception e) {
									LOGGER.debug(e);
								}
							}
							break;
						}
						break;
					} 
				}
				else {
					JOptionPane.showMessageDialog(this, change_Name+" 님은 존재하지 않습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
		}
	}

	void jButton_Delete_ActionListener() {
		LOGGER.debug("Delete click!!");
		
		String remove_Name = jTextField_Name.getText();
		
		for (int i=0 ; i<=table_Model.getRowCount() ; i++) {
			if ((table_Model.getValueAt(i, 0)) != null) {
				if ((table_Model.getValueAt(i, 0)).equals(remove_Name)) {
					int result = JOptionPane.showConfirmDialog(this, remove_Name+" 님의 정보를 삭제 하시겠습니까?", "정보 삭제", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						table_Model.removeData(i);
						tableReset();
						try {
							saveAllList();
						} catch (Exception e) {
							LOGGER.debug(e);
						}
					}
					break;
				}
			}
			else {
				JOptionPane.showMessageDialog(this, remove_Name+" 님은 존재하지 않습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
	}
	
	void jTable_Result_mouseReleased() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow >= 0) {
			String name = (String)table.getValueAt(selectedRow, 0);
			int age = (int)table.getValueAt(selectedRow, 1);
			String gender = (String)table.getValueAt(selectedRow, 2);
			String phone = (String)table.getValueAt(selectedRow, 3);
			String address = (String)table.getValueAt(selectedRow, 4);

			jTextField_Name.setText(name);
			jSpinner.setValue(age);
			if (gender.equals("남자")) {
				jCheckbox_Man.setSelected(true);
			}
			else if (gender.equals("여자")) {
				jCheckbox_Woman.setSelected(true);
			}
			jTextField_PhoneNumber.setText(phone);
			jTextField_Address.setText(address);
		}
	}

	@Override
	public void loadAllList() throws Exception {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
			Object obj = null;
			while((obj = ois.readObject()) != null) {
				PersonVO vo = (PersonVO)obj;
				Vector person = new Vector();
				
				person.add(vo.getName());
				person.add(vo.getAge());
				person.add(vo.getGender());
				person.add(vo.getPhone());
				person.add(vo.getAddress());
				
				table_Model.addData(person);
			}
		}
		catch (Exception e) {
			LOGGER.debug(e.getMessage() +" 더 이상 불러올 데이터가 없습니다.");
		}
	}

	@Override
	public void saveAllList() throws Exception {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
			for(int i=0 ; i<=table_Model.getData().size() ; i++) {
				PersonVO vo = new PersonVO();

				vo.setName((String)((Vector) table_Model.getData().get(i)).get(0));
				vo.setAge((int)((Vector) table_Model.getData().get(i)).get(1));
				vo.setGender((String)((Vector) table_Model.getData().get(i)).get(2));
				vo.setPhone((String)((Vector) table_Model.getData().get(i)).get(3));
				vo.setAddress((String)((Vector) table_Model.getData().get(i)).get(4));
				
				oos.writeObject(vo);
				LOGGER.debug(vo+" 넣음!!");
			}
		}
		catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
	}

}

class FilePanel_jButton_Add_ActionListener implements ActionListener {
	
	private FilePanel testPanel;

	public FilePanel_jButton_Add_ActionListener(FilePanel testPanel) {
		this.testPanel = testPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == testPanel.btn_Add) {
			testPanel.jButton_Add_ActionListener();
		}
		else if (e.getSource() == testPanel.btn_Change) {
			testPanel.jButton_Change_ActionListener();
		}
		else if (e.getSource() == testPanel.btn_Delete) {
			testPanel.jButton_Delete_ActionListener();
		}
		
	}
}

class FilePanel_jTable_Result_MouseAdapter extends MouseAdapter {
	private FilePanel adaptee;
	
	public FilePanel_jTable_Result_MouseAdapter(FilePanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		adaptee.jTable_Result_mouseReleased();
	}
}