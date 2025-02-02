package com.barunsw.ojt.gtkim.day06;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestDialog extends JDialog {
	private static final Logger LOGGER = LogManager.getLogger(TestDialog.class);
	
	public static final int WIDTH  = 620;
	public static final int HEIGHT = 180;
	
	private JLabel  text 		   = new JLabel("이것은 JDialog 입니까?");
	private JButton jButton_Ok	   = new JButton("예");
	private JButton jButton_Cancle = new JButton("아니요");
	
	public TestDialog() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() {
		this.setTitle("JDialog");
		// 기본레이아웃은 flowLayout
		this.setLayout(new BorderLayout());
		
		jButton_Ok.setPreferredSize(new Dimension(180, 40));
		jButton_Cancle.setPreferredSize(new Dimension(180, 40));
		
		//JLABEL 설정
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setFont(new Font("Consols", Font.BOLD, 16));
		
		JPanel jPanel_Button = new JPanel();
		jPanel_Button.add(jButton_Ok);
		jPanel_Button.add(jButton_Cancle);
		
		this.add(text, BorderLayout.CENTER);
		this.add(jPanel_Button, BorderLayout.SOUTH);
		
		// 이벤트 리스너 등록
		jButton_Ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.debug("Dialog : \"예\" 버튼이 눌렸습니다");
				setVisible(false);
			}
		});
		
		jButton_Cancle.addActionListener( e -> {
			LOGGER.debug("Dialog : \"아니오\" 버튼이 눌렸습니다.");
		});
	}
}
