package com.barunsw.ojt.day07;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);
	
	private final Dimension LABEL_SIZE 	= new Dimension(80, 22);
	private final Dimension BUTTON_SIZE = new Dimension(80, 22);
	
	private JPanel jPanel_Gender 	= new JPanel();
	private JPanel jPanel_Command 	= new JPanel();
	
	private JScrollPane jScrollPane_Address = new JScrollPane();
	
	private JLabel jLabel_Name 		= new JLabel("이름");
	private JLabel jLabel_Gender 	= new JLabel("성별");
	private JLabel jLabel_Address 	= new JLabel("주소");
	
	private JTextField jTextField_Name		= new JTextField();
	private JRadioButton jRadioButton_Man 	= new JRadioButton("남");
	private JRadioButton jRadioButton_Woman = new JRadioButton("여");
	private JTextArea jTextArea_Address		= new JTextArea();
	
	private JButton jButton_Add 	= new JButton("추가");
	private JButton jButton_Delete 	= new JButton("삭제");
	private JButton jButton_Reload 	= new JButton("재조회");
	
	private JScrollPane jScrollPane_Table = new JScrollPane();
	private JTable jTable_Result = new JTable();
	private CommonTableModel tableModel = new CommonTableModel();

	private GridBagLayout gridBagLayout = new GridBagLayout();

	public TestPanel() {
		try {
			initComponent();
			initTable();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		this.setLayout(gridBagLayout);
		jPanel_Gender.setLayout(gridBagLayout);
		jPanel_Command.setLayout(gridBagLayout);
		
		jLabel_Name.setPreferredSize(LABEL_SIZE);
		jLabel_Name.setMinimumSize(LABEL_SIZE);

		jLabel_Gender.setPreferredSize(LABEL_SIZE);
		jLabel_Address.setPreferredSize(LABEL_SIZE);
		
		jTextField_Name.setPreferredSize(new Dimension(120, 22));
		jRadioButton_Man.setPreferredSize(new Dimension(60, 22));
		jRadioButton_Woman.setPreferredSize(new Dimension(60, 22));
		// 사이즈 주면 스크롤바 안 생김
		//jTextArea_Address.setPreferredSize(new Dimension(120, 60));
		
		jButton_Add.setPreferredSize(BUTTON_SIZE);
		jButton_Delete.setPreferredSize(BUTTON_SIZE);
		jButton_Reload.setPreferredSize(BUTTON_SIZE);
		//jLabel_Name.setBackground(Color.green);
		
		jScrollPane_Address.setPreferredSize(new Dimension(100, 100));
		jScrollPane_Address.setMinimumSize(new Dimension(100, 100));
		
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
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0),
						0, 0));
		
		this.add(jLabel_Address, 
				new GridBagConstraints(0, 2, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0, 0));
		
		this.add(jScrollPane_Address, 
				new GridBagConstraints(1, 2, 1, 1, 
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));
	
		this.add(jPanel_Command, 
				new GridBagConstraints(0, 3, 2, 1, 
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0),
						0, 0));
		
		this.add(jScrollPane_Table, 
				new GridBagConstraints(0, 4, 2, 1, 
						1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0, 0));
		
		jScrollPane_Address.getViewport().add(jTextArea_Address);

		//jPanel_Gender.setBackground(Color.red);
		jPanel_Gender.add(jRadioButton_Man, 
				new GridBagConstraints(0, 0, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));		
		
		jPanel_Gender.add(jRadioButton_Woman, 
				new GridBagConstraints(1, 0, 1, 1, 
						1.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));	

		jPanel_Command.add(jButton_Add,
				new GridBagConstraints(0, 0, 1, 1, 
						1.0, 0.0,
						GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
						new Insets(0, 5, 5, 5),
						0, 0));	

		jPanel_Command.add(jButton_Delete,
				new GridBagConstraints(1, 0, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));	

		jPanel_Command.add(jButton_Reload,
				new GridBagConstraints(2, 0, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));
		
/*
		jPanel_Command.add(jButton_Add,
				new GridBagConstraints(0, 0, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 5, 5, 5),
						0, 0));	

		jPanel_Command.add(jButton_Delete,
				new GridBagConstraints(1, 0, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));	

		jPanel_Command.add(jButton_Reload,
				new GridBagConstraints(2, 0, 1, 1, 
						1.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));
*/
		jScrollPane_Table.getViewport().add(jTable_Result);
		
		jButton_Add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		jButton_Reload.addActionListener(new TestPanel_jButton_Reload_ActionListener(this));
	}
	
	private void initTable() {
		Vector<String> columnData = new Vector<>();
		columnData.add("이름");
		columnData.add("성별");
		columnData.add("나이");
		columnData.add("주소");
		
		tableModel.setColumn(columnData);
		
		jTable_Result.setModel(tableModel);		
	}
	
	private void initData() {
		Vector data = new Vector();
		
		Vector data1 = new Vector();
		data1.add("홍길동");
		data1.add("남");
		data1.add("30");
		data1.add("서울");
		
		data.add(data1);

		Vector data2 = new Vector();
		data2.add("유관순");
		data2.add("여");
		data2.add("20");
		data2.add("경기");
		
		data.add(data2);

		tableModel.setData(data);
		tableModel.fireTableDataChanged();
	}
	
	void jButton_Add_ActionListener(ActionEvent e) {
		
	}
	
	void jButton_Reload_ActionListener(ActionEvent e) {
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

class TestPanel_jButton_Reload_ActionListener implements ActionListener {
	private TestPanel adaptee;
	
	public TestPanel_jButton_Reload_ActionListener(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		adaptee.jButton_Reload_ActionListener(e);
	}
}