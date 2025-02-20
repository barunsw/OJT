package com.barunsw.ojt.jyb.day19;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;

public class FileExplorer extends JFrame {
	private JTextField pathTextField;
	private JTree fileTree;
	private JTable fileTable;
	private JScrollPane treeScrollPane;
	private JScrollPane tableScrollPane;
	private FileSystem fileSystem;

	public FileExplorer() {
		setTitle("File Explorer");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		fileSystem = new FileSystem();

		pathTextField = new JTextField(50);
		pathTextField.setEditable(false);
		pathTextField.setText("C:\\");

		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(new File("C:\\"));
		fileTree = new JTree(rootNode);

		FileUtil.createRootFileTree(rootNode, new File("C:\\"));

		fileTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				if (fileTree.getSelectionPath() != null) {
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree
							.getLastSelectedPathComponent();
					File selectedFile = (File) selectedNode.getUserObject();
					pathTextField.setText(selectedFile.getPath());

					fileTable.setModel(new FileTableModel(selectedFile));

					if (selectedNode.getChildCount() == 1) {
						FileUtil.loadChildNodes(selectedNode);
					}
				}
			}
		});

		treeScrollPane = new JScrollPane(fileTree);
		treeScrollPane.setPreferredSize(new Dimension(300, 600));

		fileTable = new JTable();
		fileTable.setModel(new FileTableModel(new File("C:\\")));

		tableScrollPane = new JScrollPane(fileTable);
		tableScrollPane.setPreferredSize(new Dimension(500, 600));

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScrollPane, tableScrollPane);
		splitPane.setDividerLocation(300);
		splitPane.setOneTouchExpandable(true);

		setLayout(new GridBagLayout());

		this.add(pathTextField, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

		this.add(splitPane, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			FileExplorer explorer = new FileExplorer();
			explorer.setVisible(true);
		});
	}
}
