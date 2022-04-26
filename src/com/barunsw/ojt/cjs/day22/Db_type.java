package com.barunsw.ojt.cjs.day22;

import com.barunsw.ojt.constants.Gender;

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

	public static Db_type toDb_type(String value) throws Exception {
		switch (value) {
		case "Maria":
			return MARIA;
		case "MARIA":
			return MARIA;
		case "maria":
			return MARIA;
		case "postgres":
			return POSTGRES;
		case "POSTGRES":
			return POSTGRES;
		case "Postgres":
			return POSTGRES;
		default:
			throw new Exception(String.format("적절하지 않은 value(%s)입니다.", value));
		}
	}
}
