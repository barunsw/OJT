package com.barunsw.ojt.cjs.common;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.barunsw.ojt.constants.BoardType;

public class BoardVo implements Serializable {
	private BoardType boardType;
	private String boardName;
	private int boardId;
	private int severity;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);

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
	public String getEventTime() {
		String currentTime = sdf.format(Calendar.getInstance().getTime());
		return currentTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
