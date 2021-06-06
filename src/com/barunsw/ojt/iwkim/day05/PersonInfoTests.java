package com.barunsw.ojt.iwkim.day05;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.iwkim.common.AddressBookInterface;
import com.barunsw.ojt.iwkim.common.PersonInfo;
import com.barunsw.ojt.iwkim.day05.service.CliService;

public class AddressBookTests {
	private static Logger LOG = LogManager.getLogger(AddressBookTests.class);
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		AddressBookInterface objFile = new ObjectFileAddressBookImpl();
		CliService cService = new CliService();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		PersonInfo person; 
		try {
			while (true) {
				cService.start();
				String input = sc.next();
				
				switch (input.toLowerCase()) {
					case "c" :
						person = cService.insertPersonInfo();
						objFile.insertPerson(person);
						System.out.println("입력이 완료되었습니다.");
						break;
					case "u" :
						break;
					case "d" :
						break;
					case "rf":
						break;
					case "rd":
						break;
					
				}
			}
		}
		catch (Exception e) {
			LOG.info(e.getMessage(), e);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
