package com.barunsw.ojt.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AlarmVo implements Serializable {
	private String alarmId;
	private int severity;
	private String alarmMsg;
	private String alarmLoc;
	private String eventTime;
	
	public String getAlarmId() {
		return alarmId;
	}
	
	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	public String getAlarmMsg() {
		return alarmMsg;
	}

	public void setAlarmMsg(String alarmMsg) {
		this.alarmMsg = alarmMsg;
	}

	public String getAlarmLoc() {
		return alarmLoc;
	}

	public void setAlarmLoc(String alarmLoc) {
		this.alarmLoc = alarmLoc;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
