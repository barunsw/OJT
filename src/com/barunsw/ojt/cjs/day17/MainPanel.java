package com.barunsw.ojt.cjs.day17;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.cjs.common.BoardVo;

public class MainPanel extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainPanel.class);
	private ServerInterface serverIf;
	private ClientInterface clientIf;
	
	private JSplitPane jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	BorderLayout grid =  new BorderLayout();

	
	public MainPanel() {   
		try {
			initRmi();
			initComponent();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	private void initRmi() throws RemoteException {
		clientIf = new ClientImpl();
		
		Registry registry = LocateRegistry.getRegistry(50001);
		Remote remote;
		try {
			remote = registry.lookup("Rack");
			if (remote instanceof ServerInterface) {
				serverIf = (ServerInterface) remote;
			}
			serverIf.register(clientIf);
		} 
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void initComponent() throws Exception {
		RackViewPanel rackViewPanel = new RackViewPanel(serverIf);
		MonitoringTablePanel monitoringTablePanel = new MonitoringTablePanel(serverIf); 
		
		this.setLayout(grid);
		this.add(jSplitPane);
		jSplitPane.setDividerLocation(600);

		jSplitPane.setTopComponent(rackViewPanel);
		jSplitPane.setBottomComponent(monitoringTablePanel);
	}
}
