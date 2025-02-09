package day9;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFrame extends JFrame {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestFrame.class);

	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;

	private TestPanel testPanel = new TestPanel();

	public TestFrame() {
		try {
			initComponent();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void initComponent() throws Exception {
		this.setTitle("Address Book");

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.setContentPane(testPanel);

		this.addWindowListener(new TestFrame_this_WindowAdapter(this));
	}

	void windowClosing(WindowEvent e) { //창을 닫을 때 호출
		LOGGER.debug("windowClosing");

		int confirmResult = JOptionPane.showConfirmDialog(TestFrame.this, "종료하시겠습니까?", "종료",
				JOptionPane.OK_CANCEL_OPTION); //대화 상자

		if (confirmResult == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
}

class TestFrame_this_WindowAdapter extends WindowAdapter {
	private TestFrame testAdapter;

	public TestFrame_this_WindowAdapter(TestFrame testAdapter) {
		this.testAdapter = testAdapter;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		testAdapter.windowClosing(e);
	}
}