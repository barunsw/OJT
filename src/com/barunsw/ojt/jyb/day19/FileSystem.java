package com.barunsw.ojt.jyb.day19;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import java.io.File;

class FileSystem {
	public DefaultTreeModel getFileTreeModel(String rootPath) {
		File rootFile = new File(rootPath);
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootFile);
		FileUtil.createRootFileTree(rootNode, rootFile);
		return new DefaultTreeModel(rootNode);
	}

	public File getSelectedFile(TreePath path) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
		return (File) node.getUserObject();
	}
}