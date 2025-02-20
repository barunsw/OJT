package com.barunsw.ojt.mjg.day19;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
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
    
    private JTree jTree 						= new JTree();
    private JScrollPane jScrollPane_Tree 		= new JScrollPane(jTree);
    
	private DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode("DB_Explorer");
	private DefaultTreeModel treeModel;
    
    private DbConnectionDialog dbConnectionDialog;
	
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
	
	// 트리 선택 시 DB 접근 후 QueryResultEvent 생성하여 이벤트 큐에 push
	private void initTreeSelectionListener() {
	    jTree.addTreeSelectionListener(new TreeSelectionListener() {
	        @Override
	        public void valueChanged(TreeSelectionEvent e) {
	            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
	            if (selectedNode == null) {
	                return;
	            }
	            if (selectedNode.isLeaf() && selectedNode.getParent() != null) {
	                String tableName = selectedNode.toString();
	                // 부모 노드에는 DB 이름이 들어있다고 가정
	                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
	                String dbName = parentNode.toString();
	                LOGGER.debug("테이블 선택: {} / DB: {}", tableName, dbName);
	                
	                Connection connection = null;
	                // 현재 연결된 DB와 부모 노드의 DB 이름이 다르면 재연결 수행
	                if (!dbName.equals(DbControl.getInstance().getCurrentDb())) {
	                    // 기존에 연결했던 id, pw를 사용하여 새로운 DB 연결 생성
	                    connection = DbControl.getInstance().connectToDB(
	                        DbControl.getInstance().getCurrentId(),
	                        DbControl.getInstance().getCurrentPw(),
	                        dbName
	                    );
	                }
	                else {
	                    connection = DbControl.getInstance().getConnection();
	                }
	                
	                if (connection == null) {
	                    LOGGER.error("DB 연결이 null입니다! DbControl에서 연결 확인 필요");
	                    return;
	                }
	                
	                try {
	                    Vector<String> columnNames = DBQueryManager.getColumnNames(connection, tableName);
	                    Vector<Vector<Object>> tableData = DBQueryManager.getTableData(connection, tableName);
	                    QueryResultEvent event = new QueryResultEvent(columnNames, tableData);
	                    ClientMain.eventQueueWorker.push(event);
	                }
	                catch (SQLException ex) {
	                    LOGGER.error("DB 쿼리 실행 중 예외 발생: {}", ex.getMessage(), ex);
	                }
	            }
	        }
	    });
	}


	// ID, PW, DB를 입력받는 팝업
	void jMenuItem_Create_ActionListener(ActionEvent e) {
	    if (dbConnectionDialog == null) {
	        MainFrame parentFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
	        if (parentFrame != null) {
	            dbConnectionDialog = new DbConnectionDialog(parentFrame);
	            LOGGER.info("dbConnectionDialog가 성공적으로 초기화되었습니다.");
	        } else {
	            LOGGER.error("MainFrame을 찾을 수 없습니다. dbConnectionDialog가 초기화되지 않습니다.");
	            JOptionPane.showMessageDialog(this, "다이얼로그 초기화 오류: 부모 창을 찾을 수 없습니다.");
	            return;
	        }
	    }

	    dbConnectionDialog.showDialog();

        if (dbConnectionDialog.isConfirmed()) {
            String id = dbConnectionDialog.getId();
            String pw = dbConnectionDialog.getPassword();
            String db = dbConnectionDialog.getDb();

			LOGGER.debug("ID : {}, PW : {}, DB : {}", id, pw, db);
			
            if (!db.isEmpty()) {
                try {
                	Connection connection = DbControl.getInstance().connectToDB(id, pw, db);

                    if (connection != null) {
                        DefaultMutableTreeNode dbNode = new DefaultMutableTreeNode(db);
                        rootTreeNode.add(dbNode);
                        treeModel.reload(rootTreeNode);

                        Vector<String> tableNames = DBQueryManager.getTableNames(connection);
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
