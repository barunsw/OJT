package com.barunsw.ojt.phs.day16.rmi;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.BoardType;

public class ClientPanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LogManager.getLogger(ClientPanel.class);
	public static EventQueueWorker eventQueueWorker = new EventQueueWorker();
	private ClientInterface clientIf;
	private ServerInterface serverIf = null;
	
	public static final int WIDTH 		 	= 854;
	
	public static final int HEIGHT 		 	= 600;
	public static final int TABLE_HEIGHT 	= 500;
	
	public final int SLOT_NUM 			    = 38;
	
	public final int TOP_BOARD_START_X 		= 27;
	public final int BOTTOM_BOARD_START_X 	= 107;
	public final int TOP_BOARD_START_Y 		= 26;
	public final int BOTTOM_BOARD_START_Y 	= 307;
	
	public final int BOARD_WIDTH_GAP		= 40;
	
	private int columnIdx = 0;
	private final int TABLE_COLUMN_SERVERITY  	= columnIdx++;
	
	private JScrollPane jScrollPane_Table 		= new JScrollPane();
	private JTable jTable_Result 				= new JTable();
	private CommonTableModel tableModel 		= new CommonTableModel();
	private List<BoardVo> boardList =  new ArrayList<BoardVo>();
	
	public ClientPanel() {
		try {
			initEvent();
			initComponent();
			initConnectRMI();
			initTable();
			initData();
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void initEvent() {
		eventQueueWorker.addEventListener(this);
		eventQueueWorker.start();
	}
	
	private void initComponent() throws Exception {
		this.setLayout(null);
		this.add(jScrollPane_Table, 
				new GridBagConstraints(0, 0, 1, 1,
						1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		jScrollPane_Table.setBounds(0,HEIGHT,WIDTH,TABLE_HEIGHT);
		jScrollPane_Table.getViewport().add(jTable_Result);
	}
	
	private void initConnectRMI() {
		try {
			clientIf = new ClientImpl();

			Registry registry = LocateRegistry.getRegistry(ServerMain.PORT);
			
			Remote remote = registry.lookup(ServerMain.BIND_NAME);
			if (remote instanceof ServerInterface) {
				serverIf = (ServerInterface)remote;
			}
			if (serverIf != null) {
				serverIf.register(clientIf);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void initTable() {
		Vector<String> columnData = new Vector<>();
		columnData.add("ID");
		columnData.add("SEV");
		columnData.add("MSG");
		columnData.add("LOC");
		columnData.add("TIME");

		tableModel.setColumn(columnData);
		jTable_Result.setModel(tableModel);
		jTable_Result.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTable_Result.setRowHeight(38);
		for (int i = 0; i < SLOT_NUM; i++) {
			Vector OneData = new Vector();
			OneData.add(i);
			if (i < 2) {
				OneData.add("MPU");
			}
			else if (i % 18 == 0 || i % 19 == 0 || i % 37 == 0) {
				OneData.add("SRGU");
			}
			else {
				OneData.add("SALC");
			}
			OneData.add("NORMAL");
			OneData.add("NONE");
			tableModel.addData(OneData);
		}		
	}
	
	private void initData() {
		boardList = getBoardData();
		for (BoardVo oneBoardVo : boardList) {
			int boardId = oneBoardVo.getBoardId();
			LOGGER.debug(oneBoardVo);
			BoardPanel boardPanel = new BoardPanel(oneBoardVo);

			this.add(boardPanel, null);

			LOGGER.debug(String.format("+++ TestPanel에 boardPanel(%s, %s) 추가", boardPanel.getWidth(),
					boardPanel.getHeight()));
			boardPanel.repaint();

			if (boardId < 20) {
				boardPanel.setBounds(TOP_BOARD_START_X + (boardId * BOARD_WIDTH_GAP), TOP_BOARD_START_Y,
						boardPanel.getBoardWidth(), boardPanel.getBoardHeight());
			}
			else {
				boardPanel.setBounds(BOTTOM_BOARD_START_X + (boardId % 20 * BOARD_WIDTH_GAP), BOTTOM_BOARD_START_Y,
						boardPanel.getBoardWidth(), boardPanel.getBoardHeight());
			}
			LOGGER.debug("--- TestPanel에 boardPanel 추가");
		}
	}
	
	private List<BoardVo> getBoardData() {

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
			boardList.add(boardVo);
		}
		return boardList;
	}
	
	private void ResetData() {
		// 연동에 의해 board 정보 조회
		for (BoardVo oneBoardVo : boardList) {
			int boardId = oneBoardVo.getBoardId();
			LOGGER.debug(oneBoardVo);
			BoardPanel boardPanel = new BoardPanel(oneBoardVo);

			this.add(boardPanel, null);

			boardPanel.repaint();

			if (boardId < 20) {
				boardPanel.setBounds(TOP_BOARD_START_X + (boardId * BOARD_WIDTH_GAP), TOP_BOARD_START_Y,
						boardPanel.getBoardWidth(), boardPanel.getBoardHeight());
			}
			else {
				boardPanel.setBounds(BOTTOM_BOARD_START_X + (boardId % 20 * BOARD_WIDTH_GAP), BOTTOM_BOARD_START_Y,
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
		LOGGER.debug("push : "+ o);
		if (o instanceof AlaramVo) {
			AlaramVo alaramVo = (AlaramVo)o;
			switch (alaramVo.getSeverity()) {
				case 0 :
					tableModel.setValueAt("CRITICAL", alaramVo.getIdx(), TABLE_COLUMN_SERVERITY);
					break;
				case 1 :
					tableModel.setValueAt("MAJOR", alaramVo.getIdx(), TABLE_COLUMN_SERVERITY);	
					break;
				case 2 :
					tableModel.setValueAt("MINOR", alaramVo.getIdx(), TABLE_COLUMN_SERVERITY);
					break;
				case 3 :
					tableModel.setValueAt("NORMAL", alaramVo.getIdx(), TABLE_COLUMN_SERVERITY);
					break;
			}
		
			for (int i = 0; i < boardList.size(); i++) {
				if(boardList.get(i).getBoardId() == alaramVo.getIdx()) {
					boardList.get(i).setSeverity(alaramVo.getSeverity());
				}
			}
		}
		ResetData();
		tableModel.fireTableDataChanged();		
	}
}