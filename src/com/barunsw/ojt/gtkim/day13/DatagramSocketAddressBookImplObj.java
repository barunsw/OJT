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

import com.barunsw.ojt.gtkim.day12.AddressBookInterface;
import com.barunsw.ojt.gtkim.day12.AddressVo;
import com.barunsw.ojt.gtkim.day12.CmdType;
import com.barunsw.ojt.gtkim.day12.SocketCommandVo;

public class DatagramSocketAddressBookImplObj implements AddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(DatagramSocketAddressBookImplObj.class);
	
	public final int CLIENT_PORT = 65432;
	
	private DatagramSocket socket;
	private DatagramPacket packet;
	
	private InetAddress hostaddress;
	private int hostport;
	
	private ObjectInput objectInput	= null;

	public DatagramSocketAddressBookImplObj(String host, int port) throws Exception {
		this.hostaddress = InetAddress.getByName(host);
		this.hostport 	 = port;
		
		this.socket = new DatagramSocket(CLIENT_PORT);
	}

	@Override
	public List<AddressVo> selectAddressList() throws Exception {	
		try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
				ObjectOutput objectOutput = new ObjectOutputStream(byteOutputStream)) {
			SocketCommandVo vo = new SocketCommandVo();
			vo.setCmdType(CmdType.SELECT);
			
			objectOutput.writeObject(vo);
			objectOutput.flush();
	
			byte[] sendMsg = byteOutputStream.toByteArray();
			
			packet = new DatagramPacket(sendMsg, sendMsg.length, hostaddress, hostport);
			socket.send(packet);
			
			LOGGER.debug("Select Data send " + new String(sendMsg));
			
			byte[] recvMsg = new byte[1024];		
			packet = new DatagramPacket(recvMsg, recvMsg.length);
			socket.receive(packet);
		
//			Thread inputThread = new Thread(new Runnable() {
//				@Override
//				public void run() {
//					try {
//						objectInput = new ObjectInputStream(
//								new ByteArrayInputStream(recvMsg));						
//					}
//					catch (Exception ex) {
//						LOGGER.error(ex.getMessage(), ex);
//					}
//				}
//			});
			//inputThread.start();
			//inputThread.join();
			objectInput = new ObjectInputStream(
					new ByteArrayInputStream(recvMsg));	
			
			Object object = objectInput.readObject();
			if (object instanceof List) {
				List<AddressVo> addressList = (List<AddressVo>) object;
				return addressList;
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		finally {
			if (objectInput != null) {
				objectInput.close();
			}
		}
		return null;
	}
	
	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
				ObjectOutput objectOutput = new ObjectOutputStream(byteOutputStream)) {

			SocketCommandVo vo = new SocketCommandVo();
			vo.setCmdType(CmdType.INSERT);
			vo.setAddressVo(addressVo);

			objectOutput.writeObject(vo);
			objectOutput.flush();

			byte[] buf = byteOutputStream.toByteArray();

			packet = new DatagramPacket(buf, buf.length, hostaddress, hostport);
			socket.send(packet);

			LOGGER.debug("Delete Data send" + new String(buf));
		}
			return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
				ObjectOutput objectOutput = new ObjectOutputStream(byteOutputStream)) {

			SocketCommandVo vo = new SocketCommandVo();
			vo.setCmdType(CmdType.UPDATE);
			vo.setAddressVo(addressVo);

			objectOutput.writeObject(vo);
			objectOutput.flush();

			byte[] buf = byteOutputStream.toByteArray();

			packet = new DatagramPacket(buf, buf.length, hostaddress, hostport);
			socket.send(packet);

			LOGGER.debug("Delete Data send" + new String(buf));
		}
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
				ObjectOutput objectOutput = new ObjectOutputStream(byteOutputStream)) {

			SocketCommandVo vo = new SocketCommandVo();
			vo.setCmdType(CmdType.DELETE);
			vo.setAddressVo(addressVo);

			objectOutput.writeObject(vo);
			objectOutput.flush();

			byte[] buf = byteOutputStream.toByteArray();

			packet = new DatagramPacket(buf, buf.length, hostaddress, hostport);
			socket.send(packet);

			LOGGER.debug("Delete Data send" + new String(buf));
		}
		return 0;
	}
	
	private void close() {
		if (socket != null) {
			socket.close();
		}
	}
}
