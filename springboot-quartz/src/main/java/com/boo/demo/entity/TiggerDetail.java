package com.boo.demo.entity;

import com.boo.demo.constants.DateFormatConstant;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class TiggerDetail {
    private Integer id;
    private String jobDescription;
    private String cronExpression;
    private String description;
    private String nextFireTime;
    private String previousFireTime;
    private String startTime;
    private String state;
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
        this.startTime = DateFormatUtils.format(startTime, DateFormatConstant.EXTENDED_DATETIME_FORMAT);;
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
