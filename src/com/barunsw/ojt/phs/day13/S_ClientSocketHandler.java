package com.barunsw.ojt.phs.day13;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class S_ClientSocketHandler extends Thread {

	private static final Logger LOGGER = LogManager.getLogger(S_ClientSocketHandler.class);

	private Socket socket;

	private List<AddressVo> addressList = new ArrayList<AddressVo>();

	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;
	
	private S_ServerMain serverMain;

	public S_ClientSocketHandler(Socket socket, S_ServerMain serverMain) {
		this.socket = socket;
		this.serverMain = serverMain;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		serverMain.addStream(oos);
	}

	@Override
	public void run() {
		LOGGER.debug("serverThread start");
		Object dataPacket = new Object();

		while (true) {
			try {
				dataPacket = ois.readObject();
				LOGGER.debug("데이터 들어옴!!!!!" + dataPacket);

				if (dataPacket instanceof String) {
					LOGGER.debug("들어온데이터는String타입");
					addressList = serverMain.addressBook.selectAddressList();
					unicast(addressList);
				}
				else if (dataPacket instanceof AddressVo) {
					LOGGER.debug("들어온데이터는AddressVo타입");
					AddressVo changeData = (AddressVo) dataPacket;
					if (changeData.getCommand().equals("Insert")) {
						serverMain.addressBook.insertAddress(changeData);
						changeData.setCommand("InsertData");
						broadcast(changeData);
					}
					else if (((AddressVo) dataPacket).getCommand().equals("UpDate")) {
						serverMain.addressBook.updateAddress(changeData);
						changeData.setCommand("UpdateData");
						broadcast(changeData);
					}
					else if (((AddressVo) dataPacket).getCommand().equals("Delete")) {
						serverMain.addressBook.deleteAddress(changeData);
						changeData.setCommand("DeleteData");
						broadcast(changeData);
					}
				}

			}
			catch (Exception e) {
				LOGGER.debug(e.getMessage());
			}
		}

	}

	private void unicast(List<AddressVo> address) {
		LOGGER.debug("unicast");
		try {
			oos.writeObject(address);
			oos.flush();
		}
		catch (Exception e) {
			LOGGER.debug(e.getMessage(), e);
		}
	}

	private void broadcast(AddressVo address) {
		LOGGER.debug("broadcast");

		for (ObjectOutputStream oos : serverMain.getStreamList()) {
			try {
				oos.writeObject(address);
				oos.flush();
			}
			catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

}
