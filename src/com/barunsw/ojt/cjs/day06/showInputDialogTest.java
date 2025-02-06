package com.barunsw.ojt.cjs.day06;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.phs.day06.TestFrame;

public class showInputDialogTest {
	private static final Logger log = LogManager.getLogger(TestFrame.class);

	public static void main(String[] args) {
		String inputWord = "";

		while (true) {
			inputWord = JOptionPane.showInputDialog("입력");
			log.debug(inputWord);

			if (inputWord.equals("-1") )
				break;
		}
	}

}
