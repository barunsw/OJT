package com.barunsw.ojt.day15;

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
	
	public static final int WIDTH 	= 854;
	public static final int HEIGHT	= 604;
	
	public final int SLOT_NUM		= 20;
	
	public final int BOARD_START_X			= 27;
	public final int TOP_BOARD_START_Y		= 26;
	public final int BOTTOM_BOARD_START_Y	= 307;
	
	private String greetings = "Hello World";
	
	public TestPanel() {
		try {
			initComponent();
			initData();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		this.setLayout(null);
	}
	
	private List<BoardVo> getBoardData() {
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		
		BoardVo boardVo = new BoardVo();
		boardVo.setBoardType(BoardType.MPU);
		boardVo.setBoardName("MPU");
		boardVo.setBoardId(0);
		
		boardList.add(boardVo);
		
		return boardList;
	}
	
	private void initData() {
		// 연동에 의해 board 정보 조회
		List<BoardVo> boardList = getBoardData();
		
		for (BoardVo oneBoardVo : boardList) {
			int boardId = oneBoardVo.getBoardId();
			
			BoardPanel boardPanel = new BoardPanel(oneBoardVo);

			this.add(boardPanel, null);
			
			LOGGER.debug(String.format("+++ TestPanel에 boardPanel(%s, %s) 추가",
					boardPanel.getWidth(), boardPanel.getHeight()));
			boardPanel.repaint();
			
			if (boardId < 20) {
				boardPanel.setBounds(BOARD_START_X ,
						TOP_BOARD_START_Y,
						boardPanel.getBoardWidth(),
						boardPanel.getBoardHeight());
			}
			else {
				boardPanel.setBounds(BOARD_START_X,
						BOTTOM_BOARD_START_Y,
						boardPanel.getBoardWidth(),
						boardPanel.getBoardHeight());
			}
			
			LOGGER.debug("--- TestPanel에 boardPanel 추가");
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		LOGGER.debug("paintComponent");
/*		
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(Color.black);
		g.drawOval(100, 100, 300, 200);
		
		// 문자열을 가운데 쓴다.
		g.setColor(Color.red);
		g.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		int stringWidth = g.getFontMetrics().stringWidth(greetings);
		int stringHeight = g.getFontMetrics().getHeight();
		
		int strX = (this.getWidth() - stringWidth) / 2; 
		int strY = (this.getHeight() - stringHeight) / 2; 
		
		g.drawString(greetings, strX, strY);
*/
		g.drawImage(ImageFactory.backgroundImageIcon.getImage(),
				0, 0, this);
	}
}