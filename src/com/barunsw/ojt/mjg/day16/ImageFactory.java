

package com.barunsw.ojt.mjg.day16;

import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImageFactory {
	private static final Logger LOGGER = LogManager.getLogger(ImageFactory.class);
	
	public static ImageIcon backgroundImageIcon;
	
	public static ImageIcon mpuNormalImageIcon;
	public static ImageIcon mpuMinorImageIcon;
	public static ImageIcon mpuMajorImageIcon;
	public static ImageIcon mpuCriticalImageIcon;
	
	public static ImageIcon salcNormalImageIcon;
	public static ImageIcon salcMinorImageIcon;
	public static ImageIcon salcMajorImageIcon;
	public static ImageIcon salcCriticalImageIcon;
	
	public static ImageIcon srguNormalImageIcon;
	public static ImageIcon srguMinorImageIcon;
	public static ImageIcon srguMajorImageIcon;
	public static ImageIcon srguCriticalImageIcon;
	
	public static ImageIcon[] mpuImageIcon = new ImageIcon[4];

	static {
		LOGGER.debug("static 블럭 실행");
		backgroundImageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/background.png"));
		
		mpuNormalImageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/MPU_normal.png"));
		mpuMinorImageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/MPU_minor.png"));
		mpuMajorImageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/MPU_major.png"));
		mpuCriticalImageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/MPU_critical.png"));
		
		salcNormalImageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SALC_normal.png"));
		salcMinorImageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SALC_minor.png"));
		salcMajorImageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SALC_major.png"));
		salcCriticalImageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SALC_critical.png"));
		
		srguNormalImageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SRGU_normal.png"));
		srguMinorImageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SRGU_minor.png"));
		srguMajorImageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SRGU_major.png"));
		srguCriticalImageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SRGU_critical.png"));
	}
}
