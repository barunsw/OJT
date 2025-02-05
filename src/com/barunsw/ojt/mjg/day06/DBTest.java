package com.barunsw.ojt.mjg.day06;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.mjg.common.AddressBookInterface;
import com.barunsw.ojt.mjg.common.AddressBookVo;
import com.barunsw.ojt.mjg.constants.Gender;
import com.barunsw.ojt.mjg.scheduler.SchedulerManager;

public class DBTest {
    private static final Logger LOGGER = LogManager.getLogger(DBTest.class);

    public static void main(String[] args) {
        LOGGER.debug("MyBatis AddressBook Test");

        // SqlSessionFactory로 SqlSession 생성
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
        
        // session 만들기
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);

            // 1. 전체 데이터 조회
            LOGGER.debug("전체 데이터 조회");
            List<AddressBookVo> addressList = mapper.selectAddressList(null);
            addressList.forEach(address -> LOGGER.debug(address));

            // 2. 새로운 주소 정보 생성
            LOGGER.debug("새로운 주소 정보 생성");
            AddressBookVo insertAddress = new AddressBookVo();
            insertAddress.setName("신사임당");
            insertAddress.setGender(Gender.toGender("여"));
            insertAddress.setAge(45);
            insertAddress.setAddress("강원도 강릉");
            int insertResult = mapper.insertAddress(insertAddress);
            LOGGER.debug("생성 결과: {}", insertResult);

            // 생성 후 데이터 조회
            addressList = mapper.selectAddressList(null);
            addressList.forEach(address -> LOGGER.debug("생성 후 데이터: " + address));            

            // 3. 주소 정보 업데이트 (seq 기준)
            LOGGER.debug("주소 정보 업데이트");
            if (!addressList.isEmpty()) {
            	int targetSeq = 4; 
            	AddressBookVo updateAddress = new AddressBookVo();
            	updateAddress.setSeq(targetSeq);
            	updateAddress.setAddress("성남 분당구");
            	int updateResult = mapper.updateAddress(updateAddress);
            	LOGGER.debug("업데이트 결과: {}", updateResult);
            } else {
                LOGGER.debug("업데이트할 대상이 없습니다.");
            }

            // 업데이트 후 데이터 조회
            addressList = mapper.selectAddressList(null);
            addressList.forEach(address -> LOGGER.debug("업데이트 후 데이터: " + address));

            
            // 4. 주소 정보 삭제 (seq 기준)
            LOGGER.debug("주소 정보 삭제");
            if (!addressList.isEmpty()) {
                int deleteSeq = 5; 
                int deleteResult = mapper.deleteAddress(deleteSeq);
                LOGGER.debug("삭제 결과: {}", deleteResult);
            } else {
                LOGGER.debug("삭제할 대상이 없습니다.");
            }

            // 삭제 후 데이터 조회
            addressList = mapper.selectAddressList(null);
            addressList.forEach(address -> LOGGER.debug("삭제 후 데이터: " + address));
	
            SchedulerManager.startAgeUpdateScheduler();

            // 5. 성이 '문'인 사람 조회
            LOGGER.debug("성이 '문'인 사람 조회");
            addressList = mapper.selectByLastNameMoon(null);
            
            if (addressList.isEmpty()) {
                LOGGER.debug("문씨인 사람이 없습니다.");
            } 
            else {
                addressList.forEach(address -> LOGGER.debug("문 씨 데이터: " + address + "\n"));
            }
          
			// 6. 성이 입력받은 값으로 시작하는 사람 조회
			LOGGER.debug("성이 입력받은 값으로 시작하는 사람 조회 (바뀐 방식, 입력값 적용)");
			
			// AddressBookVo 객체 생성 및 name 속성을 '김'으로 설정
			AddressBookVo searchCriteria = new AddressBookVo();
			searchCriteria.setName("김");
			
			// searchCriteria 객체를 전달하면 XML 동적 SQL에서 name이 null이 아니므로 "김%" 조건으로 검색됨.
			addressList = mapper.selectByLastNameMoon(searchCriteria);
			
			if (addressList.isEmpty()) {
			     LOGGER.debug("[" + searchCriteria.getName() + "] 씨인 사람이 없습니다.");
			} 
			else {
		    // 입력받은 값("김")을 로그 메시지에 포함하여 출력
			addressList.forEach(address -> LOGGER.debug("[" + searchCriteria.getName() + "] 씨 데이터: " + address));
			}
            
            // 변경 사항 커밋
            session.commit();
        } 
        catch (Exception e) {
            LOGGER.error("예외 발생: {}", e.getMessage(), e);
        }
    }
}
