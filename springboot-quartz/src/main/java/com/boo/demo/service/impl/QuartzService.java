package com.boo.demo.service.impl;

import com.boo.demo.config.quartz.BaseJob;
import com.boo.demo.config.quartz.JobAdapter;
import com.boo.demo.service.IQuartzService;
import com.boo.demo.utils.SpringUtils;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.*;

@Service
public class QuartzService implements IQuartzService {
    private static final Logger logger = LoggerFactory.getLogger(QuartzService.class);
    @Autowired
    private Scheduler scheduler;

    @Override
    public void addJob(com.boo.demo.entity.JobDetail quartzJob) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob().ofType(JobAdapter.class)
                .usingJobData("serviceName", quartzJob.getName())
                .withDescription(quartzJob.getDescription())
                .withIdentity(quartzJob.getName(), quartzJob.getGroup()).storeDurably().build();
        scheduler.addJob(jobDetail, true);
    }

    @Override
    public void addTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(cronTrigger.getName() + "Trigger", cronTrigger.getGroup())
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(cronTrigger.getCronExpression()))
                .withDescription(cronTrigger.getDescription()).forJob(cronTrigger.getJobName(), cronTrigger.getJobGroup())
                .build();
        scheduler.unscheduleJob(trigger.getKey());
        scheduler.scheduleJob(trigger);
    }

    @Override
    public void updateTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        Trigger oldTrigger = scheduler.getTrigger(TriggerKey.triggerKey(cronTrigger.getName(), cronTrigger.getGroup()));
        CronTrigger updateTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity(cronTrigger.getName(), cronTrigger.getGroup())
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(cronTrigger.getCronExpression()))
                .withDescription(cronTrigger.getDescription()).forJob(oldTrigger.getJobKey().getName(), cronTrigger.getJobGroup())
                .build();
        scheduler.rescheduleJob(oldTrigger.getKey(), updateTrigger);
    }

    @Override
    public void pauseJob(JobDetailImpl jobDetail) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(jobDetail.getName(), jobDetail.getGroup()));
    }

    @Override
    public void deleteJob(JobDetailImpl jobDetail) throws SchedulerException {
        scheduler.deleteJob(JobKey.jobKey(jobDetail.getName(), jobDetail.getGroup()));
    }

    @Override
    public void deleteTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        scheduler.unscheduleJob(TriggerKey.triggerKey(cronTrigger.getName(), cronTrigger.getGroup()));
    }

    @Override
    public void pauseTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        scheduler.pauseTrigger(TriggerKey.triggerKey(cronTrigger.getName(), cronTrigger.getGroup()));
    }

    @Override
    public void resumeTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        CronTriggerImpl oldTrigger = (CronTriggerImpl) scheduler.getTrigger(TriggerKey.triggerKey(cronTrigger.getName(), cronTrigger.getGroup()));
        CronTrigger updateTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity(oldTrigger.getName(), oldTrigger.getGroup())
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(oldTrigger.getCronExpression()))
                .withDescription(oldTrigger.getDescription()).forJob(oldTrigger.getJobKey().getName(), oldTrigger.getJobGroup())
                .build();
        scheduler.rescheduleJob(oldTrigger.getKey(), updateTrigger);
    }

    @Override
    public List<com.boo.demo.entity.JobDetail> getQuartzJobs(String jobGroup) throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.jobGroupContains(jobGroup);
        Set<JobKey> jobs = scheduler.getJobKeys(matcher);
        List<com.boo.demo.entity.JobDetail> jobDetails = new ArrayList<>();
        int i=1;
        for (JobKey key : jobs) {
            JobDetailImpl job = (JobDetailImpl) scheduler.getJobDetail(key);
            com.boo.demo.entity.JobDetail jobDetail=new com.boo.demo.entity.JobDetail();
            jobDetail.setId(i++);
            jobDetail.setName(job.getName());
            jobDetail.setGroup(job.getGroup());
            jobDetail.setDescription(job.getDescription());
            jobDetails.add(jobDetail);
        }
        return jobDetails;
    }

    @Override
    public List<Map<String, Object>> getQuartzTrigger(String triggerGroup) throws SchedulerException {
        GroupMatcher<TriggerKey> matcher = GroupMatcher.triggerGroupContains(triggerGroup);
        Set<TriggerKey> triggers = scheduler.getTriggerKeys(matcher);
        List<Map<String, Object>> triggerList = new ArrayList<>();

        for (TriggerKey key : triggers) {
            Trigger trigger = scheduler.getTrigger(key);
            String state = scheduler.getTriggerState(key).toString();
            String jobDescription = scheduler.getJobDetail(trigger.getJobKey()).getDescription();

            Map<String, Object> map = new HashMap<>();
            map.put("state", state);
            map.put("JobDescription",jobDescription);
            copyPropertys(trigger, map);
            triggerList.add(map);
        }
        return triggerList;
    }

    private void copyPropertys(Trigger src, Map<String, Object> target) {
        if(null != src){
            if (target == null) {
                target = new HashMap<>();
            }

            BeanWrapper beanWrapper = new BeanWrapperImpl(src);
            PropertyDescriptor[] descriptor = beanWrapper.getPropertyDescriptors();
            for (int i = 0; i < descriptor.length; i++) {
                String key = descriptor[i].getName();
                if(!key.equals("class")){
                    target.put(key, beanWrapper.getPropertyValue(key));
                }
            }
        }
    }

    @Override
    @Async
    public void execJob(JobDetailImpl jobDetail) {
        logger.info("execJob start jobName={}",jobDetail.getName());
        BaseJob job2 = SpringUtils.getBean(jobDetail.getName());
        job2.execute();
    }
}
