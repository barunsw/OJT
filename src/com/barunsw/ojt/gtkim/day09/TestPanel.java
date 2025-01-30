package com.barunsw.ojt.gtkim.day09;

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
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;


public class TestPanel extends JPanel {
	static final Logger LOGGER = LogManager.getLogger(TestPanel.class);
	private final Dimension SIZE 	   = new Dimension(80, 35);
	private final String[] PHONE_LOCAL = { "010", "011", "016", "017", "018", "019" };
	private final char[] hangleList	   = { 'ㄱ', 'ㄴ', 'ㄷ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅅ',
										 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' };

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
	private DefaultTableModel tableModel  = new DefaultTableModel();
	private JScrollPane jScrollPane_Table = new JScrollPane();
	
	private JTree jTree_Result			 = new JTree();
	private DefaultMutableTreeNode root  = new DefaultMutableTreeNode("Index");		
	private DefaultTreeModel treeModel   = new DefaultTreeModel(root);
	private JScrollPane jScrollPane_Tree = new JScrollPane();
	
	private DBConnector connDB = new DBConnector();
	
	public TestPanel() {
		try {
			initComponent();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			connDB.closeSession();
		}
	}
	
	private void initComponent() {		
		initTable();
		initTree();
		initLayout();
		initActionListener();
		viewData();
	}

	private DefaultMutableTreeNode searchNode(String name) {
		DefaultMutableTreeNode node    	   = null;
		DefaultMutableTreeNode defaultNode = null;
		// Iterator 대신 Enumeration 으로 사용 
		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			node = (DefaultMutableTreeNode) e.nextElement();
			if (name.equals(node.getUserObject().toString())) {
				return node;
			}
			
			if(node.getUserObject().toString().equals("기타")) {
				defaultNode = node;
			}
		}
		return defaultNode;
	}
	
	void insertTreeData(String nodeName) {	
		String parentName 			  = getChosung(nodeName);
		DefaultMutableTreeNode parent = searchNode(parentName);
		DefaultMutableTreeNode node   = new DefaultMutableTreeNode(nodeName);
		
		treeModel.insertNodeInto(node, parent, parent.getChildCount());	
		//LOGGER.debug(parent.getUserObject().toString() + "에 노드가 추가 되었습니다 : "
		//		+ node.getUserObject().toString());
	}
	
	void autoExpendTree(JTree tree, int index, int rowCount) {
		for(int i = index; i < rowCount; i++) {
			tree.expandRow(i);
		}
		
		if(tree.getRowCount() != rowCount) {
			autoExpendTree(tree, rowCount, tree.getRowCount());
		}
	}
	
	void insertData() {
		Vector insertData = new Vector();

		insertData = getComponentData();
		if (insertData != null) {
			try {
				// Mapper 가져오기
				connDB.setMapper(PersonDao.class);
				PersonDao mapper = (PersonDao) connDB.getMapper();

				PersonVO onePerson = new PersonVO();
				Vector inputdata = getComponentData();

				onePerson.setName(inputdata.get(0).toString());
				onePerson.setGender(Gender.toGender(inputdata.get(1).toString()));
				onePerson.setAge(Integer.parseInt(inputdata.get(2).toString()));
				onePerson.setPhone(inputdata.get(3).toString());
				onePerson.setAddress(inputdata.get(4).toString());

				mapper.insertPerson(onePerson);
				LOGGER.debug("DB에 입력되었습니다");
				connDB.commit();

				// JTable과 JTree view 생성
				viewData();
				// 컴포넌트 필드 초기화
				initText();
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
				connDB.closeSession();
			}
		}
	}
	
	void deleteData() {
		int row = jTable_Result.getSelectedRow();
		if(row > -1) {			
			try {
				connDB.setMapper(PersonDao.class);
				PersonDao mapper = (PersonDao) connDB.getMapper();
				
				PersonVO deletePerson = new PersonVO();
				deletePerson.setName(jTable_Result.getValueAt(row, 0).toString());
				deletePerson.setGender(Gender.toGender(jTable_Result.getValueAt(row, 1).toString()));
				deletePerson.setAge(Integer.parseInt(jTable_Result.getValueAt(row, 2).toString()));
				deletePerson.setPhone(jTable_Result.getValueAt(row, 3).toString());
				deletePerson.setAddress(jTable_Result.getValueAt(row, 4).toString());
				
				mapper.deletePerson(deletePerson);
				LOGGER.debug("테이블 삭제 성공");
				connDB.commit();
				// JTree, JTable view
				viewData();
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
				connDB.closeSession();
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "테이블에서 삭제할 행을 선택하세요", "Warning", JOptionPane.WARNING_MESSAGE);
			LOGGER.debug("삭제에 실패하였습니다");
		}		
	}
	
	void updateData() {
		try {
			connDB.setMapper(PersonDao.class);
			PersonDao mapper	= (PersonDao) connDB.getMapper();
			Vector updateDate	= tableModel.getDataVector();
			Iterator<Vector> it = updateDate.iterator();
		
			//List<PersonVO> updateList = new ArrayList<>();
			
			while(it.hasNext()) {
				Vector v= it.next();
				PersonVO onePerson = new PersonVO();
				
				onePerson.setName(v.get(0).toString());
				onePerson.setGender(Gender.toGender(v.get(1).toString()));
				onePerson.setAge(Integer.parseInt(v.get(2).toString()));
				onePerson.setPhone(v.get(3).toString());
				onePerson.setAddress(v.get(4).toString());
				onePerson.setSeq(Integer.parseInt(v.get(5).toString()));
				mapper.updatePerson(onePerson);
				//updateList.add(onePerson);
			}
			//mapper.updatePersonList(updateList);
			connDB.commit();
			LOGGER.debug("수정 완료");
			viewData();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			connDB.closeSession();
		}
	}
	
