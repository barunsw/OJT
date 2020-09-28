package com.barunsw.ojt.phs.day11;

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

public class ThreadPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(ThreadPanel.class);
	
	private JTextField jTextField_Time = new JTextField();
	
	JButton jButton_Download = new JButton("다운로드");
	JButton jButton_Date = new JButton("현재시간");
	
	private ProgressDialog progressDialog;
	
	public ThreadPanel() {
		try {
			initComponent();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	private void initComponent() throws Exception {
		this.setLayout(new GridBagLayout());

		this.add(jTextField_Time, 
				new GridBagConstraints(0, 0, 2, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(10, 10, 10, 10),
						0, 0));
		
		this.add(jButton_Download, 
				new GridBagConstraints(0, 1, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(10, 10, 10, 10),
						0, 0));
		
		this.add(jButton_Date, 
				new GridBagConstraints(1, 1, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(10, 10, 10, 10),
						0, 0));
		
		jButton_Download.addActionListener( new ThreadPanel_jButton_ActionListener(this) );

		jButton_Date.addActionListener( new ThreadPanel_jButton_ActionListener(this) );
	}
	
	void jButton_Download_Action() {
		progressDialog = new ProgressDialog(null);
		progressDialog.setVisible(true);
		SwingWorker worker = new SwingWorker() {
			@Override
			protected Object doInBackground() throws Exception {
				for (int i = 1; i <= 10; i++) {
					try {
						Thread.sleep(1000);
						setProgress(10 * (i));
					}
					catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
					}
				}
				return null;
			}
			
			@Override
			protected void done() {
				super.done();
				if (progressDialog != null)
					progressDialog.setVisible(false);
			}
		};
		
		worker.addPropertyChangeListener(progressDialog);
		worker.execute();
	}
	
	void jButton_Date_Action() {
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
		
		jTextField_Time.setText(sdf.format(Calendar.getInstance().getTime()));
	}
}

class ThreadPanel_jButton_ActionListener implements ActionListener {
	private ThreadPanel adaptee;
	
	public ThreadPanel_jButton_ActionListener(ThreadPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ( e.getSource() == adaptee.jButton_Download ) {
			adaptee.jButton_Download_Action();
		}
		else if ( e.getSource() == adaptee.jButton_Date ) {
			adaptee.jButton_Date_Action();
		}
		
	}
}