package com.barunsw.ojt.yjkim.day10;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class FileAddressBookImpl implements AddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(FileAddressBookImpl.class);
	private File outputFile = new File("data/yjkim04-2/barun.txt/addressbook.dat");
	List<AddressVo> list = new ArrayList<AddressVo>();
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		//auto_close 하면 이어 쓰기 에러
	/*	try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(outputFile))){
			AddressVo addressvo = new AddressVo();
			Object objectInput = null;
			
			while ((objectInput = ois.readObject()) != null) {
				if (objectInput instanceof AddressVo) {
					list.add(addressvo);
					LOGGER.debug(objectInput);
				}
			}
*/
		
		objectInputStream = null;
		try {
			objectInputStream 	= new ObjectInputStream(new FileInputStream(outputFile));
			Object objectInput 	= null;

			while ( (objectInput = objectInputStream.readObject()) != null ) {
				if ( objectInput instanceof AddressVo ) {
					AddressVo addressvo = (AddressVo) objectInput;
					list.add(addressvo);
					LOGGER.debug(addressvo);
				}
			}
		}
		catch ( Exception e )  {
			
		}
		finally {
			try {
				if ( objectInputStream != null ) {
					objectInputStream.close();
				}
			}
			catch ( IOException ioe ) {
				LOGGER.error(ioe.getMessage(), ioe);
			}
		}	
		return list;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		list.add(addressVo);
		output();
		return 0;
	}

	@Override
	public int updateAddress(int index, AddressVo addressVo) throws Exception {
		list.set(index, addressVo);
		output();
		return 0;
	}

	@Override
	public int deleteAddress(int index) throws Exception {
		list.remove(index);
		output();
		
		return 0;
		
	}

	public void output() {
		objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(new FileOutputStream(outputFile));

			for ( AddressVo addressvo : list ) {
				objectOutputStream.writeObject(addressvo);
			}
		}
		catch ( Exception e ) {
			
		}
		finally {
			try {
				if ( objectOutputStream != null ) {
					objectOutputStream.close();
				}
			}
			catch ( IOException ioe ) {
				LOGGER.error(ioe.getMessage(), ioe);
			}
		}
		
	}

	@Override
	public List<AddressVo> selectParticularAddress(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
