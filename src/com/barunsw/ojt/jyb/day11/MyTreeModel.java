package com.barunsw.ojt.jyb.day11;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

class MyTreeModel implements TreeModel {
	private MyTreeNode root;
	private List<TreeModelListener> listeners = new ArrayList<>();

	public MyTreeModel(MyTreeNode root) {
		this.root = root;
	}

	@Override
	public Object getRoot() { // 루트 노드 반환
		return root;
	}

	@Override
	public Object getChild(Object parent, int index) { // 부모 노드의 자식 반환
		if (parent instanceof MyTreeNode) { // 부모가 MyTreeNode 인스턴스인지 확인
			return ((MyTreeNode) parent).getChildren().get(index);
		}
		return null;
	}

	@Override
	public int getChildCount(Object parent) { // 부모 노드의 자식 수 반환
		if (parent instanceof MyTreeNode) {
			return ((MyTreeNode) parent).getChildren().size();
		}
		return 0;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) { // 부모 노드의 자식의 인덱스를 반환
		if (parent instanceof MyTreeNode && child instanceof MyTreeNode) {
			return ((MyTreeNode) parent).getChildren().indexOf(child);
		}
		return -1;
	}

	@Override
	public boolean isLeaf(Object node) { // 주어진 노드가 리프 노드인지 확인
		if (node instanceof MyTreeNode) {
			return ((MyTreeNode) node).getChildren().isEmpty(); // 자식 리스트가 비어있으면 true를 반환
		}
		return true;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) { // 리스너 추가
		listeners.add(l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) { // 리스너 제거
		listeners.remove(l);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) { // 노드의 값이 변경되었을 때 호출되는 메소드
		System.out.println("Value changed for path: " + path + " to new value: " + newValue);
	}

	public MyTreeNode searchNode(String value) {
		return searchNodeRecursive(root, value);
	}

	private MyTreeNode searchNodeRecursive(MyTreeNode node, String value) {
		if (node.getValue().equals(value)) {
			return node; // 값이 일치하면 노드 반환
		}
		for (MyTreeNode child : node.getChildren()) {
			MyTreeNode result = searchNodeRecursive(child, value);
			if (result != null) {
				return result; // 자식 노드에서 일치하는 노드를 찾으면 반환
			}
		}
		return null; // 일치하는 노드가 없으면 null 반환
	}

	@Override
	public String toString() {
		return root.toString();
	}
}