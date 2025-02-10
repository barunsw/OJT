package com.barunsw.ojt.jyb.day11;

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

	private JTextField jTextField_Time = new JTextField();

	private JButton jButton_Click = new JButton("Click");
	private JButton jButton_Date = new JButton("Date");

	private ProgressDialog progressDialog; // 작업 진행 상태를 표시

	public TestPanel() {
		try {
			initComponent();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initComponent() throws Exception { // UI 초기화
		this.setLayout(gridBagLayout);

		this.add(jTextField_Time, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

		this.add(jButton_Click, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

		this.add(jButton_Date, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

		jButton_Click.addActionListener(new TestPanel_jButton_Click_ActionListener(this));

		jButton_Date.addActionListener(new TestPanel_jButton_Date_ActionListener(this));
	}

	private void doSomething() { //백그라운드에서 실행될 작업을 정의
		Thread doSomethingThread = new Thread(new Runnable() { //새 Thread 객체 생성. Runnable을 익명 클래스로 구현하여 병렬 실행되는 작업 정의
			public void run() { //스레드 실행 시 실행되는 코드 블록
				LOGGER.debug("+++ doSomething");

				for (int i = 0; i < 5; i++) {
					try {
						Thread.sleep(1000); //1초 대기

						progressDialog.setProgress(20 * (i + 1));
						//i = 0 : 20 * (0 + 1) = 20%
						//i = 1 : 20 * (1 + 1) = 40%
						//1초마다 20%씩 증가하여 5초 후 100% 완료
					} catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}

				LOGGER.debug("--- doSomething");
			}
		});

		doSomethingThread.start(); //새로운 스레드 시작 -> run()메소드의 코드가 백그라운드에서 실행되도록 함
	}

	void jButton_Click_actionPerformed(ActionEvent e) {
		LOGGER.debug("+++ click");

		LOGGER.debug("+++ progressDialog visible");

		progressDialog = new ProgressDialog(null); //부모 창이 없는 상태에서 다이얼로그 생성
		progressDialog.setVisible(true); //진행 상태 표시창을 화면에 표시 -> 이 호출 이후에는 다이얼로그가 사용자 입력을 막고 대기 상태가 됨

		LOGGER.debug("--- progressDialog visible");

		SwingWorker worker = new SwingWorker() { //백그라운드에서 실행할 작업 정의
			//SwingWorker는 UI 스레드를 차단하지 않고 비동기 작업을 실행할 수 있음
			@Override
			protected Object doInBackground() throws Exception { //백그라운드에서 실행될 코드 정의
				// doSomething의 내용이 여기에 기술
				LOGGER.debug("+++ doSomething");

				for (int i = 0; i < 10; i++) {
					try {
						Thread.sleep(1000); //1초 대기

						setProgress(10 * (i + 1)); //1초마다 10%씩 증가
					} catch (Exception ex) {
						LOGGER.error(ex.getMessage(), ex);
					}
				}

				LOGGER.debug("--- doSomething");

				return null;
			}

			@Override
			protected void done() { //백그라운드 작업이 완료된 후 자동으로 실행
				// TODO Auto-generated method stub
				super.done();

				if (progressDialog != null)
					progressDialog.setVisible(false); //작업 완료 후 진행 상태 표시창을 닫음 -> 10초 후 자동으로 다이얼로그 닫힘
			}
		};

		worker.addPropertyChangeListener(progressDialog); //진행 상태가 변경될 때 감지하여 반영할 수 있도록 이벤트 리스너 등록
		worker.execute(); //백그라운드 작업 시작 -> SwingWorker는 execute()를 호출해야 실행됨

		// doSomething();
		/*
		 * SwingUtilities.invokeLater(new Runnable() {
		 * 
		 * @Override public void run() { doSomething(); } });
		 */
		/*
		 * try { SwingUtilities.invokeAndWait(new Runnable() {
		 * 
		 * @Override public void run() { doSomething(); } }); } catch (Exception ex) {
		 * LOGGER.error(ex.getMessage(), ex); }
		 */
		LOGGER.debug("--- click");
	}

	void jButton_Date_actionPerformed(ActionEvent e) {
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