package com.boo.quartz.config.quartz;

import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @ClassName BaseJob
 * @Description 基础任务实现，后续所有新增的任务都要继承这个类
 * @Author boo
 * @Date 2019/8/7 9:46
 * @Version 1.0.0
 */
public abstract class BaseJob {
    private static final Logger logger = LoggerFactory.getLogger(BaseJob.class);

    private boolean running = false;

    private final byte[] lock = new byte[0];

    public void execute(JobDataMap dataMap) {
        // 单机用这个,多机用redis分布式
        synchronized (lock) {
            if (running) {
                logger.warn("{} the last task is still executing", getDesc());
                return;
            } else {
                running = true;
            }
        }
        LocalDateTime start = LocalDateTime.now();
        try {
            logger.info("[{}] start, time={}", getDesc(), start);
            dowork(dataMap);
            logger.info("[{}] end, 耗时={} 秒", getDesc(), LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"))-start.toEpochSecond(ZoneOffset.of("+8")));
        } catch (Exception e) {
            logger.error("[{}] error during dowork exception={} ", getDesc(), e);
        }
        running = false;
    }

    public abstract void dowork(JobDataMap dataMap);

    public String getDesc() {
        return this.getClass().getSimpleName();
    }
}
