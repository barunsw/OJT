package com.barunsw.ojt.jyb.day12;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientTest {
	private static final Logger LOGGER = LogManager.getLogger(ClientTest.class);

	// 소켓을 이용한 클라이언트-서버 통신 테스트 코드
	// 클라이언트가 서버에 데이터를 보내고, 서버로부터 받은 응답을 출력
	public static void main(String[] args) throws Exception {
		try (Socket socket = new Socket("localhost", ServerTest.PORT)) {
			// 위의 과정과 같다.
			// Socket socket2 = new Socket();
			// socket2.connect(new InetSocketAddress("localhost", ServerTest.PORT));

			try (OutputStream outputStream = socket.getOutputStream(); // 서버로 데이터를 보내는 스트림 반환
					InputStream inputStream = socket.getInputStream()) { // 서버로부터 데이터를 읽는 스트림 반환
				outputStream.write("Hello World".getBytes()); // 문자열을 바이트 배열로 변환하여 서버로 전송
				// outputStream.flush(); //데이터가 아직 전송되지 않았다면, 즉시 데이터를 서버로 보내도록 강제

				byte[] buf = new byte[1024];
				int readNum = inputStream.read(buf);

				LOGGER.debug("read:" + new String(buf, 0, readNum));
			}
		}
	}
}
