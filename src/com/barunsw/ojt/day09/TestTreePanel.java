package com.barunsw.ojt.day09;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class TestTreePanel extends JPanel {
	private JPanel jPanel_Command = new JPanel();
	
	private JButton jButton_Reload = new JButton("조회");
	
	private JScrollPane jScrollPane_Result = new JScrollPane();
	private JTree jTree_Result = new JTree();
	
	private DefaultTreeModel treeModel;
	
	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	private DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode("ROOT"); 
	
	public TestTreePanel() {
		try {
			initComponent();
			initTree();
			initData();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void initComponent() {
		this.setLayout(gridBagLayout);
		jPanel_Command.setLayout(gridBagLayout);
		
		jButton_Reload.setPreferredSize(new Dimension(120, 22));
		
		jPanel_Command.add(jButton_Reload, new GridBagConstraints(0, 0, 1, 1,
				1.0, 1.0,
				GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
				new Insets(5, 5, 5, 5),
				0, 0));
		
		this.add(jPanel_Command, new GridBagConstraints(0, 0, 1, 1,
				1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0),
				0, 0));
		
		this.add(jScrollPane_Result, new GridBagConstraints(0, 1, 1, 1,
				1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 5, 5, 5),
				0, 0));
		
		jScrollPane_Result.getViewport().add(jTree_Result);
		
		jButton_Reload.addActionListener(new TestTablePanel_jButton_Reload_ActionListener(this));
	}
	
	private void initTree() {
		treeModel = new DefaultTreeModel(rootTreeNode);
		
		jTree_Result.setModel(treeModel);
//		jTree_Result.setRootVisible(false);
//		jTree_Result.expandRow(0);
		
		jTree_Result.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				Object o = jTree_Result.getSelectionPath().getLastPathComponent();

			}
		});
	}
	
	private void initData() {
		rootTreeNode.add(new DefaultMutableTreeNode("ㄱ"));
		rootTreeNode.add(new DefaultMutableTreeNode("ㄴ"));
		rootTreeNode.add(new DefaultMutableTreeNode("ㄷ"));
		rootTreeNode.add(new DefaultMutableTreeNode("ㄹ"));
		rootTreeNode.add(new DefaultMutableTreeNode("ㅁ"));
		rootTreeNode.add(new DefaultMutableTreeNode("ㅂ"));
	}
	
	void jButton_Reload_actionPerformed(ActionEvent e) {
	}
}

class TestTablePanel_jButton_Reload_ActionListener implements ActionListener {
	private TestTreePanel adaptee;
	
	public TestTablePanel_jButton_Reload_ActionListener(TestTreePanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		adaptee.jButton_Reload_actionPerformed(e);
	}
}