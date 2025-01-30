package com.barunsw.ojt.gtkim.day11;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
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

	private JTextField jTextField_Time = new JTextField();

	private JButton jButton_Click = new JButton("Click");
	private JButton jButton_Date  = new JButton("Date");

	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	private ProgressDialog progressDialog;
	
	public TestPanel() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() {
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
		
		jButton_Click.addActionListener(
				new TestPanel_jButton_Click_ActionListener(this));

		jButton_Date.addActionListener(
				new TestPanel_jButton_Date_ActionListener(this));
	
	}
	
	private void doSomething() {
		LOGGER.debug("doSomething 메소드 진입");
		
		Thread doSomethingThread = new Thread(new Runnable() {
			public void run() {
				LOGGER.debug("이벤트 처리 시작~");

				for (int i = 0; i < 5; i++) {
					try {
						Thread.sleep(500);						
						progressDialog.setProgress(20 * (i+1));
					}
					catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}			
				progressDialog.setVisible(false);
				LOGGER.debug("이벤트 처리 끝!");
			}
		});		
		doSomethingThread.start();
		

		LOGGER.debug("doSomething 메소드 끝");
	}
	
	void blocking() {
		try {
			Thread.sleep(3000L);
		}
		catch (InterruptedException ie) {
			LOGGER.error(ie.getMessage(), ie);
		}
	}
	
	void jButton_Click_actionPerformed(ActionEvent e) {
		LOGGER.debug("버튼클릭 메소드 시작");
		LOGGER.debug(String.format("현재 activeCount : %d ", Thread.activeCount()));;
		//LOGGER.debug(String.format("ProgressDialog 생성 전 activeCount : %d ", Thread.activeCount()));;		
		progressDialog = new ProgressDialog(null);
		progressDialog.setVisible(true);
		//LOGGER.debug(String.format("ProgressDialog 생성 후 activeCount : %d ", Thread.activeCount()));;
		
		SwingWorker worker = new SwingWorker() {
			@Override
			protected Object doInBackground() throws Exception {
				// doSomething의 내용이 여기에 기술
				LOGGER.debug("+++ doSomething");
				
				for (int i = 0; i < 2; i++) {
					try {
						Thread.sleep(1000);
						
						setProgress(10 * (i+1));
					}
					catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}				
				LOGGER.debug("--- doSomething");
				
				return null;
			}
			
			@Override
			protected void done() {
				// TODO Auto-generated method stub
				super.done();
				
				if (progressDialog != null) {
					progressDialog.dispose();
				}
				LOGGER.debug("Done! 호출됨");
			}
		};
					
		worker.addPropertyChangeListener(progressDialog);
		worker.execute();
	
		//this.remove(progressDialog);
/*	
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
				progressDialog.setVisible(false);
				
				LOGGER.debug("--- doSomething");
				LOGGER.debug(progressDialog);
			}
		});		
		//doSomethingThread.start();
		//doSomething();
	
		*/
		/*
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				doSomething();
			}
		});
*/
		//LOGGER.debug(String.format("activeCount : %d ", Thread.activeCount()));
		
/*
		if (SwingUtilities.isEventDispatchThread()) {
			LOGGER.debug("this is dispatchThread");
		} 
		else {
			LOGGER.debug("this is not " + "dispatchThread");
		}
		
		new Thread(new Runnable() {
			public void run() {
				try {
					LOGGER.debug("invokeAndWait 수행전");
					
					SwingUtilities.invokeAndWait(new Runnable() {
						@Override
						public void run() {
							doSomething();
						}
					});

					LOGGER.debug("invokeAndWait 수행후");
				}
				catch (InvocationTargetException e) {
					LOGGER.error(e.getMessage(), e);
				} 
				catch (InterruptedException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}).start();
		
		LOGGER.debug("10초 대기 이벤트  진입");
		blocking();
		LOGGER.debug("10초 대기 이벤트 끝");
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
		LOGGER.debug("버튼 클릭 메소드 끝");
	}
	
	void jButton_Date_actionPerformed(ActionEvent e) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
		
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
