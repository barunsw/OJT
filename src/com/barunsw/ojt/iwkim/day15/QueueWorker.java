package com.barunsw.ojt.iwkim.day15;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class QueueWorker<T> extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(QueueWorker.class);

	private LinkedList<T> eventLinkedList = new LinkedList<T>();	
	private List<T> messageRepository = Collections.synchronizedList(eventLinkedList);
	
	private Object waitObject = new Object();
	
	private boolean runFlag;
	
	public abstract void processObject(T t);
	
	// 서버에서 push한 데이터가 들어오면 notify메서드를 호출하여 
	// wait하고 있던 쓰레드를 깨운다!
	public void push(T t) {
		LOGGER.debug("push : " + t);
		messageRepository.add(t);
		synchronized (waitObject) {
			waitObject.notify();
		}
	}
	
	@Override
	public void run() {
		T t = null;
		
		runFlag = true;
		
		while (runFlag) {
			synchronized (waitObject) {
				if (messageRepository.size() > 0) {
					t = messageRepository.remove(0);
				}
				else {
					t = null;
					try {
						waitObject.wait();
					}
					catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);				}
				}
				
				try { 
					if (t != null) {
						try {
							processObject(t);
						}
						catch (Exception ex) { 
							LOGGER.error(ex.getMessage(), ex);
						}
					}
				}
				catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			}
		}
	}

	public void close() {
		runFlag = false;
		
		synchronized (waitObject) {
			waitObject.notify();
		}
	}
	
	public int getBufferSize() {
		return messageRepository.size();
	}
}
