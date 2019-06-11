package com.barunsw.ojt.day15;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.vo.BoardVo;

public class BoardPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(BoardPanel.class);
	
	public final int BOARD_WIDTH 	= 40;
	public final int BOARD_HEIGHT	= 271;
	
	public final int MPU_BOARD_HEIGHT	= 550;
	
	private BoardVo boardVo;
	
	public BoardPanel(BoardVo boardVo) {
		LOGGER.debug("board 생성");
		this.boardVo = boardVo;
	}
	
	private Image getBoardImage() {
		// 보드 타입, 등급(Severity)에 따라 이미지를 반환
		// ImageFactory.mpuImageIcon[boardVo.getSeverity()];
		return null;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		LOGGER.debug("paintComponent");
		
		//Graphics2D g2d = (Graphics2D)g;
		switch (boardVo.getBoardType()) {
		case MPU:
			g.drawImage(ImageFactory.mpuNormalImageIcon.getImage(),
					0, 
					0,
					this);
			break;
		case SALC:
			g.drawImage(ImageFactory.salcNormalImageIcon.getImage(),
					0, 
					0,
					this);
			break;
		case SRGU:
			g.drawImage(ImageFactory.srguNormalImageIcon.getImage(),
					0, 
					0,
					this);
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
		}
		
		return height;	
	}
}