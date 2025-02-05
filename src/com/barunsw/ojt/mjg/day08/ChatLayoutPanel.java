package com.barunsw.ojt.mjg.day08;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatLayoutPanel extends JPanel {
	
	// 채팅 화면, 채팅 화면 스크롤, 입력 필드, 전송 버튼
	
	// 채팅 화면 스크롤 확인용
//	private JTextArea jTextArea_Chat = new JTextArea("시작\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n끝");    
	private JTextArea jTextArea_Chat = new JTextArea();                 // 채팅 화면
	private JScrollPane jScrollPane = new JScrollPane(jTextArea_Chat);  // 채팅 화면 스크롤
	
	
	private JPanel jPanel_Send = new JPanel();                          // 입력 패널 따로
	private JTextArea jTextArea_Input = new JTextArea();                // 입력 필드
	private JButton jButton = new JButton("Send");                      // 전송 버튼
	
    private GridBagLayout gridBagLayout = new GridBagLayout();          // GridBagLayout 사용
	
    public ChatLayoutPanel() {
        try {
            initComponent();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

	private void initComponent() {
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
        jPanel_Send.add(jButton, new GridBagConstraints(
            4, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.EAST, GridBagConstraints.BOTH,
            new Insets(5, 5, 5, 5), 0, 0
        ));
        
        jButton.addActionListener(e -> addEventHandlers());
		
	}
	
	private void addEventHandlers() {
        // Send 버튼 클릭 시 이벤트 처리
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }
	
    private void sendMessage() {
        String message = jTextArea_Input.getText().trim();     // 입력된 메시지 가져오기
        if (!message.isEmpty()) {
            jTextArea_Chat.append("User: " + message + "\n");  // 채팅 화면에 메시지 추가
            jTextArea_Input.setText("");                       // 입력 필드 초기화
        }
    }
}
