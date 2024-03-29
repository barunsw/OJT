package com.barunsw.ojt.sjcha.day16;

import java.awt.Graphics;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.sjcha.day16.EventQueueWorker;
import com.barunsw.ojt.sjcha.day16.BoardPanel;
import com.barunsw.ojt.sjcha.day16.BoardType;
import com.barunsw.ojt.sjcha.day16.ClientImpl;
import com.barunsw.ojt.sjcha.day16.ServerInterface;
import com.barunsw.ojt.sjcha.day16.ServerMain;
import com.barunsw.ojt.vo.AlarmVo;
import com.barunsw.ojt.sjcha.day16.BoardVo;
import com.barunsw.ojt.sjcha.day16.EventListener;

public class RackViewPanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LogManager.getLogger(RackViewPanel.class);

	public static final int WIDTH = 854;
	public static final int HEIGHT = 604;

	public final int SLOT_NUM = 38;

	public final int BOARD_START_X = 27;
	public final int TOP_BOARD_START_Y = 26;
	public final int BOTTOM_BOARD_START_Y = 307;

	public final int TOP_BOARD_START_X = 27;
	public final int BOTTOM_BOARD_START_X = 107;

	public final int BOARD_WIDTH_GAP = 40;

	private ServerInterface serverIf = null;
	private ClientInterface clientIf;

	private BoardVo board;

	private Map<Integer, BoardVo> boardData = new HashMap<Integer, BoardVo>();

	private int boardIndex = 0;

	private BoardPanel boardPanel;

	public RackViewPanel() {
		try {
			initEvent();
			initRmi();
			initComponent();
			initBoardData();
			initData();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initEvent() {
		ClientMain.eventQueueWorker.addEventListener(this);
	}

	private void initRmi() {
		try {
			clientIf = new ClientImpl();

			Registry registry = LocateRegistry.getRegistry(ServerMain.PORT);

			Remote remote = registry.lookup("RMIRACKVIEW");

			if (remote instanceof ServerInterface) {
				serverIf = (ServerInterface) remote;
				LOGGER.debug("+++ SERVER +++");
			}

			serverIf.register(clientIf);

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initComponent() throws Exception {
		this.setLayout(null);
	}

	private Map<Integer, BoardVo> initBoardData() throws RemoteException {
		for (int i = 0; i < SLOT_NUM; i++) {
			BoardVo boardVo = new BoardVo();
			boardVo.setBoardId(i);
			boardVo.setSeverity(3);

			if (i < 2) {
				boardVo.setBoardType(BoardType.MPU);
				boardVo.setBoardName("MPU");
			}

			else if (i % 18 == 0 || i % 36 == 0) {
				boardVo.setBoardType(BoardType.SRGU);
				boardVo.setBoardName("SRGU");
			}

			else if (i % 19 == 0 || i % 37 == 0) {
				continue;
			}

			else {
				boardVo.setBoardType(BoardType.SALC);
				boardVo.setBoardName("SALC");
			}

			boardData.put(boardVo.getBoardId(), boardVo);
		}

		return boardData;
	}

	private void initData() throws RemoteException {
		Set set = boardData.keySet();
		// 순차 탐색
		Iterator iterator = set.iterator();

		while (iterator.hasNext()) {
			int key = (int) iterator.next();

			board = boardData.get(key);

			boardPanel = new BoardPanel(board);
			this.add(boardPanel, null);

			boardPanel.repaint();

			if (key < 20) {
				boardPanel.setBounds(TOP_BOARD_START_X + (key * BOARD_WIDTH_GAP), TOP_BOARD_START_Y,
						boardPanel.getBoardWidth(), boardPanel.getBoardHeight());
			}
			else {
				boardPanel.setBounds(BOTTOM_BOARD_START_X + (key % 20 * BOARD_WIDTH_GAP), BOTTOM_BOARD_START_Y,
						boardPanel.getBoardWidth(), boardPanel.getBoardHeight());
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		LOGGER.debug("paintComponent");
		super.paintComponent(g);

		g.drawImage(ImageFactory.backgroundImageIcon.getImage(), 0, 0, this);
	}

	@Override
	public void push(Object o) {
		LOGGER.debug("push : " + o);

		if (o instanceof BoardVo) {
			BoardVo boardVo = (BoardVo) o;

			boardIndex = boardVo.getBoardId();

			boardData.get(boardIndex).setSeverity(boardVo.getSeverity());

			repaint();
		}
	}
}