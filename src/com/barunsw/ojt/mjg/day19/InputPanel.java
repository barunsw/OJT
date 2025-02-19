package com.barunsw.ojt.mjg.day19;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputPanel extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(InputPanel.class);

	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	private JButton jButton_Execute			 	= new JButton("실행");  // 실행 버튼
	private JTextArea jTextArea_Query 			= new JTextArea();     // 쿼리 입력 필드
	private JScrollPane jScrollPane_Query 		= new JScrollPane(jTextArea_Query);
	private JTextField jTextField_Description 	= new JTextField();    // 설명 창
	
	public InputPanel() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
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

}
