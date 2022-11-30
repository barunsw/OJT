package com.barunsw.ojt.day22;

public enum Db_type {
	MARIA, POSTGRES;

	public String toString() {
		switch (this) {
		case MARIA:
			return "org.mariadb.jdbc.Driver";
		case POSTGRES:
			return "org.postgresql.Driver";
		default:
			return "";
		}
	}
}
