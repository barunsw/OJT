package com.barunsw.ojt.cjs.day17;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventQueueWorker extends QueueWorker{

	private static final Logger LOGGER = LoggerFactory.getLogger(EventQueueWorker.class);
	private List<EventListener> eventListnerList= new ArrayList<EventListener>();

	@Override
	public void processObject(Object o) {
		for (EventListener oneEventListener : eventListnerList) {
			oneEventListener.push(o);
		}
	}

	public void addEventListener(EventListener listener) {
		LOGGER.debug("addEventListener 동작 ");
		eventListnerList.add(listener);
	}

	public void removeEventListener(EventListener listener) {
		eventListnerList.remove(listener);
	}

}
