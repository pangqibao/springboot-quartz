package com.boo.quartz.service;

import com.boo.quartz.entity.JobDetail;
import com.boo.quartz.entity.TiggerDetail;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.util.List;

/**
 * @ClassName IQuartzService
 * @Description 定时任务接口，任务的添加修改动态接口
 * @Author boo
 * @Date 2019/8/7 9:53
 * @Version 1.0.0
 */
public interface IQuartzService {
    /**
     * 增加job
     * @param jobDetail
     * @throws SchedulerException
     */
    void addJob(JobDetail jobDetail) throws SchedulerException;

    /**
     * 增加Trigger
     * @param cronTrigger
     * @throws SchedulerException
     */
    void addTrigger(CronTriggerImpl cronTrigger) throws SchedulerException;

    /**
     * 修改Trigger
     * @param cronTrigger
     * @throws SchedulerException
     */
    void updateTrigger(CronTriggerImpl cronTrigger) throws SchedulerException;

    /**
     * 暂停job
     * @param jobDetail
     * @throws SchedulerException
     */
    void pauseJob(JobDetailImpl jobDetail) throws SchedulerException;

    /**
     * 删除job
     * @param jobDetail
     * @throws SchedulerException
     */
    void  deleteJob(JobDetailImpl jobDetail) throws SchedulerException;

    /**
     * 删除Trigger
     * @param cronTrigger
     * @throws SchedulerException
     */
    void deleteTrigger(CronTriggerImpl cronTrigger) throws SchedulerException;

    /**
     * 暂停trigger
     * @param cronTrigger
     * @throws SchedulerException
     */
    void pauseTrigger(CronTriggerImpl cronTrigger) throws SchedulerException;

    /**
     * 重启trigger
     * @param cronTrigger
     * @throws SchedulerException
     */
    void resumeTrigger(CronTriggerImpl cronTrigger) throws SchedulerException;

    /**
     * 获取job列表
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    List<JobDetail> getQuartzJobs(String jobGroup) throws SchedulerException;

    /**
     * 获取trigger列表
     * @param triggerGroup
     * @return
     * @throws SchedulerException
     */
    List<TiggerDetail> getQuartzTrigger(String triggerGroup) throws SchedulerException;

    /**
     * 立即触发
     * @param jobDetail
     */
    void execJob(JobDetailImpl jobDetail);
}
