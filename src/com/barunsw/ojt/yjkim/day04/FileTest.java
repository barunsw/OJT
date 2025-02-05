package com.barunsw.ojt.yjkim.day04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileTest {
	private static final Logger LOGGER = LogManager.getLogger(FileTest.class);
	/*
	 * 신입 사원 OJT day4
	 * 파일 입출력 -2 
	 * 
	 * 김윤제 
	 * */
	public static void main(String[] args) throws IOException {
		
		//java.io.File 관련 메소드 
		File file = new File("data/yjkimday04/address.txt");
		File file2 = new File("data/yjkimday04/barun.txt");
		File file3 = new File("data/yjkimday04/barun2.txt");
		File file4 = new File("data/yjkimday04/");
		File file5= new File("data/yjkim04-2/barun.txt");
		//canExcute() 메소드는 파일을 실행할 수 있는지 여부를 반환한다. 
		LOGGER.debug(String.format("File 메소드 CanExecute(), [%s]", file.canExecute())); 
		
		//canRead() 메소드는 파일을 읽을 수 있는지 여부를 반환한다.
		LOGGER.debug(String.format("File 메소드 canRead(), [%s]", file.canRead())); 

		//canWrite() 메소드는 파일을 쓸 수 있는지 여부를 반환한다.
		LOGGER.debug(String.format("File 메소드 canWrite(), [%s]", file.canWrite())); 
		
		//compareTo() 메소드는 두 개의 파일 이름을 사전 식으로 비교한다.
		//앞의 file이 file2 보다 작기 때문에 음수를 반환한다.
		//같으면 0을 크면 양수를 반환
		LOGGER.debug(String.format("File 메소드 compareTo(), [%s]",file.compareTo(file2))); 
		
		//createNewFile() 메소드는 해당 파일이 아직 존재하지 않는 경우에 한정해, 
		//경로명이 나타내는 이름을 파일로 만든다.
		//file3.createNewFile();
		
		//createTempFile(String prefix,String suffix) 메소드는 지정된 임시 파일 경로에 접두사와 접미어를 사용하여
		//빈 파일을 생성한다.
		//File.createTempFile("File","test");
		
		//createTempFile(String prefix,String suffix,File directory) 메소드는
		//지정된 경로에 새로운 빈 파일을 작성해, 지정된 접두사와 접미사를 사용해 
		//그 이름의 파일을 생성한다.
		//File.createTempFile("File", "test", file3);
	
		//delete() 메소드는 경로가 나타내는 파일 또는 디렉터리를 삭제한다.
		//file2.delete();
		
		//deleteOnExit() 메소드는 가상 머신의 종료시, 이 경로가 나타내는 파일 또는
		//디렉터리가 삭제되도록 요구한다.
		//file.deleteOnExit();
		
		//equals(Object obj) 메소드는 이경로가 지정된 객체와 동일한 지 검사한다.
		LOGGER.debug(String.format("equals() 메소드 [%s] ",file2.equals(file3)));
		
		//exists() 메소드는 파일의 존재 여부를 반환한다.
		LOGGER.debug(String.format("exists() 메소드 [%s] ", file.exists()));
		
		//getAbsoulteFile() 메소드는 파일의 절대 위치를 반환한다.
		LOGGER.debug(String.format("getAbsoluteFile() 메소드 [%s]", file.getAbsoluteFile()));
		
		//getAbsoultePath() 메소드는 파일의 절대 경로 문자열을 반환한다.
		LOGGER.debug(String.format("getAbsolutePath() 메소드 [%s]", file.getAbsolutePath()));
	
		//getCanonicalFile() 메소드는 파일의 Canonical 경로를 반환한다.
		LOGGER.debug(String.format("getCanonicalFile() 메소드 [%s]",file.getCanonicalFile()));
	
		//getFreeSpace() 메소드는 하드디스크의 남은 공간을 반환한다.
		LOGGER.debug(String.format("getFreeSpace() 메소드 [%s]", file.getFreeSpace()));
		
		//getName() 메소드는 경로가 나타내는 파일 또는 경로의 이름을 반환한다.
		LOGGER.debug(String.format("getName() 메소드 [%s]", file.getName()));
		
		//getParent() 메소드는 부모 경로에 대한 경로명을 문자열로 반환한다.
		LOGGER.debug(String.format("getParent() 메소드 [%s]", file.getParent()));
		
		//getParentFile() 메소드는 부모 폴더를 File 형태로 반환한다.
		LOGGER.debug(String.format("getParentFile() 메소드 [%s]", file.getParentFile()));
		
		//getPath() 메소드는 파일의 경로를 문자열의 형태로 반환한다.
		LOGGER.debug(String.format("getPath() 메소드 [%s]", file.getPath()));
		
		//getTotalSpace() 메소드는 하드디스크의 총 용량을 반환한다.
		LOGGER.debug(String.format("getTotalSpace() 메소드 [%s]", file.getTotalSpace()));

		//getUsableSpace() 메소드는 하드디스크의 사용가능한 용량을 반환한다.
		LOGGER.debug(String.format("getUsableSpace() 메소드 [%s]", file.getUsableSpace()));
		
		//hashCode() 메소드는 hashcode를 반환한다.
		LOGGER.debug(String.format("hashCode() 메소드 [%s]", file.hashCode()));
		
		//isAbsolute() 메소드는 해당 경로가 절대 경로인지 여부를 반환한다.
		LOGGER.debug(String.format("isAbsolute() 메소드 [%s]", file.isAbsolute()));

		//isFile() 메소드는 해당 경로가 일반 File인지 여부를 반환한다.
		LOGGER.debug(String.format("isFile() 메소드 [%s]", file.isFile()));

		//isDirectory() 메소드는 해당 경로가 폴더인지 여부를 반환한다.
		LOGGER.debug(String.format("isDirectory() 메소드 [%s]", file.isDirectory()));

		//isHidden() 메소드는 해당 경로가 숨김 File인지 여부를 반환한다.
		LOGGER.debug(String.format("isHidden() 메소드 [%s]", file.isHidden()));

		//lastModified() 메소드는 해당 경로가 마지막으로 수정된 시간을 반환한다.
		LOGGER.debug(String.format("lastModified() 메소드 [%s]", file.lastModified()));

		//length() 메소드는 해당 파일의 길이를 반환한다.
		LOGGER.debug(String.format("length() 메소드 [%s]", file.length()));
		
		//list() 해당 경로의 파일들과 폴더를 문자열 배열로 반환한다.
		LOGGER.debug(String.format("list() 메소드 [%s][%s][%s]", file4.list()[0], file4.list()[1], file4.list()[2]));
		
		//list(FilenameFilter filter) 메소드는 filter에 만족하는 파일들과
		//폴더 이름을 문자열 배열로 반환한다.
		//FilenameFilter 인터페이스를 상속받아야한다.
		JavaFilenameFilter filter = new JavaFilenameFilter();
		
		for(String path : file4.list(filter)) {
			LOGGER.debug(String.format("list(FilenameFIlter filter) () 메소드 [%s]", path));
		}
		
		//listFiles() 메소드는 해당 경로의 파일들과 폴더의 파일을 File 배열로 반환한다.
		for(File path : file4.listFiles()) {
			LOGGER.debug(String.format("listFiles() 메소드 [%s]", path));
		}
	
		//listFiles(FilenameFilter filter) 메소드는 filter에 만족되는
		//파일들과 폴더를 File 배열로 반환한다.
		for(File path : file4.listFiles(filter)) {
			LOGGER.debug(String.format("listFiles(FilenameFilter) 메소드 [%s]", path));
		}
		
		//listRoots() 메소드는 하드디스크의 루트 경로를 반환한다.
		LOGGER.debug(String.format("listRoots() 메소드 [%s]", File.listRoots()[0]));
			
		//mkdir() 해당 경로에 파일을 생성한다.
		file4.mkdir();
		
		//mkdirs() 존재하지 않는 부모 폴더까지 포함하여 해당 경로에 폴더를 만든다.
		file5.mkdirs();
		
		//renameTo(File dest) 메소드는 dest로 해당 File의 이름을 변경한다.
		file5.renameTo(new File("data/day03/yjkimbarun"));
		
		//setExecutable(boolean executeable) 메소드는 파일 소유자의 실행 권한을 설정한다.
		//파일 실행 권한 없음
		file5.setExecutable(false);
		//소유자만 파일 실행 권한 있음
		file5.setExecutable(true);
		//파일 실행 권한 소유자 없음
		file5.setExecutable(false, true);
		//파일 실행 권한 전부 있음 
		file5.setExecutable(true, true);
		//파일 실행 권한 소유자만 존재
		file5.setExecutable(true, false);
		
		//setLastModifed(long time) 메소드는 해당 파일의 최종 변경 시간을 설정한다.
		file5.setLastModified(0);
		LOGGER.debug(String.format("setLastModified() 실행 후 수정 시간 [%s]", file5.lastModified()));
	
		//setReadable() 메소드는 파일의 읽기 권한을 설정한다.
		//모두에 대해 읽기 불가
		file5.setReadable(false);
		//소유자만 읽기 가능
		file5.setReadable(true);
		//소유자 읽기 불가
		file5.setReadable(false,true);
		//모두에 대해 읽기 불가
		file5.setReadable(false,false);
		//모두에 대해 읽기 가능
		file5.setReadable(true,false);
		//소유자만 읽기 가능
		file5.setReadable(true,true);
		
		//setReadOnly() 메소드는 파일을 읽기 전용으로 변경한다.
		file5.setReadOnly();
		
		//setWritable() 메소드는 파일 소유자의 쓰기 권한을 설정한다.
		//모든 사람 쓰기 불가
		file5.setWritable(false);
		//소유자만 쓰기 가능
		file5.setWritable(true);
		//모든 사람 쓰기 불가
		file5.setWritable(false,false);
		//소유자 쓰기 불가
		file5.setWritable(false,true);
		//모든 사람 쓰기 가능
		file5.setWritable(true,false);
		//소유자만 쓰기 가능 
		file5.setWritable(true,true);
		
		//toPath() 메소드는 java.nio.file.Path 객체로 반환한다.
		LOGGER.debug(String.format("toPath () 메소드 [%s]", file5.toPath()));
		
		//toString() 메소드는 경로의 문자열을 반환한다.
		LOGGER.debug(String.format("toString () 메소드 [%s]", file5.toString()));

		//toURI() 메소드는 URI의 형태로 파일 경로를 반환한다.
		LOGGER.debug(String.format("toURI() 메소드 [%s]", file5.toURI()));

		//////////////////////////////////////////////////////////////
		
		//java.nio.file.Files 관련 메소드
		//Files.copy() 메소드는 첫번째 인자 파일의 내용으로 두번째 파일을 만든다. 
		File nfile = new File("data/yjkimday04/address.txt");
		File nfile2 = new File("data/yjkimday04/barun.txt");
		
		//Files.copy(nfile.toPath(),nfile2.toPath());
		
		//Files.createDirectories() 메소드는 
		// 상위 디렉터리가 존재하지 않을 경우, 모든 디렉토리를 만든다.
		Path path = Paths.get("data2/yjkim04-3/barun/");
		Files.createDirectories(path);
	
		Path path2 = Paths.get("data/yjkimday04/barun3.txt");
		//Files.createFile() 메소드는 새로운 파일을 생성한다.
		//Files.createFile(path2);
		
		//Files.createLink() 메소드는 이미 존재하는 파일에 대해 새로운 링크를 만든다.
		Path dir = Files.createTempDirectory("my-dir");
		Path srcFilePath = dir.resolve("test-file.txt");
		Files.write(srcFilePath, "Test Barun".getBytes());
		
		Path linkPath = dir.resolve("linked-test-file.txt");
		Path link = Files.createLink(linkPath, srcFilePath);
		LOGGER.debug(String.format("Files.createLink() 메소드 [%s]", link));
		
		
		//Files.createSymbolicLink()
		//타겟에 대한 심볼릭 링크를 생성한다.
		/*
		Path dir2 = Files.createTempDirectory(Paths.get("D:\\"), "my-dir");
		Path srcFilePath2 = dir2.resolve("test-file.txt");
		Files.write(srcFilePath2,"Test Barun".getBytes());
		
		Path symLinkToCreate = dir2.resolve("linked-test-file.txt");
		Path symLink = Files.createSymbolicLink(symLinkToCreate,srcFilePath2);
        Files.write(srcFilePath2, " .. more data ..".getBytes(), StandardOpenOption.APPEND);

		String linkData = new String(Files.readAllBytes(symLink));
		
		LOGGER.debug(String.format("Files.createSymbolicLink() 메소드 [%s]", linkData));
		*/
		//Files.createTempFile() 메소드
		// 지정된 접두사 및 문자열을 사용하여 디렉토리에 빈 파일을 생성한다.
		Path create_path = Paths.get("data/yjkimday04/");
		Files.createTempFile(create_path,"barun","sw");
		
		//Files.delete() 메소드는 파일을 지운다.
		//Files.delete(path2);
		
		//Files.deleteIfExists() 메소드는 만약 파일이 존재한다면 지운다.
		Files.deleteIfExists(path2);
		
		//Files.exists() 메소드는 파일이 존재하는지 확인한다.
		LOGGER.debug(String.format("Files.exists() 메소드 [%s]", Files.exists(path2)));
		
		//Files.getAttribute() 메소드는 파일의 속성 값을 읽는다.
		/*Path tempFile = Files.createTempFile("test-file", ".txt");
		Object attribute = Files.getAttribute(tempFile,"basic:creationTime");
		LOGGER.debug(String.format("Files.attribute() 메소드 [%s]",attribute));
		
		Object readOnly = Files.getAttribute(tempFile,"dos:readonly");
		LOGGER.debug(readOnly);*/
		
		//Files.getFileAttributeView() 메소드는 지정된 파일의 파일특성 view를 반환한다.
		Path tempFile = Files.createTempFile("test-file", ".txt");
		BasicFileAttributeView fileAttributeView =
				Files.getFileAttributeView(tempFile, BasicFileAttributeView.class);
		LOGGER.debug(String.format("Files.getFileAttributeView() 메소드 [%s]", fileAttributeView));
	
		//Files.getFileStore() 메소드는 파일이 있는 FileStore를 반환한다.
		LOGGER.debug(String.format("Files.getFileStore() 메소드 [%s]", Files.getFileStore(path)));
	
		//Files.getLastModifiedTime()
		//파일의 마지막 수정시간을 반환한다.
		LOGGER.debug(String.format("Files.getLastModifiedTime() 메소드 [%s]", Files.getLastModifiedTime(path)));
	
		//Files.getOwner()
		//파일의 소유자를 반환한다
		LOGGER.debug(String.format("Files.getOwner() [%s]", Files.getOwner(path)));
		/*
		//Files.getPosixFilePermissions()
		//파일의 POSIX 파일 사용 권한을 반환한다.
		LOGGER.debug(String.format("Files.getPosixFilePermissions() 메소드 [%s]",Files.getPosixFilePermissions(path)));
		*/
		//Files.isDirectory()
		//파일이 디렉토리인지 검사한다.
		LOGGER.debug(String.format("Files.isDirectory() 메소드 [%s]", Files.isDirectory(path)));
		
		//Files.isExecutable()
		//파일이 실행가능한지 검사한다.
		LOGGER.debug(String.format("Files.isExecutable() 메소드 [%s]", Files.isExecutable(path)));
		
		//Files.isHidden()
		//파일이 숨김 파일인지 확인한다.
		LOGGER.debug(String.format("Files.isHidden() 메소드 [%s]", Files.isHidden(path)));
		
		//Files.isReadable()
		//파일을 읽을 수 있는지 확인한다.
		LOGGER.debug(String.format("Files.isReadable() 메소드 [%s]", Files.isReadable(path)));
	
		//Files.isRegularFile()
		//파일이 불투명한 내용의 정규 파일인지 여부를 확인한다.
		LOGGER.debug(String.format("Files.isRegularFile() 메소드 [%s]", Files.isRegularFile(path)));
		
		//Files.isSameFile()
		//파일이 같은 파일인지 확인한다.
		LOGGER.debug(String.format("Files.isSameFile() 메소드 [%s]", Files.isSameFile(path, path)));
		
		//Files.isSymbolicLink()
		//파일이 심볼릭 링크 인지 확인한다.
		LOGGER.debug(String.format("Files.isSymbolicLink() 메소드 [%s]", Files.isSymbolicLink(path)));
		
		//Files.isWritable()
		//파일이 작성할 수 있는지 확인한다.
		LOGGER.debug(String.format("Files.isWritable() 메소드 [%s]", Files.isWritable(path)));
	
		//Files.move()
		//파일의 이름을 변경하거나 파일을 타겟 경로로 변경한다.
		Files.move(path, path2);
		
		//Files.newBufferedReader()
		//효율적인 방법으로 파일에서 텍스트를 읽기 위해 사용할 수 있는
		//BufferedReader를 반환하기 위한 파일을 연다.
		Path barun_path = Paths.get("data/yjkimday04/address.txt");
		
		Charset charset = Charset.forName("UTF-8");
		try (BufferedReader reader = Files.newBufferedReader(barun_path, charset)){
			String line = null;
			while ((line = reader.readLine()) != null) {
				LOGGER.debug(String.format("Files.newBufferedReader() 메소드 [%s]", line));
			}
		}catch(IOException ioe) {
			LOGGER.debug(ioe.getMessage(), ioe);
		}
		
		//Files.newBufferedWriter()
		//효율적인 방법으로 파일에 텍스트를 쓰는 데 사용될 수 있는
		//BufferedWriter를 반환하여 쓰기 위한 파일을 열거나 생성한다.
		String newName = "Barun is best";
		Path buffer_file = Paths.get("data/yjkimday04/barunsw.txt");
		try (BufferedWriter writer = Files.newBufferedWriter(buffer_file, Charset.defaultCharset(), StandardOpenOption.APPEND)){
			writer.newLine();
			writer.write(newName,0,newName.length());
		}catch(IOException ioe) {
			LOGGER.debug(ioe.getMessage(), ioe);
		}
		
		//Files.newByteChannel()
		//파일에 접근하기 위해 검색가능한 바이트 채널을 반환하는 파일을 생성하거나 연다.
		Path channel_path = Files.createTempFile("test-file", ".txt");
		try (SeekableByteChannel channel = Files.newByteChannel(channel_path, StandardOpenOption.READ,StandardOpenOption.WRITE)){
			ByteBuffer byteBuffer = ByteBuffer.wrap("test string".getBytes());
			int b = channel.write(byteBuffer);
			LOGGER.debug(String.format("Files.newByteChannel() bytes written : [%d]", b));
			channel.position(0);
			ByteBuffer byteBuffer2 = ByteBuffer.allocate(b);
			channel.read(byteBuffer2);
			LOGGER.debug(String.format("Files.newByteChannel() 메소드 [%s]", new String(byteBuffer2.array())));
			
		}catch(IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		
		//Files.newDirectoryStream()
		//디렉토리를 열어 디렉토리의 모든 항복을 반복하여 디렉토리 스트림을 반환한다.
		String pathString = System.getProperty("java.io.tmpdir");
		Path DirPath = Paths.get(pathString);
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(DirPath)){
			Iterator<Path> iterator = ds.iterator();
			int c = 0;
			while (iterator.hasNext() && c < 5) {
				Path p = iterator.next();
				LOGGER.debug(String.format("Files.newDirectoryStream() 메소드 [%s]", p));
				c++;
			}
		}catch(IOException ioe) {
			LOGGER.debug(ioe.getMessage(), ioe);
		}
		
		//Files.newInputStream()
		//파일을 열고 파일에서 읽을 입력 스트림을 반환한다.
		Path InputPath = Files.createTempFile("test-file",".txt");
		Files.write(InputPath, "test content".getBytes());
		byte[] data = new byte[1024];
		try (InputStream inputStream = Files.newInputStream(InputPath)){
			int count = inputStream.read(data);
			LOGGER.debug(String.format("Files.newInptuStream() 메소드 [%s]", new String(data)));
		}catch(IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		
		//Files.newOutputStream()
		//파일을 열거나 생성하여 파일에 바이트를 쓰는 데 사용할 수 있는 출력 스트림을 반환한다.
		Path outputPath = Files.createTempFile("test-file",".txt");
		try (OutputStream outputStream = Files.newOutputStream(outputPath)){
			outputStream.write("barunsw test".getBytes());
			LOGGER.debug(String.format("Files.newOutputStream() 메소드 [%s]", Files.readAllLines(outputPath)));
		}catch(IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		
		//Files.noExists()
		//해당 경로에 위치한 파일이 존재 하지 않는지 확인한다.
		LOGGER.debug(String.format("Files.noExist() 메소드 [%s]", Files.notExists(path))); 
	
		//Files.probeContentType()
		//파일의 컨텐츠타입을 반환한다.
		LOGGER.debug(String.format("Files.probeContentType() 메소드 [%s]", Files.probeContentType(path2)));
	
		//Files.readAllbytes()
		//파일의 모든 바이트를 읽어들인다.
		Path byte_path = Files.createTempFile("test-file",".txt");
		Files.write(byte_path,"barun's yj".getBytes());
		byte[] bytes = Files.readAllBytes(byte_path);
		LOGGER.debug(String.format("Files.readAllbytes() 메소드 [%s]",new String(bytes)));
		
		//Files.readAllLines()
		//파일로부터 모든 행을 읽어들인다.
		Path LinePath = Files.createTempFile("test-file", ".txt");
		Files.write(LinePath,"line 1 \n line2 \n".getBytes());
		List<String> lines = Files.readAllLines(LinePath);
		for (int i = 0; i < lines.size(); i++) {
			LOGGER.debug(String.format("Files.readAllLines() 메소드 [%s]", lines.get(i)));
		}
		
		//Files.readAttributes()
		//파일의 속성을 읽어들인다.
		Path attr_path = Paths.get("data/yjkimday04/address.txt");
		BasicFileAttributes attr = Files.readAttributes(attr_path, BasicFileAttributes.class);
		
		LOGGER.debug(String.format("Files.readAttributes () 메소드 creationTime = [%s]",attr.creationTime()));
		LOGGER.debug(String.format("Files.readAttributes () 메소드 lastAccessTime = [%s]",attr.lastAccessTime()));
		LOGGER.debug(String.format("Files.readAttributes () 메소드 lastModifiedTime = [%s]",attr.lastModifiedTime()));

		LOGGER.debug(String.format("Files.readAttributes () 메소드 isDirectory = [%s]",attr.isDirectory()));
		LOGGER.debug(String.format("Files.readAttributes () 메소드 isOther = [%s]",attr.isOther()));
		LOGGER.debug(String.format("Files.readAttributes () 메소드 isRegularFile = [%s]",attr.isRegularFile()));
		LOGGER.debug(String.format("Files.readAttributes () 메소드 isSymbolicLink = [%s]",attr.isSymbolicLink()));
		LOGGER.debug(String.format("Files.readAttributes () 메소드 size = [%s]",attr.size()));

		//Files.readSymbolicLink()
		//파일의 심볼릭 링크를 읽어들인다. // 심볼릭 권한문제 
		/*Path symPath = Files.createTempDirectory("test-dir");
		Path symFile = Files.createTempFile(symPath, "test-file", ".txt");
		
		Path symbolic = Files.createSymbolicLink(symPath.resolve("sym-test-file.txt"), symFile);
		Path result = Files.readSymbolicLink(symbolic);

		LOGGER.debug(String.format("Files.readSymbolicLink() [%s]", result));
		*/
		
		//Files.setAttribute()
		//파일의 속성을 설정한다.
		long time = System.currentTimeMillis();
		FileTime fileTime = FileTime.fromMillis(time);
		
		Files.setAttribute(path2, "basic:lastModifiedTime", fileTime, LinkOption.NOFOLLOW_LINKS);
		Files.setAttribute(path2, "basic:creationTime", fileTime, LinkOption.NOFOLLOW_LINKS);
		Files.setAttribute(path2, "basic:lastAccessTime", fileTime, LinkOption.NOFOLLOW_LINKS);

		
		//Files.setLastModifiedTime()
		//파일의 최종수정시간을 설정한다.
		
		Path modify_Path = Files.createTempFile("test-file",".txt");
		
		FileTime lastModifiedTime = Files.getLastModifiedTime(modify_Path);
		
		LOGGER.debug(String.format("Files.getLastModifiedTime () 메소드 [%s]", lastModifiedTime));
		
		Instant instant = Instant.now().minusSeconds(10000 * 24 * 60 * 60);
		
		FileTime fileTime2 = FileTime.from(instant);
		Files.setLastModifiedTime(modify_Path, fileTime2);
		
		lastModifiedTime = Files.getLastModifiedTime(modify_Path);
		
		LOGGER.debug(String.format("Files.setLastModifiedTime () 메소드 호출 후 값 [%s]",lastModifiedTime));
		
	
		//Files.setOwner()
		//파일의 소유자를 설정한다.
		/*UserPrincipal owner = Files.getOwner(path2);
		FileSystem fileSystem = path2.getFileSystem();
		UserPrincipalLookupService service = fileSystem.getUserPrincipalLookupService();
	
		LOGGER.debug(String.format("Files.getOwner() [%s]", Files.getOwner(path2)));
		
		UserPrincipal userPrincipal = service.lookupPrincipalByName("yj");
		Files.setOwner(path2, userPrincipal);
		LOGGER.debug(String.format("Files.setOwner() 호출 후 값 [%s]", Files.getOwner(path2)));
		 */
		
		//Files.size()
		//파일의 크기를 얻는다
		LOGGER.debug(String.format("Files.size() 메소드  [%s]", Files.size(path2)));

		//Files.walkFileTree()
		 String walk_path= "C:\\temp";
	        Files.walkFileTree(Paths.get(walk_path),new HashSet<>(), 2, new FileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
	                LOGGER.debug(String.format("Files.walkFileTree() 이전 방문 디렉토리  [%s]", dir));
	                
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	                LOGGER.debug(String.format("Files.walkFileTree()  방문 파일  [%s]", dir));

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
	                LOGGER.debug(String.format("Files.walkFileTree()  방문 실패 파일  [%s]", dir));

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
	                LOGGER.debug(String.format("Files.walkFileTree() 이후 방문 디렉토리  [%s]", dir));

					return FileVisitResult.CONTINUE;
				}
	        
	        
	        });

		//Files.write()
		//java.nio.FIle.files에서 파일 쓰기를 할 수 있다.

	    Path write_path = Files.createTempFile("test-file",".txt");
	    Files.write(write_path,"some test content".getBytes());
	    byte[] bytes2 = Files.readAllBytes(write_path);
	    LOGGER.debug(String.format("Files.write() 메소드 [%s]", new String(bytes2)));
	    
	}
	
}
class JavaFilenameFilter implements FilenameFilter{

	@Override
	public boolean accept(File dir, String name) {
		// TODO Auto-generated method stub
		return name.endsWith(".txt");
	}
	
}
