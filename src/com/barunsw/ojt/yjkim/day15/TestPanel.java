package com.barunsw.ojt.yjkim.day15;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
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
	private static final Logger LOGGER = LogManager.getLogger(TestPanel.class);

	// 패널의 폭
	public static final int WIDTH = 854;
	// 패널의 넓이
	public static final int HEIGHT = 604;
	// 슬롯 넘버
	public final int SLOT_NUM = 37;
	// 시작위치
	public final int TOP_BOARD_START_X = 27;
	public final int BOTTOM_BOARD_START_X = 107;
	public final int TOP_BOARD_START_Y = 26;
	public final int BOTTOM_BOARD_START_Y = 307;
	// board 위치 간격
	public final int BOARD_WIDTH_GAP = 40;
	private String greetings = "Hello World";

	public TestPanel() {
		try {
			initComponent();
			initData();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initComponent() throws Exception {
		this.setLayout(null);
	}

	private List<BoardVo> getBoardData() {
		List<BoardVo> boardList = new ArrayList<BoardVo>();

		for (int i = 0; i < SLOT_NUM; i++) {
			BoardVo boardVo = new BoardVo();
			boardVo.setBoardId(i);
			boardVo.setSeverity(getSeverity());
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

	private void initData() {
		// 연동에 의해 board 정보 조회
		List<BoardVo> boardList = getBoardData();

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
		
		  g.setColor(Color.white); g.fillRect(0, 0, this.getWidth(), this.getHeight());
		  
		  g.setColor(Color.black); g.drawOval(100, 100, 300, 200);
		  
		  // 문자열을 가운데 쓴다. 
		  g.setColor(Color.red); g.setFont(new Font("Tahoma",
		  Font.BOLD, 20));
		  
//		  int stringWidth = g.getFontMetrics().stringWidth(greetings); int stringHeight
//		  = g.getFontMetrics().getHeight();
//		  
//		  int strX = (this.getWidth() - stringWidth) / 2; int strY = (this.getHeight()
//		  - stringHeight) / 2;
//		  int ascent 	= g.getFontMetrics().getAscent();
//		  int descent	= g.getFontMetrics().getDescent();
//		  int height 	= g.getFontMetrics().getHeight();
//		  int leading 	= g.getFontMetrics().getLeading();
//		  int baseline 	= 60;
//		  int baseline2;
//		  String s = "java";
//		  int x = 600 , x2, y1, y2, y3, y4, y5;
//		  g.drawString(s, x, baseline);
//		  g.drawString(greetings, strX, strY);
//		 
//		  y1 = baseline - ascent;
//		  g.drawLine(580, y1, 700, y1);
//		  
//		  g.drawLine(580, baseline, 750, baseline);
//		  
//		  y2 = baseline + descent + leading;
//		  g.drawLine(580, y2, 770, y2);
//		  
//		  y3 = baseline + descent + leading;
//		  g.drawLine(580, y3, 790, y3);
//
//		  baseline2 = baseline + g.getFontMetrics().getHeight();
//		  g.drawString(s, x, baseline2);  
//		  
//		  g.drawLine(580,baseline2,800,baseline2);
//		  
//		  y4 = baseline2 - ascent;
//		  g.drawLine(700, y4, 800, y4);
//		 
//		  y5 = baseline2 - descent;
//		  g.drawLine(580, y5, 780, y5);
//		  
//		  x2 = x + g.getFontMetrics().stringWidth(s);
//		  g.drawLine(x, 5, x, 150);
//		  g.drawLine(x2, 5, x2, 150);
//		 
		 g.drawImage(ImageFactory.backgroundImageIcon.getImage(),
		 			0, 0, this);
	}

	public int getSeverity() {
		double randomServerity = Math.random();
		int randomValue = (int) (randomServerity * 4);
		if (randomValue == 0) {
			return Severity.CRITICAL;
		} else if (randomValue == 1) {
			return Severity.MAJOR;
		} else if (randomValue == 2) {
			return Severity.MINOR;
		} else {
			return Severity.NORMAL;
		}
	}
}