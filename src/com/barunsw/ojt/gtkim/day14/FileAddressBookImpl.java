package com.barunsw.ojt.gtkim.day14;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.common.RmiAddressBookInterface;
import com.barunsw.ojt.vo.AddressVo;

public class FileAddressBookImpl extends UnicastRemoteObject implements RmiAddressBookInterface{
	private static final Logger LOGGER = LogManager.getLogger(FileAddressBookImpl.class);
	private final static String FILE_NAME = "AddressBook.dat";
	private final Path DEFAULT_DIR = Paths.get("data/gtkim/day14/");

	private Path absolutePathDirectroy = DEFAULT_DIR.toAbsolutePath();
	private Path filePath = Paths.get(absolutePathDirectroy.toString(), File.separator, FILE_NAME);
	private File dataFile = filePath.toFile();
	private List<AddressVo> addressList = new ArrayList<>();

	public FileAddressBookImpl() throws RemoteException {
		this(FILE_NAME);
	}

	public FileAddressBookImpl(String FileName) throws RemoteException {
		super();
		try {
			initFile(FileName);
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initFile(String FileName) {
		try {
			filePath = Paths.get(DEFAULT_DIR.toAbsolutePath().toString(), File.separator, FileName);
			dataFile = filePath.toFile();
			if (!Files.exists(absolutePathDirectroy)) {
				Files.createDirectories(absolutePathDirectroy);
				LOGGER.debug("디렉토리가 생성되었습니다. " + absolutePathDirectroy.toString());
			}

			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
				LOGGER.debug("파일이 생성되었습니다." + filePath.toString());
			}
		} 
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
	}

	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
			
			addressList.clear();

			Object object;
			LOGGER.debug("SELECT");
			
			while ((object = ois.readObject()) != null) {
				if (object instanceof AddressVo) {
					AddressVo oneAddress = (AddressVo) object;
					addressList.add(oneAddress);
				}
				else {
					LOGGER.debug("타입 에러!");
				}
			}
		} 
		catch (FileNotFoundException fnfe) {
			LOGGER.error(fnfe.getMessage(), fnfe);
		}
		catch (EOFException e) {
		} 
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
		catch (ClassNotFoundException cnfe) {
			LOGGER.error(cnfe.getMessage(), cnfe);
		}
		return addressList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		addressList.add(addressVo);
		int ret = writeObject();

		LOGGER.debug("INSERT");
		return ret;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		int seq = addressVo.getSeq();
		int index = -1;

		Iterator iter = addressList.iterator();
		while (iter.hasNext()) {
			AddressVo deleteVo = (AddressVo) iter.next();
			index++;
			if (deleteVo.getSeq() == seq) {
				break;
			}
		}
		addressList.set(index, addressVo);
		int ret = writeObject();

		
		LOGGER.debug("UPDATE");
		return ret;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		int index = addressVo.getSeq();

		Iterator iter = addressList.iterator();
		while (iter.hasNext()) {
			AddressVo deleteVo = (AddressVo) iter.next();
			if (deleteVo.getSeq() == index) {
				iter.remove();
			}
		}

		int ret = writeObject();

		LOGGER.debug("DELETE");
		return ret;
	}

	private int writeObject() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {

			for (AddressVo v : addressList) {
				oos.writeObject(v);
			}
			LOGGER.debug("파일에 입력되었습니다.");
		}
		catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
			return -1;
		}
		return 0;
	}

	@Override
	public List<AddressVo> selectParticularAddress(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
