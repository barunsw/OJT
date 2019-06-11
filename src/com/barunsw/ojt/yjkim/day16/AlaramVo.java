package com.barunsw.ojt.yjkim.day16;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.barunsw.ojt.constants.Severity;

public class AlaramVo {
	private int severity;
	private int idx;
	
	public AlaramVo() {
		double randomServerity = Math.random();
		int randomValue = (int) (randomServerity * 37);
		idx = randomValue;
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
