package com.barunsw.ojt.yjkim.day16;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.BoardType;
import com.barunsw.ojt.constants.Severity;
import com.barunsw.ojt.vo.BoardVo;
import com.barunsw.ojt.yjkim.day10.CommonTableModel;

public class TestPanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);
	public static EventQueueWorker eventQueueWorker = new EventQueueWorker();
	private ClientInterface clientIf;
	private ServerInterface serverIf = null;
	
	// 패널의 폭
	public static final int WIDTH 	= 854;
	// 패널의 넓이
	public static final int HEIGHT 	= 604;
	// 슬롯 넘버
	public final int SLOT_NUM 		= 38;
	// 시작위치
	public final int TOP_BOARD_START_X 		= 27;
	public final int BOTTOM_BOARD_START_X 	= 107;
	public final int TOP_BOARD_START_Y 		= 26;
	public final int BOTTOM_BOARD_START_Y 	= 307;
	// board 위치 간격
	public final int BOARD_WIDTH_GAP		= 40;
	
	private int columnIdx = 0;
	private final int TABLE_COLUMN_IDX 			= columnIdx++; 
	private final int TABLE_COLUMN_TYPE 		= columnIdx++; 
	private final int TABLE_COLUMN_SERVERITY  	= columnIdx++;
	private final int TABLE_COLUMN_STATE 		= columnIdx++; 
	
	private JPanel jPanel_gridBag 				= new JPanel();
	private JScrollPane jScrollPane_Table 		= new JScrollPane();
	private JTable jTable_Result 				= new JTable();
	private CommonTableModel tableModel 		= new CommonTableModel();
	private List<BoardVo> boardList =  new ArrayList<BoardVo>();
	public TestPanel() {
		try {
			initEvent();
			initComponent();
			initConnectRMI();
			initTable();
			initData();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	private void initEvent() {
		try {
			clientIf = new ClientImpl();
		} catch (RemoteException re) {
			LOGGER.error(re);
		}
		eventQueueWorker.addEventListener(this);
		
		eventQueueWorker.start();
	}
	
	private void initComponent() throws Exception {
		this.setLayout(null);
		this.add(jPanel_gridBag);
		jPanel_gridBag.setBounds(0,604,854,520);
		jPanel_gridBag.setVisible(true);
		jPanel_gridBag.add(jScrollPane_Table, 
				new GridBagConstraints(1, 1, 1, 1,
						1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		jScrollPane_Table.getViewport().add(jTable_Result);
		LOGGER.debug("--- TestRack StateView initComponent");
	}
	
	private void initConnectRMI() {
		try {
			Registry registry;
			registry = LocateRegistry.getRegistry(ServerMain.PORT);
			
			Remote remote = registry.lookup(ServerMain.BIND_NAME);
			if (remote instanceof ServerInterface) {
				serverIf = (ServerInterface)remote;
			}
			if (serverIf != null) {
				serverIf.register("start", clientIf);
				serverIf.send("start", "start");
			}
		
		} catch (RemoteException re) {
			LOGGER.error(re.getMessage(), re);
		} catch (NotBoundException nbe) {
			LOGGER.error(nbe.getMessage(), nbe);
		}
	}
	
	private void initTable() {
		LOGGER.debug("+++ TestRack StateView initTable ");

		Vector<String> columnData = new Vector<>();
		columnData.add("Idx");
		columnData.add("Type");
		columnData.add("Severity");
		columnData.add("State");
		
		tableModel.setColumn(columnData);
		jTable_Result.setModel(tableModel);
		jTable_Result.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		LOGGER.debug("---TestRack StateView initTable ");
		
		for (int i = 0; i < SLOT_NUM; i++) {
			Vector OneData = new Vector();
			OneData.add(i);
			if (i < 2) {
				OneData.add("MPU");
			} else if (i % 18 == 0 || i % 19 == 0 || i % 37 == 0) {
				OneData.add("SRGU");
			} else {
				OneData.add("SALC");
			}
			OneData.add("NORMAL");
			OneData.add("NONE");
			tableModel.addData(OneData);
		}		
	}
	
	private void initData() {
		// 연동에 의해 board 정보 조회
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
			} else {
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
			} else if (i % 18 == 0 || i % 36 == 0) {
				boardVo.setBoardType(BoardType.SRGU);
				boardVo.setBoardName("SRGU");
			} else {
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

			LOGGER.debug(String.format("+++ TestPanel에 boardPanel(%s, %s) 추가", boardPanel.getWidth(),
					boardPanel.getHeight()));
			boardPanel.repaint();

			if (boardId < 20) {
				boardPanel.setBounds(TOP_BOARD_START_X + (boardId * BOARD_WIDTH_GAP), TOP_BOARD_START_Y,
						boardPanel.getBoardWidth(), boardPanel.getBoardHeight());
			} else {
				boardPanel.setBounds(BOTTOM_BOARD_START_X + (boardId % 20 * BOARD_WIDTH_GAP), BOTTOM_BOARD_START_Y,
						boardPanel.getBoardWidth(), boardPanel.getBoardHeight());
			}
			LOGGER.debug("--- TestPanel에 boardPanel 추가");
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
		String[] str = o.toString().split("/");
		LOGGER.debug(str[0] + str[1]);
		switch (str[0]) {
			case "0" :
				tableModel.setValueAt("CRITICAL", Integer.parseInt(str[1]), TABLE_COLUMN_SERVERITY);
				break;
			case "1" :
				tableModel.setValueAt("MAJOR", Integer.parseInt(str[1]), TABLE_COLUMN_SERVERITY);
				break;
			case "2" :
				tableModel.setValueAt("MINOR", Integer.parseInt(str[1]), TABLE_COLUMN_SERVERITY);
				break;
			case "3" :
				tableModel.setValueAt("NORMAL", Integer.parseInt(str[1]), TABLE_COLUMN_SERVERITY);
				break;
			}
		
			for (int i = 0; i < boardList.size(); i++) {
				if(boardList.get(i).getBoardId() == Integer.parseInt(str[1])) {
					boardList.get(i).setSeverity(Integer.parseInt(str[0]));
				}
			}
		ResetData();
		tableModel.fireTableDataChanged();		
	}

	
}