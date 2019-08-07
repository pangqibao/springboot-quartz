package com.boo.quartz.job;

import com.boo.quartz.config.quartz.BaseJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName HelloJob
 * @Description 测试任务
 * @Author boo
 * @Date 2019/8/7 10:48
 * @Version 1.0.0
 */
@Component("helloJob")
public class HelloJob extends BaseJob {
    private static Logger logger = LoggerFactory.getLogger(HelloJob.class);

    @Override
    public void dowork() {
        logger.info("hello job 测试定时任务执行......");
    }
}
