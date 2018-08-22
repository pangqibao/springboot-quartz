package com.boo.demo.service;


import com.boo.demo.entity.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.util.List;
import java.util.Map;

public interface IQuartzService {

    /**
     * 增加job
     * @param jobDetail
     */
    void addJob(JobDetail jobDetail) throws SchedulerException;

    /**
     * 增加Trigger
     * @param cronTrigger
     */
    void addTrigger(CronTriggerImpl cronTrigger) throws SchedulerException;

    /**
     * 修改Trigger
     * @param cronTrigger
     */
    void updateTrigger(CronTriggerImpl cronTrigger) throws SchedulerException;

    /**
     * 暂停job
     * @param jobDetail
     */
    void pauseJob(JobDetailImpl jobDetail) throws SchedulerException;

    /**
     * 删除job
     * @param jobDetail
     */
    void  deleteJob(JobDetailImpl jobDetail) throws SchedulerException;

    /**
     * 删除Trigger
     * @param cronTrigger
     */
    void deleteTrigger(CronTriggerImpl cronTrigger) throws SchedulerException;

    /**
     * 暂停trigger
     * @param cronTrigger
     */
    void pauseTrigger(CronTriggerImpl cronTrigger) throws SchedulerException;

    /**
     * 重启trigger
     * @param cronTrigger
     */
    void resumeTrigger(CronTriggerImpl cronTrigger) throws SchedulerException;

    /**
     * 获取job列表
     * @param jobGroup
     * @return
     */
    List<JobDetail> getQuartzJobs(String jobGroup) throws SchedulerException;

    /**
     * 获取trigger列表
     * @param triggerGroup
     * @return
     */
    List<Map<String,Object>> getQuartzTrigger(String triggerGroup) throws SchedulerException;

    /**
     * 立即触发
     * @param jobDetail
     */
    void execJob(JobDetailImpl jobDetail);
}
