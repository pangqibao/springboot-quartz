package com.boo.quartz.constants;

/**
 * @ClassName JobConst
 * @Description 定时任务常量字段
 * @Author boo
 * @Date 2019/8/7 9:35
 * @Version 1.0.0
 */
public class JobConst {
    /**
     * Scheduler名字
     */
    public static String SCHEDULER = "DemoJobScheduler";

    /**
     * JOB分组
     */
    public static final String JOB_GROUP="DemoJobGroup";

    /**
     * Trigger分组
     */
    public static final String TRIGGER_GROUP="DemoTriggerGroup";

    /**
     * 任务类所在包的路径
     */
    public static final String BATCH_PACKAGE = "com.boo.quartz.job.";
}
