package com.barunsw.ojt.mjg.day19;

import java.sql.Connection;

public class ConnectVo {
	private String dbUrl;
	private String dbName;
	private String dbUser;
	private String dbPass;
	private Connection conn;
	
	public ConnectVo(String dbUrl, String dbName) {
		initConnect();
	}
	
	private void initConnect() {
		
	}
	
	@Override
	public String toString() {
		return "";
	}
}
