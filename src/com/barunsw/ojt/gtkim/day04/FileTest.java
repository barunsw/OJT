package com.barunsw.ojt.gtkim.day04;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileTest {
	private final static Logger LOG = LogManager.getLogger(FileTest.class);

	public static void main(String[] args) {
		LOG.debug("day04 File class main");
		String dir = "data/gtkim/day04/";
		// File class는 디스크에 존재하는 파일에 대한 정보를 얻거나
		// 파일을 새로 생성, 제거 기능을 가지는 클래스입니다.
		// 그러나 File 클래스는 실제 파일의 내용을 조작할 수는 없으며 파일에 대한 메타데이터를
		// 다루기 위한 추상화된 클래스라고 볼 수 있습니다 -> file을 close()하지 않음

		// File class의 멤버
		// 1. static final FileSystem
		// 2. final String path
		// 3. static enum PathStatus
		// 4. transient PathStatus status
		// 주어진 문자열 경로를 갖는 파일 객체를 생성 절대경로와 상대경로 지정 가능합니다
		File firstConstructor = new File(dir);
		if (firstConstructor != null) {
			LOG.debug("첫번째 생성자 테스트 ");
		}
		// 미리 생성된 파일 객체를 부모로하는 파일을 두번째 매개변수로 지정하여 새로운 파일 객체를 생성
		File secondConstructor = new File(firstConstructor, dir + "test.txt");
		if (secondConstructor != null) {
			LOG.debug("두번째 생성자 테스트 ");
		}
		// 파일 객체는 실제 해당 경로에 파일이 존재하지 않더라도 신경쓰지 않습니다.
		if (!secondConstructor.exists()) {
			LOG.debug("두번째 생성자 테스트 [해당 파일이 없습니다]");
		}
		// URI를 통해서도 객체 생성이 가능합니다
		try {
			URI uri = new URI("file:///C:/Users/user/git/OJT/data/gtkim/day04");
			File uriConstructor = new File(uri);
			if (uriConstructor != null) {
				LOG.debug("uri 생성자 테스트 ");
			}
		} 
		catch (URISyntaxException urise) {
			LOG.error(urise.getMessage(), urise);
		}

		// 파일 생성
		File firstFile = new File(dir + "firstFile.txt");
		// 디렉토리 생성
		File secondFile = new File(dir);
		try {
			secondFile.mkdirs();
			LOG.debug(
					String.format("%s 디렉토리가 생성되었습니다. 전체 경로 : %s", secondFile.getName(), secondFile.getAbsolutePath()));

			if (!firstFile.exists()) {
				firstFile.createNewFile();
				LOG.debug("파일 생성 성공!");
			}
			// 파일권한 확인
			if (firstFile.canExecute()) {
				LOG.debug("실행 권한이 있는 파일입니다.");
			}

			if (firstFile.canRead()) {
				LOG.debug("읽기 권한이 있는 파일입니다.");
			}

			if (firstFile.canWrite()) {
				LOG.debug("쓰기 권한이 있는 파일입니다.");
			}
			// 파일 권환 변경
			LOG.debug("파일 권한을 변경합니다.");
			firstFile.setWritable(false);

			if (firstFile.canExecute()) {
				LOG.debug("실행 권한이 있는 파일입니다.");
			}

			if (firstFile.canRead()) {
				LOG.debug("읽기 권한이 있는 파일입니다.");
			}

			if (firstFile.canWrite()) {
				LOG.debug("쓰기 권한이 있는 파일입니다.");
			}
			// 파일 삭제
			LOG.debug("파일(디렉토리)을 삭제합니다");
			firstFile.delete();
			if (!firstFile.exists()) {
				LOG.debug("파일이 삭제 되었습니다.");
			}
		} 
		catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe);
		}

		// 디렉토리 삭제
		// 하위 폴더들이 있다면 삭제되지 않기때문에 하위폴더부터 삭제해야함
		File deleteFolder = new File("data/gtkim");
		File[] deleteList = deleteFolder.listFiles();
		for (File f : deleteList) {
			LOG.debug("삭제할 하위 파일(디렉터리): " + f.getName());
			f.delete();
		}
		LOG.debug("하위폴더가 삭제되면 최상위폴더도 삭제 가능 " + deleteFolder.getName());
		deleteFolder.delete();

		File thirdFile = new File("data/day04", "gtkim");
		thirdFile.mkdirs();

		if (thirdFile.isDirectory()) {
			LOG.debug("디렉토리입니다.");
		}
		else {
			LOG.debug("파일 입니다.");
		}
		
		//temp file 생성
		File tempFile = new File("data/day04/gtkim/");
		try {
			tempFile.createTempFile("tempFileTest", "temp", tempFile);
			LOG.debug("temp File 생성");
		}
		catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe);
		}
		
		//파일 출력
		firstFile = new File(thirdFile, "test");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(firstFile))) {
			firstFile.createNewFile();
			LOG.debug(String.format("새로운 파일을 생성하였습니다. %s", firstFile.getPath()));
			bw.write("Hello java.io.File!");
			bw.newLine();
		}
		catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe);
		}
		// 파일 크기 확인
		File checkSize = new File(firstFile, "");
		if (checkSize.exists()) {
			LOG.debug(String.format("파일의 크기는 %d Byte입니다.", checkSize.length()));
		}
		
		// 파일 rename
		File beforeFile = new File("data/day03/gtkim/address.txt");
		File newFile = new File("data/day04/gtkim/newaddress.txt");
		if(beforeFile.exists()) {
			LOG.debug("파일 이름과 위치를 변경합니다" + beforeFile.getPath() + " -> " + newFile.getPath());
			beforeFile.renameTo(newFile);
		}
		else {
			LOG.debug("이동할 파일이 없습니다.");
		}
		
		// 마지막 수정날자 확인
		long last = newFile.lastModified();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS ");
		Date date = new Date(last);
		LOG.debug(String.format("마지막 수정 날짜 : " + sdf.format(date)));

	}
}
