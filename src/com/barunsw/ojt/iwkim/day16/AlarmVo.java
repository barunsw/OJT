package com.barunsw.ojt.iwkim.day16;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.barunsw.ojt.constants.BoardType;

public class AlarmVo implements Serializable {
	private Date collectTime;
	private BoardType boardType;
	private int severity;
	private int boardId;
	
	public AlarmVo() {}
	public AlarmVo(BoardType boardType, int boardId, int severity) {
		this.boardType = boardType;
		this.boardId = boardId;
		this.severity = severity;
		this.collectTime = new Date();
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public BoardType getBoardType() {
		return boardType;
	}
	public void setBoardType(BoardType boardType) {
		this.boardType = boardType;
	}
	public int getSeverity() {
		return severity;
	}
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
}
