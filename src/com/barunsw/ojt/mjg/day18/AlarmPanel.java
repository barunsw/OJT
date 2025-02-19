package com.barunsw.ojt.mjg.day18;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.constants.Severity;
import com.barunsw.ojt.vo.BoardVo;

public class AlarmPanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(AlarmPanel.class);

	private GridBagLayout gridBagLayout = new GridBagLayout();

	private JTable jTable_Alarm = new JTable();
	private CommonTableModel tableModel = new CommonTableModel();
	private JScrollPane jScrollPane_Alarm = new JScrollPane(jTable_Alarm);

	private final int TABLE_CELL_ID_SEVERITY = 0;
	private final int TABLE_CELL_ID_BOARDNAME_BOARDID = 1;
	private final int TABLE_CELL_ID_TIME = 2;
	
//	private RmiControl rmiControl = new RmiControl();

	public AlarmPanel() {
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

		this.add(jScrollPane_Alarm, new GridBagConstraints(
				0, 0, 1, 1, 1.0, 1.0, 
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0
				));

		jScrollPane_Alarm.getViewport().add(jTable_Alarm);
	}
	
	// QueueWorker에서 push 해주는 정보가 ShelfPanel이랑 같아서 서버 연동부를 다시 사용하지 않아도 됨
	// 같은 이벤트(BoardVo)를 push 받음
	// 예를 들어, ChatPanel일 경우 다른 이벤트(String msg)일 때는 따로 서버 호출해 줘야 됨
/*
	private List<BoardVo> getBoardData() throws RemoteException {
		// 서버에서 보드 리스트 가져오기
	    List<BoardVo> boardList = rmiControl.selectBoardList();
	    
	    LOGGER.debug("+++ 보드 데이터 가져오기: " + boardList);
	    
	    return boardList;
	}
*/

	// 알람 테이블 생성
	private void initTable() {

		Vector<String> columnData = new Vector<>();

		// 테이블 컬럼명 정의
		columnData.add("Severity");
		columnData.add("BoardName/BoardId");
		columnData.add("발생시간");

		// 데이터 모델에 속성값 지정
		tableModel.setColumn(columnData);

		// JTable에 테이블 모델 설정하여 테이블 구성
		jTable_Alarm.setModel(tableModel);

		// 각 컬럼에 대해 width, 렌더러 설정
		for (int i = 0; i < jTable_Alarm.getColumnCount(); i++) {
			switch (i) {
			case TABLE_CELL_ID_SEVERITY:
				jTable_Alarm.getColumnModel().getColumn(i).setPreferredWidth(50);
				break;
			case TABLE_CELL_ID_BOARDNAME_BOARDID:
				jTable_Alarm.getColumnModel().getColumn(i).setPreferredWidth(100);
				break;
			case TABLE_CELL_ID_TIME:
				jTable_Alarm.getColumnModel().getColumn(i).setPreferredWidth(100);
				break;
			default:
				// 추가 컬럼이 있다면 기본값
				jTable_Alarm.getColumnModel().getColumn(i).setPreferredWidth(100);
				break;
			}
		}
	}
	
    @Override
    public void push(Object o) {
        if (o instanceof BoardVo) {
            BoardVo boardVo = (BoardVo)o;
            // 테이블에 행 추가
            addAlarmRow(boardVo);
        }
    }
    
	public void addAlarmRow(BoardVo boardVo) {
        // 현재 시간 포맷	            
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
        String timeStamp = sdf.format(new Date());
        
		Vector<Object> oneRow = new Vector<>();
		oneRow.add(Severity.items[boardVo.getSeverity()]);
		oneRow.add(boardVo.getBoardName() + "/" + boardVo.getBoardId());
		oneRow.add(timeStamp);

		tableModel.addData(oneRow);
		tableModel.fireTableDataChanged();
	}

}
