package com.boo.quartz.config.quartz;

import com.boo.quartz.util.ApplicationUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @ClassName JobAdapter
 * @Description TODO
 * @Author boo
 * @Date 2019/8/7 9:44
 * @Version 1.0.0
 */
public class JobAdapter implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String serviceName = dataMap.getString("serviceName");
        String[] serviceNames = serviceName.split("-");
        BaseJob service = ApplicationUtil.getBean(serviceNames[0]);
        service.execute(dataMap);
    }
}
