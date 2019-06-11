package com.barunsw.ojt.gtkim.day16.chatting;

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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);
	
	private JLabel jLabel_Name			= new JLabel();
	private JPanel jPanel_Write 		= new JPanel();	
	private JTextField jTextField_Write = new JTextField();
	private JButton	jButton_Send 		= new JButton("전송");
	
	private JTextArea jTextArea_View  	 = new JTextArea();
	private JScrollPane jScrollPane_View = new JScrollPane();
	
	private GridBagLayout layout = new GridBagLayout();
	
	private ClientInterface clientIf;
	private ServerInterface serverIf;
	
	private String name; 
	
	public TestPanel(String name) {
		try {
			this.name = name;
			
			initRmi();
			initComponent();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void initRmi() {
		try {
			clientIf = new ClientImpl();
			
			Registry registry = LocateRegistry.getRegistry(ServerMain.PORT);
			Remote remote = registry.lookup(ServerMain.BIND_NAME);
			if (remote instanceof ServerInterface) {
				serverIf = (ServerInterface)remote;
			}
			
			if (serverIf != null) {
				serverIf.register(name, clientIf);
			}
			
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			JOptionPane.showMessageDialog(this, "서버와 연결 할 수 없습니다", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	private void initComponent() {
		this.setLayout(layout);
		
		jLabel_Name.setText(name);
		
		jTextArea_View.setEditable(false);
		jScrollPane_View.getViewport().add(jTextArea_View);
		
		jPanel_Write.setLayout(layout);
		
		this.add(jScrollPane_View,
				new GridBagConstraints(0, 0, 2, 1,
						1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));		
		
		this.add(jPanel_Write,
				new GridBagConstraints(0, 1, 2, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0),
						0, 0));
		
		jPanel_Write.add(jLabel_Name,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.WEST,
						new Insets(0, 5, 5, 5),
						0, 0));
		
		jPanel_Write.add(jTextField_Write,
				new GridBagConstraints(1, 0, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		jPanel_Write.add(jButton_Send,
				new GridBagConstraints(2, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		jTextField_Write.addActionListener(new TestPanel_Jbutton_AcionListener(this));
		jButton_Send.addActionListener(new TestPanel_Jbutton_AcionListener(this));
		// 이벤트큐워커에 Listener 등록 
		ClientMain.eventQueueWorker.addEventListener(this);		
	}
	
	public void sendMsg(ActionEvent e) {
		String msg = jTextField_Write.getText();
		
		try {
			serverIf.send(name, msg);
			jTextField_Write.setText("");
		}
		catch (RemoteException re) {
			LOGGER.error(re.getMessage(), re);
		}
	}

	public void close() {
		try {
			serverIf.logOut(name, clientIf);
		}
		catch (Exception ex) {
			LOGGER.debug(ex.getMessage(), ex);
		}
	}
	
	@Override
	public void push(Object o) {
		if (o instanceof String) {
			String msg = (String) o;
			
			jTextArea_View.setText(jTextArea_View.getText() + "\n" + msg);
			
			jScrollPane_View.getVerticalScrollBar()
				.setValue(jScrollPane_View.getVerticalScrollBar().getMaximum());
		}
	}
}

class TestPanel_Jbutton_AcionListener implements ActionListener {
	TestPanel adaptee;
	
	public TestPanel_Jbutton_AcionListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.sendMsg(e);
	}
}
