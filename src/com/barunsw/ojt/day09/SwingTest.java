package com.barunsw.ojt.day09;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
<<<<<<< HEAD

import javax.swing.UIManager;

public class SwingTest {
	public static void main(String[] args) {
		try {
			// Look and Feel UIManager를 통해 ui 쉽게 변경 가능
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception ex) {
		}
		
=======
import java.io.FileReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwingTest {
	private static Logger LOGGER = LoggerFactory.getLogger(SwingTest.class);

	public static Properties properties = new Properties();
	
	private static void loadProperties(String configPath) throws Exception {
		Reader reader = new FileReader(configPath); 
		properties.load(reader);
		
		LOGGER.debug("========== " + configPath + " ==========");
		
		Iterator<Object> keySet = properties.keySet().iterator();
		while (keySet.hasNext()) {
			Object key = keySet.next();
			Object value = properties.get(key);
			
			LOGGER.debug(String.format("%s = %s", key, value));
		}
		
		LOGGER.debug("==============================");
		//Reader reader = Resources.getResourceAsReader(resource);
		//properties.load(reader);
	}
	
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.err.println("Usage: java SwingTest config_path");
			System.exit(1);
		}
		
		try {
			// Look and Feel UIManager를 통해 ui 쉽게 변경 가능
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception ex) {
		}
		
		loadProperties(args[0]);

>>>>>>> branch 'master' of https://github.com/barunsw/OJT.git
		TestFrame frame = new TestFrame();
		//frame.setSize(100, 100);
		// 1) 표시할 위치 지정
		//frame.setLocation(new Point(100, 100));
		// 2) 표시할 크기 지정
		//frame.setSize(new Dimension(300, 300));
		
		// 화면의 전체 크기
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xPos = (scrDim.width - TestFrame.WIDTH) / 2;
		int yPos = (scrDim.height - TestFrame.HEIGHT) / 2;
		
		// 1)과 2)를 동시에 처리
		frame.setBounds(new Rectangle(xPos, yPos, TestFrame.WIDTH, TestFrame.HEIGHT));
		// 3) 프레임 표시
		frame.setVisible(true);
	}
}
