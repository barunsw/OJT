package com.barunsw.ojt.cjs.day22;

public class ConnectVo {
	private int seq_num;
	private Db_type db_type;
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	private String dbName;
	public ConnectVo() {
		
	}
	public ConnectVo(Db_type db_type, String dbUrl, String dbUser, String dbPassword, String dbName) {
		this.db_type = db_type;
		this.dbUrl = dbUrl;
		this.dbUser = dbUser;
		this.dbName = dbName;
		this.dbPassword = dbPassword;
	}

	public int getSeq_num() {
		return seq_num;
	}

	public void setSeq_num(int seq_num) {
		this.seq_num = seq_num;
	}

	public Db_type getDb_type() {
		return db_type;
	}

	public void setDb_type(Db_type db_type) {
		this.db_type = db_type;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

}
