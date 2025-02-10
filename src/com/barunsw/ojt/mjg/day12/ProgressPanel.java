package com.barunsw.ojt.mjg.day12;

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

public class ProgressPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(ProgressPanel.class);

	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	private JTextField jTextField_Time = new JTextField();
	
	private JButton jButton_Click = new JButton("Click");
	private JButton jButton_Date = new JButton("Date");
	
	private ProgressDialog progressDialog;
	
	public ProgressPanel() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		this.setLayout(gridBagLayout);

		this.add(jTextField_Time, new GridBagConstraints(
			0, 0, 2, 1, 1.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(5, 5, 5, 5), 0, 0
		));
		
		this.add(jButton_Click, new GridBagConstraints(
			0, 1, 1, 1, 1.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(5, 5, 5, 5), 0, 0
		));
		
		this.add(jButton_Date, new GridBagConstraints(
			1, 1, 1, 1, 1.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(5, 5, 5, 5), 0, 0
		));
		
		jButton_Click.addActionListener(new TestPanel_jButton_Click_ActionListener(this));

		jButton_Date.addActionListener(new TestPanel_jButton_Date_ActionListener(this));
	}
	
	private void doSomething() {
		Thread doSomethingThread = new Thread(new Runnable() {
			public void run() {
				LOGGER.debug("+++ doSomething");

				for (int i = 0; i < 5; i++) {
					try {
						Thread.sleep(1000);
						
						progressDialog.setProgress(20 * (i + 1));
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
	
	void jButton_Click_actionPerformed(ActionEvent e) {
		LOGGER.debug("+++ click");

		LOGGER.debug("+++ progressDialog visible");

		progressDialog = new ProgressDialog(null);
		progressDialog.setVisible(true);
		
		LOGGER.debug("--- progressDialog visible");
		
		SwingWorker worker = new SwingWorker() {
			@Override
			protected Object doInBackground() throws Exception {
				LOGGER.debug("+++ doSomething");

				for (int i = 0; i < 10; i++) {
					try {
						Thread.sleep(1000);
						
						setProgress(10 * (i + 1));
					}
					catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}
				
				LOGGER.debug("--- doSomething");
				
				return null;
			}
			
			// 모든 작업이 완료되었을 때 UI 스레드에서 호출, 진행 상태 다이얼로그 종료
			@Override
			protected void done() {
				// TODO Auto-generated method stub
				super.done();
				
				if (progressDialog != null)
					progressDialog.setVisible(false);
			}
		};
		
		// 진행 상태 실시간으로 반영
		worker.addPropertyChangeListener(progressDialog);
		
		// 비동기 작업 실행
		worker.execute();
		
		doSomething();
		/*
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				doSomething();
			}
		});
		*/
		/*
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					doSomething();
				}
			});
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		*/
		LOGGER.debug("--- click");
	}
	
	void jButton_Date_actionPerformed(ActionEvent e) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
		
		jTextField_Time.setText(sdf.format(Calendar.getInstance().getTime()));
	}
}

class TestPanel_jButton_Click_ActionListener implements ActionListener {
	private ProgressPanel adaptee;
	
	public TestPanel_jButton_Click_ActionListener(ProgressPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton_Click_actionPerformed(e);
	}
}

class TestPanel_jButton_Date_ActionListener implements ActionListener {
	private ProgressPanel adaptee;
	
	public TestPanel_jButton_Date_ActionListener(ProgressPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton_Date_actionPerformed(e);
	}
}