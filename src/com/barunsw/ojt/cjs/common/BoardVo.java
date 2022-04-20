package com.barunsw.ojt.cjs.common;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.barunsw.ojt.constants.BoardType;

public class BoardVo implements Serializable {
	private BoardType boardType;
	private String boardName;
	private int boardId;
	private int severity;
	
	public BoardVo() {
	}
	
	public BoardVo(int boardId, int severity) {
		this.boardId = boardId;
		this.severity = severity;
	}

	public BoardType getBoardType() {
		return boardType;
	}
	
	public void setBoardType(BoardType boardType) {
		this.boardType = boardType;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	
	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
