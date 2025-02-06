package com.barunsw.ojt.sjcha.day08;

import java.io.File;
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
import com.barunsw.ojt.sjcha.day08.AddressBookInterface;
import com.barunsw.ojt.sjcha.day08.SqlSessionFactoryManager;
import com.barunsw.ojt.sjcha.day08.AddressVo;

public class MybatisAddressBookImplement implements AddressBookInterface {

	private List<AddressVo> personList = new ArrayList<>();

	private static final Logger LOGGER = LogManager.getLogger(FileAddressBookImplement.class);

	SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();

	// 주소록 조회
	@Override
	public List<AddressVo> selectAddressList(AddressVo person) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			List<AddressVo> personList = mapper.selectAddressList(new AddressVo());
		}
		return personList;
	}

	// 주소록 데이터 추가 - 추가 버튼 클릭
	@Override
	public int insertAddress(AddressVo person) throws Exception {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);
			 personList = mapper.selectAddressList(new AddressVo());

			AddressVo insertPerson = new AddressVo();

			insertPerson.setName(person.getName());
			insertPerson.setGender(Gender.toGender("WOMAN"));
			insertPerson.setAge(person.getAge());
			insertPerson.setPhone(person.getPhone());
			insertPerson.setAddress(person.getAddress());

			int insertResult = mapper.insertAddress(insertPerson);
			session.commit();
		}
		return 0;
	}

	// 주소록 데이터 업데이트 - 변경 버튼 클릭
	@Override
	public int updateAddress(AddressVo person) throws Exception {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);

			AddressVo updatePerson = new AddressVo();

			updatePerson.setName(person.getName());
			updatePerson.setGender(Gender.toGender("WOMAN"));
			updatePerson.setAge(person.getAge());
			updatePerson.setPhone(person.getPhone());
			updatePerson.setAddress(person.getAddress());

			int updateResult = mapper.updateAddress(updatePerson);
			session.commit();
		}

		return 0;
	}

	// 주소록 데이터 삭제 - 오른쪽 마우스 버튼 클릭
	@Override
	public int deleteAddress(AddressVo person) throws Exception {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);

			int addressBookCount = personList.size();
			int deleteIndex = -1;
			for (int i = 0; i < addressBookCount; i++) {
				AddressVo oneAddress = personList.get(i);
				if (oneAddress != null && oneAddress.getName().equals(person.getName())) {
					deleteIndex = i;
					LOGGER.debug(deleteIndex);
					break;
				}
			}
			
			if (deleteIndex >= 0) {
				personList.remove(deleteIndex);
				int deleteResult = mapper.deleteAddress(person);
			}

			session.commit();
		}

		return 0;
	}
}
