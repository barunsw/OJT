package com.barunsw.ojt.sjcha.day10;

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
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);

	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	// 현재 time을 띄울 textfield
	private JTextField jTextField_Time = new JTextField();
	
	// progress 바를 보여줄 버튼
	private JButton jButton_Click = new JButton("Click");
	// 현재 시간과 날짜를 보여줄 버튼
	private JButton jButton_Date = new JButton("Date");
	
	private ProgressDialog progressDialog;
	
	public TestPanel() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		this.setLayout(gridBagLayout);

		this.add(jTextField_Time, 
				new GridBagConstraints(0, 0, 2, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		this.add(jButton_Click, 
				new GridBagConstraints(0, 1, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		this.add(jButton_Date, 
				new GridBagConstraints(1, 1, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		// Click 버튼을 눌렀을 경우
		jButton_Click.addActionListener(new TestPanel_jButton_Click_ActionListener(this));

		// Date 버튼을 눌렀을 경우
		jButton_Date.addActionListener(new TestPanel_jButton_Date_ActionListener(this));
	}
	
	private void doSomething() {
		Thread doSomethingThread = new Thread(new Runnable() {
			public void run() {
				LOGGER.debug("+++ doSomething");

				for (int i = 0; i < 5; i++) {
					try {
						Thread.sleep(1000);
						
						progressDialog.setProgress(20 * (i+1));
					}
					catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}
				
				LOGGER.debug("--- doSomething");
			}
		});
		
		doSomethingThread.start();
	}
	
	// Click 버튼을 눌렀을 경우
	void jButton_Click_ActionListener(ActionEvent e) {
		LOGGER.debug("progress dialog");
		progressDialog = new ProgressDialog(null);
		progressDialog.setVisible(true);
		
		SwingWorker worker = new SwingWorker() {
			@Override
			protected Object doInBackground() throws Exception {
				// doSomething의 내용이 여기에 기술
				LOGGER.debug("+++ doSomething");
			
				for (int i = 0; i < 10; i++) {
					try {
						Thread.sleep(1000);
						
						// 10, 20, 30 10의 배수로 증가한다. 
						setProgress(10 * (i+1));
					}
					catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}
				
				LOGGER.debug("--- doSomething");
				
				return null;
			}
			
			// swingworker가 종료되면 실행
			@Override
			protected void done() {
				// TODO Auto-generated method stub
				super.done();
				
				if (progressDialog != null) {
					progressDialog.setVisible(false);
				}
			}
		};
		
		worker.addPropertyChangeListener(progressDialog);
		worker.execute();
		
		//LOGGER.debug("--- click");
	}
	
	// Date 버튼을 눌렀을 경우
	void jButton_Date_ActionListener(ActionEvent e) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
		
		jTextField_Time.setText(sdf.format(Calendar.getInstance().getTime()));
	}
}

class TestPanel_jButton_Click_ActionListener implements ActionListener {
	private TestPanel adaptee;
	
	public TestPanel_jButton_Click_ActionListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton_Click_ActionListener(e);
	}
}

class TestPanel_jButton_Date_ActionListener implements ActionListener {
	private TestPanel adaptee;
	
	public TestPanel_jButton_Date_ActionListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton_Date_ActionListener(e);
	}
}