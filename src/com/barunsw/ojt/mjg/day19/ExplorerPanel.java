package com.barunsw.ojt.mjg.day19;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplorerPanel extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExplorerPanel.class);
	
	private GridBagLayout gridBagLayout = new GridBagLayout();
	
    private JPopupMenu jPopupMenu_Connection 	= new JPopupMenu();
    private JMenuItem jMenuItem_Create 			= new JMenuItem("연결");
    
    // 입력 패널, 입력 필드
	private JPanel jPanel_Form = new JPanel();
	private JTextField jTextField_Id 			= new JTextField();      // ID
	private JPasswordField jPasswordField 		= new JPasswordField();  // Password
	private JTextField jTextField_Db 			= new JTextField();      // DB
	
    // 라벨
    private JLabel jLabel_Id 					= new JLabel("ID");      // ID 라벨
    private JLabel jLabel_Password 				= new JLabel("PW");      // PW 라벨
    private JLabel jLabel_Db 					= new JLabel("DB");      // DB 라벨
    
    private JTree jTree 						= new JTree();
    private JScrollPane jScrollPane_Tree 		= new JScrollPane(jTree);
    
	private DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode("DB_Explorer");
	private DefaultTreeModel treeModel;

    // 서버 연동 담당 객체 (DB 연결)
    private ServerControl serverControl = new ServerControl();
	
	public ExplorerPanel() {
		try {
			initComponent();
			initTree();
			initTreeSelectionListener();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initComponent() {
		this.setLayout(gridBagLayout);
		
	    // JPopupMenu에 JMenuItem 추가
	    jPopupMenu_Connection.add(jMenuItem_Create);
		
		jMenuItem_Create.addActionListener(new ExplorerPanel_jMenuItem_Create_ActionListener(this));
		
		// "연결" 버튼(여기서는 메뉴 아이템 자체를 패널에 붙여둔 상태)
		this.add(jMenuItem_Create, new GridBagConstraints(
			0, 0, 1, 1, 1.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.NONE,
			new Insets(5, 5, 5, 5), 0, 0
		));

		// JTree 스크롤 추가
		this.add(jScrollPane_Tree, new GridBagConstraints(
			0, 1, 1, 1, 1.0, 1.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 5, 5, 5), 0, 0
		));

	}

	private void initTree() {
		treeModel = new DefaultTreeModel(rootTreeNode);
		
		jTree.setModel(treeModel);
	}
	
    // 트리 선택 시 이벤트 큐에 TableSelectEvent push
    private void initTreeSelectionListener() {
        jTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
                if (selectedNode == null) {
                    return;
                }
                // DB 노드가 아닌 실제 테이블(leaf) 노드일 때 처리
                if (selectedNode.isLeaf() && selectedNode.getParent() != null) {
                    String tableName = selectedNode.toString();
                    LOGGER.debug("테이블 선택: {}", tableName);
                    
                    // 이벤트 큐가 주입되어 있다면 이벤트 push
                    if (ClientMain.eventQueueWorker != null) {
                        TableSelectEvent event = new TableSelectEvent(tableName);
                        ClientMain.eventQueueWorker.push(event);
                    }
                }
            }
        });
    }

	// ID, PW, DB를 입력받는 팝업 (JOptionPane)
	// 입력 후 확인 누르면 입력값으로 새로운 DB 노드를 트리에 추가
	void jMenuItem_Create_ActionListener(ActionEvent e) {
		this.setLayout(gridBagLayout);
		
		jPanel_Form.setLayout(gridBagLayout);
		
		this.add(jPanel_Form, new GridBagConstraints(
				0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0
		));
		
		jPanel_Form.add(jLabel_Id, new GridBagConstraints(
				0, 0, 1, 1, 0.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 5), 0, 0
		));
		
		jPanel_Form.add(jTextField_Id, new GridBagConstraints(
				1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 0, 5, 5), 0, 0
		));
		
		jPanel_Form.add(jLabel_Password, new GridBagConstraints(
				0, 1, 1, 1, 0.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 5, 5, 5), 0, 0
		));
		
		jPanel_Form.add(jPasswordField, new GridBagConstraints(
				1, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0
		));
		
		jPanel_Form.add(jLabel_Db, new GridBagConstraints(
				0, 2, 1, 1, 0.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 5, 5, 5), 0, 0
		));
		
		jPanel_Form.add(jTextField_Db, new GridBagConstraints(
				1, 2, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0
		));

		int option = JOptionPane.showConfirmDialog(
				this,
				jPanel_Form,
				"연결",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (option == JOptionPane.OK_OPTION) {
			String id = jTextField_Id.getText();
			String pw = new String(jPasswordField.getPassword());
			String db = jTextField_Db.getText();

			LOGGER.debug("ID : {}, PW : {}, DB : {}", id, pw, db);
			
            if (!db.isEmpty()) {
                try {
                    // ServerControl을 통해 DB 연결 시도
                    Connection connection = serverControl.connectToDB(id, pw, db);

                    if (connection != null) {
                        // 연결 성공 시, DB 노드를 생성 및 추가
                        DefaultMutableTreeNode dbNode = new DefaultMutableTreeNode(db);
                        rootTreeNode.add(dbNode);
                        treeModel.reload(rootTreeNode);

                        // DBQueryManager를 통해 테이블 목록 조회 후, DB 노드의 자식으로 추가
                        Vector<String> tableNames = DBQueryManager.getTableNames(connection);
                        // LOGGER.debug(tableNames.elementAt(0));
                    	// LOGGER.debug(tableNames.elementAt(1));
                        for (String tableName : tableNames) {
                            DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(tableName);
                            dbNode.add(tableNode);
                        }
                        treeModel.reload(rootTreeNode);
                    }
                } 
                catch (RuntimeException ex) {
                    LOGGER.error("DB 연결 예외: {}", ex.getMessage());
                    JOptionPane.showMessageDialog(this, "DB 연결 예외\n" + ex.getMessage());
                } 
                catch (SQLException ex) {
                	LOGGER.error("테이블 로드 오류: {}", ex.getMessage());
                    JOptionPane.showMessageDialog(this, "테이블 로드 오류\n" + ex.getMessage());
                }
            } 
		} 
		else {
			LOGGER.debug("연결 취소");
		}
		
		// 팝업 닫으면 초기화
        jTextField_Id.setText("");
        jPasswordField.setText("");
        jTextField_Db.setText("");
	}
}

class ExplorerPanel_jMenuItem_Create_ActionListener implements ActionListener {
    private ExplorerPanel adaptee;

    public ExplorerPanel_jMenuItem_Create_ActionListener(ExplorerPanel adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.jMenuItem_Create_ActionListener(e);
    }
}
