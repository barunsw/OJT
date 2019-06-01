package com.barunsw.ojt.gtkim.day10;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBAddressBookImpl implements AddressBookInterface {
	private static final Logger LOGGER = LogManager.getLogger(DBAddressBookImpl.class);

	private SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
	private AddressDao mapper;
	
	public DBAddressBookImpl() {
		try (SqlSession session = sqlSessionFactory.openSession()){
			mapper = session.getMapper(AddressDao.class);
			mapper.createAddressTable();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	@Override
	public List<AddressVo> selectAddressList() throws Exception {
		List<AddressVo> addressList = new ArrayList<>();
		try (SqlSession session = sqlSessionFactory.openSession()){
			mapper = session.getMapper(AddressDao.class);
			addressList = mapper.selectAddressList();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		return addressList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		int result = 0;
		try (SqlSession session = sqlSessionFactory.openSession()){
			mapper = session.getMapper(AddressDao.class);
			result = mapper.insertAddress(addressVo);
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		return result;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		int result = 0;
		try (SqlSession session = sqlSessionFactory.openSession()){
			mapper = session.getMapper(AddressDao.class);
			result = mapper.updateAddress(addressVo);
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		return result;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		int result = 0;
		try (SqlSession session = sqlSessionFactory.openSession()){
			mapper = session.getMapper(AddressDao.class);
			result = mapper.deleteAddress(addressVo);
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		return result;
	}

}
