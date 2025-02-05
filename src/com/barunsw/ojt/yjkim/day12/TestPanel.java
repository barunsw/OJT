package com.barunsw.ojt.yjkim.day12;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




public class TestPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);
	
	private int columnIdx = 0;
	private final int TABLE_COLUMN_SEQ 		= columnIdx++; 
	private final int TABLE_COLUMN_NAME 	= columnIdx++; 
	private final int TABLE_COLUMN_GENDER  	= columnIdx++;
	private final int TABLE_COLUMN_AGE 		= columnIdx++; 
	private final int TABLE_COLUMN_ADDRESS 	= columnIdx++; 
	private final int TABLE_COLUMN_REMARKS 	= columnIdx++; 
	
	private GridBagLayout gridBagLayout = new GridBagLayout();

	
	//JTable
	private JScrollPane jScrollPane_Table = new JScrollPane();
	
	private JTable jTable_Result = new JTable();
	private CommonTableModel tableModel = new CommonTableModel();
	
	//라벨
	private JPanel jPanel_InputField = new JPanel();
	//private JLabel jLabel_Num 		 = new JLabel("번호");
	private JLabel jLabel_Name 		 = new JLabel("이름");
	private JLabel jLabel_Gender 	 = new JLabel("성별");
	private JLabel jLabel_Age 		 = new JLabel("나이");
	private JLabel jLabel_Address	 = new JLabel("주소");
	//텍스트 필드
	//private JTextField jTextField_Num 		= new JTextField(3);
	private JTextField jTextField_Name 		= new JTextField(10);
	private JRadioButton jRadioButton_Man 	= new JRadioButton("남");
	private JRadioButton jRadioButton_Woman = new JRadioButton("여");
	private JTextField jTextField_Age	 	= new JTextField(3);
	private JTextField jTextField_Address 	= new JTextField(15);
	private ButtonGroup  Gender_Group = new ButtonGroup();

	
	//JTree
	private JScrollPane jScrollPane_Tree = new JScrollPane();
	private JTree jtree = new JTree();
	private DefaultMutableTreeNode root;
	private DefaultTreeModel treeModel = new DefaultTreeModel(root);

	//Button
	private JPanel jPanel_Button 	= new JPanel();
	private JButton jButton_Add 	= new JButton("추가");
	private JButton jButton_Cancle 	= new JButton("삭제");
	private JButton jButton_Update 	= new JButton("수정");
	
