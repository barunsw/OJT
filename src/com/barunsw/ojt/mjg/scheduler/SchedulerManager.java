package com.barunsw.ojt.mjg.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerManager {
    public static void startAgeUpdateScheduler() {
        try {
            // Job 정의
            JobDetail job = JobBuilder.newJob(AgeUpdateJob.class)
                .withIdentity("ageUpdateJob", "group1")
                .build();

            // 매년 12월 31일 23시 59분 59초에 실행
            Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("ageUpdateTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("59 59 23 31 12 ? *")
                		.inTimeZone(java.util.TimeZone.getTimeZone("Asia/Seoul")))
                // test용 1분마다 실행
    			// .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?")
                .build();

            // 스케줄러 시작
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);

            System.out.println("나이 자동 업데이트 스케줄러가 설정되었습니다.");
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}
