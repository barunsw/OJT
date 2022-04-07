package com.barunsw.ojt.day09;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.vo.AddressVo;

public class JdbcAddressBookImpl implements AddressBookInterface {
	private static final Logger LOGGER = LoggerFactory.getLogger(JdbcAddressBookImpl.class);
	
	private SqlSessionFactory sqlFactory = SqlSessionFactoryManager.getSqlSessionFactory();

	public JdbcAddressBookImpl() {
		LOGGER.debug("JdbcAddressBookImpl 생성");
	}
	
	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) {
		List<AddressVo> addressList = new ArrayList();
		
		try (SqlSession session = sqlFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			addressList = mapper.selectAddressList(addressVo);
		} 
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		return addressList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		int result = 0;
		
		try (SqlSession session = sqlFactory.openSession(true)) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			result = mapper.insertAddress(addressVo);
		} 
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		return result;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		int result = 0;
		
		try (SqlSession session = sqlFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			result = mapper.updateAddress(addressVo);
		} 
		catch (Exception e) {
			LOGGER.error(e.getMessage() + e);
		}
		
		return result;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		try (SqlSession session = sqlFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			LOGGER.debug(addressVo + "");
			mapper.deleteAddress(addressVo);
		} 
		catch (Exception e) {
			LOGGER.error(e.getMessage() + e);
		}

		return 0;
	}
}