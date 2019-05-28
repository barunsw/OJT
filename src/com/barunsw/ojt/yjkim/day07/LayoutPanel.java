package com.barunsw.ojt.yjkim.day07;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LayoutPanel extends JPanel implements ActionListener{
	private static final Logger LOGGER = LogManager.getLogger(LayoutPanel.class);

	//flowLayout
	private FlowLayout flowLayout 		= new FlowLayout();
	//borderLayout
	private BorderLayout borderLayout 	= new BorderLayout();
	//gridLayout
	private GridLayout gridLayout 		= new GridLayout(4, 3, 5, 5);
	//gridBagLayout
	private GridBagLayout gridBagLayout = new GridBagLayout();
	//cardLayout
	private CardLayout cardLayout		= new CardLayout(10, 10);
	
	
	private int count = 0;
	private Integer calc_result = 0;
	private String oper = "";
	private int first_value = -99999;
	private int second_value = -99999;
	
	private JTextField result = new JTextField();
	private JButton[] jbutton = new JButton[20];
	private final String[] calc_names = {"7","8","9","-","4","5","6","+","1","2","3","*","0",".","=","/"};
	public LayoutPanel(){
		try {
			//FlowLayout_initComponent();
			BorderLayout_initComponent();
			//GridLayout_initComponent();
			//CardLayout_initComponent();
			Calculator_Layout_initComponent();
			
		}catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	//계산기 Layout 
	void Calculator_Layout_initComponent() {
		this.setLayout(borderLayout);
		JPanel header_panel = new JPanel(new GridLayout(2,1,5,5));
		JLabel header = new JLabel("Swing으로 구현한 계산기");
		result.setEnabled(false);
		header_panel.add(header);
		header_panel.add(result);
		
		JPanel center_panel = new JPanel(new GridLayout(4,4));
		for(int i = 0; i < calc_names.length; i++) {
			jbutton[i] = new JButton(calc_names[i]);
			jbutton[i].addActionListener(this);
			center_panel.add(jbutton[i]);
		}
		
		JPanel footer_panel = new JPanel(new GridLayout(2,1,5,5));
		JButton clear_button = new JButton("Clear");
		clear_button.addActionListener(this);
		JLabel footer_label = new JLabel("만든이 : 김윤제");
		footer_panel.add(clear_button);
		footer_panel.add(footer_label);
		
		this.add(header_panel,BorderLayout.NORTH);
		this.add(center_panel);
		this.add(footer_panel,BorderLayout.SOUTH);
		
		
	}
	
	void FlowLayout_initComponent() {
		this.setLayout(flowLayout);
		this.setPreferredSize(new Dimension(500,500));
		for(int i = 0; i < jbutton.length; i++ ) {
			jbutton[i] = new JButton("Button" + i);
			jbutton[i].setPreferredSize(new Dimension(100,100));
			this.add(jbutton[i]);
		}
	}
	
	void BorderLayout_initComponent() {
		this.setLayout(borderLayout);
		for(int i = 0; i < 5; i++) {
			jbutton[i] = new JButton("Button" + i);
		}
		
		this.add(jbutton[0], BorderLayout.NORTH);
		this.add(jbutton[1], BorderLayout.SOUTH);
		//this.add(jbutton[2], BorderLayout.WEST);
		//this.add(jbutton[3], BorderLayout.EAST);
		this.add(jbutton[4]);
		
		/*
		//왼쪽 끝
		this.add(jbutton[0], BorderLayout.LINE_START);
		//오른쪽 끝
		this.add(jbutton[1], BorderLayout.LINE_END);
		//맨 위
		this.add(jbutton[2], BorderLayout.PAGE_START);
		//맨 아래
		this.add(jbutton[3], BorderLayout.PAGE_END);
		//중앙
		this.add(jbutton[4]);
		*/
	}
	
	void GridLayout_initComponent() {
		this.setLayout(gridLayout);
		for(int i = 0; i < 11; i++) {
			jbutton[i] = new JButton("Button" + i);
			this.add(jbutton[i]);
		}
	}
	void CardLayout_initComponent() {
		this.setLayout(cardLayout);
		jbutton[0] = new JButton("사과");
		jbutton[1] = new JButton("배");
		jbutton[2] = new JButton("바나나");
		
		jbutton[0].addActionListener(this);
		jbutton[1].addActionListener(this);
		jbutton[2].addActionListener(this);
		
		jbutton[0].setBackground(Color.red);
		jbutton[1].setBackground(Color.black);
		jbutton[2].setBackground(Color.green);
		
		this.add(jbutton[0]);
		this.add(jbutton[1]);
		this.add(jbutton[2]);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(((JButton)e.getSource()).getText() == "Clear") {
			count = 0;
			oper = "";
			first_value = -99999;
			second_value = -99999;
			calc_result = 0;
			result.setText("");
		}
		LOGGER.debug(((JButton)e.getSource()).getText());
		if(((JButton)e.getSource()).getText() == "+") {
			oper = "+";
			count = 1;
		}else if(((JButton)e.getSource()).getText() == "-") {
			oper = "-";
			count = 1;
		}else if(((JButton)e.getSource()).getText() == "*") {
			oper = "*";
			count = 1;
		}else if(((JButton)e.getSource()).getText() == "/") {
			oper = "/";
			count = 1;
		}else if(((JButton)e.getSource()).getText() == "=") {
			Calc(first_value,second_value,oper);
			count = 0;
			LOGGER.debug(String.format("count [%d] ", count));
			LOGGER.debug(String.format("calc_result [%d]", calc_result));
		}else {
			if(count == 0 && calc_result == 0) {
				first_value = Integer.parseInt(((JButton)e.getSource()).getText());
			}
			if(count == 1 && calc_result ==0) {
				second_value = Integer.parseInt(((JButton)e.getSource()).getText());
			}else if(count == 1 && calc_result != 0) {
				first_value = calc_result;
				second_value = Integer.parseInt(((JButton)e.getSource()).getText());
			}
		}
		
	}
	public void Calc(int first_value, int second_value, String oper) {
		LOGGER.debug(String.format("first_value =[%d]", first_value));
		LOGGER.debug(String.format("oper [%s]", oper));

		LOGGER.debug(String.format("second_value =[%d]", second_value));
		if(oper == "+") {
			calc_result = first_value + second_value;
		}else if(oper == "-") {
			calc_result = first_value - second_value;
		}else if(oper == "*") {
			calc_result = first_value * second_value;
		}else if(oper == "/") {
			calc_result = first_value / second_value;
		}
		result.setText(calc_result.toString()); 
		LOGGER.debug(String.format("calc_result [%d]", calc_result));
	}
}

