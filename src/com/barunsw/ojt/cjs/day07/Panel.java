package com.barunsw.ojt.cjs.day07;

import java.awt.Color;
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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Panel extends JPanel {
	private static final Logger log = LoggerFactory.getLogger(Panel.class);
	private final int COLUMN_INDEX_NAME			= 0;
	private final int COLUMN_INDEX_AGE			= 1;
	private final int COLUMN_INDEX_GENDER		= 2;
	private final int COLUMN_INDEX_ADDRESS		= 3;
	private final int COLUMN_INDEX_PERSON		= 4;
	private AddressBookInterface addressBookIf = new MemAddressBookImpl();

	private final Dimension LABEL_SIZE = new Dimension(80, 22);
	private final Dimension BUTTON_SIZE = new Dimension(80, 22);

	private GridBagLayout grid = new GridBagLayout();

	private JLabel jLabel_Name = new JLabel("Name");
	private JLabel jLabel_Gender = new JLabel("Gender");
	private JLabel jLabel_Age = new JLabel("Age");
	private JLabel jLabel_Address = new JLabel("Address");

	private JRadioButton jRadio_Man = new JRadioButton("Man");
	private JRadioButton jRadio_Woman = new JRadioButton("Woman");
	
	private JTextField jTextField_Age = new JTextField(100);
	private JTextField jTextField_Name = new JTextField();
	private JTextArea jTextArea_Address = new JTextArea();
	
	private JScrollPane jScrollPane_Address = new JScrollPane();
	private JScrollPane jScrollPane_Table = new JScrollPane();

	private JButton jButton_Add = new JButton("Add");
	private JButton jButton_Delete = new JButton("Delete");
	private JButton jButton_Update = new JButton("Update");
	private JButton jButton_Reload = new JButton("Reload");

	private JPanel jPanel_Gender = new JPanel();
	private JPanel jPanel_Button = new JPanel();

	private ButtonGroup ButtonGroup_Gender = new ButtonGroup();
	private JTable jTable_List = new JTable();
	
	private CommonTableModel tableModel = new CommonTableModel();

	private JPopupMenu popupMenu = new JPopupMenu();
	private JMenuItem jMenuItem_Delete = new JMenuItem("삭제");
	public Panel() {
		try {
			initComponent();
			initTable();
			initPopupMenu();

		} catch (Exception e) {
			log.error(e.getMessage() + e);
		}
	}

	private void initComponent() throws Exception{
		this.setLayout(grid);
		jPanel_Button.setLayout(grid);
		jPanel_Gender.setLayout(grid);
		//Panel의 Layout을 지정 
		jLabel_Name.setPreferredSize(LABEL_SIZE);
		jLabel_Gender.setPreferredSize(LABEL_SIZE);
		jLabel_Address.setPreferredSize(LABEL_SIZE);

		jTextField_Name.setPreferredSize(new Dimension(120, 22));
		jRadio_Man.setPreferredSize(new Dimension(80, 22));
		jRadio_Woman.setPreferredSize(new Dimension(80, 22));
		
		jButton_Add.setPreferredSize(BUTTON_SIZE);
		jButton_Delete.setPreferredSize(BUTTON_SIZE);
		jButton_Reload.setPreferredSize(BUTTON_SIZE);
		jButton_Update.setPreferredSize(BUTTON_SIZE);
		
		jScrollPane_Address.setPreferredSize(new Dimension(100, 100));
		jScrollPane_Address.setMinimumSize(new Dimension(100, 100));
		jScrollPane_Address.getViewport().add(jTextArea_Address);
		
		this.add(jLabel_Name,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0
						));
		this.add(jTextField_Name, 
				new GridBagConstraints(1, 0, 1, 1, 
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 0, 5, 5),
						0, 0
						));	
		this.add(jLabel_Gender, 
				new GridBagConstraints(0, 1, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0,0
						));
		this.add(jLabel_Age, 
				new GridBagConstraints(0, 2, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0,0
						));
		this.add(jTextField_Age, 
				new GridBagConstraints(1, 2, 1, 1,
						0.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0,0
						));
		this.add(jPanel_Gender,
				new GridBagConstraints(1,1,1,1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0,0,0,0),
						0,0
						));
		this.add(jLabel_Address,
				new GridBagConstraints(0, 3, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0,0
						));
		this.add(jScrollPane_Address, 
				new GridBagConstraints(1,3,1,1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0,0
						));
		this.add(jScrollPane_Table,  
				new GridBagConstraints(0, 5, 2, 1, 
						1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0, 0
						));
		//=================================================================
		//JButton Panel생성 후 버튼 추가
		this.add(jPanel_Button, 
				new GridBagConstraints(0, 4, 2, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0,0,0,0),
						0,0
						));
		jPanel_Button.add(jButton_Add,
				new GridBagConstraints(0, 0, 1, 1, 
						1.0, 0.0,
						GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
						new Insets(0, 5, 5, 5),
						0, 0
						));	
		jPanel_Button.add(jButton_Delete,
				new GridBagConstraints(1, 0, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0
						));	
		jPanel_Button.add(jButton_Reload,
				new GridBagConstraints(2, 0, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0
						));
		jPanel_Button.add(jButton_Update,
				new GridBagConstraints(3, 0, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0
						));
		jScrollPane_Table.getViewport().add(jTable_List);
		jScrollPane_Address.getViewport().add(jTextArea_Address);

		//===================================================================
		//radio버튼 Gender 패널에 추가
		//radio 버튼 ButtonGroup로 묶기
		jPanel_Gender.add(jRadio_Man, 
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0
				));
		jPanel_Gender.add(jRadio_Woman, 
				new GridBagConstraints(1, 0, 1, 1,
				1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
				new Insets(0, 0, 5, 5),
				0, 0
		));
		ButtonGroup_Gender.add(jRadio_Man);
		ButtonGroup_Gender.add(jRadio_Woman);
		//===================================================================
		jButton_Add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					AddressVo newAddress = new AddressVo();
					newAddress.setName(jTextField_Name.getText()); //jTextField_Name에 적힌 텍스트값 가져옴
					newAddress.setAddress(jTextArea_Address.getText());// jTextArea_Address에 적힌 텍스트값 가져옴
					addressBookIf.insertAddress(newAddress);
					initData();
				}
				catch (Exception ex) {
					log.error(ex.getMessage(), ex);
				}
			}
		});
		jButton_Reload.addActionListener(new Panel_jButton_Reload_ActionListener(this));
	}

	private void initTable() {
		Vector<String> columnData = new Vector<>(); //테이블의 컬럼값 저장
		columnData.add("Name");
		columnData.add("Gender");
		columnData.add("Age");
		columnData.add("Address");

		tableModel.setColumn(columnData); 
		jTable_List.setModel(tableModel);
		jTable_List.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.getModifiersEx() != MouseEvent.BUTTON1_DOWN_MASK) {
					int selectedRow = jTable_List.getSelectedRow(); // 클릭하면 테이블의 행 정보를 가져옴
					if (selectedRow >= 0) { // 클릭하면 selectRow값으 0보다 크므로
						popupMenu.show(Panel.this.jTable_List, e.getX(), e.getY()); // 해당 행의 좌표값에 팝업창을 띄워줌
					}
				}
			}
		});
	}

	private void initData() {
		Vector data = new Vector();
		List<AddressVo> addressList = addressBookIf.selectAddressList(new AddressVo());
		for (AddressVo address : addressList) {
			Vector inputData = new Vector();
			inputData.add(address.getName());
			inputData.add(address.getAge());
			inputData.add(address.getGender());
			inputData.add(address.getAddress());
			
			log.debug(inputData +"");
			data.add(inputData);
		}
		tableModel.setData(data);
		tableModel.fireTableDataChanged(); //테이블 값 갱신한다.
	}

	void jButton_Add_ActionListener(ActionEvent e) {
	}

	void jButton_Reload_ActionListener(ActionEvent e) {
		initData();
	}

	private void deleteData(int selectedRow) {
		Object value = tableModel.getValueAt(selectedRow, COLUMN_INDEX_PERSON);
		if (value instanceof AddressVo) {
			try {
				AddressVo addressVo = (AddressVo) value;
				addressBookIf.deleteAddress(addressVo);
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
			}
		}
	}

	private void initPopupMenu() {
		popupMenu.add(jMenuItem_Delete);

		jMenuItem_Delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = jTable_List.getSelectedRow();
				if (selectedRow >= 0) {
					deleteData(selectedRow);
					initData();
				}
			}
		});
	}
}

class Panel_jButton_Add_ActionListener implements ActionListener {
	private Panel adaptee;

	public Panel_jButton_Add_ActionListener(Panel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton_Add_ActionListener(e);
	}
}

class Panel_jButton_Reload_ActionListener implements ActionListener {
	private Panel adaptee;

	public Panel_jButton_Reload_ActionListener(Panel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton_Reload_ActionListener(e);
	}
}
