package com.barunsw.ojt.phs.day16;

import java.awt.Color;
import java.awt.Dimension;
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

import com.barunsw.ojt.phs.day16.MessageVO;

public class ClientPanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LogManager.getLogger(ClientPanel.class);

	private JTextArea textArea = new JTextArea(30, 1);
	
	private JScrollPane scrollPane = new JScrollPane(textArea);
	
	private JTextField jTextField_Chat = new JTextField();
	private JTextField jTextField_Name = new JTextField();
	
	private JButton jButton_Send = new JButton("SEND");
	
	private ClientInterface clientInterface;
	private ServerInterface serverInterface = null;
	
	private String myName = "";
	
	public ClientPanel() {
		try {
			LOGGER.debug("ClientTestPanel 생성자");
			initEvent();
			initConnectRMI();
			initComponent();
			initButtonEvent();
		}
		catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initEvent() {
		ClientMain.eventQueueWorker.addEventListener(this);
		ClientMain.eventQueueWorker.start();
	}
	
	private void initConnectRMI() {
		try {
			clientInterface = new ClientImpl();

			Registry registry;
			registry = LocateRegistry.getRegistry(ServerMain.PORT);
			
			Remote remote = registry.lookup(ServerMain.BIND_NAME);
			if (remote instanceof ServerInterface) {
				serverInterface = (ServerInterface)remote;
			}
		}
		catch (RemoteException re) {
			LOGGER.error(re.getMessage(), re);
		}
		catch (NotBoundException nbe) {
			LOGGER.error(nbe.getMessage(), nbe);
		}
	}
	
	private void initComponent() {
		setLayout(new GridBagLayout());
		
		setBackground(new Color(0xFFFF8F));
		
		textArea.setEditable(false);
		jTextField_Name.setText("Guest");
		jTextField_Name.setPreferredSize(new Dimension(80, 20));
		
		this.add(scrollPane, 
				new GridBagConstraints(0, 0, 3, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(10, 10, 10, 10),
						0, 0));
		
		this.add(jTextField_Name, 
				new GridBagConstraints(0, 1, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(10, 10, 10, 10),
						0, 0));
		
		this.add(jTextField_Chat, 
				new GridBagConstraints(1, 1, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(10, 10, 10, 10),
						0, 0));
		
		this.add(jButton_Send, 
				new GridBagConstraints(2, 1, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(10, 10, 10, 10),
						0, 0));
		
	}
	private void initButtonEvent() {
		jButton_Send.addActionListener(new Button_Send_Action(this));
	}

	@Override
	public void push(Object o) {
		LOGGER.debug("Client push() !! ");
		if (o instanceof MessageVO) {
			MessageVO messageVo = (MessageVO)o;
			textArea.append(messageVo.getSender() + " : " + messageVo.getMessage() +"\n");
		}
	}
	
	public void sendMessage() throws RemoteException {
		LOGGER.debug("sendMessage()!!");
		
		String name 	= jTextField_Name.getText();
		String message  = jTextField_Chat.getText();
		
		MessageVO vo = new MessageVO(name, message);
		
		if (!myName.equals(name)) {
			serverInterface.logOut(myName, clientInterface);
			myName = name;
		}
		
		if (serverInterface != null) {
			serverInterface.register(name, clientInterface);
			serverInterface.send(vo);
		}
	}
	
	public void close() {
		try {
			serverInterface.logOut(myName, clientInterface);
		}
		catch (RemoteException e) {
			
		}
	}
}

class Button_Send_Action implements ActionListener{
	private ClientPanel adaptee;
	
	public Button_Send_Action(ClientPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			adaptee.sendMessage();
		}
		catch (RemoteException e1) {
		}
	}
}