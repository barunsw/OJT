package com.barunsw.ojt.phs.day16;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventQueueWorker extends QueueWorker {
	private static final Logger LOGGER = LogManager.getLogger(EventQueueWorker.class);
	
	private List<EventListener> eventListenerList = new ArrayList<>();
	
	@Override
	public void processObject(Object o) {
		LOGGER.debug("EventQueueWorker processObject() !! ");
		for (EventListener oneListener : eventListenerList) {
			oneListener.push(o);
		}
	}
	
	public void addEventListener(EventListener listener) {
		LOGGER.debug("EventQueueWorker addEventListener() !! ");
		eventListenerList.add(listener);
	}
	
	public void removeEventListener(EventListener listener) {
		LOGGER.debug("EventQueueWorker removeEventListener() !! ");
		eventListenerList.remove(listener);
	}
}
