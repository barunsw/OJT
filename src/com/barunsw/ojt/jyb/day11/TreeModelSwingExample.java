package com.barunsw.ojt.jyb.day11;

import javax.swing.*;
import javax.swing.tree.TreePath;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TreeModelSwingExample {
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Tree Model Example");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(400, 300);

			MyTreeNode rootNode = new MyTreeNode("Root");
			MyTreeNode childNode1 = new MyTreeNode("Child 1");
			MyTreeNode childNode2 = new MyTreeNode("Child 2");

			rootNode.addChild(childNode1);
			rootNode.addChild(childNode2);

			MyTreeModel treeModel = new MyTreeModel(rootNode);

			JTree tree = new JTree(treeModel);
			tree.setRootVisible(false); // 루트 노드를 숨김
			JScrollPane scrollPane = new JScrollPane(tree);

			JPanel searchPanel = new JPanel();
			JTextField searchField = new JTextField(15);
			JButton searchButton = new JButton("Search");

			searchButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String searchValue = searchField.getText();
					MyTreeNode foundNode = treeModel.searchNode(searchValue);
					if (foundNode != null) {
						TreePath path = new TreePath(foundNode);
						tree.setSelectionPath(path);
						tree.scrollPathToVisible(path);
						JOptionPane.showMessageDialog(frame, "Found: " + foundNode.getValue());
					} else {
						JOptionPane.showMessageDialog(frame, "Node not found.");
					}
				}
			});

			searchPanel.add(searchField);
			searchPanel.add(searchButton);

			frame.add(scrollPane, BorderLayout.CENTER);
			frame.add(searchPanel, BorderLayout.SOUTH);
			frame.setVisible(true);
		});
	}
}