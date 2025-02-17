package com.barunsw.ojt.jyb.day17;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.mjg.day17.ChatFrame;

public class ClientMain {

	public static EventQueueWorker<String> eventQueueWorker = new EventQueueWorker();

	public static void main(String[] args) {

		eventQueueWorker.start(); //이벤트를 처리하는 큐 작업을 병렬로 수행하기 위해 main에서 호출
		//start 메소드를 호출하여 스레드를 시작하면 메인 스레드와 독립적으로 이벤트 큐에서 처리할 작업을 처리하게 됨

		ChatFrame frame = new ChatFrame();

		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();

		int xPos = (scrDim.width - frame.WIDTH) / 2;
		int yPos = (scrDim.height - frame.HEIGHT) / 2;

		frame.setBounds(new Rectangle(xPos, yPos, frame.WIDTH, frame.HEIGHT));

		frame.setVisible(true);
	}
}
