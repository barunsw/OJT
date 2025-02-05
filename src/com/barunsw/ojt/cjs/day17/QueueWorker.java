package com.barunsw.ojt.cjs.day17;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class QueueWorker<T> extends Thread {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueueWorker.class);

	private LinkedList<T> eventLinkedList = new LinkedList<T>();
	private List<T> messageRepository = Collections.synchronizedList(eventLinkedList);
	// ArrayList와 Vector의 장점을 모아서 구현, vector의 싱크와 arraylist의 성능을 합침
	private Object waitObject = new Object();

	private boolean runFlag;
	public abstract void processObject(T t);

	public void push(T t) {
		messageRepository.add(t); //메시지가 입력되면 리스트에 추가
		synchronized (waitObject) {
			waitObject.notify(); //기다리고 있던 객체깨워줌
		}
	}

	public void run() {
		T t = null;
		runFlag = true;
		
		while (runFlag) {
			synchronized (waitObject) {
				if (messageRepository.size() > 0) { //메시지가 한개 이상이면
					t = messageRepository.remove(0); //지워주면서
				} 
				else { //메시지가 없으면
					t = null;

					try {
						waitObject.wait(); //무한 대기
					} 
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			try {
				if (t != null) {
					try {
						processObject(t); 
					} 
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
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
