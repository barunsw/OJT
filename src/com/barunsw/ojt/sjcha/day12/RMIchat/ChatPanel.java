package com.barunsw.ojt.sjcha.day12.RMIchat;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import com.barunsw.ojt.sjcha.day12.TestPanel;

public class ChatPanel extends JPanel {
	  
	public static final Logger LOGGER = LogManager.getLogger(ChatPanel.class);
	
	private GridBagLayout gridBagLayout 	= new GridBagLayout();
	private final Dimension SIZE 			= new Dimension(120, 300);
	private final Dimension BUTTON_SIZE 	= new Dimension(80, 30);
		
	private ServerInterface serverIf = null;
	private ClientInterface clientIf;
	
	private JTextField jTextField_UserId 	= new JTextField();
	private JTextField jTextField_SendText 	= new JTextField();
	
	private JToggleButton jToggleButton_Connect = new JToggleButton("접속");
	private JButton jButton_Send = new JButton("전송");	
	
	
	private JScrollPane jScrollPane_Dispaly 	= new JScrollPane(); 
	private JTextArea jTextArea_Display 			= new JTextArea();
	
	// public static List<String> sendMessage = new ArrayList<>();
	public static String sendMessage = new String();
	public ChatPanel() {
		// TODO Auto-generated constructor stub
		initRmi();
		initComponent();
	}
	
	public void initRmi() {
		try {
			clientIf = new ClientImpl(this);
			
			Registry registry = LocateRegistry.getRegistry(ServerMain.PORT);
			
			// 서버 이름으로 "RMICHAT"으로 registry를 찾는다. 
			Remote remote = registry.lookup("RMICHAT");
			
			// remote가 serverImpl과 타입이 같으면
			// serverIf.register
			if (remote instanceof ServerInterface) {
				serverIf = (ServerInterface)remote; 
				LOGGER.debug("+++SERVER+++");
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	public void initComponent() {
		this.setLayout(gridBagLayout);
		
		this.add(jScrollPane_Dispaly,
				new GridBagConstraints(0, 0, 5, 1,
						1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(10, 10, 10, 10),
						0, 0));
		
		jScrollPane_Dispaly.getViewport().add(jTextArea_Display);
		
		this.add(jTextField_UserId,
				new GridBagConstraints(0, 1, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 10, 10, 10),
						0, 0));
		
		this.add(jToggleButton_Connect,
				new GridBagConstraints(1, 1, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 10, 10),
						0, 0));
		
		this.add(jTextField_SendText,
				new GridBagConstraints(2, 1, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 10, 10),
						0, 0));
		
		this.add(jButton_Send,
				new GridBagConstraints(3, 1, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 10, 10),
						0, 0));
		
		
		// togglebutton을 눌렀을 때
		jToggleButton_Connect.addActionListener(new ChatPanel_jToggleButton_Connect_ActionListioner(this));
		
		// 전송 버튼을 눌렀을 때
		jButton_Send.addActionListener(new ChatPanel_jButton_Send_ActionListener(this));
	}
	
	public void jToggleButton_Connect_ActionListioner(ActionEvent e) {
		String userName = jTextField_UserId.getText();
		LOGGER.debug(userName);
		
		// serverIf.map에 key에 넣기
		try {
			if (jToggleButton_Connect.isSelected() == true) {
				jToggleButton_Connect.setText("종료");
				jTextField_UserId.setEditable(false);
			}
			
			// 선택이 풀리면 
			else {
				jToggleButton_Connect.setText("접속");
				jTextField_UserId.setEditable(true);
				jTextField_UserId.setText("");
				serverIf.deregister(userName);
			}
			
			if(serverIf != null) {
				LOGGER.debug("server register");
				serverIf.register(userName, clientIf);
			}
		} 
		catch (RemoteException re) {
			// TODO Auto-generated catch block
			LOGGER.error(re.getMessage(), re);
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	public void push(String message) {
		//sendMessage = message + "\n";
		LOGGER.debug("push");
		jTextArea_Display.append(message.toString() + "\n");
	}
	
	public void jButton_Send_ActionListener(ActionEvent e) {
		String userName = jTextField_UserId.getText();
		LOGGER.debug(userName);
	
		String message = jTextField_SendText.getText();
		LOGGER.debug(message);
		
		try {
			serverIf.send(userName, message);
			// jTextArea_Display.append(sendMessage.toString());
			jTextField_SendText.setText("");
		} 
		catch (RemoteException re) {
			// TODO Auto-generated catch block
			LOGGER.error(re.getMessage(), re);
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
}

class ChatPanel_jToggleButton_Connect_ActionListioner implements ActionListener {
	private ChatPanel adaptee;
	
	public ChatPanel_jToggleButton_Connect_ActionListioner(ChatPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		adaptee.jToggleButton_Connect_ActionListioner(e);
	}
}

class ChatPanel_jButton_Send_ActionListener implements ActionListener {
private ChatPanel adaptee;
	
	public ChatPanel_jButton_Send_ActionListener(ChatPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		adaptee.jButton_Send_ActionListener(e);
	}
}
