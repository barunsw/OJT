package com.barunsw.ojt.cjs.day18;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.cjs.common.CommonTableModel;

public class ExplorePanel extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExplorePanel.class);
	private JSplitPane jSplitPane = new JSplitPane();
	private JTree jTree_Explore = new JTree();
	private JTable jTable_List = new JTable();
	private JScrollPane jScrollPane_Table = new JScrollPane();
	private JScrollPane jScrollPane_Tree = new JScrollPane();

	private JTextField jTextField_Search = new JTextField();

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
		try {
			initData(rootNode);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		initEventListner();
	}

	private void initComponent() {
		this.setLayout(new GridBagLayout());

		this.add(jTextField_Search, new GridBagConstraints(0, 0, 1, 1, 0.1, 0.1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

		this.add(jSplitPane, new GridBagConstraints(0, 1, 1, 8, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
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
	}

	private void initData(DefaultMutableTreeNode node) throws IOException {
		node.removeAllChildren(); //중복으로 추가되지 않게 해당 하위노드를 모두 지워준다.
		FileVo vo = (FileVo) node.getUserObject(); // 해당 노드의 오브젝트값을 가져옴
		LOGGER.debug(vo.toStringFull());
		String created = "";
		long bytes = 0;
		File f = new File(vo.getPath());
		Vector tableList = new Vector();
		if (f.listFiles() != null) {
			for (File oneFile : f.listFiles()) {
				Vector inputData = new Vector();
				try {
					BasicFileAttributes attrs = Files.readAttributes(oneFile.toPath(), BasicFileAttributes.class);
					FileTime fileTime = attrs.creationTime();
					created = sdf.format(new Date(fileTime.toMillis()));
				}
				catch (Exception ex) {
				}
				FileVo fileVo = new FileVo(oneFile.getName(), created, oneFile.isDirectory(),
						oneFile.getAbsolutePath());
				Path path = Paths.get(fileVo.getPath());
				bytes = Files.size(path);
				if (oneFile.isDirectory()) {
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(fileVo);
					node.add(newNode);

					if (checkSubDir(oneFile.getAbsolutePath())) {
						newNode.add(new DefaultMutableTreeNode());
					}
				}
				inputData.add(fileVo.getName());
				inputData.add(fileVo.getType());
				inputData.add(bytes);
				inputData.add(fileVo.getCreated());
				tableList.add(inputData);
			}
		}
		tableModel.setData(tableList);
		tableModel.fireTableDataChanged();
		treeModel.reload();
		jTree_Explore.expandPath(new TreePath(node.getPath()));
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
		
		if (o instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode selectedVo = (DefaultMutableTreeNode) o;
			initData(selectedVo);
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}