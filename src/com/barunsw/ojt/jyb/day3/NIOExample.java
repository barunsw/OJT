package com.barunsw.ojt.jyb.day3;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NIOExample {
	private static final Logger logger = LoggerFactory.getLogger(NIOExample.class);

	public static void main(String[] args) {
		Path path = Paths.get("src/main/resources/example.txt"); // 파일 경로를 정의하여 파일 객체 생성

		// 파일 쓰기
		try {
			String data = "파일에 작성할 데이터";
			Files.write(path, data.getBytes()); // 바이트 배열로 변환하여 파일에 작성
			logger.info("파일에 데이터가 성공적으로 작성됨 : {}", path);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		// 파일 읽기
		try {
			String content = Files.readString(path);
			logger.info("파일 내용 : {}", content);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		// 디렉토리 탐색
		FileSystem fs = FileSystems.getDefault(); // 기본 파일 시스템
		try {
			// 지정된 디렉토리에서 파일 트리 탐색 시작
			Files.walkFileTree(fs.getPath("src/main/resources"), new SimpleFileVisitor<Path>() {
				// walkFileTree : 주어진 경로를 시작으로 파일 시스템의 트리를 탐색하는 메소드
				// fs.getPath : 탐색할 시작 경로, SimpleFileVisitor<Path>는 파일 방문자 객체
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					// walkFileTree 메소드 내부에서 호출
					logger.info("파일 : {}", file.toString()); // 방문한 파일 로그 출력
					return FileVisitResult.CONTINUE; // 탐색을 계속 함
				}

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					logger.info("디렉토리 : {}", dir.toString()); // 방문한 디렉토리 로그 출력
					return FileVisitResult.CONTINUE; // 탐색 계속
				}
			});
		}
		catch (IOException e) {
			logger.error("디렉토리 탐색 중 오류 발생 : {}", e.getMessage());
		}

	}

}
