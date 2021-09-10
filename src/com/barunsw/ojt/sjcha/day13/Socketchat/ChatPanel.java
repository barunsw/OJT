package com.barunsw.ojt.sjcha.day13.Socketchat;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.sjcha.day06.TestFrame;

public class ChatPanel extends JPanel implements ClientInterface {
	public static final Logger LOGGER = LogManager.getLogger(ChatPanel.class);

	private GridBagLayout gridBagLayout = new GridBagLayout();
	private final Dimension SIZE = new Dimension(120, 300);
	private final Dimension BUTTON_SIZE = new Dimension(80, 30);

	private ServerInterface socketIf;

	private Socket socket;

	BufferedWriter writer;
	private JTextField jTextField_UserId = new JTextField();
	private JTextField jTextField_SendText = new JTextField();

	private JToggleButton jToggleButton_Connect = new JToggleButton("접속");
	private JButton jButton_Send = new JButton("전송");

	private JScrollPane jScrollPane_Dispaly = new JScrollPane();
	private JTextArea jTextArea_Display = new JTextArea();

	public static String sendMessage = new String();

	public ChatPanel() {
		try {
			initServerClient();
		} 
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		initComponent();
	}

	public void initServerClient() throws Exception {
		socketIf = new Client_ServerWriteImpl("localhost", ServerMain.PORT, this);
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
					socketIf.register(userName, this);
				}
			}

			// 선택이 풀리면
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

	@Override
	public int push(String message) throws Exception{
		jTextArea_Display.append(message + "\n");
		return 1;
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