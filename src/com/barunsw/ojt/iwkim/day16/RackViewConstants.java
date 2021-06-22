package com.barunsw.ojt.iwkim.day16;

import java.util.ArrayList;
import java.util.List;

import com.barunsw.ojt.constants.BoardType;

public class RackViewConstants {
	public final static int SLOT_NUM = 20;
	public final static int BOARD_SIZE = 38;
	public final static int SALC_BOARD_SIZE = 32;
	
	public final static int STATUS_NOMAL = 3;
	public final static int STATUS_MINOR = 2;
	public final static int STATUS_MAJOR = 1;
	public final static int STATUS_CRITICAL = 0;
	public final static int SEVERITY_SIZE = 4;
	
	public static List<BoardVo> boardList = new ArrayList<BoardVo>();
	
	public List<BoardVo> getBoardList() {
		boardList.add(new BoardVo(BoardType.MPU, 0, STATUS_NOMAL));
		boardList.add(new BoardVo(BoardType.MPU, 1, STATUS_NOMAL));

		for ( int i = 0; i < SALC_BOARD_SIZE; i ++ ) {
			boardList.add(new BoardVo(BoardType.SALC, i, STATUS_NOMAL));	
		}
		
		boardList.add(new BoardVo(BoardType.SRGU, 0, STATUS_NOMAL));
		boardList.add(new BoardVo(BoardType.SRGU, 1, STATUS_NOMAL));
		
		return boardList;
	}
}
