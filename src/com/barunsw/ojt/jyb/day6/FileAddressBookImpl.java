package com.barunsw.ojt.jyb.day6;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.common.AddressBookInterface;
import com.barunsw.ojt.vo.AddressVo;

import java.util.List;

public class FileAddressBookImpl implements AddressBookInterface {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileAddressBookImpl.class);
	private final SqlSessionFactory sqlSessionFactory;

	public FileAddressBookImpl() throws Exception {
		this.sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
	}

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVO) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.selectList("AddressMapper.selectAddressList", addressVO);
		}
		catch (Exception e) {
			LOGGER.error("주소 목록을 조회하는 중 오류 발생", e);
			throw e;
		}
	}

	@Override
	public int insertAddress(AddressVo addressVO) throws Exception {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			int newSeq = session.selectOne("AddressMapper.getNextSequence");
			addressVO.setSeq(newSeq);
			int rowsInserted = session.insert("AddressMapper.insertAddress", addressVO);
			session.commit();
			return rowsInserted;
		}
		catch (Exception e) {
			LOGGER.error("주소를 추가하는 중 오류 발생", e);
			throw e;
		}
	}

	@Override
	public int updateAddress(AddressVo addressVO) throws Exception {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			int rowsUpdated = session.update("AddressMapper.updateAddress", addressVO);
			session.commit();
			return rowsUpdated;
		}
		catch (Exception e) {
			LOGGER.error("주소를 수정하는 중 오류 발생", e);
			throw e;
		}
	}

	@Override
	public int deleteAddress(AddressVo addressVO) throws Exception {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			int rowsDeleted = session.delete("AddressMapper.deleteAddress", addressVO.getSeq());
			session.commit();
			return rowsDeleted;
		}
		catch (Exception e) {
			LOGGER.error("주소를 삭제하는 중 오류 발생", e);
			throw e;
		}
	}
}
