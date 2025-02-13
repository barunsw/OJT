package com.barunsw.ojt.mjg.common;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.common.RmiAddressBookInterface;
import com.barunsw.ojt.vo.AddressVo;
import com.barunsw.ojt.constants.Gender;

public class MybatisAddressBookImpl extends UnicastRemoteObject implements RmiAddressBookInterface {
	
	public MybatisAddressBookImpl() throws RemoteException {
	}

	private static final Logger LOGGER = LogManager.getLogger(MybatisAddressBookImpl.class);

	private SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();

	@Override
	public List<AddressVo> selectAddressList(AddressVo paramVo) {
		List<AddressVo> resultList = new ArrayList<>();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			RmiAddressBookInterface mapper = session.getMapper(RmiAddressBookInterface.class);
			resultList = mapper.selectAddressList(paramVo);
		}
		catch (Exception e) {
		}
		return resultList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) {
		int insertResult = 0;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			RmiAddressBookInterface mapper = session.getMapper(RmiAddressBookInterface.class);
			insertResult = mapper.insertAddress(addressVo);
			session.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return insertResult;
	}

	@Override
	public int updateAddress(AddressVo addressVo) {
		int updateResult = 0;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			RmiAddressBookInterface mapper = session.getMapper(RmiAddressBookInterface.class);
			updateResult = mapper.updateAddress(addressVo);
			session.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return updateResult;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) {
		int deleteResult = 0;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			RmiAddressBookInterface mapper = session.getMapper(RmiAddressBookInterface.class);
			deleteResult = mapper.deleteAddress(addressVo);
			session.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return deleteResult;
	}
}
