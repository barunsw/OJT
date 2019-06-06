package com.barunsw.ojt.gtkim.day13;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.gtkim.day12.AddressVo;
import com.barunsw.ojt.gtkim.day12.DBAddressBookImpl;
import com.barunsw.ojt.gtkim.day12.SocketCommandVo;

public class ClientDatagramHandlerObj extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(ClientDatagramHandlerObj.class);
	
	private DatagramSocket socket;
	private DatagramPacket packet;
	
	private InetAddress address;
	private int port;
	
	private byte[] byteObject = new byte[1024];
	
	private ObjectInput objectInput = null;
	
	private DBAddressBookImpl dbController = new DBAddressBookImpl();
	
	public ClientDatagramHandlerObj (DatagramSocket socket, DatagramPacket packet) throws Exception { 
		this.address = packet.getAddress();
		this.port  	 = packet.getPort();	
		this.socket  = socket;
		
		this.byteObject = packet.getData();
		LOGGER.debug(new String(byteObject).trim() +  " Handler가 생성되었습니다. ");
	}
	
	@Override 
	public void run() {	
		try {
//			Thread inputThread = new Thread(new Runnable() {
//				@Override
//				public void run() {
//					try {
//						objectInput = new ObjectInputStream(
//								new ByteArrayInputStream(byteObject));		
//					}
//					catch (Exception ex) {
//						LOGGER.error(ex.getMessage(), ex);
//					}
//				}
//			});
			//inputThread.start();
			//inputThread.join();
			objectInput = new ObjectInputStream(
					new ByteArrayInputStream(byteObject));		
		
			LOGGER.debug("오브젝트 수신 대기 : ");
			Object object = objectInput.readObject();

			LOGGER.debug("받은 데이터 : " + object);

			if (object instanceof SocketCommandVo) {
				SocketCommandVo vo = (SocketCommandVo) object;

				LOGGER.debug("입력 커맨드 : " + vo.getCmdType());

				switch (vo.getCmdType()) {
				case SELECT:
					List<AddressVo> addressList = dbController.selectAddressList();
					selectHandler(addressList);
					break;
				case INSERT:
					dbController.insertAddress(vo.getAddressVo());
					break;
				case UPDATE:
					dbController.updateAddress(vo.getAddressVo());
					break;
				case DELETE:
					dbController.deleteAddress(vo.getAddressVo());
					break;
				default:
					LOGGER.debug("알수 없는 CmdType입니다 : " + vo.getCmdType());
				}
			}

		} 
		catch (Exception e) {
		}
		finally { 
			if (objectInput != null) {
				try { 
					objectInput.close();
				}
				catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			}
		}
	}
	
	private void selectHandler(List<AddressVo> addresList) throws Exception {
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ObjectOutput objectOutput = new ObjectOutputStream(byteArrayOutputStream)){
			
			objectOutput.writeObject(addresList);
			objectOutput.flush();
			
			byte[] sendMsg = byteArrayOutputStream.toByteArray();
		
			packet = new DatagramPacket(sendMsg, sendMsg.length, address, port);
			socket.send(packet);
		}
		catch (Exception ex) { 
			LOGGER.debug(ex.getMessage(), ex);
		}
	}
}
