package com.barunsw.ojt.sjcha.day17;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
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

import com.barunsw.ojt.sjcha.day17.EventQueueWorker;
import com.barunsw.ojt.sjcha.day17.ClientImpl;
import com.barunsw.ojt.sjcha.day17.ServerInterface;
import com.barunsw.ojt.sjcha.day17.ServerMain;
import com.barunsw.ojt.yjkim.day17.SevCellRenderer;
import com.barunsw.ojt.sjcha.day17.CommonTableModel;
import com.barunsw.ojt.sjcha.day17.BoardPanel;
import com.barunsw.ojt.sjcha.day17.BoardVo;
import com.barunsw.ojt.sjcha.day17.BoardType;
import com.barunsw.ojt.sjcha.day17.AlarmVo;
import com.barunsw.ojt.sjcha.day17.EventListener;

public class RackViewPanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LogManager.getLogger(RackViewPanel.class);

	public static final int WIDTH 			= 854;
	public static final int HEIGHT 			= 604;

	public final int SLOT_NUM 				= 38;

	public final int BOARD_START_X 			= 27;
	public final int TOP_BOARD_START_Y 		= 26;
	public final int BOTTOM_BOARD_START_Y 	= 307;

	public final int TOP_BOARD_START_X 		= 27;
	public final int BOTTOM_BOARD_START_X 	= 107;

	public final int BOARD_WIDTH_GAP 		= 40;

	private ServerInterface serverIf 		= null;
	private ClientInterface clientIf;

	private List<BoardVo> boardList			= new ArrayList<>();

	private BoardPanel boardPanel;
	public static final int TABLE_HEIGHT 	= 500;

	private JScrollPane jScrollPane_Table 	= new JScrollPane();
	private JTable jTable_Result 			= new JTable();
	private CommonTableModel tableModel 	= new CommonTableModel();

	private int columnIdx = 0;
	private final int TABLE_COLUMN_SEV 			= columnIdx++; 

	public RackViewPanel() {
		try {
			getBoardData();
			initEvent();
			initRmi();
			initComponent();
			initTable();
			initData();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initEvent() {
		ClientMain.eventQueueWorker.addEventListener(this);
	}

	private void initTable() {
		Vector<String> columnData = new Vector<>();
		columnData.add("SEVERITY");
		columnData.add("ALARM ID");
		columnData.add("ALARM MSG");
		columnData.add("ALARM LOCATION");
		columnData.add("ALARM TIME");
		
		tableModel.setColumn(columnData);
		jTable_Result.setModel(tableModel);
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

			serverIf.register("차수진", clientIf);
		} 
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
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

	private List<BoardVo> getBoardData() throws RemoteException {
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

	private void initData() throws RemoteException {
		for (BoardVo oneBoardVo : boardList) {
			int boardId = oneBoardVo.getBoardId();
			LOGGER.debug(oneBoardVo);

			boardPanel = new BoardPanel(oneBoardVo);

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

			LOGGER.debug("add boardPanel to RackViewPanel");
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
		LOGGER.debug("client Panel Push : " + o);

		if (o instanceof AlarmVo) {
			AlarmVo alarmVo = (AlarmVo) o;

			// [1]에 boardId가 들어가져 있다. 
			// loc가 null이 아닌 경우.
			if (!(alarmVo.getAlarmLoc() == null)) {
				String[] alarmId = alarmVo.getAlarmLoc().split("=");
				
				int boardIndex = Integer.parseInt(alarmId[1]);
				
				Vector alarmData = new Vector();

				switch (alarmVo.getSeverity()) {
				case 0 :
					alarmData.add("CRITICAL");
					break;
				case 1 :
					alarmData.add("MAJOR");
					break;
				case 2 :
					alarmData.add("MINOR");
					break;
				case 3 :
					alarmData.add("NORMAL");
					break;
				}

				alarmData.add(alarmVo.getAlarmId());
				alarmData.add(alarmVo.getAlarmMsg());
				alarmData.add(boardIndex);
				alarmData.add(alarmVo.getEventTime());

				// 새로 들어오는 data는 첫번째 열에 들어가도록 한다.
				if(tableModel.getRowCount() == 0) {
					tableModel.addData(alarmData);
				}
				else {
					tableModel.addData(alarmData, 0);
				}

				for (int i = 0; i < boardList.size(); i++) {
					if(boardList.get(i).getBoardId() == boardIndex) {
						boardList.get(i).setSeverity(alarmVo.getSeverity());
					}
				}

				try {
					initData();
				} 
				catch (RemoteException e) {
					LOGGER.error(e.getMessage(), e);
				}

				// severity에 따라 셀의 색과 글자 색을 바꾼다. 
				jTable_Result.getColumnModel().getColumn(TABLE_COLUMN_SEV).setCellRenderer(new SeverityTableCellRenderer());
				tableModel.fireTableDataChanged();
			}
		}
	}
}