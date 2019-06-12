package com.barunsw.ojt.gtkim.day17;

import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Severity;

/*
 *  Image를 관리하는 클래스
 */

public final class ImageFactory {
	private static final Logger LOGGER = LogManager.getLogger(ImageFactory.class);

	public static final ImageIcon backgroundImageIcon;
	
	public static final ImageIcon[] mpuImageIcon	= new ImageIcon[4];
	public static final ImageIcon[] salcImageIcon 	= new ImageIcon[4];
	public static final ImageIcon[] srguImageIcon 	= new ImageIcon[4];
	
	static {
		backgroundImageIcon = new ImageIcon(Thread.currentThread()
				.getContextClassLoader().getResource("images/tamms/background.png"));
		
//		backgroundImageIcon = new ImageIcon(ImageFactory.class.getClassLoader()
//				.getResource("images/tamms/background.png"));
		for (int i = 0; i < 4; ++i) {
			String severity = null;
			
			if (i == Severity.CRITICAL) {
				severity = "critical.png";
			}
			else if (i == Severity.MAJOR) {
				severity = "major.png";
			}
			else if (i == Severity.MINOR) {
				severity = "minor.png";
			}
			else {
				severity = "normal.png";
			}
			
			mpuImageIcon[i] = new ImageIcon(Thread.currentThread()
					.getContextClassLoader().getResource("images/tamms/MPU_" + severity));		

			salcImageIcon[i] = new ImageIcon(Thread.currentThread()
					.getContextClassLoader().getResource("images/tamms/SALC_" + severity));		

			srguImageIcon[i] = new ImageIcon(Thread.currentThread()
					.getContextClassLoader().getResource("images/tamms/SRGU_" + severity));			
		}
	}	
}
