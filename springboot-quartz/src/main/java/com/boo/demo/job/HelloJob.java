package com.boo.demo.job;

import com.boo.demo.config.quartz.BaseJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("helloJob")
public class HelloJob extends BaseJob {
  
    private static Logger logger = LoggerFactory.getLogger(HelloJob.class);

    @Override
    public void dowork() {
        logger.error("Hello Job执行时间: " + LocalDateTime.now());
    }
}
