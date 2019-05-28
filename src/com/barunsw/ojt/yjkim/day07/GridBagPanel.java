package com.barunsw.ojt.yjkim.day07;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GridBagPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(GridBagPanel.class);
	
	GridBagLayout gridBagLayout = new GridBagLayout();
	
	private JPanel jPanel_Gender = new JPanel();
	private JPanel jPanel_Command = new JPanel();
	
	private JScrollPane jScrollPane_Intro = new JScrollPane();
	
	private JLabel jLabel_Id 		= new JLabel("아이디 ");
	private JLabel jLabel_Pw 		= new JLabel("비밀번호 ");
	private JLabel jLabel_Name 		= new JLabel("이름 ");
	private JLabel jLabel_Phone 	= new JLabel("전화");
	private JLabel jLabel_Address 	= new JLabel("주소");
	private JLabel jLabel_Birth		= new JLabel("생일");
	private JLabel jLabel_Work 		= new JLabel("직업");
	private JLabel jLabel_Gender 	= new JLabel("성별");
	private JLabel jLabel_Email 	= new JLabel("이메일");
	private JLabel jLabel_Intro 	= new JLabel("자기소개");
	
	
	private JTextField jTextField_Id 		= new JTextField();
	private JPasswordField jPasswordField_Pw= new JPasswordField();
	private JTextField jTextField_Name 		= new JTextField();
	private JTextField jTextField_Phone1 	= new JTextField(6);
	private JTextField jTextField_Phone2 	= new JTextField(6);
	private JTextField jTextField_Phone3 	= new JTextField(6);
	private JTextField jTextField_Address 	= new JTextField();
	private JTextField jTextField_Year		= new JTextField(6);
	private JTextField jTextField_Month		= new JTextField(6);
	private JTextField jTextField_Date		= new JTextField(6);
	private JComboBox  jComboBox_Work;
	private JRadioButton jRadioButton_Man 	= new JRadioButton("남");
	private JRadioButton jRadioButton_Woman = new JRadioButton("여");
	private JTextField jTextField_Email		= new JTextField();
	private JTextArea jTextArea_Intro		= new JTextArea();
	
	private String[] workArray = {"학생","직장인","주부","기타"};
	
	private JButton jButton_Insert 	= new JButton("가입");
	private JButton jButton_Cancle	= new JButton("취소");
	
	
	
	public GridBagPanel() {
		try {
			initComponent();
		}catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	//GridBagConstraint(x좌표,y좌표,x격자,y격자,x비율,y비율,격자 안 위치, 격자보다 작을 때의 처리 지정)
	void initComponent() {
		this.setLayout(gridBagLayout);
		jPanel_Gender.setLayout(gridBagLayout);
		jPanel_Command.setLayout(gridBagLayout);
		
		//아이디  GridBagLayout에 추가
		this.add(jLabel_Id, 
				new GridBagConstraints(0, 0, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		this.add(jTextField_Id, 
				new GridBagConstraints(1, 0, 1, 1, 
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		//비밀번호 GridLayout에 추가
		this.add(jLabel_Pw, 
				new GridBagConstraints(0, 1, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		this.add(jPasswordField_Pw, 
				new GridBagConstraints(1, 1, 1, 1, 
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		//이름 GridLayout에 추가
		this.add(jLabel_Name, 
				new GridBagConstraints(0, 2, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		this.add(jTextField_Name,
				new GridBagConstraints(1, 2, 1, 1, 
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		//전화 GridLayout에 추가
		this.add(jLabel_Phone,
				new GridBagConstraints(0, 3, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));

		//전화번호를 담을 FlowLayout 패널을 생성하여 전화번호 텍스트 필드를 담는다.
		JPanel jPanel_Phone = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jPanel_Phone.add(jTextField_Phone1);
		jPanel_Phone.add(jTextField_Phone2);
		jPanel_Phone.add(jTextField_Phone3);
		
		//전화번호 텍스트필드를 담은 FlowLayout 패널을 GridLayout 패널에 담는다.
		this.add(jPanel_Phone,
				new GridBagConstraints(1, 3, 3, 1, 
						1.0, 0.0,
						GridBagConstraints.EAST, GridBagConstraints.BOTH,
						new Insets(5, 0, 5, 5),
						0, 0));
		
		//주소 GridLayout에 추가
		this.add(jLabel_Address,
				new GridBagConstraints(0, 4, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		this.add(jTextField_Address,
				new GridBagConstraints(1, 4, 1, 1, 
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		//생년월일 GridLayout에 추가
		this.add(jLabel_Birth,
				new GridBagConstraints(0, 5, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		//FlowLayout 패널을 생성하여 생년월일 텍스트 필드를 담는다.
		JPanel jPanel_Birth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		jPanel_Birth.add(jTextField_Year);
		jPanel_Birth.add(jTextField_Month);
		jPanel_Birth.add(jTextField_Date);
		
		//생년월일이 담긴 FlowLayout 텍스트 필드를 GridLayout에 담는다.
		this.add(jPanel_Birth,
				new GridBagConstraints(1, 5, 3, 1, 
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 0, 5, 5),
						0, 0));
		
		
		//콤보 박스
		jComboBox_Work = new JComboBox(workArray);
		
		this.add(jLabel_Work,
				new GridBagConstraints(0, 6, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		this.add(jComboBox_Work,
				new GridBagConstraints(1, 6, 1, 1, 
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		//성별 
		this.add(jLabel_Gender,
				new GridBagConstraints(0, 7, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0,0));
		this.add(jPanel_Gender,
				new GridBagConstraints(1, 7, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 0, 5, 5),
						0,0));
		//성별 패널에 라디오버튼 (남자,여자)를 담는다.
		jPanel_Gender.add(jRadioButton_Man,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5,5,5,5),
						0,0));
		jPanel_Gender.add(jRadioButton_Woman,
				new GridBagConstraints(1, 0, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5,5,5,5),
						0,0));
		
		//이메일 
		this.add(jLabel_Email,
				new GridBagConstraints(0, 8, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0,0));	
		
		this.add(jTextField_Email,
				new GridBagConstraints(1, 8, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 0, 5, 5),
						0,0));	
		
		
		//자기소개
		this.add(jLabel_Intro,
				new GridBagConstraints(0, 9, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0,0));
		//스크롤바를 위해 스크롤페인을 추가한다.
		this.add(jScrollPane_Intro,
				new GridBagConstraints(1, 9, 1, 1,
						1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 0, 5, 5),
						0,0));	
		//스크롤페인에 TextArea를 담는다.
		jScrollPane_Intro.getViewport().add(jTextArea_Intro);
		
		
		//가입 , 취소 를 위한 GridBagLayout 패널
		this.add(jPanel_Command,
				new GridBagConstraints(0, 10, 2, 1,
				1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0,0,0,0),
				0,0));
		
		//가입 버튼을 패널에 넣는다.
		jPanel_Command.add(jButton_Insert,
				new GridBagConstraints(0, 0, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0, 0));	
		//취소 버튼을 패널에 넣는다.
		jPanel_Command.add(jButton_Cancle,
				new GridBagConstraints(1, 0, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));	

		

	}
}
