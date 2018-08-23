package com.boo.demo.controller;

import com.boo.demo.constants.JobConstant;
import com.boo.demo.entity.JobDetail;
import com.boo.demo.entity.TiggerDetail;
import com.boo.demo.service.IQuartzService;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/job")
public class JobController {
    private static Logger logger = LoggerFactory.getLogger(JobController.class);

    private static final String SUCCESS="000000";

    @Autowired
    private IQuartzService quartzService;

    @RequestMapping(value = "/getQuartzJobs")
    public List<JobDetail> getQuartzJobs() throws SchedulerException {
        List<JobDetail> jobDetails= quartzService.getQuartzJobs(JobConstant.JOB_GROUP);
        return jobDetails;
    }

    @RequestMapping(value = "/getQuartzTrigger")
    public List<TiggerDetail> getQuartzTrigger() throws SchedulerException {
        return quartzService.getQuartzTrigger(JobConstant.TRIGGER_GROUP);
    }

    @RequestMapping(value = "/deleteTrigger")
    public String deleteTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        quartzService.deleteTrigger(cronTrigger);
        return SUCCESS;
    }

    @RequestMapping(value = "/deleteJobs")
    public String deleteJob(JobDetailImpl jobDetail) throws SchedulerException {
        quartzService.deleteJob(jobDetail);
        return SUCCESS;
    }

    @RequestMapping(value = "/rescheduleJob")
    public String rescheduleJob(JobDetailImpl jobDetail){
        quartzService.execJob(jobDetail);
        return SUCCESS;
    }

    @RequestMapping(value = "addQuartzJobs")
    public String addQuartzJobs(JobDetail jobDetail) throws SchedulerException {
        jobDetail.setGroup(JobConstant.JOB_GROUP);
        jobDetail.setName(StringUtils.uncapitalize(jobDetail.getName()));
        quartzService.addJob(jobDetail);
        return SUCCESS;
    }

    @RequestMapping(value = "/addQuartzJobCheck")
    public String addQuartzJobCheck(String jobName) throws ClassNotFoundException {
        String className=JobConstant.BATCH_PACKAGE+StringUtils.uncapitalize(jobName);
        Class.forName(className);
        return SUCCESS;
    }

    @RequestMapping(value = "/addQuartzTrigger")
    public String addQuartzTrigger(CronTriggerImpl cronTrigger) throws ParseException, SchedulerException {
        cronTrigger.setGroup(JobConstant.TRIGGER_GROUP);
        cronTrigger.setJobName(cronTrigger.getName());
        cronTrigger.setJobGroup(JobConstant.JOB_GROUP);
        quartzService.addTrigger(cronTrigger);
        return SUCCESS;
    }

    @RequestMapping(value = "/updateQuartzTrigger")
    public String updateQuartzTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        cronTrigger.setGroup(JobConstant.TRIGGER_GROUP);
        cronTrigger.setJobGroup(JobConstant.JOB_GROUP);
        quartzService.updateTrigger(cronTrigger);
        return SUCCESS;
    }

    @RequestMapping(value = "/stopTrigger")
    public String stopTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        quartzService.pauseTrigger(cronTrigger);
        return SUCCESS;
    }

    @RequestMapping(value = "/startTrigger")
    public String startTrigger(CronTriggerImpl cronTrigger) throws SchedulerException {
        quartzService.resumeTrigger(cronTrigger);
        return SUCCESS;
    }

}
