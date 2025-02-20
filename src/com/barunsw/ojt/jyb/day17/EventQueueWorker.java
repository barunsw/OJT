package com.barunsw.ojt.jyb.day17;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventQueueWorker<T> extends QueueWorker<T> {
	private static final Logger LOGGER = LogManager.getLogger(EventQueueWorker.class);

	private List<EventListener> eventListenerList = new ArrayList<EventListener>();

	@Override
	public void processObject(T t) { // 등록된 모든 이벤트 리스너에게 객체 전달
		for (EventListener oneEventListener : eventListenerList) {
			oneEventListener.push(t);
		}
	}

	public void addEventListener(EventListener listener) { // 이벤트 리스너를 리스트에 추가
		eventListenerList.add(listener);
	}

	public void removeEventListener(EventListener listener) { // 이벤트 리스너를 리스트에서 제거
		eventListenerList.remove(listener);
	}
}
