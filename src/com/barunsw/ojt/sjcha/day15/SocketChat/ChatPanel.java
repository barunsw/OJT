package com.barunsw.ojt.sjcha.day15.SocketChat;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatPanel extends JPanel implements EventListener {
	public static final Logger LOGGER = LogManager.getLogger(ChatPanel.class);

	public static EventQueueWorker eventQueueWorker = new EventQueueWorker();

	private GridBagLayout gridBagLayout 		= new GridBagLayout();

	private ServerInterface socketIf;

	private ClientInterface clientIf;

	BufferedWriter writer;

	private JTextField jTextField_UserId 		= new JTextField(10);
	private JTextField jTextField_SendText 		= new JTextField(20);

	private JToggleButton jToggleButton_Connect = new JToggleButton("접속");
	private JButton jButton_Send 				= new JButton("전송");

	private JScrollPane jScrollPane_Dispaly 	= new JScrollPane();
	private JTextArea jTextArea_Display 		= new JTextArea();

	private JPanel jPanel_InputField 		    = new JPanel();

	public static String sendMessage 			= new String();

	public ChatPanel() {
		try {
			initEvent();
			initServerClient();
		} 
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		initComponent();
	}

	public void initEvent() {
		eventQueueWorker.addEventListener(this);
		eventQueueWorker.start();
	}

	public void initServerClient() throws Exception {
		socketIf = new socketImpl("localhost", ServerMain.PORT, new ClientImpl());
	}

	public void initComponent() {
		this.setLayout(gridBagLayout);

		this.add(jScrollPane_Dispaly, 
				new GridBagConstraints(0, 0, 1, 1,
						1.0, 1.0, 
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(10, 10, 10, 10),
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

		// togglebutton을 눌렀을 경우.
		jToggleButton_Connect.addActionListener(new ChatPanel_jToggleButton_Connect_ActionListioner(this));

		// 전송 버튼을 눌렀을 경우.
		jButton_Send.addActionListener(new ChatPanel_jButton_Send_ActionListener(this));

		// entet를 눌렀을 경우.
		jTextField_SendText.addKeyListener(new ChatPanel_EnterKey_KeyListener(this));
	}

	public void jToggleButton_Connect_ActionListioner(ActionEvent e) {
		String userName = jTextField_UserId.getText();

		LOGGER.debug(userName);

		// serverIf.map에 key에 넣기
		try {
			if (jToggleButton_Connect.isSelected() == true) {
				jToggleButton_Connect.setText("종료");
				jTextField_UserId.setEditable(false);

				if (socketIf != null) {
					LOGGER.debug("server register");
					socketIf.register(userName, clientIf);
				}
			}

			else {
				jToggleButton_Connect.setText("접속");
				jTextField_UserId.setEditable(true);
				jTextField_UserId.setText("");
				socketIf.deregister(userName);
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
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
				socketIf.send(userName, message);
				jTextField_SendText.setText("");
			} 
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
		else {
			JOptionPane.showMessageDialog(ChatPanel.this, "사용자 이름을 입력하세요.", "경고", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void push(Object o) {
		if (o instanceof String) {
			String str = (String)o;
			jTextArea_Display.append(o.toString()+"\n");

			LOGGER.debug("push:" + str);
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