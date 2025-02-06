package com.barunsw.ojt.sjcha.day12.RMIchat;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.sjcha.day12.RMIchat.ChatPanel;
import com.barunsw.ojt.sjcha.day12.RMIchat.ChatPanel_EnterKey_KeyListener;

public class ChatPanel extends JPanel {

	public static final Logger LOGGER 			= LogManager.getLogger(ChatPanel.class);

	private GridBagLayout gridBagLayout 		= new GridBagLayout();
	private final Dimension SIZE 				= new Dimension(120, 300);
	private final Dimension BUTTON_SIZE 		= new Dimension(80, 30);

	private ServerInterface serverIf = null;
	private ClientInterface clientIf;

	private JTextField jTextField_UserId 		= new JTextField(10);
	private JTextField jTextField_SendText 		= new JTextField(20);

	private JToggleButton jToggleButton_Connect = new JToggleButton("접속");
	private JButton jButton_Send 				= new JButton("전송");

	private JPanel jPanel_InputField 		    = new JPanel();
	private JScrollPane jScrollPane_Dispaly 	= new JScrollPane();
	private JTextArea jTextArea_Display 		= new JTextArea();

	public static String sendMessage 			= new String();
	public ChatPanel() {
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
			// serverIf.registe
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
				new GridBagConstraints(0, 0, 1, 1,
						1.0, 1.0, 
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(10, 10, 10, 5),
						0, 0));

		jScrollPane_Dispaly.getViewport().add(jTextArea_Display);

		this.add(jPanel_InputField,
				new GridBagConstraints(0, 10, 10, 1,
						0.0, 0.0 ,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0,0));
		
		jPanel_InputField.add(jTextField_UserId,
				new GridBagConstraints(0, 10, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 0, 0, 5),
						0, 0));
		
		jPanel_InputField.add(jToggleButton_Connect,
				new GridBagConstraints(1, 10, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 5, 0, 100),
						0, 0));
		
		jPanel_InputField.add(jTextField_SendText,
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



		// togglebutton을 누른 경우.
		jToggleButton_Connect.addActionListener(new ChatPanel_jToggleButton_Connect_ActionListioner(this));

		// 전송 버튼을 누른 경우.
		jButton_Send.addActionListener(new ChatPanel_jButton_Send_ActionListener(this));
		
		// entet를 눌렀을 경우.
		jTextField_SendText.addKeyListener(new ChatPanel_EnterKey_KeyListener(this));		
	}
	
	public void jToggleButton_Connect_ActionListioner(ActionEvent e) {
		String userName = jTextField_UserId.getText();
		LOGGER.debug(userName);

		// serverIf.map에 key에 넣기.
		try {
			if (jToggleButton_Connect.isSelected() == true) {
				jToggleButton_Connect.setText("종료");
				jTextField_UserId.setEditable(false);
			}

			// 선택이 풀린 경우.
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
			LOGGER.error(re.getMessage(), re);
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	public void push(String message) {
		LOGGER.debug("push");
		jTextArea_Display.append(message.toString() + "\n");
	}

	public void jButton_Send_ActionListener(ActionEvent e) {
		send();
	}
	
	public void EnterKey_KeyListener(KeyEvent e) {
		switch (e.getKeyChar()) {
		case '\n':
			send();
		}
	}
	
	public void send() {
		if(jTextField_UserId.getText().equals("") == false) {
			String userName = jTextField_UserId.getText();
			LOGGER.debug("textfield username : " + userName);

			String message = jTextField_SendText.getText();
			LOGGER.debug("send message : " + message);

			try {
				serverIf.send(userName, message);
				jTextField_SendText.setText("");
			} 
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
		// 값을 입력하라는 경고창 다이얼로그. 
		else {
			JOptionPane.showMessageDialog(ChatPanel.this, "사용자 이름을 입력하세요.", "경고", JOptionPane.ERROR_MESSAGE);
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
		adaptee.jButton_Send_ActionListener(e);
	}
}

class ChatPanel_EnterKey_KeyListener extends KeyAdapter {
	private ChatPanel adaptee;
	
	public ChatPanel_EnterKey_KeyListener(ChatPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	public void keyPressed(KeyEvent e) {
		adaptee.EnterKey_KeyListener(e);
	}
}
