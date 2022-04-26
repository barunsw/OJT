package com.barunsw.ojt.cjs.day22;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.cjs.common.CommonTableModel;

public class DbClientPanel extends JPanel {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DbClientPanel.class);
	private GridBagLayout grid = new GridBagLayout();

	private JSplitPane jSplitPane_Tree = new JSplitPane();
	private JSplitPane jSplitPane_Table = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

	private JTable jTable_DbSelect = new JTable();
	private JTree jTree_DbInfo = new JTree();
	private JTextArea jTextArea_DbQureyInput = new JTextArea("AREA");
	private JToolBar jToolBar_DbToolbar = new JToolBar();
	private JTextField jTextFiled_Excute = new JTextField("FIELD");
	private JPanel jPanel_AboutDbQuery = new JPanel();
	private JPanel jPanel = new JPanel();

	private JButton jButton_Toolbar_Excute = new JButton("EXCUTE");
	private JButton jButton_Toolbar_Clear = new JButton("CLEAR");
	private JPopupMenu jPopupMenu_Dbmenu = new JPopupMenu();
	private JMenuItem jMenu_ConnectCreate = new JMenuItem("연결 생성");
	private JMenuItem jMenu_ConnectUpdate = new JMenuItem("연결 수정");
	private JMenuItem jMenu_ConnectDelete= new JMenuItem("연결 삭제");

	private JScrollPane jScrollPane_Jtree = new JScrollPane();
	private JScrollPane jScrollPane_Jtable = new JScrollPane();
	private JScrollPane jScrollPane_jTextArea = new JScrollPane();

	private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("rootNode");
	private DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);

	private CommonTableModel tableModel = new CommonTableModel();
	private TreePath deleteTreePath;
	private ConnectSetType setType;
	public DbClientPanel() {
		try {
			initComponent();
			initEvent();
			initTreeData(rootNode);
			initTree();
			initTableColumn();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void initComponent() {
		this.setLayout(grid);
		jPanel_AboutDbQuery.setLayout(grid);
		//jSplitPane
		jSplitPane_Tree.setLeftComponent(jScrollPane_Jtree);
		jSplitPane_Tree.setRightComponent(jPanel);
		
		jSplitPane_Table.setTopComponent(jPanel_AboutDbQuery);
		jSplitPane_Table.setBottomComponent(jScrollPane_Jtable);
		//jScrollPane
		jScrollPane_Jtable.getViewport().add(jTable_DbSelect);
		jScrollPane_jTextArea.getViewport().add(jTextArea_DbQureyInput);
		jScrollPane_Jtree.getViewport().add(jTree_DbInfo);
		
		//ToolBar
		jToolBar_DbToolbar.setBackground(Color.white);
		jToolBar_DbToolbar.setFloatable(false);
		
		jToolBar_DbToolbar.add(jButton_Toolbar_Excute);
		jButton_Toolbar_Excute.setToolTipText("실행한다.");
		
		jToolBar_DbToolbar.add(jButton_Toolbar_Clear);
		jButton_Toolbar_Clear.setToolTipText("입력창을 초기화한다.");
		
		jSplitPane_Tree.setDividerLocation(180);
		jSplitPane_Table.setDividerLocation(300);
		jTextFiled_Excute.setEditable(false);
		jPopupMenu_Dbmenu.add(jMenu_ConnectCreate);
		jPopupMenu_Dbmenu.add(jMenu_ConnectDelete);
		jPopupMenu_Dbmenu.add(jMenu_ConnectUpdate);
		
		this.add(jSplitPane_Tree, new GridBagConstraints(
				0,0,1,1,
				1.0,1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5),
				0,0
				));
		jPanel.add(jSplitPane_Table, new GridBagConstraints(
				0, 0, 1, 1,
				1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5),
				0,0
				));
		jPanel_AboutDbQuery.add(jToolBar_DbToolbar, new GridBagConstraints(
				0, 0, 1, 1,
				1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5),
				0, 0
				));
		jPanel_AboutDbQuery.add(jScrollPane_jTextArea, new GridBagConstraints(
				0, 1, 1, 1,
				1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 5, 5, 5),
				0, 0
				));
		jPanel_AboutDbQuery.add(jTextFiled_Excute, new GridBagConstraints(
				0, 2, 1, 1,
				0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 5, 5, 5),
				0, 0
				));
		
	}
	void initEvent() {
		jButton_Toolbar_Excute.addActionListener(new DbClientPanel_jTable_Excute_ActionListener(this));
		jButton_Toolbar_Clear.addActionListener(new DbClientPanel_jTable_Clear_ActionListner(this));
		jTree_DbInfo.addMouseListener(new DbClientPanel_jTree_Popup_MouseAdapter(this));
		jMenu_ConnectCreate.addActionListener(new DbClientPanel_jMenuItem_Create_ActionListner(this));
		jMenu_ConnectUpdate.addActionListener(new DbClientPanel_jMenuItem_Update_ActionListner(this));
		jMenu_ConnectDelete.addActionListener(new DbClientPanel_jMenuItem_Delete_ActionListner(this));
	}
	
	private void initTreeData(DefaultMutableTreeNode treeNode) {
		DefaultMutableTreeNode mariaDbTree = new DefaultMutableTreeNode("MariaDB");
		rootNode.add(mariaDbTree);
		
		DefaultMutableTreeNode postGresTree = new DefaultMutableTreeNode("PostGres");
		rootNode.add(postGresTree);
	}
	
	private void initTree() {
		treeModel.setRoot(rootNode);
		jTree_DbInfo.setModel(treeModel);
		jTree_DbInfo.setRootVisible(false);
		treeModel.reload();
	}
	

	
	private void initTableColumn() {
		Vector columnData = new Vector(); 
		columnData.add("Name");
		columnData.add("Age");
		columnData.add("Address");
		
		tableModel.setColumn(columnData);
		jTable_DbSelect.setModel(tableModel);
	}
	

	//EXCUTE
	public void DbClientExcute_ActionListener() {
		
	}

	//POPUP
	public void DbClient_Jtree_Popup_MouseAdapter(MouseEvent e) {

		if (e.isPopupTrigger()) {
			TreePath tp = jTree_DbInfo.getClosestPathForLocation(e.getX(), e.getY());
			if (tp != null) {
				jTree_DbInfo.setSelectionPath(tp);
				jPopupMenu_Dbmenu.show(DbClientPanel.this.jTree_DbInfo, e.getX(), e.getY()); // 해당 행의 좌표값에 팝업창을 띄워줌
				deleteTreePath = tp;
			}
		}
	}

	private void showPopup(ConnectSetType setType) {
		ConnectSetDialog.showDialog(DbClientMain.dbFrame, setType);
		
	}
	
	
	//JtextArea CLEAR
	public void DbClientClear_ActionListener() {
		jTextArea_DbQureyInput.setText("");
	}
	
	public void DbClientCreate_ActionListener() {
		setType = ConnectSetType.ADD;
		showPopup(setType);
	}

	public void DbClientUpdate_ActionListener() {
		setType = ConnectSetType.UPDATE;
		showPopup(setType);
	}

	public void DbClientDelete_ActionListener() {
		Object o = deleteTreePath.getLastPathComponent();
		if(o instanceof DefaultMutableTreeNode) {
		DefaultMutableTreeNode deleteTree = (DefaultMutableTreeNode) o;
		initTreeData(deleteTree);
		}
	}
}

