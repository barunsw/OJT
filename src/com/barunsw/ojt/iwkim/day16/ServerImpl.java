package com.barunsw.ojt.iwkim.day16;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.vo.BoardVo;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {
	private static final long serialVersionUID = -7642182568862518191L;
	
	private static Logger LOGGER = LogManager.getLogger(ServerImpl.class);

	public ServerImpl() throws RemoteException {
		
	}

	@Override
	public List<BoardVo> getBoardData() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
