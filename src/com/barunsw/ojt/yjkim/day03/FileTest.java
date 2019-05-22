package com.barunsw.ojt.yjkim.day03;

import java.io.BufferedOutputStream;
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
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;

public class FileTest {
	private static final Logger LOGGER = LogManager.getLogger(FileTest.class);
	/*
	 * 신입 사원 OJT day3
	 * 파일 입출력
	 * 김윤제 
	 * */
	public static void main(String[] args) throws IOException {
		LOGGER.debug("main");
		// 상대경로
		File addressFile = new File("data/day03/yjkim/address.txt");
		// 절대경로
		//File addressFile = new File("D:\\git\\OJT\\data\\day03\\yjkim\\address.txt");
		
		//파일이 존재하는지 체크한다. 있으면 true, 없으면 false를 반환한다.
		LOGGER.debug("addressFile exists:" + addressFile.exists());
		
		
        //InputStream을 이용한 파일 읽기
		byte[] data = new byte[1024];
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(addressFile);
			LOGGER.debug("inputStream 생성 완료");
			//inputStream을 통해 파일을 읽어 들여 byte 배열인 data에 저장한다. 그 후 저장된 바이트 수를 반환한다.
			//read()를 사용 할 경우 byte 생성 시 크기를 지정해주어야 하기 때문에 불편하다.
			int readNum = inputStream.read(data);
			//String.format은 출력 할 형식을 지정 할 수 있다. 
			//data는 byte형이기 때문에 문자열로 출력하기 위해 new String()과 함께 써준다. 
			LOGGER.debug(String.format("inputStream을 이용한 read[%d]%s", readNum, new String(data)));
		}
		//File이 없을 경우의 예외
		catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		} 
		//입출력 오류가 발생 하였을 경우 예외
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		//파일 입출력에서 auto-close 방법을 사용하지 않는다면
		//반드시 finally에서 사용한 입력과 출력을 닫아줘야 한다.
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (IOException ioe) {
					LOGGER.error(ioe.getMessage(), ioe);
				}
			}
		}
		//OutputStream을 이용한 파일 쓰기 
		//FileOutputStream은 OutputStream 클래스를 상속받아 만든 클래스로
		//바이트 단위로 데이터를 처리한다.
		//FileOutputStream에 값을 쓸 때는 byte배열로 써야 하므로 아래와 같이
		//String을 byte배열로 바꾸어 주는 getBytes() 메서드를 사용해야 한다. 
		 OutputStream ops=null;
		try {
			//data/day03/yjkim 아래에 있는 address2.txt파일을 연다. 
			 ops = new FileOutputStream("data/day03/yjkim/address2.txt");
			 String ops_data = "this is barunsw";
			 ops.write(ops_data.getBytes());
		} catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}finally {
			if(ops != null) {
				try {
					//파일 입력이 끝나면 반드시 사용한 스트림을 닫아야 한다. 
					ops.close();
				} catch (IOException ioe) {
					LOGGER.error(ioe.getMessage(), ioe);
				}
			}
		}
///////////////////////////////////////////////////////////////////////////////
		
		//auto-close 방식으로 finally 내에서 입출력을 닫을 필요가 없다.
		/*try (InputStream inputStream2 = new FileInputStream(addressFile)) {
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		} 
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}*/
		
//////////////////////////////////////////////////////////////////////

		//FileReader을 이용한 파일 읽기 
		//auto-close 방식으로 finally 내에서 입출력을 닫을 필요가 없다.
		char[] charData = new char[1024];
		//한글이 3byte로써 크기를 많이 차지하기 때문에 FileReader를 사용하여
		//char형을 이용하여 파일을 읽을 수 있다.  
		try (Reader reader = new FileReader(addressFile)) {
			//위 방식과 마찬가지로 read() 를 통해 읽어 들인 문자를 char배열에 저장한 후, 읽어들인 수를 반환한다.
			int readNum = reader.read(charData);
			
			//하지만 이러한 방식도 문자열로 출력하려면 new String()을 해줘야하는 불편함이 있다. 
			LOGGER.debug(String.format("FileReader를 이용한 read [%d]%s", readNum, new String(charData)));
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		
		//FileWriter을 이용한 파일 쓰기
		//FileOutputStream 대신 FileWriter을 사용하면 byte 배열 대신 문자열을 
		//직접 파일에 쓸 수가 있다. 
		FileWriter fw = null;
		try {
			fw = new FileWriter("data/day03/yjkim/address3.txt");
			String fw_data = "barunsw is good r\n";
			fw.write(fw_data);
		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}finally {
			if(fw != null) {
				try {
					fw.close();
				} catch (IOException ioe) {
					LOGGER.error(ioe.getMessage(),ioe);
				}
			}
		}

		//PrintWriter을 이용한 파일 쓰기
		//PrintWriter을 이용하면 \r\n를 덧붙이는 대신 println이라는 메소드를 사용할 수 있다.
		PrintWriter pw = null;
		try {
			pw = new PrintWriter("data/day03/yjkim/address4.txt");
			String pw_data = "barun test pw";
			pw.println(pw_data);
		} catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		}finally {
			if(pw != null) {
				pw.close();
			}
		}
		
	
