package com.barunsw.ojt.jyb.day19;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.text.SimpleDateFormat;

class FileTableModel extends AbstractTableModel {
	private File[] files;
	private String[] columnNames = { "이름", "수정한 날짜", "유형", "크기" };

	public FileTableModel(File directory) {
		if (directory.isDirectory() && directory.listFiles() != null) {
			this.files = directory.listFiles();
		} else {
			this.files = new File[0];
		}
	}

	@Override
	public int getRowCount() {
		return files.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		File file = files[rowIndex];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		switch (columnIndex) {
		case 0:
			return file.getName(); // 파일/폴더 이름
		case 1:
			return sdf.format(file.lastModified()); // 수정한 날짜
		case 2:
			return file.isDirectory() ? "폴더" : "파일"; // 파일/폴더 유형
		case 3:
			return formatSize(file.length()); // 파일 크기
		default:
			return null;
		}
	}

	// 파일 크기 포맷 (바이트를 KB, MB, GB 등으로 변환하여 표시)
	private String formatSize(long size) {
		if (size <= 0)
			return "0 B";
		final long KB = 1024;
		final long MB = KB * 1024;
		final long GB = MB * 1024;

		if (size < KB) {
			return size + " B";
		} else if (size < MB) {
			return String.format("%.1f KB", size / (float) KB);
		} else if (size < GB) {
			return String.format("%.1f MB", size / (float) MB);
		} else {
			return String.format("%.1f GB", size / (float) GB);
		}
	}
}
