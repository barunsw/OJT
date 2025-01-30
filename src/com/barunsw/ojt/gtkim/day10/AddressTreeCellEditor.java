package com.barunsw.ojt.gtkim.day10;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

public class AddressTreeCellEditor extends DefaultTreeCellEditor {

    public AddressTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
        super(tree, renderer);
    }

    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value,
            boolean isSelected, boolean expanded, boolean leaf, int row) {
        if (value instanceof DefaultMutableTreeNode ) {
            value = ((DefaultMutableTreeNode ) value).toString();
        }
        return super.getTreeCellEditorComponent(tree, value, isSelected, expanded,
                leaf, row);
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return super.isCellEditable(e)
            && ((TreeNode) lastPath.getLastPathComponent()).isLeaf();
    }
}
