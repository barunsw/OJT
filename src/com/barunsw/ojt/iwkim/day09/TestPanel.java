package com.barunsw.ojt.iwkim.day09;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel extends JPanel {
	private static Logger LOGGER = LogManager.getLogger(TestPanel.class);
	
	private JTable jTable_Result = new JTable();
	private CommonTableModel tableModel = new CommonTableModel();
	//private DefaultTableModel tableModel = new DefaultTableModel();
	
	private JTree jTree_Result = new JTree();
	private DefaultTreeModel treeModel;
	
	private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("ROOT");
	
	private JPopupMenu jPopupMenu = new JPopupMenu();
	
	private JScrollPane jScrollPane_Result 	= new JScrollPane();
	private JScrollPane jScrollPane_Tree	= new JScrollPane(); 	
	
	private GridBagLayout gridBagLayout = new GridBagLayout();
			
	public TestPanel() {
		try {
			initComponent();
			initTable();
			initTree();
			initPopupMenu();
			
			initTableData();
			initTreeData();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		this.setLayout(gridBagLayout);

		jScrollPane_Tree.setMinimumSize(new Dimension(200, 100));
		jScrollPane_Tree.setPreferredSize(new Dimension(200, 100));
		
		this.add(jScrollPane_Tree, new GridBagConstraints(
				0, 0, 1, 1,
				0.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5),
				0, 0));

		this.add(jScrollPane_Result, new GridBagConstraints(
				1, 0, 1, 1,
				1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 0, 5, 5),
				0, 0));
		
		jScrollPane_Tree.getViewport().add(jTree_Result);
		jScrollPane_Result.getViewport().add(jTable_Result);
	}
	
	private void initTable() throws Exception {
		Vector<String> columnData = new Vector<>();
		columnData.add("이름");
		columnData.add("나이");
		columnData.add("성별");
		
		//tableModel.setColumnIdentifiers(columnData);
		
		tableModel.setColumnData(columnData);
		
		jTable_Result.setModel(tableModel);
		
		jTable_Result.setRowHeight(22);
	}
	
	private void initTree() throws Exception {
		treeModel = new DefaultTreeModel(rootNode);
		
		jTree_Result.setModel(treeModel);
		jTree_Result.setRootVisible(false);
	}
	
	private void initPopupMenu() {
		JMenuItem jMenuItem_Delete = new JMenuItem("삭제");
		
		jMenuItem_Delete.addActionListener(null);
		
		jPopupMenu.add(jMenuItem_Delete);
	}
	
	private void initTableData() {
		Vector<Vector> data = new Vector<>();
		
		Vector data1 = new Vector();
		data1.add("홍길동");
		data1.add("30");
		data1.add("남");
		
		data.add(data1);
		
		Vector data2 = new Vector();
		data2.add("유관순");
		data2.add("20");
		data2.add("여");
		
		data.add(data2);
		
		tableModel.setData(data);
		// 테이블 모델의 데이터가 변경되었음을 알린다.
		tableModel.fireTableDataChanged();
		
		// 단일 선택
		jTable_Result.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// 마우스 이벤트
		jTable_Result.addMouseListener(new TestPanel_jTable_Result_MouseListener(this));
	}
	
	private void initTreeData() {
		DefaultMutableTreeNode data1 = new DefaultMutableTreeNode();
		data1.setUserObject("ㄱ");
		
		rootNode.add(data1);

		DefaultMutableTreeNode data2 = new DefaultMutableTreeNode();
		data2.setUserObject("ㄴ");
		
		rootNode.add(data2);
		
		// 트리 모델의 데이터가 변경되었음을 알린다.
		treeModel.reload();		
	}
	
	void jTable_Result_mouseReleased(MouseEvent e) {
		LOGGER.debug("mouseReleased:" + e.getModifiers());
		// 마우스 우클릭
		if (e.getModifiers() != MouseEvent.BUTTON1_MASK) {
			jPopupMenu.show(jTable_Result, e.getX(), e.getY());
		}
	}
}

class TestPanel_jTable_Result_MouseListener extends MouseAdapter {
	private TestPanel adaptee;
	
	public TestPanel_jTable_Result_MouseListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		adaptee.jTable_Result_mouseReleased(e);
	}
}
