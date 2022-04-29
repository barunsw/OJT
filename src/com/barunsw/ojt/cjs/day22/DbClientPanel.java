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
	
	private static final int SEQINDEX = 1;
	private static final int NAMEINDEX= 2;
	private static final int AGEINDEX = 3;
	private static final int ADDRESSINDEX = 4;
	
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

	private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Database");
	private DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
	private DefaultMutableTreeNode mariaDbChild;
	private	DefaultMutableTreeNode postgresChild ;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String driverName;
	private ResultSet resultSet = null;

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
		
		jSplitPane_Tree.setDividerLocation(250);
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
			if (connectVo.getDb_type().equals(Db_type.MARIA)) {
				if (insertConnectData(connectData) > 0) {
					mariaDbChild = new DefaultMutableTreeNode(connectData);
					rootNode.add(mariaDbChild);
					connectDb(Db_type.MARIA);
				}
			}
			else if (connectVo.getDb_type().equals(Db_type.POSTGRES)) {
				if (insertConnectData(connectData) > 0) {
					postgresChild = new DefaultMutableTreeNode(connectData);
					rootNode.add(postgresChild);
					connectDb(Db_type.POSTGRES);
				}
			}
		}
		treeModel.nodeStructureChanged(rootNode);
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
		treeModel.setRoot(rootNode);
		jTree_DbInfo.setModel(treeModel);
		treeModel.reload();
	}


	// EXCUTE실행시
	public void dbClientExcute_ActionListener() throws Exception {
		String sql = jTextArea_DbQureyInput.getText();
		if (selectVo != null && sql != null) {
			if (sql.contains("select")) {
				int result = selectDbTable(sql);
				if (result > 0) {
					jTextFiled_Excute.setText("select가 실행되었습니다.");
				}
				else {
					JOptionPane.showMessageDialog(null, "잘못된 쿼리입니다.");
					jTextFiled_Excute.setText("잘못된 쿼리입니다.");
				}
			}
			else {
				int result = excuteQuery(sql);
				if (result > 0) {
					jTextFiled_Excute.setText(result + "개의 행이 실행되었습니다.");
				}
				else {
					JOptionPane.showMessageDialog(null, "잘못된 쿼리입니다.");
					jTextFiled_Excute.setText("잘못된 쿼리입니다.");
				}
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "DB가 선택되지 않았거나, 쿼리문이 입력되지 않았습니다.");
		}
	}
	//연결한 database의 table값을 하위 노드로 추가하여 넣어준다.
	private void connectDb(Db_type db_Type) {
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);) {
			ResultSet resultSet = conn.getMetaData().getTables(null, "OWN1", null, new String[] {"TABLE"});
			DefaultMutableTreeNode nodeName = null;
			if (db_Type.equals(Db_type.MARIA)) {
				nodeName = mariaDbChild;
			}
			else {
				nodeName = postgresChild;
			}
			while (resultSet.next()) {
				String table = resultSet.getString("TABLE_NAME");
				DefaultMutableTreeNode tableName = new DefaultMutableTreeNode(table);
				nodeName.add(tableName);
			}
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	//테이블에 SELECT 데이터값 그려준다.
	private int selectDbTable(String sql) throws Exception {
	
		Vector tableList = new Vector();
		Vector columnNameList = new Vector();
		int rowCount =0;
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement psmt = conn.prepareStatement(sql);) {
			resultSet = psmt.executeQuery();
			ResultSetMetaData metadata = resultSet.getMetaData();
			String tableName = metadata.getTableName(2);
			LOGGER.debug(tableName);
			int cols = metadata.getColumnCount();
			for (int i = 1; i <= cols; i++) {
				columnNameList.add(metadata.getColumnName(i));
			}
			tableModel.setColumn(columnNameList);
			jTable_DbSelect.setModel(tableModel);

			while (resultSet.next()) {
				Vector data = new Vector();
				for(int i =1; i <cols; i++) {
					data.add(resultSet.getString(i));
				}
				tableList.add(data);
			}
			resultSet.close();
			rowCount++;
		}
		catch (Exception e) {
			LOGGER.debug(e.getMessage(), e);
		}
		tableModel.setData(tableList);
		tableModel.fireTableDataChanged();
		return rowCount;
	}
	
	private int excuteQuery(String query) {
		String sql = query;
		int rowCount = 0;
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement psmt = conn.prepareStatement(sql);) {
			rowCount = psmt.executeUpdate();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return rowCount;
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
			try {
				setConnectData(dialogResult);
				addTreeData(dialogResult);
			}
			catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
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
			if (selectVo != null && deleteTree.getParent() ==rootNode) {
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
		if (selectedTreePath != null) {
			TreePath selectionPath = jTree_DbInfo.getSelectionPath();
			Object o = jTree_DbInfo.getLastSelectedPathComponent();

			if (o instanceof DefaultMutableTreeNode && !selectedTreePath.toString().equals("[Database]")) {
				DefaultMutableTreeNode select = (DefaultMutableTreeNode) o;
				try {
					ConnectVo vo = (ConnectVo) select.getUserObject();
					selectVo = vo;
					setConnectData(vo);
				}
				catch (Exception e2) {
					LOGGER.debug("해당 경로에는 정보가 없습니다");
				}
			}
		}
	}
	private void setConnectData(ConnectVo connectData) {
		if (connectData.getDb_type().toString().contains("maria")) {
			dbUrl = String.format(
					"jdbc:mysql://%s/%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false",
					connectData.getDbUrl(), connectData.getDbName());
		}
		else {
			dbUrl = String.format(
					"jdbc:postgresql://%s/%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false",
					connectData.getDbUrl(), connectData.getDbName());
		}
		dbUser = connectData.getDbUser();
		dbPassword = connectData.getDbPassword();
		driverName = connectData.getDb_type().toString();
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
