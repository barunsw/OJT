package com.barunsw.ojt.yjkim.day16;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.barunsw.ojt.constants.Severity;

public class AlaramVo implements Serializable{
	private int severity;
	private int idx;
	
	public AlaramVo() {
		idx = (int) (Math.random() * 37);
		severity = (int) (Math.random() * 4);
	}
	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}

	@Override
	public String toString() {
		return this.severity + "/" +this.getIdx();
	}
}
