package com.barunsw.ojt.gtkim.day12;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.gtkim.day13.SocketAddressBookImplObj;
import com.barunsw.ojt.gtkim.day13.SocketAddressBookImplString;
import com.barunsw.ojt.gtkim.day13.DatagramServerMain;
import com.barunsw.ojt.gtkim.day13.DatagramSocketAddressBookImpl;
import com.barunsw.ojt.gtkim.day13.DatagramSocketAddressBookImplObj;
import com.barunsw.ojt.gtkim.day13.ServerMain;

public class TestPanel extends JPanel {
	static final Logger LOGGER = LogManager.getLogger(TestPanel.class);
	private final Dimension SIZE 	   = new Dimension(80, 35);
	private final String[] PHONE_LOCAL = { "010", "011", "016", "017", "018", "019" };
	private final String[] hangleList  = {"ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", 
										"ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ",  
										"ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ" };
	
	private static int seq = 1;
	
	private int columnIdx = 0;
	private final int TABLE_COLUMN_SEQ 		= columnIdx++; 
	private final int TABLE_COLUMN_NAME 	= columnIdx++; 
	private final int TABLE_COLUMN_GENDER 	= columnIdx++;
	private final int TABLE_COLUMN_AGE 		= columnIdx++;
	private final int TABLE_COLUMN_PHONE 	= columnIdx++;
	private final int TABLE_COLUMN_ADDRESS 	= columnIdx++; 
	private final int TABLE_COLUMN_REALSEQ 	= columnIdx++; 
	
	private int voIdx = 0;
	private final int VO_COLUMN_NAME 	= voIdx++; 
	private final int VO_COLUMN_GENDER 	= voIdx++;
	private final int VO_COLUMN_AGE 	= voIdx++;
	private final int VO_COLUMN_PHONE 	= voIdx++;
	private final int VO_COLUMN_ADDRESS	= voIdx++; 
	
	
	private JPanel jPanel_Gender	= new JPanel();
	private JPanel jPanel_Phone 	= new JPanel();
	private JPanel jPanel_Command 	= new JPanel();
	
	private JLabel jLabel_Name 	  	= new JLabel("이름");
	private JLabel jLabel_Gender  	= new JLabel("성별");
	private JLabel jLabel_Age 	  	= new JLabel("나이");
	private JLabel jLabel_Phone   	= new JLabel("핸드폰");
	private JLabel jLabel_Address 	= new JLabel("주소");

	private JTextField jTextField_Name 	    = new JTextField();
	private JTextField jTextField_Age		= new JTextField();
	private JTextField jTextField_Phone_Mid = new JTextField();
	private JTextField jTextField_Phone_Suf = new JTextField();
	
	private JRadioButton jRadioButton_Man   = new JRadioButton("남");
	private JRadioButton jRadioButton_Woman = new JRadioButton("여");
	private ButtonGroup buttonGroup_Gender  = new ButtonGroup();
	
	private JTextArea jTextArea_Adress 		  = new JTextArea();
	private JScrollPane jScrollPane_Address   = new JScrollPane();

	private JComboBox jComboBox_Phone_Pre = null;

	private JButton jButton_Add    = new JButton("추가");
	private JButton jButton_Delete = new JButton("삭제");
	private JButton jButton_Update = new JButton("수정");
	private JButton jButton_Reload = new JButton("전체조회");
	
	private JTable jTable_Result 	      = new JTable();
	private CommonTableModel tableModel   = new CommonTableModel();
	private JScrollPane jScrollPane_Table = new JScrollPane();
	private DefaultCellEditor table_cellEditor;
	
	private JTree jTree_Result			 = new JTree();
	private DefaultMutableTreeNode root  = new DefaultMutableTreeNode("Index");		
	private DefaultTreeModel treeModel   = new DefaultTreeModel(root);
	private JScrollPane jScrollPane_Tree = new JScrollPane();
	
	private DefaultTreeCellRenderer tree_cellRenderer = new DefaultTreeCellRenderer();
	
	private AddressBookInterface addressBook ;
	
