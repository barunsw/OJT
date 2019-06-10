package com.barunsw.ojt.gtkim.day15;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.vo.BoardVo;

/*
 *  background Rack에 부착될 unit panel을 생성하는 클래스
 */

public class BoardPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(BoardPanel.class);
	
	public static final int BOARD_WIDTH 	= 40;
	public static final int BOARD_HEIGHT 	= 271;

	public final int SRGU_BOARD_WIDTH = 80;
	
	public final int MPU_BOARD_HEIGHT = 550;
	
	private BoardVo boardVo;
	
	public BoardPanel(BoardVo boardVo) {
		this.boardVo = boardVo;
	}
	
	public int getBoardWidth() {
		int width = 0;
		
		switch (boardVo.getBoardType()) {
		case MPU:
		case SALC:
			width = BOARD_WIDTH;
			break;
		case SRGU:
			width = SRGU_BOARD_WIDTH;
			break;
		default :
			LOGGER.error("알 수 없는 BoardType 입니다" + boardVo.getBoardType());
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
		default :
			LOGGER.error("알 수 없는 BoardType 입니다" + boardVo.getBoardType());
		}

		return height;
	}
	
	private Image getBoardImage() {
		Image retImage = null;
		
		switch (boardVo.getBoardType()) {
		case MPU:
			retImage = ImageFactory.mpuImageIcon[boardVo.getSeverity()].getImage();
			break;
		case SALC:
			retImage = ImageFactory.salcImageIcon[boardVo.getSeverity()].getImage();
			break;
		case SRGU:
			retImage = ImageFactory.srguImageIcon[boardVo.getSeverity()].getImage();
			break;
		default :
			LOGGER.error("알 수 없는 BoardType 입니다" + boardVo.getBoardType());
		}
		
		return retImage;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		LOGGER.debug("paint_Board_Component");
		
		Image drawImage = getBoardImage();
		
		g.drawImage(drawImage, 0, 0, this);
	}
	
	@Override 
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
}
