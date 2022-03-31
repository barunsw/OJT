package com.barunsw.ojt.cjs.day03;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OutputStreamTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(OutputStreamTest.class);

	public static void main(String[] args) {

		File readFile = new File("data/day03/cjs/address.dat");
		File writeFile = new File("data/day03/cjs/writeFile.txt");

		// List<Person> 객체를 만들고, ObjectInputStream으로 읽어서 Person 정보를 넣고
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(writeFile, true));
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(readFile))) {
			Object o;

			while ((o = ois.readObject()) != null) {
				LOGGER.debug("--- oneObject:" + o);
				if (o instanceof Person) {
					Person p = (Person) o;
					oos.writeObject(p);
				}
			}
		} catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		} catch (EOFException eofe) {
		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		} catch (ClassNotFoundException cnfe) {
			LOGGER.error(cnfe.getMessage(), cnfe);
		}

		// BufferedWriter를 통해 List<Person> 객체가 가지고 있는 정보를 txt 파일에 write한다. 
		// 단, address.txt와 동일한 결과의 txt파일이 만들어져야 한다.
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(writeFile, true));
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(readFile))) {
			Object o;

			while ((o = ois.readObject()) != null) {
				LOGGER.debug("--- oneObject:" + o);
				if (o instanceof Person) {
					Person p = (Person) o;
					bw.write(p.toString());
				}
			}
		} catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		} catch (EOFException eofe) {
		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		} catch (ClassNotFoundException cnfe) {
			LOGGER.error(cnfe.getMessage(), cnfe);
		}
	}

}
