package com.barunsw.ojt.jyb.day12;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerMain { // 클라이언트의 연결을 수락하고 각 연결을 처리
	private static final Logger LOGGER = LogManager.getLogger(ServerMain.class);

	public static final int PORT = 5000; // 서버가 클라이언트의 연결을 기다릴 포트 번호 정의. 클라이언트는 이 포트 번호로 서버에 연결

	private boolean runFlag; // 서버가 계속 실행될지 여부를 나타내는 플래그

	public void start() { // 서버의 실행을 시작하는 메소드. 서버 소켓을 열고 클라이언트 연결을 수락하는 무한 루프를 돌고 있음
		LOGGER.debug(String.format("ServerMain started."));

		runFlag = true; // 서버는 계속 클라이언트의 연결 수락

		try (ServerSocket serverSocket = new ServerSocket(PORT)) { // 서버 소켓 생성
			serverSocket.setReuseAddress(true);

			while (runFlag) {
				Socket socket = serverSocket.accept(); // 클라이언트의 연결 기다림

				LOGGER.debug(String.format("client connected(%s)", socket.getRemoteSocketAddress()));

				ClientSocketHandler handler = new ClientSocketHandler(socket);
				handler.start();
			}
		}
		catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		new ServerMain().start();
	}
}
