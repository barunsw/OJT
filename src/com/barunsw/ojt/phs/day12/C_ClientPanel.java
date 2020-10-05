package com.barunsw.ojt.phs.day12;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class C_ClientPanel extends JPanel{
	
	private static final Logger LOGGER = LogManager.getLogger(C_ClientFrame.class);
	
	private JTextArea textArea = new JTextArea(30, 1);
	
	private JScrollPane scrollPane = new JScrollPane(textArea);
	
	private JTextField chat_TextField = new JTextField();
	private JTextField name_TextField = new JTextField();
	
	private JButton send_Button = new JButton("SEND");
	
	private Socket socket;
	
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois  = null;
	
	public C_ClientPanel() {
		try {
			initComponent();
			addListener();
			socket = new Socket("127.0.0.1", 55555);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			receiveThread();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	private void addListener() {
		send_Button.addActionListener(new Send_Action());
		chat_TextField.addActionListener(new Send_Action());
	}

	private void sendMessage(MessageVO message) {
		LOGGER.debug("sendMessage");
		try {
			oos.writeObject(message);
			oos.flush();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void receiveThread() {
		new Thread() {
			@Override
			public void run() {
				MessageVO message = null;
				LOGGER.debug("receiveThread");	
				try {
					while (true) {
						if ((message = (MessageVO)ois.readObject()) != null) {
							textArea.append(message.toString());
							scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
						}
					}
				}
				catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				} 
			}
		}.start();
	}

	private void initComponent() {
		setLayout(new GridBagLayout());
		
		setBackground(new Color(0xFFFF8F));
		
		textArea.setEditable(false);
		name_TextField.setText("Guest");
		name_TextField.setPreferredSize(new Dimension(80, 20));
		
		this.add(scrollPane, 
				new GridBagConstraints(0, 0, 3, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(10, 10, 10, 10),
						0, 0));
		
		this.add(name_TextField, 
				new GridBagConstraints(0, 1, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(10, 10, 10, 10),
						0, 0));
		
		this.add(chat_TextField, 
				new GridBagConstraints(1, 1, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(10, 10, 10, 10),
						0, 0));
		
		this.add(send_Button, 
				new GridBagConstraints(2, 1, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(10, 10, 10, 10),
						0, 0));
		
	}
	
	class Send_Action implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
				LOGGER.debug("send!!");
				MessageVO vo = new MessageVO(
						name_TextField.getText(),
						chat_TextField.getText()
						);
				sendMessage(vo);
				chat_TextField.setText("");
		}
		
	}
}
