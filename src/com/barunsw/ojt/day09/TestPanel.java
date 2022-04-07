package com.barunsw.ojt.day09;

import java.util.List;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.vo.AddressVo;

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestPanel.class);

	private AddressBookInterface addressBookIf;
	
	public TestPanel() {
		try {
			initAddressBookIf();
			initData();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initAddressBookIf() throws Exception {
		String className = SwingTest.properties.getProperty("address_if_class");
				
		Object o = Class.forName(className).newInstance();
		if (o instanceof AddressBookInterface) {
			addressBookIf = (AddressBookInterface)o; 
		}
	}
	
	private void initData() {
		List<AddressVo> list = addressBookIf.selectAddressList(null);
		for (AddressVo oneAddress : list) {
			LOGGER.debug(oneAddress.toString());
		}
	}
}