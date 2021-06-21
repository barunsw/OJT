package com.barunsw.ojt.iwkim.day15;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel extends JPanel implements EventListener {
	private static Logger LOGGER = LogManager.getLogger(TestPanel.class);

	public static EventQueueWorker eventQueueWorker = new EventQueueWorker();

	private GridBagLayout gridBagLayout = new GridBagLayout();
	private JToggleButton jToggleButton_Connection = new JToggleButton("접속");


	private JTextField jTextField_Name	    = new JTextField(10);
	private JTextField jTextField_Message    = new JTextField(20); 
	public JTextArea jTextArea_ToTalMessge;

	private JButton jButton_Send = new JButton("전송");

	private JPanel jPanel_InputField 		    = new JPanel();
	private JScrollPane jScrollPane_TotalMessage = new JScrollPane();

	private ClientInterface clientIf;
	private ServerInterface serverIf = null;
	public TestPanel() {
		try {
			LOGGER.debug("TestPanel 생성자");
			initEventQueueWorker();
			initConnectRMI();
			initComponent();
			initButtonEvent();
		} catch(Exception ex) {

		}
	}

	private void initEventQueueWorker() {
		eventQueueWorker.addEventListener(this);

		eventQueueWorker.start();
	}

	private void initConnectRMI() {
		try {
			clientIf = new ClientImpl();

			Registry registry;
			registry = LocateRegistry.getRegistry(ServerMain.RMI_PORT);

			Remote remote = registry.lookup("CHAT");
			if (remote instanceof ServerInterface) {
				serverIf = (ServerInterface)remote;
			}
		} catch (RemoteException re) {
			LOGGER.error(re.getMessage(), re);
		} catch (NotBoundException nbe) {
			LOGGER.error(nbe.getMessage(), nbe);
		}

		//		
	}

	private void initComponent() {
		this.setLayout(gridBagLayout);
		jTextArea_ToTalMessge = new JTextArea();
		this.add(jScrollPane_TotalMessage,
				new GridBagConstraints(0, 0, 1, 1,
						1.0, 0.9 ,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0,0));
		jScrollPane_TotalMessage.getViewport().add(jTextArea_ToTalMessge);

		this.add(jPanel_InputField,
				new GridBagConstraints(0, 10, 10, 1,
						0.0, 0.1 ,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(5, 0, 5, 5),
						0,0));
		jPanel_InputField.add(jTextField_Name,
				new GridBagConstraints(0, 10, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 0, 0, 5),
						0, 0));
		jPanel_InputField.add(jToggleButton_Connection,
				new GridBagConstraints(1, 10, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 5, 0, 100),
						0, 0));
		jPanel_InputField.add(jTextField_Message,
				new GridBagConstraints(2, 10, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 5, 0, 20),
						0, 0));
		jPanel_InputField.add(jButton_Send,
				new GridBagConstraints(3, 10, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 20, 0, 5),
						0, 0));
	}
	
	private void initButtonEvent() {
		jButton_Send.addActionListener(new TestPanel_jButton_Send_ActionListener(this));

		jToggleButton_Connection.addActionListener(new TestPanel_jToggleButton_Connection_ActionListener(this));
	}

	@Override
	public void push(Object o) {
		LOGGER.debug("ClientTestPanel push ");
		if (o instanceof String) {
			String str = (String)o;
			jTextArea_ToTalMessge.append(o.toString()+"\n");

			LOGGER.debug("push:" + str);
		}
	}

	void jButton_Send_actionPerformed() throws RemoteException {
		String name = jTextField_Name.getText();
		String msg = jTextField_Message.getText();
		String button_state = jToggleButton_Connection.getText();
		if (serverIf != null) {
			if (!button_state.equals("종료") || name.equals("")) {
				LOGGER.info("button_state : " + button_state);
				JOptionPane.showMessageDialog(this, "이름을 입력해서 접속해주세요!", "ERROR", JOptionPane.WARNING_MESSAGE);
			}
			else {
				serverIf.sendAll(name.toString(), msg);
			}
		}
	}

	void jToggleButton_Connection_actionPerformed() throws RemoteException {
		String name = jTextField_Name.getText();
		
		if (!name.equals("")) {
			jToggleButton_Connection.setText("종료");
			jToggleButton_Connection.setSelected(true);
			jTextField_Name.setEditable(false);
			jToggleButton_Connection.setEnabled(false);
			
			if (serverIf != null) {
				ResultVo resultVo = serverIf.register(name, clientIf);
				if (resultVo.getReason() != null) {
					JOptionPane.showMessageDialog(this, resultVo.getReason(), "ERROR", JOptionPane.WARNING_MESSAGE);
					jTextField_Name.setText("");
					jToggleButton_Connection.setText("접속");
					jTextField_Name.setEditable(true);
					jToggleButton_Connection.setEnabled(true);
				}
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "공백은 이름이 될 수 없습니다. 다시 입력해주세요!", "ERROR", JOptionPane.WARNING_MESSAGE);
			jToggleButton_Connection.setSelected(false);
		}
	}
}

class TestPanel_jButton_Send_ActionListener implements ActionListener {
	private static Logger LOGGER = LogManager.getLogger(TestPanel_jButton_Send_ActionListener.class);
	
	private TestPanel adaptee;

	public TestPanel_jButton_Send_ActionListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			adaptee.jButton_Send_actionPerformed();
		} 
		catch (RemoteException re) {
			LOGGER.error(re.getMessage(), re);
		}
	}
}

class TestPanel_jToggleButton_Connection_ActionListener implements ActionListener {
	private static Logger LOGGER = LogManager.getLogger(TestPanel_jToggleButton_Connection_ActionListener.class);
	
	private TestPanel adaptee;

	public TestPanel_jToggleButton_Connection_ActionListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			adaptee.jToggleButton_Connection_actionPerformed();
		} 
		catch (RemoteException re) {
			LOGGER.error(re.getMessage(), re);
		}
	}
}