package com.barunsw.ojt.jyb.day17;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatPanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LogManager.getLogger(ChatPanel.class);
	private ServerInterface serverIf;
	private ClientInterface clientIf;

	private String userName;

	private JTextArea jTextArea = new JTextArea();
	private JScrollPane jScrollPane = new JScrollPane(jTextArea);

	private JPanel jPanel_Send = new JPanel();
	private JTextArea jTextArea_Input = new JTextArea();
	private JButton jButton_Send = new JButton("Send");

	private GridBagLayout gridBagLayout = new GridBagLayout();

	public ChatPanel() {
		try {
			initComponent();
			initEvent();
			initRmi();
		}
		catch (Exception ex) {
			LOGGER.error(ex);
		}
	}

	private void initComponent() throws Exception {
		this.setLayout(gridBagLayout);
		jPanel_Send.setLayout(gridBagLayout);

		this.add(jScrollPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

		this.add(jPanel_Send, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

		jPanel_Send.add(jTextArea_Input, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

		jPanel_Send.add(jButton_Send, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

		jButton_Send.addActionListener(new ChatPanel_jButton_Send_ActionListener(this));

	}

	private void initEvent() {
		ClientMain.eventQueueWorker.addEventListener(this);
	}

	public void jButton_Send_ActionListener() throws RemoteException {
		String msg = jTextArea_Input.getText();
		if (msg != null && !msg.isEmpty()) {
			try {
				serverIf.register(userName.toString(), clientIf);
				serverIf.send(userName, msg);

			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}

			jTextArea_Input.setText(""); // text Field를 빈칸으로 초기화
		}
	}

	private void initRmi() throws Exception {
		String name = JOptionPane.showInputDialog(this, "이름을 입력하세요", "이름", JOptionPane.QUESTION_MESSAGE);

		if (name == null || name.isEmpty()) {
			System.exit(0);
		}

		Registry registry = LocateRegistry.getRegistry(ServerMain.PORT);

		Remote remote = registry.lookup(ServerMain.BIND_NAME);
		if (remote instanceof ServerInterface) {
			serverIf = (ServerInterface) remote;
		}

		clientIf = new ClientImpl();
		this.userName = name;

		serverIf.register(userName, clientIf);

		LOGGER.info("rmi 서버에 등록", userName);
	}

	@Override
	public void push(Object o) {
		if (o instanceof String) {
			String str = (String) o;
			jTextArea.append(o.toString() + "\n");

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
		catch (RemoteException re) {
			re.printStackTrace();
		}
	}
}
