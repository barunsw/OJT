package com.barunsw.ojt.phs.day15;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.BoardType;
import com.barunsw.ojt.constants.Severity;

public class ClientPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(ClientPanel.class);
	
	public final int SLOT_NUM = 20;
	
	public final int BOARD_START_X		  = 27;
	public final int TOP_BOARD_START_Y	  = 26;
	public final int BOTTOM_BOARD_START_Y = 307;
	
	public static final int TEXT_WIDTH		= 36;
	public static final int TEXT_HEIGHT		= 18;
	
	private RackViewInterface rackViewInterface;
	
	public ClientPanel() {
		try {
			initRMI();
			initComponent();
			initData();
			selectData();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initRMI() throws NotBoundException {
		try {
			Registry registry = LocateRegistry.getRegistry(35000);
			Remote remote = registry.lookup("RackView");
			if (remote instanceof RackViewInterface) {
				LOGGER.debug("Lookup() success");
				rackViewInterface = (RackViewInterface)remote;
			}
		}
		catch (RemoteException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void initComponent() throws Exception {
		this.setLayout(null);
	}
	
	private void initData() {
		// MPU
		addBoardVo(BoardType.MPU, "MPU01", 0, Severity.NORMAL);
		addBoardVo(BoardType.MPU, "MPU02", 1, Severity.NORMAL);
		
		// SALC 2~17 밑에줄
		addBoardVo(BoardType.SALC, "SALC01", 2, Severity.MINOR);
		addBoardVo(BoardType.SALC, "SALC02", 3, Severity.NORMAL);
		addBoardVo(BoardType.SALC, "SALC03", 5, Severity.MAJOR);
		addBoardVo(BoardType.SALC, "SALC04", 6, Severity.NORMAL);
		addBoardVo(BoardType.SALC, "SALC05", 8, Severity.NORMAL);
		addBoardVo(BoardType.SALC, "SALC06", 9, Severity.MINOR);
		addBoardVo(BoardType.SALC, "SALC07", 10, Severity.CRITICAL);
		addBoardVo(BoardType.SALC, "SALC08", 12, Severity.NORMAL);
		addBoardVo(BoardType.SALC, "SALC09", 13, Severity.MAJOR);
		addBoardVo(BoardType.SALC, "SALC10", 17, Severity.NORMAL);
		
		// SALC 22~37 위에줄
		addBoardVo(BoardType.SALC, "SALC11", 24, Severity.NORMAL);
		addBoardVo(BoardType.SALC, "SALC12", 25, Severity.MINOR);
		addBoardVo(BoardType.SALC, "SALC13", 28, Severity.NORMAL);
		addBoardVo(BoardType.SALC, "SALC14", 29, Severity.NORMAL);
		addBoardVo(BoardType.SALC, "SALC15", 30, Severity.MAJOR);
		addBoardVo(BoardType.SALC, "SALC16", 33, Severity.CRITICAL);
		addBoardVo(BoardType.SALC, "SALC17", 35, Severity.MINOR);
		
		// SRGU
		addBoardVo(BoardType.SRGU, "SRGU01", 18, Severity.MAJOR);
		addBoardVo(BoardType.SRGU, "SRGU02", 38, Severity.NORMAL);
	}
	
	private void addBoardVo(BoardType type, String name, int id, int severity) {
		BoardVo vo = new BoardVo();
		
		vo.setBoardType(type);
		vo.setBoardName(name);
		vo.setBoardId(id);
		vo.setSeverity(severity);
		
		try {
			rackViewInterface.addBoardVo(vo);
		}
		catch (RemoteException e) {
			LOGGER.error(e.getMessage(), e);
		}
		
	}
	
	private void selectData() {
		List<BoardVo> boardList = null;
		
		try {
			boardList = rackViewInterface.selectBoardList();
		}
		catch (RemoteException e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		LOGGER.debug("boardList:" + boardList);
		
		for (BoardVo oneBoardVo : boardList) {
			int boardId = oneBoardVo.getBoardId();
			
			BoardPanel boardPanel = new BoardPanel(oneBoardVo);

			this.add(boardPanel, null);
			
			boardPanel.repaint();
			
			int boardWidth = boardPanel.getBoardWidth();
			int boardHeight = boardPanel.getBoardHeight();
			
			LOGGER.debug(String.format("boardId:%s, boardWidth:%s, boardHeight:%s", boardId, boardWidth, boardHeight));
			
			if (boardId < 20 && boardId > 2) {
				LOGGER.debug("startX:" + (BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM))));
				boardPanel.setBounds(BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM)),
						BOTTOM_BOARD_START_Y,
						boardPanel.getBoardWidth(),
						boardPanel.getBoardHeight());
			}
			else {
				LOGGER.debug("startX:" + (BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM))));
				boardPanel.setBounds(BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM)),
						TOP_BOARD_START_Y,
						boardPanel.getBoardWidth(),
						boardPanel.getBoardHeight());
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		LOGGER.debug("paintComponent");

		g.drawImage(ImageFactory.backgroundImageIcon.getImage(), 0, 0, this);
		
		g.setColor(Color.YELLOW);
		g.setFont(new Font("고딕체", Font.BOLD, 18));
		g.drawString("Test_Rack_View", TEXT_WIDTH, TEXT_HEIGHT);
	}
}