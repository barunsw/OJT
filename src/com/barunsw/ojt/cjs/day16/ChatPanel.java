package com.barunsw.ojt.cjs.day16;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatPanel extends JPanel implements EventListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);

	private ServerInterface serverIf;
	private ClientInterface clientIf ;

	private final Dimension Toggle_SIZE = new Dimension(80, 22);
	private final Dimension BUTTON_SIZE = new Dimension(80, 22);

	private GridBagLayout grid = new GridBagLayout();
	private JTextField jTextField_Name = new JTextField(10);
	private JTextField jTextField_Msg = new JTextField(20);
	private JTextArea jTextArea_Msg = new JTextArea();
	private JScrollPane jScrollPane_Msg = new JScrollPane();
	private JToggleButton jToggleButton_NameSet = new JToggleButton("접속");
	private JButton jButton_Send = new JButton("전송");
	private JPanel jPanel = new JPanel();

	public ChatPanel() {
		try {
			initComponent();
			initRmi();
		} catch (Exception ex) {

		}
	}

	private void initComponent() throws Exception {
		this.setLayout(grid);
		jTextField_Name.setPreferredSize(new Dimension(120, 22));
		jToggleButton_NameSet.setPreferredSize(Toggle_SIZE);
		jButton_Send.setPreferredSize(BUTTON_SIZE);
		this.add(jScrollPane_Msg, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		jScrollPane_Msg.getViewport().add(jTextArea_Msg);

		this.add(jPanel, 
				new GridBagConstraints(0, 8, 5, 1,
				0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5), 
				0, 0));
		jPanel.add(jTextField_Name,
				new GridBagConstraints(0, 8, 1, 1, 
				1.0, 1.0, GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
				new Insets(5, 5, 5, 5), 
				0, 0));
		jPanel.add(jToggleButton_NameSet,
				new GridBagConstraints(0, 8, 1, 1, 
				1.0, 1.0, GridBagConstraints.CENTER,GridBagConstraints.VERTICAL, 
				new Insets(0, 5, 5, 5), 
				0, 0));
		jPanel.add(jTextField_Msg,
				new GridBagConstraints(0, 8, 1, 1,
				1.0, 1.0, GridBagConstraints.CENTER,GridBagConstraints.VERTICAL, 
				new Insets(0, 5, 5, 5), 
				0, 0));
		jPanel.add(jButton_Send,
				new GridBagConstraints(0, 8, 1, 1,
				1.0, 1.0, GridBagConstraints.CENTER,GridBagConstraints.VERTICAL, 
				new Insets(0, 5, 5, 5), 
				0, 0));

		jButton_Send.addActionListener(new ChatPanel_jButton_Send_ActionListener(this));
		jToggleButton_NameSet.addActionListener(new ChatPanel_jToggleButton_Login_ActionListener(this));

	}

	private void initRmi() throws RemoteException, NotBoundException  {
		ClientMain.eventQueueWorker.addEventListener(this);
		clientIf = new ClientImpl();
		Registry registry = LocateRegistry.getRegistry(ServerMain.PORT);

		Remote remote = registry.lookup(ServerMain.BIND_NAME);
		if (remote instanceof ServerInterface) {
			serverIf = (ServerInterface) remote;
		}
	}

	// 접속 버튼 상태에 따라 regist, deregist 한다.
	public void jToggleButton_Login_ActionListener() throws RemoteException {
		if (!jTextField_Name.getText().isEmpty()) {
			if (jToggleButton_NameSet.isSelected()) {
				jToggleButton_NameSet.setText("종료");
				jTextField_Name.setEditable(false);
				serverIf.register(jTextField_Name.getText(), clientIf);
			} 
			else {
				jToggleButton_NameSet.setText("접속");
				jTextField_Name.setEditable(true);
				serverIf.deregister(jTextField_Name.getText());
				serverIf.send(jTextField_Name.getText(), "님이 나가셨습니다.");
			}
		} 
	}

	// textarea에 append한다.
	@Override
	public void push(Object o) {
		jTextArea_Msg.append(o.toString() +"\n");
		LOGGER.debug("PUSH");
	}

	// 전송 버튼을 누르면 serverIf.send한다.
	void jButton_Send_ActionListener() throws RemoteException {
		if (!jTextField_Msg.getText().isEmpty()) {
			serverIf.send(jTextField_Name.getText(), jTextField_Msg.getText());
			jTextField_Msg.setText("");
		}
		else {
			JOptionPane.showMessageDialog(this, "내용을 입력해주세요", "Alert", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}

class ChatPanel_jButton_Send_ActionListener implements ActionListener {
	private ChatPanel adaptee;

	public ChatPanel_jButton_Send_ActionListener(ChatPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			adaptee.jButton_Send_ActionListener();
		} 
		catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}
}

class ChatPanel_jToggleButton_Login_ActionListener implements ActionListener {
	private ChatPanel adaptee;

	public ChatPanel_jToggleButton_Login_ActionListener(ChatPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			adaptee.jToggleButton_Login_ActionListener();
		}
		catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}
}