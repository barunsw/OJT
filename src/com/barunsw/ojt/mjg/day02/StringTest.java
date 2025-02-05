package com.barunsw.ojt.mjg.day02;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StringTest {
	private static final Logger LOGGER = LogManager.getLogger(StringTest.class);
	
	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {		
		String aaa = "Hello world";
		char charValue = aaa.charAt(4);
		
		LOGGER.debug(charValue);
		
		String nameOne = new String("문종근");
		String nameTwo = "문종근";
		String nameFour = "MJG";
		
//		LOGGER.debug(nameOne.equals(nameThree));
		
//		LOGGER.debug(nameThree.length());
//		LOGGER.debug(nameFdour.length());
		
		String name = "MOONJONGGEUN";
		
		LOGGER.debug(name.substring(4, 8));
		LOGGER.debug(name.substring(3));
		
		final String ccc = "";
		final String ddd = null;
		if (ccc.isEmpty()) {
			LOGGER.debug("CCC는 빈 스트링 : 1");
		}
		if (ccc.length() > 0) {
			LOGGER.debug("CCC는 빈 스트링 : 2");
		}
		if (ccc.length() == 0) {
			LOGGER.debug("CCC는 빈 스트링 : 3");
		}
		if (ddd == null) {
			LOGGER.debug("ddd is null");
		}
		
		
		String original = "Moon JOng GeUn";
		String lowerCaseName = original.toLowerCase();
		String upperCaseName = original.toUpperCase();
		String comparison = "mOOn jOnG geUN";
		int iterations = 1_000;

        // Measure time for equalsIgnoreCase
        long startEqualsIgnoreCase = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            boolean isEqualIgnoreCase = original.equalsIgnoreCase(comparison);
        }
        long endEqualsIgnoreCase = System.nanoTime();
        long durationEqualsIgnoreCase = (endEqualsIgnoreCase - startEqualsIgnoreCase) / 1_000_000;

        // Measure time for toUpperCase comparison
        long startToUpperCase = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            boolean isEqualUpperCase = original.toUpperCase().equals(comparison.toUpperCase());
        }
        long endToUpperCase = System.nanoTime();
        long durationToUpperCase = (endToUpperCase - startToUpperCase) / 1_000_000;

        // Measure time for toLowerCase comparison
        long startToLowerCase = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            boolean isEqualLowerCase = original.toLowerCase().equals(comparison.toLowerCase());
        }
        long endToLowerCase = System.nanoTime();
        long durationToLowerCase = (endToLowerCase - startToLowerCase) / 1_000_000;

        // Log results
        LOGGER.debug("equalsIgnoreCase duration: {} ms", durationEqualsIgnoreCase);
        LOGGER.debug("toUpperCase comparison duration: {} ms", durationToUpperCase);
        LOGGER.debug("toLowerCase comparison duration: {} ms", durationToLowerCase);
		
		
		LOGGER.debug(lowerCaseName);
		LOGGER.debug(upperCaseName);
		
        boolean isEqualIgnoreCase = original.equalsIgnoreCase(comparison);

        LOGGER.debug(isEqualIgnoreCase);
		
        String abc = "\t\t\t\n\n\r\n   aa  asd  a  \t\n";
        LOGGER.debug("Original String: '{}'", abc);
        LOGGER.debug("Trimmed String: '{}'", abc.trim());
        
        Person person = new Person(26, "문종근");
        
        String formattedInfo = String.format("Person Info - Name: %s, Age: %d", person.getName(), person.getAge());
        LOGGER.debug(formattedInfo);
        String concatInfo = String.format("Person Info - Name: " + person.getName() + ", Age: " + person.getAge());
        LOGGER.debug(concatInfo);
        
        String ttt = "TTTTTT--TTTT1234TTT##TOOOOO";
        LOGGER.debug(ttt.replace("O", "T"));    
        LOGGER.debug(ttt.replace("S", "T"));
        LOGGER.debug(ttt.replace("TO", "SS"));
        
        boolean exactMatch = ttt.matches("TTTTTT--TTTT1234TTT##TOOOOO");
        LOGGER.debug(exactMatch);
        
        boolean containsNumbers = ttt.matches(".*\\d.*");
        LOGGER.debug(containsNumbers);

        boolean startsWithT = ttt.matches("T.*");
        LOGGER.debug(startsWithT);
        
        String input = "apple banana apple grape";

//        String result = input.replaceAll("\\w+", "fruit");
        String result = input.replaceFirst("\\w+", "fruit");
        
        LOGGER.debug(result);
        
        LOGGER.debug(input.substring(6));
        LOGGER.debug(input.substring(13));
//        LOGGER.debug(input.substring(200));
        
        
//        LOGGER.debug(input.indexOf('a'));
//        LOGGER.debug(input.indexOf("banana"));
//        LOGGER.debug(input.indexOf('z'));
//        LOGGER.debug(input.indexOf('a', 1));
        
        LOGGER.debug(input.lastIndexOf('a'));
        LOGGER.debug(input.lastIndexOf("banana"));
        LOGGER.debug(input.lastIndexOf('z'));
        LOGGER.debug(input.lastIndexOf('a', 6));
        
        
        LOGGER.debug(input.contains("apple"));
        LOGGER.debug(input.contains("orange"));
        
        
	}
}
