package com.barunsw.ojt.yjkim.day06;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TestPanel extends JPanel{
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);
	//LABEL들의 사이즈를 일정하게 하기위해 final 상수 선언
	private final Dimension LABEL_SIZE = new Dimension(80,22);
	//InputField에 대한 LABEL
	private JLabel jLabel_Name 		= new JLabel("이름");
	private JLabel jLabel_Gender 	= new JLabel("성별");
	private JLabel jLabel_Address 	= new JLabel("주소");
	
	//InputField
	private JTextField jTextField_Name 		= new JTextField();
	private JRadioButton jRadioButton_Man	= new JRadioButton("남");
	private JRadioButton jRadioButton_Woman = new JRadioButton("여");
	//private JTextArea jTextArea_Address 	= new JTextArea();
	
	
	//jscrollPane
	//private JScrollPane jScrollPane 	= new JScrollPane(jTextArea_Address);

	//Button 
	private JButton jButton_Add  	    = new JButton("추가");

	//flowLayout
	private FlowLayout flowLayout 		= new FlowLayout();
	//borderLayout
	private BorderLayout borderLayout 	= new BorderLayout();
	//gridLayout
	private GridLayout gridLayout 		= new GridLayout();
	//gridBagLayout
	private GridBagLayout gridBagLayout = new GridBagLayout();
	//cardLayout
	private CardLayout cardLayout		= new CardLayout();
	
	//폰트 설정
	private Font f1 = new Font("돋움", Font.PLAIN, 15);
	private Font f2 = new Font("궁서", Font.BOLD, 20);
	private Font f3 = new Font("바탕", Font.ITALIC, 30);
	
	public TestPanel() {
		try {
			initComponent();
		}catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception{
		JTextArea txt = new JTextArea(10,20);
		JScrollPane scroll = new JScrollPane(txt);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		//Layout 설정 - FlowLayout 
		//흐름대로 가로 순서로 배치가 된다.
		this.setLayout(new FlowLayout());
		
		
		//패널의 배경색을 주황색으로 변경
		this.setBackground(Color.ORANGE);

		//setPrefferedSize는 컴포넌트의 사이즈를 설정한다.
		jLabel_Name.setPreferredSize(LABEL_SIZE);
		jLabel_Gender.setPreferredSize(LABEL_SIZE);
		jLabel_Address.setPreferredSize(LABEL_SIZE);
		
		//LABEL 폰트 설정
		jLabel_Name.setFont(f1);
		jLabel_Gender.setFont(f2);
		jLabel_Address.setFont(f3);
			
		jTextField_Name.setPreferredSize(new Dimension(120,22));
		jRadioButton_Man.setPreferredSize(new Dimension(60,22));
		jRadioButton_Woman.setPreferredSize(new Dimension(60,22));
		//jTextArea_Address.setPreferredSize(new Dimension(120,60));
		
		jRadioButton_Man.setBackground(Color.white);
		jRadioButton_Woman.setBackground(Color.white);
		
		//jScrollPane

		//버튼 클릭 리스너 
		jButton_Add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				if(btn.getText().equals("Click")) {
					btn.setText("Hello");
				}else {
					btn.setText("Click");
				}
			}
			
		});
		
		
		this.add(jLabel_Name);
		this.add(jTextField_Name);
		this.add(jLabel_Gender);
		this.add(jRadioButton_Man);
		this.add(jRadioButton_Woman);
		this.add(jLabel_Address);
		this.add(scroll);
		this.add(jButton_Add);
		
	}

}
class MyKeyListener extends KeyAdapter{
	
	public void keyPressed(KeyEvent e) {
		
	}
}

