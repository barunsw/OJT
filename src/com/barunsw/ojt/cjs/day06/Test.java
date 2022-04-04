package com.barunsw.ojt.cjs.day06;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.yjkim.day07.LayoutFrame;

public class Test {
	private static Logger log = LoggerFactory.getLogger(FormFrame.class);

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} 
		catch (Exception ex) {
			log.error(ex.getMessage() + ex);
		}
		new FormFrame();
	}

}
