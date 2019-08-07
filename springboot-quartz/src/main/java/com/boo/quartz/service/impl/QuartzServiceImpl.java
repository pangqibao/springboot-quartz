package com.boo.quartz.service.impl;

import com.boo.quartz.config.quartz.BaseJob;
import com.boo.quartz.config.quartz.JobAdapter;
import com.boo.quartz.constants.JobStateEnum;
import com.boo.quartz.entity.JobDetail;
import com.boo.quartz.entity.TiggerDetail;
import com.boo.quartz.service.IQuartzService;
import com.boo.quartz.util.ApplicationUtil;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ClassName QuartzServiceImpl
 * @Description 定时任务实现类
 * @Author boo
 * @Date 2019/8/7 10:02
 * @Version 1.0.0
 */
@Service
public class QuartzServiceImpl implements IQuartzService {
    private static final Logger logger = LoggerFactory.getLogger(QuartzServiceImpl.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void addJob(JobDetail quartzJob) throws SchedulerException {
        org.quartz.JobDetail jobDetail = JobBuilder.newJob().ofType(JobAdapter.class)
                .usingJobData("serviceName", quartzJob.getName())
                .withDescription(quartzJob.getDescription())
                .withIdentity(quartzJob.getName(), quartzJob.getGroup()).storeDurably().build();
        schedulerFactoryBean.getScheduler().addJob(jobDetail, true);
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
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.unscheduleJob(trigger.getKey());
        scheduler.scheduleJob(trigger);
    }

    @Override
    public void updateTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
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
        schedulerFactoryBean.getScheduler().pauseJob(JobKey.jobKey(jobDetail.getName(), jobDetail.getGroup()));
    }

    @Override
    public void deleteJob(JobDetailImpl jobDetail) throws SchedulerException {
        schedulerFactoryBean.getScheduler().deleteJob(JobKey.jobKey(jobDetail.getName(), jobDetail.getGroup()));
    }

    @Override
    public void deleteTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        schedulerFactoryBean.getScheduler().unscheduleJob(TriggerKey.triggerKey(cronTrigger.getName(), cronTrigger.getGroup()));
    }

    @Override
    public void pauseTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        schedulerFactoryBean.getScheduler().pauseTrigger(TriggerKey.triggerKey(cronTrigger.getName(), cronTrigger.getGroup()));
    }

    @Override
    public void resumeTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
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
    public List<JobDetail> getQuartzJobs(String jobGroup) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        GroupMatcher<JobKey> matcher = GroupMatcher.jobGroupContains(jobGroup);
        Set<JobKey> jobs = scheduler.getJobKeys(matcher);
        List<JobDetail> jobDetails = new ArrayList<>();
        int i = 1;
        for (JobKey key : jobs) {
            JobDetailImpl job = (JobDetailImpl) scheduler.getJobDetail(key);
            JobDetail jobDetail = new JobDetail();
            jobDetail.setId(i++);
            jobDetail.setName(job.getName());
            jobDetail.setGroup(job.getGroup());
            jobDetail.setDescription(job.getDescription());
            jobDetails.add(jobDetail);
        }
        return jobDetails;
    }

    @Override
    public List<TiggerDetail> getQuartzTrigger(String triggerGroup) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        GroupMatcher<TriggerKey> matcher = GroupMatcher.triggerGroupContains(triggerGroup);
        Set<TriggerKey> triggers = scheduler.getTriggerKeys(matcher);

        List<TiggerDetail> triggerList = new ArrayList<>();
        int i = 1;
        for (TriggerKey key : triggers) {
            Trigger trigger = scheduler.getTrigger(key);
            String state = scheduler.getTriggerState(key).toString();
            String jobDescription = scheduler.getJobDetail(trigger.getJobKey()).getDescription();

            TiggerDetail tiggerDetail = new TiggerDetail();
            tiggerDetail.setId(i++);
            tiggerDetail.setState(state);
            tiggerDetail.setCronExpression(((CronTriggerImpl) trigger).getCronExpression());
            tiggerDetail.setJobDescription(jobDescription);
            tiggerDetail.setNextFireTime(trigger.getNextFireTime());
            tiggerDetail.setPreviousFireTime(trigger.getPreviousFireTime());
            tiggerDetail.setStartTime(trigger.getStartTime());
            tiggerDetail.setStateMessage(JobStateEnum.getByState(state).getMessage());
            triggerList.add(tiggerDetail);
        }
        return triggerList;
    }

    @Override
    public void execJob(JobDetailImpl jobDetail) {
        logger.info("execJob start jobName={}", jobDetail.getName());
        String[] jobNames = jobDetail.getName().split("-");
        BaseJob job = ApplicationUtil.getBean(jobNames[0]);
        job.execute(jobDetail.getJobDataMap());
    }
}
