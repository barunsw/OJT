package com.barunsw.ojt.yjkim.day04;

public class Gender {
	private static final int _MAN 	= 0;
	private static final int _WOMAN = 1;
	
	public static final Gender MAN 	= new Gender(_MAN);
	public static final Gender WOMAN 	= new Gender(_WOMAN);
	
	private int value;
	private Gender(int value) {
		this.value = value;
	}
}
