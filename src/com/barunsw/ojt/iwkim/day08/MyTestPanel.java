package com.barunsw.ojt.iwkim.day08;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Panel클래스에선 컴포넌트 객체 생성, 생성자로 레이아웃 설정 및 이벤트 설정을 해주면된다.
public class MyTestPanel extends JPanel{
	private static Logger LOGGER = LogManager.getLogger(MyTestPanel.class);
	
	private final Dimension LABEL_DEMENSION = new Dimension(120, 22);
	
	// 레이아웃 객체 생성!
	private GridBagLayout gridbagLayout = new GridBagLayout();
	
	// 컴포넌트 객체 생성
	private JLabel jLabel_Name = new JLabel("이름");
	private JLabel jLabel_Gender = new JLabel("성별");
	private JLabel jLabel_Age = new JLabel("나이");
	private JLabel jLabel_Address = new JLabel("주소");
	
	private JTextField jTextField_Name = new JTextField();
	
	private JRadioButton jRadioButton_Man = new JRadioButton("남");
	private JRadioButton jRadioButton_Woman = new JRadioButton("여");
	private ButtonGroup buttonGroup_Gender = new ButtonGroup();
	
	private JSpinner jSpinner_Age = new JSpinner();
	
	// 반드시 JScrollPane과 같이 써줘야 한다. 
	// getViewport().add(jTextArea_Address) 메서드를 같이 써줘야 한다!
	private JTextArea jTextArea_Address = new JTextArea();
	private JScrollPane jScrollPane_Address = new JScrollPane();
	
	// 패널 추가
	private JPanel jPanel_Command = new JPanel();
	
	JButton jButton_Add = new JButton("추가");
	JButton jButton_Change = new JButton("수정");
	JButton jButton_Delete = new JButton("삭제");
	
	public MyTestPanel() {
		try {
			initComponent();
			initEvent();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	// 컨테이너의 레이아웃 및 폰트 설정
	private void initComponent() throws Exception {
		this.setLayout(gridbagLayout);
		// 추가한 패널도 레이아웃을 지정해주어야 한다.
		jPanel_Command.setLayout(gridbagLayout);
		
		// 폰트 객체 지정 Font(String name, int style, int size)
		Font font_Label = new Font("Gothic", Font.BOLD, 17);
		
		jLabel_Name.setPreferredSize(LABEL_DEMENSION);
		
		jLabel_Name.setMinimumSize(LABEL_DEMENSION);
		
		jLabel_Name.setFont(font_Label);
		jLabel_Gender.setFont(font_Label);
		jLabel_Age.setFont(font_Label);
		jLabel_Address.setFont(font_Label);
		jButton_Add.setFont(font_Label);
		jButton_Change.setFont(font_Label);
		jButton_Delete.setFont(font_Label);
		jSpinner_Age.setFont(font_Label);
		
		this.add(jLabel_Name, new GridBagConstraints(
					0, 0, 1, 1,
					0.0, 0.0, 
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(5, 5, 5, 5),
					0, 0));
		
		this.add(jTextField_Name, new GridBagConstraints(
					1, 0, 4, 1,
					1.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(5, 0, 5, 5),
					0, 0));
		
		// 같은 그룹에 묶이면 한쪽이 선택되었을 때 나머지는 선택이 되지 않는다.
		buttonGroup_Gender.add(jRadioButton_Man);
		buttonGroup_Gender.add(jRadioButton_Woman);
		
		this.add(jLabel_Gender, new GridBagConstraints(
					0, 1, 1, 1,
					0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 5, 5, 5),
					0, 0));
		
		this.add(jRadioButton_Man, new GridBagConstraints(
					1, 1, 1, 1,
					0.2, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5),
					0, 0));
		
		this.add(jRadioButton_Woman, new GridBagConstraints(
					2, 1, 1, 1,
					1.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5),
					0, 0));
		
		this.add(jLabel_Age, new GridBagConstraints(
					3, 1, 1, 1,
					0.0, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.NONE,
					new Insets(0, 0, 5, 5),
					0, 0));
		
		jSpinner_Age.setPreferredSize(new Dimension(50, 30));
		jSpinner_Age.setValue(25);
		
		this.add(jSpinner_Age, new GridBagConstraints(
					4, 1, 1, 1,
					0.5, 0.0,
					GridBagConstraints.EAST, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5),
					0, 0));
		
		this.add(jLabel_Address, new GridBagConstraints(
					0, 2, 1, 1,
					0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 5, 5, 5),
					0, 0));
		
		this.add(jScrollPane_Address, new GridBagConstraints(
					1, 2, 4, 1,
					1.0, 1.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 5),
					0, 0));
		
		jScrollPane_Address.getViewport().add(jTextArea_Address);
		
		this.add(jPanel_Command, new GridBagConstraints(
					0, 3, 5, 1,
					0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 5, 5, 5),
					0, 0));
	
		jPanel_Command.add(jButton_Add, new GridBagConstraints(
					0, 0, 1, 1,
					0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 5, 0, 5),
					0, 0));
		
		jPanel_Command.add(jButton_Change, new GridBagConstraints(
					1, 0, 1, 1,
					0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5),
					0, 0));
		
		jPanel_Command.add(jButton_Delete, new GridBagConstraints(
					2, 0, 1, 1,
					0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5),
					0, 0));
	}
	
	// 이벤트 넣고 싶은 컴포넌트에 addActionListener()메서드사용! 
	// 추가, 수정, 삭제 버튼에 이벤트를 넣고 싶다. -> 그러면 3개를 addActionListener해주자!
	// 파라미터는 ActionListener를 구현한 클래스 객체
	private void initEvent() throws Exception {
		jButton_Add.addActionListener(new MyTestPanel_jButton_Add_ActionListener(this));
		jButton_Change.addActionListener(new MyTestPanel_jButton_Add_ActionListener(this));
		jButton_Delete.addActionListener(new MyTestPanel_jButton_Add_ActionListener(this));
	}
	
	void jButton_Add_actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "추가되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
	}
	
	void jButton_Change_actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "수정되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
	}
	
	void jButton_Delete_actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "삭제되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
	}
}

// AddListener는 메서드가 한개이므로 구현해서 사용한다.
class MyTestPanel_jButton_Add_ActionListener implements ActionListener {
	
	MyTestPanel adaptee;
	
	public MyTestPanel_jButton_Add_ActionListener(MyTestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	// getSource() : 해당 이벤트를 일어나게 만든 이벤트 소스를 반환한다.
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == adaptee.jButton_Add) {
			adaptee.jButton_Add_actionPerformed(e);
		}
		else if (e.getSource() == adaptee.jButton_Change) {
			adaptee.jButton_Change_actionPerformed(e);
		}
		else {
			adaptee.jButton_Delete_actionPerformed(e);
		}
	}
}