package com.barunsw.ojt.gtkim.day16;

import java.io.Serializable;

import com.barunsw.ojt.constants.Severity;

public class AlarmVo implements Serializable {
	private int boardId;
	private int severity;

	public AlarmVo() {
		this.boardId = (int) (Math.random() * 37);
		this.severity = ((int) (Math.random() * 4));
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

}
