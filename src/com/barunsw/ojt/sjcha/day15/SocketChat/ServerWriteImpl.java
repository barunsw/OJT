package com.barunsw.ojt.sjcha.day15.SocketChat;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

// 서버에서 클라이언트로 wirte하는 곳.
public class ServerWriteImpl implements ClientInterface {
	private Socket socket;

	public ServerWriteImpl(Socket socket) {
		this.socket = socket;
	}

	@Override
	public int push(String message) throws Exception {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		writer.write(message);
		writer.flush();

		return 0;
	}
}