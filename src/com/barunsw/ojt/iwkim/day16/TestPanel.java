package com.barunsw.ojt.iwkim.day16;

import java.awt.Graphics;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestPanel extends JPanel {
	private static Logger LOGGER = LogManager.getLogger(TestPanel.class);

	private ServerInterface serverIf;

	private final int MPU_BOARD_START_X 		= 26;
	private final int TOP_BOARD_START_Y		= 26;
	private final int BOTTOM_BOARD_START_Y	= 307;

	private final int SRGU_BOARD_START_X		= MPU_BOARD_START_X + BoardPanel.BOARD_WIDTH * 18;
	private final int SRGU_FIRST_BOARD_ID	= 0;
	private final int SRGU_SECOND_BOARD_ID	= 1;

	private final int SACL_BOARD_START_X		= MPU_BOARD_START_X + BoardPanel.BOARD_WIDTH * 2;
	private final int SACL_BOARD_HALF_SIZE	= 16;		

	private final int TERM = 5000;

	public TestPanel() {
		try {
			initRmi();
			initComponent();
			initData();
			pollingAlarmInfo();
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

		Remote remote = registry.lookup(ServerMain.BIND_NAME);

		if (remote instanceof ServerInterface) {
			LOGGER.info("clear remote : " + remote.toString());
			serverIf = (ServerInterface) remote;
		}
		else {
			LOGGER.info("error remote : " + remote.toString());
		}
	}

	private void initData() throws RemoteException {
		List<BoardVo> boardList = serverIf.getBoardData();

		for (BoardVo oneBoardVo : boardList) {
			int boardId = oneBoardVo.getBoardId();

			BoardPanel boardPanel = new BoardPanel(oneBoardVo);

			this.add(boardPanel);

			switch (oneBoardVo.getBoardType()) {
			case MPU :
				boardPanel.setBounds(
						MPU_BOARD_START_X + BoardPanel.BOARD_WIDTH * boardId
						, TOP_BOARD_START_Y
						, BoardPanel.BOARD_WIDTH
						, BoardPanel.LONG_BOARD_HEIGHT);
				break;
			case SRGU :
				if (boardId == SRGU_FIRST_BOARD_ID) {
					boardPanel.setBounds(
							SRGU_BOARD_START_X
							, BOTTOM_BOARD_START_Y
							, BoardPanel.BOARD_WIDTH * 2
							, BoardPanel.BOARD_HEIGHT);
				}
				else if (boardId == SRGU_SECOND_BOARD_ID) {
					boardPanel.setBounds(
							SRGU_BOARD_START_X
							, TOP_BOARD_START_Y
							, BoardPanel.BOARD_WIDTH * 2
							, BoardPanel.BOARD_HEIGHT);
				}
				break;
			case SALC :
				if (boardId < SACL_BOARD_HALF_SIZE) {
					boardPanel.setBounds(
							SACL_BOARD_START_X + BoardPanel.BOARD_WIDTH * boardId
							, BOTTOM_BOARD_START_Y
							, BoardPanel.BOARD_WIDTH 
							, BoardPanel.BOARD_HEIGHT);
				}
				else if (boardId >= SACL_BOARD_HALF_SIZE) {
					boardPanel.setBounds(
							SACL_BOARD_START_X + BoardPanel.BOARD_WIDTH * (boardId - SACL_BOARD_HALF_SIZE) 
							, TOP_BOARD_START_Y
							, BoardPanel.BOARD_WIDTH 
							, BoardPanel.BOARD_HEIGHT);
				}
				break;
			}

			//			int boardWidth = boardPanel.getBoardWidth();
			//			int boardHeight = boardPanel.getBoardHeight();
			//			
			//			LOGGER.debug(String.format("boardId:%s, boardWidth:%s, boardHeight:%s", boardId, boardWidth, boardHeight));
			//			
			//			if (boardId < 2) {
			//				boardPanel.setBounds(BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % RackViewConstants.SLOT_NUM)),
			//						TOP_BOARD_START_Y,
			//						boardPanel.getBoardWidth(),
			//						boardPanel.getBoardHeight());
			//			}
			//			else if (boardId < RackViewConstants.SLOT_NUM - 1) {			
			//				boardPanel.setBounds(BOARD_START_X + (BoardPanel.BOARD_WIDTH * (boardId % RackViewConstants.SLOT_NUM)),
			//						BOTTOM_BOARD_START_Y,
			//						boardPanel.getBoardWidth(),
			//						boardPanel.getBoardHeight());
			//			}
			//			else {
			//				boardPanel.setBounds(BOARD_START_X + (BoardPanel.BOARD_WIDTH * ((boardId + 3) % RackViewConstants.SLOT_NUM)),
			//						TOP_BOARD_START_Y,
			//						boardPanel.getBoardWidth(),
			//						boardPanel.getBoardHeight());
			//			}
		}
	}

	private void pollingAlarmInfo() {
		Thread thread = new Thread(new Runnable() {
			boolean runFlag = true;
			@Override
			public void run() {

				while (runFlag) {

					try {
						Thread.sleep(TERM);

						// RMI 알람 수집 할것
						AlarmVo alarmVo = serverIf.getAlarmInfo();
						LOGGER.info("서버에서 수집된 시간 : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(alarmVo.getCollectTime()));

						// 수집된 알람으로 board 반영할것. 
						initAlarmData(alarmVo);

					} catch (RemoteException re) {
						LOGGER.error(re.getMessage(), re);
					}
					catch (InterruptedException ie) {
						LOGGER.error(ie.getMessage(), ie);
					}
				}
			}
		});
		thread.start();
	}

	private void initAlarmData(AlarmVo alarmVo) throws RemoteException{
		BoardVo oneBoardVo = new BoardVo(alarmVo.getBoardType(), alarmVo.getBoardId(), alarmVo.getSeverity());
		
		LOGGER.info("oneBoardVo.getSeverity() : " + oneBoardVo.getSeverity());
		
		int boardId = oneBoardVo.getBoardId();

		BoardPanel boardPanel = new BoardPanel(oneBoardVo);

		this.add(boardPanel);

		if (boardId < SACL_BOARD_HALF_SIZE) {
			boardPanel.setBounds(
					SACL_BOARD_START_X + BoardPanel.BOARD_WIDTH * boardId
					, BOTTOM_BOARD_START_Y
					, BoardPanel.BOARD_WIDTH 
					, BoardPanel.BOARD_HEIGHT);
		}
		else if (boardId >= SACL_BOARD_HALF_SIZE) {
			boardPanel.setBounds(
					SACL_BOARD_START_X + BoardPanel.BOARD_WIDTH * (boardId - SACL_BOARD_HALF_SIZE) 
					, TOP_BOARD_START_Y
					, BoardPanel.BOARD_WIDTH 
					, BoardPanel.BOARD_HEIGHT);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(ImageFactory.backgroundImageIcon.getImage(), 0, 0, this);
	}
}