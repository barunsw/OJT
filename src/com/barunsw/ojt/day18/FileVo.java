package com.barunsw.ojt.day18;

public class FileVo {
	private String name;
	private FileType type;
	private String path;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public FileType getType() {
		return type;
	}
	
	public void setType(FileType type) {
		this.type = type;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		return name;
	}
}