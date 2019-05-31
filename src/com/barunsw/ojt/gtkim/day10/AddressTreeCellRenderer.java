package com.barunsw.ojt.gtkim.day10;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class AddressTreeCellRenderer extends DefaultTreeCellRenderer {

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {

		Component comp = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Object obj = node.getUserObject();
		
		if(selected && !hasFocus) {
	        setBackgroundSelectionColor(UIManager.getColor("Panel.background"));
	        setTextSelectionColor(UIManager.getColor("Panel.foreground"));
	    } 
		else {
	        setTextSelectionColor(UIManager.getColor("Tree.selectionForeground"));
	        setBackgroundSelectionColor(UIManager.getColor("Tree.selectionBackground"));
	    } 

		
		return this;
	}

}
