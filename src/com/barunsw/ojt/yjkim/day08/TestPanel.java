package com.barunsw.ojt.yjkim.day08;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel extends JPanel implements ActionListener,TreeSelectionListener{
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);

	private DefaultMutableTreeNode root, parent1, parent2, parent3, 
	parent4, parent5, parent6, parent7, parent8, parent9, parent10, parent11, parent12, parent13;
	private JScrollPane jScrollPane_Tree = new JScrollPane();
	private GridBagLayout gridBagLayout = new GridBagLayout();
	private JTree jtree;

	private JScrollPane jScrollPane_Table = new JScrollPane();
	
	private JTable jTable_Result = new JTable();
	private DefaultTableModel tableModel = new DefaultTableModel();
	
	private JPanel jPanel_InputField = new JPanel();
	private JLabel jLabel_Num 		 = new JLabel("번호");
	private JLabel jLabel_Name 		 = new JLabel("이름");
	private JLabel jLabel_Age 		 = new JLabel("나이");
	private JLabel jLabel_Address	 = new JLabel("주소");
	
	private JTextField jTextField_Num 		= new JTextField(3);
	private JTextField jTextField_Name 		= new JTextField(10);
	private JTextField jTextField_Age	 	= new JTextField(3);
	private JTextField jTextField_Address 	= new JTextField(15);
	
	private JPanel jPanel_Button 	= new JPanel();
	private JButton jButton_Add 	= new JButton("추가");
	private JButton jButton_Cancle 	= new JButton("삭제");
	private JButton jButton_Update 	= new JButton("수정");
	
	SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();

	private int row = 99999999;
	private int col = 99999999;
	private Object[] Value;
	private int col_count = 0;
	public TestPanel() {
		try {
			initComponent();
			initTable();
			initButtonEvent();
			initData();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		this.setLayout(gridBagLayout);
		DefaultTableModel model = (DefaultTableModel)jTable_Result.getModel();
		this.add(jScrollPane_Tree,
				new GridBagConstraints(0, 0, 1, 1,
						1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		root 		= new DefaultMutableTreeNode("주소록");
		parent1 	= new DefaultMutableTreeNode("ㄱ");
		parent2		= new DefaultMutableTreeNode("ㄴ");
		parent3 	= new DefaultMutableTreeNode("ㄷ");
		parent4 	= new DefaultMutableTreeNode("ㄹ");
		parent5 	= new DefaultMutableTreeNode("ㅁ");
		parent6 	= new DefaultMutableTreeNode("ㅂ");
		parent7 	= new DefaultMutableTreeNode("ㅅ");
		parent8 	= new DefaultMutableTreeNode("ㅇ");
		parent9 	= new DefaultMutableTreeNode("ㅈ");
		parent10 	= new DefaultMutableTreeNode("ㅋ");
		parent11 	= new DefaultMutableTreeNode("ㅌ");
		parent12 	= new DefaultMutableTreeNode("ㅍ");
		parent13 	= new DefaultMutableTreeNode("ㅎ");
		
		root.add(parent1);
		root.add(parent2);
		root.add(parent3);
		root.add(parent4);
		root.add(parent5);
		root.add(parent6);
		root.add(parent7);
		root.add(parent8);
		root.add(parent9);
		root.add(parent10);
		root.add(parent11);
		root.add(parent12);
		root.add(parent13);
		jtree = new JTree(root);
		jScrollPane_Tree.getViewport().add(jtree);
		this.add(jScrollPane_Table, 
				new GridBagConstraints(1, 0, 1, 1,
						1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		jScrollPane_Table.getViewport().add(jTable_Result);
		this.add(jPanel_InputField,
				new GridBagConstraints(0, 10, 6, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0),
						0, 0));
		jPanel_InputField.add(jLabel_Num,
				new GridBagConstraints(0, 0, 1, 1,
						1.0, 0.0,
						GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
						new Insets(0, 5, 5, 5),
						0, 0));
		jPanel_InputField.add(jTextField_Num,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 5, 5, 5),
						0, 0));
		
		jPanel_InputField.add(jLabel_Name,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 5, 5, 5),
						0, 0));
		
		jPanel_InputField.add(jTextField_Name,
				new GridBagConstraints(1, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		jPanel_InputField.add(jLabel_Age,
				new GridBagConstraints(2, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));	
		
		jPanel_InputField.add(jTextField_Age,
				new GridBagConstraints(3, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));	
		
		jPanel_InputField.add(jLabel_Address,
				new GridBagConstraints(4, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));	
		
		jPanel_InputField.add(jTextField_Address,
				new GridBagConstraints(3, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));	
		
		this.add(jPanel_Button,
				new GridBagConstraints(0, 11, 3, 1,
						1.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		jPanel_Button.add(jButton_Add,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));
		
		jPanel_Button.add(jButton_Cancle,
				new GridBagConstraints(0, 0, 1, 1,
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));
		jPanel_Button.add(jButton_Update,
				new GridBagConstraints(0, 0, 1, 1,
						1.0, 0.0,
						GridBagConstraints.EAST,GridBagConstraints.VERTICAL,
						new Insets(0, 0, 5, 5),
						0, 0));
	
	}
	
	private void initButtonEvent() {
		jTable_Result.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
					jTable_Result = (JTable)e.getSource();
					
					row = jTable_Result.getSelectedRow();
					col = jTable_Result.getSelectedColumn();
					col_count = jTable_Result.getColumnCount();
					
					Value = new Object[col_count];
					
					for(int i = 0; i < col_count; i++) {
						Value[i] = jTable_Result.getValueAt(row, i);
						LOGGER.debug(Value[i]);
					}
					jTextField_Num.setText(Value[0].toString());
					jTextField_Name.setText(Value[1].toString());
					jTextField_Age.setText(Value[2].toString());
					jTextField_Address.setText(Value[3].toString());
					
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		jButton_Update.addActionListener(this);
		jButton_Add.addActionListener(this);
		jButton_Cancle.addActionListener(this);
		jtree.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

					JTree jtree = (JTree)e.getSource();
					TreePath selectionPath = jtree.getSelectionPath();
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
					
					
					
					try (SqlSession session = sqlSessionFactory.openSession()){
					PersonDao mapper = session.getMapper(PersonDao.class);
					Map<String,Object> map = new HashMap<String,Object>();
					switch(selectedNode.toString()) {
						case "ㄱ":
							map.put("param",selectedNode.toString());
							LOGGER.debug(selectedNode.toString()+"임");
							break;
						case "ㄴ":
							map.put("param",selectedNode.toString());break;
						case "ㄷ":
							map.put("param",selectedNode.toString());break;
						case "ㄹ":
							map.put("param",selectedNode.toString());break;
						case "ㅁ":
							map.put("param",selectedNode.toString());break;
						case "ㅂ":
							map.put("param",selectedNode.toString());break;
						case "ㅅ":
							map.put("param",selectedNode.toString());break;
						case "ㅇ":
							map.put("param",selectedNode.toString());break;
						case "ㅈ":
							map.put("param",selectedNode.toString());break;
						case "ㅋ":
							map.put("param",selectedNode.toString());break;
						case "ㅌ":
							map.put("param",selectedNode.toString());break;
						case "ㅍ":
							map.put("param",selectedNode.toString());break;
						case "ㅎ":
							map.put("param",selectedNode.toString());break;
						default :
							break;
					}
					LOGGER.debug(map.get("param")+"은?");
					List<Map<String,Object>> list = mapper.select_Particular_Person(map);
					tableModel.setNumRows(0);
					LOGGER.debug(list+"리스트");
					for(int i = 0; i < list.size(); i++) {
						Vector oneData = new Vector();
						oneData.add(list.get(i).get("SEQ"));
						oneData.add(list.get(i).get("NAME"));
						oneData.add(list.get(i).get("AGE"));
						oneData.add(list.get(i).get("ADDRESS"));
						tableModel.addRow(oneData);
					}
				
					
					}	
					
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	
	private void initTable() {
		Vector<String> columnData = new Vector<>();
		columnData.add("번호");
		columnData.add("이름");
		columnData.add("나이");
		columnData.add("주소");
		
		tableModel.setColumnIdentifiers(columnData);
		jTable_Result.setAutoCreateRowSorter(true);
		jTable_Result.setModel(tableModel);

	}
	
	private void initData() {
		try (SqlSession session = sqlSessionFactory.openSession()){
			PersonDao mapper = session.getMapper(PersonDao.class);
			List<Map<String,Object>> list = mapper.select_Person();
			
			LOGGER.debug(list.size() + " 총 인원 ");
			for(int i = 0; i < list.size(); i++) {
				Vector oneData = new Vector();
				oneData.add(list.get(i).get("SEQ"));
				oneData.add(list.get(i).get("NAME"));
				oneData.add(list.get(i).get("AGE"));
				oneData.add(list.get(i).get("ADDRESS"));
				tableModel.addRow(oneData);
			}
		}catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		
		/*Vector oneData = new Vector();
		oneData.add("2");
		oneData.add("김윤제");
		oneData.add(30);
		oneData.add("서울시");
		
		tableModel.addRow(oneData);
		*/
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		
		if(((JButton)e.getSource()).getText() == "추가") {
			LOGGER.debug("추가");
			if(!(jTextField_Num.getText().equals(""))
					&& !(jTextField_Name.getText().equals("")) 
					&& !(jTextField_Age.getText().equals("")) 
					&& !(jTextField_Address.getText().equals(""))) {
				
				Vector oneData = new Vector();
				oneData.add(jTextField_Num.getText());
				oneData.add(jTextField_Name.getText());
				oneData.add(jTextField_Age.getText());
				oneData.add(jTextField_Address.getText());
				tableModel.addRow(oneData);
				
				try (SqlSession session = sqlSessionFactory.openSession()){
					PersonDao mapper = session.getMapper(PersonDao.class);
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("name",jTextField_Name.getText());
					map.put("age",Integer.parseInt(jTextField_Age.getText()));
					map.put("address",jTextField_Address.getText());

					mapper.insert_Person(map);
					session.commit();
				}catch(Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
				JOptionPane.showMessageDialog(this, "등록 되었습니다");

				jTextField_Num.setText("");
				jTextField_Name.setText("");
				jTextField_Age.setText("");
				jTextField_Address.setText("");
				
				
			}else {
				JOptionPane.showMessageDialog(this, "정보를 입력해주세요");
			}
		}
		
		
		if (((JButton)e.getSource()).getText() == "삭제") {
			tableModel.removeRow(row);
			try (SqlSession session = sqlSessionFactory.openSession()){
				PersonDao mapper = session.getMapper(PersonDao.class);
				mapper.delete_Person(Value[0]);
				
				
				session.commit();
				JOptionPane.showMessageDialog(this, "삭제 되었습니다");
			}catch(Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
		
		if(((JButton)e.getSource()).getText() == "수정") {
			jTextField_Num.setEnabled(false);
			if(!(jTextField_Num.getText().equals(""))
					&& !(jTextField_Name.getText().equals("")) 
					&& !(jTextField_Age.getText().equals("")) 
					&& !(jTextField_Address.getText().equals(""))) {
				
				
				
				try (SqlSession session = sqlSessionFactory.openSession()){
					PersonDao mapper = session.getMapper(PersonDao.class);
					
					Vector oneData = new Vector();
					oneData.add(jTextField_Num.getText());
					oneData.add(jTextField_Name.getText());
					oneData.add(jTextField_Age.getText());
					oneData.add(jTextField_Address.getText());
					tableModel.removeRow(row);
					tableModel.addRow(oneData);

					Map<String,Object> map = new HashMap<String,Object>();
					map.put("seq",Value[0]);
					map.put("name",jTextField_Name.getText());
					map.put("age",Integer.parseInt(jTextField_Age.getText()));
					map.put("address",jTextField_Address.getText());

					mapper.update_Person(map);
					session.commit();
				}catch(Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
				JOptionPane.showMessageDialog(this, "수정 되었습니다");

				jTextField_Num.setText("");
				jTextField_Name.setText("");
				jTextField_Age.setText("");
				jTextField_Address.setText("");
				
			}else {
				JOptionPane.showMessageDialog(this, "정보를 입력해주세요");
			}
			
		}
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		LOGGER.debug("선택됨");
	}

	
}
