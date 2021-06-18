package com.barunsw.ojt.iwkim.day15;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ResultVo implements Serializable {
	private Result result;
	private String reason;
	
	public Result getResult() {
		return result;
	}
	
	public void setResult(Result result) {
		this.result = result;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
