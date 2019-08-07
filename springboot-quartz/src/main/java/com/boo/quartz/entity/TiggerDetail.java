package com.boo.quartz.entity;

import com.boo.quartz.constants.DateFormatConstant;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * @ClassName TiggerDetail
 * @Description job 定时时间信息实体
 * @Author boo
 * @Date 2019/8/7 9:59
 * @Version 1.0.0
 */
public class TiggerDetail {
    /**
     * id
     */
    private Integer id;
    /**
     * job描述
     */
    private String jobDescription;
    /**
     * cron 表达式
     */
    private String cronExpression;
    /**
     * 描述
     */
    private String description;
    /**
     * 下一次执行时间
     */
    private String nextFireTime;
    /**
     * 上一次执行时间
     */
    private String previousFireTime;
    /**
     * 开始执行时间
     */
    private String startTime;
    /**
     * 任务状态码
     */
    private String state;
    /**
     * 任务状态信息
     */
    private String stateMessage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = DateFormatUtils.format(nextFireTime, DateFormatConstant.EXTENDED_DATETIME_FORMAT);
    }

    public String getPreviousFireTime() {
        return previousFireTime;
    }

    public void setPreviousFireTime(Date previousFireTime) {
        this.previousFireTime = DateFormatUtils.format(previousFireTime, DateFormatConstant.EXTENDED_DATETIME_FORMAT);
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = DateFormatUtils.format(startTime, DateFormatConstant.EXTENDED_DATETIME_FORMAT);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateMessage() {
        return stateMessage;
    }

    public void setStateMessage(String stateMessage) {
        this.stateMessage = stateMessage;
    }
}
