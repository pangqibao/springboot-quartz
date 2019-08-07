package com.boo.quartz.controller;

import com.boo.quartz.constants.JobConst;
import com.boo.quartz.constants.RestCodeConst;
import com.boo.quartz.entity.JobDetail;
import com.boo.quartz.entity.TiggerDetail;
import com.boo.quartz.service.IQuartzService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName JobController
 * @Description job 控制器，启用动态操作任务添加修改删除等操作
 * @Author boo
 * @Date 2019/8/7 9:49
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/job")
public class JobController {
    private static Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private IQuartzService quartzService;

    /**
     * 得到所有任务列表
     *
     * @return
     * @throws SchedulerException
     */
    @RequestMapping(value = "/getQuartzJobs")
    public List<JobDetail> getQuartzJobs() throws SchedulerException {
        List<JobDetail> jobDetails = quartzService.getQuartzJobs(JobConst.JOB_GROUP);
        return jobDetails;
    }

    /**
     * 得到所有的任务执行时间信息
     *
     * @return
     * @throws SchedulerException
     */
    @RequestMapping(value = "/getQuartzTrigger")
    public List<TiggerDetail> getQuartzTrigger() throws SchedulerException {
        return quartzService.getQuartzTrigger(JobConst.TRIGGER_GROUP);
    }

    /**
     * 删除任务执行时间信息
     *
     * @param cronTrigger
     * @return
     * @throws SchedulerException
     */
    @RequestMapping(value = "/deleteTrigger")
    public String deleteTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        quartzService.deleteTrigger(cronTrigger);
        return RestCodeConst.SUCCESS;
    }

    /**
     * 删除任务信息
     *
     * @param jobDetail
     * @return
     * @throws SchedulerException
     */
    @RequestMapping(value = "/deleteJobs")
    public String deleteJob(JobDetailImpl jobDetail) throws SchedulerException {
        quartzService.deleteJob(jobDetail);
        return RestCodeConst.SUCCESS;
    }

    /**
     * 立即执行一次任务
     *
     * @param jobDetail
     * @return
     */
    @RequestMapping(value = "/rescheduleJob")
    public String rescheduleJob(JobDetailImpl jobDetail) {
        quartzService.execJob(jobDetail);
        return RestCodeConst.SUCCESS;
    }

    /**
     * 添加定时任务
     *
     * @param jobDetail
     * @return
     * @throws SchedulerException
     */
    @RequestMapping(value = "addQuartzJobs")
    public String addQuartzJobs(JobDetail jobDetail) throws SchedulerException {
        jobDetail.setGroup(JobConst.JOB_GROUP);
        jobDetail.setName(StringUtils.uncapitalize(jobDetail.getName()));
        quartzService.addJob(jobDetail);
        return RestCodeConst.SUCCESS;
    }

    /**
     * 添加任务检查是否有相应的任务类
     *
     * @param jobName
     * @return
     * @throws ClassNotFoundException
     */
    @RequestMapping(value = "/addQuartzJobCheck")
    public String addQuartzJobCheck(String jobName) throws ClassNotFoundException {
        String className = JobConst.BATCH_PACKAGE + StringUtils.uncapitalize(jobName);
        Class.forName(className);
        return RestCodeConst.SUCCESS;
    }

    /**
     * 添加定时任务执行时间
     *
     * @param cronTrigger
     * @return
     * @throws SchedulerException
     */
    @RequestMapping(value = "/addQuartzTrigger")
    public String addQuartzTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        cronTrigger.setGroup(JobConst.TRIGGER_GROUP);
        cronTrigger.setJobName(cronTrigger.getName());
        cronTrigger.setJobGroup(JobConst.JOB_GROUP);
        quartzService.addTrigger(cronTrigger);
        return RestCodeConst.SUCCESS;
    }

    /**
     * 更新定时任务执行时间
     *
     * @param cronTrigger
     * @return
     * @throws SchedulerException
     */
    @RequestMapping(value = "/updateQuartzTrigger")
    public String updateQuartzTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        cronTrigger.setGroup(JobConst.TRIGGER_GROUP);
        cronTrigger.setJobGroup(JobConst.JOB_GROUP);
        quartzService.updateTrigger(cronTrigger);
        return RestCodeConst.SUCCESS;
    }

    /**
     * 暂停定时任务
     *
     * @param cronTrigger
     * @return
     * @throws SchedulerException
     */
    @RequestMapping(value = "/stopTrigger")
    public String stopTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        quartzService.pauseTrigger(cronTrigger);
        return RestCodeConst.SUCCESS;
    }

    /**
     * 重启定时任务
     *
     * @param cronTrigger
     * @return
     * @throws SchedulerException
     */
    @RequestMapping(value = "/startTrigger")
    public String startTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        quartzService.resumeTrigger(cronTrigger);
        return RestCodeConst.SUCCESS;
    }
}
