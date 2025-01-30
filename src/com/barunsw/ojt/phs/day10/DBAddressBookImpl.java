package com.barunsw.ojt.phs.day10;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBAddressBookImpl implements AddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(DBAddressBookImpl.class);
	private SqlSessionFactory fac = SqlSessionFactoryManager.getSqlSessionFactory();
	
	public DBAddressBookImpl() {}
	
	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		
		List<AddressVo> addressList = new ArrayList<>();
		
		try (SqlSession session = fac.openSession(true)) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			addressList = mapper.selectAddressList();
		}
		catch (Exception e) {
			LOGGER.debug(e);
		}
		return addressList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		int result = 0;
		try ( SqlSession session = fac.openSession(true) ) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			result = mapper.insertAddress(addressVo);
		}
		catch ( Exception e ) {
			LOGGER.debug( e.getMessage() );
		}
		
		return result;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		int result = 0;
		try ( SqlSession session = fac.openSession(true) ) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			result = mapper.updateAddress(addressVo);
		}
		catch ( Exception e ) {
			LOGGER.debug(e);
		}
		
		return result;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		
		try (SqlSession session = fac.openSession(true)) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			mapper.deleteAddress(addressVo);
		}
		catch (Exception e) {
			LOGGER.debug(e);
		}
		
		return 0;
	}

	@Override
	public int createStorage() throws Exception {
		int result = 0;
		try (SqlSession session = fac.openSession(true)) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			result = mapper.createStorage();
		}
		catch (Exception e) {
			LOGGER.debug(e);
		}
		
		return result;
	}

}
