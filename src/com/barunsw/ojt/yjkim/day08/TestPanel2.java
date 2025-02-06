package com.barunsw.ojt.yjkim.day08;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel2 extends JPanel implements ActionListener,TreeSelectionListener {
	private static final Logger LOGGER = LogManager.getLogger(TestPanel2.class);
	
	private JTree jtree;
	DefaultMutableTreeNode root, parent, parent2, child ,child2, child3;
	private JScrollPane jScrollPane_Tree = new JScrollPane();

	private JPanel jPanel_InputField = new JPanel();
	private JLabel jLabel_Num 		 = new JLabel("번호");
	private JLabel jLabel_Name 		 = new JLabel("이름");
	private JLabel jLabel_Age 		 = new JLabel("나이");
	private JLabel jLabel_Address	 = new JLabel("주소");
	
	private JTextField jTextField_Num 		= new JTextField(3);
	private JTextField jTextField_Name 		= new JTextField(10);
	private JTextField jTextField_Age	 	= new JTextField(3);
	private JTextField jTextField_Address 	= new JTextField(15);
	
	private JPanel jPanel_Button 	= new JPanel();
	private JButton jButton_Add 	= new JButton("추가");
	private JButton jButton_Delete 	= new JButton("삭제");
	
	public TestPanel2() {
		try {
			
			initComponent();
			initButtonEvent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	void initComponent() {
		this.setLayout(new GridBagLayout());
		this.add(jScrollPane_Tree, 
				new GridBagConstraints(0, 0, 1, 1,
						1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));		
		root 	= new DefaultMutableTreeNode("주소록");
		parent 	= new DefaultMutableTreeNode("신상정보");
		root.add(parent);
		jtree = new JTree(root);
		jScrollPane_Tree.getViewport().add(jtree);
		
		
		this.add(jPanel_InputField,
				new GridBagConstraints(0, 10, 6, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0),
						0, 0));
		jPanel_InputField.add(jLabel_Num,
				new GridBagConstraints(0, 0, 1, 1,
						1.0, 0.0,
						GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
						new Insets(0, 5, 5, 5),
						0, 0));
		jPanel_InputField.add(jTextField_Num,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 5, 5, 5),
						0, 0));
		
		jPanel_InputField.add(jLabel_Name,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 5, 5, 5),
						0, 0));
		
		jPanel_InputField.add(jTextField_Name,
				new GridBagConstraints(1, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		jPanel_InputField.add(jLabel_Age,
				new GridBagConstraints(2, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));	
		
		jPanel_InputField.add(jTextField_Age,
				new GridBagConstraints(3, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));	
		
		jPanel_InputField.add(jLabel_Address,
				new GridBagConstraints(4, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));	
		
		jPanel_InputField.add(jTextField_Address,
				new GridBagConstraints(3, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));	
		
		this.add(jPanel_Button,
				new GridBagConstraints(0, 11, 2, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		jPanel_Button.add(jButton_Add,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		jPanel_Button.add(jButton_Delete,
				new GridBagConstraints(0, 0, 1, 1,
						1.0, 0.0,
						GridBagConstraints.EAST,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));
	}

	void initButtonEvent() {
		jButton_Add.addActionListener(this);
		jButton_Delete.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if(((JButton)e.getSource()).getText() == "추가") {
			LOGGER.debug("추가");
			if(!(jTextField_Num.getText().equals(""))
					&& !(jTextField_Name.getText().equals("")) 
					&& !(jTextField_Age.getText().equals("")) 
					&& !(jTextField_Address.getText().equals(""))) {
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(jTextField_Num.getText()+"/"
																		+ jTextField_Name.getText()+"/"
																		+ jTextField_Age.getText()+"/"
																		+ jTextField_Address.getText());
			TreePath selectionPath = jtree.getSelectionPath();
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
			((DefaultTreeModel)jtree.getModel()).insertNodeInto(node,selectedNode, selectedNode.getChildCount());
				
				jTextField_Num.setText("");
				jTextField_Name.setText("");
				jTextField_Age.setText("");
				jTextField_Address.setText("");

			}else {
				JOptionPane.showMessageDialog(this, "정보를 입력해주세요");
			}
		}
		
		
		if (((JButton)e.getSource()).getText() == "삭제") {
			LOGGER.debug("삭제");
			TreePath selectionPath = jtree.getSelectionPath();
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
			
			((DefaultTreeModel)jtree.getModel()).removeNodeFromParent(selectedNode);
			
		}		
		
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
}
