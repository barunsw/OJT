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
