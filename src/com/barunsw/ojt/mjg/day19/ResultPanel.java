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
			initEvent();
			initComponent();
			initTable();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initEvent() {
		ClientMain.eventQueueWorker.addEventListener(this);
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

	    // 데이터에 따라 테이블 구조 변경
	    tableModel.fireTableStructureChanged();

	    // 렌더러 다시 적용 (fireTableStructureChanged() 후 컬럼 모델이 초기화되므로 필수)
	    applyRowAlignment();
	    
	    jTable.revalidate();
	    jTable.repaint();
	}

	// 동적으로 row 정렬을 적용하는 메서드
	// 요구사항 이미지대로 좌측 정렬
	private void applyRowAlignment() {
	    DefaultTableCellRenderer leftAlignRenderer = new DefaultTableCellRenderer();
	    leftAlignRenderer.setHorizontalAlignment(SwingConstants.LEFT);

	    for (int i = 0; i < jTable.getColumnCount(); i++) {
	        jTable.getColumnModel().getColumn(i).setCellRenderer(leftAlignRenderer);
	    }
	}

    // ResultPanel에서 쿼리 실행하는게 아님
    // QueryResultEvent를 수신하면 이벤트 안의 데이터를 이용해 JTable 업데이트
    @Override
    public void push(Object o) {
        if (o instanceof QueryResultEvent) {
            final QueryResultEvent event = (QueryResultEvent) o;
            final Vector<String> columnNames = event.getColumnNames();
            final Vector<Vector<Object>> tableData = event.getTableData();
            javax.swing.SwingUtilities.invokeLater(() -> updateTable(columnNames, tableData));
        }
    }
}
