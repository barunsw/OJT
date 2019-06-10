package com.barunsw.ojt.gtkim.day15;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.BoardType;
import com.barunsw.ojt.vo.BoardVo;

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);

	public static final int WIDTH 	 = 870;
	public static final int HEIGHT 	 = 635;
	
	public static final int SLOT_NUM = 20;
	
	public static final int TEXT_WIDTH		= 30;
	public static final int TEXT_HEIGHT		= 18;
	
	public final int BOARD_START_X			= 27;
	public final int BOARD_START_Y_TOP		= 26;
	public final int BOARD_START_Y_BOTTOM 	= 307;
	
	public TestPanel() {
		try {
			initComponent();
			initData();
		}
		catch (Exception ex) {
			LOGGER.debug(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() {
		this.setLayout(null);
	}
	
	private void initData() {
		List<BoardVo> boardList = getBoardData();
		
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
			
//			LOGGER.debug(String.format("Draw bounds X : %d, Board_X : %d, Board_Y : %d, id : %d Type : %s", 
//					BOARD_START_X + (border * width),boardPanel.getBoardWidth(), boardPanel.getBoardHeight(), oneBoardVo.getBoardId(), oneBoardVo.getBoardType()));
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
				boardVo.setSeverity((int)(Math.random()*4));
				boardVo.setBoardId(i);
			}
			else if ((i % (SLOT_NUM - 2)) == 0) {
				boardVo.setBoardType(BoardType.SRGU);
				boardVo.setBoardName("SRGU" + i);
				boardVo.setSeverity((int)(Math.random()*4));
				boardVo.setBoardId(i);
			}
			else if ((i % SLOT_NUM) == (SLOT_NUM - 1)) {	
//				boardVo.setSeverity(-1);
				boardVo.setBoardId(i);
			}
			else {
				boardVo.setBoardType(BoardType.SALC);
				boardVo.setBoardName("SALC" + i);
				boardVo.setSeverity((int)(Math.random()*4));
				boardVo.setBoardId(i);
			}
			
			boardList.add(boardVo);	
		}
		
		return boardList;
	}
	
	@Override 
	protected void paintComponent(Graphics g) { 
		LOGGER.debug("paint_Background_Component");
		
		g.drawImage(ImageFactory.backgroundImageIcon.getImage(),
				0, 0, this);
		
		g.setColor(Color.RED);
		g.setFont(new Font("바탕", Font.BOLD, 16));
		g.drawString("테스트 Rack_View", TEXT_WIDTH, TEXT_HEIGHT);
	}
}

