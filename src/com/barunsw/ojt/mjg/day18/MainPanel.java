package com.barunsw.ojt.mjg.day18;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.rmi.RemoteException;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainPanel extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainPanel.class);
	
	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	private JSplitPane jSplitPane = new JSplitPane();
	
	private ShelfPanel shelfPanel = new ShelfPanel();
	private AlarmPanel alarmPanel = new AlarmPanel();
	
	public MainPanel() {
		try {
			initComponent();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initComponent() throws RemoteException {
		this.setLayout(gridBagLayout);
		
        // JSplitPane 기본 설정
        jSplitPane.setOneTouchExpandable(true); 			  
        jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT); // 수직 분할
        jSplitPane.setDividerLocation(604); 			   	  

        // 위: Shelf, 아래: Alarm
        jSplitPane.setTopComponent(shelfPanel);
        jSplitPane.setBottomComponent(alarmPanel);

        // GridBagLayout에 추가
        this.add(jSplitPane, new GridBagConstraints(
                0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0
        		));
	}
}
