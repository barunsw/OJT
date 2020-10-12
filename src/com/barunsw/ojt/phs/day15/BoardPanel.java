package com.barunsw.ojt.phs.day15;

import java.awt.Graphics;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Severity;

public class BoardPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(BoardPanel.class);
	
	public static final int BOARD_WIDTH 	= 40;
	public static final int BOARD_HEIGHT	= 271;
	
	public static final int MPU_BOARD_HEIGHT	= 550;
	
	private BoardVo boardVo;
	
	public BoardPanel(BoardVo boardVo) {
		LOGGER.debug("board 생성");
		this.boardVo = boardVo;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		int severity = boardVo.getSeverity();
		
		switch (boardVo.getBoardType()) {
			case MPU: 
				if (severity == Severity.NORMAL) {
					g.drawImage(ImageFactory.mpuImageIcon[0].getImage(),
								0, 
								0,
								this);
				}
				else if (severity == Severity.MINOR) {
					g.drawImage(ImageFactory.mpuImageIcon[1].getImage(),
							0, 
							0,
							this);
				}
				else if (severity == Severity.MAJOR) {
					g.drawImage(ImageFactory.mpuImageIcon[2].getImage(),
							0, 
							0,
							this);
				}
				else if (severity == Severity.CRITICAL) {
					g.drawImage(ImageFactory.mpuImageIcon[3].getImage(),
							0, 
							0,
							this);
				}
				break;
				
			case SALC:
				if (severity == Severity.NORMAL) {
					g.drawImage(ImageFactory.salcImageIcon[0].getImage(),
								0, 
								0,
								this);
				}
				else if (severity == Severity.MINOR) {
					g.drawImage(ImageFactory.salcImageIcon[1].getImage(),
							0, 
							0,
							this);
				}
				else if (severity == Severity.MAJOR) {
					g.drawImage(ImageFactory.salcImageIcon[2].getImage(),
							0, 
							0,
							this);
				}
				else if (severity == Severity.CRITICAL) {
					g.drawImage(ImageFactory.salcImageIcon[3].getImage(),
							0, 
							0,
							this);
				}
				break;
				
				
			case SRGU:
				if (severity == Severity.NORMAL) {
					g.drawImage(ImageFactory.srguImageIcon[0].getImage(),
								0, 
								0,
								this);
				}
				else if (severity == Severity.MINOR) {
					g.drawImage(ImageFactory.srguImageIcon[1].getImage(),
							0, 
							0,
							this);
				}
				else if (severity == Severity.MAJOR) {
					g.drawImage(ImageFactory.srguImageIcon[2].getImage(),
							0, 
							0,
							this);
				}
				else if (severity == Severity.CRITICAL) {
					g.drawImage(ImageFactory.srguImageIcon[3].getImage(),
							0, 
							0,
							this);
				}
				break;
			default:
				break;
		}
	}
	
	public int getBoardWidth() {
		int width = 0;
		
		switch (boardVo.getBoardType()) {
			case MPU:
			case SALC:
				width = BOARD_WIDTH;
				break;
			case SRGU:
				width = BOARD_WIDTH * 2;
				break;
			default:
				break;
		}
		
		return width;
	}
	
	public int getBoardHeight() {
		int height = 0;
		
		switch (boardVo.getBoardType()) {
			case MPU:
				height = MPU_BOARD_HEIGHT;
				break;
			case SALC:
			case SRGU:
				height = BOARD_HEIGHT;
				break;
			default:
				break;
		}
		
		return height;	
	}
}
