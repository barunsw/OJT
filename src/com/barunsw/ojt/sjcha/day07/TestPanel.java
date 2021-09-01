package com.barunsw.ojt.sjcha.day07;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.sjcha.day07.TestPanel;

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);
	
	private final Dimension LABEL_SIZE = new Dimension(80, 22);
	
	// layout Object 생성
	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	// panel 
	private JPanel jPanel_Gender	= new JPanel();
	private JPanel jPanel_Button 	= new JPanel();
	
	// Address - scroll 필요
	private JScrollPane jScrollPane_Address = new JScrollPane();
	
	// component
	private JLabel jLabel_Id 		= new JLabel("아이디");
	private JLabel jLabel_Password 	= new JLabel("비밀번호");
	private JLabel jLabel_Name 		= new JLabel("이름");
	private JLabel jLabel_Gender 	= new JLabel("성별");
	private JLabel jLabel_Address 	= new JLabel("주소");
	
	private JTextField jTextField_Id 		= new JTextField();
	private JTextField jTextField_Password 	= new JTextField();
	private JTextField jTextField_Name 		= new JTextField();
	
	private JRadioButton jRadioButton_Man 	= new JRadioButton("남");
	private JRadioButton jRadioButton_Woman = new JRadioButton("여");
	
	private JTextArea jTextArea_Address		= new JTextArea();
	
	private JScrollPane jScrollPane_Table 	= new JScrollPane(); 
	private JTable jTable_Result 			= new JTable();
	private CommonTableModel tableModel 	= new CommonTableModel();
			
	private JButton jButton_Add 	= new JButton("가입");
	private JButton jButton_Delete 	= new JButton("삭제");
	private JButton jButton_Reload 	= new JButton("조회");
	
	// TestPanel의 생성자
	public TestPanel() {
		try {
			initComponent();
			initTable();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	public void initComponent() throws Exception {
		this.setLayout(gridBagLayout);
		jPanel_Gender.setLayout(gridBagLayout);
		
		jLabel_Id.setPreferredSize(LABEL_SIZE);
		jTextField_Id.setPreferredSize(new Dimension(120, 22));
		
		jLabel_Password.setPreferredSize(LABEL_SIZE);
		jTextField_Password.setPreferredSize(new Dimension(120, 22));
		
		jLabel_Name.setPreferredSize(LABEL_SIZE);
		jTextField_Name.setPreferredSize(new Dimension(120, 22));
		
		jLabel_Gender.setPreferredSize(LABEL_SIZE);
		jRadioButton_Man.setPreferredSize(new Dimension(60, 22));
		jRadioButton_Woman.setPreferredSize(new Dimension(60, 22));
		
		jScrollPane_Address.setPreferredSize(new Dimension(100, 100));
		
		// id label
		// x, y, width, height, weightx, weighty, anchor, fill, insets, ipadx, ipady
		this.add(jLabel_Id, 
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		// id textfield
		this.add(jTextField_Id,
				new GridBagConstraints(1, 0, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 0, 5, 5),
						0, 0));
		
		// password label
		this.add(jLabel_Password,
				new GridBagConstraints(0, 1, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0, 0));
		
		// password textfield
		this.add(jTextField_Password,
				new GridBagConstraints(1, 1, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		// 이름 label
		this.add(jLabel_Name,
				new GridBagConstraints(0, 2, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0, 0));	
		
		
		// 이름 textfield
		this.add(jTextField_Name,
				new GridBagConstraints(1, 2, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));
					
		// 성별 label
		this.add(jLabel_Gender,
				new GridBagConstraints(0, 3, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0, 0));	
		
		// 성별 - 라디오 버튼(panel 안에 넣기)
		this.add(jPanel_Gender,
				new GridBagConstraints(1, 3, 1, 1,
						1.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
						new Insets(5, 0, 5, 5),
						0, 0));
		
		// 주소 label
		this.add(jLabel_Address,
				new GridBagConstraints(0, 4, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
						new Insets(0, 5, 5, 5),
						0, 0));		
		
		this.add(jScrollPane_Address,
				new GridBagConstraints(1, 4, 1, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		jScrollPane_Address.getViewport().add(jTextArea_Address);
		
		this.add(jScrollPane_Table,
				new GridBagConstraints(0, 5, 2, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0, 0));
		
		jScrollPane_Table.getViewport().add(jTable_Result);
		
		this.add(jPanel_Button, 
				new GridBagConstraints(0, 6, 2, 1,
						1.0, 0.0,
						GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
						new Insets(0, 5, 5, 5),
						0, 0));

		// 라디오 버튼
		jPanel_Gender.add(jRadioButton_Man,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		jPanel_Gender.add(jRadioButton_Woman,
				new GridBagConstraints(1, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 0),
						0, 0));
		
		jPanel_Button.add(jButton_Add,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0, 0));
		
		jPanel_Button.add(jButton_Delete,
				new GridBagConstraints(1, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0, 0));
		
		jPanel_Button.add(jButton_Reload,
				new GridBagConstraints(2, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0, 0));
		
		jButton_Add.addActionListener(new TestPanel_jButton_Add_ActionListener(this));
	}
	
	// 테이블 생성
	private void initTable() {
		Vector<String> columnData = new Vector<>();
		
		columnData.add("아이디");
		columnData.add("비밀번호");
		columnData.add("이름");
		columnData.add("성별");
		columnData.add("주소");
		
		// 데이터 모델에 속성값 지정
		tableModel.setColumn(columnData);
		
		// 테이블에 데이터 모델 삽입
		jTable_Result.setModel(tableModel);
	}
	
	// 데이터 초기화
	private void initData() {
		Vector dataLine = new Vector();
		
		Vector oneData = new Vector();
		oneData.add(jTextField_Id.getText());
		oneData.add(jTextField_Password.getText());
		oneData.add(jTextField_Name.getText());
		oneData.add("여");
		oneData.add(jTextArea_Address.getText());
		
		Vector twoData = new Vector();
		twoData.add("tnwls4120");
		twoData.add("tnwls6134");
		twoData.add("홍길동");
		twoData.add("20");
		twoData.add("서울 송파구");
	
		dataLine.add(oneData);
		dataLine.add(twoData);
		
		tableModel.setData(dataLine);
		
		// 데이터의 변화가 있으면 변화 가능하게 함
		tableModel.fireTableDataChanged();
	}
	
	void jButton_Add_ActionListener(ActionEvent e) {
		initData();
	}
}

class TestPanel_jButton_Add_ActionListener implements ActionListener {
	private TestPanel adaptee;
	
	public TestPanel_jButton_Add_ActionListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		adaptee.jButton_Add_ActionListener(e);
	}
}

