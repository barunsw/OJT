package com.barunsw.ojt.jyb.day19;

import com.barunsw.ojt.day08.CommonTableModel;

import java.io.File;
import java.util.Vector;

class FileTableModel extends CommonTableModel {
	private static final String[] COLUMN_NAMES = { "이름", "수정한 날짜", "유형", "크기" };

	public FileTableModel(File directory) {
		Vector<String> columnInfo = new Vector<>();
		for (String columnName : COLUMN_NAMES) {
			columnInfo.add(columnName);
		}
		setColumn(columnInfo);

		if (directory.isDirectory() && directory.listFiles() != null) {
			Vector<Vector<Object>> dataInfo = new Vector<>();
			for (File file : directory.listFiles()) {
				Vector<Object> row = new Vector<>();
				row.add(file.getName()); 
				row.add(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified())); 
				row.add(file.isDirectory() ? "폴더" : "파일"); 
				row.add(file.length());
				dataInfo.add(row);
			}
			setData(dataInfo);
		} else {
			setData(new Vector<>());
		}
	}
}
