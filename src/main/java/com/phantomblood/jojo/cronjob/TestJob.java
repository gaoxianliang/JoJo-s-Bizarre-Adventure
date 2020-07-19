package com.phantomblood.jojo.cronjob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

/**
 * @Description // 发票上传巡检
 * @Date 2019/07/08 - 14:09
 * @Param
 * @return
 **/

@Configuration
@EnableScheduling
public class TestJob {

    private static final Logger logger = LoggerFactory.getLogger(com.phantomblood.jojo.cronjob.TestJob.class);

    @Scheduled(cron = "${Job.inspectionJob}")
    public void scheduler() throws Exception {
        String requestId = UUID.randomUUID().toString();
        logger.info("定时任务-->开始执行-->ID:[{}]", requestId);
    }
}
