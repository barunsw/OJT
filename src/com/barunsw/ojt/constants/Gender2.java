package com.barunsw.ojt.constants;

public class Gender2 {
	private static final int _MAN 	= 0;
	private static final int _WOMAN = 1;
	
	public static final Gender2 MAN 	= new Gender2(_MAN);
	public static final Gender2 WOMAN 	= new Gender2(_WOMAN);
	
	private int value;
	private Gender2(int value) {
		this.value = value;
	}
}
