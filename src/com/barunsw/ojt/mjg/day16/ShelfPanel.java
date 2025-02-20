package com.barunsw.ojt.mjg.day16;

import java.awt.Graphics;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.BoardType;
import com.barunsw.ojt.constants.Severity;
import com.barunsw.ojt.day15.ImageFactory;
import com.barunsw.ojt.vo.BoardVo;

public class ShelfPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(ShelfPanel.class);
	
	private List<BoardVo> boardList = new ArrayList<>();
	private Map<Integer, BoardPanel> boardMap = new HashMap<>();
	
	
	public static final int WIDTH 			= 854;
	public static final int HEIGHT			= 604;
	
	public final int SLOT_NUM				= 20;
	
	public final int BOARD_START_X			= 27;
	public final int TOP_BOARD_START_Y		= 26;
	public final int BOTTOM_BOARD_START_Y	= 307;
	
    // 알람 테이블 크기 및 중앙 정렬
    public static final int tableWidth 		= 700;
    public static final int tableHeight 	= 100;
    int tableX = (WIDTH - tableWidth) / 2; 			// 중앙 정렬
    int tableY = BOTTOM_BOARD_START_Y + 300; 		// 보드 아래 여백 추가
	
	private String greetings = "Hello World";
	
	private ClientInterface clientIf;
	private ServerInterface serverIf;
	
    private JTable jTable_alarm = new JTable();
    private DefaultTableModel tableModel = new DefaultTableModel();
    private JScrollPane jScrollPane_alarm = new JScrollPane(jTable_alarm);
	
	public ShelfPanel() {
		try {
			initRmi();
			initComponent();
			initData();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initRmi() {
		// 1. 레지스트리를 가져온다.
		// 2. 서버쪽 RMI 객체가져온다.
		// 3. ClientImpl을 생성한다. new ClientImpl(this)
		// 4. ClientImpl을 서버쪽에 register한다.
	    try {
	        // 1. 레지스트리를 가져온다.
	        Registry registry = LocateRegistry.getRegistry("localhost", ServerMain.PORT);

	        // 2. 서버쪽 RMI 객체가져온다.
	        serverIf = (ServerInterface) registry.lookup("RMIRACKVIEW"); // 서버 인터페이스 저장

	        // 3. ClientImpl을 생성한다. new ClientImpl(this)
	        clientIf = new ClientImpl(this); // 클라이언트 인터페이스 저장

	        // 4. ClientImpl을 서버쪽에 register한다.
	        serverIf.register(clientIf);
	        
	        LOGGER.info("+++Connected to RMI Server.");
	    } 
	    catch (Exception e) {
	        LOGGER.error("+++RMI initialization failed.", e);
	    }
	}
	
	private void initComponent() throws Exception {
		this.setLayout(null);
		
        // 경보 테이블 초기화
        String[] columnNames = {"경보 시간", "BoardName", "Severity"};
        tableModel.setColumnIdentifiers(columnNames);
        jTable_alarm.setModel(tableModel);

        // 스크롤 패널 위치 조정
        jScrollPane_alarm.setBounds(tableX, tableY, tableWidth, tableHeight);

        this.add(jScrollPane_alarm);
	}
	
	private List<BoardVo> getBoardData() {
	    
	    try {
	        // 서버에서 보드 리스트 가져오기
	        if (serverIf != null) {
	            boardList = serverIf.selectBoardList();
	        }
        }
	    catch (Exception e) {
	    	LOGGER.error("서버에서 보드 리스트 가져오기 실패", e);
	    }

	    LOGGER.debug("Board data fetched: " + boardList);
	    return boardList;
	}

	private void initData() {
		// 연동에 의해 board 정보 조회. 
		boardList = getBoardData();
				
		LOGGER.debug("boardList:" + boardList);
		
		for (BoardVo oneBoardVo : boardList) {
			int boardId = oneBoardVo.getBoardId();
			
			BoardPanel boardPanel = new BoardPanel(oneBoardVo);
			
			// 전역에 boardList를 Map<Board ID, BoardPanel>으로 변환하여 저장한다.
			boardMap.put(boardId, boardPanel);
			
			this.add(boardPanel, null);
			
			LOGGER.debug(String.format("+++ ShelfPanel에 boardPanel(%s, %s) 추가",
					boardPanel.getWidth(), boardPanel.getHeight()));
			boardPanel.repaint();
			
			int boardWidth = boardPanel.getBoardWidth();
			int boardHeight = boardPanel.getBoardHeight();
			
			LOGGER.debug(String.format("boardId:%s, boardWidth:%s, boardHeight:%s", boardId, boardWidth, boardHeight));
			
			if (boardId < 2) {
				boardPanel.setBounds(BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM)),
						TOP_BOARD_START_Y,
						boardPanel.getBoardWidth(),
						boardPanel.getBoardHeight());
			}
			else if (boardId < 20) {
				LOGGER.debug("startX:" + (BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM))));
				
				boardPanel.setBounds(BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM)),
						BOTTOM_BOARD_START_Y,
						boardPanel.getBoardWidth(),
						boardPanel.getBoardHeight());
			}
			else {
				boardPanel.setBounds(BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM)),
						TOP_BOARD_START_Y,
						boardPanel.getBoardWidth(),
						boardPanel.getBoardHeight());
			}
			
			LOGGER.debug("--- TestPanel에 boardPanel 추가");
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		LOGGER.debug("paintComponent");
		 
		g.drawImage(ImageFactory.backgroundImageIcon.getImage(),
				0, 0, this);
	}

	public void pushAlarm(BoardVo boardVo) {
		// ID에 해당하는 BoardVo를 찾아 severity를 바꾼다.
		// repaint한다.
		
		// NORMAL 상태는 경보 테이블에 추가하지 않음
	    if (boardVo.getSeverity() == Severity.NORMAL) {
	        return;
	    }
		
	    // boardMap에서 해당 보드 찾기
	    BoardPanel boardPanel = boardMap.get(boardVo.getBoardId());

	    if (boardPanel != null) {
	        SwingUtilities.invokeLater(() -> {
	            // 기존 BoardVo의 severity 업데이트
	            boardPanel.getBoardVo().setSeverity(boardVo.getSeverity());

	            // 보드 패널 다시 그리기
	            boardPanel.repaint();  
	            
	            // 전체 ShelfPanel 다시 그리기
	            repaint();
	            
                // 현재 시간 포맷	            
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
	            String timeStamp = sdf.format(new Date());

                // 테이블에 알람 정보 추가
                tableModel.addRow(new Object[]{timeStamp, boardVo.getBoardName(), Severity.items[boardVo.getSeverity()]});
	        });
	    }
	}
}