package com.barunsw.ojt.jyb.day3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddressBookFilePractice {
	private static final Logger logger = LoggerFactory.getLogger(AddressBookFilePractice.class);

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ArrayList<AddressBook> addressBookList = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			AddressBook addressBook = new AddressBook(); // 새 객체 생성

			System.out.print("이름을 입력하세요 : ");
			String name = br.readLine();
			addressBook.name = name;

			System.out.print("전화번호를 입력하세요 : ");
			String phoneNumber = br.readLine();
			addressBook.phoneNumber = phoneNumber;

			System.out.print("주소를 입력하세요 : ");
			String address = br.readLine();
			addressBook.address = address;

			addressBookList.add(addressBook);
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/addressBook.txt"))) {
			int size = addressBookList.size();
			for (int i = 0; i < size; i++) {
				AddressBook ab = addressBookList.get(i);
				if (i < size - 1) {
					bw.write("이름 : " + ab.name + ", 전화번호 : " + ab.phoneNumber + ", 주소 : " + ab.address + "\n");
				} else {
					bw.write("이름 : " + ab.name + ", 전화번호 : " + ab.phoneNumber + ", 주소 : " + ab.address);
				}

			}
		} catch (IOException e) {
			logger.error("주소록 저장 중 오류 발생", e);
		} finally {
			br.close();
		}

	}

}
