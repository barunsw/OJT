package com.barunsw.ojt.yjkim.day16;

import java.rmi.RemoteException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlaramGenerator extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(AlaramGenerator.class);

	private boolean runFlag;
	private ServerInterface serverIf;
	
	public AlaramGenerator(ServerInterface serverIf) {
		this.serverIf = serverIf;
	}
	
	@Override
	public void run() {
		runFlag = true;
		
		while (runFlag) {
			try {
				serverIf.send();
				Thread.sleep(10000);
			} catch (RemoteException re) {
				LOGGER.error(re.getMessage(), re);
			} catch (InterruptedException ie) {
				LOGGER.error(ie.getMessage(), ie);
			}
		}
	}
}
