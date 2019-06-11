package com.barunsw.ojt.gtkim.day16;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.vo.BoardVo;

import sun.applet.Main;

/*
 *  background Rack에 부착될 unit panel을 생성하는 클래스
 */

public class BoardPanel extends JPanel implements EventListener {
	private static final Logger LOGGER = LogManager.getLogger(BoardPanel.class);
	
	public static final int BOARD_WIDTH 	= 40;
	public static final int BOARD_HEIGHT 	= 271;

	public final int SRGU_BOARD_WIDTH = 80;
	
	public final int MPU_BOARD_HEIGHT = 550;
	
	private BoardVo boardVo;
	
	
	public BoardPanel() {
//		LOGGER.debug("BoardPanel Create!");
		
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	public BoardPanel(BoardVo boardVo) {
		this();
		this.boardVo = boardVo;
	}
	
	private void initComponent() {
		this.setLayout(null);
		
		ClientMain.eventQueueWorker.addEventListener(this);
//		this.addMouseListener(new BoardPanel_this_MouseListener(this));
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
	
	void showInfo(MouseEvent e) {
		JLabel jLabel_Info = new JLabel(boardVo.getBoardName());
		
		
		this.add(jLabel_Info);
		jLabel_Info.setBackground(Color.white);
		jLabel_Info.setBounds(e.getX(), e.getY(), 70, 10);
		
	}
	
	public void close() {
		ClientMain.eventQueueWorker.removeEventListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
//		LOGGER.debug("paint_Board_Component");
		
		Image drawImage = getBoardImage();
		
		g.drawImage(drawImage, 0, 0, this);
	}

	@Override
	public void push(Object o) {
		if (o instanceof AlarmVo) {
			AlarmVo alarmVo = (AlarmVo)o;
			if (alarmVo.getBoardId() == boardVo.getBoardId()) {
				boardVo.setSeverity(alarmVo.getSeverity());
				repaint();
			}
		}
	}
	
	@Override 
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
}

class BoardPanel_this_MouseListener extends MouseAdapter {
	BoardPanel adaptee;
	
	public BoardPanel_this_MouseListener(BoardPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		adaptee.showInfo(e);
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
}
