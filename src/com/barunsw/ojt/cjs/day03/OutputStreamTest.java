package com.barunsw.ojt.cjs.day03;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.barunsw.ojt.day03.Person;

public class OutputStreamTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(OutputStreamTest.class);

	public static void main(String[] args) {

		File readFile = new File("data/day03/cjs/address.dat");
		File writeFile = new File("data/day03/cjs/writeFile.txt");

		// List<Person> 객체를 만들고, ObjectInputStream으로 읽어서 Person 정보를 넣고
		List<Person> list = new ArrayList<Person>();

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(readFile))) {
			Object o;

			while ((o = ois.readObject()) != null) {

				if (o instanceof Person) {
					Person p = (Person) o;
					list.add(p);
					LOGGER.debug("--- oneObject:" + p);
				}
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
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		// BufferedWriter를 통해 List<Person> 객체가 가지고 있는 정보를 txt 파일에 write한다.
		// 단, address.txt와 동일한 결과의 txt파일이 만들어져야 한다.

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(writeFile))) {
			for (int i = 0; i < list.size(); i++) {
				Person p = list.get(i);
				
				bw.write(String.format("%s,%s,%s,%s,%s,%s\n", 
						(i+1), p.getName(), p.getGender(), p.getAge(), p.getPhone(), p.getAddress()));
				/*
				bw.write(i + 1 + ",");
				bw.write(list.get(i).getName() + ",");
				bw.write(list.get(i).getGender() + ",");
				bw.write(list.get(i).getAge() + ",");
				bw.write(list.get(i).getPhone() + ",");
				bw.write(list.get(i).getAddress());
				bw.newLine();
				*/
			}
			
			bw.flush();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
