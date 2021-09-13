package com.barunsw.ojt.sjcha.day12;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.sjcha.day12.AddressBookInterface;
import com.barunsw.ojt.sjcha.day12.SqlSessionFactoryManager;
import com.barunsw.ojt.sjcha.day12.AddressVo;

public class MybatisAddressBookImpl extends UnicastRemoteObject implements AddressBookInterface {
	
	// 생성자가 반드시 필요!!
	protected MybatisAddressBookImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final Logger LOGGER = LogManager.getLogger(MybatisAddressBookImpl.class);

	SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();

	// 주소록 조회
	@Override
	public List<AddressVo> selectAddressList(AddressVo person) throws Exception {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			List<AddressVo> personList = mapper.selectAddressList(new AddressVo());
			return personList;
		}
	}

	// 주소록 데이터 추가 - 추가 버튼 클릭
	@Override
	public int insertAddress(AddressVo person) throws Exception {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);

			int insertResult = mapper.insertAddress(person);
			session.commit();
		}
		return 0;
	}

	// 주소록 데이터 업데이트 - 변경 버튼 클릭
	@Override
	public int updateAddress(AddressVo person) throws Exception {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);

			int updateResult = mapper.updateAddress(person);
			session.commit();
		}

		return 0;
	}

	// 주소록 데이터 삭제 - 오른쪽 마우스 버튼 클릭
	@Override
	public int deleteAddress(AddressVo person) throws Exception {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);

			int deleteResult = mapper.deleteAddress(person);

			session.commit();
		}

		return 0;
	}
}