//	private AddressBookInterface addressbookInter	 =  new FileAddressBookImpl();
	//private AddressBookInterface DBaddressbookInter  = new DBAddressBookImpl();
	private String selected_Name ="";
	private int selectedRow = 9999999;
	
	private final String[] hangleList2	   = {"ㄱ", "ㄴ", "ㄷ", "ㄹ", "ㅁ", 
			"ㅂ", "ㅅ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ" };
	private final String[] hangleList	   = {"ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", 
			"ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ",  
			"ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ" };
	
	Enumeration<AbstractButton> enums;
	public TestPanel() {
		try {
			initComponent();
			initTable();
			initData();
			initButtonEvent();

		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		this.setLayout(gridBagLayout);
		Gender_Group.add(jRadioButton_Man);
		Gender_Group.add(jRadioButton_Woman);

		this.add(jScrollPane_Tree,
				new GridBagConstraints(0, 0, 1, 1,
						1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		root = new DefaultMutableTreeNode("주소록");
		DefaultMutableTreeNode child = null;

		for (String treeName : hangleList) {
			child = new DefaultMutableTreeNode(treeName);
			root.add(child);
		}
		treeModel.setRoot(root);
		jtree.setModel(treeModel);
		
		jScrollPane_Tree.getViewport().add(jtree);
		this.add(jScrollPane_Table, 
				new GridBagConstraints(1, 0, 1, 1,
						1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		jScrollPane_Table.getViewport().add(jTable_Result);
		this.add(jPanel_InputField,
				new GridBagConstraints(0, 10, 6, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0),
						0, 0));
	
		
		jPanel_InputField.add(jLabel_Name,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 5, 5, 5),
						0, 0));
		
		jPanel_InputField.add(jTextField_Name,
				new GridBagConstraints(1, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));
		jPanel_InputField.add(jLabel_Gender,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 5, 5, 5),
						0, 0));
		jPanel_InputField.add(jRadioButton_Man,
				new GridBagConstraints(1, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));
		jPanel_InputField.add(jRadioButton_Woman,
				new GridBagConstraints(2, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		jPanel_InputField.add(jLabel_Age,
				new GridBagConstraints(2, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 5, 5, 5),
						0, 0));	
		
		jPanel_InputField.add(jTextField_Age,
				new GridBagConstraints(3, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));	
		
		jPanel_InputField.add(jLabel_Address,
				new GridBagConstraints(4, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 5, 5, 5),
						0, 0));	
		
		jPanel_InputField.add(jTextField_Address,
				new GridBagConstraints(3, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));	
		
		this.add(jPanel_Button,
				new GridBagConstraints(0, 11, 3, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		jPanel_Button.add(jButton_Add,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		jPanel_Button.add(jButton_Cancle,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));
		jPanel_Button.add(jButton_Update,
				new GridBagConstraints(0, 0, 1, 1,
						1.0, 0.0,
						GridBagConstraints.EAST,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));
	}
	
	private void initData() {
		try {
			/*List<AddressVo> list = addressbookInter.selectAddressList();
			LOGGER.debug(list.size());
			for(int i = 0; i < list.size(); i++) {
				Vector OneData = new Vector();

				OneData.add(i+1);
				OneData.add(list.get(i).getName());
				OneData.add(list.get(i).getGender());
				OneData.add(list.get(i).getAge());
				OneData.add(list.get(i).getAddress());
				tableModel.addData(OneData);
				//LOGGER.debug(list.get(i).getName());
			}*/
			ClientMain client = new ClientMain();
			List<AddressVo> selectList = client.addressBookIf.selectAddressList();

			tableModel.setNumRows(0);
			for(int i = 0; i < selectList.size(); i++) {
				Vector OneData = new Vector();

				OneData.add(selectList.get(i).getSeq());
				OneData.add(selectList.get(i).getName());
				OneData.add(selectList.get(i).getGender());
				OneData.add(selectList.get(i).getAge());
				OneData.add(selectList.get(i).getAddress());
				tableModel.addData(OneData);
				//LOGGER.debug(list.get(i).getName());
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

		
		
	}
	private void initButtonEvent() {
		jButton_Update.addActionListener(new Button_this_ActionListener(this));
		jButton_Add.addActionListener(new Button_this_ActionListener(this));
		jButton_Cancle.addActionListener(new Button_this_ActionListener(this));
	}
	private void initTable() {
		Vector<String> columnData = new Vector<>();
		columnData.add("번호");
		columnData.add("이름");
		columnData.add("성별");
		columnData.add("나이");
		columnData.add("주소");
		
		tableModel.setColumn(columnData);
		tableModel.setCellEditable(TABLE_COLUMN_NAME);
		tableModel.setCellEditable(TABLE_COLUMN_REMARKS);
		
		jTable_Result.setModel(tableModel);
		jTable_Result.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTable_Result.getColumnModel().getColumn(TABLE_COLUMN_AGE)
			.setCellRenderer(new AgeCellRenderer());
	
		jTable_Result.setRowHeight(32);
		jTable_Result.setAutoCreateRowSorter(true);

		jTable_Result.addMouseListener(new TestPanel_jTable_Result_MouseAdapter(this));
		jtree.addMouseListener(new TestPanel_jTree_MouseAdapter(this));
	}
	
	void jTree_mouseReleased(MouseEvent e) {
		JTree jtree = (JTree)e.getSource();
		TreePath selectionPath = jtree.getSelectionPath();
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
		Map<String,Object> map = new HashMap<String,Object>();
		switch(selectedNode.toString()) {
			case "ㄱ":
				map.put("param",selectedNode.toString());break;
			case "ㄲ":
				map.put("param",selectedNode.toString());break;
			case "ㄴ":
				map.put("param",selectedNode.toString());break;
			case "ㄷ":
				map.put("param",selectedNode.toString());break;
			case "ㄸ":
				map.put("param",selectedNode.toString());break;
			case "ㄹ":
				map.put("param",selectedNode.toString());break;
			case "ㅁ":
				map.put("param",selectedNode.toString());break;
			case "ㅂ":
				map.put("param",selectedNode.toString());break;
			case "ㅃ":
				map.put("param",selectedNode.toString());break;
			case "ㅅ":
				map.put("param",selectedNode.toString());break;
			case "ㅆ":
				map.put("param",selectedNode.toString());break;
			case "ㅇ":
				map.put("param",selectedNode.toString());break;
			case "ㅈ":
				map.put("param",selectedNode.toString());break;
			case "ㅉ":
				map.put("param",selectedNode.toString());break;
			case "ㅋ":
				map.put("param",selectedNode.toString());break;
			case "ㅌ":
				map.put("param",selectedNode.toString());break;
			case "ㅍ":
				map.put("param",selectedNode.toString());break;
			case "ㅎ":
				map.put("param",selectedNode.toString());break;
			default :
				break;	
		}
		try {
			/*List<AddressVo> list = DBaddressbookInter.selectParticularAddress(map);
			LOGGER.debug(list.size()+"리스트 사이즈");
			tableModel.setNumRows(0);
		
			for(int i = 0; i < list.size(); i++) {
				
				Vector oneData = new Vector();
				oneData.add(list.get(i).getSeq());
				oneData.add(list.get(i).getName());
				oneData.add(list.get(i).getGender());
				oneData.add(list.get(i).getAge());
				oneData.add(list.get(i).getAddress());
				tableModel.addData(oneData);
			}*/
			tableModel.fireTableDataChanged();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

	}
	
	void jTable_Result_mouseReleased(MouseEvent e) {
		int selectedRow = jTable_Result.getSelectedRow();
		if (selectedRow >= 0) {
			selected_Name = (String)tableModel.getValueAt(selectedRow, TABLE_COLUMN_NAME);
			LOGGER.debug(String.format("--- selectedRow:%s, name:%s", selectedRow, selected_Name));
			
			jTextField_Name.setText((String)tableModel.getValueAt(selectedRow, TABLE_COLUMN_NAME));
			LOGGER.debug(tableModel.getValueAt(selectedRow, TABLE_COLUMN_GENDER));
			if(tableModel.getValueAt(selectedRow, TABLE_COLUMN_GENDER).toString() == "남") {
				jRadioButton_Man.setSelected(true);
				jRadioButton_Woman.setSelected(false);

			}else {
				jRadioButton_Woman.setSelected(true);
				jRadioButton_Man.setSelected(false);

			}
			jTextField_Age.setText(tableModel.getValueAt(selectedRow, TABLE_COLUMN_AGE).toString());
			jTextField_Address.setText((String)tableModel.getValueAt(selectedRow, TABLE_COLUMN_ADDRESS));
			//Person onePerson = (Person)tableModel.getValueAt(selectedRow, TABLE_COLUMN_REMARKS);
			//LOGGER.debug(String.format("--- selectedRow:%s, person:%s", selectedRow, onePerson));
		}
	}
	
	private String insertgetChosung(String name) {
		char ch = name.charAt(0);
		String str = new String();
		str = hangleList[(ch-44032)/(21*28)];
		return str;
	}

	void dataInsert() {
		if(	!(jTextField_Name.getText().equals("")) 
				&& !(jTextField_Age.getText().equals("")) 
				&& !(jTextField_Address.getText().equals(""))) {
			
			String str = insertgetChosung(jTextField_Name.getText());
			Enumeration chosung = root.breadthFirstEnumeration();
			DefaultMutableTreeNode node  	= null;
			DefaultMutableTreeNode parent	= null;
			DefaultMutableTreeNode insertNode = new DefaultMutableTreeNode(jTextField_Name.getText());
			
			while(chosung.hasMoreElements()) {
				node = (DefaultMutableTreeNode) chosung.nextElement();
				if(str.equals(node.getUserObject().toString())) {
					parent = node;
				}
			}
			treeModel.insertNodeInto(insertNode, parent, parent.getChildCount());
			
			Vector oneData = new Vector();
			AddressVo addressvo = new AddressVo();
			addressvo.setName(jTextField_Name.getText());
		
			addressvo.setAge(Integer.parseInt(jTextField_Age.getText()));
			
			oneData.add(jTable_Result.getRowCount()+1);
			oneData.add(jTextField_Name.getText());
			enums = Gender_Group.getElements();
			
			while(enums.hasMoreElements()) {
				AbstractButton ab = enums.nextElement();
				JRadioButton jb = (JRadioButton)ab;
				
				if(jb.isSelected()) {
					LOGGER.debug(jb.getText());
					try {
						addressvo.setGender(Gender.toGender(jb.getText()));
						oneData.add(Gender.toGender(jb.getText()));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			oneData.add(Integer.parseInt(jTextField_Age.getText()));

			addressvo.setAddress(jTextField_Address.getText());
			oneData.add(jTextField_Address.getText());
			tableModel.addData(oneData);
			tableModel.fireTableDataChanged();
			//jTextField_Num.setText("");
			jTextField_Name.setText("");
			jTextField_Age.setText("");
			jTextField_Address.setText("");
			Gender_Group.clearSelection();
			try {
				ClientMain client = new ClientMain();
				client.addressBookIf.insertAddress(addressvo);
				initData();
				//ClientMain.addressBookIf = new  SocketAddressBookImpl("localhost", 50000);
				//ClientMain.addressBookIf.insertAddress(addressvo);
				//addressbookInter.insertAddress(addressvo);
				//DBaddressbookInter.insertAddress(addressvo);
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
			JOptionPane.showMessageDialog(this, "추가 되었습니다.");

		}else {
			JOptionPane.showMessageDialog(this, "정보를 입력해주세요");
		}
	
	}
	void dataDelete() {
		try {
			AddressVo addressVo = new AddressVo();
			addressVo.setSeq(Integer.parseInt(tableModel.getValueAt(jTable_Result.getSelectedRow(), TABLE_COLUMN_SEQ).toString()));
			ClientMain client = new ClientMain();
			client.addressBookIf.deleteAddress(addressVo);
			
			//DBaddressbookInter.deleteAddress(jTable_Result.getSelectedRow()+1);

			//addressbookInter.deleteAddress(jTable_Result.getSelectedRow());
			tableModel.removeData(jTable_Result.getSelectedRow());
			tableModel.fireTableDataChanged();
			JOptionPane.showMessageDialog(this, "삭제 되었습니다.");
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	void dataUpdate() {
		AddressVo addressvo = new AddressVo();
		addressvo.setSeq(Integer.parseInt(tableModel.getValueAt(jTable_Result.getSelectedRow(), TABLE_COLUMN_SEQ).toString()));
		addressvo.setName(jTextField_Name.getText());
		enums = Gender_Group.getElements();
		
		while(enums.hasMoreElements()) {
			AbstractButton ab = enums.nextElement();
			JRadioButton jb = (JRadioButton)ab;
			
			if(jb.isSelected()) {
				LOGGER.debug(jb.getText());
				try {
					addressvo.setGender(Gender.toGender(jb.getText()));
					tableModel.setValueAt(Gender.toGender(jb.getText()), jTable_Result.getSelectedRow(), TABLE_COLUMN_GENDER);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		addressvo.setAge(Integer.parseInt(jTextField_Age.getText()));
		addressvo.setAddress(jTextField_Address.getText());
		try {
			ClientMain client = new ClientMain();
			client.addressBookIf.updateAddress(addressvo);

			//DBaddressbookInter.updateAddress(jTable_Result.getSelectedRow()+1, addressvo);
			//addressbookInter.updateAddress(jTable_Result.getSelectedRow(), addressvo);
			tableModel.setValueAt(jTextField_Name.getText(), jTable_Result.getSelectedRow(), TABLE_COLUMN_NAME);
			tableModel.setValueAt(jTextField_Age.getText(), jTable_Result.getSelectedRow(), TABLE_COLUMN_AGE);
			tableModel.setValueAt(jTextField_Address.getText(), jTable_Result.getSelectedRow(), TABLE_COLUMN_ADDRESS);
			tableModel.fireTableDataChanged();
			JOptionPane.showMessageDialog(this, "수정 되었습니다.");

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
}


class Button_this_ActionListener implements ActionListener {
	private TestPanel adaptee;
	
	public Button_this_ActionListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String event = e.getActionCommand();
		
		switch (event) {
			case "추가": adaptee.dataInsert();
				break;
			case "삭제": adaptee.dataDelete();
				break;
			case "수정": adaptee.dataUpdate();
				break;
			default :
				break;
		}	
	}
}

class TestPanel_jTable_Result_MouseAdapter extends MouseAdapter {
	private TestPanel adaptee;
	
	public TestPanel_jTable_Result_MouseAdapter(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		adaptee.jTable_Result_mouseReleased(e);
	}
}

class TestPanel_jTree_MouseAdapter extends MouseAdapter{
	private TestPanel adaptee;
	
	public TestPanel_jTree_MouseAdapter(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		adaptee.jTree_mouseReleased(e);
	}
}

