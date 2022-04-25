package com.barunsw.ojt.cjs.day18;

public class FileVo {
	private String name;
	private String created;
	private FileType type;
	private String path;

	public FileVo() {
	}
	
	public FileVo(String name, String created, boolean isdir, String path) {
		this.name = name;
		this.setCreated(created);
		if (isdir) {
			this.type = FileType.D;
		}
		else {
			this.type = FileType.F;
		}
		this.path = path;
	}

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
	
	public String toStringFull() {
		return String.format("name=%s, type=%s, path=%s", name, type, path);
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}
}