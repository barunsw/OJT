package com.barunsw.ojt.jyb.day16;

import java.awt.Graphics;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import com.barunsw.ojt.day16.EventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.vo.BoardVo;

public class ShelfPanel extends JPanel implements EventListener {
    private static final Logger LOGGER = LogManager.getLogger(ShelfPanel.class);

    private List<BoardVo> boardList = new ArrayList<>();
    private Map<Integer, BoardPanel> boardMap = new HashMap<>();

    private RmiControl rmiControl;
    private ClientInterface clientIf;
    private ServerInterface serverIf;

    private EventQueueWorker<BoardVo> eventQueueWorker;

    private MainPanel mainPanel; // MainPanel 인스턴스 추가

    public ShelfPanel(MainPanel mainPanel) { // MainPanel을 인자로 받도록 수정
        this.mainPanel = mainPanel; // MainPanel 참조 저장
        try {
            eventQueueWorker = new EventQueueWorker<>();
            ClientImpl clientImpl = new ClientImpl(this);
            rmiControl = new RmiControl(clientImpl);

            serverIf = rmiControl.getServerIf();
            clientIf = rmiControl.getClientIf();

            initComponent();
            initData();

            eventQueueWorker.addEventListener(this); // 이벤트 리스너 등록
        }
        catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public EventQueueWorker<BoardVo> getEventQueueWorker() {
        if (this.eventQueueWorker == null) {
            LOGGER.error("이벤트 큐 워커가 비어있음");
        }
        return eventQueueWorker;
    }

    @Override
    public void push(Object o) {
        // o가 BoardVo 타입인지 확인하고 처리
        if (o instanceof BoardVo) {
            BoardVo boardVo = (BoardVo) o;
            SwingUtilities.invokeLater(() -> {
                updateBoardSeverity(boardVo);
                // 알람을 테이블에 추가
                addAlarmToTable(boardVo);
            });
        }
    }

    private void updateBoardSeverity(BoardVo boardVo) {
        // severity 값을 받아서 UI 업데이트하는 코드
        BoardPanel boardPanel = boardMap.get(boardVo.getBoardId());
        if (boardPanel != null) {
            boardPanel.getBoardVo().setSeverity(boardVo.getSeverity());
            boardPanel.repaint(); // UI 갱신
        }
    }

    private void addAlarmToTable(BoardVo boardVo) {
        String severity = String.valueOf(boardVo.getSeverity());
        String boardInfo = "Board " + boardVo.getBoardId(); // 또는 boardVo.getBoardName() 등 사용
        String time = java.time.LocalTime.now().toString(); // 현재 시간

        // MainPanel의 addAlarmData 호출하여 테이블에 추가
        mainPanel.addAlarmData(severity, boardInfo, time);
    }

    private void initComponent() throws Exception {
        this.setLayout(null);
    }

    private List<BoardVo> getBoardData() {
        // rmi서버에서 보드 리스트 가져오기
        if (serverIf != null) {
            try {
                boardList = serverIf.selectBoardList();
            }
            catch (RemoteException e) {
                LOGGER.error("RMI 서버에서 BoardList 가져오기 실패");
            }
        }
        return boardList;
    }

    private void initData() throws RemoteException {
        // 연동에 의해 board 정보 조회.
        boardList = getBoardData();

        LOGGER.debug("boardList:" + boardList);

        for (BoardVo oneBoardVo : boardList) {
            int boardId = oneBoardVo.getBoardId();

            BoardPanel boardPanel = new BoardPanel(oneBoardVo);

            // 전역에 boardList를 Map<Board ID, BoardPanel>으로 변환하여 저장한다.
            boardMap.put(boardId, boardPanel);
            this.add(boardPanel, null);

            LOGGER.debug(String.format("+++ TestPanel에 boardPanel(%s, %s) 추가", boardPanel.getWidth(),
                    boardPanel.getHeight()));
            boardPanel.repaint();

            int boardWidth = boardPanel.getBoardWidth();
            int boardHeight = boardPanel.getBoardHeight();

            LOGGER.debug(String.format("boardId:%s, boardWidth:%s, boardHeight:%s", boardId, boardWidth, boardHeight));

            if (boardId < 2) {
                boardPanel.setBounds(27 + (BoardPanel.BOARD_WIDTH * (boardId % 20)), 26, boardPanel.getBoardWidth(),
                        boardPanel.getBoardHeight());
            } else if (boardId < 20) {
                boardPanel.setBounds(27 + (BoardPanel.BOARD_WIDTH * (boardId % 20)), 307, boardPanel.getBoardWidth(),
                        boardPanel.getBoardHeight());
            } else {
                boardPanel.setBounds(27 + (BoardPanel.BOARD_WIDTH * (boardId % 20)), 26, boardPanel.getBoardWidth(),
                        boardPanel.getBoardHeight());
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        LOGGER.debug("paintComponent");
        g.drawImage(ImageFactory.backgroundImageIcon.getImage(), 0, 0, this);
    }

    public void pushAlarm(BoardVo boardVo) {
        // ID에 해당하는 BoardVo를 찾아 severity를 바꾼다.
        BoardPanel boardPanel = boardMap.get(boardVo.getBoardId());
        if (boardPanel != null) {
            SwingUtilities.invokeLater(() -> {
                boardPanel.getBoardVo().setSeverity(boardVo.getSeverity());
                boardPanel.repaint();
            });
        }
    }
}