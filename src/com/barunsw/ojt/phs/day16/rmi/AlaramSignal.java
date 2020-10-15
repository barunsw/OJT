package com.barunsw.ojt.phs.day16.rmi;

import java.rmi.RemoteException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlaramSignal extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(AlaramSignal.class);

	private boolean runFlag;
	private ServerInterface serverInterface;
	
	public AlaramSignal(ServerInterface serverInterface) {
		this.serverInterface = serverInterface;
	}
	
	@Override
	public void run() {
		runFlag = true;
		
		while (runFlag) {
			try {
				serverInterface.alaram();
				Thread.sleep(3000);
			}
			catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
}
