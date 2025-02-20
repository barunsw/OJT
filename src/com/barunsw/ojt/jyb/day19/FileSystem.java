package com.barunsw.ojt.jyb.day19;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import java.io.File;

class FileSystem {
	// 파일 트리 모델을 생성하는 메소드
	public DefaultTreeModel getFileTreeModel(String rootPath) {
		File rootFile = new File(rootPath);
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootFile);
		FileUtil.createRootFileTree(rootNode, rootFile); // 트리 생성
		return new DefaultTreeModel(rootNode);
	}

	// 선택된 파일을 반환하는 메소드 (추가된 부분)
	public File getSelectedFile(TreePath path) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
		return (File) node.getUserObject(); // 선택된 파일 반환
	}
}
