package com.barunsw.ojt.jyb.day19;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

public class FileUtil {

	public static long formatSize(long size) {
		return size;
	}

	public static void createRootFileTree(DefaultMutableTreeNode parentNode, File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (File f : files) {
					if (f.isDirectory()) {
						DefaultMutableTreeNode node = new DefaultMutableTreeNode(f);
						node.add(new DefaultMutableTreeNode("Loading..."));
						parentNode.add(node);
					}
				}
			}
		}
	}

	public static void loadChildNodes(DefaultMutableTreeNode parentNode) {
		if (parentNode.getChildCount() == 1 && parentNode.getChildAt(0).toString().equals("Loading...")) {
			parentNode.removeAllChildren();
			File file = (File) parentNode.getUserObject();

			File[] files = file.listFiles();
			if (files != null) {
				for (File f : files) {
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(f);
					parentNode.add(node);
					if (f.isDirectory()) {
						node.add(new DefaultMutableTreeNode("Loading..."));
					}
				}
			}
		}
	}

	public static String getFileType(File file) {
		return file.isDirectory() ? "폴더" : "파일";
	}
}