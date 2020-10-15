package com.barunsw.ojt.phs.day16.rmi;

import java.util.ArrayList;
import java.util.List;

import com.barunsw.ojt.day16.QueueWorker;

public class EventQueueWorker extends QueueWorker {
	private List<EventListener> eventListenerList = new ArrayList<EventListener>();
	
	@Override
	public void processObject(Object o) {
		for (EventListener oneEventListener : eventListenerList) {
			oneEventListener.push(o);
		}
	}

	public void addEventListener(EventListener listener) {
		eventListenerList.add(listener);
	}

	public void removeEventListener(EventListener listener) {
		eventListenerList.remove(listener);
	}
}
