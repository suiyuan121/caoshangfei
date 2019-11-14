package com.zj.caoshangfei.task;

import com.zj.caoshangfei.service.CaoliuService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by jin.zhang@fuwo.com on 2017/10/25.
 */
@Component
public class ScheduledTask {

    private final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    private CaoliuService caoliuService;

    /**
     * 更新视频
     */
    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void updateVideo() {
        logger.info("更新视频 定时任务开始*******");

        for (int i = 1; i <= 5; i++) {
            caoliuService.getList(i);
        }

    }


}
