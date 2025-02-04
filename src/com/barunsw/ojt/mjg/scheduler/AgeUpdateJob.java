package com.barunsw.ojt.mjg.scheduler;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.mjg.common.AddressBookInterface;
import com.barunsw.ojt.mjg.day06.SqlSessionFactoryManager;

public class AgeUpdateJob implements org.quartz.Job {
    private static final Logger LOGGER = LogManager.getLogger(AgeUpdateJob.class);

    @Override
    public void execute(org.quartz.JobExecutionContext context) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryManager.getSqlSessionFactory();
        
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AddressBookInterface mapper = session.getMapper(AddressBookInterface.class);

            // 나이 +1 업데이트
            int updateResult = mapper.updateAllAges();
            LOGGER.info("나이 업데이트 완료: {} 개의 레코드가 업데이트됨", updateResult);

            session.commit(); // 변경 사항 반영
        } catch (Exception e) {
            LOGGER.error("나이 업데이트 중 예외 발생: {}", e.getMessage(), e);
        }
    }
}
