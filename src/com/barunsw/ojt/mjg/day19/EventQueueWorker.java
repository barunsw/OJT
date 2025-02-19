package com.barunsw.ojt.mjg.day19;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// EventQueueWorker는 QueueWorker를 상속받아, 큐에서 꺼낸 이벤트를
// 등록된 EventListener들에게 전달(push)해주는 역할을 한다.
public class EventQueueWorker<T> extends QueueWorker<T> {
	private static final Logger LOGGER = LogManager.getLogger(EventQueueWorker.class);
	
	private List<EventListener> eventListenerList = new ArrayList<EventListener>();

	// QueueWorker로부터 상속받은 processObject 메서드를 구현한다.
	// 큐에서 꺼낸 데이터를 등록된 모든 리스너에게 전달한다.
	@Override
	public void processObject(T t) {
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