	void viewData() {
		try {
			connDB.setMapper(PersonDao.class);
			PersonDao mapper = (PersonDao) connDB.getMapper();
			
			// 테이블, 트리 초기화
			tableModel.setNumRows(0);
			root.removeAllChildren();
			initTree();
			
			List<PersonVO> personList = mapper.selectPersonList();
			for (PersonVO p : personList) {
				Vector getData = new Vector();
				getData.add(p.getName());
				getData.add(p.getGender());
				getData.add(p.getAge());
				getData.add(p.getPhone());
				getData.add(p.getAddress());
				getData.add(p.getSeq());
				
				tableModel.addRow(getData);
				insertTreeData(p.getName());
			}
			LOGGER.debug("조회 완료");
			autoExpendTree(jTree_Result, 0, jTree_Result.getRowCount());
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			connDB.closeSession();
		}
	}
	
	private Vector getComponentData() {
		Vector data = new Vector();
		try {
			String name    = jTextField_Name.getText();
			String gender  = jRadioButton_Man.isSelected() ? new String("남자") : new String("여자");
			int age 	   = jTextField_Age.getText().equals("") ? null : Integer.parseInt(jTextField_Age.getText());
			String phone   = jComboBox_Phone_Pre.getSelectedItem().toString() + "-" 
								+ jTextField_Phone_Mid.getText() + "-" 
								+ jTextField_Phone_Suf.getText();	
			String regPhone = "^01(?:0|1|[6-9])[-]?(\\d{3}|\\d{4})[-]?(\\d{4})$";
			String address = jTextArea_Adress.getText();
			
			if(phone.matches(regPhone)) {
				data = new Vector();
				data.add(name);
				data.add(gender);
				data.add(age);
				data.add(phone);
				data.add(address);
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
		String chosung = new String();
		// 초성만 추출해서 유니코드를 계산하는 식 이라고 함
		int value = (ch - 44032) / (21 * 28);
		switch (value) {
		case 0 :
		case 1 :
			chosung = "ㄱ";
			break;
		case 2 :
			chosung = "ㄴ";
			break;
		case 3 :
		case 4 :
			chosung = "ㄷ";
			break;
		case 5 :
			chosung = "ㄹ";
			break;
		case 6 :
			chosung = "ㅁ";
			break;
		case 7 :
		case 8 :
			chosung = "ㅂ";
			break;
		case 9 :
		case 10 :
			chosung = "ㅅ";
			break;
		case 11 :
			chosung = "ㅇ";
			break;
		case 12 :
		case 13 :
			chosung = "ㅈ";
			break;
		case 14 :
			chosung = "ㅊ";
			break;
		case 15 :
			chosung = "ㅋ";
			break;
		case 16 :
			chosung = "ㅌ";
			break;
		case 17 :
			chosung = "ㅍ";
			break;
		case 18 :
			chosung = "ㅎ";
			break;
		}
		return chosung;
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
	
	private void initActionListener() {
		jButton_Add.addActionListener(new TestPanel_this_Button_ActionListener(this));
		jButton_Delete.addActionListener(new TestPanel_this_Button_ActionListener(this));
		jButton_Update.addActionListener(new TestPanel_this_Button_ActionListener(this));
		jButton_Reload.addActionListener(new TestPanel_this_Button_ActionListener(this));
		
		jTree_Result.addMouseListener(new TestPanel_this_Tree_MouseListener(this));
		//addComponentListener(new TestPanel_this_AutoReSize(this));
		//jTable_Result.addMouseListener(new TestPanel_this_Mouse_ActionListener(this));
	}
	
	private void initTable() {
		Vector<String> columnData = new Vector<>();
		
		columnData.add("이름");
		columnData.add("성별");
		columnData.add("나이");
		columnData.add("휴대폰");
		columnData.add("주소");
		columnData.add("Seq");

		tableModel.setColumnIdentifiers(columnData);
		jTable_Result.setModel(tableModel);
		
		jTable_Result.getColumn("이름").setMaxWidth(150);
		jTable_Result.getColumn("성별").setMaxWidth(60);
		jTable_Result.getColumn("나이").setMaxWidth(60);
		
		// seq는 안보이게 처리 
		jTable_Result.removeColumn(jTable_Result.getColumn("Seq"));
		
		jTable_Result.getTableHeader().setReorderingAllowed(false);
	}
	
	private void initTree() {
		DefaultMutableTreeNode child = null;
		
		for(char ch : hangleList) {
			child = new DefaultMutableTreeNode(Character.toString(ch));
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
		treeModel.setRoot(root);
		jTree_Result.setModel(treeModel);
	}
	
	private void initLayout() {
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
			adaptee.viewData();
			break;
     	case "수정" :
     		adaptee.updateData();
     		break;
		default :
			TestPanel.LOGGER.debug("발생 할 수 없는 이벤트 입니다.");
		}
	}
}

class TestPanel_this_Tree_MouseListener extends MouseAdapter {
	private TestPanel adaptee;
	
	public TestPanel_this_Tree_MouseListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		JTree getTree 				= (JTree) e.getSource();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) getTree.getLastSelectedPathComponent();
		if(e.getClickCount() == 2) {
			//TestPanel.LOGGER.debug("더블 클릭 됨: " +  node);
			new TestFileManager(node.toString()).fileOpen();;	
		}
	}
}