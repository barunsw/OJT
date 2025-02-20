package com.barunsw.ojt.mjg.day19;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputPanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(InputPanel.class);

	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	private JButton jButton_Execute			 	= new JButton("실행");  // 실행 버튼
	private JTextArea jTextArea_Query 			= new JTextArea();     // 쿼리 입력 필드
	private JScrollPane jScrollPane_Query 		= new JScrollPane(jTextArea_Query);
	private JTextField jTextField_Description 	= new JTextField();    // 설명 창
	
	public InputPanel() {
		try {
			initEvent();
			initComponent();
			initQuery();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initEvent() {
		ClientMain.eventQueueWorker.addEventListener(this);
	}

	private void initComponent() {
		this.setLayout(gridBagLayout);
		
		// 설명 창 입력 불가, 투명 설정
		jTextField_Description.setEditable(false);
		jTextField_Description.setOpaque(false);
		jTextField_Description.setBackground(new Color(0, 0, 0, 0));
				
        // 실행 버튼
        this.add(jButton_Execute, new GridBagConstraints(
            0, 0, 1, 1, 1.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 5, 5, 5), 0, 0
		));
        
        // 쿼리 입력 필드
        this.add(jScrollPane_Query, new GridBagConstraints(
            0, 1, 1, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 5, 5, 5), 0, 0
		));	
        
        // 설명 창 
        this.add(jTextField_Description, new GridBagConstraints(
            0, 2, 1, 1, 1.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 5, 5, 5), 0, 0
		));
	}
	
    private void initQuery() {
        // 실행 버튼 클릭 시 쿼리 실행
        jButton_Execute.addActionListener(e -> {
            executeQuery();
        });
        
        // Ctrl+Enter 키 바인딩: JTextArea에서 Ctrl+Enter 누르면 실행 버튼 클릭과 동일 동작
        jTextArea_Query.getInputMap().put(KeyStroke.getKeyStroke("control ENTER"), "executeQuery");
        jTextArea_Query.getActionMap().put("executeQuery", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton_Execute.doClick();
            }
        });
    }
    
    private void executeQuery() {
        String query = jTextArea_Query.getText().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(InputPanel.this, "쿼리를 입력하세요.");
            return;
        }
        try {
            Connection connection = DbControl.getInstance().getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(InputPanel.this, "DB 연결이 없습니다.");
                return;
            }
            // 사용자 쿼리 실행 -> QueryResultEvent 반환
            QueryResultEvent resultEvent = DBQueryManager.executeQuery(connection, query);
            
            // 이벤트 큐에 결과 이벤트 push
            ClientMain.eventQueueWorker.push(resultEvent);
            
            // 쿼리 종류에 따라 설명 메시지 구성
            String descriptionMessage;
            String queryUpper = query.toUpperCase();
            if (queryUpper.startsWith("SELECT")) {
                descriptionMessage = resultEvent.getTableData().size() + "개의 row를 출력했습니다.";
            }
            else if (queryUpper.startsWith("INSERT")) {
                descriptionMessage = resultEvent.getTableData().get(0).get(0).toString();
            }
            else if (queryUpper.startsWith("UPDATE")) {
                descriptionMessage = resultEvent.getTableData().get(0).get(0).toString();
            }
            else if (queryUpper.startsWith("DELETE")) {
                descriptionMessage = resultEvent.getTableData().get(0).get(0).toString();
            }
            else {
                descriptionMessage = resultEvent.getTableData().get(0).get(0).toString();
            }
            
            jTextField_Description.setText(descriptionMessage);
            blinkDescription();
        }
        catch (SQLException ex) {
            LOGGER.error("쿼리 실행 중 예외 발생", ex);
            jTextField_Description.setText("쿼리 실행 오류: " + ex.getMessage());
            // 오류 발생 시 ResultPanel 테이블 초기화를 위해 빈 QueryResultEvent push
            QueryResultEvent emptyEvent = new QueryResultEvent(new Vector<>(), new Vector<>());
            ClientMain.eventQueueWorker.push(emptyEvent);
        }
    }
    
    // 깜빡임 효과: 설명창의 글자 색상을 일시적으로 투명하게 변경하여 실행 완료를 강조
    private void blinkDescription() {
        final Color original = jTextField_Description.getForeground();
        final Color blinkColor = new Color(0, 0, 0, 0);  // 투명
        int delay = 200; // 깜빡임 지속 시간 (밀리초)
        jTextField_Description.setForeground(blinkColor);
        javax.swing.Timer timer = new javax.swing.Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextField_Description.setForeground(original);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void push(Object o) {
        if (o instanceof QueryResultEvent) {
            QueryResultEvent event = (QueryResultEvent) o;
            if (event.getColumnNames().isEmpty()) {
                return;
            }
            String message;
            if (event.getColumnNames().size() == 1 &&
                "Message".equalsIgnoreCase(event.getColumnNames().get(0))) {
                message = event.getTableData().get(0).get(0).toString();
            }
            else {
                int rowCount = event.getTableData().size();
                message = rowCount + "개의 row를 출력했습니다.";
            }
            javax.swing.SwingUtilities.invokeLater(() -> {
                jTextField_Description.setText(message);
            });
        }
    }
}

