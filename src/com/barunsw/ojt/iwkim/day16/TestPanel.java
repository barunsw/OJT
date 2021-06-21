package com.barunsw.ojt.iwkim.day16;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.BoardType;
import com.barunsw.ojt.constants.Severity;
import com.barunsw.ojt.vo.BoardVo;

public class TestPanel extends JPanel {
	private static Logger LOGGER = LogManager.getLogger(TestPanel.class);
	
	private List<BoardVo> boardList = new ArrayList<>();
	
	private final int BOARD_START_X = 26;
	private final int BOARD_START_Y = 26;
	
	private final int BOARD_WIDTH	= 40;
	private final int BOARD_HEIGHT	= 271;
	
	private final int LONG_BOARD_HEIGHT = 550;
	
	

	public TestPanel() {
		try {
			initTestData();
		} 
		catch(Exception ex) {
			LOGGER.error(ex.getMessage());
		}
	}

	private void initComponent() {
		this.setLayout(null);
	}
	
	private void initTestData() {
		BoardVo mpuBoardVo = new BoardVo();
		mpuBoardVo.setBoardId(0);
		mpuBoardVo.setBoardType(BoardType.MPU);
		mpuBoardVo.setBoardName("MPU" + 0);
		mpuBoardVo.setSeverity(Severity.NORMAL);
		
		boardList.add(mpuBoardVo);
		
		mpuBoardVo = new BoardVo();
		mpuBoardVo.setBoardId(1);
		mpuBoardVo.setBoardType(BoardType.MPU);
		mpuBoardVo.setBoardName("MPU" + 1);
		mpuBoardVo.setSeverity(Severity.CRITICAL);
		
		boardList.add(mpuBoardVo);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(ImageFactory.backgroundImageIcon.getImage(), 0, 0, this);
		
		for (BoardVo oneBoard : boardList) {
			BoardType boardType = oneBoard.getBoardType();
			int boardId 		= oneBoard.getBoardId();
			int severity		= oneBoard.getSeverity();
			
			if (boardType == BoardType.MPU) {
				g.drawImage(ImageFactory.mpuImageIcon[severity].getImage(), BOARD_START_X + (boardId * BOARD_WIDTH), BOARD_START_Y, this);
			}
		}
	}
}