	public TestPanel() {
//		try {
//			addressBook = new DatagramSocketAddressBookImplObj("localhost", DatagramServerMain.PORT);
//			addressBook = new DatagramSocketAddressBookImpl("localhost", DatagramServerMain.PORT);
//			addressBook = new SocketAddressBookImplString("localhost", ServerMain.PORT);
//			addressBook = new SocketAddressBookImplObject("localhost", ServerMain.PORT);
//		}
//		catch (Exception ex) {
//			LOGGER.error(ex.getMessage(), ex);
//		}	
		try {
			initComponent();
			initTable();
			initTree();
			
			selectData();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void initComponent() {		
		// 레이아웃 설정
				this.setLayout(new GridBagLayout());
				// 라디오 그룹화
				jRadioButton_Man.setSelected(true);
				buttonGroup_Gender.add(jRadioButton_Man);
				buttonGroup_Gender.add(jRadioButton_Woman);
				// 스크롤팬 초기화
				jTextArea_Adress.setLineWrap(true);
				jScrollPane_Address.getViewport().add(jTextArea_Adress);
				jScrollPane_Address.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				
				jScrollPane_Table.getViewport().add(jTable_Result);
				jScrollPane_Tree.getViewport().add(jTree_Result);
				// 콤보박스 초기화
				jComboBox_Phone_Pre = new JComboBox<String>(PHONE_LOCAL);

				// 컴포넌트 사이즈 정렬
				jTextField_Name.setPreferredSize(SIZE);
				jTextField_Age.setPreferredSize(SIZE);
				jTextField_Phone_Mid.setPreferredSize(SIZE);
				jTextField_Phone_Suf.setPreferredSize(SIZE);
				
				jLabel_Name.setHorizontalAlignment(SwingConstants.CENTER);
				jLabel_Gender.setHorizontalAlignment(SwingConstants.CENTER);
				jLabel_Age.setHorizontalAlignment(SwingConstants.CENTER);
				jLabel_Phone.setHorizontalAlignment(SwingConstants.CENTER);
				jLabel_Address.setHorizontalAlignment(SwingConstants.CENTER);
				
				jScrollPane_Tree.setPreferredSize(new Dimension(150, 30));
				
				// 컴포넌트 배치
				jPanel_Gender.setLayout(new GridBagLayout());
				jPanel_Phone.setLayout(new GridBagLayout());
				jPanel_Command.setLayout(new GridBagLayout());
				this.add(jLabel_Name,
						new GridBagConstraints(0, 0, 1, 1,
								0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(5, 5, 5, 5),
								0, 0));
				
				this.add(jTextField_Name,
						new GridBagConstraints(1, 0, 1, 1,
								1.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(5, 0, 5, 5),
								0, 0));

				this.add(jLabel_Gender,
						new GridBagConstraints(0, 1, 1, 1,
								0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 5, 5, 5),
								0, 0));
				
				this.add(jPanel_Gender,
						new GridBagConstraints(1, 1, 1, 1,
								0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0),
								0, 0));
				
				this.add(jLabel_Age,
						new GridBagConstraints(0, 2, 1, 1,
								0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 5, 5, 5),
								0, 0));
				
				this.add(jTextField_Age,
						new GridBagConstraints(1, 2, 1, 1,
								0.0, 0.0,
								GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
								new Insets(0, 0, 5, 5),
								0, 0));
				
