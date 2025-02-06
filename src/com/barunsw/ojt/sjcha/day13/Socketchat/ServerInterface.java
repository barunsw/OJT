package com.barunsw.ojt.sjcha.day13.Socketchat;

public interface ServerInterface {
	public int register(String userId, ClientInterface clientIf) throws Exception;
	public int deregister(String userId) throws Exception;
	public int send(String userId, String message) throws Exception;
}
