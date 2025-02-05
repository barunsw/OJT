package com.barunsw.ojt.cjs.day12;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestPanel extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestPanel.class);

	private GridBagLayout gridBagLayout = new GridBagLayout();

	private JTextField jTextField_Time = new JTextField();

	private JButton jButton_Click = new JButton("Click");
	private JButton jButton_Date = new JButton("Date");

	private DialogTest dialogTest;

	public TestPanel() {
		try {
			initComponent();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void initComponent() throws Exception {
		this.setLayout(gridBagLayout);

		this.add(jTextField_Time, new GridBagConstraints(0, 0, 2, 1,
				1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5),
				0, 0));

		this.add(jButton_Click, new GridBagConstraints(0, 1, 1, 1,
				1.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
				new Insets(5, 5, 5, 5),
				0, 0));

		this.add(jButton_Date, new GridBagConstraints(1, 1, 1, 1, 
				1.0, 0.0, 
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
				new Insets(5, 5, 5, 5), 
				0, 0));

		jButton_Click.addActionListener(new TestPanel_jButton_Click_ActionListener(this));

		jButton_Date.addActionListener(new TestPanel_jButton_Date_ActionListener(this));
	}

	private void doSomething() {
		Thread doSomethingThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				LOGGER.debug("+++doSomething");

				for (int i = 0; i < 5; i++) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
					}
				}
				LOGGER.debug("---doSomething");
			}
		});
		doSomethingThread.start();
	}

	void jButton_Click_actionPerformed(ActionEvent e) {
		LOGGER.debug("+++ click");
		
		LOGGER.debug("+++dialogTest");
		
		dialogTest = new DialogTest(null);
		dialogTest.setVisible(true);
		
		LOGGER.debug("---dialogTest");
		
		SwingWorker worker = new SwingWorker() {
			@Override
			protected Object doInBackground() throws Exception {
				LOGGER.debug("+++doSomething");
				
				for(int i =0; i<10; i++) {
					try {
						Thread.sleep(500);
						
						setProgress(10 *(i+1));
					} 
					catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
					}
				}
				LOGGER.debug("---doSomething");
				return null;
			}
			@Override
			protected void done() {
				// TODO Auto-generated method stub
				super.done();
				
				if (dialogTest != null)
					dialogTest.setVisible(false);
			}
		};
		worker.addPropertyChangeListener(dialogTest);
		worker.execute();
	}
	void jButton_Date_actionPerformed(ActionEvent e) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
		
		jTextField_Time.setText(sdf.format(Calendar.getInstance().getTime()));
	}
}

class TestPanel_jButton_Click_ActionListener implements ActionListener{
	private TestPanel adaptee;

	public TestPanel_jButton_Click_ActionListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton_Click_actionPerformed(e);
	}
}

class TestPanel_jButton_Date_ActionListener implements ActionListener {
	private TestPanel adaptee;
	
	public TestPanel_jButton_Date_ActionListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton_Date_actionPerformed(e);
	}
}
