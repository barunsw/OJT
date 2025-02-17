package com.barunsw.ojt.mjg.day17;

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
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatPanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LogManager.getLogger(ChatPanel.class);
	private ServerInterface serverIf;
	private ClientInterface clientIf;

	// Component.name과 구분하기 위한 userName(사용자 이름)
	private String userName;

	private JTextArea jTextArea_Chat 	= new JTextArea();                 // 채팅 화면
	private JScrollPane jScrollPane 	= new JScrollPane(jTextArea_Chat); // 채팅 화면 스크롤

	private JPanel jPanel_Send 			= new JPanel();        // 입력 패널 따로
	private JTextArea jTextArea_Input 	= new JTextArea();     // 입력 필드
	private JButton jButton_Send 		= new JButton("Send"); // 전송 버튼

	private GridBagLayout gridBagLayout = new GridBagLayout(); // GridBagLayout 사용

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
		// 채팅화면은 읽기 전용, 입력 불가
		jTextArea_Chat.setEditable(false);
		jPanel_Send.setLayout(gridBagLayout);
		
        // 채팅 화면 + 스크롤
        this.add(jScrollPane, new GridBagConstraints(
            0, 0, 5, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 5, 5, 5), 0, 0
        ));
    	
        // 입력 패널 추가
        this.add(jPanel_Send, new GridBagConstraints(
            0, 1, 5, 1, 1.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
            new Insets(5, 5, 5, 5), 0, 0
        ));
        
        // 입력 필드
        jPanel_Send.add(jTextArea_Input, new GridBagConstraints(
            0, 1, 4, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.BOTH,
            new Insets(5, 5, 5, 5), 0, 0
        ));

        // 전송 버튼
        jPanel_Send.add(jButton_Send, new GridBagConstraints(
            4, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.EAST, GridBagConstraints.BOTH,
            new Insets(5, 5, 5, 5), 0, 0
        ));

		jButton_Send.addActionListener(new ChatPanel_jButton_Send_ActionListener(this));

	}

	// 패널에서 사용할 이벤트 및 리스너 연결
	private void initEvent() {
		// EventQueueWorker에서 발생하는 이벤트를 수신받도록 등록
		ClientMain.eventQueueWorker.addEventListener(this);
		
	}

	// Send 버튼 클릭 → 서버에 메시지 전송
	public void jButton_Send_ActionListener() throws RemoteException {
		String message = jTextArea_Input.getText();
		if (message != null && !message.trim().isEmpty()) {
			try {
				if (serverIf != null && userName != null) {
					// 사용자 이름 + 메시지를 서버로 전송
					serverIf.register(userName.toString(), clientIf);
					serverIf.send(userName, message);
				} 
				else {
					LOGGER.warn("서버 연결이 안 되었거나 userName이 없습니다.");
				}
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}

			// 로컬 TextArea를 초기화
			jTextArea_Input.setText("");
		}
	}

	private void initRmi() throws Exception {
		// 접속 버튼 상태에 따라 regist, deregist 한다.
		// 전송 버튼을 누르면 serverIf.send한다.

		// 1. 사용자 이름 입력한다.
		String inputName = JOptionPane.showInputDialog(
				this // 부모 컴포넌트(현재 패널)
				, "사용자 이름을 입력하세요."
				, "이름 입력"
				, JOptionPane.QUESTION_MESSAGE);

		if (inputName == null || inputName.trim().isEmpty()) {
			// 취소 혹은 빈값
			LOGGER.info("이름 입력 취소됨. RMI 등록을 진행하지 않습니다.");
			System.exit(0);
		}

		// 2. 레지스트리를 가져온다.
		Registry registry = LocateRegistry.getRegistry(ServerMain.PORT);

		// 3. 서버쪽 RMI 객체 가져온다.
		Remote remote = registry.lookup(ServerMain.BIND_NAME);
		if (remote instanceof ServerInterface) {
			serverIf = (ServerInterface) remote;
		}

		if (serverIf != null) {
			// 4. ClientImpl을 생성한다.
			clientIf = new ClientImpl();
			this.userName = inputName;

			// 5. 서버에 이름 등록한다.
			serverIf.register(userName, clientIf);
			
	        // 입장 메시지를 서버로 전송
	        String entryMessage = userName + " 님이 입장하셨습니다.";
	        serverIf.send("", entryMessage);

			LOGGER.info("+++서버에 등록 성공: {}", userName);
		} 
		else {
			LOGGER.warn("+++ServerInterface를 찾지 못했습니다.");
		}
	}

	@Override
	public void push(Object o) {
		LOGGER.debug("+++push: " + (String) o);
		LOGGER.debug("+++push: " + o);
		// textarea에 append한다.
		if (o instanceof String) {
			String str = (String) o;
			jTextArea_Chat.append(o.toString() + "\n");

			LOGGER.debug("+++push:" + str);
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
