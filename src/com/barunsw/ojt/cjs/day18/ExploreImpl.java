package com.barunsw.ojt.cjs.day18;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExploreImpl implements ExploreInterface {
	private File rootFile = new File("/");
	File[] fileList = rootFile.listFiles();

	@Override
	public List<FileVo> selectAddressList() {
		return null;
	}

}
