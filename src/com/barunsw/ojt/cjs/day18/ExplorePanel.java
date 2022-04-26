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
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.cjs.common.CommonTableModel;

public class ExplorePanel extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExplorePanel.class);

	private final int ABSOLUTE_PATH = 4;

	private JSplitPane jSplitPane = new JSplitPane();
	private JTree jTree_Explore = new JTree();
	private JTable jTable_List = new JTable();
	private JScrollPane jScrollPane_Table = new JScrollPane();
	private JScrollPane jScrollPane_Tree = new JScrollPane();
	private JPanel jPanel_Search = new JPanel();
	private JTextField jTextField_Search = new JTextField();

	private CommonTableModel tableModel = new CommonTableModel();

  private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(
			new FileVo("C:", "", true, new File("/").getAbsolutePath()));

	private DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
	private DefaultMutableTreeNode treeData;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private GridBagLayout gridBagLayout = new GridBagLayout();

	public ExplorePanel() {
		try {
			initComponent();
			initTree();
			initTable();
			initEventListner();
			initTreeData(rootNode);
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	private void initComponent() {
		this.setLayout(gridBagLayout);
		jPanel_Search.setLayout(gridBagLayout);

		this.add(jPanel_Search, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		this.add(jSplitPane, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));

		jPanel_Search.add(jTextField_Search, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

		jScrollPane_Table.getViewport().add(jTable_List);
		jScrollPane_Tree.getViewport().add(jTree_Explore);

		jSplitPane.setRightComponent(jScrollPane_Table);
		jSplitPane.setLeftComponent(jScrollPane_Tree);

		jSplitPane.setDividerLocation(220);

		jTree_Explore.addMouseListener(null);
	}

	private void initTree() {
		treeModel.setRoot(rootNode);
		jTree_Explore.setModel(treeModel);
		treeModel.reload();
	}

	private void initTable() {
		Vector<String> columnData = new Vector<>();
		columnData.add("Name");
		columnData.add("Type");
		columnData.add("Size");
		columnData.add("Created");

		tableModel.setColumn(columnData);
		jTable_List.setModel(tableModel);
	}

	private void initEventListner() {
		jTree_Explore.addTreeWillExpandListener(new ExplorePanel_jTree_Explore_TreeWillExpandListener(this));
		jTextField_Search.addKeyListener(new ExplorePanel_jTextField_Search_keyListener(this));
		jTable_List.addMouseListener(new ExplorePanel_jTable_Explore_MouseAdapter(this));
	}

	private void initTreeData(DefaultMutableTreeNode node) {

		node.removeAllChildren();
		LOGGER.debug(node + "");
		FileVo vo = (FileVo) node.getUserObject();
		LOGGER.debug(vo.toStringFull());

		String created = "";
		File f = new File(vo.getPath());
		File[] fileList = f.listFiles();
		if (fileList != null) {
			for (File oneFile : fileList) {
				FileVo fileVo = new FileVo(oneFile.getName(), created, oneFile.isDirectory(),
						oneFile.getAbsolutePath());
				if (oneFile.isDirectory()) {
					treeData = new DefaultMutableTreeNode(fileVo);
					node.add(treeData);

					if (checkSubDir(oneFile.getAbsolutePath())) {
						treeData.add(new DefaultMutableTreeNode());
					}
				}
			}
			setTableData(vo);
		}
		setFilePath(vo);
		setTreePath(vo);
	}

	void setTableData(FileVo file) {
		String created = "";
		long kbytes = 0;
		Vector tableList = new Vector();
		File f = new File(file.getPath());

		File parentFile = f.getParentFile();

		if (!(parentFile == null) && file.getPath() != "\\") {
			Vector v = new Vector();

			v.add("..");
			v.add("");
			v.add("");
			v.add("");

			FileVo parentVo = new FileVo();
			parentVo.setPath(parentFile.getAbsolutePath());

			v.add(parentVo);

			tableList.add(v);
		}

		File[] fileList = f.listFiles();
		if (fileList != null) {
			for (File oneFile : fileList) {
				try {
					Vector inputData = new Vector();
					FileVo fileVo = new FileVo(oneFile.getName(), created, oneFile.isDirectory(),
							oneFile.getAbsolutePath());
					Path path = Paths.get(fileVo.getPath());
					BasicFileAttributes attrs = Files.readAttributes(oneFile.toPath(), BasicFileAttributes.class);
					FileTime fileTime = attrs.creationTime();
					created = sdf.format(new Date(fileTime.toMillis()));
					kbytes = (Files.size(path) / 1024);

					inputData.add(fileVo.getName());
					inputData.add(fileVo.getType());
					inputData.add(kbytes);
					inputData.add(fileVo.getCreated());
					inputData.add(fileVo);

					tableList.add(inputData);
				}
				catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			}

			tableModel.setData(tableList);
			tableModel.fireTableDataChanged();
		}
		setTreePath(file);
	}

	private void setFilePath(FileVo fileVo) {
		// C:\\apache\\apache-jmeter-5.4.1 이렇게 표시
		File file = new File(fileVo.getPath());
		jTextField_Search.setText(file.getAbsolutePath());

	}

	private TreeNode findTreeNode(TreeNode parentNode, String[] paths, int depth) {
		Enumeration<TreeNode> childEnum = parentNode.children(); // 반복문의 역할을 하여 데이터를 한 번에 출력할 수 있도록 한다.

		while (childEnum.hasMoreElements()) { // 현재의 데이터 이후에도 데이터가 있는지 확인한다.
			TreeNode childNode = childEnum.nextElement(); // 다음 데이터로 이동시키고 현재 데이터를 꺼내서 반환한다.
			LOGGER.debug(paths[depth] + "");
			LOGGER.debug(childNode + "-------------");
			if (paths[depth].equals(childNode.toString())) { //
				if (paths.length - 1 == depth) {
					LOGGER.debug(childNode + "++++++++++++");
					return childNode;
				}
				else {
					return findTreeNode(childNode, paths, depth + 1);
				}
			}
		}
		
		return null;
	}
	
	private void setTreePath(FileVo fileVo) {

		String path = fileVo.getPath();
		String[] paths = path.split("\\" + File.separator);
		// C:, apache, apache-jmeter-5.4.1

		TreeNode treeNode = rootNode;

		int pathLen = paths.length;
		for (int i = 0; i < paths.length; i++) {
			LOGGER.debug(paths[i] + "");
		}
		if (pathLen > 1) {
			LOGGER.debug(pathLen + "");
			treeNode = findTreeNode(rootNode, paths, 1);
		}
		LOGGER.debug("setTreePath:" + treeNode);

		if (treeNode != null) {
		TreeNode[] treeNodes = treeModel.getPathToRoot(treeNode);
		jTree_Explore.setSelectionPath(new TreePath(treeNodes));
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


	void jTree_Explore_treeWillExpand(TreeExpansionEvent e) {
		TreePath expandPath = e.getPath();
		LOGGER.debug("treeWillExpand:" + expandPath);

		Object o = expandPath.getLastPathComponent();

		if (o instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode selectedVo = (DefaultMutableTreeNode) o;
			Object userObject = selectedVo.getUserObject();
			if (userObject instanceof FileVo) {
				FileVo fileVo = (FileVo) userObject;
				setFilePath(fileVo);
			}
			initTreeData(selectedVo);
		}
	}

	public void jTextField_Search_keyListener(KeyEvent e) throws IOException {
		switch (e.getKeyChar()) {
		case KeyEvent.VK_ENTER:
			String path = jTextField_Search.getText();
			FileVo file = new FileVo("", "", true, path);
			setTableData(file);
		}
	}

	public void jTable_Explore_mouseReleased(MouseEvent e) throws IOException {

		int row = jTable_List.getSelectedRow();

		if (e.getClickCount() == 2) {
			Object selectedObject = tableModel.getValueAt(row, ABSOLUTE_PATH);
			LOGGER.debug("selectedObject:" + selectedObject);

			if (selectedObject instanceof FileVo) {
				FileVo fileVo = (FileVo) selectedObject;

				LOGGER.debug(fileVo.getPath());
				
				setTreePath(fileVo);
				setFilePath(fileVo);
				setTableData(fileVo);
			}
		}
	}
}

class ExplorePanel_jTree_Explore_TreeWillExpandListener implements TreeWillExpandListener {
	private ExplorePanel adaptee;

	public ExplorePanel_jTree_Explore_TreeWillExpandListener(ExplorePanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
		try {
			adaptee.jTree_Explore_treeWillExpand(event);
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
		try {
			adaptee.jTree_Explore_treeWillExpand(event);
		}
		catch (Exception e1) {
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
