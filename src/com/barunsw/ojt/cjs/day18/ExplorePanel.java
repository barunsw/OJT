package com.barunsw.ojt.cjs.day18;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.cjs.common.CommonTableModel;

public class ExplorePanel extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExplorePanel.class);
	private JSplitPane jSplitPane = new JSplitPane();
	private JTree jTree_Explore = new JTree();
	private JButton JButton_Search = new JButton("이동");
	private JTable jTable_List = new JTable();
	private JScrollPane jScrollPane_Table = new JScrollPane();
	private JScrollPane jScrollPane_Tree = new JScrollPane();
	private JPanel jPanel_Search = new JPanel();
	private JTextField jTextField_Search = new JTextField(50);

	private CommonTableModel tableModel = new CommonTableModel();
	private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(new FileVo("C:", "", true, "/"));

	private DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
	String separator = System.getProperty("file.separator");
	DefaultMutableTreeNode treeData;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public ExplorePanel() {
		initComponent();
		initTree();
		initTable();
		initData(rootNode);
		initEventListner();
	}

	private void initComponent() {
		this.setLayout(new GridBagLayout());
		this.add(jSplitPane, new GridBagConstraints(0, 1, 5, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));

		this.add(jPanel_Search, new GridBagConstraints(0, 0, 3, 1, 1.0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		jPanel_Search.add(jTextField_Search, new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

//		jPanel_Search.add(JButton_Search, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
//				GridBagConstraints.VERTICAL, new Insets(0, 5, 5, 5), 0, 0));

		jScrollPane_Table.getViewport().add(jTable_List);
		jScrollPane_Tree.getViewport().add(jTree_Explore);

		jSplitPane.setRightComponent(jScrollPane_Table);
		jSplitPane.setLeftComponent(jScrollPane_Tree);

		jSplitPane.setDividerLocation(220);

		jTree_Explore.addMouseListener(null);
	}

	private void initTable() {
		Vector<String> columnData = new Vector<>();
		columnData.add("Name");
		columnData.add("Type");
		columnData.add("Size");
		columnData.add("Created");
		columnData.add("path");
		tableModel.setColumn(columnData);
		jTable_List.setModel(tableModel);

		jTable_List.getColumn("path").setWidth(0);
		jTable_List.getColumn("path").setMinWidth(0);
		jTable_List.getColumn("path").setMaxWidth(0);
	}

	private void initTree() {
		treeModel.setRoot(rootNode);
		jTree_Explore.setModel(treeModel);
		treeModel.reload();
	}

	private void initEventListner() {
		jTree_Explore.addMouseListener(new ExplorePanel_jTree_Explore_MouseAdapter(this));
		jTree_Explore.addTreeWillExpandListener(null);
		jTextField_Search.addKeyListener(new ExplorePanel_jTextField_Search_keyListener(this));
		jTable_List.addMouseListener(new ExplorePanel_jTable_Explore_MouseAdapter(this));
	}

	private void initData(DefaultMutableTreeNode node) {
		node.removeAllChildren(); // 중복으로 추가되지 않게 해당 하위노드를 모두 지워준다.
		LOGGER.debug(node + "");
		FileVo vo = (FileVo) node.getUserObject(); // 해당 노드의 오브젝트값을 가져옴
		LOGGER.debug(vo.toStringFull());
		String created = "";
		long kbytes = 0;
		File f = new File(vo.getPath());
		Vector tableList = new Vector();
		if (node != rootNode) {
			Vector v = new Vector();
			v.add("..");
			tableList.add(v);
		}

		if (f.listFiles() != null) {
			for (File oneFile : f.listFiles()) {
				Vector inputData = new Vector();
				FileVo fileVo = new FileVo(oneFile.getName(), created, oneFile.isDirectory(),
						oneFile.getAbsolutePath());
				Path path = Paths.get(fileVo.getPath());
				try {
					BasicFileAttributes attrs = Files.readAttributes(oneFile.toPath(), BasicFileAttributes.class);
					FileTime fileTime = attrs.creationTime();
					created = sdf.format(new Date(fileTime.toMillis()));
					kbytes = (Files.size(path) / 1024);
				}
				catch (Exception ex) {
				}

				if (oneFile.isDirectory()) {
					treeData = new DefaultMutableTreeNode(fileVo);
					node.add(treeData);

					if (checkSubDir(oneFile.getAbsolutePath())) {
						treeData.add(new DefaultMutableTreeNode());
					}
				}
				inputData.add(fileVo.getName());
				inputData.add(fileVo.getType());
				inputData.add(kbytes);
				inputData.add(fileVo.getCreated());
				inputData.add(oneFile.getAbsoluteFile());
				tableList.add(inputData);
			}
		}
		tableModel.setData(tableList);
		tableModel.fireTableDataChanged();
//		treeModel.reload();
		treeModel.nodeStructureChanged(node);
		jTree_Explore.expandPath(new TreePath(node.getPath()));
		if (rootNode == node) {
			jTree_Explore.setSelectionPath(new TreePath(rootNode.getPath()));
		}
		else {
			jTree_Explore.setSelectionPath(new TreePath(node.getPath()));

		}
	}

	private boolean checkSubDir(String filePath) {
		File f = new File(filePath);
		File[] fileList = f.listFiles();
		if (fileList == null) {
			return false;
		}

		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				return true;
			}
		}
		return false;
	}

	void jTree_Explore_mouseReleased(MouseEvent e) throws IOException {
		Object o = jTree_Explore.getLastSelectedPathComponent();
		LOGGER.debug(jTree_Explore.getLastSelectedPathComponent().getClass() + "");
		
		TreePath p = jTree_Explore.getSelectionPath();
		jTree_Explore.setSelectionPath(new TreePath(p.getPath()));

		LOGGER.debug(p.toString());
		
		if (o instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode selectedVo = (DefaultMutableTreeNode) o;
			LOGGER.debug(selectedVo.getPath() +"");
			initData(selectedVo);
		}
	}

	public void jTextField_Search_keyListener(KeyEvent e) throws IOException {
		switch (e.getKeyChar()) {
		case KeyEvent.VK_ENTER:
			String path = jTextField_Search.getText();
//			treeData = new DefaultMutableTreeNode(new FileVo(path, "", true, path));
			treeData = new DefaultMutableTreeNode(path);
			initData(treeData);
		}
	}

	public void jTable_Explore_mouseReleased(MouseEvent e) throws IOException {

		if (e.getClickCount() == 2) {
			int row = jTable_List.getSelectedRow();
			//LOGGER.debug(row + "");
			String path = jTable_List.getValueAt(row, 0).toString();
			//LOGGER.debug(path);

			if (!path.equals("..")) {
				TreePath p = jTree_Explore.getSelectionPath();
				TreePath p2 = jTree_Explore.getAnchorSelectionPath();
				TreePath p4 = jTree_Explore.getLeadSelectionPath();
				DefaultMutableTreeNode p5 = (DefaultMutableTreeNode) jTree_Explore.getLastSelectedPathComponent();
				
				jTree_Explore.setSelectionPath(new TreePath(p.getPath()));

				LOGGER.debug("[TreePath p] : {}", p.toString());
				LOGGER.debug("[TreePath p.getPath] : {}", new TreePath(p.getPath()).toString());
				LOGGER.debug("[TreePath p2.getAnchorSelectionPath] : {}", new TreePath(p2.getPath()).toString());
				LOGGER.debug("[TreePath p4.getLeadSelectionPath] : {}", new TreePath(p4.getPath()).toString());
				LOGGER.debug("[TreePath p5.getLastSelectedPathComponent] : {}", new TreePath(p5.getPath()).toString());
				
				Object o = jTree_Explore.getLastSelectedPathComponent();
				LOGGER.debug(o + "");
				if (o instanceof DefaultMutableTreeNode) {
					DefaultMutableTreeNode topRoot = (DefaultMutableTreeNode) o;
					LOGGER.debug(topRoot.getPath() +"");
					for (int i = 0; i < topRoot.getChildCount(); i++) {
						if (String.valueOf(topRoot.getChildAt(i)).equals(path)) {
							LOGGER.debug("왜 안돼 ,,");
							DefaultMutableTreeNode a = (DefaultMutableTreeNode) topRoot.getChildAt(i);
							LOGGER.debug(topRoot + "");
							a.getPath();
							initData(a);
						}
					}
				}
			}

			else {
				TreePath p = jTree_Explore.getSelectionPath();
				jTree_Explore.setSelectionPath(p.getParentPath());
				LOGGER.debug(p + "");
				Object o = jTree_Explore.getLastSelectedPathComponent();
				if (o instanceof DefaultMutableTreeNode) {
					LOGGER.debug("안됨?");
					DefaultMutableTreeNode topRoot = (DefaultMutableTreeNode) o;
					initData(topRoot);
				}
			}
		}
	}

	/*
	 * @Override public void valueChanged(TreeSelectionEvent e) {
	 * LOGGER.debug(e.getOldLeadSelectionPath() + ""); TreePath getPath = new
	 * TreePath(e); LOGGER.debug(getPath + ""); }
	 * 
	 * @Override public void treeWillExpand(TreeExpansionEvent event) throws
	 * ExpandVetoException { if (event.getSource() == jTree_Explore) { Object o =
	 * jTree_Explore.getLastSelectedPathComponent();
	 * LOGGER.debug(jTree_Explore.getLastSelectedPathComponent().getClass() + "");
	 * LOGGER.debug(o.getClass() + ""); if (o instanceof DefaultMutableTreeNode) {
	 * DefaultMutableTreeNode selectedVo = (DefaultMutableTreeNode) o; try {
	 * initData(selectedVo); } catch (IOException e) { e.printStackTrace(); } } } }
	 * 
	 * @Override public void treeWillCollapse(TreeExpansionEvent event) throws
	 * ExpandVetoException {
	 * 
	 * }
	 */
}

class ExplorePanel_jTree_Explore_MouseAdapter extends MouseAdapter {
	private ExplorePanel adaptee;

	public ExplorePanel_jTree_Explore_MouseAdapter(ExplorePanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		try {
			adaptee.jTree_Explore_mouseReleased(e);
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}

class ExplorePanel_jTable_Explore_MouseAdapter extends MouseAdapter {
	private ExplorePanel adaptee;

	public ExplorePanel_jTable_Explore_MouseAdapter(ExplorePanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		try {
			adaptee.jTable_Explore_mouseReleased(e);
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}

class ExplorePanel_jTextField_Search_keyListener extends KeyAdapter {
	private ExplorePanel adaptee;

	public ExplorePanel_jTextField_Search_keyListener(ExplorePanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			adaptee.jTextField_Search_keyListener(e);
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
