package com.barunsw.ojt.day16;

public class EventVo {
	private EventType type;
	private Object message;

	public EventType getType() {
		return type;
	}
	
	public void setType(EventType type) {
		this.type = type;
	}
	
	public Object getMessage() {
		return message;
	}
	
	public void setMessage(Object message) {
		this.message = message;
	}
}
