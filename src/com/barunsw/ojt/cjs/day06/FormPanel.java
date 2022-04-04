package com.barunsw.ojt.cjs.day06;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormPanel extends JPanel {
	private static Logger log = LoggerFactory.getLogger(FormFrame.class);

	private final Dimension LABEL_SIZE = new Dimension(80, 22);
	private final Dimension RADIO_BUTTON_SIZE = new Dimension(60, 22);
	// dimension == 폭과 높이를 설정

	private JLabel jLabel_Gender = new JLabel("성별");
	private JLabel jLabel_Name = new JLabel("이름");
	private JLabel jLabel_Address = new JLabel("주소");

	private JTextField jTextField_Name = new JTextField();
	//swing의 텍스트필드
	private JRadioButton jRadioButton_Man = new JRadioButton("남");
	private JRadioButton jRadioButton_Woman = new JRadioButton("여");
	//swing의 라디오버튼
	private JTextArea jTextArea_Address = new JTextArea();
	//swing의 텍스트에어리어
	private ButtonGroup buttonGroup = new ButtonGroup();
	//라디오버튼의 값을 묶을 버튼 그룹
	private JScrollPane jScrollPane_Address = new JScrollPane();
	//swing에서는 scrollpane이 있어야 스크롤이 생성된다.
	private JPanel jPanel_Command = new JPanel();
	private JButton jButton_Add = new JButton("변경");
	//swing 버튼
	private Font f1 = new Font("궁서", Font.BOLD, 20);
	private Font f2 = new Font("돋움", Font.ITALIC, 10);
	// Swing은 2D Graphic지원 가능

	private FlowLayout flowLayout = new FlowLayout();
	//기본 레이아웃, 왼쪽애서부터 입력한데로 출력
	//컨테이너의 크기를 넘어서면 자동으로 줄바꿈
	private BorderLayout borderLayout = new BorderLayout();
	//JFrame의 기본 layout
	//동서남북 그리고 중앙으로 지정가능, 배치할 때 플래그를 지정하지 않으면 기본 값은 center
	//한구역에 하나의 컴포넌트만 배치가능 == 추가적으로 넣기 위해서는 추가적인 패널이 필요하다.
	private GridLayout gridLayout = new GridLayout();
	//격자 모양으로 배치된다 (행, 열)
	//열의 갯수 이상의 컴포넌트가 필요하고, 총 사용 가능한 그리드 수보다 컴포넌트가 작거나 같아야함
	//컴포넌트의 크기는 컨테이너의 크기에 자동으로 맞춰진다.
	private CardLayout cardLayout = new CardLayout();
	//한 화면에 여러 컨테이너를 겹쳐 슬라이드처럼 사용이 가능하다.
	//하나의 프레임웍에서 여러 화면을 보여주고 싶을 때 사용한다.
	public FormPanel() {
		try {
			initComponent();
		} 
		catch (Exception e) {
			log.error(e.getMessage() + e);
		}
	}

	private void initComponent() {

		this.setLayout(new FlowLayout());
		JTextArea textArea = new JTextArea(5, 5);
		JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(4, 4, 50, 50);
		JScrollPane addressScroll = new JScrollPane(jTextArea_Address, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// 다른 컴포넌트 객체를 포함하는 객체를 생성하여 사용
		// JScrollPane.VERTICAL_SCROLLBAR_ALWAYS == 세로 스크롤 사용
		// JScrollPane.HORIZONTAL_SCROLLBAR_NEVER == 가로 스크롤 미사용

		jLabel_Name.setPreferredSize(LABEL_SIZE);
		jLabel_Gender.setPreferredSize(LABEL_SIZE);
		jLabel_Address.setPreferredSize(LABEL_SIZE);
		// setPreferredSize는 dimension 객체를 인자로 받아 해당 컴포넌트의 크기 지정
		// BorderLayout을 사용 중이면 영향 x
		jLabel_Name.setFont(f1);
		jLabel_Address.setFont(f2);
		// font 변경 가능

		jTextField_Name.setPreferredSize(new Dimension(120, 22));
		jTextField_Name.setBackground(Color.pink);
		//배경화면 색지정
		jRadioButton_Man.setPreferredSize(new Dimension(RADIO_BUTTON_SIZE));
		jRadioButton_Woman.setPreferredSize(new Dimension(RADIO_BUTTON_SIZE));

		jButton_Add.addActionListener(new ActionListener() {
			//버튼이벤트 == 버튼을 클릭하면 이벤트 발생
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				//이벤트리스너를 등록한 버튼을 실행할 때 이벤트가 실행되는 컴포넌트의 속성을 가져옴
				if (button.getText().equals("변경")) {
					button.setText("성공");
					button.setBackground(Color.green);
				} else {
					button.setText("실패");
					button.setBackground(Color.red);
				}
			}
		});

		buttonGroup.add(jRadioButton_Man);
		buttonGroup.add(jRadioButton_Woman);
		this.setVisible(true);
		this.setBackground(Color.gray);
		
		
		this.add(jLabel_Name);
		this.add(jTextField_Name);
		this.add(scrollPane);
		this.add(addressScroll);
		this.add(jTextArea_Address);
		this.add(jLabel_Gender);
		this.add(jRadioButton_Man);
		this.add(jRadioButton_Woman);
		this.add(jButton_Add);
	}

}
