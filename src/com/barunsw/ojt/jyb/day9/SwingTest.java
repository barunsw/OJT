package day9;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.UIManager;

public class SwingTest {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception ex) {
			
		}

		TestFrame frame = new TestFrame();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int xPosition = (screenSize.width - TestFrame.WIDTH) / 2;
		int yPosition = (screenSize.height - TestFrame.HEIGHT) / 2;

		// x위치, y위치, 프레임 가로길이, 프레임 세로길이
		frame.setBounds(new Rectangle(xPosition, yPosition, TestFrame.WIDTH, TestFrame.HEIGHT));

		frame.setVisible(true);
	}
}