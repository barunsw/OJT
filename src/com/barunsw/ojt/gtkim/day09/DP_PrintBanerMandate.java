package com.barunsw.ojt.gtkim.day09;

public class DP_PrintBanerMandate extends DP_PrintMandate{
	private DP_Banner banner;

	public DP_PrintBanerMandate(String string) {
		this.banner = new DP_Banner(string);
	}

	@Override
	public void printWeak() {
		banner.showWithParen();		
	}

	@Override
	public void printStrong() {
		banner.showWithAster();
	}
	
}
