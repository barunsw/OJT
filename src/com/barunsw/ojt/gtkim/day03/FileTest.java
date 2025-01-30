package com.barunsw.ojt.gtkim.day03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileTest {
	private static final Logger LOG = LogManager.getLogger(FileTest.class);

	public static void main(String[] args) {
		LOG.info("File IO Stream");

		File exampleFile = new File("data/day03/gtkim/address.txt");
		// InputStream / OutputStream 은 바이트 단위 스트림이다.
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(exampleFile);
			LOG.debug("InputStream 생성");

			byte[] data = new byte[512];
			int bytesize = inputStream.read(data);
			LOG.debug(String.format("[%d]Byte msg : %s", bytesize, new String(data)));

			outputStream = new FileOutputStream(
					new File("data/day03/gtkim/addressByOutputStream.txt"));
			outputStream.write(data);
			LOG.debug("OutputStream Done!");
		}
		catch (FileNotFoundException fnfe) {
			LOG.error(fnfe.getMessage(), fnfe);
		}
		catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe);
		}
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} 
				catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				}
				catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}				
			}
		}

		// AutoCloseable -> Closeable을 상속받아 자동으로 close를 수행
		try (InputStream is = new FileInputStream(exampleFile)) {
			LOG.debug("InputStream 생성 with AutoCloseable");

			byte[] byteData = new byte[10];
			int bytesize = is.read(byteData);
			// UTF-8 인코딩에서 한글은 3B값이라고 함
			LOG.debug(String.format("[%d]Byte msg : %s", bytesize, new String(byteData)));
		}
		catch (FileNotFoundException fnfe) {
			LOG.error(fnfe.getMessage(), fnfe);
		}
		catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe);
		}

		// Reader / writer는 문자 단위 입출력 스트림이다.
		try (Reader reader = new FileReader(exampleFile);
				Writer writer = new FileWriter(
						new File("data/day03/gtkim/addressByWriter.txt"))) {
			LOG.debug("Reader & Writer 생성");
			char[] charData = new char[5];
			int charSize = reader.read(charData);

			LOG.debug(String.format("[%d]Char msg : %s", charSize, new String(charData)));
			writer.write(charData);
			LOG.debug("Writer Done!");
		}
		catch (FileNotFoundException fnfe) {
			LOG.error(fnfe.getMessage(), fnfe);
		}
		catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe);
		}

		List<Person> personList = new ArrayList<Person>();
		// BufferedReader를 사용하면 라인(\r\n)으로 읽기 가능
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(exampleFile));
			writer = new BufferedWriter(new FileWriter(
					new File("data/day03/gtkim/addressByBufferedWriter.txt")));
			// 라인별로 읽어 문자열로 바로 저장 가능하다
			String readLine = null;

			while ((readLine = reader.readLine()) != null) {
				LOG.debug("msg by line : " + readLine);
				String[] splitData = readLine.trim().split(",");
				
				// BufferedWriter의 경우 버퍼를 잡아 놓기 떄문에 반드시 flush()호출이 필요하다고 함
				// 또한 자동 개행 기능이 없기에 개행을 해주어야 함.
				writer.write(readLine + "\n");
				writer.flush();
				LOG.debug("BufferWrite Done!");
				
				if (splitData.length > 5) {
					Person onePerson = new Person();
					try {
						onePerson.setName(splitData[1]);
						onePerson.setGender(Gender.toGender(splitData[2]));
						onePerson.setAge(Integer.parseInt(splitData[3]));
						onePerson.setPhone(splitData[4]);
						onePerson.setAddress(splitData[5]);

						//LOG.debug(String.format("onePerson : %s", onePerson));

						personList.add(onePerson);
					} catch (Exception e) {
						LOG.error(e.getMessage(), e);
					}
				}
			}
		}
		catch (FileNotFoundException fnfe) {
			LOG.error(fnfe.getMessage(), fnfe);
		}
		catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				}
				catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}		
			}
			if (writer != null) {
				try {
					writer.close();
				}
				catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
		
		File objFile = new File("data/day03/gtkim/addressByObject.dat");
		//객체 단위로 출력과 입력 
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(objFile));
			 ObjectInputStream ois  = new ObjectInputStream(
			    new FileInputStream(objFile))) {			
			
			//위에서 저장한 person List를 obj스트림으로 출력 
			for(Person one : personList) {
				oos.writeObject(one);
			}
			LOG.debug("object outputStream Done!");
			
			Object o;
			while((o = ois.readObject()) != null) {
				LOG.debug("불러온 객체 : " + (Person)o);
			}
		}
		catch (FileNotFoundException fnfe) {
			LOG.error(fnfe.getMessage(), fnfe);
		}
		catch (EOFException e) {
		}
		catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe);
		}
		catch (ClassNotFoundException cnfe) {
			LOG.error(cnfe.getMessage(), cnfe);
		}
	}
}
