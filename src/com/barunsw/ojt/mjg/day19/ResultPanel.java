package com.barunsw.ojt.mjg.day19;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultPanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResultPanel.class);

	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	private JButton jButton_Result = new JButton("Result");
	
    private JTable jTable = new JTable();                        
    private JScrollPane jScrollPane_Table = new JScrollPane(jTable);
    
    // 테이블 모델 가져오기
    private CommonTableModel tableModel = new CommonTableModel();
    
    // 셀 렌더러
    private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    
    // 헤더 렌더러
    private JTableHeader tableHeader = jTable.getTableHeader();
    
    // 기본 헤더 렌더러 가져오기 - 기존 룩앤필 유지
    private DefaultTableCellRenderer defaultHeaderRenderer = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();
	
	public ResultPanel() {
		try {
			initComponent();
			initTable();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initComponent() {
		this.setLayout(gridBagLayout);
		
		// JTable
		this.add(jScrollPane_Table, new GridBagConstraints(
		    0, 0, 1, 1, 1.0, 1.0,
		    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
		    new Insets(5, 5, 5, 5), 0, 0
		));
		
		jScrollPane_Table.getViewport().add(jTable);
	}
	
	private void initTable() {
        // JTable에 테이블 모델 설정하여 테이블 구성
        jTable.setModel(tableModel);
        
	    // 헤더만 가운데 정렬로 변경
	    defaultHeaderRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	
	    // 렌더러를 다시 테이블 헤더에 설정
	    tableHeader.setDefaultRenderer(defaultHeaderRenderer);
	}
	
    // 외부에서 테이블 데이터를 업데이트할 수 있는 메서드
    public void updateTable(Vector<String> columnNames, Vector<Vector<Object>> tableData) {
        if (columnNames == null || tableData == null) {
            LOGGER.error("컬럼명 또는 데이터가 null입니다.");
            return;
        }

        LOGGER.debug("JTable 업데이트 - 컬럼: {}, 데이터 행 수: {}", columnNames, tableData.size());

        tableModel.setColumn(columnNames);
        tableModel.setData(tableData);
        tableModel.fireTableDataChanged(); // 테이블 변경 알림
//        tableModel.fireTableStructureChanged(); 
        jTable.revalidate();
        jTable.repaint();
    }
    
    @Override
    public void push(Object o) {
        if (o instanceof TableSelectEvent) {
            final TableSelectEvent event = (TableSelectEvent) o;
            final String tableName = event.getTableName();
            LOGGER.debug("ResultPanel 처리 - 테이블 선택 이벤트: {}", tableName);

            final Connection connection = ServerControl.getInstance().getConnection();
            if (connection == null) {
                LOGGER.error("DB 연결이 null입니다! ServerControl에서 연결 확인 필요");
                return;
            }

            Vector<String> columnNames = null;
            Vector<Vector<Object>> tableData = null;

            try {
                // 컬럼 정보 가져오기
                columnNames = DBQueryManager.getColumnNames(connection, tableName);
                LOGGER.debug("컬럼 가져오기 성공: {}", columnNames);
            } catch (SQLException ex) {
                LOGGER.error("getColumnNames()에서 예외 발생: {}", ex.getMessage(), ex);
            }

            try {
                // 테이블 데이터 가져오기
                tableData = DBQueryManager.getTableData(connection, tableName);
                LOGGER.debug("데이터 가져오기 성공: {} 개의 행", (tableData != null) ? tableData.size() : 0);
            } catch (SQLException ex) {
                LOGGER.error("getTableData()에서 예외 발생: {}", ex.getMessage(), ex);
            }

            if (columnNames != null && tableData != null) {
                // UI 업데이트를 EDT에서 실행
                final Vector<String> finalColumnNames = columnNames;
                final Vector<Vector<Object>> finalTableData = tableData;
                javax.swing.SwingUtilities.invokeLater(() -> updateTable(finalColumnNames, finalTableData));
            } else {
                LOGGER.error("컬럼명 또는 테이블 데이터가 null입니다. JTable 업데이트 불가");
            }
        }
    }

    
/*
    // EventListener 구현: 이벤트 push -> DBQueryManager 데이터 조회 -> 테이블 갱신
    @Override
    public void push(Object o) {
        if (o instanceof TableSelectEvent) {
            TableSelectEvent event = (TableSelectEvent) o;
            String tableName = event.getTableName();
            LOGGER.debug("ResultPanel 처리 - 테이블 선택 이벤트: {}", tableName);
            
            // DB 연결 객체는 ServerControl을 싱글톤(getInstance()) 방식으로 관리하도록 수정
            Connection connection = ServerControl.getInstance().getConnection(); 
            
            if (connection != null) {
                Vector<String> columnNames = null;
				try {
					columnNames = DBQueryManager.getColumnNames(connection, tableName);
				}
				catch (SQLException sqle) {
					sqle.printStackTrace();
				}
				
				Vector<Vector<Object>> tableData = null;
				try {
					tableData = DBQueryManager.getTableData(connection, tableName);
				}
				catch (SQLException sqle) {
					sqle.printStackTrace();
				}
				
				updateTable(columnNames, tableData);
				LOGGER.debug("컬럼: {}", columnNames);
				LOGGER.debug("데이터 행 수: {}", tableData.size());

            }
        }
    }
*/
}
