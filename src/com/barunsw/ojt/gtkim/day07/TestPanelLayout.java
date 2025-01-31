package com.barunsw.ojt.gtkim.day07;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanelLayout extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(TestPanelLayout.class);
	
	private final Dimension SIZE 	   = new Dimension(90, 30);
	private final String[] PHONE_LOCAL = { "010", "011", "012", "016", "017", "018", "019", "070" };
	private final Font FONT 		   = new Font("바탕", Font.BOLD, 16);
	
	private JLabel jLabel_Name 	  	= new JLabel("이름");
	private JLabel jLabel_Gender  	= new JLabel("성별");
	private JLabel jLabel_Age 	  	= new JLabel("나이");
	private JLabel jLabel_Phone   	= new JLabel("핸드폰");
	private JLabel jLabel_Address 	= new JLabel("주소");
	private JLabel jLabel_Inroduce 	= new JLabel("소개");
	
	private JTextField jTextField_Name 	    = new JTextField();
	private JTextField jTextField_Age		= new JTextField();
	private JTextField jTextField_Phone_Mid = new JTextField();
	private JTextField jTextField_Phone_Suf = new JTextField();
	
	private JRadioButton jRadioButton_Man   = new JRadioButton("남");
	private JRadioButton jRadioButton_Woman = new JRadioButton("여");
	private ButtonGroup buttonGroup_Gender  = new ButtonGroup();
	
	private JTextArea jTextArea_Adress 		  = new JTextArea();
	private JTextArea jTextArea_Introduce     = new JTextArea();
	private JScrollPane jScrollPane_Address   = null;
	private JScrollPane jScrollPane_Introduce = new JScrollPane();
	
	private JComboBox jComboBox_Phone_Pre = null;

	private JButton jButton_Add    = new JButton("추가");
	private JButton jButton_Delete = new JButton("삭제");
	private JButton jButton_Reload = new JButton("재조회");
	
	
	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	private String layout = "";
	
	public TestPanelLayout() {
		this("gridbagLayout");
	}
	
	public TestPanelLayout(String layout) {
		try {
			initComponent(layout);
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initComponent(String layout) {
		this.layout = layout;
		// 라디오 그룹화
		buttonGroup_Gender.add(jRadioButton_Man);
		buttonGroup_Gender.add(jRadioButton_Woman);

		// 스크롤팬 초기화
		jTextArea_Adress.setLineWrap(true);
		jScrollPane_Address = new JScrollPane(jTextArea_Adress, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		jScrollPane_Introduce.getViewport().add(jTextArea_Introduce);

		// 콤보박스 초기화
		jComboBox_Phone_Pre = new JComboBox<String>(PHONE_LOCAL);

		// 컴포넌트 크기 초기화
		initSize();

		// Layouts
		switch (layout) {
		case "flowLayout" :
			useFlowLayout();
			break;
		case "borderLayout" :
			useBorderLayout();
			break;
		case "gridLayout" :
			useGridLayout();
			break;
		case "gridbagLayout" :
			useGridBagLayout();
			break;
		case "userLayout" :
			useUserLayout();
			break;
		case "cardLayout" :
			useCardLayout();
			break;
		default :
			LOGGER.debug("잘못된 레이아웃 입니다");
		}
	}
	
	private void initSize() {
		//컴포넌트 사이즈 초기화
		jLabel_Name.setPreferredSize(SIZE);
		jLabel_Gender.setPreferredSize(SIZE);
		jLabel_Age.setPreferredSize(SIZE);
		jLabel_Phone.setPreferredSize(SIZE);
		jLabel_Address.setPreferredSize(SIZE);
		jLabel_Inroduce.setPreferredSize(SIZE);

		jLabel_Name.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_Gender.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_Age.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_Phone.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_Address.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel_Inroduce.setHorizontalAlignment(SwingConstants.CENTER);

		jLabel_Name.setFont(FONT);
		jLabel_Gender.setFont(FONT);
		jLabel_Age.setFont(FONT);
		jLabel_Phone.setFont(FONT);
		jLabel_Address.setFont(FONT);
		jLabel_Inroduce.setFont(FONT);

		jTextField_Name.setPreferredSize(new Dimension(150, 25));
		jRadioButton_Man.setPreferredSize(new Dimension(60, 25));
		jRadioButton_Woman.setPreferredSize(new Dimension(60, 25));
		jTextField_Age.setPreferredSize(new Dimension(50, 25));
		jComboBox_Phone_Pre.setPreferredSize(new Dimension(70, 35));
		jTextField_Phone_Mid.setPreferredSize(new Dimension(70, 35));
		jTextField_Phone_Suf.setPreferredSize(new Dimension(70, 35));
		jScrollPane_Address.setPreferredSize(new Dimension(200, 60));
		jScrollPane_Introduce.setPreferredSize(new Dimension(200, 60));
		
		jTextField_Name.setFont(FONT);
		jRadioButton_Man.setFont(FONT);
		jRadioButton_Woman.setFont(FONT);
		jTextField_Age.setFont(FONT);
		jComboBox_Phone_Pre.setFont(FONT);
		jTextField_Phone_Mid.setFont(FONT);
		jTextField_Phone_Suf.setFont(FONT);
		jTextArea_Adress.setFont(FONT);
		jTextArea_Introduce.setFont(FONT);
		
		jButton_Add.setPreferredSize(SIZE);
		jButton_Delete.setPreferredSize(SIZE);
		jButton_Reload.setPreferredSize(SIZE);
	}

	private void useUserLayout() {
		LOGGER.debug("사용자 지정 레이아웃 입니다.");
		this.setLayout(null);
		
		// 직접 좌표를 찍어주어야 한다.
		jLabel_Name.setBounds(50, 20, 90, 25);
		jTextField_Name.setBounds(150, 20, 150, 25);
		jLabel_Gender.setBounds(50, 80, 90, 25);
		jRadioButton_Man.setBounds(160, 80, 60, 25);
		jRadioButton_Woman.setBounds(220, 80, 60, 25);
		jLabel_Age.setBounds(50, 140, 90, 25);
		jTextField_Age.setBounds(150, 140, 70, 25);
		jLabel_Phone.setBounds(50, 200, 90, 25);
		jComboBox_Phone_Pre.setBounds(150, 200, 70, 25);
		jTextField_Phone_Mid.setBounds(250, 200, 70, 25);
		jTextField_Phone_Suf.setBounds(350, 200, 70, 25);
		jLabel_Address.setBounds(50, 260, 90, 25);
		jScrollPane_Address.setBounds(150, 260, 200, 60);
		
		this.add(jLabel_Name);
		this.add(jTextField_Name);
		this.add(jLabel_Gender);
		this.add(jRadioButton_Man);
		this.add(jRadioButton_Woman);
		this.add(jLabel_Age);
		this.add(jTextField_Age);
		this.add(jLabel_Phone);
		this.add(jComboBox_Phone_Pre);
		this.add(jTextField_Phone_Mid);
		this.add(jTextField_Phone_Suf);
		this.add(jLabel_Address);
		this.add(jScrollPane_Address);
	}
	
	void useFlowLayout() {
		LOGGER.debug("Flow 레이아웃 입니다.");
		this.setLayout(new FlowLayout());
		
		// 기본 레이아웃으로 가로로 순서대로 쌓인다.
		this.add(jLabel_Name);
		this.add(jTextField_Name);
		this.add(jLabel_Gender);
		this.add(jRadioButton_Man);
		this.add(jRadioButton_Woman);
		this.add(jLabel_Age);
		this.add(jTextField_Age);
		this.add(jLabel_Phone);
		this.add(jComboBox_Phone_Pre);
		this.add(jTextField_Phone_Mid);
		this.add(jTextField_Phone_Suf);
		this.add(jLabel_Address);
		this.add(jScrollPane_Address);
		this.add(jButton_Add);
		this.add(jButton_Delete);
		this.add(jButton_Reload);
	}
	
	void useBorderLayout() {
		LOGGER.debug("Border 레이아웃 입니다.");
		this.setLayout(new BorderLayout());
		
		// 원하는 방위에 여러 컴포넌트를 한번에 올리려면 다른 컨테이너에 쌓아두고 컨테이너를 올려야 된다.
		JPanel jPanel_Labels = new JPanel(new GridLayout(5, 1));
		jPanel_Labels.add(jLabel_Name);
		jPanel_Labels.add(jLabel_Gender);
		jPanel_Labels.add(jLabel_Age);
		jPanel_Labels.add(jLabel_Phone);
		jPanel_Labels.add(jLabel_Address);
		
		JPanel jPanel_Gender = new JPanel();
		jPanel_Gender.add(jRadioButton_Man);
		jPanel_Gender.add(jRadioButton_Woman);
		
		JPanel jPanel_Phone = new JPanel();
		jPanel_Phone.add(jComboBox_Phone_Pre);
		jPanel_Phone.add(jTextField_Phone_Mid);
		jPanel_Phone.add(jTextField_Phone_Suf);
		
		JPanel jPanel_Components = new JPanel(new GridLayout(5, 1));
		jPanel_Components.add(jTextField_Name);
		jPanel_Components.add(jPanel_Gender);
		jPanel_Components.add(jTextField_Age);
		jPanel_Components.add(jPanel_Phone);
		jPanel_Components.add(jScrollPane_Address);
		
		JPanel jPanel_Command = new JPanel();
		jPanel_Command.add(jButton_Add);
		jPanel_Command.add(jButton_Delete);
		jPanel_Command.add(jButton_Reload);
		
		this.add(jPanel_Labels, BorderLayout.WEST);
		this.add(jPanel_Components, BorderLayout.CENTER);	
		this.add(jPanel_Command, BorderLayout.SOUTH);
	}
	
	private void useGridLayout() {
		LOGGER.debug("Grid 레이아웃 입니다.");
		this.setLayout(new GridLayout(6,2));

		// 생성된 격자 수를 동일한 크기로 할당받아 맨 위 왼쪽부터 차례대로 들어간다.
		JPanel jPanel_Gender = new JPanel();
		jPanel_Gender.add(jRadioButton_Man);
		jPanel_Gender.add(jRadioButton_Woman);
		
		JPanel jPanel_Phone = new JPanel();
		jPanel_Phone.add(jComboBox_Phone_Pre);
		jPanel_Phone.add(jTextField_Phone_Mid);
		jPanel_Phone.add(jTextField_Phone_Suf);
		
		JPanel jPanel_Command = new JPanel();
		jPanel_Command.add(jButton_Add);
		jPanel_Command.add(jButton_Delete);
		jPanel_Command.add(jButton_Reload);
		
		this.add(jLabel_Name);
		this.add(jTextField_Name);
		this.add(jLabel_Gender);
		this.add(jPanel_Gender);
		this.add(jLabel_Age);
		this.add(jTextField_Age);
		this.add(jLabel_Phone);
		this.add(jPanel_Phone);
		this.add(jLabel_Address);
		this.add(jScrollPane_Address);	
		this.add(new JPanel());
		this.add(jPanel_Command);
	}
	
	private void useCardLayout() {
		LOGGER.debug("Card 레이아웃을 사용합니다");
		CardLayout cardLayout = new CardLayout();
		// 다수의 패널을 미리 생성하여 원하는 패널만 맨 위로 올리는 방식이다.
		this.setLayout(cardLayout);
		
		// 패널 전환 버튼
		JButton jButton_Change = new JButton("Next");
		jButton_Change.setPreferredSize(new Dimension(70, 70));
		jButton_Change.setBackground(Color.cyan);
		
		JPanel jPanel_Name = new JPanel();
		jPanel_Name.add(jLabel_Name);
		jPanel_Name.add(jTextField_Name);
		jPanel_Name.add(jButton_Change);
		jButton_Change.setActionCommand("name");
		
		JPanel jPanel_Gender = new JPanel();
		jPanel_Gender.add(jLabel_Gender);
		jPanel_Gender.add(jRadioButton_Man);
		jPanel_Gender.add(jRadioButton_Woman);

		JPanel jPanel_Age = new JPanel();
		jPanel_Age.add(jLabel_Age);
		jPanel_Age.add(jTextField_Age);

		JPanel jPanel_Phone = new JPanel();
		jPanel_Phone.add(jComboBox_Phone_Pre);
		jPanel_Phone.add(jTextField_Phone_Mid);
		jPanel_Phone.add(jTextField_Phone_Suf);

		JPanel jPanel_Address = new JPanel();
		jPanel_Address.add(jLabel_Address);
		jPanel_Address.add(jScrollPane_Address);

		this.add(jPanel_Name, "name");
		this.add(jPanel_Gender, "gender");
		this.add(jPanel_Age, "age");
		this.add(jPanel_Phone, "phone");
		this.add(jPanel_Address, "address");
		
		jButton_Change.addActionListener( e -> {
			String action = e.getActionCommand();
			switch (action) {
			case "name" :
				LOGGER.debug("name 패널 -> gender 패널");
				jPanel_Gender.add(jButton_Change);
				jButton_Change.setActionCommand("gender");
				cardLayout.show(this, "gender");
				break;
			case "gender" : 
				LOGGER.debug("gender 패널 -> age 패널");
				jPanel_Age.add(jButton_Change);
				jButton_Change.setActionCommand("age");
				cardLayout.show(this, "age");
				break;
			case "age" :
				LOGGER.debug("age 패널 -> phone 패널");
				jPanel_Phone.add(jButton_Change);
				jButton_Change.setActionCommand("phone");
				cardLayout.show(this, "phone");
				break;
			case "phone" : 
				LOGGER.debug("phone 패널 -> address 패널");
				jPanel_Address.add(jButton_Change);
				jButton_Change.setActionCommand("address");
				cardLayout.show(this, "address");
				break;
			case "address" :
				LOGGER.debug("address 패널 -> name 패널");
				jPanel_Name.add(jButton_Change);
				jButton_Change.setActionCommand("name");
				cardLayout.show(this, "name");
				break;
			default :
				LOGGER.debug("잘못된 값입니다.");
				System.exit(1);
			}
		});	
	}
	
	private void useGridBagLayout() {
		LOGGER.debug("GridBag 레이아웃입니다.");
		this.setLayout(gridBagLayout);
		
		// 세세한 배치 조절이 가능한 만능형 레이아웃
		JPanel jPanel_Gender	= new JPanel();
		JPanel jPanel_Phone 	= new JPanel();
		JPanel jPanel_Command 	= new JPanel();
	
		jPanel_Gender.setLayout(gridBagLayout);
		jPanel_Phone.setLayout(gridBagLayout);
		jPanel_Command.setLayout(gridBagLayout);
		
		// GridBagConstraints(Grid X값, Grid Y 값 , 차지할 가로 Grid 폭, 차지할 세로 Grid 폭,
		//			해당 컴포넌트가 차지할 가로 비율, 해당 컴포넌트가 차지할 세로 비율
		//			Grid 안에서 컴포넌트의 위치, Grid 안에서 컴포넌트 채우기 조정
		//			Grid 사이의 간격을 조정 
		//			Grid 내부에서 컴포넌트와의 간격을 조정하는 x, y 값
		this.add(jLabel_Name,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		this.add(jTextField_Name,
				new GridBagConstraints(1, 0, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 0, 5, 5),
						0, 0));

		this.add(jLabel_Gender,
				new GridBagConstraints(0, 1, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0, 0));
		
		this.add(jPanel_Gender,
				new GridBagConstraints(1, 1, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0),
						0, 0));
		
		this.add(jLabel_Age,
				new GridBagConstraints(0, 2, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0, 0));
		
		this.add(jTextField_Age,
				new GridBagConstraints(1, 2, 1, 1,
						0.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		this.add(jLabel_Phone,
				new GridBagConstraints(0, 3, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0, 0));
		
		this.add(jPanel_Phone,
				new GridBagConstraints(1, 3, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0),
						0, 0));	
		
		this.add(jLabel_Address,
				new GridBagConstraints(0, 4, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0, 0));	
		
		this.add(jScrollPane_Address,
				new GridBagConstraints(1, 4, 1, 1,
						1.0, 0.3,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));	
		
		this.add(jLabel_Inroduce,
				new GridBagConstraints(0, 5, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0, 0));
		
		this.add(jScrollPane_Introduce,
				new GridBagConstraints(1, 5, 1, 1,
						0.0, 0.7,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		this.add(jPanel_Command,
				new GridBagConstraints(0, 6, 2, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0)
						,0, 0));	
		
		
		jPanel_Gender.add(jRadioButton_Man,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		jPanel_Gender.add(jRadioButton_Woman,
				new GridBagConstraints(1, 0, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));

		jPanel_Phone.add(jComboBox_Phone_Pre,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		jPanel_Phone.add(jTextField_Phone_Mid,
				new GridBagConstraints(1, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));
		

		jPanel_Phone.add(jTextField_Phone_Suf,
				new GridBagConstraints(2, 0, 1, 1,
						1.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));
	
		jPanel_Command.add(jButton_Add,
				new GridBagConstraints(0, 0, 1, 1,
						1.0, 0.0,
						GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		jPanel_Command.add(jButton_Delete,
				new GridBagConstraints(1, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 0, 5, 5),
						0, 0));
		
		jPanel_Command.add(jButton_Reload,
				new GridBagConstraints(2, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 0, 5, 5),
						0, 0));						
	}
}
