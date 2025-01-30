package com.barunsw.ojt.gtkim.day16;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.BoardType;
import com.barunsw.ojt.constants.Severity;
import com.barunsw.ojt.vo.BoardVo;

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);

//	public static final int WIDTH 	 = 870;
//	public static final int HEIGHT 	 = 635;
	public static final int WIDTH 	 = 854;
	public static final int HEIGHT 	 = 604;
	
	public static final int TABLE_HEIGHT = 380;
	
	public static final int SLOT_NUM = 20;
	
	public static final int TEXT_WIDTH		= 30;
	public static final int TEXT_HEIGHT		= 18;
	
	public static final int BUTTON_WIDTH    = 80;
	public static final int BUTTON_HEIGHT	= 25;
	
	public final int BOARD_START_X			= 27;
	public final int BOARD_START_Y_TOP		= 26;
	public final int BOARD_START_Y_BOTTOM 	= 307;
	
	private JButton jButton_Change = new JButton("Change");
	
	private TablePanel tablePanel = new TablePanel();
	
	private ServerInterface serverIf;
	private ClientInterface clientIf;
	
	public TestPanel() {
		try {
			initRmi();
			initComponent();
			initData();
		}
		catch (Exception ex) {
			LOGGER.debug(ex.getMessage(), ex);
		}
	}
	
	private void initRmi() {
		try {
			clientIf = new ClientImpl();
			
			Registry registry = LocateRegistry.getRegistry(ServerMain.PORT);
			Remote remote = registry.lookup(ServerMain.BIND_NAME);
			if (remote instanceof ServerInterface) {
				serverIf = (ServerInterface)remote;
			}
			
			if(serverIf != null) {
				serverIf.register(clientIf);
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() {
		this.setLayout(null);
		
		this.add(tablePanel);
		this.add(jButton_Change);
	
		tablePanel.setBounds(0, HEIGHT, WIDTH, TABLE_HEIGHT);
		jButton_Change.setBounds(TEXT_WIDTH * 7, 0, BUTTON_WIDTH, BUTTON_HEIGHT);

		jButton_Change.addActionListener(new TestPanel_JButton_ActionListener(this));
	}
	
	private void initData() {
		List<BoardVo> boardList = getBoardData();
		
		tablePanel.updateData(boardList);
		
		for (BoardVo oneBoardVo : boardList) {
			int boardId = oneBoardVo.getBoardId();
					
			if ((boardId % SLOT_NUM) == 19) {
				continue;
			}
			
			BoardPanel boardPanel = new BoardPanel(oneBoardVo);
			this.add(boardPanel);
			
			int border = boardId % SLOT_NUM;

			if (boardId < SLOT_NUM) { 	
				boardPanel.setBounds(BOARD_START_X + (border * BoardPanel.BOARD_WIDTH), BOARD_START_Y_TOP,
						boardPanel.getBoardWidth(), boardPanel.getBoardHeight());
			}
			else { 
				border += 2;
				boardPanel.setBounds(BOARD_START_X + (border * BoardPanel.BOARD_WIDTH), BOARD_START_Y_BOTTOM,
						boardPanel.getBoardWidth(), boardPanel.getBoardHeight());
			}
		}
	}
	
	private List<BoardVo> getBoardData() {
		LOGGER.debug("데이터를 읽어옵니다.");
		List<BoardVo> boardList = new ArrayList<>();
		
		for (int i = 0; i < 37; i++) {
			BoardVo boardVo = new BoardVo();
			
			// 0, 1번 MPU 2 ~ 17번, 20 ~ 35번 SALC, 18번 36번 SRGU
			if (i < 2) {
				boardVo.setBoardType(BoardType.MPU);
				boardVo.setBoardName("MPU" + i);
			}
			else if ((i % (SLOT_NUM - 2)) == 0) {
				boardVo.setBoardType(BoardType.SRGU);
				boardVo.setBoardName("SRGU" + i);

			}
			else {
				boardVo.setBoardType(BoardType.SALC);
				boardVo.setBoardName("SALC" + i);
			}
			boardVo.setSeverity(Severity.NORMAL);
			boardVo.setBoardId(i);
			
			boardList.add(boardVo);	
		}
		
		return boardList;
	}
	
	public void buttonAction(ActionEvent e) {
		try {
			serverIf.change();
		}
		catch (RemoteException re) {
			LOGGER.error(re.getMessage(), re);
		}
	}
	
	public void close() {
		try {
			tablePanel.close();
			// boardPanel.close(); 
			if (clientIf != null) {
				serverIf.deRegister(clientIf);
			}
		}
		catch (RemoteException re) {
			LOGGER.error(re.getMessage(), re);
		}
	}
	
	@Override 
	protected void paintComponent(Graphics g) { 
//		LOGGER.debug("paint_Background_Component");
		
		g.drawImage(ImageFactory.backgroundImageIcon.getImage(),
				0, 0, this);
		
		g.setColor(Color.RED);
		g.setFont(new Font("바탕", Font.BOLD, 16));
		g.drawString("테스트 Rack_View", TEXT_WIDTH, TEXT_HEIGHT);
	}
}

class TestPanel_JButton_ActionListener implements ActionListener {
	TestPanel adaptee;
	
	public TestPanel_JButton_ActionListener(TestPanel adaptee) { 
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.buttonAction(e);
	}
	
}