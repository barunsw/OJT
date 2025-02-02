package day6;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FileAddressBookImpl implements AddressBookInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileAddressBookImpl.class);
    private final SqlSessionFactory sqlSessionFactory;

    public FileAddressBookImpl() throws Exception {
        this.sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
    }

    @Override
    public List<AddressVO> selectAddressList(AddressVO addressVO) throws Exception {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList("AddressMapper.selectAddressList", addressVO);
        } catch (Exception e) {
            LOGGER.error("주소 목록을 조회하는 중 오류 발생", e);
            throw e;
        }
    }

    @Override
    public void insertAddress(AddressVO addressVO) throws Exception {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int newSeq = session.selectOne("AddressMapper.getNextSequence");
            addressVO.setSeq(newSeq);
            session.insert("AddressMapper.insertAddress", addressVO);
            session.commit();
        } catch (Exception e) {
            LOGGER.error("주소를 추가하는 중 오류 발생", e);
            throw e;
        }
    }

    @Override
    public void updateAddress(AddressVO addressVO) throws Exception {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("AddressMapper.updateAddress", addressVO);
            session.commit();
        } catch (Exception e) {
            LOGGER.error("주소를 수정하는 중 오류 발생", e);
            throw e;
        }
    }

    @Override
    public void deleteAddress(AddressVO addressVO) throws Exception {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.delete("AddressMapper.deleteAddress", addressVO.getSeq());
            session.commit();
        } catch (Exception e) {
            LOGGER.error("주소를 삭제하는 중 오류 발생", e);
            throw e;
        }
    }
}
