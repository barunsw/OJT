package com.barunsw.ojt.gtkim.day16;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlarmGenerator extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(AlarmGenerator.class);
	
	private boolean runFlag;
	private ServerInterface serverIf;
	
	public AlarmGenerator(ServerInterface serverIf) {
		this.serverIf = serverIf;
	}
	
	@Override 
	public void run() {
		runFlag = true;
		
		while (runFlag) {
			long startTime = System.currentTimeMillis();
			
			try {
				serverIf.change();
				LOGGER.debug("Send AlarmVO");
			} 
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
			
			long endTime = System.currentTimeMillis();
			
			try {
				Thread.sleep(5000L - (endTime - startTime));
			}
			catch (InterruptedException ie) {
				LOGGER.error(ie.getMessage(), ie);
			}
		}
	}
}
