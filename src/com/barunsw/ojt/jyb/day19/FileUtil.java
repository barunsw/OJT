package com.barunsw.ojt.jyb.day19;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

public class FileUtil {

	// 파일 크기 포맷 (바이트 단위 그대로 반환)
	public static long formatSize(long size) {
		return size;
	}

	// 루트 디렉토리만 추가 (초기에는 하위 폴더 X)
	public static void createRootFileTree(DefaultMutableTreeNode parentNode, File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (File f : files) {
					// 자식 폴더만 추가 (파일 제외)
					if (f.isDirectory()) {
						DefaultMutableTreeNode node = new DefaultMutableTreeNode(f);
						// 미리 더미 노드 추가 → 확장 시 동적 로드 가능하도록 설정
						node.add(new DefaultMutableTreeNode("Loading..."));
						parentNode.add(node);
					}
				}
			}
		}
	}

	// 클릭(확장)할 때 하위 디렉토리 로드
	public static void loadChildNodes(DefaultMutableTreeNode parentNode) {
		// 첫 번째 자식이 "Loading..."이면 실제 데이터로 변경
		if (parentNode.getChildCount() == 1 && parentNode.getChildAt(0).toString().equals("Loading...")) {
			parentNode.removeAllChildren(); // 기존 "Loading..." 삭제
			File file = (File) parentNode.getUserObject();

			File[] files = file.listFiles();
			if (files != null) {
				for (File f : files) {
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(f);
					parentNode.add(node);
					// 하위 디렉토리가 있다면 "Loading..." 노드 추가
					if (f.isDirectory()) {
						node.add(new DefaultMutableTreeNode("Loading..."));
					}
				}
			}
		}
	}

	// 파일이 디렉토리인지 파일인지 확인
	public static String getFileType(File file) {
		return file.isDirectory() ? "폴더" : "파일";
	}
}
