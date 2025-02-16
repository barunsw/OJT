package com.barunsw.ojt.jyb.day16;

import java.awt.Graphics;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.vo.BoardVo;

public class ShelfPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(ShelfPanel.class);

	private List<BoardVo> boardList = new ArrayList<>();
	private Map<Integer, BoardPanel> boardMap = new HashMap<>();

	public static final int WIDTH = 854;
	public static final int HEIGHT = 604;

	public final int SLOT_NUM = 20;

	public final int BOARD_START_X = 27;
	public final int TOP_BOARD_START_Y = 26;
	public final int BOTTOM_BOARD_START_Y = 307;

	private String greetings = "Hello World";

	private ClientInterface clientIf;
	private ServerInterface serverIf;

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

	private void initRmi() throws NotBoundException {
		// 1. 레지스트리를 가져온다.
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", ServerMain.PORT);
			// 2. 서버쪽 RMI 객체가져온다
			serverIf = (ServerInterface) registry.lookup("RMIRACKVIEW");
			// 3. ClientImpl을 생성한다. new ClientImpl(this)
			clientIf = (ClientInterface) new ClientImpl(this);
			// 4. ClientImpl을 서버쪽에 register한다.
			serverIf.register(clientIf);
			LOGGER.info("RMI 서버 연결 완료");
		}
		catch (RemoteException e) {
			LOGGER.error("RMI 서버 연결 실패");
		}

	}

	private void initComponent() throws Exception {
		this.setLayout(null);
	}

	private List<BoardVo> getBoardData() {
		// rmi서버에서 보드 리스트 갖고와야함
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

			// BoardPanel을 boardMap에 추가
			boardMap.put(boardId, boardPanel);
			this.add(boardPanel, null);

			LOGGER.debug(String.format("+++ TestPanel에 boardPanel(%s, %s) 추가", boardPanel.getWidth(),
					boardPanel.getHeight()));
			boardPanel.repaint();

			int boardWidth = boardPanel.getBoardWidth();
			int boardHeight = boardPanel.getBoardHeight();

			LOGGER.debug(String.format("boardId:%s, boardWidth:%s, boardHeight:%s", boardId, boardWidth, boardHeight));

			if (boardId < 2) {
				boardPanel.setBounds(BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM)), TOP_BOARD_START_Y,
						boardPanel.getBoardWidth(), boardPanel.getBoardHeight());
			} else if (boardId < 20) {
				LOGGER.debug("startX:" + (BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM))));

				boardPanel.setBounds(BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM)),
						BOTTOM_BOARD_START_Y, boardPanel.getBoardWidth(), boardPanel.getBoardHeight());
			} else {
				boardPanel.setBounds(BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM)), TOP_BOARD_START_Y,
						boardPanel.getBoardWidth(), boardPanel.getBoardHeight());
			}

			LOGGER.debug("--- TestPanel에 boardPanel 추가");
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		LOGGER.debug("paintComponent");
		/*
		 * g.setColor(Color.white); g.fillRect(0, 0, this.getWidth(), this.getHeight());
		 * 
		 * g.setColor(Color.black); g.drawOval(100, 100, 300, 200);
		 * 
		 * // 문자열을 가운데 쓴다. g.setColor(Color.red); g.setFont(new Font("Tahoma",
		 * Font.BOLD, 20));
		 * 
		 * int stringWidth = g.getFontMetrics().stringWidth(greetings); int stringHeight
		 * = g.getFontMetrics().getHeight();
		 * 
		 * int strX = (this.getWidth() - stringWidth) / 2; int strY = (this.getHeight()
		 * - stringHeight) / 2;
		 * 
		 * g.drawString(greetings, strX, strY);
		 */
		g.drawImage(ImageFactory.backgroundImageIcon.getImage(), 0, 0, this);
	}

	public void pushAlarm(BoardVo boardVo) {
		// ID에 해당하는 BoardVo를 찾아 severity를 바꾼다.
		BoardPanel boardPanel = boardMap.get(boardVo.getBoardId());
		if (boardPanel != null) {
			SwingUtilities.invokeLater(() -> {
				// 기존 BoardVo의 severity 업데이트
				boardPanel.getBoardVo().setSeverity(boardVo.getSeverity());

				// 보드 패널 다시 그리기
				boardPanel.repaint();
			});
		}
	}
}