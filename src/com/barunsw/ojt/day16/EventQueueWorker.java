package com.barunsw.ojt.day16;

import java.util.ArrayList;
import java.util.List;

public class EventQueueWorker<T> extends QueueWorker<T> {
	private List<EventListener> eventListenerList = 
			new ArrayList<EventListener>();
	
	@Override
	public void processObject(T t) {
		// TODO Auto-generated method stub
		for (EventListener oneEventListener : eventListenerList) {
			oneEventListener.push(t);
		}
	}

	public void addEventListener(EventListener listener) {
		eventListenerList.add(listener);
	}

	public void removeEventListener(EventListener listener) {
		eventListenerList.remove(listener);
	}
}
