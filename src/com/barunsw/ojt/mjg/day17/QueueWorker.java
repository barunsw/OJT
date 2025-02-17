package com.barunsw.ojt.mjg.day17;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// <T>: 큐에 담을 데이터 타입
// 추상 클래스 QueueWorker는 내부 큐(LinkedList)에 데이터를 적재(push)하고,
// 데이터를 꺼내서(processObject) 처리하는 작업을 스레드로 동작시키는 역할을 한다.
public abstract class QueueWorker<T> extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(QueueWorker.class);

	// 내부 큐: 동기화 리스트로 감싸서 thread-safe하게 관리
	private LinkedList<T> eventLinkedList = new LinkedList<T>();
	private List<T> messageRepository = Collections.synchronizedList(eventLinkedList);

	// 대기 처리를 위한 lock 객체: 이벤트 변화 없을 때 대기 상태 유지한다.
	private Object waitObject = new Object();

	// 쓰레드 종료 제어용 플래그: 쓰레드 close() 하면 false로 변경
	private boolean runFlag;

	// 큐에서 꺼낸 데이터를 실제로 처리하는 로직은
	// QueueWorker를 상속받는 클래스에서 구현한다.
	public abstract void processObject(T t);


	// 큐에 데이터 넣고 대기 중인 쓰레드에 notify()
    public void push(T t) {
		messageRepository.add(t);
		synchronized (waitObject) {
			waitObject.notify();
		}
    }

	// 쓰레드 시작 -> 무한 루프를 돌면서 큐에 데이터가 있으면 꺼내서
	// processObject 메서드로 처리한다. 큐가 비어있으면 대기한다.
	public void run() {
		T t = null;
		runFlag = true;

		while (runFlag) {
			// 큐에서 데이터 하나 꺼냄
			synchronized (waitObject) {
				if (messageRepository.size() > 0) {
					// 맨 앞 데이터 꺼내기
					// remove = 반환하고 삭제
					t = messageRepository.remove(0);
				} 
				else {
					t = null;
					
					try {
						waitObject.wait();
					}
					catch (Exception ex) {
						LOGGER.error("+++Thread interrupted while waiting.", ex);
					}
				}
			}
			
			// 큐에서 꺼낸 데이터가 있다면 처리(processObject)
            try {
                if (t != null) {
                	try { 
                		processObject(t);
                	} catch(Exception ex) {
                		LOGGER.debug(ex);
                	}
                }
            }
            catch (Exception ex) {
                LOGGER.debug(ex);
            }
		}
	}


	// 쓰레드를 종료 -> runFlag = false -> 대기 상태의 쓰레드 notify
	public void close() {
		runFlag = false;

		synchronized (waitObject) {
			waitObject.notify();
		}
	}

	// 현재 내부 큐에 쌓여있는 데이터 개수를 반환한다.
	public int getBufferSize() {
		return messageRepository.size();
	}
}