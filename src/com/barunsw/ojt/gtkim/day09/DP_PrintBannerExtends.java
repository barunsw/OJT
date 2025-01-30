package com.barunsw.ojt.gtkim.day09;

public class DP_PrintBannerExtends extends DP_Banner implements DP_PrintExtends{

	public DP_PrintBannerExtends(String string) {
		super(string);
	}
	
	@Override
	public void printWeak() {
		showWithParen();
	}

	@Override
	public void printStrong() {
		showWithAster();
	}

}