////////////////////////////////////////////////////////////////////////////////
	
		//BufferedReader를 이용한 파일 읽기 & auto close
		//auto close 방식을 이용하면 파입 입출력 시 입출력이 끝난 후 스트림을 닫지 않아도 자동으로 닫힌다.
		try(BufferedReader br = new BufferedReader(new FileReader("data/day03/yjkim/address4.txt"))){
			String br_line = null;
			while((br_line = br.readLine()) != null) {
				LOGGER.debug(String.format("BufferedReader를 이용한 파일 읽기 [%s]",br_line));
			}
		}catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		
		/*//BufferedReader를 이용한 파일 객체 단위 읽기 
		//personList ArrayList 선언 
		List<Person> personList = new ArrayList<Person>();
		
		// line 단위로 읽기 위해 BufferedReader 사용
		try (BufferedReader reader = new BufferedReader(new FileReader(addressFile))) {
			String readLine = null;
			//readLine이 null일때 까지라는 말은 파일의 끝까지 읽는다는 뜻
			while ((readLine = reader.readLine()) != null) {
				LOGGER.debug(String.format("BufferedReader를 이용한 객체 단위 Read [%s]", readLine));
				//split()을 이용해 ,로 이어진 문자열들을 분리하여 String 배열에 저장한다. 
				String[] splitData = readLine.split(",");
				if (splitData.length >= 6) {
					try {
						Person onePerson = new Person();
						onePerson.setName(splitData[1]);
						//성별은 남,녀 단 두개이기 때문에 편의성과 가독성을 위해 열거형,또는 클래스를 사용하여 처리한다.
						onePerson.setGender(Gender.toGender(splitData[2]));
						//Age는 int형이기 때문에 문자열을 Integer형으로 Parsing 해준다.
						onePerson.setAge(Integer.parseInt(splitData[3]));
						onePerson.setPhone(splitData[4]);
						onePerson.setAddress(splitData[5]);
						personList.add(onePerson);
						
						LOGGER.debug(String.format("bufferedReader를 이용한 onePerson:%s", onePerson));
					}
					catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}
			}
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		*/
		
		//BufferedReader를 이용한 파일 객체 단위로 전부 읽기 
		//personList ArrayList 선언 
		List<Person> personList = new ArrayList<Person>();
		int count = 0;
		// line 단위로 읽기 위해 BufferedReader 사용
		try (BufferedReader reader = new BufferedReader(new FileReader(addressFile))) {
			String readLine = null;
			//readLine이 null일때 까지라는 말은 파일의 끝까지 읽는다는 뜻
			while ((readLine = reader.readLine()) != null) {
					//파일 안에 있는 3명의 사람 전부 리스트에 추가하고 출력한다. 
					try {
							String[] splitData2 = readLine.split(",");
							Person onePerson = new Person();
							onePerson.setName(splitData2[1]);
							onePerson.setGender(Gender.toGender(splitData2[2]));
							onePerson.setAge(Integer.parseInt(splitData2[3]));
							onePerson.setPhone(splitData2[4]);
							onePerson.setAddress(splitData2[5]);
							personList.add(onePerson);
							LOGGER.debug(String.format("bufferedReader를 이용한 모든 사람 출력하기 :[%d번째 사람] %s 총 인원 [%d]", ++count, onePerson, personList.size()));

						}
						
					catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}
			}
		
		
		
		
		
		
		//BufferedInputStream을 이용한 파일 읽기
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("data/day03/yjkim/address.txt");
		
			//버퍼 선언
			byte[] readBuffer = new byte[fis.available()];
			//버퍼가 꽉 찰때 까지 읽어 들인다.
			while(fis.read(readBuffer) != -1) {
				//readBuffer는 byte형이므로 출력을 위해 String으로 변환한다. 
				LOGGER.debug(String.format("BufferedInputStream을 이용한 파일 읽기 [%s]",new String(readBuffer)));
			}
		}catch(IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		
		
		//BufferedOutputStream을 이용한 파일 쓰기
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream("data/day03/yjkim/address5.txt"));
			String bos_str = "바른 개발 연구소 입니다. ";
			//BufferedOutputStream은 byte단위로 데이터를 처리하기 때문에 Byte형으로 변환해야 한다.
			bos.write(bos_str.getBytes());
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
		}finally {
			if(bos != null) {
				try{
					bos.close();
				}catch(IOException ioe) {
					LOGGER.error(ioe.getMessage(), ioe);
				}
			}
		}
		
		
		//BufferedWriter를 이용한 파일 쓰기
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/day03/yjkim/address6.txt")))
		{
			bw.write("barun's third java");
		}catch(IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
				
		
				
		
		
		
//////////////////////////////////////////////////////////////////////////////////////
		//ObjectOutputStream을 이용한 객체 단위 write && auto close
		File outputFile = new File("data/day03/yjkim/address2.dat");
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile))) {
			//PersonList에 있는 객체들을 전부 outputFile에 저장한다. 
			for (Person onePerson : personList) {
				oos.writeObject(onePerson);
			}
		}
		
		catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		
		//ObjectInputStream을 이용한 객체 단위 read
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(outputFile))) {
			Object o;
			while ((o = ois.readObject()) != null) {
				LOGGER.debug("ObjectInputStream을 이용한  oneObject:" + o);
			}
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		}
		catch (EOFException eofe) {
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		} 
		catch (ClassNotFoundException cnfe) {
			LOGGER.error(cnfe.getMessage(), cnfe);
		}
		
		
		
////////////////////////////////////////////////////////////////////////////////////////////////////////		
		//newBufferedReader와 Path.get(uri)을 이용한 출력 
		//Paths.get(uri)는 uri 또는 String을 Path로 Converting하여 반환한다.
		try (BufferedReader reader = Files.newBufferedReader(Paths.get("data/day03/address.txt"), StandardCharsets.UTF_8)) {
			String readLine = null;
			while ((readLine = reader.readLine()) != null) {
				LOGGER.debug(String.format("=== [%s]", readLine));
			}
		}
		catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
	}
}
