package com.barunsw.ojt.iwkim.day16;

import java.awt.Graphics;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel extends JPanel {
	private static Logger LOGGER = LogManager.getLogger(TestPanel.class);
	
	private ServerInterface serverIf;
	
	private final int BOARD_START_X 			= 26;
	private final int TOP_BOARD_START_Y		= 26;
	private final int BOTTOM_BOARD_START_Y	= 307;
	
	private final int SLOT_NUM = 20;
	
	public TestPanel() {
		try {
			initRmi();
			initComponent();
		} 
		catch(Exception ex) {
			LOGGER.error(ex.getMessage());
		}
	}

	private void initComponent() {
		this.setLayout(null);
	}
	
	private void initRmi() throws Exception {
		LOGGER.info("+++ Client Try Connection Start");
		Registry registry = LocateRegistry.getRegistry("127.0.0.1", ServerMain.RMI_PORT);
		
		Remote remote = registry.lookup("RACK_VIEW");
		
		if (remote instanceof ServerInterface) {
			LOGGER.info("clear remote : " + remote.toString());
			serverIf = (ServerInterface) remote;
		}
		else {
			LOGGER.info("error remote : " + remote.toString());
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(ImageFactory.backgroundImageIcon.getImage(), 0, 0, this);
		
		try {
			List<BoardVo> boardList = serverIf.getBoardData();

			for (BoardVo oneBoardVo : boardList) {
				int boardId = oneBoardVo.getBoardId();
						
				if (boardId == SLOT_NUM - 1) {
					continue;
				}
				
				BoardPanel boardPanel = new BoardPanel(oneBoardVo);
				
				this.add(boardPanel, null);
				
				int boardWidth = boardPanel.getBoardWidth();
				int boardHeight = boardPanel.getBoardHeight();
				
				LOGGER.debug(String.format("boardId:%s, boardWidth:%s, boardHeight:%s", boardId, boardWidth, boardHeight));
				
				if (boardId < 2) {
					boardPanel.setBounds(BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM)),
							TOP_BOARD_START_Y,
							boardPanel.getBoardWidth(),
							boardPanel.getBoardHeight());
					LOGGER.debug(String.format("x : %s, y : %s", BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM)), TOP_BOARD_START_Y));
				}
				else if (boardId < SLOT_NUM - 1) {			
					boardPanel.setBounds(BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM)),
							BOTTOM_BOARD_START_Y,
							boardPanel.getBoardWidth(),
							boardPanel.getBoardHeight());
					LOGGER.debug(String.format("x : %s, y : %s", BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM)), BOTTOM_BOARD_START_Y));
				}
				else {
					boardPanel.setBounds(BOARD_START_X + (BoardPanel.BOARD_WIDTH * ((boardId + 2) % SLOT_NUM)),
							TOP_BOARD_START_Y,
							boardPanel.getBoardWidth(),
							boardPanel.getBoardHeight());
					LOGGER.debug(String.format("x : %s, y : %s", BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % SLOT_NUM)), TOP_BOARD_START_Y));
				}
				
			}
		} catch (RemoteException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}