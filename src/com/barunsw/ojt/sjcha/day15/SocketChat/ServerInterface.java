package com.barunsw.ojt.sjcha.day15.SocketChat;

public interface ServerInterface {
	public int register(String userId, ClientInterface clientIf) throws Exception;
	public int deregister(String userId) throws Exception;
	public int send(String userId, String message) throws Exception;
}
