package com.barunsw.ojt.cjs.day22;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
	
	private static final int nameIndex = 0;
	private static final int ageIndex = 1;
	private static final int addressIndex = 2;
	
	private JSplitPane jSplitPane_Tree = new JSplitPane();
	private JSplitPane jSplitPane_Table = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

	private JTable jTable_DbSelect = new JTable();
	private JTree jTree_DbInfo = new JTree();
	private JTextArea jTextArea_DbQureyInput = new JTextArea("select * from tb_db_user");
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
	private DefaultMutableTreeNode mariaDbTree = new DefaultMutableTreeNode("MariaDB");
	private DefaultMutableTreeNode postGresTree = new DefaultMutableTreeNode("PostGres");
	private DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);

    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String driverName;
    
	private CommonTableModel  tableModel = new CommonTableModel ();
	private ConnectSetType setType;
	private JdbcConnect jdbcConnect;
	private ConnectVo selectVo;
	public DbClientPanel() {
		try {
			initComponent();
			initEvent();
			initTree();
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
		jTree_DbInfo.addMouseListener(new DbClientPanel_jTree_GetDbData_MouseAdapter(this));
		jMenu_ConnectCreate.addActionListener(new DbClientPanel_jMenuItem_Create_ActionListner(this));
		jMenu_ConnectUpdate.addActionListener(new DbClientPanel_jMenuItem_Update_ActionListner(this));
		jMenu_ConnectDelete.addActionListener(new DbClientPanel_jMenuItem_Delete_ActionListner(this));
	}

	private void addTreeData(ConnectVo connectVo) throws Exception {
		TreePath tp = jTree_DbInfo.getSelectionPath();

		ConnectVo connectData = new ConnectVo(connectVo.getDb_type(), connectVo.getDbUrl(), connectVo.getDbUser(),
				connectVo.getDbPassword(), connectVo.getDbName());
		if (setType == ConnectSetType.ADD) {
			if (connectVo.getDb_type().equals(Db_type.MARIA)
					&& tp.getLastPathComponent().toString().equals("MariaDB")) {
				if (insertConnectData(connectData) > 0) {
					DefaultMutableTreeNode mariaDbChild = new DefaultMutableTreeNode(connectData);
					mariaDbTree.add(mariaDbChild);
					treeModel.nodeStructureChanged(mariaDbTree);
				}
			}
			else if (connectVo.getDb_type().equals(Db_type.POSTGRES)
					&& tp.getLastPathComponent().toString().equals("PostGres")) {
				if (insertConnectData(connectData) > 0) {
					DefaultMutableTreeNode postgresChild = new DefaultMutableTreeNode(connectData);
					postGresTree.add(postgresChild);
					treeModel.nodeStructureChanged(postGresTree);
				}
			}
		}
	}

	private int insertConnectData(ConnectVo connect) {
		jdbcConnect = new JdbcConnect(connect);
		int executeSuccess = 0;
		executeSuccess= jdbcConnect.insertConnectData();
		return executeSuccess;
	}

	private void deleteConnectData(ConnectVo connect) {
		jdbcConnect = new JdbcConnect(connect);
		String result = connect.getDbUrl().substring(connect.getDbUrl().lastIndexOf(":") + 1);
		jdbcConnect.deleteConnectData(result);
	}
	
	private void initTree() {
		rootNode.add(mariaDbTree);
		rootNode.add(postGresTree);

		treeModel.setRoot(rootNode);
		jTree_DbInfo.setModel(treeModel);
		jTree_DbInfo.setRootVisible(false);
		treeModel.reload();
	}


	// EXCUTE
	public void dbClientExcute_ActionListener() throws Exception {
		String sql = jTextArea_DbQureyInput.getText();
		if (sql.contains("select")) {
			if (!(selectDbTable(sql) == 1) && selectVo != null) {
				JOptionPane.showMessageDialog(null, "잘못된 요청입니다");
			}
		}
		else {
			int result = excuteQuery(sql);
			jTextFiled_Excute.setText(result + "개의 행이 실행되었습니다.");
		}
	}

	private int selectDbTable(String sql) throws Exception {
		ResultSet rs = null;
		Vector tableList = new Vector();
		Vector colsName = new Vector();
		Vector data = new Vector();
		int j = 0;
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement psmt = conn.prepareStatement(sql);) {
			rs = psmt.executeQuery();
			ResultSetMetaData md = rs.getMetaData();

			int cols = md.getColumnCount();
			
			for (int i = 1; i <= cols; i++) {
				colsName.add(md.getColumnName(i));
			}
			tableModel.setColumn(colsName);
			jTable_DbSelect.setModel(tableModel);
			
			while (rs.next()) {
				data.add(rs.getInt(1));
				data.add(rs.getString(2));
				data.add(rs.getInt(3));
				data.add(rs.getString(4));
				
				tableList.add(data);
			}
			j++;
		}
		tableModel.setData(tableList);
		tableModel.fireTableDataChanged();
		return j;
	}
	
	private int excuteQuery(String query) {
		String sql = query;
		int result = 0;
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement psmt = conn.prepareStatement(sql);) {
			UserVo user = new UserVo();

			psmt.setString(nameIndex, user.getName());
			psmt.setInt(ageIndex, user.getAge());
			psmt.setString(addressIndex, user.getAddress());

			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return result;
	}

	// POPUP
	public void dbClient_Jtree_Popup_MouseAdapter(MouseEvent e) {
		if (e.isPopupTrigger()) {
			TreePath tp = jTree_DbInfo.getClosestPathForLocation(e.getX(), e.getY());
			if (tp != null) {
				jTree_DbInfo.setSelectionPath(tp);
				jPopupMenu_Dbmenu.show(DbClientPanel.this.jTree_DbInfo, e.getX(), e.getY()); // 해당 행의 좌표값에 팝업창을 띄워줌
			}
		}
	}
	//다이얼로그불러오면서 종료될 때 value가져와서 tree에 보내줌
	private void showPopup(ConnectSetType setType, ConnectVo selectVo) throws Exception {
		ConnectVo dialogResult = ConnectSetDialog.showDialog(DbClientMain.dbFrame, setType, selectVo); // 리턴값 ConnectVo
		if (dialogResult.getDbName()!= null ) {
			addTreeData(dialogResult);
		}
		else {
			return;
		}
	}

	// JtextArea CLEAR
	public void dbClientClear_ActionListener() {
		jTextArea_DbQureyInput.setText("");
	}

	public void dbClientCreate_ActionListener() throws Exception {
		setType = ConnectSetType.ADD;
		showPopup(setType, null);
	}

	public void dbClientUpdate_ActionListener() throws Exception {
		setType = ConnectSetType.UPDATE;
		showPopup(setType, selectVo);
	}

	public void dbClientDelete_ActionListener() {
		Object o = jTree_DbInfo.getLastSelectedPathComponent();
		if (o instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode deleteTree = (DefaultMutableTreeNode) o;
			deleteTree.getUserObject();
			LOGGER.debug(deleteTree.getUserObject() + "");
			if (deleteTree.getUserObject() != "MariaDB" && deleteTree.getUserObject() != "PostGres") {
				int result = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					deleteTree.removeFromParent();
					deleteConnectData(selectVo);
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "삭제할 수 없습니다.");
			}
		}
		treeModel.nodeStructureChanged(rootNode);
	}

	 void jTree_GetDbData_MouseAdapter(MouseEvent e) throws Exception {
		TreePath selectedTreePath = jTree_DbInfo.getPathForLocation(e.getX(), e.getY());
		LOGGER.debug(selectedTreePath +"");
		if (selectedTreePath != null) {
			TreePath selectionPath = jTree_DbInfo.getSelectionPath();
			Object o = jTree_DbInfo.getLastSelectedPathComponent();
			if (!o.toString().equals("MariaDB") && !o.toString().equals("PostGres")) {
				if (o instanceof DefaultMutableTreeNode) {
					DefaultMutableTreeNode select = (DefaultMutableTreeNode) o;
					ConnectVo vo = (ConnectVo) select.getUserObject();
					selectVo = vo;
					dbUrl = String.format(
							"jdbc:mysql://%s/%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false",
							vo.getDbUrl(), vo.getDbName());
					dbUser = vo.getDbUser();
					dbPassword = vo.getDbPassword();
					driverName = vo.getDb_type().toString();
				}
			}
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
			adaptee.dbClient_Jtree_Popup_MouseAdapter(e);
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
			adaptee.dbClientExcute_ActionListener();
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
			adaptee.dbClientClear_ActionListener();
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
			adaptee.dbClientDelete_ActionListener();
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
			adaptee.dbClientUpdate_ActionListener();
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
			adaptee.dbClientCreate_ActionListener();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}


class DbClientPanel_jTree_GetDbData_MouseAdapter extends MouseAdapter {
	private DbClientPanel adaptee;

	public DbClientPanel_jTree_GetDbData_MouseAdapter(DbClientPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		try {
			adaptee.jTree_GetDbData_MouseAdapter(e);
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
