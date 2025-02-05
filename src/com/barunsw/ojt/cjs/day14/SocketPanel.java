package com.barunsw.ojt.cjs.day14;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.vo.AddressVo;

public class SocketPanel extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketPanel.class);
	private final int COLUMN_INDEX_NAME = 0;
	private final int COLUMN_INDEX_AGE = 1;
	private final int COLUMN_INDEX_GENDER = 2;
	private final int COLUMN_INDEX_ADDRESS = 3;
	private final int COLUMN_INDEX_PERSON = 4;

	public static AddressBookInterface addressBookIf;

	private JSplitPane jSplitPane = new JSplitPane();
	private JTree jTree_Address = new JTree();

	private CommonTableModel tableModel = new CommonTableModel();
	private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
	private DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);

	private final Dimension LABEL_SIZE = new Dimension(80, 22);
	private final Dimension BUTTON_SIZE = new Dimension(80, 22);
	private final Dimension Tree_Size = new Dimension(120, 22);

	private GridBagLayout grid = new GridBagLayout();

	private JLabel jLabel_Name = new JLabel("Name");
	private JLabel jLabel_Gender = new JLabel("Gender");
	private JLabel jLabel_Age = new JLabel("Age");
	private JLabel jLabel_Address = new JLabel("Address");

	private JRadioButton jRadio_Man = new JRadioButton("MAN");
	private JRadioButton jRadio_Woman = new JRadioButton("WOMAN");

	private JSpinner jSpinner_Age = new JSpinner(new SpinnerNumberModel(20, 1, 100, 1));
	private JTextField jTextField_Name = new JTextField();
	private JTextArea jTextArea_Address = new JTextArea();

	private JScrollPane jScrollPane_Address = new JScrollPane();
	private JScrollPane jScrollPane_Table = new JScrollPane();
	private JScrollPane jScrollPane_Tree = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	public JButton jButton_Add = new JButton("Add");
	public JButton jButton_Delete = new JButton("Delete");
	public JButton jButton_Update = new JButton("Update");
	public JButton jButton_Reload = new JButton("Reload");

	private JPanel jPanel_Gender = new JPanel();
	private JPanel jPanel_Button = new JPanel();

	private ButtonGroup ButtonGroup_Gender = new ButtonGroup();
	private JTable jTable_List = new JTable();

	private JPopupMenu popupMenu = new JPopupMenu();
	private JMenuItem jMenuItem_Delete = new JMenuItem("삭제");

	String[] chs = { "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ" };

	public SocketPanel() {
		try {
			initAddressBookIf();
			initComponent();
			initTable();
			initTree();
			initTableData(null, 0);
			initTreeData();
			initEventListner();
			initPopupMenu();

		} catch (Exception e) {
			LOGGER.error(e.getMessage() + e);
		}
	}

	private void initAddressBookIf() throws Exception {
		
		String className = ClientMain.properties.getProperty("address_if_class");
		LOGGER.debug(className);
		Object o = null;
		if (className.contains("SocketAddressBookImpl")) {
			// server.Host를 가져온다.
			String serverHost = ClientMain.properties.getProperty("host");
			LOGGER.debug(serverHost + "");
			// server.port를 가져온다.
			int serverPort = Integer.parseInt(ClientMain.properties.getProperty("port"));
			LOGGER.debug(serverPort + "");

			Constructor c = Class.forName(className).getConstructor(String.class, Integer.class);
			o = c.newInstance(serverHost, serverPort);
//			o = new SocketAddressBookImpl(serverHost, serverPort);
			LOGGER.debug(o + "");
		} else {
			o = Class.forName(className).newInstance();
			LOGGER.debug(addressBookIf + "");
		}
		if (o != null && o instanceof AddressBookInterface) {
			addressBookIf = (AddressBookInterface) o;
			LOGGER.debug(addressBookIf + "");
		}
	}

	private void initComponent() throws Exception {

		this.setLayout(grid);
		jPanel_Button.setLayout(grid);
		jPanel_Gender.setLayout(grid);
		jLabel_Name.setPreferredSize(LABEL_SIZE);
		jLabel_Gender.setPreferredSize(LABEL_SIZE);
		jLabel_Address.setPreferredSize(LABEL_SIZE);

		jTree_Address.setPreferredSize(Tree_Size);
		jTree_Address.setMinimumSize(Tree_Size);
		jTree_Address.setMaximumSize(Tree_Size);

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

		jSplitPane.setDividerSize(10);
		jSplitPane.setOneTouchExpandable(true);
		jSplitPane.setDividerLocation(300);

		this.add(jLabel_Name, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.add(jTextField_Name, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));
		this.add(jLabel_Gender, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
		this.add(jLabel_Age, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
		this.add(jSpinner_Age, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
		this.add(jPanel_Gender, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		this.add(jLabel_Address, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
		this.add(jScrollPane_Address, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
		this.add(jSplitPane, new GridBagConstraints(0, 5, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));

		// =================================================================
		// JButton Panel생성 후 버튼 추가
		this.add(jPanel_Button, new GridBagConstraints(0, 4, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		jPanel_Button.add(jButton_Add, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.VERTICAL, new Insets(0, 5, 5, 5), 0, 0));
		jPanel_Button.add(jButton_Delete, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
		jPanel_Button.add(jButton_Reload, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
		jPanel_Button.add(jButton_Update, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		jScrollPane_Table.getViewport().add(jTable_List);
		jScrollPane_Address.getViewport().add(jTextArea_Address);
		jScrollPane_Tree.getViewport().add(jTree_Address);
		jTree_Address.setSize(400, 300);
		jSplitPane.setLeftComponent(jScrollPane_Tree);
		jSplitPane.setRightComponent(jScrollPane_Table);
		// ===================================================================
		// radio버튼 Gender 패널에 추가
		// radio 버튼 ButtonGroup로 묶기
		jPanel_Gender.add(jRadio_Man, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
		jPanel_Gender.add(jRadio_Woman, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.VERTICAL, new Insets(0, 0, 5, 5), 0, 0));
		ButtonGroup_Gender.add(jRadio_Man);
		ButtonGroup_Gender.add(jRadio_Woman);
		// ===================================================================

	}

	private void initTable() {
		Vector<String> columnData = new Vector<>(); // 테이블의 컬럼값 저장
		columnData.add("Name");
		columnData.add("Gender");
		columnData.add("Age");
		columnData.add("Address");

		tableModel.setColumn(columnData);
		jTable_List.setModel(tableModel);
	}

	private void initTree() {
		treeModel.setRoot(rootNode);
		jTree_Address.setModel(treeModel);
		// root노드 숨김
		jTree_Address.setRootVisible(false);
		treeModel.reload();
	}

	private void treeReset() throws Exception {
		rootNode.removeAllChildren();
		initTreeData();
	}

	/**
	 * 
	 * @param searchWord 검색어 null인 경우 전체 검색
	 * @param level      2인 경우 초성 검색, 3인 경우 이름 검색
	 * @throws Exception
	 */
	private void initTableData(String searchWord, int level) throws Exception {
		Vector data = new Vector();
		List<AddressVo> addressList = addressBookIf.selectAddressList(new AddressVo());

		if (searchWord == null) {
			for (AddressVo address : addressList) {
				Vector inputData = new Vector();
				inputData.add(address.getName());
				inputData.add(address.getAge());
				inputData.add(address.getGender());
				inputData.add(address.getAddress());

				data.add(inputData);
				LOGGER.debug(data + "");

			}
		} else if (searchWord != null && level == 3) {
			for (AddressVo address : addressList) {
				Vector inputData = new Vector();
				if (address.getName().equals(searchWord)) {
					inputData.add(address.getName());
					inputData.add(address.getAge());
					inputData.add(address.getGender());
					inputData.add(address.getAddress());

					data.add(inputData);
				}
			}
		} else if (searchWord != null && level == 2){
			
			for (AddressVo address : addressList) {
				Vector inputData = new Vector();
				String text = address.getName();
				
				if (text.length() > 0) {
					char chName = text.charAt(0);
					
					if (chName >= 0xAC00) {
						int uniVal = chName - 0xAC00;
						int cho = ((uniVal - (uniVal % 28)) / 28) / 21;
						
						if (chs[cho].equals(searchWord)) {

							inputData.add(address.getName());
							inputData.add(address.getAge());
							inputData.add(address.getGender());
							inputData.add(address.getAddress());

							data.add(inputData);
						}
					}
				}
			}
		}
		tableModel.setData(data);
		tableModel.fireTableDataChanged();
	}

	private void initTreeData() throws Exception {
		AddressVo paramData = new AddressVo();

		List<AddressVo> addressList = addressBookIf.selectAddressList(new AddressVo());

		for (int i = 0; i < chs.length; i++) {
			DefaultMutableTreeNode consanantLetter = new DefaultMutableTreeNode(chs[i]);
			rootNode.add(consanantLetter);

			for (int j = 0; j < addressList.size(); j++) {
				String text = addressList.get(j).getName();
				if (text.length() > 0) {
					char chName = text.charAt(0);
					if (chName >= 0xAC00) {
						int uniVal = chName - 0xAC00;
						int cho = ((uniVal - (uniVal % 28)) / 28) / 21;
						if (chs[cho].equals(chs[i])) {
							LOGGER.debug(text);
							DefaultMutableTreeNode personName = new DefaultMutableTreeNode(text);
							consanantLetter.add(personName);
						}
					}
				}
			}
			treeModel.reload();
		}
	}

	private void initEventListner() {
		jButton_Add.addActionListener(new AddressBookPanel_jButton_Add_ActionListener(this));
		jButton_Delete.addActionListener(new AddressBookPanel_jButton_Delete_ActionListener(this));
		jButton_Update.addActionListener(new AddressBookPanel_jButton_Update_ActionListener(this));
		jButton_Reload.addActionListener(new AddressBookPanel_jButton_Reload_ActionListener(this));

		jTree_Address.addMouseListener(new AddressBookPanel_jTree_Address_MouseAdapter(this));
	}

	void ResetForm() {
		jTextField_Name.setText("");
		jSpinner_Age.setValue(20);
		ButtonGroup_Gender.clearSelection();
		jTextArea_Address.setText("");
	}

	private AddressVo createAddressVo() {
		AddressVo address = new AddressVo();
		address.setName(jTextField_Name.getText());
		address.setAddress(jTextArea_Address.getText());
		address.setAge((int) jSpinner_Age.getValue());
		address.setGender(jRadio_Man.isSelected() ? Gender.MAN : Gender.WOMAN);
		return address;
	}

	void jButton_Add_ActionListener() throws Exception {
		try {
			addressBookIf.insertAddress(createAddressVo());
			JOptionPane.showMessageDialog(this, "ADD Complete", "Alert", JOptionPane.INFORMATION_MESSAGE);
			ResetForm();
		} catch (Exception e) {
			LOGGER.error(e.getMessage() + e);
		}

		initTableData(null, 0);
		treeReset();
	}

	void jButton_Delete_ActionListener() throws Exception {
		AddressVo address = new AddressVo();
		String remove_Name = jTextField_Name.getText();
		address.setName(remove_Name);
		try {
			addressBookIf.deleteAddress(address);
			JOptionPane.showMessageDialog(null, "DELETE Complete", "Alert", JOptionPane.INFORMATION_MESSAGE);
			ResetForm();
		} catch (Exception e) {
			LOGGER.error(e.getMessage() + e);
		}

		initTableData(null, 0);
		treeReset();
	}

	void jButton_update_ActionListner() throws Exception {
		AddressVo address = new AddressVo();
		String update_Name = jTextField_Name.getText();
		address.setName(update_Name);
		try {
			address.setAge((int) jSpinner_Age.getValue());
			address.setGender(jRadio_Man.isSelected() ? Gender.MAN : Gender.WOMAN);
			address.setAddress(jTextArea_Address.getText());
			addressBookIf.updateAddress(address);
			JOptionPane.showMessageDialog(null, "UPDATE Complete", "Alert", JOptionPane.INFORMATION_MESSAGE);
			ResetForm();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		initTableData(null, 0);
		treeReset();
	}

	void jButton_Reload_ActionListener() throws Exception {
		initTableData(null, 0);
	}

	void jTree_Address_mouseReleased(MouseEvent e) {
		LOGGER.debug("selectionPath:" + jTree_Address.getSelectionPath());
		LOGGER.debug("lastSelectedPathComponent:" + jTree_Address.getLastSelectedPathComponent());

		LOGGER.debug("component:" + jTree_Address.getPathForLocation(e.getX(), e.getY()));

		TreePath selectedTreePath = jTree_Address.getPathForLocation(e.getX(), e.getY());
		if (selectedTreePath != null) {
			// Object o = jTree_Address.getLastSelectedPathComponent();
			TreePath selectionPath = jTree_Address.getSelectionPath();
			int pathCount = selectionPath.getPathCount();

			LOGGER.debug("pathCount:" + pathCount);
		}
	}

	private void deleteData(int selectedRow) {
		Object value = tableModel.getValueAt(selectedRow, COLUMN_INDEX_PERSON);
		if (value instanceof AddressVo) {
			try {
				AddressVo addressVo = (AddressVo) value;
				LOGGER.debug(value + "");
				addressBookIf.deleteAddress(addressVo);
				initTableData(null, 0);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
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
				}
			}
		});
	}
}

class AddressBookPanel_jButton_Add_ActionListener implements ActionListener {
	private SocketPanel adaptee;

	public AddressBookPanel_jButton_Add_ActionListener(SocketPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			adaptee.jButton_Add_ActionListener();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class AddressBookPanel_jButton_Delete_ActionListener implements ActionListener {
	private SocketPanel adaptee;

	public AddressBookPanel_jButton_Delete_ActionListener(SocketPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			adaptee.jButton_Delete_ActionListener();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class AddressBookPanel_jButton_Update_ActionListener implements ActionListener {
	private SocketPanel adaptee;

	public AddressBookPanel_jButton_Update_ActionListener(SocketPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			adaptee.jButton_update_ActionListner();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class AddressBookPanel_jButton_Reload_ActionListener implements ActionListener {
	private SocketPanel adaptee;

	public AddressBookPanel_jButton_Reload_ActionListener(SocketPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			adaptee.jButton_Reload_ActionListener();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class AddressBookPanel_jTree_Address_MouseAdapter extends MouseAdapter {
	private SocketPanel adaptee;

	public AddressBookPanel_jTree_Address_MouseAdapter(SocketPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		adaptee.jTree_Address_mouseReleased(e);
	}
}
