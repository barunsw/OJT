package com.barunsw.ojt.yjkim.day15;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.BoardType;
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
		Image boardImage = null;
		switch (boardVo.getSeverity()) {
			//CRITICAL
			case 0 :
				if (boardVo.getBoardType() == BoardType.MPU) {
					boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/MPU_critical.png")).getImage();
				} else if (boardVo.getBoardType() == BoardType.SALC) {
					boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SALC_critical.png")).getImage();
				} else {
					boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SRGU_critical.png")).getImage();
				}
				break;
			//MAJOR
			case 1 :
				if (boardVo.getBoardType() == BoardType.MPU) {
					boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/MPU_major.png")).getImage();
				} else if (boardVo.getBoardType() == BoardType.SALC) {
					boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SALC_major.png")).getImage();
				} else {
					boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SRGU_major.png")).getImage();
				}
				break;
			//MINOR
			case 2 :
				if (boardVo.getBoardType() == BoardType.MPU) {
					boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/MPU_minor.png")).getImage();
				} else if (boardVo.getBoardType() == BoardType.SALC) {
					boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SALC_minor.png")).getImage();
				} else {
					boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SRGU_minor.png")).getImage();
				}
				break;
			//NORMAL
			case 3 :
				if (boardVo.getBoardType() == BoardType.MPU) {
					boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/MPU_normal.png")).getImage();
				} else if (boardVo.getBoardType() == BoardType.SALC) {
					boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SALC_normal.png")).getImage();
				} else {
					boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SRGU_normal.png")).getImage();
				}
				break;
		}
		return boardImage;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		LOGGER.debug("paintComponent");
		
		//Graphics2D g2d = (Graphics2D)g;
		LOGGER.debug(boardVo.getBoardType());
		switch (boardVo.getBoardType()) {
		case MPU:
			/*g.drawImage(ImageFactory.mpuImageIcon[boardVo.getSeverity()].getImage(),
					0, 
					0,
					this);*/
			g.drawImage(getBoardImage(),
					0, 
					0,
					this);
			break;
		case SALC:
			/*g.drawImage(ImageFactory.salcImageIcon[boardVo.getSeverity()].getImage(),
					0, 
					0,
					this);*/
			g.drawImage(getBoardImage(),
					0, 
					0,
					this);
			break;
		case SRGU:
			/*g.drawImage(ImageFactory.srguImageIcon[boardVo.getSeverity()].getImage(),
					0, 
					0,
					this);*/
			g.drawImage(getBoardImage(),
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
