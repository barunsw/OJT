package com.barunsw.ojt.gtkim.day10;

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
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileAddressBookImpl implements AddressBookInterface{
	private static final Logger LOGGER = LogManager.getLogger(FileAddressBookImpl.class);
	private final Path DEFAULT_DIR = Paths.get("data/gtkim/day10/");
	private final String FILE_NAME = "Address.dat";
	
	private Path absolutePathDirectroy  = DEFAULT_DIR.toAbsolutePath();
	private Path filePath 				= Paths.get(absolutePathDirectroy.toString(),
													File.separator, FILE_NAME);
	private File dataFile 				= filePath.toFile();
	private List<AddressVo> addressList = new ArrayList<>();
	public FileAddressBookImpl() {
		try {
			initFile();
		}
		catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initFile() {
		try {
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
			while ((object = ois.readObject()) != null) {	
				if (object instanceof AddressVo) {
					LOGGER.debug("Object is : " + object);
					AddressVo oneAddress = (AddressVo) object;
					LOGGER.debug("Address is : " + oneAddress);
					addressList.add(oneAddress);
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

		return ret;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		int seq = addressVo.getSeq();
		
		addressList.set(seq, addressVo);
		int ret = writeObject();
		
		return ret;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		int index = addressVo.getSeq();		
		addressList.remove(index-1);
		int ret = writeObject();
		
		return ret;
	}
	
	private int writeObject() {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile)) ){
			
			for(AddressVo v : addressList) {
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
	
	public AddressVo serchAddress(int seq) {
		AddressVo search = null;
		for(AddressVo v : addressList) {
			if (v.getSeq() == seq) {
				search = v;
			}
		}
		return search;
	}

}
