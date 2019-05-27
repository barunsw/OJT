package com.barunsw.ojt.gtkim.day06;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);

	private final Dimension LABEL_SIZE = new Dimension(80, 22);
	private final String[] FRUITS = { "apple", "banana", "grape", "mango", "melon" };

	private JLabel jLabel_Name 	  = new JLabel("이름");
	private JLabel jLabel_Gender  = new JLabel("성별");
	private JLabel jLabel_Address = new JLabel("주소");
	private JLabel jLabel_Fruit	  = new JLabel("과일");

	private JTextField jTextField_Name		= new JTextField();
	private JRadioButton jRadioButton_Man 	= new JRadioButton("남");
	private JRadioButton jRadioButton_Woman = new JRadioButton("여");
	private JTextArea jTextArea_Address 	= new JTextArea();
	private JButton jButton_Add 		    = new JButton("추가");
	private ButtonGroup jButton_Group 	    = new ButtonGroup();
	private JPasswordField jPasswd_Pwd		= new JPasswordField();
	private JScrollPane jScrollPane_Address = null;
	private JComboBox jComboBox_Fruit 		= null;
	private JTable	jTable_Maria 	        = null;
	
	private CardLayout cardLayout = new CardLayout();

	public TestPanel() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() {
		// 라디오 버튼 그룹화 (하나만 선택 가능하게)
		jButton_Group.add(jRadioButton_Man);
		jButton_Group.add(jRadioButton_Woman);
		
		// 스크롤 설정 
		jScrollPane_Address = new JScrollPane(jTextArea_Address,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		jComboBox_Fruit = new JComboBox<String>(FRUITS);
		
		//컴포넌트 크기 초기화
		initComponentSize();
		
		// 기본 레이아웃 사용
		//useFlowLayout();
		// 동서남북중 레이아웃
		//useBorderLayout();
		// 격자식 레이아웃 (row x col)
		useGridLayout();
		// 전환용 레이아웃
		//useCardLayout();

	}

	private void initComponentSize() {
		// setSize() vs setPreferredSize()
		// setSize 레이아웃 매니저가 사용될떄??
		jLabel_Name.setPreferredSize(LABEL_SIZE);
		jLabel_Gender.setPreferredSize(LABEL_SIZE);
		jLabel_Address.setPreferredSize(LABEL_SIZE);
		jLabel_Fruit.setPreferredSize(LABEL_SIZE);
		
		//JLabel 정렬
		jLabel_Name.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_Gender.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_Address.setHorizontalAlignment(SwingConstants.LEFT);
		jLabel_Fruit.setHorizontalAlignment(SwingConstants.CENTER);
		
		//폰트 크기, 색상 
		jLabel_Name.setFont(new Font("궁서", Font.ITALIC, 16));
		jLabel_Gender.setFont(new Font("바탕", Font.BOLD, 14));
		jLabel_Address.setFont(new Font("돋움", Font.PLAIN, 22));
		jLabel_Fruit.setFont(new Font("궁서", Font.BOLD, 28));
		
		//나머지 컴포넌트 사이즈 지정
		jTextField_Name.setPreferredSize(new Dimension(150, 22));
		jRadioButton_Man.setPreferredSize(new Dimension(80, 22));
		jRadioButton_Woman.setPreferredSize(new Dimension(80, 22));
		jTextArea_Address.setPreferredSize(new Dimension(150, 60));
		jComboBox_Fruit.setPreferredSize(new Dimension(150, 22));
		jPasswd_Pwd.setPreferredSize(new Dimension(150, 22));
	}
	
	private void useFlowLayout() {
		LOGGER.info("Flow Layout을 사용합니다.");
		// 레이아웃을 따로 설정하지 않으면 flow layout이 기본
		// this.setLayout(new FlowLayout());		
		JOptionPane.showMessageDialog(this, "기본 레이아웃 입니다.");
		
		//패널에 붙이기
		this.add(jLabel_Name);
		this.add(jTextField_Name);
		this.add(jLabel_Gender);
		this.add(jRadioButton_Man);
		this.add(jRadioButton_Woman);
		this.add(jLabel_Address);
		this.add(jScrollPane_Address);		
		this.add(jButton_Add);	
	}
	
	private void useBorderLayout() {
		LOGGER.info("Border Layout을 사용합니다.");
		this.setLayout(new BorderLayout());
		
		// 이러면 맨 나중에꺼만 보임
		// this.add(jLabel_Name, BorderLayout.WEST);
		// this.add(jLabel_Gender, BorderLayout.WEST);
		// this.add(jLabel_Address, BorderLayout.WEST);
		
		JPanel textPanel = new JPanel(new GridLayout(3, 1));
		textPanel.add(jLabel_Name);
		textPanel.add(jLabel_Gender);
		textPanel.add(jLabel_Address);
		
		JPanel genderPanel = new JPanel();
		genderPanel.add(jRadioButton_Man);
		genderPanel.add(jRadioButton_Woman);
		
		JPanel otherPanel = new JPanel(new GridLayout(3, 1));
		otherPanel.add(jComboBox_Fruit);
		otherPanel.add(genderPanel);
		otherPanel.add(jScrollPane_Address);
		
		this.add(textPanel, BorderLayout.WEST);
		this.add(otherPanel, BorderLayout.CENTER);
	}
	
	private void useGridLayout() {
		LOGGER.info("Grid Layout(4,2)을 사용합니다");
		this.setLayout(new GridLayout(5, 2));

		JPanel genderPanel = new JPanel();
		genderPanel.add(jRadioButton_Man);
		genderPanel.add(jRadioButton_Woman);
		
		//패널에 붙이기
		this.add(jLabel_Fruit);
		this.add(jComboBox_Fruit);
		this.add(jLabel_Name);
		this.add(jTextField_Name);
		this.add(jLabel_Gender);
		this.add(genderPanel);
		//this.add(jRadioButton_Man);
		//this.add(jRadioButton_Woman);
		this.add(jLabel_Address);
		this.add(jScrollPane_Address);		
		this.add(jButton_Add);
		this.add(jPasswd_Pwd);
	}
	
	private void useCardLayout() {
		LOGGER.info("Card Layout을 사용합니다.");
		this.setLayout(cardLayout);
		
		JPanel redPanel = new JPanel();
		redPanel.setBackground(Color.red);
		
		JPanel greenPanel = new JPanel();
		greenPanel.setBackground(Color.green);
		
		JPanel yellowPanel = new JPanel();
		yellowPanel.setBackground(Color.yellow);
		
		this.add(redPanel, "red");
		this.add(greenPanel, "green");
		this.add(yellowPanel, "yellow");
		
		// 화면 전환 테스트
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String count = JOptionPane.showInputDialog("화면 전환 횟수를 입력하세요 : ");
					for (int i = 0; i < Integer.parseInt(count); i++) {
						Thread.sleep(500);
						if (i % 3 == 0) {
							LOGGER.debug("red panel");
							cardLayout.show(TestPanel.this, "red");
						} else if (i % 3 == 1) {
							LOGGER.debug("green panel");
							cardLayout.show(TestPanel.this, "green");
						} else {
							LOGGER.debug("yellow panel");
							cardLayout.show(TestPanel.this, "yellow");
						}
					}
				} catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			}
		}).start();
		
		// Runnable은 FunctionalInterface라 람다식사용이 가능함
		/*
		new Thread(() -> { 
			try {
				for (int i = 0; i < 10; i++) {
					Thread.sleep(500);
					if (i % 3 == 0) {
						LOGGER.debug("red panel");
						cardLayout.show(TestPanel.this, "red");
					} else if (i % 3 == 1) {
						LOGGER.debug("green panel");
						cardLayout.show(TestPanel.this, "green");
					} else {
						LOGGER.debug("yellow panel");
						cardLayout.show(TestPanel.this, "yellow");
					}
				}
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}).start();
		*/
	}
}
