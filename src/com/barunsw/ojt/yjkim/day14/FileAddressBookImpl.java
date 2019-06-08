package com.barunsw.ojt.yjkim.day14;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.AddressVo;
import com.barunsw.ojt.constants.RmiAddressBookInterface;

public class FileAddressBookImpl extends UnicastRemoteObject implements RmiAddressBookInterface{
	private static final Logger LOGGER = LogManager.getLogger(FileAddressBookImpl.class);
	private final static String FILENAME = "RMIAddressBook.dat";
	private File file;
	private List<AddressVo> list= new ArrayList<>();
	private Path filePath;
	private final String[] hangleList	   = {"ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", 
			"ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ",  
			"ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ" };
	
	protected FileAddressBookImpl() throws RemoteException {
		this(FILENAME);
	}
	
	protected FileAddressBookImpl(String fileName) throws RemoteException {
		super();
		try {
			initFileEnvironment(fileName);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

	}
	
	private void initFileEnvironment(String fileName) throws Exception {
		filePath = Paths.get("data/yjkim04-2/barun.txt/",File.separator,fileName);
		file = filePath.toFile();
		if(!Files.exists(filePath)) {
			Files.createFile(filePath);
		}
	}
	
	
	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		LOGGER.debug("+++ selectAddressList");
		try (ObjectInputStream objectInputStream
				= new ObjectInputStream(new FileInputStream(file))) {
			Object o = objectInputStream.readObject();
			if (o instanceof List) {
				list = (List) o;
			}
			
		} catch (EOFException eofe) {
			return list;
		}
		LOGGER.debug("--- selectAddressList");
		return list;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		addressVo.setSeq(list.size()+1);
		list.add(addressVo);
		output();
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		list.set(addressVo.getSeq()-1, addressVo);
		output();
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		list.remove(addressVo.getSeq()-1);
		output();
		return 0;
	}

	@Override
	public List<AddressVo> selectParticularAddress(Map<String, Object> map) throws Exception {
		List<AddressVo> Particularlist = new ArrayList<>();

		try (ObjectInputStream objectInputStream
				= new ObjectInputStream(new FileInputStream(file))) {
			Object o = objectInputStream.readObject();
			if (o instanceof List) {
				list = (List) o;
			}
		} catch (EOFException eofe) {
			return list;
		}
		
		for (AddressVo addressVo : list) {
			LOGGER.debug(String.format("param [%s], getChosung [%s]",map.get("param"),getChosung(addressVo.getName())));

			if (map.get("param").equals("주소록")) {
				return list;
			}
			
			if (getChosung(addressVo.getName()).equals(map.get("param"))) {
				Particularlist.add(addressVo);
			}
		}
		
		return Particularlist;
	}
	
	private void output() throws Exception {
		try (ObjectOutputStream objectOutputStream 
				= new ObjectOutputStream(new FileOutputStream(file))) {
			objectOutputStream.writeObject(list);
			objectOutputStream.flush();
		}
	}
	
	private String getChosung(String name) {
		char ch = name.charAt(0);
		String str = new String();
		str = hangleList[(ch-44032)/(21*28)];
		return str;
	}
}
