package com.boo.quartz.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName JobStateEnum
 * @Description 任务状态枚举
 * @Author boo
 * @Date 2019/8/7 10:21
 * @Version 1.0.0
 */
public enum JobStateEnum {
    PAUSED("PAUSED", "暂停"),
    WAITING("WAITING", "等待中"),
    ACQUIRED("ACQUIRED", "正常执行"),
    BLOCKED("BLOCKED", "阻塞"),
    ERROR("ERROR", "错误"),
    NORMAL("NORMAL", "正常"),
    COMPLETE("COMPLETE", "完成"),
    NONE("NONE", "无");

    /**
     * 遍历，根据任务状态获取状态枚举
     *
     * @param state
     * @return
     */
    public static JobStateEnum getByState(String state) {
        if (StringUtils.isEmpty(state)) {
            return NONE;
        }
        for (JobStateEnum jobStateEnum : values()) {
            if (jobStateEnum.getState().equals(state)) {
                return jobStateEnum;
            }
        }
        return NONE;
    }

    JobStateEnum(String state, String message) {
        this.state = state;
        this.message = message;
    }

    /**
     * 状态
     */
    private String state;
    /**
     * 状态信息
     */
    private String message;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
