package com.barunsw.ojt.jyb.day11;

import java.util.ArrayList;
import java.util.List;

public class MyTreeNode {
	private String value; // 노드의 값을 저장할 변수
	private List<MyTreeNode> children;

	// String 매개변수를 받는 생성자 추가
	public MyTreeNode(String value) {
		this.value = value;
		this.children = new ArrayList<>(); // children 초기화
	}

	public List<MyTreeNode> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>(); // null인 경우 빈 리스트 초기화
		}
		return this.children;
	}

	public void addChild(MyTreeNode child) {
		if (this.children == null) {
			this.children = new ArrayList<>(); // null인 경우 초기화
		}
		this.children.add(child);
	}

	// 추가: 노드의 값을 반환하는 메서드
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(value); // 현재 노드의 값 추가
		if (!children.isEmpty()) {
			sb.append(" -> ["); // 자식 노드가 있을 경우
			for (int i = 0; i < children.size(); i++) {
				sb.append(children.get(i).toString()); // 자식 노드의 toString 호출
				if (i < children.size() - 1) {
					sb.append(", "); // 마지막 자식 노드가 아닐 경우 쉼표 추가
				}
			}
			sb.append("]"); // 자식 노드 리스트 종료
		}
		return sb.toString();
	}
}