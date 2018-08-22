package com.boo.demo.config.quartz;


import com.boo.demo.utils.SpringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class JobAdapter implements Job {

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String serviceName = dataMap.getString("serviceName");
        BaseJob service = SpringUtils.getBean(serviceName);
        service.execute();
    }



}
