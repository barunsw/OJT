package com.barunsw.ojt.yjkim.day17;

import java.util.ArrayList;
import java.util.List;

import com.barunsw.ojt.day16.QueueWorker;

public class EventQueueWorker extends QueueWorker {
	private List<EventListener> eventListenerList = 
			new ArrayList<EventListener>();
	
	@Override
	public void processObject(Object o) {
		for (EventListener oneEventListener : eventListenerList) {
			oneEventListener.push(o);
			
			//ClientTestPanel.jTextArea_ToTalMessge.setText(o.toString());
		}
	}

	public void addEventListener(EventListener listener) {
		eventListenerList.add(listener);
	}

	public void removeEventListener(EventListener listener) {
		eventListenerList.remove(listener);
	}
}
