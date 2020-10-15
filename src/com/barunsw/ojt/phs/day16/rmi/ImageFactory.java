package com.barunsw.ojt.phs.day16.rmi;

import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImageFactory {
	private static final Logger LOGGER = LogManager.getLogger(ImageFactory.class);
	
	public static ImageIcon backgroundImageIcon;
	
	public static ImageIcon mpuNormalImageIcon;
	public static ImageIcon salcNormalImageIcon;
	public static ImageIcon srguNormalImageIcon;
	
	//배열을 이용한 Icon
	public static ImageIcon[] mpuImageIcon = new ImageIcon[4];
	public static ImageIcon[] salcImageIcon = new ImageIcon[4];
	public static ImageIcon[] srguImageIcon = new ImageIcon[4];

	static {
		LOGGER.debug("static 블럭 실행");
		backgroundImageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/background.png"));
		//normal
		mpuImageIcon[3] = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/MPU_normal.png"));
		salcImageIcon[3] = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SALC_normal.png"));
		srguImageIcon[3] = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SRGU_normal.png"));
		//minor
		mpuImageIcon[2] = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/MPU_minor.png"));
		salcImageIcon[2] = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SALC_minor.png"));
		srguImageIcon[2] = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SRGU_minor.png"));
		//major
		mpuImageIcon[1] = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/MPU_major.png"));
		salcImageIcon[1] = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SALC_major.png"));
		srguImageIcon[1] = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SRGU_major.png"));
		//critical
		mpuImageIcon[0] = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/MPU_critical.png"));
		salcImageIcon[0] = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SALC_critical.png"));
		srguImageIcon[0] = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SRGU_critical.png"));
		
	
	}
}
