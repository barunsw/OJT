package com.barunsw.ojt.yjkim.day16.RMIChat;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class ClientTestPanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LogManager.getLogger(ClientTestPanel.class);
	public static EventQueueWorker eventQueueWorker = new EventQueueWorker();

	private GridBagLayout gridBagLayout 	 	 = new GridBagLayout();
	private JLabel jLabel_Name 					 = new JLabel("이름");
	private JLabel jLabel_Message 				 = new JLabel("메시지");
	
	private JTextField jTextField_Name			 = new JTextField(10);
	private JTextField jTextField_Message 		 = new JTextField(20); 
	public JTextArea jTextArea_ToTalMessge;
	
	private JButton jButton_Send 				 = new JButton("전송");
	
	private JPanel jPanel_InputField 			 = new JPanel();
	private JScrollPane jScrollPane_TotalMessage = new JScrollPane();
	
	private ClientInterface clientIf;
	private ServerInterface serverIf = null;
	public ClientTestPanel() {
		try {
			LOGGER.debug("ClientTestPanel 생성자");
			initEvent();
			initConnectRMI();
			initComponent();
			initButtonEvent();
		} catch(Exception ex) {
			
		}
	}
	
	private void initEvent() {
		eventQueueWorker.addEventListener(this);
		
		eventQueueWorker.start();
	}
	
	private void initConnectRMI() {
		try {
			clientIf = new ClientImpl();

			Registry registry;
			registry = LocateRegistry.getRegistry(ServerMain.PORT);
			
			Remote remote = registry.lookup(ServerMain.BIND_NAME);
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
		jPanel_InputField.add(jLabel_Name,
				new GridBagConstraints(0, 10, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 0, 0, 5),
						0, 0));
		jPanel_InputField.add(jTextField_Name,
				new GridBagConstraints(1, 10, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 5, 0, 100),
						0, 0));
		jPanel_InputField.add(jLabel_Message,
				new GridBagConstraints(2, 10, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 5, 0, 20),
						0, 0));
		jPanel_InputField.add(jTextField_Message,
				new GridBagConstraints(3, 10, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 20, 0, 5),
						0, 0));
		jPanel_InputField.add(jButton_Send,
				new GridBagConstraints(4, 10, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 0, 0, 0),
						0, 0));
	}
	private void initButtonEvent() {
		jButton_Send.addActionListener(new Button_this_ActionAdapter(this));
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
	
	public void sendMessage() throws RemoteException {
		Object name 	= jTextField_Name.getText();
		Object message  = jTextField_Message.getText();
		if (serverIf != null) {
			serverIf.register(name.toString(), clientIf);
			serverIf.send(name.toString(), message.toString());
		}
	}
}

class Button_this_ActionAdapter implements ActionListener {
	private ClientTestPanel adaptee;
	
	public Button_this_ActionAdapter(ClientTestPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String event  = e.getActionCommand();
		
		switch (event) {
			case "전송" : 
				try {
					adaptee.sendMessage();
				} catch (RemoteException e1) {
					
				}
			break;
		}
	}
	
}