				this.add(jLabel_Phone,
						new GridBagConstraints(0, 3, 1, 1,
								0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 5, 5, 5),
								0, 0));
				
				this.add(jPanel_Phone,
						new GridBagConstraints(1, 3, 1, 1,
								0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0),
								0, 0));	
				
				this.add(jLabel_Address,
						new GridBagConstraints(0, 4, 1, 1,
								0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 5, 5, 5),
								0, 0));	
				
				this.add(jScrollPane_Address,
						new GridBagConstraints(1, 4, 1, 1,
								1.0, 0.3,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5),
								0, 0));	
						
				this.add(jPanel_Command,
						new GridBagConstraints(0, 5, 2, 1,
								1.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0)
								,0, 0));	
				
				this.add(jScrollPane_Tree,
						new GridBagConstraints(0, 6, 1, 1,
								0.3, 0.5,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 5, 5, 5),
								0, 0));
				
				this.add(jScrollPane_Table,
						new GridBagConstraints(1, 6, 1, 1,
								1.0, 0.5,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5),
								0, 0));
						
				jPanel_Gender.add(jRadioButton_Man,
						new GridBagConstraints(0, 0, 1, 1,
								0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5),
								0, 0));
				
				jPanel_Gender.add(jRadioButton_Woman,
						new GridBagConstraints(1, 0, 1, 1,
								1.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5),
								0, 0));

				jPanel_Phone.add(jComboBox_Phone_Pre,
						new GridBagConstraints(0, 0, 1, 1,
								0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5),
								0, 0));
				
				jPanel_Phone.add(jTextField_Phone_Mid,
						new GridBagConstraints(1, 0, 1, 1,
								0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 5, 5),
								0, 0));	

				jPanel_Phone.add(jTextField_Phone_Suf,
						new GridBagConstraints(2, 0, 1, 1,
								1.0, 0.0,
								GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
								new Insets(0, 0, 5, 5),
								0, 0));
			
				jPanel_Command.add(jButton_Add,
						new GridBagConstraints(0, 0, 1, 1,
								1.0, 0.0,
								GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
								new Insets(5, 5, 5, 5),
								0, 0));
				
				
				jPanel_Command.add(jButton_Delete,
						new GridBagConstraints(1, 0, 1, 1,
								0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(5, 0, 5, 5),
								0, 0));
				
				jPanel_Command.add(jButton_Update,
						new GridBagConstraints(2, 0, 1, 1,
								0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(5, 0, 5, 5),
								0, 0));		

				jPanel_Command.add(jButton_Reload,
						new GridBagConstraints(3, 0, 1, 1,
								0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(5, 0, 5, 5),
								0, 0));		

				jButton_Add.addActionListener(new TestPanel_this_Button_ActionListener(this));
				jButton_Delete.addActionListener(new TestPanel_this_Button_ActionListener(this));
				jButton_Update.addActionListener(new TestPanel_this_Button_ActionListener(this));
				jButton_Reload.addActionListener(new TestPanel_this_Button_ActionListener(this));
				
				jTable_Result.addMouseListener(new TestPanel_jTable_Result_MouseAdapter(this));
				jTree_Result.addMouseListener(new TestPanel_JTree_Reuslt_MouseAdapter(this));
	}
	
	private void initTable() {
		Vector<String> columnData = new Vector<>();
		
		columnData.add("번호");
		columnData.add("이름");
		columnData.add("성별");
		columnData.add("나이");
		columnData.add("휴대폰");
		columnData.add("주소");
		columnData.add("Seq");

		tableModel.setColumn(columnData);
		jTable_Result.setModel(tableModel);
		
		int columnCount = jTable_Result.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			TableColumn oneColumn = jTable_Result.getColumnModel().getColumn(i);
			
			if (i == TABLE_COLUMN_SEQ) {
				oneColumn.setMaxWidth(40);
			}
			else if (i == TABLE_COLUMN_NAME) {
				oneColumn.setMaxWidth(150);
			}
			else if (i == TABLE_COLUMN_GENDER) {
				oneColumn.setMaxWidth(60);
			}
			else if (i == TABLE_COLUMN_AGE) {
				oneColumn.setMaxWidth(60);
			}
		}
		
		
		jTable_Result.setCellEditor(jTable_Result.getDefaultEditor(Boolean.class));
		// 한줄씩만 선택가능 
		jTable_Result.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		// seq는 안보이게 처리 
		//jTable_Result.removeColumn(jTable_Result.getColumn("Seq"));
		jTable_Result.getColumn("Seq").setMaxWidth(0);
		jTable_Result.getColumn("Seq").setMinWidth(0);
		jTable_Result.getColumn("Seq").setWidth(0);
		
		jTable_Result.setRowHeight(34);
		jTable_Result.getColumnModel().getColumn(TABLE_COLUMN_GENDER)
			.setCellRenderer(new AddressTableCellRenderer());
	}
	
	private void initTree() {
		DefaultMutableTreeNode child = null;
		
		for(String indexName : hangleList) {
			child = new DefaultMutableTreeNode(indexName);
			root.add(child);
		}
		root.add(new DefaultMutableTreeNode("기타"));
		
	/*	
		for(int i = 65; i <= 90; i++) {
			char ch = (char)i;
			child = new DefaultMutableTreeNode(Character.toString(ch));
			root.add(child);
		}
	*/
		tree_cellRenderer.setBackground(Color.yellow);
		tree_cellRenderer.setBackgroundNonSelectionColor(Color.blue);
		tree_cellRenderer.setBackgroundSelectionColor(Color.red);
		
		treeModel.setRoot(root);
		jTree_Result.setCellRenderer(tree_cellRenderer);
		jTree_Result.setModel(treeModel);
		// root노드 숨김
		jTree_Result.setRootVisible(false);
	
	}
	
	private void initText() {
		jTextField_Name.setText("");
		jTextField_Age.setText("");
		jRadioButton_Man.setSelected(true);
		jComboBox_Phone_Pre.setSelectedIndex(0);
		jTextField_Phone_Mid.setText("");
		jTextField_Phone_Suf.setText("");
		jTextArea_Adress.setText("");
	}
	
	void selectData() {
		List<AddressVo> addressList = new ArrayList<>();

		try {
			addressList = addressBook.selectAddressList();
		} 
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		
		tableModel.setNumRows(0);
		root.removeAllChildren();
		initTree();

		if (!addressList.isEmpty()) {
			seq = addressList.get(addressList.size() - 1).getSeq() + 1;

			try {
				int seqnumber = 1;
				for (AddressVo address : addressList) {
					Vector rowdata = new Vector();
					rowdata.add(seqnumber++);
					rowdata.add(address.getName());
					rowdata.add(address.getGender());
					rowdata.add(address.getAge());
					rowdata.add(address.getPhone());
					rowdata.add(address.getAddress());
					rowdata.add(address.getSeq());

					LOGGER.debug("rowdata : " + rowdata);
					tableModel.addData(rowdata);
					insertTreeData(address.getName());				
				}

				tableModel.fireTableDataChanged();
				autoExpendTree(jTree_Result, 0, jTree_Result.getRowCount());
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
		else {
			LOGGER.debug("데이터가 없습니다." + addressList);
		}
	}
	
	void insertData() {
		Vector getData = getComponentData();
		AddressVo data = new AddressVo();
		
		if (getData != null) {
			try {
				data.setSeq(seq++);
				data.setName(getData.get(VO_COLUMN_NAME).toString());
				data.setGender(Gender.toGender(getData.get(VO_COLUMN_GENDER).toString()));
				data.setAge(Integer.parseInt(getData.get(VO_COLUMN_AGE).toString()));
				data.setPhone(getData.get(VO_COLUMN_PHONE).toString());
				data.setAddress(getData.get(VO_COLUMN_ADDRESS).toString());

				addressBook.insertAddress(data);
				initText();
				selectData();
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}		
		}
	}

	void deleteData() {
		int row = jTable_Result.getSelectedRow();

		if (row >= 0) {
			try {
				int delseq = (int) jTable_Result.getValueAt(row, TABLE_COLUMN_REALSEQ);
				
				AddressVo deleteData = new AddressVo();
				deleteData.setSeq(delseq);
				
				LOGGER.debug("삭제할 seq : " + delseq);
				addressBook.deleteAddress(deleteData);	
				initText();
				selectData();
			}
			catch (Exception ex) {
				LOGGER.debug(ex.getMessage(), ex);
			}
					
		}
		else {
			JOptionPane.showMessageDialog(this, "삭제할 데이터를 선택하세요", "Warn", JOptionPane.WARNING_MESSAGE);
		}	
	}
	
	void updateData() {
		int row = jTable_Result.getSelectedRow();
		Vector getData = getComponentData();

		if (row >= 0 && getData != null) {
			try {
				AddressVo updateData = new AddressVo();

				updateData.setName(getData.get(VO_COLUMN_NAME).toString());
				updateData.setGender(Gender.toGender(getData.get(VO_COLUMN_GENDER).toString()));
				updateData.setAge(Integer.parseInt(getData.get(VO_COLUMN_AGE).toString()));
				updateData.setPhone(getData.get(VO_COLUMN_PHONE).toString());
				updateData.setAddress(getData.get(VO_COLUMN_ADDRESS).toString());
				updateData.setSeq((int) (jTable_Result.getValueAt(row, TABLE_COLUMN_REALSEQ)));

				addressBook.updateAddress(updateData);
				initText();
				selectData();
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		} 
		else {
			JOptionPane.showMessageDialog(this, "수정할 테이블을 선택 하세요", "error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	void jTable_Result_mouseReleased(MouseEvent e) {
		setComponentData();
	}
	
	private void setComponentData() {
		int row = jTable_Result.getSelectedRow();
		try {
			jTextField_Name.setText(jTable_Result.getValueAt(row, TABLE_COLUMN_NAME).toString());
			jTextField_Age.setText(jTable_Result.getValueAt(row, TABLE_COLUMN_AGE).toString());
			jTextArea_Adress.setText(jTable_Result.getValueAt(row, TABLE_COLUMN_ADDRESS).toString());

			String gender = jTable_Result.getValueAt(row, TABLE_COLUMN_GENDER).toString();
			if (gender.equals("남")) {
				jRadioButton_Man.setSelected(true);
			}
			else {
				jRadioButton_Woman.setSelected(true);

			}
			
			String phone  = jTable_Result.getValueAt(row, TABLE_COLUMN_PHONE).toString();
			String[] list = phone.split("-");
			jComboBox_Phone_Pre.setSelectedItem(list[0]);
			jTextField_Phone_Mid.setText(list[1]);
			jTextField_Phone_Suf.setText(list[2]);
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}		
	}
	
	private Vector getComponentData() {
		Vector data		 = new Vector();
		//AddressVo data = new AddressVo();
		try {
			String name    = jTextField_Name.getText();
			String gender  = jRadioButton_Man.isSelected() ? new String("남자") : new String("여자");
			int age 	   = jTextField_Age.getText().equals("") ? null : Integer.parseInt(jTextField_Age.getText());
			String phone   = jComboBox_Phone_Pre.getSelectedItem().toString() + "-" 
								+ jTextField_Phone_Mid.getText() + "-" 
								+ jTextField_Phone_Suf.getText();	
			String regPhone = "^01(?:0|1|[6-9])[-]?(\\d{3}|\\d{4})[-]?(\\d{4})$";
			String address = jTextArea_Adress.getText();
			
			if (phone.matches(regPhone)) {		
				data.add(name);
				data.add(gender);
				data.add(age);
				data.add(phone);
				data.add(address);
				/*
				data.setName(name);
				data.setGender(Gender.toGender(gender));
				data.setAge(age);
				data.setPhone(phone);
				data.setAddress(address);
				*/
			}
			else {
				throw new Exception();
			}
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "잘못된 입력이 있습니다.", "Error", JOptionPane.ERROR_MESSAGE);
			LOGGER.error("데이터 읽기 실패");
			return null;
		}
		return data;
	}
	
	private String getChosung(String name) {
		char ch = name.charAt(0);
		// 초성: 글자의 코드에서 44032를 빼고, 21*28로 나눈 몫 0 ~ 18
		// 중성: 글자의 코드에서 44032를 빼고, 21*28로 나눈 나머지를 다시 28로 나눈 몫 0 ~ 20
		// 종성: 글자의 코드에서 44032를 빼고, 21*28로 나눈 나머지를 다시 28로 나눈 나머지 0 ~ 27
		// https://linuxism.ustd.ip.or.kr/1451

		// 한글인지 확인
		if (ch < 0xAC00 || ch > 0xD7A3) {
			return "기타";
		}
		else {
			return hangleList[(ch - 44032) / (21 * 28)];
		}
	}

	private DefaultMutableTreeNode searchNode(String name) {
		DefaultMutableTreeNode node 		= null;
		DefaultMutableTreeNode defaultNode	= null;
		// Iterator 대신 Enumeration 으로 사용
		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			node = (DefaultMutableTreeNode) e.nextElement();
			if (name.equals(node.getUserObject().toString())) {
				return node;
			}

			if (node.getUserObject().toString().equals("기타")) {
				defaultNode = node;
			}
		}
		return defaultNode;
	}
	
	private void insertTreeData(String nodeName) {	
		String parentName 			  = getChosung(nodeName);
		DefaultMutableTreeNode parent = searchNode(parentName);
		DefaultMutableTreeNode node   = new DefaultMutableTreeNode(nodeName);
		
		treeModel.insertNodeInto(node, parent, parent.getChildCount());	
		//LOGGER.debug(parent.getUserObject().toString() + "에 노드가 추가 되었습니다 : "
		//		+ node.getUserObject().toString());
	}
	
	private void autoExpendTree(JTree tree, int index, int rowCount) {
		for (int i = index; i < rowCount; i++) {
			tree.expandRow(i);
		}

		if (tree.getRowCount() != rowCount) {
			autoExpendTree(tree, rowCount, tree.getRowCount());
		}
	}
}

class TestPanel_this_Button_ActionListener implements ActionListener {
	private TestPanel adaptee;
	
	public TestPanel_this_Button_ActionListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String event = e.getActionCommand();
		TestPanel.LOGGER.debug("발생이벤트 : " + event);
		
		switch(event) {
		case "추가" :
			adaptee.insertData();
			break;
		case "삭제" : 
			adaptee.deleteData();
			break;
     	case "전체조회" :
			adaptee.selectData();
			break;
     	case "수정" :
     		adaptee.updateData();
     		break;
		default :
			TestPanel.LOGGER.debug("발생 할 수 없는 이벤트 입니다.");
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

class TestPanel_JTree_Reuslt_MouseAdapter extends MouseAdapter {
	private TestPanel adaptee;
	
	public TestPanel_JTree_Reuslt_MouseAdapter(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		JTree getTree 				= (JTree) e.getSource();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) getTree.getLastSelectedPathComponent();
		if(e.getClickCount() == 2) {
			new TestFileManager(node.toString()).fileOpen();
		}
	}
}