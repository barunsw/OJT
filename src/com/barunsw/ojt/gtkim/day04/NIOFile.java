package com.barunsw.ojt.gtkim.day04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NIOFile {
	private static final Logger LOG = LogManager.getLogger(NIOFile.class);

	public static void main(String[] args) {
		LOG.debug("day04 NIO(Files, Path) main");
		String dir = "data/day04/gtkim/";
		// java.nio.file 패키지는 자바 7부터 추가된 기능입니다.
		// 자바의 성능적 문제였던 IO를 향상시키기 위해 추가되었습니다.
		// 입출력 방식에서 채널방식을 도입하였고 비동기 지원과 블로킹/넌블로킹 입출력을 모두 지원합니다
		// 채널 방식의 큰 특징으로는 입력, 출력을 하나만 생성해서 할 수 있습니다.
		// Files는 생성자가 private이고 Path는 인터페이스입니다.

		// Path를 통한 경로정의
		Path path = Paths.get(dir + "newaddress.txt");

		// path to file
		File file = path.toFile();
		LOG.debug("Path to File : " + file.getName());

		// compare
		Path compare = Paths.get("data/day04/gtkim/newaddress.txt");
		if (path.compareTo(compare) == 0) {
			LOG.debug("서로 같은 abstract path 입니다.");
		} else {
			LOG.debug(String.format("서로 같지 않은 abstract path 입니다.[%d] : [%s], [%s]", path.compareTo(compare),
					path.toAbsolutePath(), compare.toAbsolutePath()));
		}

		// equals
		if (path.equals(compare)) {
			LOG.debug("서로 같은 오브젝트입니다.");
		}

		// endsWith : String으로 확인할때는 Path도 String으로 해줘야 된다.
		if (path.toString().endsWith("txt")) {
			LOG.debug(path.getFileName() + "은 txt로 끝납니다.");
		} else {
			LOG.debug(path.getFileName() + "은 txt로 끝나지 않습니다.");
		}
		
		// startsWith
		if (path.toString().startsWith("data")) {
			LOG.debug(path.getParent() + "은 data로 시작합니다.");
		}

		// getFileName, getParent, getRoot -> 상대일땐 null?
		LOG.debug("파일명 : " + path.getFileName());
		LOG.debug("부모 디렉토리 명 : " + path.getParent());

		// resolve 경로를 합칠때 주로 사용함
		Path base = Paths.get(dir);
		Path resolve1 = base.resolve("resolve1.txt");
		LOG.debug("경로 조합 : " + resolve1.toString());

		// 디렉토리 생성
		Path createDir = Paths.get(dir + "today/is/beautiful/day");
		try {
			Files.createDirectories(createDir);
			// directory stream
			DirectoryStream<Path> stream = Files.newDirectoryStream(base, "*.txt");
			stream.forEach(p -> LOG.debug("텍스트파일 : " + p.getFileName()));
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}

		// getNameCount, getName(i), toAbsolutePath
		int count = path.getNameCount();
		for (int i = 0; i < count; i++) {
			LOG.debug(String.format("상대 경로[%d] : %s", i, path.getName(i)));
		}
		LOG.debug("Path의 절대 경로 : " + path.toAbsolutePath());

		// Files로 파일 속성 읽고 쓰기 버퍼라이터, Files 직접 쓰기, 채널이용해서 쓰기
		Path newFile = Paths.get(dir + "propertiesAddress.txt");
		try (BufferedWriter bw = Files.newBufferedWriter(newFile, StandardCharsets.UTF_8);
				FileChannel channel = FileChannel.open(Paths.get(dir + "channelWrite.txt"), StandardOpenOption.CREATE,
						StandardOpenOption.WRITE, StandardOpenOption.READ)) {
			StringBuffer sb = new StringBuffer();
			if (!Files.exists(newFile)) {
				LOG.debug("파일을 생성합니다.");
				Files.createFile(newFile);
			}

			if (Files.isDirectory(path)) {
				LOG.debug("디렉토리 입니다.");
				sb.append("디렉토리 입니다.\n");
			}

			if (Files.isRegularFile(path)) {
				LOG.debug("파일입니다.");
				sb.append("파일입니다.\n");
			}

			// probeConteneType
			LOG.debug("파일의 타입은? : " + Files.probeContentType(path));

			// 파일 정보 읽기
			LOG.debug("파일 크기 : " + Files.size(path));
			LOG.debug("소유자 : " + Files.getOwner(path) + "Bytes");
			LOG.debug(String.format("파일권한 실행여부 : %s, 쓰기여부 : %s, 읽기여부 : %s, 숨김파일여부 : %s", Files.isExecutable(path),
					Files.isWritable(path), Files.isReadable(path), Files.isHidden(path)));
			LOG.debug("마지막 수정 날짜 : " + Files.getLastModifiedTime(path));
			sb.append("파일크기 : ").append(Files.size(path)).append("Bytes").append("\n소유자 : ")
					.append(Files.getOwner(path)).append("파일권한 Excute : ").append(Files.isExecutable(path))
					.append(" Write : ").append(Files.isWritable(path)).append(" Read : ")
					.append(Files.isReadable(path)).append("\n마지막 수정 날짜 : ").append(Files.getLastModifiedTime(path));
			bw.write(sb.toString());
			bw.newLine();
			LOG.debug("버퍼라이터를 이용한 파일 출력 수행");

			// Files를 통해 바로 파일에 입력
			byte[] buf = new String(sb).getBytes();
			Path directWrite = Paths.get(dir + "directWrite.txt");
			Files.write(directWrite, buf);
			LOG.debug("Files 메소드를 이용한 파일 출력 수행");
			
			// 채널을 통한 출력 바이트 버퍼를 사용한다.
			ByteBuffer byteBuffer = Charset.defaultCharset().encode(sb.toString());
			channel.write(byteBuffer);
			LOG.debug("채널을 통한 파일 출력 수행");
		} catch (FileNotFoundException fnfe) {
			LOG.error(fnfe.getMessage(), fnfe);
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe);
		}

		// input방법 기존에 사용하던 버퍼리더, Files를 통해 바로 읽기, 채널을 통해 읽기
		try (FileChannel channel = FileChannel.open(Paths.get(dir + "FileCopyMain.txt"));
				BufferedReader br = Files.newBufferedReader(Paths.get(dir + "FileCopyMain.txt"),
						StandardCharsets.UTF_8)) {
			ByteBuffer readBuf = ByteBuffer.allocate((int) Files.size(Paths.get(dir + "FileCopyMain.txt")));
			// 버퍼로 읽기
			char[] cbuf = new char[512];
			br.read(cbuf);
			LOG.debug("버퍼리더로 읽은 데이터 : " + new String(cbuf));

			// Files로 바로 읽기
			List<String> readLines = Files.readAllLines(Paths.get(dir + "FileCopyMain.txt"));
			for (String s : readLines) {
				LOG.debug("Files input(String) : " + s);
			}
			byte[] bbuf = new byte[512];
			bbuf = Files.readAllBytes(path);
			LOG.debug("Files input(Byte) : " + new String(bbuf));

			// 채널로 읽기
			int size = channel.read(readBuf);
			readBuf.flip();
			LOG.debug(String.format("채널 입력 [%d]Bytes : %s", size, Charset.defaultCharset().decode(readBuf).toString()));

		} catch (FileNotFoundException fnfe) {
			LOG.error(fnfe.getMessage(), fnfe);
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe);
		}

		try {
			// 파일 복사
			Path copySource = Paths.get("data/day04/gtkim/FileCopyMain.txt");
			Path copyTarget = Paths.get("data/day04/gtkim/today/FileCopyMain.txt");
			Files.copy(copySource, copyTarget, StandardCopyOption.REPLACE_EXISTING);
			LOG.debug("Files.copy 수행");

			// 파일 삭제
			if (Files.exists(copyTarget)) {
				Files.delete(copyTarget);
				LOG.debug("파일이 삭제 되었습니다.");
			}

		} catch (FileNotFoundException fnfe) {
			LOG.error(fnfe.getMessage(), fnfe);
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe);
		}

		// 채널 입출력을 통한 복사
		FileChannel fcSend = null;
		FileChannel fcRecv = null;
		try {
			fcSend = FileChannel.open(path, StandardOpenOption.READ);
			fcRecv = FileChannel.open(Paths.get(dir + "/today/copy.txt"), StandardOpenOption.WRITE);

			fcSend.transferTo(0, Files.size(path), fcRecv);
			LOG.debug("채널복사가 수행되었습니다.");
		} catch (FileNotFoundException fnfe) {
			LOG.error(fnfe.getMessage(), fnfe);
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe);
		} finally {
			if (fcSend != null) {
				try {
					fcSend.close();
				} catch (Exception e) {
				}
			}
			if (fcRecv != null) {
				try {
					fcRecv.close();
				} catch (Exception e) {
				}
			}
		}
	}
}