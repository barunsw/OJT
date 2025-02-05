package com.barunsw.ojt.cjs.day03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.day03.Person;

public class FileTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileTest.class);

	public static void main(String[] args) {

		// 상대경로
		File addressFile = new File("data/day03/cjs/address.txt");

		LOGGER.debug("Address File exists " + addressFile.exists()); // exists() == 파일이 있는지 확인(boolean 값)

		// stream단위 입출력

		byte[] data = new byte[11];
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(addressFile);
			LOGGER.debug("inputStream 생성 완료");

			int readNum = inputStream.read(data);
			LOGGER.debug(String.format("[%d]%s", readNum, new String(data)));
		} catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ioe) {
					LOGGER.error(ioe.getMessage(), ioe);
				}
			}
		}
		OutputStream outputStream = null;
		File output = new File("data/day03/cjs/outputStream.txt");
		try {
			inputStream = new FileInputStream(addressFile);
			LOGGER.debug("InputSteam 생성");
			outputStream = new FileOutputStream(output);
			int readNum = inputStream.read(data);
			outputStream.write(data);
			LOGGER.debug("OutputStream 생성");

		} catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (Exception e) {
					LOGGER.debug(e.getMessage(), e);
				}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception e) {
					LOGGER.debug(e.getMessage(), e);
				}
			}
		}

		/*
		 * /try -with - resources try에 객체를 전달 -> try 코드블록이 끝나면 자동을 자원 종료 AutoCloseable
		 * 인터페이스를 상속받는 클래스를 통해 사용 finally에 close메서드 호출할 필요없이 try 코드블럭끝나면 close됨
		 */

		try (InputStream inputStream2 = new FileInputStream(addressFile)) {
			int readNum = inputStream2.read(data);
			LOGGER.debug(String.format("[%d] %s", readNum, new String(data)));
		} catch (FileNotFoundException fnfe) {
			LOGGER.debug(fnfe.getMessage(), fnfe);
		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}

		List<Person> personlist = new ArrayList<Person>();

		/*
		 * line 단위로 읽기 위해 BufferedReader사용
		 */

		try (BufferedReader reader = new BufferedReader(new FileReader(addressFile))) {
			String line = null;
			while ((line = reader.readLine()) != null) { // 파일에 더 이상 읽을 문자열이 없을 때 까지
				LOGGER.debug(String.format("+++ [%s]", line)); // 읽은 문자열 하나씩 반환

				String[] splitData = line.split(","); // csv형식, ','를 기준으로 한문자씩 자름
				if (splitData.length >= 6) { // pk, name, gender, age, num, address ==6
					try {
						Person onePerson = new Person();

						onePerson.setName(splitData[1]);
						onePerson.setGender(Gender.toGender(splitData[2]));
						onePerson.setAge(Integer.parseInt(splitData[3]));
						onePerson.setPhone(splitData[4]);
						onePerson.setAddress(splitData[5]);

						personlist.add(onePerson);

						LOGGER.debug(String.format("+++ OnePerson: %s", onePerson)); // 객체의 toString 반환
					} catch (Exception e) {
						// TODO: handle exception
						LOGGER.debug(e.getMessage(), e);
					}
				}
			}
		} catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}

		File outputFile = new File("data/day03/cjs/address.dat");

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile))) {
			for (Person onePerson : personlist) {
				oos.writeObject(onePerson);
			}
		} catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}

		try (BufferedReader reader = Files.newBufferedReader(Paths.get("data/day03/cjs/address.txt"),
				StandardCharsets.UTF_8)) {
			String readLine = null;
			while ((readLine = reader.readLine()) != null) {
				LOGGER.debug(String.format("=== [%s]", readLine));
			}
		} catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}

		File readFile = new File("data/day03/cjs/address.dat");
		File writeFile = new File("data/day03/cjs/BufferdWriterTest.txt");

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(readFile)));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeFile)))) {
			String read = null;
			while ((read = br.readLine()) != null) {
				bw.write(read);
				bw.newLine();
			}
		} catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}

	}
}
