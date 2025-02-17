package com.barunsw.ojt.jyb.day17;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class QueueWorker<T> extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(QueueWorker.class);

	private LinkedList<T> eventLinkedList = new LinkedList<T>(); //이벤트 저장
	private List<T> messageRepository = Collections.synchronizedList(eventLinkedList);

	private Object waitObject = new Object(); //스레드 동기화를 위함

	private boolean runFlag; //스레드 실행 플래그 -> 스레드 종료 여부 결정

	public abstract void processObject(T t); //각 서브클래스에서 처리할 작업을 정의하기 위한 추상 메소드

	public void push(T t) { //메세지를 큐에 추가 + 대기 중인 스레드 깨움
		messageRepository.add(t);
		synchronized (waitObject) {
			waitObject.notify();
		}
	}

	public void run() { //스레드 실행
		T t = null;
		runFlag = true; //스레드가 실행되도록 함

		while (runFlag) {
			synchronized (waitObject) { //대기 중인 객체에 대한 동기화
				if (messageRepository.size() > 0) { //메세지가 존재하면 큐에서 하나 꺼냄
					t = messageRepository.remove(0);
				} else {
					t = null;

					try {
						waitObject.wait(); //메세지가 없으면 대기
					}
					catch (Exception ex) {
						LOGGER.error("+++Thread interrupted while waiting.", ex);
					}
				}
			}

			try {
				if (t != null) {
					try {
						processObject(t);
					}
					catch (Exception ex) {
						LOGGER.debug(ex);
					}
				}
			}
			catch (Exception ex) {
				LOGGER.debug(ex);
			}
		}
	}

	public void close() { //스레드 종료
		runFlag = false;

		synchronized (waitObject) {
			waitObject.notify(); //대기 중인 스레드 깨움
		}
	}

	public int getBufferSize() { //큐의 크기 반환
		return messageRepository.size();
	}
}