//POPUP EVENT
class DbClientPanel_jTree_Popup_MouseAdapter extends MouseAdapter {
	private DbClientPanel adaptee;

	public DbClientPanel_jTree_Popup_MouseAdapter(DbClientPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		try {
			adaptee.DbClient_Jtree_Popup_MouseAdapter(e);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

//EXCUTE BUTTON
class DbClientPanel_jTable_Excute_ActionListener implements ActionListener {
	private DbClientPanel adaptee;

	public DbClientPanel_jTable_Excute_ActionListener(DbClientPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			adaptee.DbClientExcute_ActionListener();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
//CLEAR BUTTON
class DbClientPanel_jTable_Clear_ActionListner implements ActionListener{
	private DbClientPanel adaptee;

	public DbClientPanel_jTable_Clear_ActionListner(DbClientPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			adaptee.DbClientClear_ActionListener();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
//Delete
class DbClientPanel_jMenuItem_Delete_ActionListner implements ActionListener{
	private DbClientPanel adaptee;

	public DbClientPanel_jMenuItem_Delete_ActionListner(DbClientPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			adaptee.DbClientDelete_ActionListener();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
//Update
class DbClientPanel_jMenuItem_Update_ActionListner implements ActionListener{
	private DbClientPanel adaptee;

	public DbClientPanel_jMenuItem_Update_ActionListner(DbClientPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			adaptee.DbClientUpdate_ActionListener();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

//CREATE
class DbClientPanel_jMenuItem_Create_ActionListner implements ActionListener{
	private DbClientPanel adaptee;

	public DbClientPanel_jMenuItem_Create_ActionListner(DbClientPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			adaptee.DbClientCreate_ActionListener();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

