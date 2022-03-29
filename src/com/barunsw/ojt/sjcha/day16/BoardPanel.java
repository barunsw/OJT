package com.barunsw.ojt.sjcha.day16;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BoardPanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(BoardPanel.class);

	public static final int BOARD_WIDTH 	= 40;
	public static final int BOARD_HEIGHT	= 271;

	public static final int MPU_BOARD_HEIGHT	= 550;

	private BoardVo boardVo;

	public BoardPanel(BoardVo boardVo) {
		LOGGER.debug("board test");
		this.boardVo = boardVo;

		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initComponent() throws Exception {
		this.setToolTipText(boardVo.getBoardName());
	}

	private Image getBoardImage() {
		Image boardImage = null;
		switch (boardVo.getSeverity()) {
		// normal
		case 0 :
			if (boardVo.getBoardType() == BoardType.MPU) {
				boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/MPU_critical.png")).getImage();
			}
			else if (boardVo.getBoardType() == BoardType.SALC) {
				boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SALC_critical.png")).getImage();
			}
			else {
				boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SRGU_critical.png")).getImage();
			}
			break;

		//MAJOR
		case 1 :
			if (boardVo.getBoardType() == BoardType.MPU) {
				boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/MPU_major.png")).getImage();
			}
			else if (boardVo.getBoardType() == BoardType.SALC) {
				boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SALC_major.png")).getImage();
			}
			else {
				boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SRGU_major.png")).getImage();
			}
			break;

		//MINOR
		case 2 :
			if (boardVo.getBoardType() == BoardType.MPU) {
				boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/MPU_minor.png")).getImage();
			}
			else if (boardVo.getBoardType() == BoardType.SALC) {
				boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SALC_minor.png")).getImage();
			}
			else {
				boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SRGU_minor.png")).getImage();
			}
			break;

		//NORMAL
		case 3 :
			if (boardVo.getBoardType() == BoardType.MPU) {
				boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/MPU_normal.png")).getImage();
			}
			else if (boardVo.getBoardType() == BoardType.SALC) {
				boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SALC_normal.png")).getImage();
			}
			else {
				boardImage = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/tamms/SRGU_normal.png")).getImage();
			}
			break;
		}
		return boardImage;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		switch (boardVo.getBoardType()) {
		case MPU:
			g.drawImage(getBoardImage(), 0, 0, this);
			break;
		case SALC:
			g.drawImage(getBoardImage(), 0, 0, this);
			break;
		case SRGU:
			g.drawImage(getBoardImage(), 0, 0, this);
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