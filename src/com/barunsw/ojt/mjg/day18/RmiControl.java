package com.barunsw.ojt.mjg.day18;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.vo.BoardVo;

public class RmiControl {
	private static final Logger LOGGER = LogManager.getLogger(RmiControl.class);
	
	private ServerInterface serverIf;
	private ClientInterface clientIf;

	public RmiControl() {
		try {
			initRmi();
		}
		catch (Exception ex) {
			
		}
	}

	private void initRmi() {
	    try {
	        Registry registry = LocateRegistry.getRegistry(ServerMain.PORT);

	        Remote remote = registry.lookup(ServerMain.BIND_NAME);

			if (remote instanceof ServerInterface) {
				serverIf = (ServerInterface) remote;
			}
				
			// 클라이언트 구현체 생성
			clientIf = new ClientImpl();
			
			// ClientImpl을 서버쪽에 register
			// Panel에서 따로 register 호출할 필요 없이 미리 등록
			serverIf.register(clientIf);
	    } 
	    catch (Exception e) {
	        LOGGER.error("+++RMI initialization failed.", e);
	    }
	}
	
	public int register(ClientInterface clientIf) throws RemoteException {
		return (int) serverIf.register(clientIf);
	};
	
	public List<BoardVo> selectBoardList() throws RemoteException {
		return serverIf.selectBoardList();
	}
}
