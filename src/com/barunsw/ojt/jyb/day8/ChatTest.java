package com.barunsw.ojt.jyb.day8;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class ChatTest {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Window Name");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setContentPane(new Chat());

		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();

		int xPos = (scrDim.width - AddressBook.WIDTH) / 2;
		int yPos = (scrDim.height - AddressBook.HEIGHT) / 2;

		frame.setBounds(new Rectangle(xPos, yPos, AddressBook.WIDTH, AddressBook.HEIGHT));

		frame.setVisible(true);


	}

}
