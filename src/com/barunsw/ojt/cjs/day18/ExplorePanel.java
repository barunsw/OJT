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
	String separator = System.getProperty("File.separator");
	DefaultMutableTreeNode treeData;
	TreeNode absolutePath;
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
		tableModel.setColumn(columnData);
		jTable_List.setModel(tableModel);

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
		node.removeAllChildren();
		LOGGER.debug(node + "");
		FileVo vo = (FileVo) node.getUserObject();
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
				tableList.add(inputData);
			}
		}
		tableModel.setData(tableList);
		tableModel.fireTableDataChanged();
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

		TreePath p = jTree_Explore.getSelectionPath();
		jTree_Explore.setSelectionPath(new TreePath(p.getPath()));

		LOGGER.debug(p.toString());

		if (o instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode selectedVo = (DefaultMutableTreeNode) o;
			initData(selectedVo);
		}
	}

	public void jTextField_Search_keyListener(KeyEvent e) throws IOException {
		switch (e.getKeyChar()) {
		case KeyEvent.VK_ENTER:
			String path = jTextField_Search.getText();
			// textField에 입력한 경로를 그대로 treenode로 만들어서 가져올 수 있는지,,
			// treepath를 구해야하는데,,
			
			treeData = new DefaultMutableTreeNode(new FileVo("", "", true, path));
			initData(treeData);
//			if (!path.equals("/")) {
//				String paths[] = path.split("/");
//				path = paths[paths.length - 1];
//			}
//			TreePath p = jTree_Explore.getSelectionPath();
//			jTree_Explore.setSelectionPath(new TreePath(p.getPath()));
//			Object o = jTree_Explore.getLastSelectedPathComponent();
// 
//			treeData = (DefaultMutableTreeNode) o;
//			treeData.setUserObject(path);
//			LOGGER.debug(treeData + "");
//			initData(treeData);
		}
	}

	public void jTable_Explore_mouseReleased(MouseEvent e) throws IOException {

		if (e.getClickCount() == 2) { // 더블클릭하면
			int row = jTable_List.getSelectedRow();
			String path = jTable_List.getValueAt(row, 0).toString();

			if (!path.equals("..")) { // 상위디렉토리가 아닌

				TreePath p = jTree_Explore.getSelectionPath();
				jTree_Explore.setSelectionPath(new TreePath(p.getPath()));

				Object o = jTree_Explore.getLastSelectedPathComponent();
				if (o instanceof DefaultMutableTreeNode) {
					DefaultMutableTreeNode dirRoot = (DefaultMutableTreeNode) o; // 선택한 경로의 노드를 가져와서
					for (int i = 0; i < dirRoot.getChildCount(); i++) { // 해당 노드의 자식 노드수만큼 반복함
						if (String.valueOf(dirRoot.getChildAt(i)).equals(path)) { // 선택한 행의 이름과 같은 노드의 이름이 같으면
							DefaultMutableTreeNode dirRootNode = (DefaultMutableTreeNode) dirRoot.getChildAt(i);
							LOGGER.debug(dirRootNode + "");
							initData(dirRootNode); // 해당 노드를 출력
						}
					}
				}
			}

			else {// 상위 폴더로
				TreePath p = jTree_Explore.getSelectionPath();
				jTree_Explore.setSelectionPath(p.getParentPath());
				LOGGER.debug(p + "");
				Object o = jTree_Explore.getLastSelectedPathComponent();
				if (o instanceof DefaultMutableTreeNode) {
					DefaultMutableTreeNode topRoot = (DefaultMutableTreeNode) o;
					initData(topRoot);
				}
			}
		}
	}
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
